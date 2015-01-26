package com.lp.actionbar.jsonhandler;


import java.io.UnsupportedEncodingException;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;



public class UserFunctions {
	

	
	private static String loginURL = "";
	private static String registerURL = "http://10.10.10.52:7777/register";
	
	
	private JSONParser jsonParser;
	
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	

	public JSONObject loginUser(String email, String password) throws JSONException, UnsupportedEncodingException {
		String jsonString = "";
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("email", email);
        jsonObject.accumulate("password",password );
		jsonString = jsonObject.toString();
        StringEntity se = new StringEntity(jsonString);
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, se);

		return json;
	}
	

	public JSONObject registerUser(String name, String email,String image) throws JSONException, UnsupportedEncodingException {

		String jsonString = "";
 
        JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("name",name );
        jsonObject.accumulate("email", email);
        jsonObject.accumulate("password", image);
		jsonString = jsonObject.toString();
        StringEntity se = new StringEntity(jsonString);
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, se);
		
		return json;
	}
	


	
}
