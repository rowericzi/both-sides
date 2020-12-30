package com.example.bothsides;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EndGameActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_game);

		TextView textView = (TextView) findViewById(R.id.textView);
		String resultStr = "";
		Intent intent = getIntent();
		double result1 = intent.getDoubleExtra(MainActivity.EXTRA_RESULT_1, 1.0);


		if (intent.getBooleanExtra(MainActivity.EXTRA_HAS_RESULT_2, false)) {
			resultStr += getString(R.string.left_hand_result);
			resultStr += String.valueOf(result1);
			double result2 = intent.getDoubleExtra(MainActivity.EXTRA_RESULT_2, 1.0);
			resultStr += "\n" + getString(R.string.right_hand_result);
			resultStr += String.valueOf(result2);
		} else {
			resultStr += getString(R.string.result);
			resultStr += String.valueOf(result1);
		}
		textView.setText(resultStr);
	}

	@Override
	protected void onDestroy() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		super.onDestroy();
	}

	public void returnToMainMenu(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}