package com.example.demo.Level;

import com.example.demo.Display.GameOverImage;
import com.example.demo.Display.HeartDisplay;
import com.example.demo.Display.WinImage;
import javafx.scene.Group;

/**
 * Handles the visual representation of the game state, including the player's heart display,
 * win screen, and game-over screen.
 */
public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSITION = -375;

	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;

	/**
	 * Constructs a new LevelView for the specified game level.
	 *
	 * @param root            The root Group to which visual elements will be added.
	 * @param heartsToDisplay The initial number of hearts to display in the heart display.
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
	}

	/**
	 * Displays the heart container on the screen.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Displays the win image on the screen.
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	/**
	 * Displays the game-over image on the screen.
	 */
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	/**
	 * Updates the heart display to reflect the player's remaining hearts.
	 *
	 * @param heartsRemaining The number of hearts remaining for the player.
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}
}
