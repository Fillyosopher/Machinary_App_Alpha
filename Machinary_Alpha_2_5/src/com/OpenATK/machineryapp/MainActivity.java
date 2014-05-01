package com.OpenATK.machineryapp;

import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.OpenATK.machineryapp.db.DatabaseHelper;
import com.OpenATK.machineryapp.db.TableMachine;
import com.OpenATK.machineryapp.db.TableMachineTypeList;
import com.OpenATK.machineryapp.models.Machine;
import com.OpenATK.machineryapp.models.MachineTypeList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ToggleButton;

@TargetApi(11)
public class MainActivity extends Activity {
	
	public static int STATE_SORTBY_NAME = 0;
	public static int STATE_SORTBY_GREASED = 1;
	public static int STATE_SORTBY_MAINTENANCE = 2;
	
	public final static String MACHINE = "com.OpenATK.machineryapp.MACHINE";

	private int state;
		
	private EditText searchEditText = null;
	private DatabaseHelper dbHelper;
	HListView OuterListView;
	
	ToggleButton orderByName;
	ToggleButton orderByGrease;
	ToggleButton orderByMaintenance;
	
	private List<MachineTypeList> typesList = new ArrayList<MachineTypeList>();
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		// The activity is being created.
		/**
		 * Called when the activity is first created. 
		 * This is where you should do all of your normal static set up — create views, bind data to lists, and so on. 
		 * This method is passed a Bundle object containing the activity's previous state, if that state was captured (see Saving Activity State, later). 
		 * Always followed by onStart().
		 */
		setContentView( R.layout.activity_main );//Sets View to inflate
		
		dbHelper = new DatabaseHelper(this);
		
		if (savedInstanceState == null) {
			// First incarnation of this activity.
			setState(STATE_SORTBY_NAME);
		} else {
			// Reincarnated activity.
		}
		
		searchEditText = (EditText) this.findViewById(R.id.searchByEditText);
		orderByName = (ToggleButton) this.findViewById(R.id.orderByNameButton);
		orderByGrease = (ToggleButton) this.findViewById(R.id.orderByGreaseButton);		
		orderByMaintenance = (ToggleButton) this.findViewById(R.id.orderByMaintenanceButton);		
		OuterListView = (HListView) this.findViewById(R.id.outerMachineListView);
		Button addColumn = (Button) this.findViewById(R.id.addColumnButton);
		Button removeColumn = (Button) this.findViewById(R.id.removeColumnButton);
				
		orderByName.setOnClickListener(orderByListener);
		orderByGrease.setOnClickListener(orderByListener);
		orderByMaintenance.setOnClickListener(orderByListener);
		
		typesList = dbHelper.readLists();
		if (typesList.size() == 0){
			Date currentDate = new Date(System.currentTimeMillis());
			boolean added = TableMachineTypeList.updateMachineTypeList(dbHelper,new MachineTypeList(
					null,null,"New List",currentDate,1,currentDate,false,null
					));
			typesList = dbHelper.readLists();
			if (added == false || typesList.size() == 0){
				Log.e("Main_Activity", "Types List database error");
			}
		}
		OuterListView.setAdapter(AdapterByState());		
		
		searchEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		});
		searchEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				OuterListView.setAdapter(AdapterByState(s.toString()));
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}			
		});
		
		addColumn.setOnClickListener(columnEditListener);
		removeColumn.setOnClickListener(columnEditListener);
		
		OuterListView.setHeaderDividersEnabled( true );
		OuterListView.setFooterDividersEnabled( true );
		//OuterListView.setOnItemClickListener( this );
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// The activity is regaining focus.
		/**
		 * Called after the activity has been stopped, just prior to it being started again.
		 * Always followed by onStart()
		 */
	}   
	@Override
	protected void onStart() {
		super.onStart();
		// The activity is about to become visible.
		/**
		 * Called just before the activity becomes visible to the user.
		 * Followed by onResume() if the activity comes to the foreground, or onStop() if it becomes hidden.
		 */
	}
	@Override
	protected void onRestoreInstanceState (Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		// The activity is returning after destruction
	}
	@Override
	protected void onResume() {
		super.onResume();
		// The activity has become visible (it is now "resumed").
		/**
		 * Called just before the activity starts interacting with the user.
		 * At this point the activity is at the top of the activity stack, with user input going to it.
		 * Always followed by onPause().
		 */
	}    
	@Override
	protected void onSaveInstanceState (Bundle outState) {
		super.onSaveInstanceState(outState);
		// The activity may closed out, so resume setting should be saved
	}
	@Override
	protected void onPause() {
		super.onPause();
		// Another activity is taking focus (this activity is about to be "paused").
		/**
		 * Called when the system is about to start resuming another activity.
		 * This method is typically used to commit unsaved changes to persistent data, stop animations and other things that may be consuming CPU, and so on.
		 * It should do whatever it does very quickly, because the next activity will not be resumed until it returns.
		 * Followed either by onResume() if the activity returns back to the front, or by onStop() if it becomes invisible to the user.
		 */
		// TODO save state and restore state
	}
	@Override
	protected void onStop() {
		super.onStop();
		// The activity is no longer visible (it is now "stopped")
		/**
		 * Called when the activity is no longer visible to the user.
		 * This may happen because it is being destroyed, or because another activity (either an existing one or a new one) has been resumed and is covering it.
		 * Followed either by onRestart() if the activity is coming back to interact with the user, or by onDestroy() if this activity is going away.
		 */
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// The activity is about to be destroyed.
		/**
		 * Called before the activity is destroyed.
		 * This is the final call that the activity will receive.
		 * It could be called either because the activity is finishing (someone called finish() on it), or because the system is temporarily destroying this instance of the activity to save space.
		 * You can distinguish between these two scenarios with the isFinishing() method.
		 */
	}
	
	protected OnClickListener columnEditListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Date currentDate;
			switch (v.getId()){
			case R.id.addColumnButton:
				currentDate = new Date(System.currentTimeMillis());
				boolean added = TableMachineTypeList.updateMachineTypeList(dbHelper,new MachineTypeList(
						null,null,"New List",currentDate,1,currentDate,false,null
						));
				if (!added){
					Log.w("Main Activity","Column Add Failed");
				} else {
					typesList = dbHelper.readLists();
					OuterListView.setAdapter(AdapterByState());
				}
				break;
			case R.id.removeColumnButton:
				currentDate = new Date(System.currentTimeMillis());
				//TODO
				typesList = dbHelper.readLists();
				MachineTypeList removelist = typesList.get(typesList.size()-1);
				removelist.setDeleted(true);
				removelist.setDateDeleted(currentDate);
				boolean removed = TableMachineTypeList.updateMachineTypeList(dbHelper,removelist);
				if (!removed){
					Log.w("Main Activity","Column Delete Failed");
				} else {
					typesList = dbHelper.readLists();
					OuterListView.setAdapter(AdapterByState());
				}
				break;
			}
		}//onClick
	};//columnEditListener
	
	protected OnClickListener orderByListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()){
			case R.id.orderByNameButton:
				setState(STATE_SORTBY_NAME);
				break;
			case R.id.orderByGreaseButton:
				setState(STATE_SORTBY_GREASED);
				break;
			case R.id.orderByMaintenanceButton:
				setState(STATE_SORTBY_MAINTENANCE);
				break;
			}
		}//onClick
	};//sortByListener
	
	private void setState(int newState) {
		if(this.state != newState){
			if(newState == STATE_SORTBY_NAME){
				Log.d("MainActivity","Sortby to name");
				orderByName.setChecked(true);
				orderByGrease.setChecked(false);
				orderByMaintenance.setChecked(false);
			} else if(newState == STATE_SORTBY_GREASED){
				Log.d("MainActivity","Sortby to grease");
				orderByName.setChecked(false);
				orderByGrease.setChecked(true);
				orderByMaintenance.setChecked(false);
			} else if(newState == STATE_SORTBY_MAINTENANCE) {
				Log.d("MainActivity","Sortby to maintenance");
				orderByName.setChecked(false);
				orderByGrease.setChecked(false);
				orderByMaintenance.setChecked(true);
			}

			this.state = newState;
			OuterListView.setAdapter(AdapterByState());
		}	
	}
	
	private MachineTypeListArrayAdapter AdapterByState() {
		String s = searchEditText.getText().toString();
		return AdapterByState(s);
	}
	private MachineTypeListArrayAdapter AdapterByState(String searched) {
		return new MachineTypeListArrayAdapter(
				getBaseContext(),R.layout.column_item_1,typesList,R.layout.row_item_1,dbHelper,state,searched
				);
	}
}