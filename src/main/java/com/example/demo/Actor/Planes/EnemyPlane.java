package com.example.demo.Actor.Planes;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Projectiles.EnemyProjectile;
import javafx.animation.AnimationTimer;

/**
 * Represents an enemy plane in the game.
 * This plane can move horizontally, fire projectiles, and take damage with visual feedback.
 */
public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 0.0;
	private static final int INITIAL_HEALTH = 2;
	private static final double FIRE_RATE = 0.01;

	/**
	 * Constructs an enemy plane at the specified initial position.
	 *
	 * @param initialXPos The initial X-coordinate of the plane.
	 * @param initialYPos The initial Y-coordinate of the plane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile from the enemy plane with a certain probability.
	 *
	 * @return A new {@link EnemyProjectile} if fired, or {@code null} if not.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (shouldFireProjectile()) {
			return new EnemyProjectile(
					getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET),
					getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET)
			);
		}
		return null;
	}

	/**
	 * Updates the behavior of the enemy plane, including movement.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Handles the logic when the enemy plane takes damage.
	 * If the plane is not invincible, it applies damage and starts a visual flashing effect.
	 */
	@Override
	public void takeDamage() {
		if (!isInvincible()) { // Apply damage only if not invincible
			super.takeDamage();
			startFlashingEffect();
		}
	}

	/**
	 * Starts a visual flashing effect to indicate temporary invincibility.
	 * The plane alternates between visible and semi-transparent states.
	 */
	private void startFlashingEffect() {
		AnimationTimer flashTimer = new AnimationTimer() {
			private boolean visible = true;
			private long lastToggleTime = 0;

			@Override
			public void handle(long now) {
				if (now - lastToggleTime >= 200_000_000L) { // Toggle every 200ms
					visible = !visible;
					setOpacity(visible ? 1.0 : 0.5); // Alternate between fully visible and semi-transparent
					lastToggleTime = now;
				}
				if (!isInvincible()) {
					setOpacity(1.0); // Reset to fully visible
					stop(); // Stop the flashing effect
				}
			}
		};
		flashTimer.start();
	}

	/**
	 * Determines if the enemy plane should fire a projectile based on the fire rate.
	 *
	 * @return {@code true} if the plane fires, {@code false} otherwise.
	 */
	private boolean shouldFireProjectile() {
		return Math.random() < FIRE_RATE;
	}
}
