package com.OpenATK.machineryapp.db;

import java.util.Date;

import com.OpenATK.machineryapp.models.Machine;
import com.OpenATK.machineryapp.models.MachineTypeList;
import com.OpenATK.machineryapp.models.Maintenance;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableMaintenance {
	// Database table
		private static String tableName;
		public static final String COL_ID = "_id";
		public static final String COL_REMOTE_ID = "remote_id";
		public static final String COL_NAME = "name";
		public static final String COL_NAME_CHANGED = "name_changed";
		public static final String COL_NOTE = "note";
		public static final String COL_NOTE_CHANGED = "note_changed";
		public static final String COL_DELETED = "deleted";
		public static final String COL_DELETED_CHANGED = "deleted_changed";


		public static String[] COL_LIST = { COL_ID, COL_REMOTE_ID, COL_NAME, 
			COL_NAME_CHANGED, COL_NOTE, COL_NOTE_CHANGED, COL_DELETED, 
			COL_DELETED_CHANGED };

		public static void onCreate(SQLiteDatabase database, Machine machine) {
			tableName = machine.getMaintenanceTableName();
			
			// Database creation SQL statement
			String DATABASE_CREATE = "create table " 
				      + tableName
				      + "(" 
				      + COL_ID + " integer primary key autoincrement," 
				      + COL_REMOTE_ID + " text default ''," 
				      + COL_NAME + " text," 
				      + COL_NAME_CHANGED + " text,"
				      + COL_NOTE + " text,"
				      + COL_NOTE_CHANGED + " text,"
				      + COL_DELETED + " integer default 0,"
				      + COL_DELETED_CHANGED + " text"
				      + ");";
		  database.execSQL(DATABASE_CREATE);
		}

		//TODO handle upgrade
		public static void onUpgrade(SQLiteDatabase database, Machine machine, int oldVersion, int newVersion) {
			tableName = machine.getMaintenanceTableName();
			Log.d("TableMaintenance - onUpgrade", "Upgrade from " + Integer.toString(oldVersion) + " to " + Integer.toString(newVersion));
	    	int version = oldVersion;
	    	switch(version){
	    		case 1: //Launch
	    			//Do nothing this is the launch version
	    		case 2: //Implement on database version 2
	    	}
		    //database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		    //onCreate(database);
		}

		public static Maintenance cursorToMaintenance(Cursor cursor){
			if(cursor != null){
				Integer id = cursor.getInt(cursor.getColumnIndex(TableMaintenance.COL_ID));
				String remote_id = cursor.getString(cursor.getColumnIndex(TableMaintenance.COL_REMOTE_ID));

				String name = cursor.getString(cursor.getColumnIndex(TableMaintenance.COL_NAME));
				Date dateNameChanged = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableMaintenance.COL_NAME_CHANGED)));

				String note = cursor.getString(cursor.getColumnIndex(TableMaintenance.COL_NOTE));
				Date dateNoteChanged = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableMaintenance.COL_NOTE_CHANGED)));

				Boolean deleted = cursor.getInt(cursor.getColumnIndex(TableMaintenance.COL_DELETED)) == 1 ? true : false;
				Date dateDeleted = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableMaintenance.COL_DELETED_CHANGED)));

				Maintenance newMaintenance = new Maintenance(id, remote_id, name,
						dateNameChanged, note, dateNoteChanged,
					    deleted, dateDeleted);

				return newMaintenance;
			} else {
				return null;
			}
		}

		public static Maintenance FindMaintenanceByName(DatabaseHelper dbHelper, Machine machine, String name) {
			tableName = machine.getMaintenanceTableName();
			if(dbHelper == null) return null;

			if (name != null) {
				SQLiteDatabase database = dbHelper.getReadableDatabase();
				// Find current Maintenance
				Maintenance theMaintenance = null;
				String where = TableMaintenance.COL_NAME + " = '" + name + "' AND " + TableMaintenance.COL_DELETED + " = 0";
				Cursor cursor = database.query(tableName,
						TableMaintenance.COL_LIST, where, null, null, null, null);
				if (cursor.moveToFirst()) {
					theMaintenance = TableMaintenance.cursorToMaintenance(cursor);
				}
				cursor.close();
				database.close();
				dbHelper.close();
				return theMaintenance;
			} else {
				return null;
			}
		}

		public static Maintenance FindMaintenanceById(DatabaseHelper dbHelper, Machine machine, Integer id) {
			tableName = machine.getMaintenanceTableName();
			if(dbHelper == null) return null;

			if (id != null) {
				SQLiteDatabase database = dbHelper.getReadableDatabase();
				// Find current MachineTypeList
				Maintenance theMaintenance = null;
				String where = TableMaintenance.COL_ID + " = " + Integer.toString(id) + " AND " + TableMaintenance.COL_DELETED + " = 0";
				Cursor cursor = database.query(tableName,TableMaintenance.COL_LIST, where, null, null, null, null);
				if (cursor.moveToFirst()) {
					theMaintenance = TableMaintenance.cursorToMaintenance(cursor);
				}
				cursor.close();
				database.close();
				dbHelper.close();
				return theMaintenance;
			} else {
				return null;
			}
		}

		public static boolean updateMaintenance(DatabaseHelper dbHelper, Machine machine, Maintenance maintenance){
			//Inserts, updates
			//Only non-null lists are updated
			//Used by both LibTrello and MainActivity to update database data
			
			tableName = machine.getMaintenanceTableName();
			
			boolean ret = false;
			SQLiteDatabase database = dbHelper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			if(maintenance.getRemote_id() != null) values.put(TableMaintenance.COL_REMOTE_ID, maintenance.getRemote_id());

			if(maintenance.getDateNameChanged() != null) values.put(TableMaintenance.COL_NAME_CHANGED, DatabaseHelper.dateToStringUTC(maintenance.getDateNameChanged()));
			if(maintenance.getName() != null) values.put(TableMaintenance.COL_NAME, maintenance.getName());

			if(maintenance.getNote() != null) values.put(TableMaintenance.COL_NOTE, maintenance.getNote());
			if(maintenance.getDateNoteChanged() != null) values.put(TableMaintenance.COL_NOTE_CHANGED, DatabaseHelper.dateToStringUTC(maintenance.getDateNoteChanged()));

			if(maintenance.getDeleted() != null) values.put(TableMaintenance.COL_DELETED, (maintenance.getDeleted() == false ? 0 : 1));
			if(maintenance.getDateDeleted() != null) values.put(TableMaintenance.COL_DELETED_CHANGED, DatabaseHelper.dateToStringUTC(maintenance.getDateDeleted()));


			if(maintenance.getId() == null && (maintenance.getRemote_id() == null || maintenance.getRemote_id().length() == 0)) {
				//INSERT This is a new maintenance, has no id's
				int id = (int) database.insert(tableName, null, values);
				maintenance.setId(id);
				ret = true;
			} else {
				//UPDATE
				//If have id, lookup by that, it's fastest
				String where;
				if(maintenance.getId() != null){
					where = TableMaintenance.COL_ID + " = " + Integer.toString(maintenance.getId());
				} else {
					where = TableMaintenance.COL_REMOTE_ID + " = '" + maintenance.getRemote_id() + "'";
				}
				database.update(tableName, values, where, null);
				ret = true;
			}

			database.close();
			dbHelper.close();
			return ret;
		}
}
