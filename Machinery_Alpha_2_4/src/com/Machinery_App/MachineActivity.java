package com.Machinery_App;

import java.util.ArrayList;

import com.Machinery_App.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MachineActivity extends Activity {
	/** Called when the activity is first created. */
	long id;
	String name;
	long purchaseYear;
	long modelYear;
	long lastGrease;
	long lastService;
	String consumables;
	ArrayList<String> serviceList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_machine);

		//Getting View IDs
		TextView titleT = (TextView) findViewById(R.id.Mtitle);
		TextView infoT = (TextView) findViewById(R.id.Minfo);
		TextView consumablesT = (TextView) findViewById(R.id.Mconsumables);
		//TextView maintenanceT = (TextView) findViewById(R.id.Mmaintenance);
		ListView listview = (ListView) findViewById(R.id.Mlistview);
		Button noteB = (Button) findViewById(R.id.MbtnNote);
		Button greaseB = (Button) findViewById(R.id.MbtnGrease);
		Button maintenanceB = (Button) findViewById(R.id.MbtnMaintenance);
		Button backB = (Button) findViewById(R.id.MbtnBack);

		//Getting Machine information from passed Bundle
		Intent i = getIntent();
		id = i.getLongExtra("id", 0);
		name = i.getStringExtra("name");
		purchaseYear = i.getLongExtra("purchaseYear",0);
		modelYear = i.getLongExtra("modelYear",0);
		lastGrease = i.getLongExtra("lastGrease",0);
		lastService = i.getLongExtra("lastService",0);
		consumables = i.getStringExtra("consumables");
		serviceList = i.getStringArrayListExtra("serviceList");

		//Setting View Information
		titleT.setText(name);
		infoT.setText(
						"Card ID: " + id + "\n" +
						"Purchased: " + purchaseYear + "\n" +
						"Model Year: " + modelYear + "\n" +
						"Last Greased: " + lastGrease + "\n" +
						"Last Serviced: " + lastService
				);
		consumablesT.setText(consumables);
		listview.setAdapter(new MaintenanceAdapter(this, serviceList));

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
				returnIntent.putExtra("ID", id);
				returnIntent.putExtra("name",name);
				returnIntent.putExtra("purchaseYear",purchaseYear);
				returnIntent.putExtra("modelYear",purchaseYear);
				returnIntent.putExtra("lastGrease",lastGrease);
				returnIntent.putExtra("lastService",lastService);
				returnIntent.putExtra("consumables",consumables);
				returnIntent.putStringArrayListExtra("serviceList",serviceList);
				setResult(RESULT_OK,returnIntent);     
				finish();
				break;
			}
		}
	};

}
