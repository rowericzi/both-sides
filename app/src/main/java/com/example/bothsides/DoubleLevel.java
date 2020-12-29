package com.example.bothsides;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DoubleLevel extends AppCompatActivity implements Level{
	private GameManager gm1;
	private GameManager gm2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_double_level);

		Intent intent = getIntent();
		double tempo = intent.getDoubleExtra(MainActivity.EXTRA_TEMPO, 120.0);
		int measures1 = intent.getIntExtra(MainActivity.EXTRA_MEASURES_1, 10);
		double[] rhythm1 = intent.getDoubleArrayExtra(MainActivity.EXTRA_RHYTHM_1);
		int measures2 = intent.getIntExtra(MainActivity.EXTRA_MEASURES_2, 10);
		double[] rhythm2 = intent.getDoubleArrayExtra(MainActivity.EXTRA_RHYTHM_2);

		RelativeLayout imgHolder1 = (RelativeLayout) findViewById(R.id.img_view_double_1);
		RelativeLayout imgHolder2 = (RelativeLayout) findViewById(R.id.img_view_double_2);

		gm1 = new GameManager(this, imgHolder1, tempo, measures1, rhythm1);
		gm2 = new GameManager(this, imgHolder2, tempo, measures2, rhythm2);
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
	public void endGame(ArrayList<Integer> expectedTimestamps, ArrayList<Integer> userInputTimestamps) {
		Log.d("endgame", "gra skonczona ecks dee");

		Intent intent = new Intent(this, EndGameActivity.class);
		startActivity(intent);
	}
}