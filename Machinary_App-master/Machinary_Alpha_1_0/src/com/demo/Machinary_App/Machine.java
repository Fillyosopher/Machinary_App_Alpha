package com.demo.Machinary_App;

import java.util.HashMap;
import java.util.List;

public class Machine{
	// DATA STORED IN SQLite
	// if you edit this, you should edit the helper
	private long 	id; 			//Required
	private String 	name; 			//Required
	private String 	list; 			//Required
	private int 	year;
	private String purchase;
	private String 	lastGrease;
	private String 	lastMaintenance;
	private String 	color;
	private String oil_filter, trans_filter, hyd_filter, hyd_pump;
	// TODO implement these data types
	private long 	picture;
	private long 	thumbnail;
	private long 	dateAdded;
	// Part List
	// Greasing Intervals
	// Service Intervals
	// Interval Settings
	// Comments List

	public Machine(long id, String name, String list, int year, String Purchased, String lastGrease, String lastMaintenance, String color, String oilfilter, String trans_filter, String hydfilter, String  hyd_pump) {
		this.id = id;
		this.name = name;
		this.list = list;
		this.year = year;
		this.purchase = Purchased;
		this.lastGrease = lastGrease;
		this.lastMaintenance = lastMaintenance;
		this.color = color;
		this.oil_filter = oilfilter;
		this.trans_filter = trans_filter;
		this.hyd_filter = hydfilter;
		this.hyd_pump = hyd_pump;
	}

	public Machine() {
		// TODO Auto-generated constructor stub
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

	public String getoilfilter(){
		return oil_filter;
	}
	
	public String gettransfilter(){
		return trans_filter;
	}
	
	public String gethydfilter(){
		return hyd_filter;
	}
	
	public String gethydpump(){
		return hyd_pump;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
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
	
	public void setPurchaseDate(String purchased){
		purchase = purchased;
	}

	public void setThumbnail(long Thumbnail) {
		this.thumbnail = Thumbnail;
	}

	public String getLastGrease() {
		return lastGrease;
	}

	public void setLastGrease(String lastGrease) {
		this.lastGrease = lastGrease;
	}

	public String getLastMaintenance() {
		return lastMaintenance;
	}

	public String getPurchaseDate(){
		return purchase;
	}
	
	public void setLastMaintenance(String lastMaintenance) {
		this.lastMaintenance = lastMaintenance;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	/*// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return name;
	}*/

	// TODO static function machine to cursor

}