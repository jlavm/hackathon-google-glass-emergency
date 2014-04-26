package com.hackathon.loginemergency;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.TextView;

public class Login extends Activity implements SensorEventListener {

	public String deviceID;
	public String userID;
	SensorManager sm;
	float previous;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		ArrayList<String> voiceResults = getIntent().getExtras()
				.getStringArrayList(RecognizerIntent.EXTRA_RESULTS);

		userID = voiceResults.get(0);
		deviceID = Secure.getString(getApplicationContext()
				.getContentResolver(), Secure.ANDROID_ID);

		TextView login_details = (TextView) findViewById(R.id.login_details);
		login_details.setText("Welcome, " + userID);

		Log.d("Sensor", "Ready to read sensor");
		// new CallAPI().execute("https://github.com/", userID, deviceID);

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
			int axisX = (int)event.values[0]*10;
			int axisY = (int)event.values[1]*10;
			int axisZ = (int)event.values[2]*10;

			Log.d("Sensor", axisX + ", " + axisY + ", " + axisZ);

			
			
			
		}
		timestamp = event.timestamp;

	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

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
