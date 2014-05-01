package com.OpenATK.machineryapp;

import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.List;

import com.OpenATK.machineryapp.db.DatabaseHelper;
import com.OpenATK.machineryapp.models.MachineTypeList;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

@TargetApi(11)
public class MainActivity extends Activity {
	
	private EditText searchEditText = null;
	private DatabaseHelper dbHelper;
	
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
		} else {
			// Reincarnated activity.
		}
		
		HListView OuterListView = (HListView) this.findViewById(R.id.outerMachineListView);
		typesList = dbHelper.readLists();
		
		OuterListView.setAdapter(new MachineTypeListArrayAdapter(
				getBaseContext(),R.layout.column_item_1,typesList,R.layout.row_item_1,dbHelper
				));
		
		searchEditText = (EditText) this.findViewById(R.id.searchByEditText);
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
				//TODO
				//if(fragmentListView != null) fragmentListView.search(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}			
		});
		
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
}