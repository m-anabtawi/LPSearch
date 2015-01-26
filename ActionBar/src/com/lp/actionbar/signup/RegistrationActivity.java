package com.lp.actionbar.signup;

import java.io.UnsupportedEncodingException;
import android.view.ViewGroup;
import org.json.JSONException;
import org.json.JSONObject;
import com.lp.actionbar.MainActivity;
import com.lp.actionbar.R;
import com.lp.actionbar.jsonhandler.UserFunctions;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;


public class RegistrationActivity extends Activity{
	private Button register;
	private Button takePhoto;
	private EditText inputEmail;
	private EditText userName;
	private TextView checkError;
	private ImageView image;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	final int REQUEST_CAMERA = 100;
 	final int MEDIA_TYPE_IMAGE = 1;
 	final int SELECT_FILE = 1;
 	public String ImagePath;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		inputEmail = (EditText) findViewById(R.id.email);
	    userName=(EditText)findViewById(R.id.name);
		register = (Button)findViewById(R.id.registerBtn);
		takePhoto = (Button)findViewById(R.id.take_photo_btn);
		checkError = (TextView) findViewById(R.id.errorText);
		image = (ImageView) findViewById(R.id.profile_photo);
		register.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				    String name = userName.getText().toString();
	                String email = inputEmail.getText().toString();
	                String image =ImagePath;
	                new RegisterAsyncTask().execute(name,email,image);
				
			}
		});
		
		takePhoto.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				selectImage();
				
			}
		});
		
	}
	
	private class RegisterAsyncTask extends AsyncTask<String, Void,JSONObject> {

        protected JSONObject doInBackground(String... params) {
        	
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = null;
            try {
            	
                json = userFunction.registerUser(params[0], params[1],params[2]);
                
            } catch (JSONException e) {
            	
                e.printStackTrace();
                
            } catch (UnsupportedEncodingException e) {
            	
                e.printStackTrace();
            }
            return json;
        }
        protected void onPostExecute(JSONObject json) {
        	
           try{
        	   
                if (json != null && json.getString(KEY_SUCCESS) != null) {
                	
                    String res = json.getString(KEY_SUCCESS);
                    
                    if(Integer.parseInt(res) == 1){
                    	Bundle fieldresults = new Bundle();
                        Intent start = new Intent(RegistrationActivity.this,MainActivity.class);
                        fieldresults.putString("bitmap", ImagePath);
                        start.putExtras(fieldresults);
                        startActivity(start);

                    }
                }
            } 
           catch (JSONException e) {
        	   
                String res2 = null;
                try {
                    res2 = json.getString(KEY_ERROR);
                    checkError.setText(res2);
                }
                catch (JSONException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }

    }
	
		  
	 	  
	void selectImage() {
    		final String[] items = {
			   "Take photo",
			   "Choose from library",
			   "Cancel"
			};


			final int[] icons = {
			     R.drawable.ic_launcher,
			     R.drawable.ic_launcher,
			     R.drawable.ic_launcher
			};
	final ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.drawer_list_item, items) {

			    ViewHolder holder;
			    class ViewHolder {
			        ImageView icon;
			        TextView title;
			    }
			    public View getView(int position, View convertView, ViewGroup parent) {
			    final LayoutInflater inflater = (LayoutInflater) getApplicationContext()
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			    if (convertView == null) {
			        convertView = inflater.inflate( R.layout.drawer_list_item, null);
			        holder = new ViewHolder();
			        holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			        holder.title = (TextView) convertView.findViewById(R.id.title);
			        convertView.setTag(holder);
			    } else {
			     
			        holder = (ViewHolder) convertView.getTag();
			    }     

			    holder.title.setText(items[position]);

			    holder.icon.setImageResource(icons[position]);
			    return convertView;
			    }
			};
		    			
	
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
   	  builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
  	   @Override
  	   public void onClick(DialogInterface dialog, int item) {
  	       if (items[item]=="Take photo") {
  	    	   
  	    	 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	  		  startActivityForResult(intent, REQUEST_CAMERA);
  	       } 
  	       else if (items[item].equals("Choose from library")) {
  	           Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
  	           intent.setType("image/*");
  	           startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
  	       } else if (items[item].equals("Cancel")) {
  	           dialog.dismiss();
  	       }
  	   }
  	   });
    	  builder.show();
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        Bundle fieldresults = new Bundle();
     	  Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
     	 
     	 
 		if (resultCode == RESULT_OK) {
             if (requestCode == REQUEST_CAMERA) {
          	   
          	     Uri ImageUri = data.getData();
                 ImagePath = getPath(ImageUri);
          	     fieldresults.putString("bitmap", ImagePath);
                 i.putExtras(fieldresults);
                 image.setImageURI(ImageUri);
          	  
          	    
  	             
             } 
             else if (requestCode == SELECT_FILE) {   
          	   
          	    Uri ImageUri = data.getData();
                ImagePath = getPath(ImageUri);
          	    fieldresults.putString("bitmap", ImagePath);
                i.putExtras(fieldresults);
                image.setImageURI(ImageUri);
  	          
          	  
             }
 		}
	}
	 public String getPath(Uri uri) {
	        if( uri == null ) {
	            return null;}
	        
	        String[] projection = { MediaStore.Images.Media.DATA };

			Cursor cursor = managedQuery(uri, projection, null, null, null);
	        if( cursor != null ){
	            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	            cursor.moveToFirst();
	            return cursor.getString(column_index);
	        }	        return uri.getPath();
	    }
	
      
}
