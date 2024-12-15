Compilation Instructions
To compile and run the game:

Prerequisites:

Install Java JDK 11+
Install JavaFX SDK
Ensure an IDE like IntelliJ IDEA or Eclipse is configured to include JavaFX libraries.
Project Setup:

Clone the repository:
bash
Copy code
git clone https://github.com/YourUsername/SkyBattle.git
cd SkyBattle
Set up JavaFX library paths in your IDE.
Compilation:

Use your IDE to build and compile the project.
Run:

Execute the main class to launch the game.
Example for JavaFX setup in CLI:
bash
Copy code
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -jar SkyBattle.jar
Implemented and Working Properly
The following features have been successfully implemented and are functioning correctly:

Level One (LevelOne.java)

Introduces the player with 5 health points.
Spawns enemies randomly with a defined probability (20%).
Transitions to Level Two after 2 enemy kills.
Level Two (LevelTwo.java)

Spawns a Boss enemy when no regular enemies are active.
Implements a boss health shield.
Transitions to Level Three upon defeating the boss.
Pause Menu

Added a MiniMenu that pauses the game (P key) with options to continue or return to the main menu.
Implemented but Not Working Properly
Enemy Collision Detection

While projectiles and planes collide, sometimes collisions fail to register due to hitbox inaccuracies.
Game State Persistence

The game does not save progress when quitting, requiring a restart for level transitions.
Features Not Implemented
Level Three

Code references LevelThree, but it has not been implemented.
Reason: Limited time and scope of the coursework.
User Stats Display

Displaying score and stats (e.g., total kills, health) during gameplay remains unimplemented.
New Java Classes
The following new classes were introduced:

LevelOne (com/example/demo/Level/LevelOne.java)

Purpose: Handles gameplay for the first level, including enemy spawning and win conditions.
LevelTwo (com/example/demo/Level/LevelTwo.java)

Purpose: Implements a boss-level stage with unique logic for spawning and transitioning.
Modified Java Classes
LevelParent (com/example/demo/Level/LevelParent.java)
Changes Made:
Added mini menu support for pausing and resuming the game.
Enhanced collision handling for enemy penetration.
Reason: To provide a consistent parent class for managing shared game logic across levels.
Unexpected Problems
Resource Path Issues

Background images and boss shield images caused errors when loaded.
Resolution: Used getResource() to safely fetch image files.
Enemy Spawn Lag

Excessive enemy units caused performance issues during spawning.
Resolution: Reduced enemy count and adjusted spawn probability.
