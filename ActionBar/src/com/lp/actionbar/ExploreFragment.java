package com.lp.actionbar;


import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import com.lp.actionbar.R;
import com.lp.actionbar.jsonhandler.UserFunctions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;



public class ExploreFragment extends Fragment {
	public ExploreFragment(){}
	ListView listView;
	DisplayImageOptions options;
	ImageLoader imageLoader;
	String [] imageUrls;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
	}

	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_list, container, false);
        listView = (ListView) rootView.findViewById(android.R.id.list);
        new GetImageAsyncTask().execute("");
        return rootView;
    }
	
   class ImageAdapter extends BaseAdapter {
	    private LayoutInflater inflater;
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	    ImageAdapter() {
		inflater = LayoutInflater.from(getActivity());
		}
		
		public int getCount() {
		
		return imageUrls.length;
		}
		@Override
		public Object getItem(int position) {
		return position;
		}
		
		public long getItemId(int position) {
		return position;
		}
        public View getView(final int position, View convertView, ViewGroup parent) {
        	View view = convertView;
        	final ViewHolder holder;
        	ImageLoader imageLoader;
        	imageLoader = ImageLoader.getInstance();
        	imageLoader.init(ImageLoaderConfiguration.createDefault((Context)getActivity()));
        	if (convertView == null) {
        	view = inflater.inflate(R.layout.item_list_view, parent, false);
        	holder = new ViewHolder();
        	holder.button = (Button) view.findViewById(R.id.button);
        	holder.image = (ImageView) view.findViewById(R.id.image);
        	view.setTag(holder);
        	} else {
        	holder = (ViewHolder) view.getTag();
        	} 
			ImageLoader.getInstance().displayImage(imageUrls[position], holder.image, options, animateFirstListener);
			return view;
	   }
    }
	private static class ViewHolder {
		Button button;
		ImageView image;
	}
	private class GetImageAsyncTask extends AsyncTask<String, Void,String [] > {
        protected String [] doInBackground(String... params) {
        	UserFunctions userFunction = new UserFunctions();
            JSONObject json=null;
            try {
            	json = userFunction.getImage();             	
            	String res = json.getString("url");
         		String delims = ",";
         		imageUrls= res.split(delims);
            }
            catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            catch (JSONException e) {
				e.printStackTrace();
			}
          return imageUrls;
        }
   
        protected void onPostExecute(String []image) {
        	((ListView) listView).setAdapter(new ImageAdapter());
        }
        
    }
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
	
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			 if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			 }
		}
    }
}
