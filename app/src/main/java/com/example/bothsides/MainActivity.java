package com.example.bothsides;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
	private GameManager gm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RelativeLayout imgHolder = (RelativeLayout) findViewById(R.id.image_view_holder);
		gm = new GameManager(this, imgHolder);
		gm.start();
	}

	public void userInput(View view) { // button onClick function
		//gm.handleUserInput(); // not implemented yet
	}
}
