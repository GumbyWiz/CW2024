package com.example.demo.Actor.Planes;

import com.example.demo.Actor.ActiveActorDestructible;
import javafx.animation.AnimationTimer;

/**
 * Represents a generic fighter plane in the game.
 * Fighter planes have health, can take damage, fire projectiles, and activate temporary invincibility.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;
	private boolean isInvincible; // Tracks if the fighter plane is invincible
	private long invincibilityStartTime; // Timestamp for invincibility activation
	private static final long INVINCIBILITY_DURATION = 1_000_000_000L; // 1 second in nanoseconds

	/**
	 * Constructs a FighterPlane with the specified image, position, and health.
	 *
	 * @param imageName    The name of the image file representing the plane.
	 * @param imageHeight  The height of the plane's image.
	 * @param initialXPos  The initial X-coordinate of the plane.
	 * @param initialYPos  The initial Y-coordinate of the plane.
	 * @param health       The initial health of the plane.
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
		this.isInvincible = false;
	}

	/**
	 * Fires a projectile from the fighter plane.
	 *
	 * @return An {@link ActiveActorDestructible} representing the fired projectile, or {@code null} if no projectile is fired.
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Reduces the fighter plane's health by 1 if it is not invincible.
	 * If health reaches zero, the plane is destroyed. Activates invincibility after taking damage.
	 */
	@Override
	public void takeDamage() {
		if (!isInvincible) {
			health--;
			if (isHealthDepleted()) {
				destroy();
			} else {
				activateInvincibility();
			}
		}
	}

	/**
	 * Activates temporary invincibility for the plane. Invincibility duration is controlled
	 * using an {@link AnimationTimer}.
	 */
	public void activateInvincibility() {
		isInvincible = true;
		invincibilityStartTime = System.nanoTime();

		// Timer to disable invincibility after the specified duration
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (now - invincibilityStartTime >= INVINCIBILITY_DURATION) {
					isInvincible = false;
					stop(); // Stop the timer when invincibility ends
				}
			}
		}.start();
	}

	/**
	 * Computes the X-coordinate for firing a projectile, adjusted by an offset.
	 *
	 * @param xPositionOffset The X-axis offset for the projectile's initial position.
	 * @return The X-coordinate for the projectile's initial position.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Computes the Y-coordinate for firing a projectile, adjusted by an offset.
	 *
	 * @param yPositionOffset The Y-axis offset for the projectile's initial position.
	 * @return The Y-coordinate for the projectile's initial position.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the plane's health has been fully depleted.
	 *
	 * @return {@code true} if health is zero, {@code false} otherwise.
	 */
	private boolean isHealthDepleted() {
		return health <= 0;
	}

	/**
	 * Retrieves the current health of the fighter plane.
	 *
	 * @return The current health value.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Checks if the fighter plane is currently invincible.
	 *
	 * @return {@code true} if the plane is invincible, {@code false} otherwise.
	 */
	public boolean isInvincible() {
		return isInvincible;
	}
}
