package com.demo.Machinary_App;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class MachineDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MachinerySQLiteHelper dbHelper;
	private String[] allColumns = {MachinerySQLiteHelper.COLUMN_ID, MachinerySQLiteHelper.COLUMN_NAMES[0], MachinerySQLiteHelper.COLUMN_NAMES[1], MachinerySQLiteHelper.COLUMN_NAMES[2]};
	
	public MachineDataSource(Context context) {
		dbHelper = new MachinerySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Machine createMachine(String name, int list, String comment) {
		ContentValues values = new ContentValues();
		//edit this with all important values to add to the table
		values.put(allColumns[1], name);
		values.put(allColumns[2], list);
		values.put(allColumns[3], comment);
		
		long insertId = database.insert(MachinerySQLiteHelper.TABLE_NAME, null,
				values);
		
		Cursor cursor = database.query(MachinerySQLiteHelper.TABLE_NAME,
				allColumns, MachinerySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Machine newMachine = cursorToMachine(cursor);
		cursor.close();
		return newMachine;
	}

	public void deleteMachine(Machine machine) {
		long id = machine.getId();
		database.delete(MachinerySQLiteHelper.TABLE_NAME, MachinerySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}
	
	public List<Machine> getAllMachines() {
		List<Machine> machines = new ArrayList<Machine>();
		
		// query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
		Cursor cursor = database.query(MachinerySQLiteHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Machine machine = cursorToMachine(cursor);
			machines.add(machine);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return machines;
	}

	public List<Machine> getMachineByList(int listNum) {
		List<Machine> machines = new ArrayList<Machine>();
		String[] searchColumn = {MachinerySQLiteHelper.COLUMN_ID, "List"};
		
		// query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
		Cursor cursor = database.query(MachinerySQLiteHelper.TABLE_NAME,
				searchColumn, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Machine machine = cursorToMachine(cursor);
			machines.add(machine);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return machines;
	}
	
	public String[] getColumnNames(){		
		return allColumns;
	}

	public String[] getListNames(){	
		//This will break if you move types from column 2
		String selecet = "SELECT DISTINCT " + allColumns[2] + " FROM " + MachinerySQLiteHelper.TABLE_NAME;
		database.rawQuery(selecet, null);
		return allColumns;
	}
	
	private Machine cursorToMachine(Cursor cursor) {
		Machine machine = new Machine();
		machine.setId(cursor.getLong(0));
		machine.setName(cursor.getString(1));
		machine.setComment(cursor.getString(3));
		return machine;
	}
} 
