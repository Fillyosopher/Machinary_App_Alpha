package com.demo.Machinary_App;

public class PictureTable extends DatabaseTable {

	protected static final String TABLE_NAME = "pictures";

	//CHANGE THESE IF CHANGED PICTURE CLASS
	protected static final String[] COLUMN_NAMES = {	"Type", "Data"};
	protected static final String[] COLUMN_TYPES = {	"text not null", "text"};
	
	protected static final String[] ALL_COLUMNS = {	COLUMN_ID, "Type", "Data"};

	public PictureTable(){
		super(TABLE_NAME, COLUMN_NAMES, COLUMN_TYPES, ALL_COLUMNS);
	}
}
