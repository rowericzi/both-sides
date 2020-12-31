package com.example.bothsides;

/**
 * This interface provides a callback for the {@link com.example.bothsides.GameManager} when the game ends
 * @see com.example.bothsides.GameManager
 * @author Ryszard Jezierski
 */
public interface Level {
	/**
	 * Called by GameManager when game ends
	 * @param gameManager GameManager instance
	 * @param result Final score
	 */
	void endGame(GameManager gameManager, double result);
}
