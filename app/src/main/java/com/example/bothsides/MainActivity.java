package com.example.bothsides;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
	//values common for both rhythm tracks
	public static final String EXTRA_TEMPO = "com.example.bothsides.TEMPO";
	public static final String EXTRA_METER = "com.example.bothsides.METER";
	public static final String EXTRA_MEASURES = "com.example.bothsides.MEASURES";
	public static final String EXTRA_METRONOME_ENABLED = "com.example.bothsides.METRONOME_ENABLED";
	//values for individual rhythm tracks
	public static final String EXTRA_RHYTHM_1 = "com.example.bothsides.RHYTHM_1";
	public static final String EXTRA_RHYTHM_2 = "com.example.bothsides.RHYTHM_2";
	public static final String EXTRA_PATTERN_LENGTH_1 = "com.example.bothsides.PATTERN_LENGTH_1";
	public static final String EXTRA_PATTERN_LENGTH_2 = "com.example.bothsides.PATTERN_LENGTH_2";
	public static final String EXTRA_RESULT_1 = "com.example.bothsides.RESULTS_1";
	public static final String EXTRA_RESULT_2 = "com.example.bothsides.RESULTS_2";
	public static final String EXTRA_HAS_RESULT_2 = "com.example.bothsides.HAS_RESULTS_2";

	private static class RhythmPattern {
		public String name;
		public int image;
		public double patternLength;
		public double[] pattern;

		RhythmPattern(String name, int image, double patternLength, double[] pattern) {
			this.name = name;
			this.image = image;
			this.patternLength = patternLength;
			this.pattern = pattern;
		}
	}
	private final ArrayList<RhythmPattern> rhythmPatternList = new ArrayList<>();
	private RhythmPattern chosenRhythmPattern1;
	private RhythmPattern chosenRhythmPattern2;
	private Switch metronomeSwitch;
	NumberPicker measuresPicker;
	NumberPicker tempoPicker;
	NumberPicker meterPicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeRhythmPatternList();
		ImageArrayAdapter adapter = initializeImageArrayAdapter();

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setDropDownWidth(metrics.widthPixels);
		spinner1.setAdapter(adapter);
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				chosenRhythmPattern1 = rhythmPatternList.get(position);
				Log.d("Spinner1", chosenRhythmPattern1.name);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner2.setDropDownWidth(metrics.widthPixels);
		spinner2.setAdapter(adapter);
		spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				chosenRhythmPattern2 = rhythmPatternList.get(position);
				Log.d("Spinner2", chosenRhythmPattern2.name);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		metronomeSwitch = (Switch) findViewById(R.id.metronomeSwitch);
		measuresPicker = (NumberPicker) findViewById(R.id.measuresEditNumber);
		tempoPicker = (NumberPicker) findViewById(R.id.tempoEditNumber);
		meterPicker = (NumberPicker) findViewById(R.id.meterEditTumber);

		measuresPicker.setMinValue(1);
		measuresPicker.setMaxValue(60);
		measuresPicker.setValue(4);
		measuresPicker.setWrapSelectorWheel(false);
		tempoPicker.setMinValue(30);
		tempoPicker.setMaxValue(120);
		tempoPicker.setValue(80);
		tempoPicker.setWrapSelectorWheel(false);
		meterPicker.setMinValue(2);
		meterPicker.setMaxValue(21);
		meterPicker.setValue(4);
		meterPicker.setWrapSelectorWheel(false);
	}

	public void startSingleLevel(View view) {
		Intent intent = new Intent(this, SingleLevel.class);
		intent.putExtra(EXTRA_TEMPO, tempoPicker.getValue());
		intent.putExtra(EXTRA_METER, meterPicker.getValue());
		intent.putExtra(EXTRA_MEASURES, measuresPicker.getValue());
		intent.putExtra(EXTRA_METRONOME_ENABLED, metronomeSwitch.isChecked());
		intent.putExtra(EXTRA_RHYTHM_1, chosenRhythmPattern1.pattern);
		intent.putExtra(EXTRA_PATTERN_LENGTH_1, chosenRhythmPattern1.patternLength);
		startActivity(intent);
	}

	public void startDoubleLevel(View view) {
		Intent intent = new Intent(this, DoubleLevel.class);
		intent.putExtra(EXTRA_TEMPO, tempoPicker.getValue());
		intent.putExtra(EXTRA_METER, meterPicker.getValue());
		intent.putExtra(EXTRA_MEASURES, measuresPicker.getValue());
		intent.putExtra(EXTRA_METRONOME_ENABLED, metronomeSwitch.isChecked());
		intent.putExtra(EXTRA_RHYTHM_1, chosenRhythmPattern1.pattern);
		intent.putExtra(EXTRA_RHYTHM_2, chosenRhythmPattern2.pattern);
		intent.putExtra(EXTRA_PATTERN_LENGTH_1, chosenRhythmPattern1.patternLength);
		intent.putExtra(EXTRA_PATTERN_LENGTH_2, chosenRhythmPattern2.patternLength);

		startActivity(intent);
	}

	private void initializeRhythmPatternList() {
		rhythmPatternList.add(new RhythmPattern("Half notes", R.drawable.papryka, 2.0, new double[]{0.0}));
		rhythmPatternList.add(new RhythmPattern("Quarter notes", R.drawable.papryka, 1.0, new double[]{0.0}));
		rhythmPatternList.add(new RhythmPattern("Eight notes", R.drawable.papryka, 0.5, new double[]{0.0}));
		rhythmPatternList.add(new RhythmPattern("Eight note triplets", R.drawable.papryka, 1.0/3, new double[]{0.0}));
		rhythmPatternList.add(new RhythmPattern("Quarter note quintuplets", R.drawable.papryka, 2.0/5, new double[]{0.0}));
		rhythmPatternList.add(new RhythmPattern("Triplet swing", R.drawable.papryka, 2.0, new double[]{0.0, 1, 5.0/3}));
		rhythmPatternList.add(new RhythmPattern("Quintuplet swing", R.drawable.papryka, 2.0, new double[]{0.0, 1, 8.0/5}));
		rhythmPatternList.add(new RhythmPattern("Septuplet swing", R.drawable.papryka, 2.0, new double[]{0.0, 1, 11.0/7}));
	}

	private ImageArrayAdapter initializeImageArrayAdapter() {
		String[] listItems = new String[rhythmPatternList.size()];
		int[] listImages = new int[rhythmPatternList.size()];
		for (int i = 0; i < rhythmPatternList.size(); i++) {
			listItems[i] = rhythmPatternList.get(i).name;
			listImages[i] = rhythmPatternList.get(i).image;
		}
		return new ImageArrayAdapter(this, listItems, listImages);
	}
}
