package com.Machinery_App;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MaintenanceAdapter extends ArrayAdapter<String> {
	//private static final String LOG_TAG = "MaintenanceAdapter";
	
	private static int resource = R.layout.simple_list_item_1;
	
	public MaintenanceAdapter(Context context, List<String> data) {
		super(context, resource, data);
		// TODO Auto-generated constructor stub
	}
}
