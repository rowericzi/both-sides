package com.example.bothsides;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
	public static final String EXTRA_TEMPO = "com.example.bothsides.TEMPO";
	public static final String EXTRA_METRE = "com.example.bothsides.METRE";

	public static final String EXTRA_REPETITIONS_1 = "com.example.bothsides.REPETITIONS_1";
	public static final String EXTRA_REPETITIONS_2 = "com.example.bothsides.REPETITIONS_2";
	public static final String EXTRA_RHYTHM_1 = "com.example.bothsides.RHYTHM_1";
	public static final String EXTRA_RHYTHM_2 = "com.example.bothsides.RHYTHM_2";
	public static final String EXTRA_PATTERN_LENGTH_1 = "com.example.bothsides.PATTERN_LENGTH_1";
	public static final String EXTRA_PATTERN_LENGTH_2 = "com.example.bothsides.PATTERN_LENGTH_2";


	public static final String EXTRA_RESULT_1 = "com.example.bothsides.RESULTS_1";
	public static final String EXTRA_RESULT_2 = "com.example.bothsides.RESULTS_2";
	public static final String EXTRA_HAS_RESULT_2 = "com.example.bothsides.HAS_RESULTS_2";
	Context context = this;
	Spinner spinner1;
	Spinner spinner2;
	String[] listItems = {"item 1", "item 2 ", "item 3", "item 4", "item 2137"};
	int[] listImages = {
		R.drawable.papryka,
		R.drawable.papryka,
		R.drawable.papryka,
		R.drawable.papryka,
		R.drawable.papryka};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		ImageArrayAdapter adapter = new ImageArrayAdapter(this, listItems, listImages);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setDropDownWidth(metrics.widthPixels);
		spinner1.setAdapter(adapter);
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(context, "spinner 1" + listItems[position], Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner2.setDropDownWidth(metrics.widthPixels);
		spinner2.setAdapter(adapter);
		spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(context, "spinner 2" + listItems[position], Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	public void startSingleLevel(View view) {
		Intent intent = new Intent(this, SingleLevel.class);
		//using hard-coded values for now
		intent.putExtra(EXTRA_TEMPO, 90.0);
		intent.putExtra(EXTRA_METRE, 4);
		intent.putExtra(EXTRA_REPETITIONS_1, 1);
		intent.putExtra(EXTRA_RHYTHM_1, new double[]{0.0, 0.5, 1.0, 2.0, 3.0, 3.25, 3.5, 3.75});
		intent.putExtra(EXTRA_PATTERN_LENGTH_1, 4.0);
		startActivity(intent);
	}

	public void startDoubleLevel(View view) {
		Intent intent = new Intent(this, DoubleLevel.class);
		intent.putExtra(EXTRA_TEMPO, 90.0);
		intent.putExtra(EXTRA_METRE, 4);
		intent.putExtra(EXTRA_REPETITIONS_1, 1);
		intent.putExtra(EXTRA_REPETITIONS_2, 1);
		intent.putExtra(EXTRA_RHYTHM_1, new double[]{0.0, 0.5, 1.0, 2.0, 3.0, 3.25, 3.5, 3.75});
		intent.putExtra(EXTRA_RHYTHM_2, new double[]{0.0, 1.0, 4.0/3, 3.5});
		intent.putExtra(EXTRA_PATTERN_LENGTH_1, 4.0);
		intent.putExtra(EXTRA_PATTERN_LENGTH_2, 4.0);

		startActivity(intent);
	}
}
