package com.example.bothsides;

import java.util.ArrayList;

public interface Level {
	void endGame(ArrayList<Integer> expectedTimestamps, ArrayList<Integer> userInputTimestamps);
}
