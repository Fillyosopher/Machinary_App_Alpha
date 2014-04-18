package com.demo.Machinery_App;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MachineSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "machines";
	public static final String COLUMN_ID = "_id";

	//CHANGE THESE IF CHANGED MACHINE CLASS
	public static final String[] COLUMN_NAMES = {	"Name", "List", "Model_Year", "Last_Grease",
													"Last_Maintenance", "Color", "Filter_Info"};
	public static final String[] COLUMN_TYPES = {	"text not null", "integer", "integer", "integer",
													"integer", "text", "text"};
	public static final int COLUMN_NUMBER = 7;
	
	public static final String[] ALL_COLUMNS = {	"_id", "Name", "List", "Model_Year", "Last_Grease",
													"Last_Maintenance", "Color", "Filter_Info"};

	private static final String DATABASE_NAME = "machines.db";
	private static final int DATABASE_VERSION = 5;

	
	public MachineSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		// Database creation sql statement
		String DATABASE_CREATE = "create table " + TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement";
		for (int i=0; i<COLUMN_NUMBER; i++){
			DATABASE_CREATE += ", " + COLUMN_NAMES[i];
			DATABASE_CREATE += " " + COLUMN_TYPES[i];
		}
		DATABASE_CREATE += ");";
		
		database.execSQL(DATABASE_CREATE);
		
		ContentValues values = new ContentValues();
	    values.put(MachineSQLiteHelper.COLUMN_NAMES[0], "Empty Card"); // Machine Name
	    values.put(MachineSQLiteHelper.COLUMN_NAMES[1], 0); // Machine List Number
	    values.put(MachineSQLiteHelper.COLUMN_NAMES[2], 2014); // Machine Modal Year
	    values.put(MachineSQLiteHelper.COLUMN_NAMES[3], 0); // Machine Last Grease
	    values.put(MachineSQLiteHelper.COLUMN_NAMES[4], 0); // Machine Last Maintenance
	    values.put(MachineSQLiteHelper.COLUMN_NAMES[5], "white"); // Machine Card Color
		database.insert(TABLE_NAME, null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO, switch over to separate 'Table' class onUpgrade
		Log.w(MachineSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
} 