package com.example.demo.Actor.Projectiles;

import com.example.demo.Actor.ActiveActorDestructible;

/**
 * Represents a base class for projectiles in the game.
 * <p>
 * The {@code Projectile} class extends {@link ActiveActorDestructible} and provides common functionality for all projectiles,
 * such as initialization and damage handling. Specific projectile types (e.g., user, enemy, boss) should extend this class
 * to define their unique behavior.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructs a {@code Projectile} with the specified image, dimensions, and initial position.
	 *
	 * @param imageName    The name of the image file representing the projectile.
	 * @param imageHeight  The height of the projectile's image.
	 * @param initialXPos  The initial X-coordinate of the projectile.
	 * @param initialYPos  The initial Y-coordinate of the projectile.
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles the behavior when the projectile takes damage.
	 * <p>
	 * When a projectile takes damage, it is destroyed.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Updates the position of the projectile.
	 * <p>
	 * This method must be implemented by subclasses to define specific movement behavior for each type of projectile.
	 */
	@Override
	public abstract void updatePosition();
}
