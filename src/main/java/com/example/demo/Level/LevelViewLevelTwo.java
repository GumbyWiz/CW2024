package com.example.demo.Level;

import com.example.demo.Display.ShieldImage;
import javafx.scene.Group;

/**
 * Represents the view for Level Two of the game, displaying hearts and the shield image.
 * This class is responsible for adding the shield image to the root group and managing the heart display.
 */
public class LevelViewLevelTwo extends LevelView {

	// Constant values for the shield image position
	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;

	// Instance variables
	private final Group root;
	private final ShieldImage shieldImage;

	/**
	 * Constructs a LevelView for Level Two with the specified root group and hearts to display.
	 * Initializes the shield image at the predefined position and adds it to the root group.
	 *
	 * @param root           The root group where the images will be added.
	 * @param heartsToDisplay The number of hearts to display for the player.
	 */
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}

	/**
	 * Adds the shield image to the root group.
	 * This method ensures that the shield image is visible on the screen.
	 */
	private void addImagesToRoot() {
		root.getChildren().add(shieldImage);
	}
}
