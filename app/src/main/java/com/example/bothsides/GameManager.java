/*
    Copyright 2020 Ryszard Jezierski

    This file is part of both][sides.

    both][sides is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    both][sides is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with both][sides.  If not, see <https://www.gnu.org/licenses/>.
 */
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

/**
 * The game engine, animating notes falling from the top of the screen and calculating
 * player's score. It needs to be provided with a {@link RelativeLayout} used to store animated
 * notes.
 * The activity used to host the engine must implement the {@link Level} interface.
 * @see Level
 * @author Ryszard Jezierski
 */
public class GameManager extends Thread {
	private final static int ANIMATION_DURATION = 1500;
	private final static int INITIAL_DELAY = 1000;
	private final static int LATENCY = 200;
	private static int INITIAL_METRONOME_DURATION = 0;
	private final Context context;
	private final RelativeLayout imgHolder;
	private final ArrayList<ImageView> imgList = new ArrayList<>();
	private final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(128,128);
	private final ArrayList<Integer> timestamps = new ArrayList<>();
	private final ArrayList<Long> userInputTimestamps = new ArrayList<>();
	private Long startTime;
	//these are global, so they can be accessed by cancelGame();
	private final Timer endGameTimer = new Timer();
	private final Timer metronomeTimer = new Timer();

	private final double tempo;
	private boolean activateMetronome = false;
	private MediaPlayer metronome;

	/**
	 * Start game
	 */
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

	/**
	 * Class constructor
	 * @param context Activity where the game is launched
	 * @param imgHolder Container for animated images
	 * @param tempo Game tempo in Beats Per Minute
	 * @param meter Meter nominator (denominator is 4)
	 * @param measures Number of measures to play
	 * @param rhythm Array of "timestamps"; 1.0 = quarter note
	 * @param patternLength Length of the pattern
	 */
	public GameManager(Context context, RelativeLayout imgHolder, double tempo, int meter, int measures, double[] rhythm, double patternLength) {
		this.context = context;
		this.imgHolder = imgHolder;
		this.tempo = tempo;
		InitTimestamps(tempo, meter, measures, rhythm, patternLength);
	}

	/**
	 * Class constructor allowing to activate metronome
	 * @param context Activity where the game is launched
	 * @param imgHolder Container for animated images
	 * @param tempo Game tempo in Beats Per Minute
	 * @param meter Meter nominator (denominator is 4)
	 * @param measures Number of measures to play
	 * @param rhythm Array of "timestamps"; 1.0 = quarter note
	 * @param patternLength Length of the pattern
	 * @param activateMetronome Whether the metronome should be activated or not
	 */
	public GameManager(Context context, RelativeLayout imgHolder, double tempo, int meter, int measures, double[] rhythm, double patternLength, boolean activateMetronome) {
		this.context = context;
		this.imgHolder = imgHolder;
		this.tempo = tempo;
		if (activateMetronome) {
			this.activateMetronome = activateMetronome;
			metronome = MediaPlayer.create(context, R.raw.metronome);
		}
		InitTimestamps(tempo, meter, measures, rhythm, patternLength);
	}

	private void InitTimestamps(double tempo, int meter, int measures, double[] rhythm, double patternLength) {
		INITIAL_METRONOME_DURATION = (int) Math.round(60000*meter/tempo);
		int i = 0;
		do {
			for ( double note : rhythm ){
				timestamps.add((int) Math.round(60000*(note + patternLength * i )/tempo + INITIAL_DELAY + INITIAL_METRONOME_DURATION - ANIMATION_DURATION));
			}
			i++;
		} while (timestamps.get(timestamps.size() - 1) < 60000*measures*meter/tempo + INITIAL_DELAY + INITIAL_METRONOME_DURATION - ANIMATION_DURATION);
	}

	private void addImageAndStartAnimation() {
		//create new image and add it to the screen
		imgList.add(new ImageView(context));
		final int currentElem = imgList.size() - 1;
		imgList.get(currentElem).setLayoutParams(params);
		imgList.get(currentElem).setImageResource(R.drawable.quarter_note);
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

	/**
	 * Call this method on user interaction
	 */
	public void processUserInput() {
		userInputTimestamps.add(System.nanoTime());
	}

	private void endGame() {
		metronomeTimer.cancel();
		metronomeTimer.purge();
		ArrayList<Integer> logTimestampsCorrected = new ArrayList<>();
		ArrayList<Integer> results = new ArrayList<>();
		double result;
		for (Long timestamp : userInputTimestamps) {
			int correctedTimestamp = Math.toIntExact((timestamp-startTime)/1000000 - ANIMATION_DURATION - LATENCY);
			int closest = searchClosest(timestamps, correctedTimestamp);
			results.add(closest - correctedTimestamp);
			logTimestampsCorrected.add(correctedTimestamp);
		}
		Log.d("Timestamps", String.valueOf(timestamps));
		Log.d("InputTimestampCorrected", String.valueOf(logTimestampsCorrected));
		Log.d("Results", String.valueOf(results));
		if (results.size() == timestamps.size()) {
			result = calculateResult(results);
		} else {
			result = 2.0;
		}
		((Level) context).endGame(this, result);

	}

	private int searchClosest (ArrayList<Integer> arrayList, int key) {
		int minimumValue = Integer.MAX_VALUE;
		int minimumIndex = 0;
		for (int i = 0; i < arrayList.size(); i++) {
			if (Math.abs(arrayList.get(i) - key) < minimumValue) {
				minimumValue = Math.abs(arrayList.get(i) - key);
				minimumIndex = i;
			}
		}
		return arrayList.get(minimumIndex);
	}

	private double calculateResult(ArrayList<Integer> arrayList) {
		int sum = 0;
		double variance = 0.0;
		for (int elem : arrayList) {
			sum += elem;
		}
		double mean = sum/(double)arrayList.size();
		Log.d("mean", String.valueOf(mean));
		for (int elem : arrayList) {
			variance += Math.pow(elem - mean, 2);
		}
		variance = variance /arrayList.size();
		Log.d("stdDev", String.valueOf(Math.sqrt(variance)));
		return 10 - (Math.sqrt(variance) + Math.abs(mean))*8/500;
	}

	/**
	 * Cancels currently running game
	 */
	public void cancelGame() {
		endGameTimer.cancel();
		endGameTimer.purge();
		metronomeTimer.cancel();
		metronomeTimer.purge();
		Log.d("GameManager", "Game cancelled!");
	}
}
