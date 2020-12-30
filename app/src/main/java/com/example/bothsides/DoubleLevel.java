package com.example.bothsides;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class DoubleLevel extends AppCompatActivity implements Level{
	private GameManager gm1;
	private GameManager gm2;
	private double gm1_result;
	private double gm2_result;
	private boolean gm1_finished = false;
	private boolean gm2_finished = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_double_level);

		Intent intent = getIntent();
		int tempo = intent.getIntExtra(MainActivity.EXTRA_TEMPO, 120);
		int metre = intent.getIntExtra(MainActivity.EXTRA_METER, 4);
		int measures = intent.getIntExtra(MainActivity.EXTRA_MEASURES, 4);
		boolean metronome = intent.getBooleanExtra(MainActivity.EXTRA_METRONOME_ENABLED, true);
		double[] rhythm1 = intent.getDoubleArrayExtra(MainActivity.EXTRA_RHYTHM_1);
		double[] rhythm2 = intent.getDoubleArrayExtra(MainActivity.EXTRA_RHYTHM_2);
		double patternLength1 = intent.getDoubleExtra(MainActivity.EXTRA_PATTERN_LENGTH_1, 4.0);
		double patternLength2 = intent.getDoubleExtra(MainActivity.EXTRA_PATTERN_LENGTH_2, 4.0);

		RelativeLayout imgHolder1 = (RelativeLayout) findViewById(R.id.img_view_double_1);
		RelativeLayout imgHolder2 = (RelativeLayout) findViewById(R.id.img_view_double_2);

		gm1 = new GameManager(this, imgHolder1, tempo, metre, measures, rhythm1, patternLength1, metronome);
		gm2 = new GameManager(this, imgHolder2, tempo, metre, measures, rhythm2, patternLength2);
		gm1.start();
		gm2.start();
	}

	public void userInput1(View view) {
		gm1.processUserInput();
	}

	public void userInput2(View view) {
		gm2.processUserInput();
	}

	@Override
	public void endGame(GameManager gameManager, double result) {
		if (gameManager == gm1) {
			gm1_result = result;
			gm1_finished = true;
			Log.d("DoubleLevel", "gm1 finished");
		} else {
			gm2_result = result;
			gm2_finished = true;
			Log.d("DoubleLevel", "gm2 finished");

		}
		if (gm1_finished && gm2_finished) {
			switchToEndGameActivity();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		gm1.cancelGame();
		gm2.cancelGame();
	}

	public void switchToEndGameActivity () {
		Intent intent = new Intent(this, EndGameActivity.class);
		intent.putExtra(MainActivity.EXTRA_RESULT_1, gm1_result);
		intent.putExtra(MainActivity.EXTRA_RESULT_2, gm2_result);
		intent.putExtra(MainActivity.EXTRA_HAS_RESULT_2, true);
		startActivity(intent);
	}
}