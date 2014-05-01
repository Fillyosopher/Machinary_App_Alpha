package com.OpenATK.machineryapp;

import java.util.List;

import com.OpenATK.machineryapp.models.Machine;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MachineArrayAdapter extends ArrayAdapter<Machine> {
	private final Context context;
	private List<Machine> machines = null;
	private int resId;
	
	public MachineArrayAdapter(Context context, int layoutResourceId, List<Machine> data) {
		super(context, layoutResourceId, data);
		this.resId = layoutResourceId;
		this.context = context;
		this.machines = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View col = convertView;
		Holder holder = null;

		if(col == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			col = inflater.inflate(resId, parent, false);

			holder = new Holder();
			holder.name = (TextView) col.findViewById(R.id.name);
			holder.year = (TextView) col.findViewById(R.id.year);
			holder.line1 = (TextView) col.findViewById(R.id.line1);
			holder.line2 = (TextView) col.findViewById(R.id.line2);
			holder.line3 = (TextView) col.findViewById(R.id.line3);
			
			col.setTag(holder);
		} else {
			holder = (Holder) col.getTag();
		}

		if(machines == null){
			Log.e("MachineArrayAdapter", "machines null");
		} else {
		}

		if(holder == null){
			Log.d("MachineArrayAdapter", "holder null");
		} else {
			Machine machine = machines.get(position);
			holder.year.setText(machine.getYear());
			holder.name.setText(machine.getName());
			if(machine.getDateGreasedChanged() != null){
				holder.line1.setText(machine.getGreased());
			} else {
				holder.line1.setVisibility(View.GONE);
			}
			if(machine.getDateMaintenanceChanged() != null){
				holder.line2.setText(machine.getMaintenance());
			} else {
				holder.line2.setVisibility(View.GONE);
			}
			holder.line2.setVisibility(View.GONE);
		}
		return col;
	}
	
	static class Holder
    {
        TextView year;
        TextView name;
        TextView line1;
        TextView line2;
        TextView line3;       
    }
	
}
