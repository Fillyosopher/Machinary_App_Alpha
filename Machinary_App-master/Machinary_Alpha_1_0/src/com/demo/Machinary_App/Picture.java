package com.demo.Machinary_App;

public class Picture {
	// DATA STORED IN SQLite
	// if you edit this, you should edit the helper	
	private long id;
	private String type;
	// TODO change this to the proper data type
	private String pictureData;

	// TODO generate with ThumbnalUtils.extractThumbnail

	public Picture(long id, String type, String pictureData) {
		this.id = id;
		this.type = type;
		this.pictureData = pictureData;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType(){
		return type;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getPicture() {
		return pictureData;
	}

	public void setPicture(String pictureData) {
		this.pictureData = pictureData;
	}	

}
