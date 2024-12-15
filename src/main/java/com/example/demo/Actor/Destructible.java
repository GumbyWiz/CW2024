package com.example.demo.Actor;

/**
 * Represents an entity that can take damage and be destroyed within the game.
 * Classes implementing this interface should define specific behavior for taking damage
 * and destruction.
 */
public interface Destructible {

	/**
	 * Reduces the health or durability of the entity.
	 * Classes implementing this method should define how the entity responds
	 * when damage is inflicted.
	 */
	void takeDamage();

	/**
	 * Destroys the entity, marking it as no longer functional or present in the game.
	 * Classes implementing this method should define how the entity is removed or rendered
	 * inactive upon destruction.
	 */
	void destroy();
}
