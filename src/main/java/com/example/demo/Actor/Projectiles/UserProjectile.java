package com.example.demo.Actor.Projectiles;

/**
 * Represents a projectile fired by the user-controlled plane in the game.
 * <p>
 * The {@code UserProjectile} class extends the {@link Projectile} class and defines the behavior of projectiles fired by the user,
 * including their appearance and movement.
 */
public class UserProjectile extends Projectile {

	/** The name of the image file used for the user projectile. */
	private static final String IMAGE_NAME = "userfire.png";

	/** The height of the user projectile's image. */
	private static final int IMAGE_HEIGHT = 7;

	/** The horizontal velocity of the user projectile. */
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructs a {@code UserProjectile} at the specified initial position.
	 *
	 * @param initialXPos The initial X-coordinate of the projectile.
	 * @param initialYPos The initial Y-coordinate of the projectile.
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the user projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the user projectile.
	 * <p>
	 * This includes updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
