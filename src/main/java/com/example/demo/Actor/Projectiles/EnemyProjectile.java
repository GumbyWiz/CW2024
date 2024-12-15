package com.example.demo.Actor.Projectiles;

/**
 * Represents a projectile fired by an enemy plane in the game.
 * <p>
 * The {@code EnemyProjectile} class extends the {@link Projectile} class and defines the behavior of enemy-fired projectiles,
 * including their appearance and movement.
 */
public class EnemyProjectile extends Projectile {

	/** The name of the image file used for the enemy projectile. */
	private static final String IMAGE_NAME = "enemyFire.png";

	/** The height of the enemy projectile's image. */
	private static final int IMAGE_HEIGHT = 25;

	/** The horizontal velocity of the enemy projectile. */
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Constructs an {@code EnemyProjectile} at the specified initial position.
	 *
	 * @param initialXPos The initial X-coordinate of the projectile.
	 * @param initialYPos The initial Y-coordinate of the projectile.
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the enemy projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the enemy projectile.
	 * <p>
	 * This includes updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

}
