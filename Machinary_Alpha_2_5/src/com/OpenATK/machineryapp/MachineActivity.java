package com.OpenATK.machineryapp;

import java.util.ArrayList;
import java.util.List;

import com.OpenATK.machineryapp.R;
import com.OpenATK.machineryapp.models.Machine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MachineActivity extends Activity {
	public final static String MACHINE = "com.OpenATK.machineryapp.MACHINE";
	Machine machine;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_machine);

		//Getting View IDs
		TextView titleT = (TextView) findViewById(R.id.Mtitle);
		TextView infoT = (TextView) findViewById(R.id.Minfo);
		TextView consumablesT = (TextView) findViewById(R.id.Mconsumables);
		TextView maintenanceT = (TextView) findViewById(R.id.Mmaintenance);
		ListView listview = (ListView) findViewById(R.id.Mlistview);
		Button noteB = (Button) findViewById(R.id.MbtnNote);
		Button greaseB = (Button) findViewById(R.id.MbtnGrease);
		Button maintenanceB = (Button) findViewById(R.id.MbtnMaintenance);
		Button backB = (Button) findViewById(R.id.MbtnBack);

		//Getting Machine information from passed Bundle
		Intent i = getIntent();
		machine = (Machine) i.getSerializableExtra(MACHINE);

		//Setting View Information
		titleT.setText(machine.getName());
		infoT.setText(
						"Model Year: " + machine.getYear() + "\n" +
						"Last Greased: " + machine.getDateGreasedChanged() + "\n" +
						"Last Serviced: " + machine.getDateMaintenanceChanged()
				);
		consumablesT.setText(machine.getMaintenance());
		//TODO
		//listview.setAdapter(new MaintenanceAdapter(this, serviceList));

		//Setting Button Listeners
		noteB.setOnClickListener(onClickListener);
		greaseB.setOnClickListener(onClickListener);
		maintenanceB.setOnClickListener(onClickListener);
		backB.setOnClickListener(onClickListener);
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(final View v) {
			switch(v.getId()){
			case R.id.MbtnNote:
				// TODO
				break;
			case R.id.MbtnGrease:
				//TODO
				break;
			case R.id.MbtnMaintenance:
				//TODO
				break;			
			case R.id.MbtnBack:
				Intent returnIntent = new Intent();

				setResult(RESULT_OK,returnIntent);     
				finish();
				break;
			}
		}
	};

}
