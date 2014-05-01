package com.Machinery_App.db;

public class DatabaseTable {
	public String TABLE_NAME;
	public static final String COLUMN_ID = "_id";

	//should be private, fix later, TODO
	public String[] COLUMN_NAMES;
	protected String[] COLUMN_TYPES;
	protected int COLUMN_NUMBER;
	
	protected String[] ALL_COLUMNS;

	DatabaseTable(String arg0, String[] arg1, String[] arg2, String[] arg3){
		this.TABLE_NAME = arg0;
		this.COLUMN_NAMES = arg1;
		this.COLUMN_TYPES = arg2;
		this.COLUMN_NUMBER = arg1.length;
		this.ALL_COLUMNS = arg3;
	}
}
