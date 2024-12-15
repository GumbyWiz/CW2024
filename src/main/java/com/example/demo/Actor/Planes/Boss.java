package com.example.demo.Actor.Planes;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Projectiles.BossProjectile;
import com.example.demo.Display.ShieldImage;
import javafx.animation.AnimationTimer;

import java.util.*;

/**
 * Represents a boss in the game, extending the {@link FighterPlane} class.
 * <p>
 * The {@code Boss} class defines the behavior of the boss plane, including movement, firing projectiles, taking damage,
 * and activating a shield. The boss can move vertically in a predefined pattern and has a chance to fire projectiles and activate
 * a shield during gameplay.
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1300.0;
	private static final double INITIAL_Y_POSITION = 350;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = .002;
	private static final int IMAGE_HEIGHT = 50;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 2;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = 0;
	private static final int Y_POSITION_LOWER_BOUND = 750;
	private static final int MAX_FRAMES_WITH_SHIELD = 100;

	private final List<Integer> movePattern;
	private final ShieldImage shieldImage;
	public boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	/**
	 * Constructs a new {@code Boss} instance.
	 * <p>
	 * Initializes the boss plane's position, health, and movement pattern. A shield image is also instantiated for the boss.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION);
		initializeMovePattern();
	}

	/**
	 * Updates the position of the boss plane.
	 * <p>
	 * Moves the boss vertically according to the next move in the movement pattern and updates the position of the shield image.
	 * If the boss moves outside the allowed vertical bounds, its position is reset.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		shieldImage.setLayoutY(currentPosition);
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Updates the state of the boss, including its position and shield status.
	 * <p>
	 * This method is called to update the boss's position and shield.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile from the boss plane.
	 * <p>
	 * The boss fires a projectile based on its fire rate. If the boss decides to fire, a new {@link BossProjectile} is created.
	 *
	 * @return A new {@link BossProjectile} if the boss fires, otherwise {@code null}.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Handles the boss taking damage.
	 * <p>
	 * The boss can only take damage if it is not shielded or invincible. When the boss is hit, invincibility is activated,
	 * and a flashing effect is applied.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded && !isInvincible()) { // Take damage only if not shielded or invincible
			super.takeDamage();
			activateInvincibility();
			startFlashingEffect(); // Activate invincibility after being hit
		}
	}

	/**
	 * Starts the flashing effect to indicate the boss is invincible.
	 * <p>
	 * The boss will alternate between visible and semi-transparent states to provide visual feedback for invincibility.
	 */
	private void startFlashingEffect() {
		AnimationTimer flashTimer = new AnimationTimer() {
			private boolean visible = true;
			private long lastToggleTime = 0;

			@Override
			public void handle(long now) {
				if (now - lastToggleTime >= 200_000_000L) { // Toggle every 200ms
					visible = !visible;
					setOpacity(visible ? 1.0 : 0.5); // Alternate between fully visible and semi-transparent
					lastToggleTime = now;
				}
				if (!isInvincible()) {
					setOpacity(1.0); // Reset to fully visible
					stop(); // Stop the flashing effect
				}
			}
		};
		flashTimer.start();
	}

	/**
	 * Initializes the movement pattern for the boss.
	 * <p>
	 * The boss's movement alternates between upward, downward, and stationary moves. The movement pattern is randomized.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield status of the boss.
	 * <p>
	 * The boss may activate or deactivate its shield depending on a random chance and the number of frames the shield has been active.
	 */
	public void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			shieldImage.showShield();
		} else if (shieldShouldBeActivated()) {
			activateShield();
		}
		if (shieldExhausted()) {
			deactivateShield();
		}
	}

	/**
	 * Retrieves the next vertical move for the boss.
	 * <p>
	 * The boss moves according to a predefined pattern. The movement direction is randomized after a certain number of consecutive moves.
	 *
	 * @return The next vertical movement value, which can be positive, negative, or zero.
	 */
	public int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines whether the boss should fire a projectile in the current frame.
	 * <p>
	 * The decision is based on the boss's fire rate.
	 *
	 * @return {@code true} if the boss fires a projectile, otherwise {@code false}.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Retrieves the initial position for a projectile fired by the boss.
	 * <p>
	 * The projectile is positioned based on the boss's current Y position with an additional offset.
	 *
	 * @return The Y position for the projectile.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Determines whether the boss should activate its shield in the current frame.
	 * <p>
	 * The decision is based on a random chance.
	 *
	 * @return {@code true} if the shield should be activated, otherwise {@code false}.
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Determines whether the boss's shield has been active for too long.
	 * <p>
	 * The shield is deactivated after a set number of frames.
	 *
	 * @return {@code true} if the shield should be deactivated, otherwise {@code false}.
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the boss's shield.
	 * <p>
	 * The shield becomes visible, and the boss is considered shielded.
	 */
	public void activateShield() {
		isShielded = true;
		shieldImage.showShield();
	}

	/**
	 * Deactivates the boss's shield.
	 * <p>
	 * The shield is hidden, and the boss is no longer shielded.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		shieldImage.hideShield();
	}

	/**
	 * Retrieves the shield image associated with the boss.
	 * <p>
	 * The shield image is displayed when the boss is shielded.
	 *
	 * @return The {@link ShieldImage} instance associated with the boss.
	 */
	public ShieldImage getShieldImage() {
		return shieldImage;
	}
}
