package com.example.bothsides;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameManager extends Thread {
	private final Context context;
	private final RelativeLayout imgHolder;
	private final ArrayList<ImageView> imgList = new ArrayList<>();
	private final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(128,128);
	private final ArrayList<Integer> timestamps = new ArrayList<>();

	@Override
	public void run() {
		//for each timestamp, schedule the animation
		for (int timestamp : timestamps) {
			TimerTask addImageTimerTask = new TimerTask() {
				@Override
				public void run() {
					addImageAndStartAnimation();
				}
			};
			Timer addImageTimer = new Timer();
			addImageTimer.schedule(addImageTimerTask, timestamp);
		}
	}

	public GameManager(Context context, RelativeLayout imgHolder) {
		Intent intent = ((Activity) context).getIntent();
		double tempo = intent.getDoubleExtra(MainActivity.EXTRA_TEMPO, 120.0);
		int measures = intent.getIntExtra(MainActivity.EXTRA_MEASURES, 10);
		double[] rhythm = intent.getDoubleArrayExtra(MainActivity.EXTRA_RHYTHM);
		this.context = context;
		this.imgHolder = imgHolder;
		for (int i = 0; i < measures; i++){
			for ( double note : rhythm ){
				timestamps.add((int) Math.round(60000*(note + 4 * i )/tempo + 1000));
			}
		}
	}

	private void addImageAndStartAnimation() {
		//create new image and add it to the screen
		imgList.add(new ImageView(context));
		final int currentElem = imgList.size() - 1;
		imgList.get(currentElem).setLayoutParams(params);
		imgList.get(currentElem).setImageResource(R.drawable.papryka);
		((Activity) context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				imgHolder.addView(imgList.get(currentElem));
			}
		});
		//create animation
		Animation animate = new TranslateAnimation(0, 0, -128, imgHolder.getMeasuredHeight() - 128);
		animate.setInterpolator(new LinearInterpolator());
		animate.setDuration(1500);
		animate.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				imgList.get(currentElem).setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		imgList.get(currentElem).startAnimation(animate);
	}
}
