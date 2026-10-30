// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import net.dasik.social.api.command.GameRuleCommandBridge;
import net.dasik.social.api.command.UniversalCommandRegistry;
import net.minecraft.SharedConstants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KeepGearCommandTest {

    @BeforeAll
    public static void setUpAll() {
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable ignored) {
        }
    }

    @Test
    @DisplayName("Pillar 1: Chaos Fuzzing - Boundary and Malformed Input Fuzzing")
    void testChaosFuzzing() {
        GameRuleCommandBridge bridge = KeepGearCommands.createBridge();
        assertNotNull(bridge, "Bridge must not be null");

        // 1. Boundary & Extreme Saturated Values for Double (penaltyPercent: [0.0, 100.0])
        GameRuleCommandBridge.SetResult resOver = bridge.setValue(null, "penaltyPercent", "999999.0");
        assertTrue(resOver.isSuccess(), "Over-saturated value should succeed with warning");
        assertEquals(GameRuleCommandBridge.Status.SATURATED_WARNING, resOver.getStatus());
        assertEquals(100.0, ((Number) resOver.getValue()).doubleValue(), 0.001);

        GameRuleCommandBridge.SetResult resUnder = bridge.setValue(null, "penaltyPercent", "-50.0");
        assertTrue(resUnder.isSuccess(), "Under-saturated value should succeed with warning");
        assertEquals(GameRuleCommandBridge.Status.SATURATED_WARNING, resUnder.getStatus());
        assertEquals(0.0, ((Number) resUnder.getValue()).doubleValue(), 0.001);

        GameRuleCommandBridge.SetResult resValid = bridge.setValue(null, "penaltyPercent", "25.5");
        assertTrue(resValid.isSuccess());
        assertEquals(25.5, ((Number) resValid.getValue()).doubleValue(), 0.001);

        // 2. Malformed & Extreme String Fuzzing on Numerical field
        String[] malformedDoubles = {
            "NaN", "Infinity", "-Infinity", "abc", "12.34.56", "", "   ", "0x12", "null", "!@#$%^&*()", "1e500"
        };
        for (String fuzzed : malformedDoubles) {
            GameRuleCommandBridge.SetResult res = bridge.setValue(null, "penaltyPercent", fuzzed);
            assertNotNull(res, "SetResult must never be null when fuzzing: " + fuzzed);
            assertTrue(res.isError() || res.isWarning() || res.isSuccess());
        }

        // 3. Boolean Variations & Fuzzing
        String[] truthy = {"true", "1", "yes", "on", "enable", "enabled", "TRUE", "Yes"};
        for (String t : truthy) {
            GameRuleCommandBridge.SetResult res = bridge.setValue(null, "enabled", t);
            assertTrue(res.isSuccess(), "Truthy input '" + t + "' should parse as true");
            assertEquals(true, res.getValue());
        }

        String[] falsy = {"false", "0", "no", "off", "disable", "disabled", "FALSE", "No"};
        for (String f : falsy) {
            GameRuleCommandBridge.SetResult res = bridge.setValue(null, "enabled", f);
            assertTrue(res.isSuccess(), "Falsy input '" + f + "' should parse as false");
            assertEquals(false, res.getValue());
        }

        String[] invalidBools = {"maybe", "2", "-1", "none", "null", "blah", "true1", ""};
        for (String inv : invalidBools) {
            GameRuleCommandBridge.SetResult res = bridge.setValue(null, "enabled", inv);
            assertTrue(res.isError(), "Malformed boolean input '" + inv + "' should return error status");
        }

        // 4. Random Chaos Generator Fuzzing (500 iterations)
        Random random = new Random(42);
        List<String> keys = new ArrayList<>(bridge.getRegisteredKeys());
        for (int i = 0; i < 500; i++) {
            String key = keys.get(random.nextInt(keys.size()));
            byte[] bytes = new byte[random.nextInt(32) + 1];
            random.nextBytes(bytes);
            String randomPayload = new String(bytes);

            assertDoesNotThrow(() -> {
                GameRuleCommandBridge.SetResult r = bridge.setValue(null, key, randomPayload);
                assertNotNull(r);
            }, "Bridge setValue crashed on random payload");
        }
    }

    @Test
    @DisplayName("Pillar 2: Concurrency Load - Multi-Threaded Read & Mutation Stress")
    void testConcurrencyLoad() throws InterruptedException {
        GameRuleCommandBridge bridge = KeepGearCommands.createBridge();
        int threadCount = 20;
        int operationsPerThread = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadCount);
        AtomicInteger failureCount = new AtomicInteger(0);

        for (int t = 0; t < threadCount; t++) {
            final int threadId = t;
            executor.submit(() -> {
                try {
                    startLatch.await();
                    for (int i = 0; i < operationsPerThread; i++) {
                        // Concurrent reads
                        Object enabled = bridge.getValue(null, "enabled");
                        assertNotNull(enabled);
                        Object penalty = bridge.getValue(null, "penaltyPercent");
                        assertNotNull(penalty);
                        assertEquals(9, bridge.getRegisteredKeys().size());

                        // Concurrent mutation
                        if (threadId % 2 == 0) {
                            bridge.setValue(null, "penaltyPercent", String.valueOf((i % 100)));
                        } else {
                            bridge.setValue(null, "enabled", i % 2 == 0 ? "true" : "false");
                        }
                    }
                } catch (Throwable e) {
                    failureCount.incrementAndGet();
                } finally {
                    endLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        boolean finished = endLatch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        assertTrue(finished, "Concurrency stress test timed out");
        assertEquals(0, failureCount.get(), "Concurrency stress test encountered exceptions");
    }

    @Test
    @DisplayName("Pillar 3: Brigadier AST - Verification of Command Tree & Subcommands")
    void testBrigadierAstStructure() {
        CommandDispatcher<CommandSourceStack> dispatcher = new CommandDispatcher<>();
        GameRuleCommandBridge bridge = KeepGearCommands.createBridge();
        UniversalCommandRegistry.Builder builder = UniversalCommandRegistry.builder("keepgear", dispatcher);
        KeepGearCommands.configureBuilder(builder, bridge);
        builder.register();

        CommandNode<CommandSourceStack> root = dispatcher.getRoot().getChild("keepgear");
        assertNotNull(root, "Root command '/keepgear' should be registered");
        assertTrue(root instanceof LiteralCommandNode, "Root node must be a LiteralCommandNode");

        // Verify standard subcommands: help, status, get, set, reset, reload
        CommandNode<CommandSourceStack> helpNode = root.getChild("help");
        assertNotNull(helpNode, "Subcommand '/keepgear help' must exist");

        CommandNode<CommandSourceStack> statusNode = root.getChild("status");
        assertNotNull(statusNode, "Subcommand '/keepgear status' must exist");

        CommandNode<CommandSourceStack> getNode = root.getChild("get");
        assertNotNull(getNode, "Subcommand '/keepgear get' must exist");
        assertNotNull(getNode.getChild("key"), "Subcommand '/keepgear get <key>' argument node must exist");

        CommandNode<CommandSourceStack> setNode = root.getChild("set");
        assertNotNull(setNode, "Subcommand '/keepgear set' must exist");
        assertNotNull(setNode.getChild("key"), "Subcommand '/keepgear set <key>' argument node must exist");
        assertNotNull(setNode.getChild("key").getChild("value"), "Subcommand '/keepgear set <key> <value>' argument node must exist");

        CommandNode<CommandSourceStack> resetNode = root.getChild("reset");
        assertNotNull(resetNode, "Subcommand '/keepgear reset' must exist");

        CommandNode<CommandSourceStack> reloadNode = root.getChild("reload");
        assertNotNull(reloadNode, "Subcommand '/keepgear reload' must exist");
    }

    @Test
    @DisplayName("Pillar 5: Sidedness & Common Code Hygiene - 0 Client Imports and GPLv3 Header")
    void testSidednessAndHygiene() throws IOException {
        Path commandSrcDir = Path.of("src/main/java/net/vanillaoutsider/keepgear/command");
        assertTrue(Files.exists(commandSrcDir), "Command package directory must exist: " + commandSrcDir.toAbsolutePath());

        try (Stream<Path> stream = Files.walk(commandSrcDir)) {
            List<Path> javaFiles = stream.filter(p -> p.toString().endsWith(".java")).toList();
            assertFalse(javaFiles.isEmpty(), "Command package should contain at least 1 java file");

            for (Path javaFile : javaFiles) {
                List<String> lines = Files.readAllLines(javaFile);
                assertFalse(lines.isEmpty(), "Java file should not be empty: " + javaFile);

                // GPLv3 single-line header verification
                assertEquals("// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3", lines.get(0).trim(),
                        "File must begin with canonical GPLv3 header: " + javaFile.getFileName());

                // Zero net.minecraft.client.* imports verification
                for (String line : lines) {
                    assertFalse(line.trim().startsWith("import net.minecraft.client."),
                            "Common command code must not import net.minecraft.client.* (Sidedness violation in "
                                    + javaFile.getFileName() + "): " + line);
                }
            }
        }
    }
}
