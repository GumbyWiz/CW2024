package com.example.demo.Actor.Projectiles;

/**
 * Represents a projectile fired by the boss plane in the game.
 * <p>
 * The {@code BossProjectile} class extends the {@link Projectile} class and defines the behavior of boss-fired projectiles,
 * including their appearance and movement.
 */
public class BossProjectile extends Projectile {

	/** The name of the image file used for the boss projectile. */
	private static final String IMAGE_NAME = "fireball.png";

	/** The height of the boss projectile's image. */
	private static final int IMAGE_HEIGHT = 75;

	/** The horizontal velocity of the boss projectile. */
	private static final int HORIZONTAL_VELOCITY = -15;

	/** The initial X-coordinate for all boss projectiles. */
	private static final int INITIAL_X_POSITION = 1250;

	/**
	 * Constructs a {@code BossProjectile} at the specified initial Y-coordinate.
	 * <p>
	 * The X-coordinate is set to a predefined position for all boss projectiles.
	 *
	 * @param initialYPos The initial Y-coordinate of the projectile.
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the position of the boss projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the boss projectile.
	 * <p>
	 * This includes updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
