package com.OpenATK.machineryapp;

import java.util.Date;
import java.util.List;

import com.OpenATK.machineryapp.db.DatabaseHelper;
import com.OpenATK.machineryapp.db.TableMachine;
import com.OpenATK.machineryapp.models.Machine;
import com.OpenATK.machineryapp.models.MachineTypeList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MachineTypeListArrayAdapter extends ArrayAdapter<MachineTypeList> {
	public static final Integer POSITION_TAG = 0;
	public static final Integer LISTVIEW_TAG = 1;
	
	private final Context context;
	private List<MachineTypeList> lists = null;
	private int resId;
	private int innerResId;
	private DatabaseHelper dbHelper = null;
	
	public MachineTypeListArrayAdapter(Context context, int layoutResourceId, List<MachineTypeList> data, int innerLayoutResourceId, DatabaseHelper dbHelper) {
		super(context, layoutResourceId, data);
		this.resId = layoutResourceId;
		this.context = context;
		this.lists = data;
		this.innerResId = innerLayoutResourceId;
		this.dbHelper = dbHelper;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View col = convertView;
		Holder holder = null;
		ButtonHolder bHolder = null;
		
		if(col == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			col = inflater.inflate(resId, parent, false);

			holder = new Holder();
			holder.txtTitle = (TextView) col.findViewById(R.id.columnTitleTextView);
			holder.innerList = (ListView) col.findViewById(R.id.innerMachineListView);
			holder.buttonAddRow = (Button) col.findViewById(R.id.addRowButton);
			holder.buttonRemoveRow = (Button) col.findViewById(R.id.removeRowButton);
			
			bHolder = new ButtonHolder();
			bHolder.innerList = holder.innerList;
			bHolder.position = position;
		
			List<Machine> machineList = dbHelper.readMachinesOfList(lists.get(position));
			if (machineList.size() == 0){
				Date currentDate = new Date(System.currentTimeMillis());
				boolean added = TableMachine.updateMachine(dbHelper,new Machine(
						null, null,
						"New Machine",currentDate,
						lists.get(position).getId(),currentDate,
						"20XX", currentDate,
						position, currentDate,
						null, currentDate, 
						null, currentDate, null,
						false, null
						));
				machineList = dbHelper.readMachinesOfList(lists.get(position));
				if (added == false || machineList.size() == 0){
					Log.e("MachineTypeListArrayAdapter", "Machines List database error");
				}
			}
			
			holder.innerList.setAdapter(new MachineArrayAdapter(this.context,innerResId,machineList));	
			
			holder.buttonAddRow.setOnClickListener(rowEditListener);
			holder.buttonRemoveRow.setOnClickListener(rowEditListener);
			holder.buttonAddRow.setTag(bHolder);
			holder.buttonRemoveRow.setTag(bHolder);
			
			col.setTag(holder);
		} else {
			holder = (Holder) col.getTag();
		}

		if(lists == null){
			Log.e("MachineTypeListArrayAdapter", "lists null");
		} else {
			Log.d("MachineTypeListArrayAdapter", "Length:" + Integer.toString(lists.size()));
			Log.d("MachineTypeListArrayAdapter", "Pos:" + Integer.toString(position));
		}

		if(holder == null){
			Log.d("MachineTypeListArrayAdapter", "holder null");
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
	
	static class ButtonHolder
    {
        Integer position;
        ListView innerList;
    }
	
	protected OnClickListener rowEditListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Date currentDate;
			ButtonHolder bHolder;
			Integer position;
			ListView InnerListView;
			switch (v.getId()){
			case R.id.addRowButton:
				bHolder = (ButtonHolder) v.getTag();
				position = bHolder.position;
				InnerListView = bHolder.innerList;
				currentDate = new Date(System.currentTimeMillis());
				boolean added = TableMachine.updateMachine(dbHelper,new Machine(
						null, null,
						"New Machine",currentDate,
						lists.get(position).getId(),currentDate,
						"20XX", currentDate,
						position, currentDate,
						null, currentDate, 
						null, currentDate, null,
						false, null
						));
				if (!added){
					Log.w("Main Activity","Row Add Failed");
				} else {
					List<Machine> machineList = dbHelper.readMachinesOfList(lists.get(position));
					InnerListView.setAdapter(new MachineArrayAdapter(
							context,innerResId,machineList
							));
				}
				break;
			case R.id.removeRowButton:
				bHolder = (ButtonHolder) v.getTag();
				position = bHolder.position;
				InnerListView = bHolder.innerList;
				currentDate = new Date(System.currentTimeMillis());
				//TODO
				List<Machine> machineList = dbHelper.readMachinesOfList(lists.get(position));
				Machine removeMachine = machineList.get(machineList.size()-1);
				removeMachine.setDeleted(true);
				removeMachine.setDateDeleted(currentDate);
				boolean removed = TableMachine.updateMachine(dbHelper,removeMachine);
				if (!removed){
					Log.w("Main Activity","Row Delete Failed");
				} else {
					machineList = dbHelper.readMachinesOfList(lists.get(position));
					InnerListView.setAdapter(new MachineArrayAdapter(
							context,innerResId,machineList
							));
				}
				break;
			}
		}//onClick
	};//rowEditListener
}
