package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Display.MiniMenu;
import com.example.demo.Display.ScreenManager;
import com.example.demo.Actor.Planes.FighterPlane;
import com.example.demo.Actor.Planes.UserPlane;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Represents a level in the game, acting as the parent class for specific level implementations.
 * <p>
 * The {@code LevelParent} class provides common functionality for managing the game environment, including the background,
 * user plane, projectiles, and enemies. It also handles the game's timeline and key press events.
 * Derived classes should implement level-specific behaviors such as spawning enemies, checking for game over conditions,
 * and managing the level view.
 */
public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	public final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;
	private com.example.demo.Display.MiniMenu MiniMenu; // Instance of the MiniMenu

	private final List<ActiveActorDestructible> friendlyUnits;
	protected final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

	private int currentNumberOfEnemies;
	private final LevelView levelView;
	private static final Logger LOGGER = Logger.getLogger(LevelParent.class.getName());
	private boolean isGameRunning;

	/**
	 * Constructs a new {@code LevelParent} instance.
	 * <p>
	 * Initializes the game level, including setting up the screen size, user plane, projectiles, and enemies. It also
	 * configures the background image based on the provided file path, sets up the game loop timeline, initializes the
	 * friendly and enemy units, and creates the mini menu. Additionally, it sets up the key press event handler for user
	 * controls and starts the game in a running state.
	 * <p>
	 * If the background image resource is not found, an {@link IllegalArgumentException} is thrown.
	 *
	 * @param backgroundImageName The file path to the background image for the level.
	 * @param screenHeight The height of the screen.
	 * @param screenWidth The width of the screen.
	 * @param playerInitialHealth The initial health of the player.
	 * @throws IllegalArgumentException If the background image resource is not found.
	 */

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		var resource = getClass().getResource(backgroundImageName); // Safely fetch the resource
		if (resource != null) {
			this.background = new ImageView(new Image(resource.toExternalForm())); // Set the image if resource is found
		} else {
			throw new IllegalArgumentException("Resource not found: " + backgroundImageName); // Throw exception if resource is not found
		}
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);

		// Initialize the mini menu
		MiniMenu = new MiniMenu(
				root,
				this::resumeGame,       // Pass resumeGame() as the "Continue" action
				this::returnToMainMenu  // Pass returnToMainMenu() as the "Return to Main Menu" action
		);
		scene.setOnKeyPressed(this::handleKeyPress);
		this.isGameRunning = true;
	}
	/**
	 * Initializes the friendly units (e.g., the player or other allies) for the level.
	 * <p>
	 * This method should be implemented by subclasses to add and initialize the friendly units that are part of the level.
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Checks whether the game is over based on the current state.
	 * <p>
	 * This method should be implemented by subclasses to define the logic for determining if the game is over,
	 * such as checking if the player has been destroyed or other win/lose conditions.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Spawns enemy units for the level.
	 * <p>
	 * This method should be implemented by subclasses to spawn and initialize enemy units that will be active in the level.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Instantiates a level view specific to the current level.
	 * <p>
	 * This method should be overridden by subclasses to provide a custom level view that represents the visual
	 * components of the level (e.g., health, score, etc.).
	 *
	 * @return A {@link LevelView} instance representing the level's visual components.
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Gets the height of the primary screen.
	 * <p>
	 * This method retrieves the height of the primary monitor's screen. It can be useful for adjusting the size of
	 * elements in the game to fit the screen's dimensions.
	 *
	 * @return The height of the primary screen in pixels.
	 */
	public double getScreenHeight() {
		// Get the bounds of the primary screen
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();

		// Return the height of the primary screen
		return primaryScreenBounds.getHeight();
	}


	/**
	 * Initializes the scene for the current level by setting up the background, friendly units, and other visual components.
	 * <p>
	 * This method prepares the scene for the current level, initializing necessary game elements such as the background
	 * and the display for friendly units (e.g., player health, score). It returns the scene to be displayed.
	 *
	 * @return The {@link Scene} object representing the level's scene, ready to be displayed.
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	/**
	 * Starts the game by requesting focus for the background and starting the game loop.
	 * <p>
	 * This method prepares the game to run by giving focus to the background and starting the timeline, which controls
	 * the game's main loop. It also sets the game running state to true.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
		isGameRunning = true;
	}

	/**
	 * Transitions to the next level by loading the specified level class and setting its scene.
	 * <p>
	 * This method dynamically loads the next level using reflection, stops the current level's game loop,
	 * initializes the next level, and sets its scene. It also ensures that the new level is displayed in full-screen mode.
	 *
	 * @param levelName The fully qualified name of the class representing the next level to load.
	 */
	public void goToNextLevel(String levelName) {
		timeline.stop(); // Stop the current level's timeline (game loop)

		Stage stage = (Stage) getRoot().getScene().getWindow();

		try {
			// Dynamically load the next level using reflection
			Class<?> levelClass = Class.forName(levelName);
			LevelParent nextLevel = (LevelParent) levelClass
					.getConstructor(double.class, double.class)
					.newInstance(getScreenHeight(), getScreenWidth());

			// Initialize and set the next scene
			Scene nextScene = nextLevel.initializeScene();
			stage.setScene(nextScene);  // Set the new level's scene
			stage.setFullScreen(true);  // Set fullscreen after setting the scene

			// Start the game loop for the new level
			nextLevel.startGame();

		}  catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to load level: " + levelName, e);
		}
	}

	/**
	 * Updates the game scene by spawning enemies, updating actor positions, handling collisions,
	 * and performing various game state updates.
	 * <p>
	 * This method is called during each game loop cycle. It handles spawning new enemy units, updating positions of all
	 * actors, generating enemy fire, checking for collisions, and updating the game state, such as the kill count and level view.
	 * If the game is paused, no updates are made.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		handleProjectileCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}


	/**
	 * Initializes the timeline for the game loop.
	 * <p>
	 * This method sets up the game loop by creating a {@link KeyFrame} that runs repeatedly at intervals defined
	 * by the {@link #MILLISECOND_DELAY}. The game loop triggers the {@link #updateScene()} method on each cycle,
	 * ensuring the game state is updated regularly.
	 * <p>
	 * The timeline will run indefinitely, creating a continuous loop for updating the game scene, handling user input,
	 * enemy actions, and other game mechanics at the specified interval.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Initializes the background image for the game, setting up its size, focus, and key event handlers.
	 * <p>
	 * This method adjusts the background to fit the screen size, ensures the background can receive keyboard focus,
	 * and adds event handlers to handle key presses and releases, such as moving the user plane and firing projectiles.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(e -> {
			KeyCode kc = e.getCode();
			if (kc == KeyCode.UP) user.moveUp();
			if (kc == KeyCode.DOWN) user.moveDown();
			if (kc == KeyCode.SPACE) fireProjectile();
		});
		background.setOnKeyReleased(e -> {
			KeyCode kc = e.getCode();
			if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
		});
		root.getChildren().add(background);
	}

	/**
	 * Fires a projectile from the user plane if the game is currently running.
	 * <p>
	 * This method checks if the game is running, and if so, it creates a new projectile from the user plane and adds
	 * it to the scene. The new projectile is also added to the list of user projectiles for tracking and collision detection.
	 */
	private void fireProjectile() {
		if (!isGameRunning){
			return;
		}
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}


	/**
	 * Generates enemy projectiles by firing from each enemy unit.
	 * <p>
	 * This method iterates through all the enemy units and attempts to fire a projectile for each. The generated
	 * projectiles are passed to the {@link #spawnEnemyProjectile(ActiveActorDestructible)} method for handling.
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * Spawns an enemy projectile and adds it to the game.
	 * <p>
	 * If the provided projectile is not {@code null}, it is added to the root node of the scene and added to the
	 * list of enemy projectiles.
	 *
	 * @param projectile The projectile to spawn.
	 */
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * Updates all actors in the game by calling their respective update methods.
	 * <p>
	 * This method updates the position and state of all friendly units, enemy units, user projectiles, and enemy
	 * projectiles.
	 */
	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}

	/**
	 * Removes all destroyed actors from the game.
	 * <p>
	 * This method checks all friendly units, enemy units, user projectiles, and enemy projectiles to determine which
	 * ones are destroyed and removes them from the game scene and the respective lists.
	 */
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	/**
	 * Removes destroyed actors from the provided list and the game scene.
	 * <p>
	 * This method filters the provided list to find all destroyed actors, removes them from the root scene, and
	 * then removes them from the list.
	 *
	 * @param actors The list of actors to check and remove destroyed ones from.
	 */
	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)  // Filter destroyed actors
				.toList();  // Collect them into a List (using the method reference toList())
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	/**
	 * Handles collisions between friendly units and enemy units.
	 * <p>
	 * This method checks for collisions between all friendly units and enemy units. If any two actors intersect,
	 * both actors will take damage.
	 */
	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	/**
	 * Handles collisions between user projectiles and enemy units.
	 * <p>
	 * This method checks for collisions between all user projectiles and enemy units. If any two actors intersect,
	 * both actors will take damage.
	 */
	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	/**
	 * Handles collisions between enemy projectiles and friendly units.
	 * <p>
	 * This method checks for collisions between all enemy projectiles and friendly units. If any two actors intersect,
	 * both actors will take damage.
	 */
	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	/**
	 * Handles collisions between user projectiles and enemy projectiles.
	 * <p>
	 * This method checks for collisions between all user projectiles and enemy projectiles. If any two actors intersect,
	 * both actors will take damage.
	 */
	private void handleProjectileCollisions() {
		handleCollisions(userProjectiles, enemyProjectiles);
	}

	/**
	 * Handles collisions between two lists of actors.
	 * <p>
	 * This method checks all pairs of actors between the two provided lists to see if their bounding boxes intersect.
	 * If they do, both actors will take damage.
	 *
	 * @param actors1 The first list of actors to check for collisions.
	 * @param actors2 The second list of actors to check for collisions.
	 */
	private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
				}
			}
		}
	}

	/**
	 * Checks if an enemy unit has penetrated the user's defenses.
	 * <p>
	 * This method checks if any of the enemy units have crossed a threshold, meaning they have penetrated the user's
	 * defenses. If so, the user takes damage, and the enemy unit is destroyed.
	 */
	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
				user.decrementKillCount();
			}
		}
	}


	/**
	 * Updates the level view to reflect the user's current health.
	 * <p>
	 * This method removes hearts from the level view based on the user's current health, providing visual feedback
	 * of the player's remaining health.
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Updates the player's kill count based on the number of enemies that have been destroyed.
	 * <p>
	 * This method compares the current number of enemies with the remaining enemies and increments the kill count
	 * for each enemy destroyed.
	 */
	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	/**
	 * Determines if an enemy has penetrated the user's defenses.
	 * <p>
	 * This method checks if the enemy has crossed beyond the screen width, indicating that it has bypassed the user's defenses.
	 *
	 * @param enemy The enemy actor to check.
	 * @return True if the enemy has penetrated the defenses, false otherwise.
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	/**
	 * Handles the event when the user wins the game.
	 * <p>
	 * This method stops the game loop, displays the win image on the level view, and sets the game state to not running.
	 */
	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
		isGameRunning = false;
	}

	/**
	 * Handles the event when the user loses the game.
	 * <p>
	 * This method stops the game loop, displays the game over image on the level view, and sets the game state to not running.
	 */
	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
		isGameRunning = false;
	}

	/**
	 * Retrieves the user plane in the game.
	 * <p>
	 * This method returns the current instance of the user plane, allowing access to the player's plane in the game.
	 *
	 * @return The user plane.
	 */
	protected UserPlane getUser() {
		return user;
	}

	/**
	 * Retrieves the root group of the game scene.
	 * <p>
	 * This method returns the root group that contains all the elements of the game scene, allowing interaction
	 * with the scene's components.
	 *
	 * @return The root group of the scene.
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Retrieves the current number of enemies in the game.
	 * <p>
	 * This method returns the number of enemies that are still active in the game, which is based on the size of
	 * the enemy units list.
	 *
	 * @return The current number of enemies.
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Adds an enemy unit to the game.
	 * <p>
	 * This method adds the given enemy unit to both the list of enemy units and the root group of the game scene,
	 * allowing the enemy to appear and be updated in the game.
	 *
	 * @param enemy The enemy unit to add.
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * Retrieves the maximum Y position for the enemy units.
	 * <p>
	 * This method returns the maximum Y position where enemy units can move within the screen boundaries.
	 *
	 * @return The maximum Y position for enemy units.
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Retrieves the width of the game screen.
	 * <p>
	 * This method returns the width of the game screen, useful for positioning elements within the game scene.
	 *
	 * @return The width of the game screen.
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Determines if the user (player) has been destroyed.
	 * <p>
	 * This method checks if the player's plane is marked as destroyed, indicating that the game is over for the player.
	 *
	 * @return True if the user is destroyed, false otherwise.
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Updates the current number of active enemies in the game.
	 * <p>
	 * This method updates the variable tracking the number of enemies based on the size of the enemy units list.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	/**
	 * Sets the mini menu for the game.
	 * <p>
	 * This method allows setting a custom mini menu, typically used to pause or resume the game, or navigate to the main menu.
	 *
	 * @param minimenu The mini menu to set.
	 */
	public void setMiniMenu(MiniMenu minimenu) {
		this.MiniMenu = minimenu;
	}

	/**
	 * Handles key press events during the game.
	 * <p>
	 * This method listens for key presses and triggers the appropriate action based on the key pressed.
	 *
	 * @param event The key event to handle.
	 */
	private void handleKeyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.P) {
			toggleMiniMenu();
		}
	}

	/**
	 * Toggles the visibility of the mini menu.
	 * <p>
	 * This method either shows or hides the mini menu, depending on its current visibility state.
	 * It also pauses or resumes the game based on the mini menu's state.
	 */
	private void toggleMiniMenu() {
		if (MiniMenu.isVisible()) {
			resumeGame();
		} else {
			pauseGame();
		}
	}

	/**
	 * Pauses the game and displays the mini menu.
	 * <p>
	 * This method stops the game loop, halts the timeline, and displays the mini menu, effectively pausing the game.
	 */
	private void pauseGame() {
		isGameRunning = false;
		timeline.pause();
		MiniMenu.showMenu();
	}

	/**
	 * Resumes the game from a paused state.
	 * <p>
	 * This method resumes the game loop, restarts the timeline, and hides the mini menu, effectively resuming the game.
	 */
	protected void resumeGame() {
		isGameRunning = true;
		timeline.play();
		MiniMenu.hideMenu();
	}

	/**
	 * Stops the current game and returns to the main menu.
	 * <p>
	 * This method halts the game loop, stops the timeline, and transitions to the main menu screen.
	 */
	protected void returnToMainMenu() {
		// Stop the game and return to the main menu
		timeline.stop();
		Stage stage = (Stage) scene.getWindow();
		ScreenManager screenManager = new ScreenManager(stage);
		screenManager.showMainMenu();
	}
}