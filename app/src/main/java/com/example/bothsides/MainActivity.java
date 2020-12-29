package com.example.bothsides;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
	public static final String EXTRA_TEMPO = "com.example.bothsides.TEMPO";
	public static final String EXTRA_MEASURES_1 = "com.example.bothsides.MEASURES_1";
	public static final String EXTRA_RHYTHM_1 = "com.example.bothsides.RHYTHM_1";
	public static final String EXTRA_MEASURES_2 = "com.example.bothsides.MEASURES_2";
	public static final String EXTRA_RHYTHM_2 = "com.example.bothsides.RHYTHM_2";
	Spinner spinner;
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
		spinner = (Spinner) findViewById(R.id.spinner);
		ImageArrayAdapter adapter = new ImageArrayAdapter(this, listItems, listImages);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}

	public void startSingleLevel(View view) {
		Intent intent = new Intent(this, SingleLevel.class);
		//using hard-coded values for now
		intent.putExtra(EXTRA_TEMPO, 90.0);
		intent.putExtra(EXTRA_MEASURES_1, 2);
		intent.putExtra(EXTRA_RHYTHM_1, new double[]{0.0, 0.5, 1.0, 2.0, 3.0, 3.25, 3.5, 3.75});
		startActivity(intent);
	}

	public void startDoubleLevel(View view) {
		Intent intent = new Intent(this, DoubleLevel.class);
		intent.putExtra(EXTRA_TEMPO, 90.0);
		intent.putExtra(EXTRA_MEASURES_1, 2);
		intent.putExtra(EXTRA_RHYTHM_1, new double[]{0.0, 0.5, 1.0, 2.0, 3.0, 3.25, 3.5, 3.75});
		intent.putExtra(EXTRA_MEASURES_2, 2);
		intent.putExtra(EXTRA_RHYTHM_2, new double[]{0.0, 1.0, 4.0/3, 3.5});
		startActivity(intent);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(this, listItems[position], Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
}
