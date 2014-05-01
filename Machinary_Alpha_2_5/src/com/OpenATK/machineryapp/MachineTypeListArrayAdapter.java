package com.OpenATK.machineryapp;

import java.util.List;

import com.OpenATK.machineryapp.db.DatabaseHelper;
import com.OpenATK.machineryapp.models.MachineTypeList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MachineTypeListArrayAdapter extends ArrayAdapter<MachineTypeList> {
	private final Context context;
	private List<MachineTypeList> lists = null;
	private int resId;
	private int innerResId;
	private DatabaseHelper database = null;
	
	public MachineTypeListArrayAdapter(Context context, int layoutResourceId, List<MachineTypeList> data, int innerLayoutResourceId, DatabaseHelper database) {
		super(context, layoutResourceId, data);
		this.resId = layoutResourceId;
		this.context = context;
		this.lists = data;
		this.innerResId = innerLayoutResourceId;
		this.database = database;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View col = convertView;
		Holder holder = null;

		if(col == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			col = inflater.inflate(resId, parent, false);

			holder = new Holder();
			holder.txtTitle = (TextView) col.findViewById(R.id.columnTitleTextView);
			holder.innerList = (ListView) col.findViewById(R.id.innerMachineListView);
			holder.buttonAddRow = (Button) col.findViewById(R.id.addRowButton);
			holder.buttonRemoveRow = (Button) col.findViewById(R.id.removeRowButton);

			col.setTag(holder);
		} else {
			holder = (Holder) col.getTag();
		}

		if(lists == null){
			Log.d("ListViewJobsArrayAdapter", "jobs null");
		} else {
			Log.d("ListViewJobsArrayAdapter", "Length:" + Integer.toString(lists.size()));
			Log.d("ListViewJobsArrayAdapter", "Pos:" + Integer.toString(position));
		}

		if(holder == null){
			Log.d("ListViewJobsArrayAdapter", "holder null");
		} else {
			MachineTypeList list = lists.get(position);
			holder.txtTitle.setText(list.getName());
		}
		return col;
	}
	
	static class Holder
    {
        TextView txtTitle;
        ListView innerList;
        Button buttonAddRow;
        Button buttonRemoveRow;
    }
}
