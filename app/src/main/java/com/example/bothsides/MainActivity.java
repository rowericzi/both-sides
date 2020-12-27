package com.example.bothsides;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
	private GameManager gm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RelativeLayout imgHolder = (RelativeLayout) findViewById(R.id.image_view_holder);
		gm = new GameManager(this, imgHolder);
		gm.run();
	}

	public void userInput(View view) { // button onClick function
		//gm.handleUserInput(); // not implemented yet
	}
}
