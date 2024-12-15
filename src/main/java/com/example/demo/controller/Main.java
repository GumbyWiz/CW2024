package com.example.demo.controller;

import com.example.demo.Display.ScreenManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class is the entry point for launching the JavaFX application.
 * It initializes and manages the primary stage, displaying the main menu through the ScreenManager.
 */
public class Main extends Application {

	/**
	 * The start method is the entry point for the JavaFX application. It initializes
 	 * the primary stage and sets up the screen manager to manage the main menu.
	 *
	 * @param primaryStage the primary Stage object used to set up and display the scene
	 */
	@Override
	public void start(Stage primaryStage) {
		// Create the ScreenManager to manage the screen size and fullscreen
		ScreenManager screenManager = new ScreenManager(primaryStage);

		// Show the main menu
		screenManager.showMainMenu();  // Show the main menu
	}

	/**
	 * The main method serves as the entry point for running the JavaFX application.
	 * It launches the application by calling the launch() method from the Application class.
	 *
	 * @param args command-line arguments passed to the application (not used in this case)
	 */
	public static void main(String[] args) {
		launch(args); // Launch the application
	}
}
