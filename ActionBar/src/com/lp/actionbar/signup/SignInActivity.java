package com.lp.actionbar.signup;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.lp.actionbar.MainActivity;
import com.lp.actionbar.R;
import com.lp.actionbar.jsonhandler.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends Activity{
	private EditText inputEmail;
	private EditText inputPassword;
	private TextView checkError;
	private Button login;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_PATH ="path";
	private static final String PREFS_NAME = "LoginPrefs";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		inputEmail =(EditText) findViewById(R.id.email);
	    inputPassword =(EditText) findViewById(R.id.password);
	    checkError = (TextView) findViewById(R.id.errorText);
	    login =(Button) findViewById(R.id.loginBtn);
	    login.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				
				String  email = inputEmail.getText().toString();
	            String  password=inputPassword.getText().toString();
				new LoginAsyncTask().execute(email,password);
				
			}
		});
	}
	
	private class LoginAsyncTask extends AsyncTask<String, Void, JSONObject>
    {

        protected JSONObject doInBackground(String... params) {
          
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = null;
            try {
                json = userFunction.loginUser(params[0], params[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(JSONObject json){
            try {
                if (json != null && json.getString(KEY_SUCCESS) != null) {
                    Toast.makeText(getApplicationContext(),"login",Toast.LENGTH_LONG).show();
                    String res = json.getString(KEY_SUCCESS);
                    if(Integer.parseInt(res) == 1){
                    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("logged", "logged");
                        editor.commit();
                    	Bundle fieldresults = new Bundle();
                    	String path = json.getString(KEY_PATH);
                    	fieldresults.putString("bitmap", path);
                        Intent start = new Intent(SignInActivity.this,MainActivity.class);
                        start.putExtras(fieldresults);
                        startActivity(start);

                    }else{
                       
                        String res2 = json.getString(KEY_ERROR);
                        Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                        checkError.setText(res2);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                String res2 = null;
                try {
                    res2 = json.getString(KEY_ERROR);
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                    checkError.setText(res2);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }
        }

    }

}
