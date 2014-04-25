package com.demo.Machinary_App;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ListSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "lists";
	public static final String COLUMN_ID = "_id";

	//CHANGE THESE IF CHANGED LIST CLASS
	public static final String[] COLUMN_NAMES = {	"Name", "Sorting"};
	public static final String[] COLUMN_TYPES = {	"text", "text"};
	public static final int COLUMN_NUMBER = 2;

	private static final String DATABASE_NAME = "lists.db";
	private static final int DATABASE_VERSION = 1;

	public static final String[] ALL_COLUMNS = {	"_id", "Name", "Sorting"};

	public ListSQLiteHelper(Context context) {
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