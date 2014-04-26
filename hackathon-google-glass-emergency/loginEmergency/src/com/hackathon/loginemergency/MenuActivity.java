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
import android.content.Intent;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Activity showing the stopwatch options menu.
 */
public class MenuActivity extends Activity {

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
			startActivity(new Intent(MenuActivity.this,MenuAlertActivity.class));
			return true;

        case R.id.alarm:
                counter = 0;
         end = 0;

        mAudioManager = (AudioManager) this
                .getSystemService(Context.AUDIO_SERVICE);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sm.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor s = sm.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);

            // login_details.setText(s.getResolution()+"");
            sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp;

    public void onSensorChanged(SensorEvent event) {
        // This timestep's delta rotation to be multiplied by the current
        // rotation
        // after computing it from the gyro sample data.
        if (timestamp != 0) {
            final float dT = (event.timestamp - timestamp) * NS2S;
            // Axis of the rotation sample, not normalized yet.
            int axisX = (int) event.values[0] * 10;
            int axisY = (int) event.values[1] * 10;
            int axisZ = (int) event.values[2] * 10;

            // Log.d("Sensor", axisX + ", " + axisY + ", " + axisZ);

            if (Math.abs(axisY - previous) <= 3) {
                counter++;
            } else {
                counter = 0;
            }

            if (counter > 75) {
                Log.d("sensor", "ALAAAAAARM");
                mAudioManager.playSoundEffect(11);
                Log.d("sensor", counter + "");
                //counter =- 3;
            }
            
            if (counter > 125) {
                new CallAPI().execute("https://hackathonsolidario.com/"+deviceID, userID, deviceID);
                counter = 0;
            }
            previous = axisY;
        }
        timestamp = event.timestamp;
        end++;
        if (end >= 5000) {
            finish();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

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
     * Posts a {@link Runnable} at the end of the message loop, overridable for testing.
     */
    protected void post(Runnable runnable) {
        mHandler.post(runnable);
    }

}
