package com.lp.actionbar.jsonhandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;



public class JSONParser {

	 private InputStream is = null;
	 private JSONObject jObj = null;
	 private String json = "";
	 private HttpClient httpClient;
	 private HttpPost httpPost;
	
	


	public JSONParser() {

	}

	public JSONObject getJSONFromUrl(String url, StringEntity params) {

	
		try {
		
			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost(url);
			httpPost.setEntity(params);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();

		} catch (Exception e) {

		}

		
		try {
			jObj = new JSONObject(json);			
		} catch (JSONException e) {

		}

	
		return jObj;

	}
	public JSONObject getJSONFromUrl2(String url,MultipartEntity params,String email) {

		try {
			
			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost(url);
			httpPost.setHeader("email",email);
			httpPost.setEntity(params);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();

		} catch (Exception e) {

		}

		
		try {
			jObj = new JSONObject(json);			
		} catch (JSONException e) {

		}

	
		return jObj;
		}
	
}

