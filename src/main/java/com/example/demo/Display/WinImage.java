package com.example.demo.Display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@code WinImage} class represents the "You Win" image displayed
 * when the player completes the game successfully.
 * It extends {@link ImageView} to show an image at a specific position.
 */
public class WinImage extends ImageView {

	/** Path to the "You Win" image resource. */
	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";

	/** The fixed height of the win image. */
	private static final int HEIGHT = 500;

	/** The fixed width of the win image. */
	private static final int WIDTH = 800;

	/**
	 * Constructs a {@code WinImage} object at the specified screen position.
	 * The image is initially invisible. If the image resource cannot be found,
	 * a warning will be logged to the console.
	 *
	 * @param xPosition The X-coordinate where the image is positioned.
	 * @param yPosition The Y-coordinate where the image is positioned.
	 * @throws IllegalArgumentException if the image resource cannot be found at the specified path.
	 */
	public WinImage(double xPosition, double yPosition) {
		var resource = getClass().getResource(IMAGE_NAME);
		if (resource != null) {
			setImage(new Image(resource.toExternalForm()));
		} else {
			System.err.println("Warning: Resource not found: " + IMAGE_NAME);
		}
		setVisible(false); // Image is hidden by default
		setFitHeight(HEIGHT);
		setFitWidth(WIDTH);
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

	/**
	 * Makes the win image visible on the screen.
	 */
	public void showWinImage() {
		setVisible(true);
	}
}
