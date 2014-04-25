package com.demo.Machinary_App;

public class DatabaseTable {
	protected String TABLE_NAME;

	public static final String COLUMN_ID = "_id";

	protected String[] COLUMN_NAMES;
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
