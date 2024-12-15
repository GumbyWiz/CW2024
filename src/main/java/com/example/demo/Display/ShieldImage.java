package com.example.demo.Display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@code ShieldImage} class represents a shield image in the game.
 * It extends {@link ImageView} to display a shield graphic that can be shown or hidden.
 */
public class ShieldImage extends ImageView {

	/** Default height of the shield image. */
	private static final int SHIELD_HEIGHT = 100;

	/** Default width of the shield image. */
	private static final int SHIELD_WIDTH = 75;

	/**
	 * Constructs a {@code ShieldImage} object with specified position coordinates.
	 * <p>
	 * Initializes the shield image with default dimensions, makes it invisible by default,
	 * and sets its position on the screen.
	 *
	 * @param xPosition The X-coordinate for the shield's layout position.
	 * @param yPosition The Y-coordinate for the shield's layout position.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		initializeShieldImage(xPosition, yPosition);
	}

	/**
	 * Initializes the shield image properties, including its layout position,
	 * image source, dimensions, and visibility.
	 *
	 * @param xPosition The X-coordinate for the shield's layout position.
	 * @param yPosition The Y-coordinate for the shield's layout position.
	 * @throws IllegalArgumentException if the shield image resource is not found.
	 */
	private void initializeShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);

		// Safely retrieve the image resource
		var resource = getClass().getResource("/com/example/demo/images/shield.png");
		if (resource == null) {
			throw new IllegalArgumentException("Shield image resource not found: /com/example/demo/images/shield.png");
		}
		this.setImage(new Image(resource.toExternalForm()));
		this.setFitHeight(SHIELD_HEIGHT);
		this.setFitWidth(SHIELD_WIDTH);
		this.setVisible(false); // Hidden by default
	}


	/**
	 * Makes the shield image visible on the screen.
	 */
	public void showShield() {
		this.setVisible(true);
	}

	/**
	 * Hides the shield image from the screen.
	 */
	public void hideShield() {
		this.setVisible(false);
	}
}
