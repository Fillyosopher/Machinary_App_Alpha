package com.Machinery_App;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MachineCursorAdapter extends CursorAdapter  {
	private Context context;
	
	private LayoutInflater mInflater;
	
	private TextView year;
	private TextView name;
	private TextView line1;
	private TextView line2;
	private TextView line3;
	
	public MachineCursorAdapter(Context context, Cursor c) {
		super(context, c, 0);
		this.context = context;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		year = (TextView) view.findViewById(R.id.year);
		year.setText(cursor.getString(3));
		
		name = (TextView) view.findViewById(R.id.name);
		name.setText(cursor.getString(1));
		
		//TODO add in the extra lines
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return mInflater.inflate(R.layout.simple_list_item_1, parent, false);
	}

}
