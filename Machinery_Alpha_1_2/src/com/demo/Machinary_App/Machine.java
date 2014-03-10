package com.demo.Machinary_App;

public class Machine implements Colors{
	// DATA STORED IN SQLite
	// if you edit this, you should edit 
	private String comment;
	//private picture;
	private long id;
	private String name;
	private int color;
	
	
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
	
}