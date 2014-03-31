package com.demo.Machinery_App;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ListVerAdapter extends ArrayAdapter<String> {

	//vertical List views
	List<String> vCardList = new ArrayList<String>();

	public ListVerAdapter(Context context, int textViewResourceId,
			List<String> objects) {
		super(context, textViewResourceId, objects);
		for (int i = 0; i < objects.size(); ++i) {
			vCardList.add(objects.get(i));
		}
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}	
}

