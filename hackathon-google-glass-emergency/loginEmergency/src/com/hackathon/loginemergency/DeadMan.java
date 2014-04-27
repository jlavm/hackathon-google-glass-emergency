package com.hackathon.loginemergency;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class DeadMan extends Activity implements SensorEventListener {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);

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

	final float NS2S = 1.0f / 1000000000.0f;
	final float[] deltaRotationVector = new float[4];
	float timestamp;
	
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
				// counter =- 3;
			}

			if (counter > 125) {
				new CallAPI().execute("https://hackathonsolidario.com/"
						+ deviceID, userID, deviceID);
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
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	class CallAPI extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {

			String url = params[0];
			String userID = params[1];
			String deviceID = params[2];

			JSONObject Parent = new JSONObject();
			try {
				Parent.put("id_user", userID);
				Parent.put("id_glass", deviceID);
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(url);
				StringEntity se = new StringEntity(Parent.toString());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				post.setHeader("Accept", "application/json");
				post.setHeader("Content-type", "application/json");
				post.setEntity(se);
				client.execute(post);
				Log.d("POST", "Done");
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("POST", "Failed");
			}

			return "";
		}

		protected void onPostExecute(String result) {

		}
	} // end CallAPI

}
