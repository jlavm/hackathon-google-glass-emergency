package com.hackathon.loginemergency;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.speech.RecognizerIntent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

public class Login extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		String deviceID = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);

		ArrayList<String> voiceResults = getIntent().getExtras()
		        .getStringArrayList(RecognizerIntent.EXTRA_RESULTS);

		TextView login_details = (TextView) findViewById(R.id.login_details);
		login_details.setText("Welcome, " + voiceResults.get(0));
		
		
		TextView device_ID = (TextView) findViewById(R.id.device_id);
		device_ID.setText(deviceID);
	}
	
	


}
