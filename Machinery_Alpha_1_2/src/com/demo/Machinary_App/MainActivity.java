package com.demo.Machinary_App;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.List;
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
	Button scrollButton;
	MachineAdapter mHorAdapter;
	
	//SQLite
	private MachineDataSource datasource;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		setContentView( R.layout.activity_main );
		
		//SQLite datasource
		datasource = new MachineDataSource(this);
	    datasource.open();

	    String[] allColumns = datasource.getColumnNames();
		
		//HorListView initial populating
		List<String> elementList = new ArrayList<String>();
		List<List<Machine>> cardList = new ArrayList<List<Machine>>();
		
		for (int i = 1; i < allColumns.length; i++) {
			int j;
			elementList.add( allColumns[i] );
			List<Machine> cardlist = new ArrayList<Machine>();
			List<Machine> machineList = datasource.getMachineByList(i);
			for (j = 0; j < machineList.size(); j++) {
				cardlist.add(datasource.getMachineByList(i).get(j));
			}
			if ( j == 0 ) {
				datasource.createMachine("Empty",i,"");
				cardlist.add(datasource.getMachineByList(i).get(j));
			}
			cardList.add(cardlist);
		}
		elementList.add( lasttype ); //this allows the add/remove element buttons to appear
		
		mHorAdapter = new MachineAdapter( this, elementList, cardList, mHorListView, datasource );
		
		mHorListView.setHeaderDividersEnabled( true );
		mHorListView.setFooterDividersEnabled( true );
		mHorListView.setAdapter( mHorAdapter );
		mHorListView.setOnItemClickListener( this );
		
		scrollButton.setOnClickListener( this );
	}
	
	@Override
	public void onContentChanged() {
		super.onContentChanged();
		mHorListView = (HListView) findViewById( R.id.hListView1 );
		scrollButton = (Button) findViewById( R.id.button3 );
		searchEditText = (EditText) findViewById(R.id.edittext);
		searchButton = (Button) findViewById(R.id.searchbutton);
	}

	@Override
	public void onClick( View v ) {
		final int id = v.getId();
		if( id == scrollButton.getId() ) {
			scrollList();
		}
	}	
	
	private void scrollList() {
		mHorListView.smoothScrollBy( 406, 0 );
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