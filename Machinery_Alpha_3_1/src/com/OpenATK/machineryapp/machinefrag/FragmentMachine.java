package com.OpenATK.machineryapp.machinefrag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.OpenATK.machineryapp.R;
import com.OpenATK.machineryapp.db.DatabaseHelper;
import com.OpenATK.machineryapp.db.TableMachine;
import com.OpenATK.machineryapp.listfrag.FragmentList.FragmentListListener;
import com.OpenATK.machineryapp.models.Machine;
import com.OpenATK.machineryapp.models.Maintenance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentMachine extends Fragment {
	private DatabaseHelper dbHelper;
	Context context;
	private FragmentMachineListener listener;
	Machine machine;
	List<Maintenance> maintenances = new ArrayList<Maintenance>();
	
	// Interface for receiving data
	public interface FragmentMachineListener {
		public void FragmentMachine_Init(); //This -> Listener
		public void FragmentMachine_Done(Machine machine);  //This -> Listener
	}
	
	/** Called when the fragment is first created. 
	 * @return */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, container,false);

		//Getting View IDs
		TextView titleT = (TextView) view.findViewById(R.id.Mtitle);
		EditText infoT = (EditText) view.findViewById(R.id.Minfo);
		EditText consumablesT = (EditText) view.findViewById(R.id.Mconsumables);
		TextView maintenanceT = (TextView) view.findViewById(R.id.Mmaintenance);
		ListView listview = (ListView) view.findViewById(R.id.Mlistview);
		Button maintenanceB = (Button) view.findViewById(R.id.MbtnMaintenance);
		Button backB = (Button) view.findViewById(R.id.MbtnBack);
		
		//Getting specific information from machine
		Object greased;
		Object maintenance;
		String partInfo;
		if (machine.getDateGreasedChanged()==null) {
			greased = "Never Greased";
		} else {
			greased = machine.getDateGreasedChanged();
		}
		
		if (machine.getDateMaintenanceChanged()==null) {
			maintenance = "Never Serviced";
		} else {
			maintenance = machine.getDateMaintenanceChanged();
		}
		
		if (machine.getMaintenance()==null) {
			partInfo = "No Part Information Recorded";
		} else {
			partInfo = machine.getMaintenance();
		}

		//Setting View Information
		titleT.setText(machine.getName());
		infoT.setText(
						"Model Year: " + machine.getYear() + "\n" +
						"Last Greased: " + greased.toString() + "\n" +
						"Last Serviced: " + maintenance.toString()
				);
		consumablesT.setText(partInfo);
		maintenanceT.setText("Maintenance List");
		
		machine.setMaintenanceTableName("maintenance" + machine.getId().toString());
		if(machine.getMaintenanceTableName()!=null) {
			maintenances = dbHelper.readMaintenancesOfMachine(machine);
		} else {
			Log.e("MachineActivity","Machine " + machine.getName()+ " lacks a maintenance database: " + machine.getMaintenanceTableName());
		}
		listview.setAdapter(new MaintenanceArrayAdapter(this.context, R.layout.maintenance_item, maintenances));

		//Setting Button Listeners
		maintenanceB.setOnClickListener(onClickListener);
		backB.setOnClickListener(onClickListener);
		
		consumablesT.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				Date currentDate = new Date(System.currentTimeMillis());
				machine.setMaintenance(s.toString());
				machine.setDateMaintenanceChanged(currentDate);
				Log.d("Machine Activity","Maintenance set to "+machine.getMaintenance());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}			
		});
		
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof FragmentMachineListener) {
			listener = (FragmentMachineListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + " must implement FragmentMachine.FragmentMachineListener");
		}
		Log.d("FragmentMachine", "Attached");
	}
	
	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(final View v) {
			switch(v.getId()){
			case R.id.MbtnMaintenance:
				//TODO
				break;			
			case R.id.MbtnBack:
				TableMachine.updateMachine(dbHelper, machine);
				listener.FragmentMachine_Done(machine);
				break;
			}
		}
	};
	
	
	public void closing() {
		// TODO Auto-generated method stub
		
	}

	public int getHeight() {
		// Method so close transition can work
		return getView().getHeight();
	}
	
	public void setdbHelper(DatabaseHelper dbHelper) {
		this.dbHelper = dbHelper;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}

	public void setMachine(Machine currentMachine) {
		this.machine = currentMachine;
	}
}
