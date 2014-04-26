package com.hackathon.loginemergency;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.speech.RecognizerIntent;
import android.widget.TextView;

public class Login extends Activity{

	TextView device_ID;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		
		String deviceID = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);

		ArrayList<String> voiceResults = getIntent().getExtras()
		        .getStringArrayList(RecognizerIntent.EXTRA_RESULTS);

		TextView login_details = (TextView) findViewById(R.id.login_details);
		login_details.setText("Welcome, " + voiceResults.get(0));
		
		
		device_ID = (TextView) findViewById(R.id.device_id);
		device_ID.setText(deviceID);
		
		
		
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
	
	


}
