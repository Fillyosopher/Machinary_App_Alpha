package com.OpenATK.machineryapp;

import com.OpenATK.machineryapp.db.DatabaseHelper;
import com.OpenATK.machineryapp.db.TableMachine;
import com.OpenATK.machineryapp.listfrag.FragmentList;
import com.OpenATK.machineryapp.listfrag.FragmentList.FragmentListListener;
import com.OpenATK.machineryapp.machinefrag.FragmentMachine;
import com.OpenATK.machineryapp.machinefrag.FragmentMachine.FragmentMachineListener;
import com.OpenATK.machineryapp.models.Machine;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

@TargetApi(11)
public class MainActivity  extends FragmentActivity implements FragmentListListener, FragmentMachineListener {

	FragmentList fragmentList = null;
	FragmentMachine fragmentMachine = null;

	Machine currentMachine = null;

	private DatabaseHelper dbHelper;

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

		dbHelper = new DatabaseHelper(this);

		//Get fragments
		FragmentManager fm = getSupportFragmentManager();
		fragmentList = (FragmentList) fm.findFragmentByTag("list");
		fragmentMachine = (FragmentMachine) fm.findFragmentByTag("machine");
		Log.d("MainActivity","Point5");
		
		this.fragmentMachine.setdbHelper(dbHelper);
		this.fragmentMachine.setContext(getBaseContext());
		this.fragmentMachine.setMachine(currentMachine);
		this.fragmentList.setdbHelper(dbHelper);
		this.fragmentList.setContext(getBaseContext());
		
		setContentView(R.layout.activity_main);
		
		FragmentList_Init();
		Log.d("MainActivity","Point6");
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


	private Void showFragmentList(Boolean transition) {
		if (this.fragmentList == null || this.fragmentList.isVisible() == false) {
			hideFragmentMachine(true);
			FrameLayout layout = (FrameLayout) findViewById(R.id.fragment_container_list);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
			params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
			layout.setLayoutParams(params);

			FragmentManager fm = getSupportFragmentManager();
			FragmentList fragment = new FragmentList();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.fragment_container_list, fragment, "list");
			ft.commit();

			fragmentList = fragment;
			fragment.setRetainInstance(true);
		}
		return null;
	}

	private void hideFragmentList(Boolean transition) {
		if (this.fragmentList != null && this.fragmentList.isVisible()) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentList fragment = (FragmentList) fm.findFragmentByTag("machine");

			if(fragment != null){
				fragment.closing();
			}

			// Set height so transition works
			FrameLayout layout = (FrameLayout) findViewById(R.id.fragment_container_list);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
			params.height = fragment.getHeight();
			layout.setLayoutParams(params);
			// Do transition
			FragmentTransaction ft = fm.beginTransaction();
			ft.hide(fragment);
			ft.commit();
			fragmentList = null;
		}
	}
	private Void showFragmentMachine(Boolean transition) {
		if (this.fragmentMachine == null || this.fragmentMachine.isVisible() == false) {
			hideFragmentList(true);
			FrameLayout layout = (FrameLayout) findViewById(R.id.fragment_container_machine);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
			params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
			layout.setLayoutParams(params);

			FragmentManager fm = getSupportFragmentManager();
			FragmentMachine fragment = new FragmentMachine();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.fragment_container_machine, fragment, "machine");
			ft.commit();

			fragmentMachine = fragment;
			fragment.setRetainInstance(true);
		}
		return null;
	}

	private void hideFragmentMachine(Boolean transition) {
		if (this.fragmentMachine != null && this.fragmentMachine.isVisible()) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentMachine fragment = (FragmentMachine) fm.findFragmentByTag("machine");

			if(fragment != null){
				fragment.closing();
			}

			// Set height so transition works
			FrameLayout layout = (FrameLayout) findViewById(R.id.fragment_container_machine);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
			params.height = fragment.getHeight();
			layout.setLayoutParams(params);
			// Do transition
			FragmentTransaction ft = fm.beginTransaction();
			ft.hide(fragment);
			ft.commit();
			fragmentMachine = null;
		}
	}

	@Override
	public void FragmentList_Init() {
		if(this.fragmentList != null){
			this.fragmentList.setdbHelper(dbHelper);
			this.fragmentList.setContext(getBaseContext());
			showFragmentList(true);
		}

	}

	@Override
	public void FragmentList_Done(Machine machine) {
		if(this.fragmentList != null){
			currentMachine = machine;
			hideFragmentList(true);
			FragmentMachine_Init();
		}

	}

	@Override
	public void FragmentMachine_Init() {
		if(this.fragmentMachine != null){
			this.fragmentMachine.setdbHelper(dbHelper);
			this.fragmentMachine.setContext(getBaseContext());
			this.fragmentMachine.setMachine(currentMachine);
			showFragmentMachine(true);
		}
	}

	@Override
	public void FragmentMachine_Done(Machine machine) {
		if(this.fragmentMachine != null){
			TableMachine.updateMachine(dbHelper, machine);
			hideFragmentMachine(true);
			FragmentList_Init();
		}
	}
}