package com.example.bothsides;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
	public static final String EXTRA_TEMPO = "com.example.bothsides.TEMPO";
	public static final String EXTRA_MEASURES = "com.example.bothsides.MEASURES";
	public static final String EXTRA_RHYTHM = "com.example.bothsides.RHYTHM";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void startSingleLevel(View view) {
		Intent intent = new Intent(this, SingleLevel.class);
		intent.putExtra(EXTRA_TEMPO, 90.0);
		intent.putExtra(EXTRA_MEASURES, 10);
		double[] rhythm = new double[]{0.0, 0.5, 1.0, 2.0, 3.0, 3.25, 3.5, 3.75};
		intent.putExtra(EXTRA_RHYTHM, rhythm);
		startActivity(intent);
	}

	public void startDoubleLevel(View view) {
		Intent intent = new Intent(this, DoubleLevel.class);
		startActivity(intent);
	}
}
