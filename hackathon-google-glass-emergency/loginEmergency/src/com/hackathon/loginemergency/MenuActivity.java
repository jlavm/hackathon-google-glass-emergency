package com.hackathon.loginemergency;

/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Activity showing the stopwatch options menu.
 */
public class MenuActivity extends Activity {

	public String deviceID;
	public String userID;
	SensorManager sm;
	float previous;
	int counter;
	int end;
	int mFinishSoundId;
	int mCountDownSoundId;
	private SoundPool mSoundPool;
	AudioManager mAudioManager;
	final int SOUND_PRIORITY = 1;
	final int MAX_STREAMS = 1;
	private final Handler mHandler = new Handler();

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		openOptionsMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.alert:
			startActivity(new Intent(MenuActivity.this, MenuAlertActivity.class));
			return true;

		case R.id.alarm:
			startActivity(new Intent(MenuActivity.this, DeadMan.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		// Nothing else to do, closing the Activity.
		finish();
	}

	/**
	 * Posts a {@link Runnable} at the end of the message loop, overridable for
	 * testing.
	 */
	protected void post(Runnable runnable) {
		mHandler.post(runnable);
	}

}
