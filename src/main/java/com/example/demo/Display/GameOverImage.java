package com.example.demo.Display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the "Game Over" image displayed when the game ends.
 * This class extends {@link ImageView} to display an image on the screen.
 */
public class GameOverImage extends ImageView {

	/** Path to the "Game Over" image resource. */
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
	 * Constructs a {@code GameOverImage} object and sets its position on the screen.
	 *
	 * @param xPosition The X-coordinate for the image layout.
	 * @param yPosition The Y-coordinate for the image layout.
	 * @throws IllegalArgumentException if the image resource is not found.
	 */
	public GameOverImage(double xPosition, double yPosition) {
		var resource = getClass().getResource(IMAGE_NAME); // Safely fetch the resource
		if (resource != null) {
			setImage(new Image(resource.toExternalForm())); // Set the image if resource is found
		} else {
			throw new IllegalArgumentException("Resource not found: " + IMAGE_NAME);
		}

		// Set the position of the image
		setLayoutX(xPosition); // Set the X-coordinate position
		setLayoutY(yPosition); // Set the Y-coordinate position
	}
}
