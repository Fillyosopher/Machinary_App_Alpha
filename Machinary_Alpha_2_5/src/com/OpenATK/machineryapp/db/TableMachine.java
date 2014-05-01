package com.OpenATK.machineryapp.db;

import java.util.Date;

import com.OpenATK.machineryapp.models.Machine;
import com.OpenATK.machineryapp.models.MachineTypeList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableMachine {
	// Database table
		public static final String TABLE_NAME = "machines";
		public static final String COL_ID = "_id";
		public static final String COL_REMOTE_ID = "remote_id";
		public static final String COL_NAME = "name";
		public static final String COL_NAME_CHANGED = "name_changed";
		public static final String COL_LIST = "list";
		public static final String COL_LIST_CHANGED = "list_changed";
		public static final String COL_YEAR = "year";
		public static final String COL_YEAR_CHANGED = "year_changed";
		public static final String COL_ORDER = "place_order";
		public static final String COL_ORDER_CHANGED = "order_changed";
		public static final String COL_GREASED = "greased";
		public static final String COL_GREASED_CHANGED = "greased_changed";
		public static final String COL_MAINTENANCE = "maintenance";
		public static final String COL_MAINTENANCE_CHANGED = "maintenance_changed";
		public static final String COL_MAINTENANCE_TABLE_NAME = "maintenance_table_name";
		public static final String COL_DELETED = "deleted";
		public static final String COL_DELETED_CHANGED = "deleted_changed";


		public static String[] COLUMNS = { COL_ID, COL_REMOTE_ID, COL_NAME, 
			COL_NAME_CHANGED, COL_ORDER, COL_ORDER_CHANGED, COL_DELETED, 
			COL_DELETED_CHANGED };

		// Database creation SQL statement
		private static final String DATABASE_CREATE = "create table " 
		      + TABLE_NAME
		      + "(" 
		      + COL_ID + " integer primary key autoincrement," 
		      + COL_REMOTE_ID + " text default ''," 
		      + COL_NAME + " text," 
		      + COL_NAME_CHANGED + " text,"
		      + COL_LIST + " integer," 
		      + COL_LIST_CHANGED + " text,"
		      + COL_YEAR + " text," 
		      + COL_YEAR_CHANGED + " text,"
		      + COL_ORDER + " integer default 0,"
		      + COL_ORDER_CHANGED + " text,"
		      + COL_GREASED + " text," 
		      + COL_GREASED_CHANGED + " text,"
		      + COL_MAINTENANCE + " text," 
		      + COL_MAINTENANCE_CHANGED + " text,"
		      + COL_MAINTENANCE_TABLE_NAME + " text,"
		      + COL_DELETED + " integer default 0,"
		      + COL_DELETED_CHANGED + " text"
		      + ");";

		public static void onCreate(SQLiteDatabase database) {
		  database.execSQL(DATABASE_CREATE);
		}

		//TODO handle upgrade
		public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			Log.d("TableFields - onUpgrade", "Upgrade from " + Integer.toString(oldVersion) + " to " + Integer.toString(newVersion));
	    	int version = oldVersion;
	    	switch(version){
	    		case 1: //Launch
	    			//Do nothing this is the launch version
	    		case 2: //Implement on database version 2
	    	}
		    //database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		    //onCreate(database);
		}

		public static Machine cursorToMachine(Cursor cursor){
			if(cursor != null){
				Integer id = cursor.getInt(cursor.getColumnIndex(TableMachine.COL_ID));
				String remote_id = cursor.getString(cursor.getColumnIndex(TableMachine.COL_REMOTE_ID));

				String name = cursor.getString(cursor.getColumnIndex(TableMachine.COL_NAME));
				Date dateNameChanged = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableMachine.COL_NAME_CHANGED)));

				Integer list = cursor.getInt(cursor.getColumnIndex(TableMachine.COL_LIST));
				Date dateListChanged = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableMachine.COL_LIST_CHANGED)));

				String year = cursor.getString(cursor.getColumnIndex(TableMachine.COL_YEAR));
				Date dateYearChanged = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableMachine.COL_YEAR_CHANGED)));
				
				Integer order = cursor.getInt(cursor.getColumnIndex(TableMachine.COL_ORDER));
				Date dateOrderChanged = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableMachine.COL_ORDER_CHANGED)));

				String greased = cursor.getString(cursor.getColumnIndex(TableMachine.COL_GREASED));
				Date dateGreasedChanged = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableMachine.COL_GREASED_CHANGED)));
				
				String maintenance = cursor.getString(cursor.getColumnIndex(TableMachine.COL_MAINTENANCE));
				Date dateMaintenanceChanged = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableMachine.COL_MAINTENANCE_CHANGED)));
				String maintenanceTableName = cursor.getString(cursor.getColumnIndex(TableMachine.COL_MAINTENANCE_TABLE_NAME));
				
				Boolean deleted = cursor.getInt(cursor.getColumnIndex(TableMachine.COL_DELETED)) == 1 ? true : false;
				Date dateDeleted = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableMachine.COL_DELETED_CHANGED)));

				Machine newMachine = new Machine(id, remote_id, name, dateNameChanged, list, dateListChanged, year, dateYearChanged, order, 
						dateOrderChanged, greased, dateGreasedChanged, maintenance, dateMaintenanceChanged,	maintenanceTableName, deleted, dateDeleted);

				return newMachine;
			} else {
				return null;
			}
		}

		public static Machine FindMachineByName(DatabaseHelper dbHelper, String name) {
			if(dbHelper == null) return null;

			if (name != null) {
				SQLiteDatabase database = dbHelper.getReadableDatabase();
				// Find current MachineTypeList
				Machine theMachine = null;
				String where = TableMachine.COL_NAME + " = '" + name + "' AND " + TableMachine.COL_DELETED + " = 0";
				Cursor cursor = database.query(TableMachine.TABLE_NAME,
						TableMachine.COLUMNS, where, null, null, null, null);
				if (cursor.moveToFirst()) {
					theMachine = TableMachine.cursorToMachine(cursor);
				}
				cursor.close();
				database.close();
				dbHelper.close();
				return theMachine;
			} else {
				return null;
			}
		}

		public static Machine FindMachineById(DatabaseHelper dbHelper, Integer id) {
			if(dbHelper == null) return null;

			if (id != null) {
				SQLiteDatabase database = dbHelper.getReadableDatabase();
				// Find current MachineTypeList
				Machine theMachine = null;
				String where = TableMachine.COL_ID + " = " + Integer.toString(id) + " AND " + TableMachine.COL_DELETED + " = 0";
				Cursor cursor = database.query(TableMachine.TABLE_NAME,TableMachine.COLUMNS, where, null, null, null, null);
				if (cursor.moveToFirst()) {
					theMachine = TableMachine.cursorToMachine(cursor);
				}
				cursor.close();
				database.close();
				dbHelper.close();
				return theMachine;
			} else {
				return null;
			}
		}


		public static boolean updateMachine(DatabaseHelper dbHelper, Machine machine){
			//Inserts, updates
			//Only non-null lists are updated
			//Used by both LibTrello and MainActivity to update database data

			boolean ret = false;
			SQLiteDatabase database = dbHelper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			if(machine.getRemote_id() != null) values.put(TableMachine.COL_REMOTE_ID, machine.getRemote_id());

			if(machine.getName() != null) values.put(TableMachine.COL_NAME, machine.getName());
			if(machine.getDateNameChanged() != null) values.put(TableMachine.COL_NAME_CHANGED, DatabaseHelper.dateToStringUTC(machine.getDateNameChanged()));

			if(machine.getYear() != null) values.put(TableMachine.COL_YEAR, machine.getYear());
			if(machine.getDateYearChanged() != null) values.put(TableMachine.COL_YEAR_CHANGED, DatabaseHelper.dateToStringUTC(machine.getDateYearChanged()));
			
			if(machine.getList() != null) values.put(TableMachine.COL_LIST, machine.getList());
			if(machine.getDateListChanged() != null) values.put(TableMachine.COL_LIST_CHANGED, DatabaseHelper.dateToStringUTC(machine.getDateListChanged()));
			
			if(machine.getOrder() != null) values.put(TableMachine.COL_ORDER, machine.getOrder());
			if(machine.getDateOrderChanged() != null) values.put(TableMachine.COL_ORDER_CHANGED, DatabaseHelper.dateToStringUTC(machine.getDateOrderChanged()));

			if(machine.getGreased() != null) values.put(TableMachine.COL_GREASED, machine.getGreased());
			if(machine.getDateGreasedChanged() != null) values.put(TableMachine.COL_GREASED_CHANGED, DatabaseHelper.dateToStringUTC(machine.getDateGreasedChanged()));

			if(machine.getMaintenance() != null) values.put(TableMachine.COL_MAINTENANCE, machine.getMaintenance());
			if(machine.getDateMaintenanceChanged() != null) values.put(TableMachine.COL_MAINTENANCE_CHANGED, DatabaseHelper.dateToStringUTC(machine.getDateMaintenanceChanged()));
			if(machine.getMaintenanceTableName() != null) values.put(TableMachine.COL_MAINTENANCE_TABLE_NAME, machine.getMaintenanceTableName());

			if(machine.getDeleted() != null) values.put(TableMachine.COL_DELETED, (machine.getDeleted() == false ? 0 : 1));
			if(machine.getDateDeleted() != null) values.put(TableMachine.COL_DELETED_CHANGED, DatabaseHelper.dateToStringUTC(machine.getDateDeleted()));


			if(machine.getId() == null && (machine.getRemote_id() == null || machine.getRemote_id().length() == 0)) {
				//INSERT This is a new worker, has no id's
				int id = (int) database.insert(TableMachine.TABLE_NAME, null, values);
				machine.setId(id);
				ret = true;
			} else {
				//UPDATE
				//If have id, lookup by that, it's fastest
				String where;
				if(machine.getId() != null){
					where = TableMachine.COL_ID + " = " + Integer.toString(machine.getId());
				} else {
					where = TableMachine.COL_REMOTE_ID + " = '" + machine.getRemote_id() + "'";
				}
				database.update(TableMachine.TABLE_NAME, values, where, null);
				ret = true;
			}

			database.close();
			dbHelper.close();
			return ret;
		}
}
