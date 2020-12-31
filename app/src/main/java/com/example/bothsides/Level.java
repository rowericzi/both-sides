/*
    Copyright 2020 Ryszard Jezierski

    This file is part of both][sides.

    both][sides is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    both][sides is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with both][sides.  If not, see <https://www.gnu.org/licenses/>.
 */
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
