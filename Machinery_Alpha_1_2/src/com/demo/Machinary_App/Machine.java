package com.demo.Machinary_App;

import android.graphics.Bitmap;

public class Machine implements Colors{
	// DATA STORED IN SQLite
	// if you edit this, you should edit 
	private long id;
	private String name;
	private int color;
	private String comment;
	// TODO generate with ThumbnalUtils.extractThumbnail
	private Bitmap thumbnail;
	//private String picture;
	// TODO separate table for pictures, linked by id 
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public int colorCount(){
		return numberOfColors;
	}
	
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return name;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	// TODO static function machine to cursor
	
}