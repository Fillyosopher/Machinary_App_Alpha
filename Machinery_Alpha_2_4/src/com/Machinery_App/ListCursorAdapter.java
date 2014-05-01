package com.Machinery_App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.Machinery_App.db.CoreDataSource;
import com.Machinery_App.db.MachineTable;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListCursorAdapter extends CursorAdapter {
	private Context context;
	
	private CoreDataSource datasource;
	
	private LayoutInflater mInflater;
	
	private Cursor cursor1;

	private HashMap<Integer,Cursor> cursors = new HashMap<Integer,Cursor>();
	//private List<MachineCursorAdapter> adapters = new ArrayList<MachineCursorAdapter>();
	private HashMap<Integer,Button> buttons = new HashMap<Integer,Button>();
	
	private TextView name;
	private ListView listview;
	
	private Button addButton;
	private Button removeButton;
	
	public ListCursorAdapter(Context context, Cursor c, CoreDataSource datasource) {
		super(context, c, 0);
		//this.context = context;
		this.datasource = datasource;
		Log.i("LCA.OnCreate", "List Cursor Initialized");
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		name = (TextView) view.findViewById(R.id.name);
		name.setText(cursor.getString(1));
		
		Log.i("LCA.bindview", "Name: " + cursor.getString(1));
		
		listview = (ListView) view.findViewById(R.id.listview);
		if(cursor.getString(2)=="Name"){
			cursor1 = datasource.database.rawQuery("SELECT * FROM " + MachineTable.TABLE_NAME + " WHERE table = '" + cursor.getInt(0) + "' ORDER BY Name", null);
		} else if(cursor.getString(2)=="Last Grease"){
			cursor1 = datasource.database.rawQuery("SELECT * FROM " + MachineTable.TABLE_NAME + " WHERE table = '" + cursor.getInt(0) + "' ORDER BY Last_Grease", null);
		} else if(cursor.getString(2)=="Last Maintenance"){
			cursor1 = datasource.database.rawQuery("SELECT * FROM " + MachineTable.TABLE_NAME + " WHERE table = '" + cursor.getInt(0) + "' ORDER BY Last_Maintenance", null);
		}
		int id = cursor.getInt(0);
		cursors.put(id, cursor1);
		listview.setAdapter(new MachineCursorAdapter(context, cursors.get(id)));
		
		if(buttons.size() <= id*2){
			buttons.put(2*id ,(Button) view.findViewById(R.id.addButton0)) ;
			buttons.put(2*id+1 ,(Button) view.findViewById(R.id.removeButton0));
		}

		buttons.get(id*2).setTag(id*2);
		buttons.get(id*2+1).setTag(id*2+1);
		//buttons.get(id*2).setOnClickListener( onClickListener1 );
		//buttons.get(id*2+1).setOnClickListener( onClickListener1 );
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View v = mInflater.inflate(R.layout.test_item_1, parent, false);
		return v;
	}
}
