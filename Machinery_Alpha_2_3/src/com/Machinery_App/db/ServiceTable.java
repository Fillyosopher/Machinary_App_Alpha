package com.Machinery_App.db;

public class ServiceTable extends DatabaseTable {
	protected static final String TABLE_NAME = "Service";

	//CHANGE THESE IF CHANGED LIST CLASS
	protected static final String[] COLUMN_NAMES = {	"Service", "Date", "Note"};
	protected static final String[] COLUMN_TYPES = {	"text not null", "int", "text"};
	
	protected static final String[] ALL_COLUMNS = {	COLUMN_ID, "Service", "Date", "Note"};
	
	public ServiceTable(long tableName){
		super(makeTableName(tableName), COLUMN_NAMES, COLUMN_TYPES, ALL_COLUMNS);
	}
	
	public static String makeTableName(long tableName) {
		return TABLE_NAME + String.valueOf(tableName);
	}
}
