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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.glass.app.Card;

/**
 * Activity showing the stopwatch options menu.
 */
public class MenuAlertActivity extends Activity {

	private final Handler mHandler = new Handler();

	String latitude;
	String longitude;
	String altitude;
	Card card;
	LocationManager locManager;

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		if (locManager.getBestProvider(criteria, true) != null) {
			Location myLocation = locManager.getLastKnownLocation(locManager
					.getBestProvider(criteria, true));
			latitude = Double.toString(myLocation.getLatitude());
			longitude = Double.toString(myLocation.getLongitude());
			altitude = Double.toString(myLocation.getAltitude());
			Log.d("values", latitude);
			Log.d("values", longitude);
			Log.d("values", altitude);
		}
		openOptionsMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.alert_menu, menu);

		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			//startActivity(new Intent(MenuAlertActivity.this,
					//MenuAlertActivity.class));
			 finish();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			//startActivity(new Intent(MenuAlertActivity.this,
					//MenuAlertActivity.class));
			 finish();
		}
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.team:
			//finish();
			startActivity(new Intent(MenuAlertActivity.this,
					CardsScroll.class));
			return true;
		case R.id.map:
			//finish();
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
					Uri.parse("http://maps.google.com/maps?q=" + latitude + ","
							+ longitude));
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

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
