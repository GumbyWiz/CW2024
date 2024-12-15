package com.example.demo.Display;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;

/**
 * The {@code Tutorial} class represents a tutorial screen in the application.
 * It displays a background image along with a button to return to the main menu.
 */
public class Tutorial {

    /** The height of the screen for the tutorial. */
    private final double screenHeight;

    /** The width of the screen for the tutorial. */
    private final double screenWidth;

    /** The root layout of the tutorial screen. */
    private final StackPane root;

    /**
     * Constructs a {@code Tutorial} object with the specified screen dimensions.
     *
     * @param screenHeight The height of the tutorial screen.
     * @param screenWidth  The width of the tutorial screen.
     */
    public Tutorial(double screenHeight, double screenWidth) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.root = new StackPane(); // Initialize the root layout
    }

    /**
     * Initializes and returns the tutorial scene.
     * <p>
     * The scene includes a background image and a button to return to the main menu.
     *
     * @param onReturnToMainMenu A {@link Runnable} action to be executed when the return button is clicked.
     * @return A {@link Scene} object representing the tutorial screen.
     */
    public Scene initializeScene(Runnable onReturnToMainMenu) {
        // Create and configure the background image
        ImageView background = createBackgroundImage();

        // Create and configure the return button
        Pane buttonPane = createReturnButtonPane(onReturnToMainMenu);

        // Add components to the root layout
        root.getChildren().addAll(background, buttonPane);
        buttonPane.toFront(); // Ensure the button appears in front of the background

        // Return the tutorial scene
        return new Scene(root, screenWidth, screenHeight);
    }

    /**
     * Creates the background image for the tutorial screen.
     *
     * @return An {@link ImageView} configured with the background image.
     * @throws IllegalArgumentException if the background image resource is not found.
     */
    private ImageView createBackgroundImage() {
        var resource = getClass().getResource("/com/example/demo/images/TutorialContent.png");
        if (resource == null) {
            throw new IllegalArgumentException("Background image resource not found: /com/example/demo/images/TutorialContent.png");
        }

        ImageView background = new ImageView(new Image(resource.toExternalForm()));
        background.setFitWidth(screenWidth);
        background.setFitHeight(screenHeight);
        return background;
    }

    /**
     * Creates the return button and positions it in the top-left corner of the screen.
     *
     * @param onReturnToMainMenu A {@link Runnable} action to be executed when the return button is clicked.
     * @return A {@link Pane} containing the return button for absolute positioning.
     * @throws IllegalArgumentException if the button image resource is not found.
     */
    private Pane createReturnButtonPane(Runnable onReturnToMainMenu) {
        var resource = getClass().getResource("/com/example/demo/images/TutorialBack.png");
        if (resource == null) {
            throw new IllegalArgumentException("Button image resource not found: /com/example/demo/images/TutorialBack.png");
        }

        // Initialize the return button
        Button returnButton = new Button();
        returnButton.setStyle("-fx-background-color: transparent;"); // Transparent background
        ImageView buttonImage = new ImageView(new Image(resource.toExternalForm()));
        buttonImage.setFitWidth(150); // Set button width
        buttonImage.setFitHeight(50); // Set button height
        returnButton.setGraphic(buttonImage); // Set image as button graphic

        // Set button position
        returnButton.setLayoutX(10); // 10px from the left
        returnButton.setLayoutY(10); // 10px from the top

        // Handle button click action
        returnButton.setOnAction(e -> onReturnToMainMenu.run());

        // Place button in a Pane for absolute positioning
        Pane buttonPane = new Pane();
        buttonPane.getChildren().add(returnButton);
        return buttonPane;
    }
}
