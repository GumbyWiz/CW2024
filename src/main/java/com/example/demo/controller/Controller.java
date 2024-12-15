package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.Level.LevelParent;

/**
 * The Controller class manages the flow of the game, specifically the navigation
 * between levels. It listens for updates from levels (using the Observer pattern)
 * and handles the dynamic creation and transition to different levels.
 */
public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.Level.LevelOne"; // The class name for level one
	private final Stage stage; // The stage to display the scenes on

	/**
	 * Constructs a new Controller with the given Stage to control the game's scenes.
	 *
	 * @param stage the Stage object used to display the game scenes.
	 */
	public Controller(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Launches the game by displaying the initial level (level one).
	 * This method calls the goToLevel method to load the first level.
	 *
	 * @throws ClassNotFoundException if the class for the level cannot be found.
	 * @throws NoSuchMethodException if the constructor for the level class is not found.
	 * @throws SecurityException if there is a security violation during reflection.
	 * @throws InstantiationException if there is an issue instantiating the level class.
	 * @throws IllegalAccessException if there is illegal access during reflection.
	 * @throws IllegalArgumentException if the constructor has incorrect arguments.
	 * @throws InvocationTargetException if the constructor throws an exception.
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.show(); // Show the game stage
		goToLevel(LEVEL_ONE_CLASS_NAME); // Load the first level
	}

	/**
	 * Navigates to the specified level class by its name, creates an instance of it,
	 * and sets it as the current scene on the stage.
	 *
	 * @param className the fully qualified name of the level class to transition to.
	 * @throws ClassNotFoundException if the class for the level cannot be found.
	 * @throws NoSuchMethodException if the constructor for the level class is not found.
	 * @throws SecurityException if there is a security violation during reflection.
	 * @throws InstantiationException if there is an issue instantiating the level class.
	 * @throws IllegalAccessException if there is illegal access during reflection.
	 * @throws IllegalArgumentException if the constructor has incorrect arguments.
	 * @throws InvocationTargetException if the constructor throws an exception.
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Load the class dynamically via reflection
		Class<?> myClass = Class.forName(className);

		// Get the constructor of the level class that accepts width and height
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);

		// Create a new instance of the level class
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());

		// Add this controller as an observer to the level (so we can listen for level changes)
		myLevel.addObserver(this);

		// Initialize the scene and set it on the stage
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);

		// Start the level's game logic
		myLevel.startGame();
	}

	/**
	 * Called when an update is received from the observable object (level).
	 * This method handles level transitions based on the level's update.
	 *
	 * @param arg0 the observable object (the level) sending the update.
	 * @param arg1 the object passed by the level, which contains the next level class name.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			// Go to the next level specified by the update (next level class name)
			goToLevel((String) arg1);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// Display an error if an exception occurs during the level transition
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString()); // Show the exception class name
			alert.show(); // Display the alert to the user
		}
	}

}
