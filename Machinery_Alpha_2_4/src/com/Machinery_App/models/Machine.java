package com.Machinery_App.models;

public class Machine{
	// DATA STORED IN SQLite
	// if you edit this, you should edit the helper
	private long 	id; 			//Required
	private String 	name; 			//Required
	private long 	list; 			//Required
	private long 	year;
	private long 	lastGrease;
	private long 	lastMaintenance;
	private String 	consumables;
	private String	serviceTableName;
	private String 	color;
	// TODO implement these data types
	private long 	picture;
	private long 	thumbnail;
	// private boolean 	isGreaseable;
	// private boolean 	isServicable;
	// private long 	dateAdded;
	// Part List
	// Greasing Intervals
	// Service Intervals
	// Interval Settings
	
	//Complete constructors
	public Machine(long id, String name, long list, long year, long lastGrease, long lastMaintenance, String consumables, String serviceTableName, String color) {
		this.id = id;
		this.name = name;
		this.list = list;
		this.year = year;
		this.lastGrease = lastGrease;
		this.lastMaintenance = lastMaintenance;		
		this.consumables = consumables;
		this.serviceTableName = serviceTableName;
		this.color = color;
	}
	
	//Prefered constructor
	public Machine(long id, String name, long list) {
		this.id = id;
		this.name = name;
		this.list = list;
		this.year = 0;
		this.lastGrease = 0;
		this.lastMaintenance = 0;
		this.color = "white";
		this.consumables = "";
		this.serviceTableName = "";
	}
	
	public Machine() {
		this.name = "New Card";
	}
	
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
	
	public long getList() {
		return list;
	}

	public void setList(long list) {
		this.list = list;
	}

	public long getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public long getPicture() {
		return picture;
	}

	public void setPicture(long picture) {
		this.picture = picture;
	}

	public long getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(long Thumbnail) {
		this.thumbnail = Thumbnail;
	}
	
	public long getLastGrease() {
		return lastGrease;
	}

	public void setLastGrease(int lastGrease) {
		this.lastGrease = lastGrease;
	}
	
	public long getLastMaintenance() {
		return lastMaintenance;
	}

	public void setLastMaintenance(int lastMaintenance) {
		this.lastMaintenance = lastMaintenance;
	}
	
	public String getConsumables() {
		return consumables;
	}

	public void setConsumables(String consumables) {
		this.consumables = consumables;
	}
	
	public String getServiceTableName() {
		return serviceTableName;
	}

	public void setServiceTableName(String tableName) {
		this.serviceTableName = tableName;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/*// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return name;
	}*/
	
}