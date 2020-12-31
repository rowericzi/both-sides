package com.example.bothsides;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * End game screen
 * @author Ryszard Jezierski
 */
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
public class EndGameActivity extends AppCompatActivity {
	/**
	 * Displays results on the screen
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_game);

		TextView textView = findViewById(R.id.textView);
		String resultStr = "";
		Intent intent = getIntent();
		double result1 = intent.getDoubleExtra(MainActivity.EXTRA_RESULT_1, 1.0);


		if (intent.getBooleanExtra(MainActivity.EXTRA_HAS_RESULT_2, false)) {
			resultStr += getString(R.string.left_hand_score);
			resultStr += String.valueOf(result1);
			double result2 = intent.getDoubleExtra(MainActivity.EXTRA_RESULT_2, 1.0);
			resultStr += "\n" + getString(R.string.right_hand_score);
			resultStr += String.valueOf(result2);
		} else {
			resultStr += getString(R.string.score);
			resultStr += String.valueOf(result1);
		}
		textView.setText(resultStr);
	}

	/**
	 * Returns to {@link MainActivity} to prevent going back to (empty, finished) {@link Level}
	 */
	@Override
	protected void onDestroy() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		super.onDestroy();
	}

	/**
	 * Returns to {@link MainActivity}
	 * @param view this is an onClick function
	 */
	public void returnToMainMenu(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}