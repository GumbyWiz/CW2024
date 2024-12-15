package com.example.demo;

import com.example.demo.Actor.Planes.UserPlane;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserPlaneTest {

    private UserPlane userPlane;

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
        // Initialize UserPlane with initial health
        userPlane = new UserPlane(5);
    }

    @Test
    void testInitialSetup() {
        // Verify initial health and other logic
        assertEquals(5, userPlane.getHealth());
        assertEquals(0, userPlane.getNumberOfKills());
    }

    @Test
    void testMoveUpAndDown() {
        userPlane.moveUp();
        assertTrue(userPlane.isMoving(), "UserPlane should move up.");

        userPlane.moveDown();
        assertTrue(userPlane.isMoving(), "UserPlane should move down.");

        userPlane.stop();
        assertFalse(userPlane.isMoving(), "UserPlane should stop.");
    }

    @Test
    void testTakeDamage() {
        int initialHealth = userPlane.getHealth();

        userPlane.takeDamage();
        assertEquals(initialHealth - 1, userPlane.getHealth(), "Health should decrease by 1 after taking damage");
    }

    @Test
    void testInvincibilityFrames() {
        // Activate invincibility
        userPlane.activateInvincibility();
        int initialHealth = userPlane.getHealth();

        // Take damage during invincibility (health should not change)
        userPlane.takeDamage();
        assertEquals(initialHealth, userPlane.getHealth(), "Health should not decrease while invincible");
    }

    @Test
    void testIncrementKillCount() {
        userPlane.incrementKillCount();
        assertEquals(1, userPlane.getNumberOfKills(), "Kill count should increment by 1.");
    }

    @Test
    void testDecrementKillCount() {
        userPlane.incrementKillCount();
        userPlane.incrementKillCount();
        userPlane.decrementKillCount();
        assertEquals(1, userPlane.getNumberOfKills(), "Kill count should decrement by 1.");
    }
}
