package com.example.bothsides;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
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
	private final static int ANIMATION_DURATION = 1500;
	private final static int INITIAL_DELAY = 1000;
	private final Context context;
	private final RelativeLayout imgHolder;
	private final ArrayList<ImageView> imgList = new ArrayList<>();
	private final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(128,128);
	private final ArrayList<Integer> timestamps = new ArrayList<>();
	private final ArrayList<Integer> userInputTimestamps = new ArrayList<>();
	private Long startTime;
	//these are global, so they can be accessed by cancelGame();
	private final Timer endGameTimer = new Timer();
	private final Timer metronomeTimer = new Timer();

	private final double tempo;
	private boolean activateMetronome = false;
	private MediaPlayer metronome;

	@Override
	public void run() {
		startTime = System.nanoTime();
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

		if (activateMetronome) {
			TimerTask metronomeTimerTask = new TimerTask() {
				@Override
				public void run() {
					metronome.start();
				}
			};
			metronomeTimer.schedule(metronomeTimerTask, INITIAL_DELAY, Math.round(60000/tempo));
		}

		TimerTask endGameTimerTask = new TimerTask() {
			@Override
			public void run() {
				endGame();
			}
		};
		endGameTimer.schedule(endGameTimerTask, timestamps.get(timestamps.size()-1)+2000);
	}

	public GameManager(Context context, RelativeLayout imgHolder, double tempo, int metre, int repetitions, double[] rhythm, double patternLength) {
		this.context = context;
		this.imgHolder = imgHolder;
		this.tempo = tempo;
		InitTimestamps(tempo, metre, repetitions, rhythm, patternLength);
	}

	public GameManager(Context context, RelativeLayout imgHolder, double tempo, int metre, int repetitions, double[] rhythm, double patternLength, boolean activateMetronome) {
		this.context = context;
		this.imgHolder = imgHolder;
		this.tempo = tempo;
		if (activateMetronome) {
			this.activateMetronome = activateMetronome;
			metronome = MediaPlayer.create(context, R.raw.metronome);
		}
		InitTimestamps(tempo, metre, repetitions, rhythm, patternLength);
	}

	private void InitTimestamps(double tempo, int metre, int repetitions, double[] rhythm, double patternLength) {
		for (int i = 0; i < repetitions; i++){
			for ( double note : rhythm ){
				timestamps.add((int) Math.round(60000*(metre + note + patternLength * i )/tempo + INITIAL_DELAY - ANIMATION_DURATION));
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
		animate.setDuration(ANIMATION_DURATION);
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

	public void processUserInput() {
		userInputTimestamps.add((int) (System.nanoTime() - startTime)/1000000);
	}

	public void endGame() {
		((Level) context).endGame(this, 2137.0);
		metronomeTimer.cancel();
		metronomeTimer.purge();
	}

	public void cancelGame() {
		endGameTimer.cancel();
		endGameTimer.purge();
		metronomeTimer.cancel();
		metronomeTimer.purge();
		Log.d("GameManager", "Game cancelled!");
	}
}
