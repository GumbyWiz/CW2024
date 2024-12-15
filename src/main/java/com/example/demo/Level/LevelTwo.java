package com.example.demo.Level;

import com.example.demo.Display.MiniMenu;
import com.example.demo.Actor.Planes.Boss;

/**
 * Represents the second level in the game, {@code LevelTwo}.
 * <p>
 * This level introduces a boss and includes specific gameplay logic such as spawning enemies, checking game-over conditions,
 * and navigating to the next level upon defeating the boss.
 */
public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.Level.LevelThree";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;

	/**
	 * Constructs a new {@code LevelTwo} instance.
	 * <p>
	 * Initializes the background, player health, and the boss for this level. It also sets up the mini menu, allowing
	 * the player to resume the game or return to the main menu.
	 *
	 * @param screenHeight The height of the screen for this level.
	 * @param screenWidth The width of the screen for this level.
	 */
	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

		// Initialize boss
		boss = new Boss();

		// Initialize the mini menu
		com.example.demo.Display.MiniMenu miniMenu = new MiniMenu(
				getRoot(),
				this::resumeGame,  // Action for "Continue"
				this::returnToMainMenu  // Action for "Return to Main Menu"
		);
		setMiniMenu(miniMenu); // Set the mini menu in LevelParent
	}

	/**
	 * Initializes the friendly units for this level, specifically the user/player.
	 * <p>
	 * This method adds the player (user) to the root container of the level.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Checks whether the game is over, either by the player's destruction or by defeating the boss.
	 * <p>
	 * If the player is destroyed, the game is lost. If the boss is defeated, the next level is triggered.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Spawns enemy units for this level.
	 * <p>
	 * In {@code LevelTwo}, the boss is added to the level when no other enemies remain.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
			getRoot().getChildren().add(boss.getShieldImage());
		}
	}

	/**
	 * Instantiates the level view for this level.
	 * <p>
	 * The level view is a custom implementation specific to {@code LevelTwo}, which handles the display and logic
	 * of the health bar and other level-specific elements.
	 *
	 * @return A {@link LevelView} instance tailored for {@code LevelTwo}.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
	}
}
