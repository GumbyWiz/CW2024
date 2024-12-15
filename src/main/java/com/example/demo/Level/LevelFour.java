package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Display.MiniMenu;
import com.example.demo.Actor.Planes.Boss;
import com.example.demo.Actor.Planes.EnemyPlane;

/**
 * Represents the fourth level of the game, combining standard enemies with a boss fight.
 * Manages the player's progression, enemy spawns, and the boss's health display.
 */
public class LevelFour extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/Night.jpg";
    private static final int TOTAL_ENEMIES = 5;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    private final Boss boss; // The boss enemy for Level Four

    /**
     * Constructs the fourth level with the specified screen dimensions.
     *
     * @param screenHeight The height of the screen.
     * @param screenWidth  The width of the screen.
     */
    public LevelFour(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

        // Initialize the boss
        boss = new Boss();

        // Initialize the mini menu
        MiniMenu miniMenu = new MiniMenu(
                getRoot(),
                this::resumeGame,  // Action for "Continue"
                this::returnToMainMenu  // Action for "Return to Main Menu"
        );
        setMiniMenu(miniMenu);
    }

    /**
     * Adds the player's unit to the scene.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Spawns standard enemies and ensures the boss is always present in the scene.
     */
    @Override
    protected void spawnEnemyUnits() {
        // Spawn standard enemy planes
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
        }

        // Ensure the boss is always present
        if (!enemyUnits.contains(boss)) {
            addEnemyUnit(boss);
            getRoot().getChildren().add(boss.getShieldImage());
        }
    }

    /**
     * Checks if the game is over by evaluating the player's health or the boss's destruction.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            winGame();
        }
    }

    /**
     * Creates and returns a LevelView specific to Level Four, including the heart display and boss health bar.
     *
     * @return The LevelView instance for Level Four.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
    }
}
