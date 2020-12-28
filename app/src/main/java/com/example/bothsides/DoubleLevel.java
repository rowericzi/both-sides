package com.example.bothsides;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class DoubleLevel extends AppCompatActivity {
	private GameManager gm1;
	private GameManager gm2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_double_level);
		RelativeLayout imgHolder1 = (RelativeLayout) findViewById(R.id.img_view_double_1);
		RelativeLayout imgHolder2 = (RelativeLayout) findViewById(R.id.img_view_double_2);
		gm1 = new GameManager(this, imgHolder1);
		gm2 = new GameManager(this, imgHolder2);
		gm1.start();
		gm2.start();
	}

	public void userInput1(View view) { // button onClick function
		//gm.handleUserInput(); // not implemented yet
	}

	public void userInput2(View view) {
	}
}