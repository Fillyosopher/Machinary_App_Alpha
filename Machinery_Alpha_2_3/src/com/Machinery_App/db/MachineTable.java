package com.Machinery_App.db;

public class MachineTable extends DatabaseTable {
	protected static final String TABLE_NAME = "machines";

	//CHANGE THESE IF CHANGED MACHINE CLASS
	protected static final String[] COLUMN_NAMES = {	"Name", "List", "Model_Year", "Last_Grease",
													"Last_Maintenance", "Color", "Filter_Info"};
	protected static final String[] COLUMN_TYPES = {	"text not null", "integer", "integer", "integer",
													"integer", "text", "text"};
	protected static final String[] ALL_COLUMNS = {	COLUMN_ID, "Name", "List", "Model_Year", "Last_Grease",
													"Last_Maintenance", "Color", "Filter_Info"};

	public MachineTable(){
		super(TABLE_NAME, COLUMN_NAMES, COLUMN_TYPES, ALL_COLUMNS);
	}
}
