package com.Machinery_App;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.List;

import com.Machinery_App.R;
import com.Machinery_App.MachineAdapter;
import com.Machinery_App.db.CoreDataSource;
import com.Machinery_App.models.List2;
import com.Machinery_App.models.Machine;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;

@TargetApi(11)
public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {
	//Static string declarations
	private static final String LOG_TAG = "MainActivity";

	//Main View Declarations of layout/activity_main
	EditText searchEditText;
	
	Button sortbyMachine;
	Button sortbyGrease;
	Button sortbyMaintenance;
	
	HListView mHorListView;
	
	MachineAdapter horAdapter;

	//SQLite
	//this should be private, fix later, TODO
	CoreDataSource datasource;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );	

		//SQLite datasource
		datasource = new CoreDataSource(this);
		
		Button btnNextScreen = (Button) findViewById(R.id.switchview);
		btnNextScreen.setOnClickListener(switchviewListener);
		
		sortbyMachine = (Button)findViewById(R.id.nameOrder);
		sortbyGrease = (Button)findViewById(R.id.greaseOrder);
		sortbyMaintenance = (Button)findViewById(R.id.maintenanceOrder);
		sortbyMachine.setOnClickListener(sortbyNameListener);
		sortbyGrease.setOnClickListener(sortbyGreaseListener);
		sortbyMaintenance.setOnClickListener(sortbyMaintenanceListener);
		
		// HorListView initial populating
		List<List2> listList = datasource.getAllLists();
		List<String> listNames = datasource.getListNames();
		
		// This is to add the column buttons at the end of the horizontal list
		listNames.add("Buttons");
		listList.add(new List2(1,"Buttons","Name")); 
		
		//Start the Main ListView
		horAdapter = new MachineAdapter( this, listNames, listList, datasource);
		mHorListView.setAdapter( horAdapter );
		
		mHorListView.setHeaderDividersEnabled( true );
		mHorListView.setFooterDividersEnabled( true );
		mHorListView.setOnItemClickListener( this );
		
		searchEditText.addTextChangedListener(watcher);
	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		mHorListView = (HListView) findViewById( R.id.hListView1 );
		searchEditText = (EditText) findViewById(R.id.edittext);
		sortbyMachine = (Button)findViewById(R.id.nameOrder);
		sortbyGrease = (Button)findViewById(R.id.greaseOrder);
		sortbyMaintenance = (Button)findViewById(R.id.maintenanceOrder);
	}

	@Override
	public void onClick( View v ) {
		//final int id = v.getId();
	}	

	@Override
	public void onItemClick( AdapterView<?> parent, View veiw, int position, long id ) {
		Log.i( LOG_TAG, "onItemClick: " + position );
	}

	protected OnClickListener sortbyGreaseListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			sortbyGrease.setPressed(true);
			sortbyMaintenance.setPressed(false);
			sortbyMachine.setPressed(false);
			//datasource.database.execSQL("UPDATE +"+MachineTable.TABLE_NAME+" ORDER BY "+MachineTable.COLUMN_NAMES[0]);
		}//onClick
	};
	
	protected OnClickListener sortbyNameListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			sortbyGrease.setPressed(false);
			sortbyMaintenance.setPressed(false);
			sortbyMachine.setPressed(true);
			//datasource.database.execSQL("UPDATE +"+MachineTable.TABLE_NAME+" ORDER BY "+MachineTable.COLUMN_NAMES[0]);
		}//onClick
	};
	
	protected OnClickListener sortbyMaintenanceListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			sortbyGrease.setPressed(false);
			sortbyMaintenance.setPressed(true);
			sortbyMachine.setPressed(false);
			//datasource.database.execSQL("UPDATE +"+MachineTable.TABLE_NAME+" ORDER BY "+MachineTable.COLUMN_NAMES[0]);
		}//onClick
	};
	
	protected OnClickListener switchviewListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			//Starting a new Intent
			Intent nextScreen = new Intent(getApplicationContext(), MachineActivity.class);

			//get clicked machine and pass to the activity
			// TODO make this work by Machine
			Machine machine = datasource.getMachine(1);
			nextScreen.putExtra("ID", machine.getId());
			nextScreen.putExtra("name", machine.getName());
			nextScreen.putExtra("purchaseYear", machine.getYear());
			nextScreen.putExtra("lastGrease", machine.getLastGrease());
			nextScreen.putExtra("lastService", machine.getLastMaintenance());
			nextScreen.putExtra("consumables", machine.getConsumables());
			nextScreen.putStringArrayListExtra("serviceList", datasource.getAllServiceNames(machine.getServiceTableName()));
			
			startActivityForResult(nextScreen, 1);
		}//onClick
	};//switchViewListener

	// Function to read the result from newly created activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if(resultCode == RESULT_OK){      
				Long id = data.getLongExtra("ID", 0);
				String name = data.getStringExtra("name");
				long purchaseYear = data.getLongExtra("purchaseYear",0);
				long modelYear = data.getLongExtra("modelYear", 0);
				long lastGrease = data.getLongExtra("lastGrease", 0);
				long lastService = data.getLongExtra("lastService", 0);
				String consumables = data.getStringExtra("consumables");
				ArrayList<String> serviceList = data.getStringArrayListExtra("serviceList");
				//TODO
				//Machine machine = datasource.getMachine(id);
				//datasource.updateMachine(new Machine(id, name, machine.getList(), modelYear, lastGrease, lastService, consumables, machine.getServiceTableName(), machine.getColor()));
			}
			if (resultCode == RESULT_CANCELED) {    
				//Write your code if there's no result
			}
		}
	}//onActivityResult
	
	TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence cs, int arg1, int arg2,int arg3) {
        	MainActivity.this.horAdapter.getFilter().filter(cs.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1,
                int arg2, int arg3) {
        }

        @Override
        public void afterTextChanged(Editable arg0) {
        	MainActivity.this.horAdapter.getFilter().filter(arg0.toString());
        }
	};
}