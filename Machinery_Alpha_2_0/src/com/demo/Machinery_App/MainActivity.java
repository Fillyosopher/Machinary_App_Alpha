package com.demo.Machinery_App;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.List;

import com.demo.Machinary_App.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

@TargetApi(11)
public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {
	//Static string declarations
	private static final String LOG_TAG = "MainActivity";
	private static String lasttype = "Button1type";
	
	//Main View Declarations of layout/activity_main
	EditText searchEditText;
	Button searchButton;
	HListView mHorListView;
	MachineAdapter mHorAdapter;
	
	//SQLite
	private CoreDataSource datasource;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		setContentView( R.layout.activity_main );
		
		//SQLite datasource
		datasource = new CoreDataSource(this);
	    datasource.open();
	    
	    datasource.addList(new List2(0,"New list","Name"));
		
	    //HorListView initial populating
		//This converts the new datasource to the old viewer
	    //eventually, this should be converted.
	    List<String> elementList = new ArrayList<String>();
	    List<List<Machine>> cardList = new ArrayList<List<Machine>>();
	    List<Machine> machineList = datasource.getAllMachines();
	    List<List2> listList = datasource.getAllLists();
		
		Log.i( LOG_TAG, "First Machine: " + String.valueOf( machineList.get(0).getName() ) );
		Log.i( LOG_TAG, "First List: " + String.valueOf( listList.get(0).getName() ) );
		
		for (int i = 0; i < listList.size() ; i++) {
			int j;
			elementList.add( listList.get(i).getName() );
			List<Machine> cardlist = new ArrayList<Machine>();
			for (j = 0; j < machineList.size(); j++) {
				if (machineList.get(j).getList() == i){
					cardlist.add(machineList.get(j));	
				}
			}
			if ( j == 0 ) {
			    datasource.addMachine(new Machine(0,"New Machine",0,0,0,0,"red"));
			    cardlist.add(machineList.get(j));
			}
			cardList.add(cardlist);
		}
		elementList.add( lasttype ); //this allows the add/remove element buttons to appear

		Log.i( LOG_TAG, "First card now: " + String.valueOf( cardList.get(0).get(0).getName() ) );
		Log.i( LOG_TAG, "First list now: " + elementList.get(0) );
		mHorAdapter = new MachineAdapter( this, elementList, cardList, mHorListView, datasource );
		
		mHorListView.setHeaderDividersEnabled( true );
		mHorListView.setFooterDividersEnabled( true );
		mHorListView.setAdapter( mHorAdapter );
		mHorListView.setOnItemClickListener( this );
	}
	
	@Override
	public void onContentChanged() {
		super.onContentChanged();
		mHorListView = (HListView) findViewById( R.id.hListView1 );
		searchEditText = (EditText) findViewById(R.id.edittext);
	}

	@Override
	public void onClick( View v ) {
		//final int id = v.getId();
	}	
	
	@Override
	public void onItemClick( AdapterView<?> parent, View veiw, int position, long id ) {
		Log.i( LOG_TAG, "onItemClick: " + position );
	}
	
	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
}