package com.Machinery_App.db;

import java.util.ArrayList;
import java.util.List;

import com.Machinery_App.models.List2;
import com.Machinery_App.models.Machine;
import com.Machinery_App.models.Picture;
import com.Machinery_App.models.Service2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CoreDataSource {
	@SuppressWarnings("unused")
	private static final String LOG_TAG = "CoreDataSource";

	// Database fields
	private SQLiteDatabase database;
	private CoreSQLiteHelper helper;

	public CoreDataSource(Context context) {
		helper = new CoreSQLiteHelper(context);
	}
	
	//Database Getters
	private SQLiteDatabase getWritableDatabase(){
		if (database != null) database.close();
		database = helper.getWritableDatabase();
		return database;
	}
	private SQLiteDatabase getReadableDatabase(){
		if (database != null) database.close();
		database = helper.getWritableDatabase();
		return database;
	}
	
	// convert SQLite row to Machine Object
	public Machine cursor2Machine(Cursor cursor) {
		Machine machine = new Machine(
				Long.parseLong(cursor.getString(0)),
				cursor.getString(1), 
				Long.parseLong(cursor.getString(2)), 
				Long.parseLong(cursor.getString(3)), 
				Long.parseLong(cursor.getString(4)),
				Long.parseLong(cursor.getString(5)),
				cursor.getString(6),
				cursor.getString(7),
				cursor.getString(8));
		return machine;
	}

	// Create ContentValues from a Machine object
	public ContentValues getMachineContent(Machine machine){
		ContentValues values = new ContentValues();
		values.put(MachineTable.COLUMN_NAMES[0], machine.getName()); // Machine Name
		values.put(MachineTable.COLUMN_NAMES[1], machine.getList()); // Machine List Number
		values.put(MachineTable.COLUMN_NAMES[2], machine.getYear()); // Machine Modal Year
		values.put(MachineTable.COLUMN_NAMES[3], machine.getLastGrease()); // Machine Last Grease
		values.put(MachineTable.COLUMN_NAMES[4], machine.getLastMaintenance()); // Machine Last Maintenance
		values.put(MachineTable.COLUMN_NAMES[5], machine.getConsumables()); //Machine Consumables
		values.put(MachineTable.COLUMN_NAMES[6], machine.getServiceTableName()); //Machine Consumables
		values.put(MachineTable.COLUMN_NAMES[7], machine.getColor()); // Machine Card Color
		// TODO, add in other columns as needed
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
		Picture picture = new Picture(
				Long.parseLong(cursor.getString(0)),
				cursor.getString(1), 
				cursor.getString(2) );
		return picture;
	}

	// Create ContentValues from a Picture object
	public ContentValues getPictureContent(Picture picture){
		ContentValues values = new ContentValues();
		values.put(PictureTable.COLUMN_NAMES[0], picture.getType()); // Picture Name
		values.put(PictureTable.COLUMN_NAMES[1], picture.getPicture()); // Picture Sort Type
		return values;
	}

	// convert SQLite row to Service Object
	public Service2 cursor2Service(Cursor cursor) {
		Service2 service = new Service2(
				Long.parseLong(cursor.getString(0)),
				cursor.getString(1), 
				Long.parseLong(cursor.getString(2)),
				Long.parseLong(cursor.getString(3)),
				cursor.getString(4));
		return service;
	}

	// Create ContentValues from a Service object
	public ContentValues getServiceContent(DatabaseTable table, Service2 service){
		ContentValues values = new ContentValues();
		values.put(table.COLUMN_NAMES[0], service.getId()); // Service ID
		values.put(table.COLUMN_NAMES[1], service.getName()); // Service Name
		values.put(table.COLUMN_NAMES[2], service.getMachine()); // Machine ID
		values.put(table.COLUMN_NAMES[3], service.getDate()); // Service Date
		values.put(table.COLUMN_NAMES[4], service.getNote()); // Service Notes
		return values;
	}	

	// get the table of a given Service
	public DatabaseTable getServiceTable(Service2 service){
		long machine = service.getMachine();
		DatabaseTable table = new ServiceTable(machine);
		return table;
	}

	// get the table name of a given Service
	public String getServiceTableName(Service2 service){
		long machine = service.getMachine();
		String table = ServiceTable.makeTableName(machine);
		return table;
	}	

	
	// Adding new machine
	public void addMachine(Machine machine) {
		ContentValues values = getMachineContent(machine);
		database = getWritableDatabase();
		
		// Inserting Row
		database.insert(MachineTable.TABLE_NAME, null, values);
		
		//Create Service Table
		machine = getLastMachine();
		long id = machine.getId();
		Log.i(LOG_TAG, "Machine ID gotten as: " + id);
		helper.CreateServiceTable(database, id);
		database.close();
		
		machine.setServiceTableName(ServiceTable.makeTableName(id));
		updateMachine(machine);
	}

	// Getting single machine
	public Machine getMachine(long id) {
		Log.d("getMachine", "getMachine called");
		database = getReadableDatabase();
		Cursor cursor = database.query(
				MachineTable.TABLE_NAME,
				MachineTable.ALL_COLUMNS, 
				MachineTable.COLUMN_ID + "=?",
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

	// Get Last Machine
	public Machine getLastMachine() {
		database = getReadableDatabase();
		Machine machine = new Machine();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + MachineTable.TABLE_NAME;

		Cursor cursor = database.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToLast()) {
			machine = cursor2Machine(cursor);
		}
		
		return machine;		
	}

	// Get All list Names
	public List<String> getAllMachineNames() {
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
	public List<Machine> getMachinesFromList(List2 list) {
		database = getReadableDatabase();
		List<Machine> machineList = new ArrayList<Machine>();
		String sqlQuery = "SELECT * FROM " + MachineTable.TABLE_NAME + " where list = " + list.getId() + " order by " + list.getSortBy();
		Log.i("MachineNames", "List ID: " + list.getId());

		Cursor cursor = database.rawQuery(sqlQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Machine machine = cursor2Machine(cursor);
				// Adding contact to list
				machineList.add(machine);
			} while (cursor.moveToNext());
		}

		return machineList;
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

		// return count
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
		helper.RemoveServiceTable(database, machine.getServiceTableName());
	}


	// Adding new list
	public void addList(List2 list) {
		database = getWritableDatabase();
		ContentValues values = getListContent(list);
		// Inserting Row
		database.insert(ListTable.TABLE_NAME, null, values);
	}

	// Getting single list
	public List2 getList(long id) {
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

	// Get Last Machine
	public List2 getLastList() {
		database = getReadableDatabase();
		List2 list = new List2(0,"debug","debug");
		// Select All Query
		String selectQuery = "SELECT  * FROM " + ListTable.TABLE_NAME;

		Cursor cursor = database.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToLast()) {
			list = cursor2List(cursor);
		}
		
		return list;		
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

		// return count
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
	public void addPicture(Picture picture) {
		database = getWritableDatabase();
		ContentValues values = getPictureContent(picture);
		// Inserting Row
		database.insert(PictureTable.TABLE_NAME, null, values);
	}

	// Getting single Picture
	public Picture getPicture(long id) {
		database = getReadableDatabase();
		Cursor cursor = database.query(
				PictureTable.TABLE_NAME,
				PictureTable.ALL_COLUMNS, 
				PictureTable.COLUMN_ID + "=?",
				new String[] { String.valueOf(id) }, 
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Picture picture = cursor2Picture(cursor);
		// return contact
		return picture;
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


	// Adding new Service
	public void addService(Service2 service) {
		database = getWritableDatabase();
		ContentValues values = getServiceContent(getServiceTable(service), service);
		// Inserting Row
		database.insert(getServiceTableName(service), null, values);
	}

	// Adding new Service
	public void addService(DatabaseTable table, Service2 service) {
		//This line gets the Machine ID from the table name
		service.setMachine(Long.parseLong(table.TABLE_NAME.split("e")[1]));
		addService(service);
	}

	// Getting single Service
	public Service2 getService(DatabaseTable table, long id) {
		database = getReadableDatabase();
		Cursor cursor = database.query(
				table.TABLE_NAME,
				table.ALL_COLUMNS, 
				DatabaseTable.COLUMN_ID + "=?",
				new String[] { String.valueOf(id) }, 
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Service2 service = cursor2Service(cursor);
		// return contact
		return service;
	}

	// Getting All Services
	public List<Service2> getAllServices(DatabaseTable table) {
		return getAllServices(table.TABLE_NAME);
	}

	// Getting All Services
	public List<Service2> getAllServices(String tableName) {
		database = getReadableDatabase();
		List<Service2> listService = new ArrayList<Service2>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + tableName;

		Cursor cursor = database.rawQuery(selectQuery, null);

		// looping through all rows and adding to Picture
		if (cursor.moveToFirst()) {
			do {
				Service2 service = cursor2Service(cursor);
				// Adding contact to Picture
				listService.add(service);
			} while (cursor.moveToNext());
		}

		// return contact picture
		return listService;
	}

	// Get All list Names
	public ArrayList<String> getAllServiceNames(String tableName) {
		database = getReadableDatabase();
		ArrayList<String> nameList = new ArrayList<String>();
		String sqlQuery = "SELECT * FROM " + tableName;

		Cursor cursor = database.rawQuery(sqlQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Service2 service = cursor2Service(cursor);
				// Adding contact to list
				nameList.add(service.getName());
			} while (cursor.moveToNext());
		}

		return nameList;
	}

	// Getting Services Count
	public int getServicesCount(DatabaseTable table) {
		database = getReadableDatabase();
		String countQuery = "SELECT  * FROM " + table.TABLE_NAME;
		Cursor cursor = database.rawQuery(countQuery, null);

		// return count
		return cursor.getCount();
	}

	// Updating single Service
	public int updateService(Service2 service) {
		database = getWritableDatabase();
		ContentValues values = getServiceContent(getServiceTable(service), service);

		// updating row
		return database.update(getServiceTableName(service), values, DatabaseTable.COLUMN_ID + " = ?",
				new String[] { String.valueOf(service.getId()) });
	}

	// Updating single Service
	public void updateService(DatabaseTable table, Service2 service) {
		//This line gets the Machine ID from the table name
		service.setMachine(Long.parseLong(table.TABLE_NAME.split("e")[1]));
		updateService(service);
	}

	// Deleting single Service
	public void deleteService(Service2 service) {
		database = getWritableDatabase();
		database.delete(getServiceTableName(service), DatabaseTable.COLUMN_ID + " = ?",
				new String[] { String.valueOf(service.getId()) });
	}

	// Deleting single Service
	public void deleteService(DatabaseTable table, Service2 service) {
		//This line gets the Machine ID from the table name
		service.setMachine(Long.parseLong(table.TABLE_NAME.split("e")[1]));
		deleteService(service);
	}
};