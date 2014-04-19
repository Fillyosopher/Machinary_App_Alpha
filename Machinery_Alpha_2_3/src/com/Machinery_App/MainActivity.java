package com.Machinery_App;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

@TargetApi(11)
public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {
	//Static string declarations
	private static final String LOG_TAG = "MainActivity";
	
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
		
	    Button btnNextScreen = (Button) findViewById(R.id.switchview);
	    btnNextScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), MachineActivity.class);
                startActivity(nextScreen);
            }
        });
	    
		//initial table populate
		if (datasource.updateCheck()){
			Log.i(LOG_TAG, "Update flag Caught");
		    datasource.addList(new List2(1,"New List","Name"));
			datasource.addMachine(new Machine(1,"New Machine",1));
		}
		
	    // HorListView initial populating
		List<List2> listList = datasource.getAllLists();
		List<String> listNames = datasource.getListNames();
		
	    // This is to add the column buttons at the end of the horizontal list
		listNames.add("Buttons");
		listList.add(new List2(1,"Buttons","nope")); 
		
		//Set 
	    mHorListView.setAdapter( new MachineAdapter( this, listNames, listList, datasource) );
		
		mHorListView.setHeaderDividersEnabled( true );
		mHorListView.setFooterDividersEnabled( true );
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