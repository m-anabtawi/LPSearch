package com.lp.actionbar.signup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import android.view.ViewGroup;
import org.json.JSONException;
import org.json.JSONObject;
import com.lp.actionbar.MainActivity;
import com.lp.actionbar.R;
import com.lp.actionbar.bean.ViewHolder;
import com.lp.actionbar.jsonhandler.UserFunctions;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
	private EditText password;
	private TextView checkError;
	private ImageView image;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private int REQUEST_CAMERA = 100;
	private int SELECT_FILE = 1;
	private String[] items = {"Take photo","Choose from library","Cancel"};
	private int[] icons = {R.drawable.ic_launcher,R.drawable.ic_launcher, R.drawable.ic_launcher};
	private ListAdapter adapter;
	private AlertDialog.Builder builder;
 	private ViewHolder holder;
 	private String ImagePath;
 	private static final String PREFS_NAME = "LoginPrefs";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		inputEmail = (EditText) findViewById(R.id.email);
	    userName=(EditText)findViewById(R.id.name);
	    password=(EditText)findViewById(R.id.password);
		register = (Button)findViewById(R.id.registerBtn);
		takePhoto = (Button)findViewById(R.id.take_photo_btn);
		checkError = (TextView) findViewById(R.id.errorText);
		image = (ImageView) findViewById(R.id.profile_photo);
		register.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				    String name = userName.getText().toString();
	                String email = inputEmail.getText().toString();
	                String userPassword=password.getText().toString();
	                String image =imagepath();
	                new RegisterAsyncTask().execute(name,email,userPassword,image);
	                
				
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
            JSONObject json=null;
            try {
            	json = userFunction.registerUser(params[0], params[1],params[2],params[3]);
                if (Integer.parseInt(json.getString(KEY_SUCCESS))==1){
                	new uploadAsyncTask().execute(params[3],params[1]);
                }
            }
            catch (JSONException e){
            	e.printStackTrace();
            } 
            catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        return json;
        }
        protected void onPostExecute(JSONObject json) {
        	try{
               if (json != null && json.getString(KEY_SUCCESS) != null) {
                	String res = json.getString(KEY_SUCCESS);
                    if(Integer.parseInt(res) == 1){
                    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("logged", "logged");
                        editor.commit();
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
	
	private class uploadAsyncTask extends AsyncTask<String, Void,JSONObject> {
        protected JSONObject doInBackground(String... params) {
        	UserFunctions userFunction = new UserFunctions();
            JSONObject json=null;
            try {
				json = userFunction.uploadeImage(params[0],params[1]);
			} 
            catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
            if(json == null)
            {
				new uploadAsyncTask().execute(params[0]);
            }
            return json;
        }
    }	  
	 	  
	void selectImage() {
     adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.drawer_list_item, items) {
     public View getView(int position, View convertView, ViewGroup parent) {
			final LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               if (convertView == null) {
			        convertView = inflater.inflate( R.layout.drawer_list_item, null);
			        holder = new ViewHolder();
			        holder.setIcon((ImageView) convertView.findViewById(R.id.icon)); 
			        holder.setTitle((TextView) convertView.findViewById(R.id.title));
			        convertView.setTag(holder);
			   } 
               else {
			     holder = (ViewHolder) convertView.getTag();
			   }     
               holder.getTitle().setText(items[position]);
               holder.getIcon().setImageResource(icons[position]);
			   return convertView;
			   }
			};
		    			
	
      builder = new AlertDialog.Builder(this);
   	  builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
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
        Uri ImageUri ;
        Bitmap profile;
     	Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
     	if (resultCode == RESULT_OK) {
             if (requestCode == REQUEST_CAMERA) {
          	     ImageUri = data.getData();
                 ImagePath = getPath(ImageUri);
          	     fieldresults.putString("bitmap", ImagePath);
                 i.putExtras(fieldresults);
                 image.setImageURI(ImageUri);
                 profile= BitmapFactory.decodeFile(ImagePath);
                 saveImage(profile);
          	  
          	 } 
             else if(requestCode == SELECT_FILE){   
                ImageUri = data.getData();
                ImagePath = getPath(ImageUri);
          	    fieldresults.putString("bitmap", ImagePath);
                i.putExtras(fieldresults);
                image.setImageURI(ImageUri);
                profile= BitmapFactory.decodeFile(ImagePath);
                saveImage(profile);
             }
 		}
	}
	 void saveImage(Bitmap myBitmap ) {
         
		 String root = Environment.getExternalStorageDirectory().toString();
		 File myDir = new File(root );
		 String fname = "Image.jpeg";
		 File file = new File (myDir, fname);
		 if (file.exists ())
			 file.delete (); 
		 try {
		     FileOutputStream out = new FileOutputStream(file);
		     myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		     out.flush();
		     out.close();
		 }
		 catch (Exception e){
		     e.printStackTrace();
		 }
	}
	 
	 public String getPath(Uri uri) {
		    Cursor cursor = getContentResolver().query(uri, null, null, null, null); 
		    cursor.moveToFirst(); 
		    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
		    return cursor.getString(idx); 
	}
	 private String imagepath() {
		    String root = Environment.getExternalStorageDirectory().toString();
		    String path =root+"/Image.jpeg";
		    return path;
	}
      
}
