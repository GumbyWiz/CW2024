package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Display.MiniMenu;
import com.example.demo.Actor.Planes.EnemyPlane;

/**
 * Represents the first level of the game.
 * It initializes the player's setup, manages enemy spawns, and transitions to the next level upon conditions being met.
 */
public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.Level.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 2;
	private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	/**
	 * Constructs the first level with the specified screen dimensions.
	 *
	 * @param screenHeight The height of the screen.
	 * @param screenWidth  The width of the screen.
	 */
	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		initializeMiniMenu();
	}

	/**
	 * Initializes the mini menu for the level.
	 */
	private void initializeMiniMenu() {
		MiniMenu miniMenu = new MiniMenu(
				getRoot(),
				this::resumeGame,  // Action for "Continue"
				this::returnToMainMenu  // Action for "Return to Main Menu"
		);
		setMiniMenu(miniMenu); // Set the mini menu in LevelParent
	}

	/**
	 * Checks if the game is over or if the player has met the win conditions.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (userHasReachedKillTarget()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Adds the user's plane to the scene.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Spawns enemy units based on the current number of enemies and spawn probability.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	/**
	 * Creates the level-specific view, including heart displays and game state visuals.
	 *
	 * @return The level view instance.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks if the player has reached the kill target to advance to the next level.
	 *
	 * @return True if the player has reached the kill target, false otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}
