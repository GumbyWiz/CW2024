package com.example.demo;

import com.example.demo.Actor.Planes.Boss;
import javafx.application.Platform;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BossTest {

    private Boss boss;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        // Initialize the JavaFX Toolkit
        Thread thread = new Thread(() -> Platform.startup(() -> {}));
        thread.setDaemon(true);
        thread.start();
        thread.join(); // Ensure JavaFX is initialized before proceeding
    }

    @BeforeEach
    void setUp() {
        // Initialize Boss with initial health
        boss = new Boss();
    }

    @Test
    void testInitialSetup() {
        // Verify initial health and other properties
        assertEquals(2, boss.getHealth(), "Boss should have initial health of 2");
        assertFalse(boss.isInvincible(), "Boss should not be invincible initially");
        assertFalse(boss.isShielded, "Boss should not be shielded initially");
    }

    @Test
    void testTakeDamageWithoutShield() {
        int initialHealth = boss.getHealth();

        // Simulate taking damage without shield
        boss.takeDamage();
        assertEquals(initialHealth - 1, boss.getHealth(), "Boss health should decrease by 1 when not shielded and not invincible");
        assertTrue(boss.isInvincible(), "Boss should become invincible after taking damage");
    }

    @Test
    void testTakeDamageWithShield() {
        // Activate shield manually
        boss.activateShield();
        int initialHealth = boss.getHealth();

        // Simulate taking damage with shield activated
        boss.takeDamage();
        assertEquals(initialHealth, boss.getHealth(), "Boss health should not decrease when shielded");
        assertTrue(boss.isShielded, "Boss should remain shielded after taking damage");
    }

    @Test
    void testShieldActivationAndDeactivation() {
        // Simulate shield activation
        boss.activateShield();
        assertTrue(boss.isShielded, "Boss should be shielded after activation");

        // Simulate shield deactivation after max frames
        for (int i = 0; i < 100; i++) {
            boss.updateShield();
        }
        assertFalse(boss.isShielded, "Boss should not be shielded after shield duration expires");
    }

    @Test
    void testMovement() {
        double initialYPosition = boss.getLayoutY() + boss.getTranslateY();

        // Perform a few movement updates
        boss.updatePosition();
        assertNotEquals(initialYPosition, boss.getLayoutY() + boss.getTranslateY(), "Boss should move vertically based on move pattern");
    }

    @Test
    void testProjectileFiring() {
        // Test if the boss fires a projectile based on fire rate
        int fireCount = 0;

        // Fire several times to test the probability
        for (int i = 0; i < 1000; i++) {
            if (boss.fireProjectile() != null) {
                fireCount++;
            }
        }

        // Check that the firing rate is within expected range
        double firingRate = (double) fireCount / 1000;
        assertTrue(firingRate >= 0.03 && firingRate <= 0.05, "Firing rate should be close to BOSS_FIRE_RATE");
    }

    @Test
    void testInvincibilityFrames() {
        // Activate invincibility
        boss.activateInvincibility();
        int initialHealth = boss.getHealth();

        // Take damage during invincibility (health should not change)
        boss.takeDamage();
        assertEquals(initialHealth, boss.getHealth(), "Health should not decrease while invincible");
    }

    @Test
    void testMovePattern() {
        // Make sure the move pattern has the expected behavior
        List<Integer> expectedMovePattern = new ArrayList<>(Arrays.asList(8, -8, 0, 8, -8, 0));
        expectedMovePattern.addAll(Arrays.asList(8, -8, 0, 8, -8, 0));  // Repeat the same pattern

        // Update the Boss's position and verify movement
        boss.updatePosition();
        int currentMove = boss.getNextMove();
        assertTrue(expectedMovePattern.contains(currentMove), "The Boss should follow its move pattern");
    }
}
