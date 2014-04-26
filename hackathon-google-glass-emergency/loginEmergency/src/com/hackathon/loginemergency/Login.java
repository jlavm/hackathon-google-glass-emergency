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
import android.content.Intent;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.TextView;

public class Login extends Activity implements SensorEventListener {

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

	TextView device_ID;
	
	
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
		
		
		device_ID = (TextView) findViewById(R.id.device_id);
		device_ID.setText(deviceID);
		
		Log.d("Sensor", "Ready to read sensor");
		new CallAPI().execute("https://hackathonsolidario.com/", userID, deviceID);
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		    @Override
		    public void run() {
		        // Do something after 5s = 5000ms

		    	finish();
				startActivity(new Intent(Login.this,MenuActivity.class));
		    }
		}, 3000);
		
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
