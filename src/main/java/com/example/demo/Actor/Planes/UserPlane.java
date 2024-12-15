package com.example.demo.Actor.Planes;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Projectiles.UserProjectile;
import javafx.animation.AnimationTimer;

/**
 * Represents the user-controlled plane in the game, extending the {@link FighterPlane} class.
 * <p>
 * The {@code UserPlane} class defines the behavior of the user's plane, including movement, firing projectiles,
 * taking damage, and providing visual feedback for invincibility.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = 750.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 350.0;
	private static final int IMAGE_HEIGHT = 40;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int velocityMultiplier;
	private int numberOfKills;

	/**
	 * Constructs a new {@code UserPlane} instance.
	 * <p>
	 * Initializes the user plane with an image, position, health, and velocity multiplier.
	 *
	 * @param initialHealth The initial health of the user plane.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
	}

	/**
	 * Updates the position of the user plane.
	 * <p>
	 * If the plane is moving, its vertical position is updated according to the current velocity multiplier.
	 * The plane's movement is constrained to within the vertical bounds.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}
	}

	/**
	 * Updates the state of the user plane.
	 * <p>
	 * This method is called to update the position and other necessary aspects of the user plane.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the user plane.
	 * <p>
	 * The user plane fires a projectile at a specified X position with an offset for the Y position.
	 *
	 * @return A new {@link UserProjectile} instance representing the projectile fired by the user plane.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	/**
	 * Handles the user plane taking damage.
	 * <p>
	 * The user plane can only take damage if it is not invincible. When the user plane is hit,
	 * invincibility is activated and a flashing effect is applied as visual feedback.
	 */
	@Override
	public void takeDamage() {
		if (!isInvincible()) { // Check if the user plane is not invincible
			super.takeDamage(); // Apply damage logic from FighterPlane
			startFlashingEffect(); // Trigger visual feedback for invincibility
		}
	}

	/**
	 * Starts the flashing effect to indicate the user plane is invincible.
	 * <p>
	 * The user plane will alternate between visible and semi-transparent states to provide visual feedback for invincibility.
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
	 * Checks if the user plane is moving.
	 * <p>
	 * The user plane is considered moving if its vertical velocity multiplier is non-zero.
	 *
	 * @return {@code true} if the user plane is moving, otherwise {@code false}.
	 */
	public boolean isMoving() {
		return velocityMultiplier != 0;
	}

	/**
	 * Makes the user plane move upward.
	 * <p>
	 * This method sets the velocity multiplier to -1, causing the plane to move upwards.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
	}

	/**
	 * Makes the user plane move downward.
	 * <p>
	 * This method sets the velocity multiplier to 1, causing the plane to move downwards.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
	}

	/**
	 * Stops the movement of the user plane.
	 * <p>
	 * This method sets the velocity multiplier to 0, causing the plane to stop moving.
	 */
	public void stop() {
		velocityMultiplier = 0;
	}

	/**
	 * Retrieves the current number of kills made by the user plane.
	 * <p>
	 * The kill count is tracked as the number of enemies defeated by the user plane.
	 *
	 * @return The number of kills made by the user plane.
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the number of kills made by the user plane.
	 * <p>
	 * This method is called when the user plane defeats an enemy.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Decrements the number of kills made by the user plane.
	 * <p>
	 * This method is called when the user plane loses a kill (e.g., for game reset).
	 */
	public void decrementKillCount() {
		numberOfKills--;
	}
}
