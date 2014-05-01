package com.OpenATK.machineryapp.listfrag;

import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.OpenATK.machineryapp.R;
import com.OpenATK.machineryapp.db.DatabaseHelper;
import com.OpenATK.machineryapp.db.TableMachineTypeList;
import com.OpenATK.machineryapp.listfrag.MachineTypeListArrayAdapter;
import com.OpenATK.machineryapp.models.Machine;
import com.OpenATK.machineryapp.models.MachineTypeList;

public class FragmentList extends Fragment {
	public static int STATE_SORTBY_NAME = 0;
	public static int STATE_SORTBY_GREASED = 1;
	public static int STATE_SORTBY_MAINTENANCE = 2;
	
	private int sortByState;
	
	private DatabaseHelper dbHelper;
	Context context;
	private FragmentListListener listener;
		
	private EditText searchEditText = null;

	HListView OuterListView;
	
	ToggleButton orderByName;
	ToggleButton orderByGrease;
	ToggleButton orderByMaintenance;
	
	private List<MachineTypeList> typesList = new ArrayList<MachineTypeList>();
	
	// Interface for receiving data
	public interface FragmentListListener {
		public void FragmentList_Init(); //This -> Listener
		public void FragmentList_Done(Machine machine);  //This -> Listener
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, container,false);
				
		if (savedInstanceState == null) {
			// First incarnation of this activity.
			setState(STATE_SORTBY_NAME);
		} else {
			// Reincarnated activity.
		}
		
		searchEditText = (EditText) view.findViewById(R.id.searchByEditText);
		orderByName = (ToggleButton) view.findViewById(R.id.orderByNameButton);
		orderByGrease = (ToggleButton) view.findViewById(R.id.orderByGreaseButton);		
		orderByMaintenance = (ToggleButton) view.findViewById(R.id.orderByMaintenanceButton);		
		OuterListView = (HListView) view.findViewById(R.id.outerMachineListView);
		Button addColumn = (Button) view.findViewById(R.id.addColumnButton);
		Button removeColumn = (Button) view.findViewById(R.id.removeColumnButton);
				
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
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof FragmentListListener) {
			listener = (FragmentListListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + " must implement FragmenList.FragmentListListener");
		}
		Log.d("FragmentList", "Attached");
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
		if(this.sortByState != newState){
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

			this.sortByState = newState;
			OuterListView.setAdapter(AdapterByState());
		}	
	}
	
	private MachineTypeListArrayAdapter AdapterByState() {
		String s = searchEditText.getText().toString();
		return AdapterByState(s);
	}
	private MachineTypeListArrayAdapter AdapterByState(String searched) {
		return new MachineTypeListArrayAdapter(
				context,R.layout.column_item_1,typesList,R.layout.row_item_1,dbHelper,sortByState,searched,listener
				);
	}

	public int getHeight() {
		// Method so close transition can work
		return getView().getHeight();
	}

	public void closing(){
		
	}

	public void setdbHelper(DatabaseHelper dbHelper) {
		this.dbHelper = dbHelper;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
}
