package com.lp.actionbar.model;


public class NavDrawerItem {
	
	private String title;
	private int icon;
	private String iconPath;
	
	public NavDrawerItem(){}

	public NavDrawerItem(String title, int icon){
		this.title = title;
		this.icon = icon;
	}
	
	public NavDrawerItem(String title, String icon){
		this.title = title;
		this.iconPath = icon;
	}
	
	
	public String getTitle(){
		return this.title;
	}
	
	public int getIcon(){
		return this.icon;
	}
	
	public String getIconPath(){
		return this.iconPath;
	}
	
	
	
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setIcon(int icon){
		this.icon = icon;
	}
	public void setIconPath(String icon){
		this.iconPath = icon;
	}

}
