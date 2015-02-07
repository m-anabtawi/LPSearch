package com.lp.actionbar.jsonhandler;


import java.io.File;
import java.io.UnsupportedEncodingException;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;
import org.json.JSONObject;



public class UserFunctions {
	
	private String loginURL = "http://192.168.1.102:7777/login";
	private String registerURL = "http://192.168.1.102:7777/register";
	private String uploadeURL = "http://192.168.1.102:7777/upload";
	private String getImageURL = "http://192.168.1.102:7777/getImage";
	private MultipartEntity reqEntity;
	private File file;
	private FileBody fileBody;

	
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
	

	public JSONObject registerUser(String name, String email,String password,String image) throws JSONException, UnsupportedEncodingException {

		String jsonString = "";
 
        JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("name",name );
        jsonObject.accumulate("email", email);
        jsonObject.accumulate("password", password);
        jsonObject.accumulate("image", image);
		jsonString = jsonObject.toString();
		StringEntity se = new StringEntity(jsonString);
	    JSONObject json = jsonParser.getJSONFromUrl(registerURL, se);
		return json;
	}
	
	public JSONObject uploadeImage(String image,String id) throws UnsupportedEncodingException{
		
		reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		file = new File(image);
		fileBody = new FileBody(file, "images/jpeg");
		reqEntity.addPart("fileupload", fileBody);
	    JSONObject json = jsonParser.getJSONFromUrl2(uploadeURL, reqEntity,id);
		return json;
	}
	public JSONObject getImage() throws UnsupportedEncodingException{
		StringEntity se=null ;
		JSONObject json = jsonParser.getJSONFromUrl(getImageURL,se);
		return json;
	}
	
	
}
