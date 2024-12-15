package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Display.MiniMenu;
import com.example.demo.Actor.Planes.EnemyPlane;

/**
 * Represents the third level of the game.
 * It introduces more enemies and requires the player to meet a kill target to progress to the next level.
 */
public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/Evening.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.Level.LevelFour";
    private static final int TOTAL_ENEMIES = 8;
    private static final int KILLS_TO_ADVANCE = 6;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * Constructs the third level with the specified screen dimensions.
     *
     * @param screenHeight The height of the screen.
     * @param screenWidth  The width of the screen.
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

        // Initialize the mini menu
        MiniMenu miniMenu = new MiniMenu(
                getRoot(),
                this::resumeGame,  // Action for "Continue"
                this::returnToMainMenu  // Action for "Return to Main Menu"
        );
        setMiniMenu(miniMenu);
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
     * Creates and returns a LevelView specific to LevelThree, including the heart display.
     *
     * @return The LevelView instance for LevelThree.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    /**
     * Determines if the player has met the kill target required to progress to the next level.
     *
     * @return True if the player has reached the required kills, false otherwise.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}
