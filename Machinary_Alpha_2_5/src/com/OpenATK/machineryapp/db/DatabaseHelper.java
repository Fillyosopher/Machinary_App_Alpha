package com.OpenATK.machineryapp.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.OpenATK.machineryapp.models.MachineTypeList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "field_work.db";
	private static final int DATABASE_VERSION = 2;

	private static SimpleDateFormat dateFormaterUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
	private static SimpleDateFormat dateFormaterLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);


	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		dateFormaterUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
		dateFormaterLocal.setTimeZone(TimeZone.getDefault());
	}


	@Override
	public void onCreate(SQLiteDatabase database) {
		TableMachineTypeList.onCreate(database);
	}


	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		TableMachineTypeList.onUpgrade(database, oldVersion, newVersion);
	}
	
	/*
	 * Takes in a date and returns it in a string format
	 */
	public static String dateToStringUTC(Date date) {
		if(date == null){
			return null;
		}
		return DatabaseHelper.dateFormaterUTC.format(date);
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
			d = DatabaseHelper.dateFormaterUTC.parse(date);
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
		return DatabaseHelper.dateFormaterLocal.format(date);
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
			d = DatabaseHelper.dateFormaterLocal.parse(date);
		} catch (ParseException e) {
			d = new Date(0);
		}
		return d;
	}
	
	//Functions to read database
		public List<MachineTypeList> readLists(){
			return DatabaseHelper.readLists(this);
		}
		public static List<MachineTypeList> readLists(DatabaseHelper dbHelper){
			List<MachineTypeList> lists = new ArrayList<MachineTypeList>();
			SQLiteDatabase database = dbHelper.getReadableDatabase();

			Cursor cursor = database.query(TableMachineTypeList.TABLE_NAME, TableMachineTypeList.COLUMNS, null, null, null, null, null);
			while (cursor.moveToNext()) {
				lists.add(TableMachineTypeList.cursorToMachineTypeList(cursor));
			}
			cursor.close();

			database.close();
			dbHelper.close();
			return lists;
		}
}
