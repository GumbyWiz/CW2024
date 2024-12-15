package com.example.demo.Display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.Group;

/**
 * Represents a mini in-game menu with options to continue the game or return to the main menu.
 */
public class MiniMenu {

    /** The layout containing the menu buttons. */
    private final VBox menuLayout;

    /** Image dimensions for menu buttons. */
    private static final double BUTTON_IMAGE_WIDTH = 200;
    private static final double BUTTON_IMAGE_HEIGHT = 80;

    /**
     * Constructs a {@code MiniMenu} and adds it to the root layout.
     *
     * @param root The root group to which the menu will be added.
     * @param onContinue A {@link Runnable} action to execute when "Continue" is clicked.
     * @param onReturnToMainMenu A {@link Runnable} action to execute when "Return to Main Menu" is clicked.
     * @throws IllegalArgumentException if any required resource image is not found.
     */
    public MiniMenu(Group root, Runnable onContinue, Runnable onReturnToMainMenu) {
        menuLayout = new VBox(20); // Vertical layout with spacing
        menuLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 50; -fx-alignment: center;");
        menuLayout.setVisible(false); // Hidden by default

        // Create the "Continue" button with an image
        Button continueButton = new Button();
        continueButton.setStyle("-fx-background-color: transparent;");
        ImageView continueImage = createImageView("/com/example/demo/images/Continue.png");
        continueButton.setGraphic(continueImage);
        continueButton.setOnAction(e -> onContinue.run());

        // Create the "Return to Main Menu" button with an image
        Button returnToMainMenuButton = new Button();
        returnToMainMenuButton.setStyle("-fx-background-color: transparent;");
        ImageView returnToMainMenuImage = createImageView("/com/example/demo/images/Menu.png");
        returnToMainMenuButton.setGraphic(returnToMainMenuImage);
        returnToMainMenuButton.setOnAction(e -> onReturnToMainMenu.run());

        // Add the buttons to the layout
        menuLayout.getChildren().addAll(continueButton, returnToMainMenuButton);

        // Add the mini menu to the root layout (e.g., Group)
        root.getChildren().add(menuLayout);
    }

    /**
     * Creates an {@link ImageView} from the specified resource path.
     *
     * @param resourcePath The path to the image resource.
     * @return An {@link ImageView} object.
     * @throws IllegalArgumentException if the resource is not found.
     */
    private ImageView createImageView(String resourcePath) {
        var resource = getClass().getResource(resourcePath);
        if (resource == null) {
            throw new IllegalArgumentException("Image resource not found: " + resourcePath);
        }

        ImageView imageView = new ImageView(new Image(resource.toExternalForm()));
        imageView.setFitWidth(BUTTON_IMAGE_WIDTH);
        imageView.setFitHeight(BUTTON_IMAGE_HEIGHT);
        return imageView;
    }

    /**
     * Shows the mini menu, centering it on the screen.
     */
    public void showMenu() {
        menuLayout.setLayoutX(menuLayout.getParent().getScene().getWidth() / 2 - menuLayout.getWidth() / 2);
        menuLayout.setLayoutY(menuLayout.getParent().getScene().getHeight() / 2 - menuLayout.getHeight() / 2);

        menuLayout.setVisible(true);
        menuLayout.toFront();
        menuLayout.getParent().requestLayout(); // Force layout refresh
        System.out.println("Mini Menu is now visible!"); // Debug log
    }

    /**
     * Hides the mini menu.
     */
    public void hideMenu() {
        System.out.println("Mini Menu is now hidden!"); // Debug log
        menuLayout.setVisible(false);
    }

    /**
     * Checks if the mini menu is visible.
     *
     * @return {@code true} if the menu is visible, otherwise {@code false}.
     */
    public boolean isVisible() {
        return menuLayout.isVisible();
    }
}
