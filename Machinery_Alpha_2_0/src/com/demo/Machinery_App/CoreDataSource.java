package com.demo.Machinery_App;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CoreDataSource {

	// Database fields
	private SQLiteDatabase databaseMachine;
	private SQLiteDatabase databaseList;
	private SQLiteDatabase databasePicture;
	private MachineSQLiteHelper dbMHelper;
	private ListSQLiteHelper dbLHelper;
	private PictureSQLiteHelper dbPHelper;
	
	public CoreDataSource(Context context) {
		dbMHelper = new MachineSQLiteHelper(context);
		dbLHelper = new ListSQLiteHelper(context);
		dbPHelper = new PictureSQLiteHelper(context);
	}

	public void open() throws SQLException {
		databaseMachine = dbMHelper.getWritableDatabase();
		databaseList = dbLHelper.getWritableDatabase();
		databasePicture = dbPHelper.getWritableDatabase();
	}

	public void close() {
		dbMHelper.close();
		dbLHelper.close();
		dbPHelper.close();
	}
	
	// convert SQLite row to Machine Object
	public Machine cursor2Machine(Cursor cursor) {
		Machine machine = new Machine(
	    		Long.parseLong(cursor.getString(0)),
	            cursor.getString(1), 
	            Long.parseLong(cursor.getString(2)), 
	            Integer.parseInt(cursor.getString(3)), 
	            Integer.parseInt(cursor.getString(4)),
	            Integer.parseInt(cursor.getString(5)),
	            cursor.getString(6));
		return machine;
	}
	
	// Create ContentValues from a Machine object
	public ContentValues getMachineContent(Machine machine){
		ContentValues values = new ContentValues();
	    values.put(dbMHelper.COLUMN_NAMES[0], machine.getName()); // Machine Name
	    values.put(dbMHelper.COLUMN_NAMES[1], machine.getList()); // Machine List Number
	    values.put(dbMHelper.COLUMN_NAMES[2], machine.getYear()); // Machine Modal Year
	    values.put(dbMHelper.COLUMN_NAMES[3], machine.getLastGrease()); // Machine Last Grease
	    values.put(dbMHelper.COLUMN_NAMES[4], machine.getLastMaintenance()); // Machine Last Maintenance
	    values.put(dbMHelper.COLUMN_NAMES[5], machine.getColor()); // Machine Card Color
	    // TODO, add in other columns as needed
	    //values.put(dbMHelper.COLUMN_NAMES[6], machine.getList()); // Machine List Number
	    return values;
	}
	
	// convert SQLite row to List Object
	public List2 cursor2List(Cursor cursor) {
		List2 list = new List2(
	    		Long.parseLong(cursor.getString(0)),
	            cursor.getString(1), 
	            cursor.getString(2) );
		return list;
	}
	
	// Create ContentValues from a List object
	public ContentValues getListContent(List2 list){
		ContentValues values = new ContentValues();
	    values.put(dbLHelper.COLUMN_NAMES[0], list.getName()); // List Name
	    values.put(dbLHelper.COLUMN_NAMES[1], list.getSortBy()); // List Sort Type
	    return values;
	}
	
	// convert SQLite row to Picture Object
	public Picture cursor2Picture(Cursor cursor) {
		Picture Picture = new Picture(
	    		Long.parseLong(cursor.getString(0)),
	            cursor.getString(1), 
	            cursor.getString(2) );
		return Picture;
	}
	
	// Create ContentValues from a Picture object
	public ContentValues getPictureContent(Picture Picture){
		ContentValues values = new ContentValues();
	    values.put(dbPHelper.COLUMN_NAMES[0], Picture.getType()); // Picture Name
	    values.put(dbPHelper.COLUMN_NAMES[1], Picture.getPicture()); // Picture Sort Type
	    return values;
	}

	
	// Adding new machine
	public void addMachine(Machine machine) {
	    ContentValues values = getMachineContent(machine);
	    // Inserting Row
	    databaseMachine.insert(dbMHelper.TABLE_NAME, null, values);
	}
	 
	// Getting single machine
	public Machine getMachine(int id) {
	    Cursor cursor = databaseMachine.query(
	    		dbMHelper.TABLE_NAME,
	    		dbMHelper.ALL_COLUMNS, 
	    		dbMHelper.COLUMN_ID + "=?",
	            new String[] { String.valueOf(id) }, 
	            null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Machine machine = cursor2Machine(cursor);
	    // return contact
	    return machine;
	}
	 
	// Getting All Machines
	public List<Machine> getAllMachines() {
		List<Machine> machineList = new ArrayList<Machine>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + dbMHelper.TABLE_NAME;
	 
	    Cursor cursor = databaseMachine.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Machine machine = cursor2Machine(cursor);
	        	// Adding contact to list
	            machineList.add(machine);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return machineList;
	}
	 
	// Getting machines Count
	public int getMachinesCount() {
        String countQuery = "SELECT  * FROM " + dbMHelper.TABLE_NAME;
        Cursor cursor = databaseMachine.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
	}
	
	// Updating single machine
	public int updateMachine(Machine machine) {
		ContentValues values = getMachineContent(machine);
	 
	    // updating row
	    return databaseMachine.update(dbMHelper.TABLE_NAME, values, dbMHelper.COLUMN_ID + " = ?",
	            new String[] { String.valueOf(machine.getId()) });
	}
	 
	// Deleting single machine
	public void deleteMachine(Machine machine) {
	    databaseMachine.delete(dbMHelper.TABLE_NAME, dbMHelper.COLUMN_ID + " = ?",
	            new String[] { String.valueOf(machine.getId()) });
	}
	
	
	// Adding new list
	public void addList(List2 list) {
	    ContentValues values = getListContent(list);
	    // Inserting Row
	    databaseList.insert(dbLHelper.TABLE_NAME, null, values);
	}
	 
	// Getting single list
	public List2 getList(int id) {
	    Cursor cursor = databaseList.query(
	    		dbLHelper.TABLE_NAME,
	    		dbLHelper.ALL_COLUMNS, 
	    		dbLHelper.COLUMN_ID + "=?",
	            new String[] { String.valueOf(id) }, 
	            null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    List2 list = cursor2List(cursor);
	    // return contact
	    return list;
	}
	 
	// Getting All Lists
	public List<List2> getAllLists() {
		List<List2> listList = new ArrayList<List2>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + dbLHelper.TABLE_NAME;
	 
	    Cursor cursor = databaseList.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            List2 list = cursor2List(cursor);
	        	// Adding contact to list
	            listList.add(list);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return listList;
	}
	 
	// Getting lists Count
	public int getListsCount() {
        String countQuery = "SELECT  * FROM " + dbLHelper.TABLE_NAME;
        Cursor cursor = databaseList.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
	}
	
	// Updating single list
	public int updateList(List2 list) {
		ContentValues values = getListContent(list);
	 
	    // updating row
	    return databaseList.update(dbLHelper.TABLE_NAME, values, dbLHelper.COLUMN_ID + " = ?",
	            new String[] { String.valueOf(list.getId()) });
	}
	 
	// Deleting single list
	public void deleteList(List2 list) {
	    databaseList.delete(dbLHelper.TABLE_NAME, dbLHelper.COLUMN_ID + " = ?",
	            new String[] { String.valueOf(list.getId()) });
	}

		
	// Adding new Picture
	public void addPicture(Picture Picture) {
	    ContentValues values = getPictureContent(Picture);
	    // Inserting Row
	    databasePicture.insert(dbPHelper.TABLE_NAME, null, values);
	}
	 
	// Getting single Picture
	public Picture getPicture(int id) {
	    Cursor cursor = databasePicture.query(
	    		dbPHelper.TABLE_NAME,
	    		dbPHelper.ALL_COLUMNS, 
	    		dbPHelper.COLUMN_ID + "=?",
	            new String[] { String.valueOf(id) }, 
	            null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Picture Picture = cursor2Picture(cursor);
	    // return contact
	    return Picture;
	}
	 
	// Getting All Pictures
	public List<Picture> getAllPictures() {
		List<Picture> listPicture = new ArrayList<Picture>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + dbPHelper.TABLE_NAME;
	 
	    Cursor cursor = databasePicture.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to Picture
	    if (cursor.moveToFirst()) {
	        do {
	            Picture picture = cursor2Picture(cursor);
	        	// Adding contact to Picture
	            listPicture.add(picture);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact picture
	    return listPicture;
	}
	 
	// Getting Pictures Count
	public int getPicturesCount() {
        String countQuery = "SELECT  * FROM " + dbPHelper.TABLE_NAME;
        Cursor cursor = databasePicture.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
	}
	
	// Updating single Picture
	public int updatePicture(Picture picture) {
		ContentValues values = getPictureContent(picture);
	 
	    // updating row
	    return databasePicture.update(dbPHelper.TABLE_NAME, values, dbPHelper.COLUMN_ID + " = ?",
	            new String[] { String.valueOf(picture.getId()) });
	}
	 
	// Deleting single picture
	public void deletePicture(Picture picture) {
	    databasePicture.delete(dbPHelper.TABLE_NAME, dbPHelper.COLUMN_ID + " = ?",
	            new String[] { String.valueOf(picture.getId()) });
	}
};