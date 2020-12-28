package com.example.bothsides;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SingleLevel extends AppCompatActivity {
	private GameManager gm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_level);
		RelativeLayout imgHolder = (RelativeLayout) findViewById(R.id.img_view_single);
		gm = new GameManager(this, imgHolder);
		gm.start();
	}

	public void userInput(View view) { // button onClick function
		//gm.handleUserInput(); // not implemented yet
	}
}
