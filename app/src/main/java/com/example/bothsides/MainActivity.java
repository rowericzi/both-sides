package com.example.bothsides;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
	String[] listItems = {"item 1", "item 2 ", "item 3", "item 4", "item 2137"};
	int[] listImages = {R.drawable.papryka, R.drawable.papryka, R.drawable.papryka, R.drawable.papryka, R.drawable.papryka};
	public static final String EXTRA_TEMPO = "com.example.bothsides.TEMPO";
	public static final String EXTRA_MEASURES_1 = "com.example.bothsides.MEASURES_1";
	public static final String EXTRA_RHYTHM_1 = "com.example.bothsides.RHYTHM_1";
	public static final String EXTRA_MEASURES_2 = "com.example.bothsides.MEASURES_2";
	public static final String EXTRA_RHYTHM_2 = "com.example.bothsides.RHYTHM_2";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void startSingleLevel(View view) {
		Intent intent = new Intent(this, SingleLevel.class);
		//using hard-coded values for now
		intent.putExtra(EXTRA_TEMPO, 90.0);
		intent.putExtra(EXTRA_MEASURES_1, 10);
		intent.putExtra(EXTRA_RHYTHM_1, new double[]{0.0, 0.5, 1.0, 2.0, 3.0, 3.25, 3.5, 3.75});
		startActivity(intent);
	}

	public void startDoubleLevel(View view) {
		Intent intent = new Intent(this, DoubleLevel.class);
		intent.putExtra(EXTRA_TEMPO, 90.0);
		intent.putExtra(EXTRA_MEASURES_1, 10);
		intent.putExtra(EXTRA_RHYTHM_1, new double[]{0.0, 0.5, 1.0, 2.0, 3.0, 3.25, 3.5, 3.75});
		intent.putExtra(EXTRA_MEASURES_2, 10);
		intent.putExtra(EXTRA_RHYTHM_2, new double[]{0.0, 1.0, 4.0/3, 3.5});
		startActivity(intent);
	}

	public void xd(View view) {
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
		ImageArrayAdapter adapterr = new ImageArrayAdapter(this, listItems, listImages);
		final ListPopupWindow popup = new ListPopupWindow(this);
		popup.setAnchorView(view);
		popup.setWidth(1000);
		popup.setAdapter(adapterr);
		popup.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				popup.dismiss();
			}
		});
		popup.show();
	}
}
