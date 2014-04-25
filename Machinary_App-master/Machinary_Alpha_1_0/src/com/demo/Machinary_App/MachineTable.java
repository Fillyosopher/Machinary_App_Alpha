package com.demo.Machinary_App;

public class MachineTable extends DatabaseTable {
	public static String TABLE_NAME = "machines";

	//CHANGE THESE IF CHANGED MACHINE CLASS
	public static final String[] COLUMN_NAMES = {	"Name", "List", "Model_Year", "Purchase_Year","Last_Grease",
													"Last_Maintenance", "Color", "Oil_Filter_Info","Trans_Filter","Hyd_Filter","Hyd_pump" };
	public static final String[] COLUMN_TYPES = {	"text not null", "integer", "text", "text", "text",
													"text", "text", "text","text","text", "text"};
	public static final String[] ALL_COLUMNS = {	COLUMN_ID, "Name", "List", "Model_Year", "Purchase_Year", "Last_Grease",
													"Last_Maintenance", "Color", "Filter_Info", "Trans_Filter","Hyd_Filter","Hyd_pump"};

	public MachineTable(){
		super(TABLE_NAME, COLUMN_NAMES, COLUMN_TYPES, ALL_COLUMNS);
	}
}
