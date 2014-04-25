package com.demo.Machinary_App;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CoreSQLiteHelper extends SQLiteOpenHelper {
	@SuppressWarnings("unused")
	private static final String LOG_TAG = "CoreSQLiteHelper";
	
	private static final String DATABASE_NAME = "OpenATKmachine.db";
	private static final int DATABASE_VERSION = 30;
	boolean update_flag = false;
	
	public static DatabaseTable[] tables = {new MachineTable(), new ListTable()};
	
	public CoreSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		for (int i = 0; i<tables.length; i++){
			// Database creation SQL statement
			String DATABASE_CREATE = "create table " + tables[i].TABLE_NAME + "(" + DatabaseTable.COLUMN_ID + " integer primary key autoincrement";
			for (int j=0; j<tables[i].COLUMN_NUMBER; j++){
				DATABASE_CREATE += ", " + tables[i].COLUMN_NAMES[j];
				DATABASE_CREATE += " " + tables[i].COLUMN_TYPES[j];
			}
			DATABASE_CREATE += ");";
			
			arg0.execSQL(DATABASE_CREATE);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		Log.w(CoreSQLiteHelper.class.getName(),
				"Upgrading database from version " + arg1 + " to "
				+ arg2 + ", which will destroy all old data");
		for (int i = 0; i<tables.length; i++){
			arg0.execSQL("DROP TABLE IF EXISTS " + tables[i].TABLE_NAME);
		}
		onCreate(arg0);
	}
}
