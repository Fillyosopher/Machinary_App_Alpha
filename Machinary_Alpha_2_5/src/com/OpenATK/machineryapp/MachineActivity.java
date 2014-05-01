package com.OpenATK.machineryapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.OpenATK.machineryapp.R;
import com.OpenATK.machineryapp.db.DatabaseHelper;
import com.OpenATK.machineryapp.db.TableMachine;
import com.OpenATK.machineryapp.models.Machine;
import com.OpenATK.machineryapp.models.Maintenance;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public class MachineActivity extends Activity {
	public final static String MACHINE = "com.OpenATK.machineryapp.MACHINE";
	
	private DatabaseHelper dbHelper;
	Machine machine;
	List<Maintenance> maintenances = new ArrayList<Maintenance>();
	private Context context;

	/** Called when the fragment is first created. 
	 * @return */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_machine);

		//Getting View IDs
		TextView titleT = (TextView) findViewById(R.id.Mtitle);
		EditText infoT = (EditText) findViewById(R.id.Minfo);
		EditText consumablesT = (EditText) findViewById(R.id.Mconsumables);
		TextView maintenanceT = (TextView) findViewById(R.id.Mmaintenance);
		ListView listview = (ListView) findViewById(R.id.Mlistview);
		Button maintenanceB = (Button) findViewById(R.id.MbtnMaintenance);
		Button backB = (Button) findViewById(R.id.MbtnBack);
		
		Intent i = getIntent();
		machine = (Machine) i.getSerializableExtra(MACHINE);
		//TODO
		//dbHelper = DatabaseHelper();
		
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
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(final View v) {
			switch(v.getId()){
			case R.id.MbtnMaintenance:
				//TODO
				break;			
			case R.id.MbtnBack:
				// TODO
				//TableMachine.updateMachine(dbHelper, machine);
				finish();
				break;
			}
		}
	};
}
