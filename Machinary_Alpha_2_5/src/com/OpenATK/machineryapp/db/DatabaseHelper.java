package com.OpenATK.machineryapp.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.OpenATK.machineryapp.models.Machine;
import com.OpenATK.machineryapp.models.MachineTypeList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "machinery.db";
	private static final int DATABASE_VERSION = 1;

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
		TableMachine.onCreate(database);
	}


	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		TableMachineTypeList.onUpgrade(database, oldVersion, newVersion);
		TableMachine.onUpgrade(database, oldVersion, newVersion);
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
		MachineTypeList addlist;
		SQLiteDatabase database = dbHelper.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TableMachineTypeList.TABLE_NAME;
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				addlist = TableMachineTypeList.cursorToMachineTypeList(cursor);
				if (addlist.getDeleted()==false){
					lists.add(addlist);
				}
			} while (cursor.moveToNext());
		}
		cursor.close();

		database.close();
		dbHelper.close();
		return lists;
	}
	
	public List<Machine> readMachines(){
		return DatabaseHelper.readMachines(this);
	}
	public static List<Machine> readMachines(DatabaseHelper dbHelper){
		List<Machine> machine = new ArrayList<Machine>();
		Machine addlist;
		SQLiteDatabase database = dbHelper.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TableMachine.TABLE_NAME;
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				addlist = TableMachine.cursorToMachine(cursor);
				if (addlist.getDeleted()==false){
					machine.add(addlist);
				}
			} while (cursor.moveToNext());
		}
		cursor.close();

		database.close();
		dbHelper.close();
		return machine;
	}
	
	public List<Machine> readMachinesOfList(MachineTypeList list){
		return DatabaseHelper.readMachinesOfList(this, list);
	}
	public static List<Machine> readMachinesOfList(DatabaseHelper dbHelper, MachineTypeList list){
		List<Machine> machine = new ArrayList<Machine>();
		Machine addmachine;
		SQLiteDatabase database = dbHelper.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TableMachine.TABLE_NAME + " WHERE " + TableMachine.COL_LIST + " = " + String.valueOf(list.getId());
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				addmachine = TableMachine.cursorToMachine(cursor);
				if (addmachine.getDeleted()==false){
					machine.add(addmachine);
				}
			} while (cursor.moveToNext());
		}
		cursor.close();

		database.close();
		dbHelper.close();
		return machine;
	}

	public List<Machine> readMachinesOfListOrdered(MachineTypeList list, int order, String searched){
		return DatabaseHelper.readMachinesOfListOrdered(this, list, order, searched);
	}
	public static List<Machine> readMachinesOfListOrdered(DatabaseHelper dbHelper, MachineTypeList list, int order, String searched) {
		List<Machine> machine = new ArrayList<Machine>();
		Machine addmachine;
		SQLiteDatabase database = dbHelper.getReadableDatabase();

		String orderBy;
		switch(order){
		case 0:
			orderBy = TableMachine.COL_NAME;
			break;
		case 1:
			orderBy = TableMachine.COL_GREASED_CHANGED;
			break;
		case 2:
			orderBy = TableMachine.COL_MAINTENANCE_CHANGED;
			break;	
		default:
			orderBy = TableMachine.COL_NAME;	
		}
				
		String selectQuery = null;
		if (searched.length() == 0) {
			selectQuery = 
					"SELECT  * FROM " + TableMachine.TABLE_NAME + 
					" WHERE " + TableMachine.COL_LIST + " = '" + String.valueOf(list.getId()) + "'" +
					" ORDER BY " + orderBy + " ASC";
		} else {
			selectQuery = 
					"SELECT  * FROM " + TableMachine.TABLE_NAME + 
					" WHERE " + TableMachine.COL_LIST + " = '" + String.valueOf(list.getId()) + "'" +
					" AND " + TableMachine.COL_NAME + " LIKE '" + searched + "%" + "'" +
					" ORDER BY " + orderBy + " ASC";
		}
		
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				addmachine = TableMachine.cursorToMachine(cursor);
				if (addmachine.getDeleted()==false){
					machine.add(addmachine);
				}
			} while (cursor.moveToNext());
		}
		cursor.close();

		database.close();
		dbHelper.close();
		return machine;
	}
}
