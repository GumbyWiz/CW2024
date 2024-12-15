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

**Boss**

**Changes made**


## Modified Java Classes

**Main**

**Changes made**
- Most of the screen functions were moved to `ScreenManager`, therefore the main class calls the ScreenManager to handle the main menu when the appliation is running. This was because incorporating a fullscreen on the application would be easier if that implementation as on a seperate class completely.

**Controller**

**Changes made**


**Boss**

**Changes made**
- Added invincibility logic so that the boss can't take anymore damage until their invincibility runs out.
- Added `startFlashingEffect` as a way to indicate to the player that the boss is currently invincible until the flashing effect runs out.

**EnemyPlane**

**Changes Made**
- Added invincibility logic so that the enemies can't take anymore damage until their invincibility runs out.
- Added `startFlashingEffect` as a way to indicate to the player that the enemy planes are currently invincible until the flashing effect runs out.

**UserPlane**

**Changes Made**
- Added `decrementKillCount` to make sure that when an enemy plane passes through the border the kill count will not increase, this will not affect how kills are counted normally either.
- Added invincibility logic so that the player can't take anymore damage until their invincibility runs out.
- Added `startFlashingEffect` as a way to indicate to the player that they are currently invincible until the flashing effect runs out.

**UserProjectile**

**Changes Made**
- Adjusted IMAGE_HEIGHT from 125 to 7. This change optimizes the visual scaling of projectiles, making them smaller for better representation on the game screen.

**EnemyProjectile**

**Changes Made**
- Adjusted IMAGE_HEIGHT from 50 to 25. This change optimizes the visual scaling of projectiles, making them smaller for better representation on the game screen.

**ActiveActor**

**Changes Made**
- The setImage method was updated to use getClass().getResource(resourcePath).toExternalForm(). This ensures that the image resource is loaded properly from the classpath.

**ActiveActorDestructible**

**Changes Made**
- Added a new `isInvincible` flag as a final field. This is to manage the actor’s state more robustly, particularly if there’s a need to handle invincibility logic. The flag improves flexibility for extending game mechanics.


**GameOverImage**

**Changes made**
- Ensures the Game Over image resource is loaded safely and throws an `IllegalArgumentException` if unavailable.

**WinImage**

**Changes made**
- Ensures the Win image resource is loaded safely and throws an `IllegalArgumentException` if unavailable.


**ShieldImage**

**Changes made**
- Ensures the Shield image resource is loaded safely and throws an `IllegalArgumentException` if unavailable.

**Level One (LevelOne.java)**

**Changes Made**
- `PLAYER_INTIIAL_HEALTH` fixes the playrs health to 5 at the start of every level
- Spawns enemies randomly with a defined probability.
- Transitions to Level Two after the `KILLS_TO_ADVANCE` has been achieved.
- Initializes mini menu.

**Level Two (LevelTwo.java)**

**Changes Made**
- `boss=new Boss();` initializes the boss, without it the boss wont spawn in the level.
- `spawnEnemyUnits` in this class only spawns the boss and no other enemies.
- Implements a boss health shield.
- Transitions to Level Three upon defeating the boss.
- Initializes mini menu.


**LevelParent** (com/example/demo/Level/LevelParent.java)

**Changes Made**
- Added mini menu support for pausing and resuming the game.
- Enhanced collision handling for enemy penetration.
- Added `isGameRunning` to handle when the player can fire, since without it the player would be able to fire a projectile during the win/lose screen and when the game was paused.
- Modified `goToNextLevel` to prevent a memory leak using `timeline.stop` otherwise the game would attempt to load the next level without stopping the previous level. This is to provide a consistent parent class for managing shared game logic across levels.

**LevelViewLevelTwo**

**Changes Made**
- Refactored the addImagesToRoot method to improve clarity and responsibility by restricting it to adding only the shieldImage.
- Removed unnecessary use of addAll in the original addImagesToRoot method since only the shieldImage is being added.

**Unexpected Problems**

- Background images and boss shield images caused errors when loaded.
Solution: Used getResource() to safely fetch image files.
- Enemy Spawn Lag, excessive enemy units caused performance issues during spawning.
Solution: Reduced enemy count and adjusted spawn probability.
- Could not find a way to change observable.




