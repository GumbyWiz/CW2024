package com.example.demo.Actor;

/**
 * Represents an actor that can be destroyed within the game.
 * This class extends from {@link ActiveActor} and implements the {@link Destructible} interface.
 * It includes functionality for managing the actor's destruction and invincibility status.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	// Flag to check if the actor is destroyed
	private boolean isDestroyed;

	// Flag to check if the actor is invincible (cannot be destroyed)
	private final boolean isInvincible;

	/**
	 * Constructs an ActiveActorDestructible with the specified parameters.
	 * Initializes the actor with a specified image, size, and initial position, and sets
	 * the initial destruction and invincibility states.
	 *
	 * @param imageName      The name of the image to represent the actor.
	 * @param imageHeight    The height of the actor image.
	 * @param initialXPos    The initial X position of the actor.
	 * @param initialYPos    The initial Y position of the actor.
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;  // Actor is not destroyed initially
		isInvincible = false; // Actor is not invincible initially
	}

	/**
	 * Abstract method to update the position of the actor.
	 * Subclasses should implement this method to define the behavior of the actor's movement.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Abstract method to update the state of the actor.
	 * Subclasses should implement this method to define the actor's behavior when updated.
	 */
	public abstract void updateActor();

	/**
	 * Abstract method that is invoked when the actor takes damage.
	 * Subclasses should implement this method to define how the actor responds to taking damage.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Marks the actor as destroyed if it is not already destroyed.
	 * This method is used to destroy the actor in the game.
	 */
	public void destroy() {
		if (!isDestroyed) {  // Only set it if it's not already destroyed
			isDestroyed = true; // Directly set isDestroyed to true
		}
	}

	/**
	 * Returns whether the actor is destroyed.
	 *
	 * @return {@code true} if the actor is destroyed, otherwise {@code false}.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Returns whether the actor is invincible.
	 *
	 * @return {@code true} if the actor is invincible, otherwise {@code false}.
	 */
	public boolean isInvincible() {
		return isInvincible;
	}
}
