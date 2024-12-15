package com.example.demo.Display;

import com.example.demo.controller.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code ScreenManager} class is responsible for managing the application's screens
 * and handling transitions between different scenes.
 */
public class ScreenManager {
    /** The primary stage of the application. */
    private final Stage stage;
    private static final Logger LOGGER = Logger.getLogger(ScreenManager.class.getName());

    /**
     * Constructs a new {@code ScreenManager} with the specified primary stage.
     *
     * @param stage The primary {@link Stage} used for displaying scenes.
     */
    public ScreenManager(Stage stage) {
        this.stage = stage;
        configureStage();
    }

    /**
     * Configures the initial settings for the stage.
     * <p>
     * Sets the stage to be non-resizable and clears the fullscreen exit hint.
     */
    private void configureStage() {
        stage.setResizable(false);
        stage.setTitle(""); // Default title
        stage.setFullScreenExitHint(""); // Disable fullscreen exit hint
    }

    /**
     * Displays the main menu by loading the {@code MainMenu.fxml} file.
     * <p>
     * The {@link MainMenuController} is initialized with a reference to this {@code ScreenManager}.
     *
     * @throws RuntimeException if the FXML file cannot be loaded or the controller fails to initialize.
     */
    public void showMainMenu() {
        try {
            // Load MainMenu.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            Parent root = loader.load();

            // Get the controller and initialize it
            MainMenuController controller = loader.getController();
            controller.initialize(this);  // Pass ScreenManager to the controller

            // Set the scene for the main menu
            Scene menuScene = new Scene(root, 1600, 900);  // Set the appropriate size for the menu
            setScene(menuScene);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load MainMenu.fxml or initialize the main menu.", e);
            throw new RuntimeException("Failed to load MainMenu.fxml or initialize the main menu.", e);
        }
    }

    /**
     * Sets the specified scene to the stage and ensures fullscreen mode is applied.
     *
     * @param scene The {@link Scene} to display.
     */
    public void setScene(Scene scene) {
        stage.setScene(scene);
        stage.setFullScreen(true); // Enable fullscreen
        stage.show(); // Show the updated stage
    }

    /**
     * Retrieves the primary stage used by the application.
     *
     * @return The primary {@link Stage}.
     */
    public Stage getStage() {
        return stage;
    }
}
