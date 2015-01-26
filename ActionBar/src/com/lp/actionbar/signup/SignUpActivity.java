package com.lp.actionbar.signup;


import com.lp.actionbar.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SignUpActivity extends Activity{
	Button signup;
	Button login;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		signup = (Button) findViewById(R.id.signup_btn);
		login= (Button) findViewById(R.id.login_btn);
		signup.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
			    Intent registration = new Intent(SignUpActivity.this,RegistrationActivity.class);
			    startActivity(registration);
			    
				
			}
		});
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent registration = new Intent(SignUpActivity.this,SignInActivity.class);
			    startActivity(registration);
				
			}
		});
	}
}
