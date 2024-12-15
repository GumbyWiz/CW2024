## Github Repository

**Github Repo link:** https://github.com/GumbyWiz/CW2024.git

## Compilation Instructions

**Prerequisites:**

1. Verify that java and maven are installed into your system.
   You can verify it by copying the commands below into your terminal.
   ```bash
   java -version
   javac -version
   mvn -v
   ```
   Install Java JDK 19+ from [Oracle's JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
   Install maven from [Apache Maven](https://maven.apache.org/download.cgi).
   Install JavaFx SDK 19+ from [JavaFx](https://openjfx.io/).
   maven is for the dependancies and javafx is for the UI.
2. Clone the repository.
3. Ensure an IDE like IntelliJ IDEA or Eclipse is configured to run the project.
4. Build the project.
5. Find the main.java class.
6. Make sure the `Run` is currently on `Current File`, click the green play button and the game will open.

## Implemented and Working Properly
The following features have been successfully implemented and are functioning correctly:

**Start game**
- Main Menu- Displays `Play`, `Tutorial`, `Exit`.
- Play- Directs the player to the first level.
- Tutorial- Displays the tutorial menu which teaches the player how the game works.
- Exit- Ends the programme upon being clicked.
- FullScreen- The game will automatically be set to FullScreen upon launch.

**In Game**
- `handleProjectileCollisions`- Allowed the user and enemy plane projectiles to collide and cancel each other out.
- Enemy flying past the border- Adjusted the way the game increments kills so that when planes fly past the user they don't increment the kill counter.
- Added Level three and Level Four.
- Pausing- Allows the user to pause the game.
- Mini Menu- A mini menu will appear when the game is paused, with a button to continue the current game and unpause, while another button is to return to the main menu.
- Invincibility- After any actor(user, enemy, boss) takes damage, they will gain a couple frames(1s) of invincibility ensuring that their health will not decrease until that phase is over.
- FlashingEffect- The model of the actors will flash from opaque to semi-transparent during the invincibility frames.

**Visuals**
- Main Menu buttons.
- Main Menu.
- Mini Menu buttons.
- Mini Menu.
- Tutorial buttons.
- Tutorial image.
- Different background for all levels.
- Planes.
- Projectiles.
- Win/Lose screen.

## Features not yet Implemented

**New enemies**
- Initially the plan for Levels three and four would be more extravagant, level three would be a hail of enemies constantly overwhelming the player and the enemies would be a different enemy all together, this would allow level three to be more difficult due to their higher hp and faster movement speed.
Level four would have incorporated a new boss entirely while also spawning in some of the enemies from level three itself, the boss would have different phases and do more damage to the player than any other enemy.

**Kill Count to advance and Health Bar**
- There was going to be a counter for the player to know how many more kills were needed before they could advance to the next level, and the boss health bar was going to be implemented during Levels two and four as both levels have bosses, specifically for level four there would be multiple sections for the bosses health since it would have phases.

**Item System**
- Items would occasionally float around a level and if you collided with one you would gain a powerup or a boost to your health, this makes the gameplay more engaging which would have offered more incentive to go forwards as the player.

The reason for not implementing these changes were due to time constraints, I was too busy focusing on other parts of the game that I forgot to test how some of these features would work in the game and how much time it would take for me to actually implement these features.

## New Java Classes


**Level One (LevelOne.java)**

Introduces the player with 5 health points.
Spawns enemies randomly with a defined probability (20%).
Transitions to Level Two after 2 enemy kills.

**Level Two (LevelTwo.java)**

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
