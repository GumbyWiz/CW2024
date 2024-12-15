package com.example.demo.controller;

import com.example.demo.Display.Tutorial;
import com.example.demo.Display.ScreenManager;
import javafx.fxml.FXML;
import com.example.demo.Level.LevelOne;

/**
 * Controller for the main menu of the game.
 */
public class MainMenuController {
    private ScreenManager screenManager;

    /**
     * Initialize the controller with the ScreenManager.
     *
     * @param screenManager the ScreenManager instance
     */
    public void initialize(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    /**
     * Start the game by loading and starting LevelOne.
     */
    @FXML
    public void startGame() {
        // Start LevelOne
        LevelOne levelOne = new LevelOne(900, 1600); // Pass screen dimensions
        screenManager.getStage().setFullScreen(true); // Enable fullscreen
        screenManager.setScene(levelOne.initializeScene()); // Set LevelOne scene
        levelOne.startGame(); // Start game loop
    }

    /**
     * Show the tutorial screen.
     */
    @FXML
    public void showTutorial() {
        Tutorial tutorial = new Tutorial(900, 1600); // Pass screen dimensions

        // Set the action to return to the main menu
        screenManager.getStage().setFullScreen(true); // Enable fullscreen
        screenManager.setScene(tutorial.initializeScene(() -> {
            // Return to the main menu when the button is clicked
            screenManager.showMainMenu();
        }));
    }

    /**
     * Exit the game by closing the application.
     */
    @FXML
    public void exitGame() {
        // Close the application
        screenManager.getStage().close();
    }
}
