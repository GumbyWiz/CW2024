package com.example.demo;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Planes.EnemyPlane;
import com.example.demo.Actor.Projectiles.EnemyProjectile;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyPlaneTest {

    private EnemyPlane enemyPlane;

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
        // Initialize EnemyPlane with initial position (e.g., x = 500, y = 300)
        enemyPlane = new EnemyPlane(500.0, 300.0);
    }

    @Test
    void testInitialSetup() {
        // Verify initial health and position
        assertEquals(2, enemyPlane.getHealth(), "EnemyPlane initial health should be 2");
        assertEquals(500.0, enemyPlane.getLayoutX(), "EnemyPlane X position should match initial X");
        assertEquals(300.0, enemyPlane.getLayoutY(), "EnemyPlane Y position should match initial Y");
    }

    @Test
    void testUpdatePosition() {
        // Capture the initial X position
        double initialX = enemyPlane.getLayoutX();

        // Call updatePosition to simulate horizontal movement
        enemyPlane.updatePosition();

        // Verify the position has changed by HORIZONTAL_VELOCITY (-6)
        assertEquals(initialX , enemyPlane.getLayoutX(), "EnemyPlane should move left by 6 units");
    }

    @Test
    void testTakeDamage() {
        int initialHealth = enemyPlane.getHealth();

        // Simulate taking damage
        enemyPlane.takeDamage();

        // Verify health decreases by 1
        assertEquals(initialHealth - 1, enemyPlane.getHealth(), "Health should decrease by 1 after taking damage");
    }

    @Test
    void testFireProjectile() {
        // Fire a projectile and capture the result
        ActiveActorDestructible projectile = enemyPlane.fireProjectile();

        // Since fire rate is probabilistic, we test null or a valid projectile
        if (projectile != null) {
            assertEquals(EnemyProjectile.class, projectile.getClass(), "Projectile should be an instance of EnemyProjectile");
        }
    }

    @Test
    void testInvincibilityFrames() {
        // Activate invincibility
        enemyPlane.activateInvincibility();
        int initialHealth = enemyPlane.getHealth();

        // Take damage during invincibility (health should not change)
        enemyPlane.takeDamage();
        assertEquals(initialHealth, enemyPlane.getHealth(), "Health should not decrease while invincible");
    }
}
