package com.example.demo.Display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a heart display to visually show the player's health as hearts.
 */
public class HeartDisplay {

	/** Path to the heart image resource. */
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	/** Fixed height for each heart image. */
	private static final int HEART_HEIGHT = 50;
	/** Index of the first item in the heart container. */
	private static final int INDEX_OF_FIRST_ITEM = 0;

	/** Container to hold the heart images. */
	private HBox container;
	/** X-coordinate position for the heart display. */
	private final double containerXPosition;
	/** Y-coordinate position for the heart display. */
	private final double containerYPosition;
	/** Number of hearts to display initially. */
	private final int numberOfHeartsToDisplay;

	/**
	 * Constructs a {@code HeartDisplay} with a given position and number of hearts.
	 *
	 * @param xPosition The X-coordinate for the heart display.
	 * @param yPosition The Y-coordinate for the heart display.
	 * @param heartsToDisplay The initial number of hearts to display.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container (HBox) for displaying the hearts.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes the hearts and adds them to the container.
	 * <p>
	 * This method attempts to load the heart image and add the appropriate number of hearts to the container.
	 * If the heart image resource is not found, an error message is printed and an {@link IllegalArgumentException} is thrown.
	 * If the container is not initialized, an error message is printed and the method exits early.
	 */
	private void initializeHearts() {
		if (container == null) {
			System.err.println("Container is null, cannot add hearts.");
			return; // Exit early if container is not initialized
		}

		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			// Safely fetch the heart image resource
			var resource = getClass().getResource(HEART_IMAGE_NAME);

			if (resource != null) {
				// Set the image if the resource is found
				Image heartImage = new Image(resource.toExternalForm());
				ImageView heart = new ImageView(heartImage);
				heart.setFitHeight(HEART_HEIGHT);
				heart.setPreserveRatio(true);

				// Add the heart to the container
				container.getChildren().add(heart);
			} else {
				// Handle the error without logging, just print an error message
				System.err.println("Heart image not found: " + HEART_IMAGE_NAME);
				// Optionally, throw an exception or return
				throw new IllegalArgumentException("Heart image not found: " + HEART_IMAGE_NAME);
			}
		}
	}


	/**
	 * Removes one heart from the display, starting from the first heart.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
	}

	/**
	 * Returns the container (HBox) that holds the heart images.
	 *
	 * @return The HBox containing the hearts.
	 */
	public HBox getContainer() {
		return container;
	}
}
