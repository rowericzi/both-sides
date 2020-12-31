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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Single-track level, using one {@link GameManager} object
 * @see GameManager
 * @author Ryszard Jezierski
 */
public class SingleLevel extends AppCompatActivity implements Level{
	private GameManager gm;

	/**
	 * Gets game parameters from {@link MainActivity} via an Intent and initializes a new {@link GameManager}
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_level);

		Intent intent = getIntent();
		int tempo = intent.getIntExtra(MainActivity.EXTRA_TEMPO, 120);
		int metre = intent.getIntExtra(MainActivity.EXTRA_METER, 4);
		int measures = intent.getIntExtra(MainActivity.EXTRA_MEASURES, 4);
		boolean metronome = intent.getBooleanExtra(MainActivity.EXTRA_METRONOME_ENABLED, true);
		double[] rhythm = intent.getDoubleArrayExtra(MainActivity.EXTRA_RHYTHM_1);
		double patternLength = intent.getDoubleExtra(MainActivity.EXTRA_PATTERN_LENGTH_1, 4.0);

		RelativeLayout imgHolder = findViewById(R.id.img_view_single);

		gm = new GameManager(this, imgHolder, tempo, metre, measures, rhythm, patternLength, metronome);
		gm.start();
	}

	/**
	 * Button callback
	 * @param view this is an onClick function
	 */
	public void userInput(View view) {
		gm.processUserInput();
	}

	/**
	 * Calls {@link GameManager#cancelGame()} to cancel currently running game when activity is destroyed
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		gm.cancelGame();
	}

	/**
	 * Callback for GameManager when the game ends
	 * @param gameManager GameManager instance calling this method
	 * @param result End score
	 */
	@Override
	public void endGame(GameManager gameManager, double result) {
		Log.d("SingleLevel", "gm finished");
		Intent intent = new Intent(this, EndGameActivity.class);
		intent.putExtra(MainActivity.EXTRA_RESULT_1, result);
		startActivity(intent);

	}
}
