package com.lp.actionbar.signup;


import com.lp.actionbar.MainActivity;
import com.lp.actionbar.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SignUpActivity extends Activity{
	private Button signup;
	private Button login;
	private static final String PREFS_NAME = "LoginPrefs";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		signup = (Button) findViewById(R.id.signup_btn);
		login= (Button) findViewById(R.id.login_btn);
		
		
		 SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
      
        
		if (settings.getString("logged", "").toString().equals("logged")) {
	            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
	            startActivity(intent);
	        }
		signup.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
			    Intent registration = new Intent(SignUpActivity.this,RegistrationActivity.class);
			    startActivity(registration);
			    
				
			}
		});
		
		login.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent registration = new Intent(SignUpActivity.this,SignInActivity.class);
			    startActivity(registration);
				
			}
		});
	}
}
