package com.example.bothsides;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SingleLevel extends AppCompatActivity implements Level{
	private GameManager gm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_level);

		Intent intent = getIntent();
		double tempo = intent.getDoubleExtra(MainActivity.EXTRA_TEMPO, 120.0);
		int metre = intent.getIntExtra(MainActivity.EXTRA_METRE, 4);
		int measures = intent.getIntExtra(MainActivity.EXTRA_MEASURES, 10);
		double[] rhythm = intent.getDoubleArrayExtra(MainActivity.EXTRA_RHYTHM_1);
		double patternLength = intent.getDoubleExtra(MainActivity.EXTRA_PATTERN_LENGTH_1, 4.0);

		RelativeLayout imgHolder = (RelativeLayout) findViewById(R.id.img_view_single);

		gm = new GameManager(this, imgHolder, tempo, metre, measures, rhythm, patternLength, true);
		gm.start();
	}

	public void userInput(View view) {
		gm.processUserInput();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		gm.cancelGame();
	}

	@Override
	public void endGame(GameManager gameManager, double result) {
		Log.d("SingleLevel", "gm finished");
		Intent intent = new Intent(this, EndGameActivity.class);
		intent.putExtra(MainActivity.EXTRA_RESULT_1, result);
		startActivity(intent);

	}
}
