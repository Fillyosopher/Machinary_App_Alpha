package com.Machinery_App;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.util.List;

import com.Machinery_App.R;
import com.Machinery_App.db.CoreDataSource;
import com.Machinery_App.db.ListTable;
import com.Machinery_App.models.List2;
import com.Machinery_App.models.Machine;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

@TargetApi(11)
public class MainActivity extends Activity {
	//Static string declarations
	private static final String LOG_TAG = "MainActivity";

	//Main View Declarations of layout/activity_main
	EditText searchEditText;
	
	ToggleButton sortbyMachine;
	ToggleButton sortbyGrease;
	ToggleButton sortbyMaintenance;
	
	HListView mHorListView;
	Cursor cursor1;
	ListCursorAdapter lcadapter;
	
	Button addList;
	Button removeList;
	


	//SQLite
	//this should be private, fix later, TODO
	CoreDataSource datasource;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );	

		//SQLite datasource
		datasource = new CoreDataSource(this);
				
		sortbyMachine = (ToggleButton)findViewById(R.id.nameOrder);
		sortbyGrease = (ToggleButton)findViewById(R.id.greaseOrder);
		sortbyMaintenance = (ToggleButton)findViewById(R.id.maintenanceOrder);
		sortbyMachine.setOnClickListener(sortbyNameListener);
		sortbyGrease.setOnClickListener(sortbyGreaseListener);
		sortbyMaintenance.setOnClickListener(sortbyMaintenanceListener);
		sortbyMachine.setChecked(true);
				
		addList = (Button)findViewById(R.id.addButton);
		removeList = (Button)findViewById(R.id.removeButton);
		
		Log.i("Main Activity", "Button initialization finished");
		
		mHorListView = (HListView)findViewById(R.id.listview);

		datasource.getWritableDatabase();
		datasource.database.execSQL("DROP TABLE IF EXISTS Service1");
		datasource.database.execSQL("DROP TABLE IF EXISTS "+datasource.helper.tables[1].TABLE_NAME);
		datasource.database.execSQL("DROP TABLE IF EXISTS "+datasource.helper.tables[0].TABLE_NAME);
		datasource.helper.onCreate(datasource.database);
		
		datasource.addList(new List2((long)1,"List1","Name"));
		datasource.addMachine(new Machine((long)1,"Machine1",(long)1,(long)2013,(long)11,(long)12, "Consumables: blank", "", "white"));
		
		//Start the Main ListView
		//TODO order by order column
		Log.i(LOG_TAG,"List table size = "+datasource.getListsCount());
		cursor1 = datasource.database.query("lists",null,null,null,null,null,null,null);
		lcadapter = new ListCursorAdapter(this, cursor1, datasource);
		if(cursor1!=null && cursor1.getCount()>0){
			mHorListView.setAdapter( lcadapter );
		} else {
			Log.e("Main Activity", "Cursor Failed");
		}
		mHorListView.setHeaderDividersEnabled( true );
		mHorListView.setFooterDividersEnabled( true );
		
		Log.i("Main Activity", "listview initialization finished");
	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		mHorListView = (HListView) findViewById( R.id.hListView1 );
		searchEditText = (EditText) findViewById(R.id.edittext);
		sortbyMachine = (ToggleButton)findViewById(R.id.nameOrder);
		sortbyGrease = (ToggleButton)findViewById(R.id.greaseOrder);
		sortbyMaintenance = (ToggleButton)findViewById(R.id.maintenanceOrder);
		addList = (Button)findViewById(R.id.addButton);
		removeList = (Button)findViewById(R.id.removeButton);
	}

	protected OnClickListener sortbyGreaseListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			sortbyGrease.setChecked(true);
			sortbyMaintenance.setChecked(false);
			sortbyMachine.setChecked(false);
			//datasource.database.execSQL("UPDATE +"+MachineTable.TABLE_NAME+" ORDER BY "+MachineTable.COLUMN_NAMES[0]);
		}//onClick
	};
	
	protected OnClickListener sortbyNameListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			sortbyGrease.setChecked(false);
			sortbyMaintenance.setChecked(false);
			sortbyMachine.setChecked(true);
			//datasource.database.execSQL("UPDATE +"+MachineTable.TABLE_NAME+" ORDER BY "+MachineTable.COLUMN_NAMES[0]);
		}//onClick
	};
	
	protected OnClickListener sortbyMaintenanceListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			sortbyGrease.setChecked(false);
			sortbyMaintenance.setChecked(true);
			sortbyMachine.setChecked(false);
			//datasource.database.execSQL("UPDATE +"+MachineTable.TABLE_NAME+" ORDER BY "+MachineTable.COLUMN_NAMES[0]);
		}//onClick
	};	
}