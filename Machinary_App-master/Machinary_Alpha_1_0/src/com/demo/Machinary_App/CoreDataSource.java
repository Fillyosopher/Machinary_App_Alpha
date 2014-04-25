package com.demo.Machinary_App;

import java.util.ArrayList;
import java.util.List;

import com.demo.Machinary_App.*;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CoreDataSource {
	@SuppressWarnings("unused")
	private static final String LOG_TAG = "CoreDataSource";
	
	// Database fields
	public SQLiteDatabase database;
	public CoreSQLiteHelper helper;
	
	private boolean readEnable = false;
	private boolean writeEnable = false;
	
	public CoreDataSource(Context context) {
		helper = new CoreSQLiteHelper(context);
	}

	public void open() {
		if(readEnable){
			database=getReadableDatabase();
		}
		if(writeEnable){
			database=getWritableDatabase();
		}
	}
	
	public void close(){
		if(readEnable || writeEnable){
			database.close();
		}
	}
	
	public boolean updateCheck(){
		return helper.update_flag;
	}
	
	// convert SQLite row to Machine Object
	public Machine cursor2Machine(Cursor cursor) {
		Machine machine = new Machine(
	    		Long.parseLong(cursor.getString(0)),
	    		cursor.getString(1), 
	            cursor.getString(2), 
	            Integer.parseInt(cursor.getString(3)), 
	            cursor.getString(4),
	            cursor.getString(5),
	            cursor.getString(6),
	            cursor.getString(7),
	            cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11)
	         );
		return machine;
	}
	
	// Create ContentValues from a Machine object
	public ContentValues getMachineContent(Machine machine){
		ContentValues values = new ContentValues();
	    values.put(MachineTable.COLUMN_NAMES[0], machine.getName()); // Machine Name
	    values.put(MachineTable.COLUMN_NAMES[1], machine.getList()); // Machine List Number
	    values.put(MachineTable.COLUMN_NAMES[2], machine.getYear()); // Machine Modal Year
	    values.put(MachineTable.COLUMN_NAMES[3], machine.getPurchaseDate());//Machine Purchase Date
	    values.put(MachineTable.COLUMN_NAMES[4], machine.getLastGrease()); // Machine Last Grease
	    values.put(MachineTable.COLUMN_NAMES[5], machine.getLastMaintenance()); // Machine Last Maintenance
	    values.put(MachineTable.COLUMN_NAMES[6], machine.getColor()); // Machine Card Color
	    values.put(MachineTable.COLUMN_NAMES[7], machine.getoilfilter());
	    values.put(MachineTable.COLUMN_NAMES[8], machine.gettransfilter());
	    values.put(MachineTable.COLUMN_NAMES[9], machine.gethydfilter());
	    values.put(MachineTable.COLUMN_NAMES[10], machine.gethydpump());
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
	    values.put(ListTable.COLUMN_NAMES[0], list.getName()); // List Name
	    values.put(ListTable.COLUMN_NAMES[1], list.getSortBy()); // List Sort Type
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
	    values.put(PictureTable.COLUMN_NAMES[0], Picture.getType()); // Picture Name
	    values.put(PictureTable.COLUMN_NAMES[1], Picture.getPicture()); // Picture Sort Type
	    return values;
	}
	
	
	//Database Getters
	public SQLiteDatabase getWritableDatabase(){
		if(!writeEnable){
	    	if(readEnable){
	    		database.close();
	    		readEnable = false;
	    	}
	    	database = helper.getWritableDatabase();
	    }
		writeEnable = true;
		return database;
	}
	public SQLiteDatabase getReadableDatabase(){
		if(!readEnable){
	    	if(writeEnable){
	    		database.close();
	    		writeEnable = false;
	    	}
	    	database = helper.getReadableDatabase();
	    }
		readEnable = true;
		return database;
	}
	
	
	// Adding new machine
	public void addMachine(Machine machine) {
	    ContentValues values = getMachineContent(machine);
	    database = getWritableDatabase();
	    // Inserting Row
	    database.insert(MachineTable.TABLE_NAME, null, values);
	}
	 
	// Getting single machine
	public Machine getMachine(int id) {
		database = getReadableDatabase();
	    Cursor cursor = database.query(
	    		MachineTable.TABLE_NAME,
	    		MachineTable.ALL_COLUMNS, 
	    		MachineTable.COLUMN_ID + "=?",
	            new String[] { String.valueOf(id) }, 
	            null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Machine machine = cursor2Machine(cursor);
	    // return contact
	    return machine;
	}
	 
	// Getting All Machines
	public List<Machine> getAllMachines() {
		database = getReadableDatabase();
		List<Machine> machineList = new ArrayList<Machine>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + MachineTable.TABLE_NAME;
	    
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    
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

	// Get All list Names
	public List<String> getMachineNames() {
		database = getReadableDatabase();
		List<String> nameList = new ArrayList<String>();
		String sqlQuery = "SELECT * FROM " + MachineTable.TABLE_NAME;

		Cursor cursor = database.rawQuery(sqlQuery, null);
		
	    if (cursor.moveToFirst()) {
	    	do {
	            Machine machine = cursor2Machine(cursor);
	        	// Adding contact to list
	            nameList.add(machine.getName());
	        } while (cursor.moveToNext());
	    }
		
	    return nameList;
	}	 

	// Getting Machine Carddata
	public List<String> getMachineNamesFromList(List2 list) {
		database = getReadableDatabase();
		List<String> nameList = new ArrayList<String>();
		String sqlQuery = "SELECT * FROM " + MachineTable.TABLE_NAME + " where list = " + list.getId() + " order by " + list.getSortBy();
		Log.i("MachineNames", "List ID: " + list.getId());
		
		Cursor cursor = database.rawQuery(sqlQuery, null);
		
	    if (cursor.moveToFirst()) {
	    	do {
	            Machine machine = cursor2Machine(cursor);
	        	// Adding contact to list
	            nameList.add(machine.getName());
	        } while (cursor.moveToNext());
	    }
		
	    return nameList;
	}
	
	// Getting machines Count
	public int getMachinesCount() {
		database = getReadableDatabase();
        String countQuery = "SELECT  * FROM " + MachineTable.TABLE_NAME;
        Cursor cursor = database.rawQuery(countQuery, null);
        return cursor.getCount();
	}
	
	// Updating single machine
	public int updateMachine(Machine machine) {
		database = getWritableDatabase();
		ContentValues values = getMachineContent(machine);
	 
	    // updating row
	    return database.update(MachineTable.TABLE_NAME, values, MachineTable.COLUMN_ID + " = ?",
	            new String[] { String.valueOf(machine.getId()) });
	}
	 
	// Deleting single machine
	public void deleteMachine(Machine machine) {
		database = getWritableDatabase();
		database.delete(MachineTable.TABLE_NAME, MachineTable.COLUMN_ID + " = ?",
	            new String[] { String.valueOf(machine.getId()) });
	}
	
	
	// Adding new list
	public void addList(List2 list) {
		database = getWritableDatabase();
		ContentValues values = getListContent(list);
	    // Inserting Row
	    database.insert(ListTable.TABLE_NAME, null, values);
	}
	 
	// Getting single list
	public List2 getList(int id) {
		database = getReadableDatabase();
		Cursor cursor = database.query(
	    		ListTable.TABLE_NAME,
	    		ListTable.ALL_COLUMNS, 
	    		ListTable.COLUMN_ID + "=?",
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
		database = getReadableDatabase();
		List<List2> listList = new ArrayList<List2>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + ListTable.TABLE_NAME;
	 
	    Cursor cursor = database.rawQuery(selectQuery, null);
	 
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
	 
	// Get All list Names
	public List<String> getListNames() {
		database = getReadableDatabase();
		List<String> nameList = new ArrayList<String>();
		String sqlQuery = "SELECT * FROM " + ListTable.TABLE_NAME;

		Cursor cursor = database.rawQuery(sqlQuery, null);
		
	    if (cursor.moveToFirst()) {
	    	do {
	            List2 machine = cursor2List(cursor);
	        	// Adding contact to list
	            nameList.add(machine.getName());
	        } while (cursor.moveToNext());
	    }
		
	    return nameList;
	}
	
	// Getting lists Count
	public int getListsCount() {
		database = getReadableDatabase();
        String countQuery = "SELECT  * FROM " + ListTable.TABLE_NAME;
        Cursor cursor = database.rawQuery(countQuery, null);
        return cursor.getCount();
	}
	
	// Updating single list
	public int updateList(List2 list) {
		database = getWritableDatabase();
		ContentValues values = getListContent(list);
	 
	    // updating row
	    return database.update(ListTable.TABLE_NAME, values, ListTable.COLUMN_ID + " = ?",
	            new String[] { String.valueOf(list.getId()) });
	}
	 
	// Deleting single list
	public void deleteList(List2 list) {
		database = getWritableDatabase();
		database.delete(ListTable.TABLE_NAME, ListTable.COLUMN_ID + " = ?",
	            new String[] { String.valueOf(list.getId()) });
	}

		
	// Adding new Picture
	public void addPicture(Picture Picture) {
		database = getWritableDatabase();
		ContentValues values = getPictureContent(Picture);
	    // Inserting Row
	    database.insert(PictureTable.TABLE_NAME, null, values);
	}
	 
	// Getting single Picture
	public Picture getPicture(int id) {
		database = getReadableDatabase();
	    Cursor cursor = database.query(
	    		PictureTable.TABLE_NAME,
	    		PictureTable.ALL_COLUMNS, 
	    		PictureTable.COLUMN_ID + "=?",
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
		database = getReadableDatabase();
		List<Picture> listPicture = new ArrayList<Picture>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + PictureTable.TABLE_NAME;
	 
	    Cursor cursor = database.rawQuery(selectQuery, null);
	 
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
		database = getReadableDatabase();
		String countQuery = "SELECT  * FROM " + PictureTable.TABLE_NAME;
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
	}
	
	// Updating single Picture
	public int updatePicture(Picture picture) {
		database = getWritableDatabase();
		ContentValues values = getPictureContent(picture);
	 
	    // updating row
	    return database.update(PictureTable.TABLE_NAME, values, PictureTable.COLUMN_ID + " = ?",
	            new String[] { String.valueOf(picture.getId()) });
	}
	 
	// Deleting single picture
	public void deletePicture(Picture picture) {
		database = getWritableDatabase();
		database.delete(PictureTable.TABLE_NAME, PictureTable.COLUMN_ID + " = ?",
	            new String[] { String.valueOf(picture.getId()) });
	}
};