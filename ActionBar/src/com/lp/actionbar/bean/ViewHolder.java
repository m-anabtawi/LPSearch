package com.lp.actionbar.bean;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
	 private ImageView icon;
	 private TextView title;
	 
	 public void setIcon(ImageView icon){
		 this.icon=icon;
	 }
	 public void setTitle(TextView title){
		 this.title=title;
	 }
	 public ImageView getIcon(){
		 return this.icon;
	 }
	 public TextView getTitle(){
		 return this.title;
	 }
}
