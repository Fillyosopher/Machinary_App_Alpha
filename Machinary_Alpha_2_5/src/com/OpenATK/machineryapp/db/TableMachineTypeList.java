package com.OpenATK.machineryapp.db;

import java.util.Date;

import com.OpenATK.machineryapp.models.MachineTypeList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableMachineTypeList {
	// Database table
		public static final String TABLE_NAME = "lists";
		public static final String COL_ID = "_id";
		public static final String COL_REMOTE_ID = "remote_id";
		public static final String COL_NAME = "name";
		public static final String COL_NAME_CHANGED = "name_changed";
		public static final String COL_ORDER = "place_order";
		public static final String COL_ORDER_CHANGED = "order_changed";
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
		      + COL_ORDER + " integer default 0,"
		      + COL_ORDER_CHANGED + " text,"
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

		public static MachineTypeList cursorToMachineTypeList(Cursor cursor){
			if(cursor != null){
				Integer id = cursor.getInt(cursor.getColumnIndex(TableMachineTypeList.COL_ID));
				String remote_id = cursor.getString(cursor.getColumnIndex(TableMachineTypeList.COL_REMOTE_ID));

				String name = cursor.getString(cursor.getColumnIndex(TableMachineTypeList.COL_NAME));
				Date dateNameChanged = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableMachineTypeList.COL_NAME_CHANGED)));

				Integer order = cursor.getInt(cursor.getColumnIndex(TableMachineTypeList.COL_ORDER));
				Date dateOrderChanged = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableMachineTypeList.COL_ORDER_CHANGED)));

				Boolean deleted = cursor.getInt(cursor.getColumnIndex(TableMachineTypeList.COL_DELETED)) == 1 ? true : false;
				Date dateDeleted = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableMachineTypeList.COL_DELETED_CHANGED)));

				MachineTypeList newMachineTypeList = new MachineTypeList(id, remote_id, name,
						dateNameChanged, order, dateOrderChanged,
					    deleted, dateDeleted);

				return newMachineTypeList;
			} else {
				return null;
			}
		}

		public static MachineTypeList FindMachineTypeListByName(DatabaseHelper dbHelper, String name) {
			if(dbHelper == null) return null;

			if (name != null) {
				SQLiteDatabase database = dbHelper.getReadableDatabase();
				// Find current MachineTypeList
				MachineTypeList theMachineTypeList = null;
				String where = TableMachineTypeList.COL_NAME + " = '" + name + "' AND " + TableMachineTypeList.COL_DELETED + " = 0";
				Cursor cursor = database.query(TableMachineTypeList.TABLE_NAME,
						TableMachineTypeList.COLUMNS, where, null, null, null, null);
				if (cursor.moveToFirst()) {
					theMachineTypeList = TableMachineTypeList.cursorToMachineTypeList(cursor);
				}
				cursor.close();
				database.close();
				dbHelper.close();
				return theMachineTypeList;
			} else {
				return null;
			}
		}

		public static MachineTypeList FindMachineTypeListById(DatabaseHelper dbHelper, Integer id) {
			if(dbHelper == null) return null;

			if (id != null) {
				SQLiteDatabase database = dbHelper.getReadableDatabase();
				// Find current MachineTypeList
				MachineTypeList theMachineTypeList = null;
				String where = TableMachineTypeList.COL_ID + " = " + Integer.toString(id) + " AND " + TableMachineTypeList.COL_DELETED + " = 0";
				Cursor cursor = database.query(TableMachineTypeList.TABLE_NAME,TableMachineTypeList.COLUMNS, where, null, null, null, null);
				if (cursor.moveToFirst()) {
					theMachineTypeList = TableMachineTypeList.cursorToMachineTypeList(cursor);
				}
				cursor.close();
				database.close();
				dbHelper.close();
				return theMachineTypeList;
			} else {
				return null;
			}
		}


		public static boolean updateMachineTypeList(DatabaseHelper dbHelper, MachineTypeList list){
			//Inserts, updates
			//Only non-null lists are updated
			//Used by both LibTrello and MainActivity to update database data

			boolean ret = false;
			SQLiteDatabase database = dbHelper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			if(list.getRemote_id() != null) values.put(TableMachineTypeList.COL_REMOTE_ID, list.getRemote_id());

			if(list.getDateNameChanged() != null) values.put(TableMachineTypeList.COL_NAME_CHANGED, DatabaseHelper.dateToStringUTC(list.getDateNameChanged()));
			if(list.getName() != null) values.put(TableMachineTypeList.COL_NAME, list.getName());

			if(list.getOrder() != null) values.put(TableMachineTypeList.COL_ORDER, list.getOrder());
			if(list.getDateOrderChanged() != null) values.put(TableMachineTypeList.COL_ORDER_CHANGED, DatabaseHelper.dateToStringUTC(list.getDateOrderChanged()));

			if(list.getDeleted() != null) values.put(TableMachineTypeList.COL_DELETED, (list.getDeleted() == false ? 0 : 1));
			if(list.getDateDeleted() != null) values.put(TableMachineTypeList.COL_DELETED_CHANGED, DatabaseHelper.dateToStringUTC(list.getDateDeleted()));


			if(list.getId() == null && (list.getRemote_id() == null || list.getRemote_id().length() == 0)) {
				//INSERT This is a new worker, has no id's
				int id = (int) database.insert(TableMachineTypeList.TABLE_NAME, null, values);
				list.setId(id);
				ret = true;
			} else {
				//UPDATE
				//If have id, lookup by that, it's fastest
				String where;
				if(list.getId() != null){
					where = TableMachineTypeList.COL_ID + " = " + Integer.toString(list.getId());
				} else {
					where = TableMachineTypeList.COL_REMOTE_ID + " = '" + list.getRemote_id() + "'";
				}
				database.update(TableMachineTypeList.TABLE_NAME, values, where, null);
				ret = true;
			}

			database.close();
			dbHelper.close();
			return ret;
		}
}
