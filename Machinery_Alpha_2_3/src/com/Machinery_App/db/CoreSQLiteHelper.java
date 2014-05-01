package com.Machinery_App.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CoreSQLiteHelper extends SQLiteOpenHelper {
	@SuppressWarnings("unused")
	private static final String LOG_TAG = "CoreSQLiteHelper";
	
	private static final String DATABASE_NAME = "OpenATKmachine.db";
	private static final int DATABASE_VERSION = 38;
	
	private static SimpleDateFormat dateFormaterUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
	private static SimpleDateFormat dateFormaterLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
	
	//should be private, fix later, TODO
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
	
	public void CreateServiceTable(SQLiteDatabase arg0, long tableName){
		DatabaseTable table = new ServiceTable(tableName);
		String DATABASE_CREATE = "create table " + table.TABLE_NAME + "(" + DatabaseTable.COLUMN_ID + " integer primary key autoincrement";
		for (int j=0; j<table.COLUMN_NUMBER; j++){
			DATABASE_CREATE += ", " + ServiceTable.COLUMN_NAMES[j];
			DATABASE_CREATE += " " + ServiceTable.COLUMN_TYPES[j];
		}
		DATABASE_CREATE += ");";
		
		arg0.execSQL(DATABASE_CREATE);
	}
	
	public void RemoveServiceTable(SQLiteDatabase arg0, String tableName){
		arg0.execSQL("DROP TABLE IF EXISTS " + tableName);
	}
	
	/*
	 * Takes in a date and returns it in a string format
	 */
	public static String dateToStringUTC(Date date) {
		if(date == null){
			return null;
		}
		return CoreSQLiteHelper.dateFormaterUTC.format(date);
	}

	/*
	 * Takes in a string formated by dateFormat() and returns the
	 * original date.
	 */
	public static Date stringToDateUTC(String date) {
		if(date == null){
			return null;
		}
		Date d;
		try {
			d = CoreSQLiteHelper.dateFormaterUTC.parse(date);
		} catch (ParseException e) {
			d = new Date(0);
		}
		return d;
	}
	/*
	 * Takes in a date and returns it in a string format
	 */
	public static String dateToStringLocal(Date date) {
		if(date == null){
			return null;
		}
		return CoreSQLiteHelper.dateFormaterLocal.format(date);
	}

	/*
	 * Takes in a string formated by dateFormat() and returns the
	 * original date.
	 */
	public static Date stringToDateLocal(String date) {
		if(date == null){
			return null;
		}
		Date d;
		try {
			d = CoreSQLiteHelper.dateFormaterLocal.parse(date);
		} catch (ParseException e) {
			d = new Date(0);
		}
		return d;
	}
}
