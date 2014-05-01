package com.Machinery_App.db;

public class ListTable extends DatabaseTable {
	//this should be private, fix later, TODO
	public static final String TABLE_NAME = "lists";

	//CHANGE THESE IF CHANGED LIST CLASS
	protected static final String[] COLUMN_NAMES = {	"Name", "Sorting"};
	protected static final String[] COLUMN_TYPES = {	"text", "text"};
	
	protected static final String[] ALL_COLUMNS = {	COLUMN_ID, "Name", "Sorting"};
	
	public ListTable(){
		super(TABLE_NAME, COLUMN_NAMES, COLUMN_TYPES, ALL_COLUMNS);
	}
}
