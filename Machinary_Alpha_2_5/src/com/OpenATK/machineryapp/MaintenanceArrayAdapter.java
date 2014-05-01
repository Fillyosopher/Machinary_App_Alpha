package com.OpenATK.machineryapp;

import java.util.List;

import com.OpenATK.machineryapp.MachineArrayAdapter.Holder;
import com.OpenATK.machineryapp.models.Machine;
import com.OpenATK.machineryapp.models.Maintenance;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MaintenanceArrayAdapter extends ArrayAdapter<Maintenance>  {
	private final Context context;
	private List<Maintenance> maintenances = null;
	private int resId;
	
	public MaintenanceArrayAdapter(Context context, int layoutResourceId, List<Maintenance> data) {
		super(context, layoutResourceId, data);
		this.resId = layoutResourceId;
		this.context = context;
		this.maintenances = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View col = convertView;
		Holder holder = null;

		if(col == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			col = inflater.inflate(resId, parent, false);

			holder = new Holder();
			holder.name = (TextView) col.findViewById(R.id.maintenanceNameTextView);
			holder.note = (TextView) col.findViewById(R.id.maintenceNoteTextView);
			holder.date = (TextView) col.findViewById(R.id.maintenceDateTextView);
			
			col.setTag(holder);
		} else {
			holder = (Holder) col.getTag();
		}

		if(maintenances == null){
			Log.e("MaintenanceArrayAdapter", "maintenances null");
		} else {
		}

		if(holder == null){
			Log.d("MaintenanceArrayAdapter", "holder null");
		} else {
			Maintenance maintenance = maintenances.get(position);
			holder.note.setText(maintenance.getNote());
			holder.name.setText(maintenance.getName());
			holder.date.setText(maintenance.getDateNameChanged().toString());
		}
		return col;
	}
	
	static class Holder
    {
        TextView name;
        TextView note; 
        TextView date;
    }
	
}
