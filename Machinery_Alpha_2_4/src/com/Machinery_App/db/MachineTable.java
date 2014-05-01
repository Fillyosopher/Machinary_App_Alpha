package com.Machinery_App.db;

public class MachineTable extends DatabaseTable {
	//this should be private, fix later, TODO
	public static final String TABLE_NAME = "machines";

	//CHANGE THESE IF CHANGED MACHINE CLASS
	protected static final String[] COLUMN_NAMES = {	"Name", "List", "Model_Year", "Last_Grease",
													"Last_Maintenance", "Consumables", "ServiceTableName",
													"Color"};
	protected static final String[] COLUMN_TYPES = {	"text not null", "integer", "integer", "integer", 
													"integer", "text", "text",
													"text"};
	
	protected static final String[] ALL_COLUMNS = {	COLUMN_ID, "Name", "List", "Model_Year", "Last_Grease",
													"Last_Maintenance", "Consumables", "ServiceTableName",
													"Color"};

	public MachineTable(){
		super(TABLE_NAME, COLUMN_NAMES, COLUMN_TYPES, ALL_COLUMNS);
	}
}
