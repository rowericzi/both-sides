package com.example.bothsides;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SingleLevel extends AppCompatActivity implements Level{
	private GameManager gm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_level);

		Intent intent = getIntent();
		double tempo = intent.getDoubleExtra(MainActivity.EXTRA_TEMPO, 120.0);
		int measures = intent.getIntExtra(MainActivity.EXTRA_MEASURES_1, 10);
		double[] rhythm = intent.getDoubleArrayExtra(MainActivity.EXTRA_RHYTHM_1);

		RelativeLayout imgHolder = (RelativeLayout) findViewById(R.id.img_view_single);

		gm = new GameManager(this, imgHolder, tempo, measures, rhythm, true);
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
