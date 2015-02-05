package com.lp.actionbar;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.lp.actionbar.R;
import com.lp.actionbar.jsonhandler.UserFunctions;
import com.lp.actionbar.signup.RegistrationActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ExploreFragment extends Fragment {
	private LinearLayout imageLayout;
	private ImageView[] img_items;
	private Button [] button_items;
	private int length=0;
	public ExploreFragment(){}
	private Context cont;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        imageLayout = (LinearLayout)rootView.findViewById(R.id.lay);
        cont=  getActivity();
        new GetImageAsyncTask().execute("");
        return rootView;
    }
	
	private class GetImageAsyncTask extends AsyncTask<String, Void,Bitmap []> {
        protected Bitmap[] doInBackground(String... params) {
        	UserFunctions userFunction = new UserFunctions();
            JSONObject json=null;
            try {
            	json = userFunction.getImage();  
            }
            catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            Bitmap [] bitmap=null;
            try {
            	String res = json.getString("url");
         		String delims = ",";
     	        String[] tokens = res.split(delims);
     	        length=tokens.length;
                bitmap=new Bitmap[length];
        		for(int i=0;i<length;i++){
    	        	URL url = new URL("http://pin2me.comyr.com/upload/image/100PINT.jpg");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
    				bitmap[i] = BitmapFactory.decodeStream(input);
    	        }
            	
				
				
			}
        	catch(IOException e){
        		e.printStackTrace();
        	}
        	catch (JSONException e) {
				e.printStackTrace();
			}
        return bitmap;
        	
        }
        protected void onPostExecute(Bitmap [] bitmap) {
        	//Toast.makeText(cont.getApplicationContext(), "length="+length, Toast.LENGTH_LONG).show();
        	img_items = new ImageView[length];
 		    button_items=new Button[length];
        	for(int i=0;i<length;i++){
        		img_items[i] = new ImageView(cont);
				button_items[i]=new Button(cont);
				button_items[i].setText("image");
		    	img_items[i].setImageBitmap(bitmap[i]);
	    	    imageLayout.addView(img_items[i]);
				imageLayout.addView(button_items[i]);
        		
        	}
        	
        }

        	
	}
}
