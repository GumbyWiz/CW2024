package com.example.demo.Actor;

import javafx.scene.image.*;

/**
 * Represents an active game actor with an image, position, and movement capabilities.
 * This class extends {@link ImageView}, enabling actors to be represented visually
 * in the game scene.
 */
public abstract class ActiveActor extends ImageView {

	// Base location for the actor's image resources
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs an ActiveActor with the specified image, size, and initial position.
	 *
	 * @param imageName      The name of the image file to represent the actor.
	 * @param imageHeight    The height of the actor's image.
	 * @param initialXPos    The initial X position of the actor on the screen.
	 * @param initialYPos    The initial Y position of the actor on the screen.
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		String resourcePath = IMAGE_LOCATION + imageName;
		var resource = getClass().getResource(resourcePath);

		if (resource == null) {
			throw new IllegalArgumentException("Resource not found: " + resourcePath);
		}

		this.setImage(new Image(resource.toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true); // Ensures the aspect ratio of the image is maintained
	}

	/**
	 * Abstract method to update the position of the actor.
	 * Subclasses must implement this method to define specific behavior for actor movement.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by a specified distance.
	 *
	 * @param horizontalMove The distance to move the actor along the X-axis. Positive values move the actor to the right, and negative values move it to the left.
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by a specified distance.
	 *
	 * @param verticalMove The distance to move the actor along the Y-axis. Positive values move the actor downward, and negative values move it upward.
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
}
