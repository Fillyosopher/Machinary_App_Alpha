package com.demo.Machinary_App;

import android.R.color;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;

public class MachineActivity extends Activity {
	/** Called when the activity is first created. */
	Bundle machine = new Bundle();
	EditText textLastS, textLastG, textYear , textPurch, text , Oilfilter , Transfilter ,Hydfilter, Hydpump;
	private static final String LOG_TAG = "MachineActivity";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.activity_machine);
		text= (EditText) findViewById(R.id.MAtitle);
		text.setCursorVisible(false);
		text.setTag(R.id.MAtitle);
		text.setBackgroundColor(Color.TRANSPARENT);
		machine = getIntent().getExtras();
		text.setText(machine.getString("Machine"));
		Log.w(LOG_TAG,"Datasource check = "+MainActivity.datasource.getMachinesCount());
		Cursor mcursor = MainActivity.datasource.database.rawQuery("SELECT * FROM "+MainActivity.datasource.helper.tables[0].TABLE_NAME+" WHERE Name = '"+machine.getString("Machine")+"'", null);
		if(mcursor != null){
			TextView Ptag = (TextView) findViewById(R.id.PurchaseTag);
			Ptag.setText("Purchase Date : ");
			TextView Ytag = (TextView) findViewById(R.id.YearTag);
			Ytag.setText("Model Year : ");
			TextView LastGtag = (TextView) findViewById(R.id.LastGTag);
			LastGtag.setText("Last Greased : ");
			TextView LastStag = (TextView) findViewById(R.id.LastSTag);
			LastStag.setText("Last Serviced : ");
			mcursor.moveToFirst();
			textPurch = (EditText) findViewById(R.id.MAinfoPur);
			textPurch.setEnabled(true);
			textPurch.setTag(R.id.MAinfoPur);
			textPurch.setCursorVisible(false);
			textPurch.setBackgroundColor(Color.TRANSPARENT);
			//textPurch.setOnClickListener(clickhandle);
			textPurch.setText(mcursor.getString(4));
			textYear = (EditText) findViewById(R.id.MAinfoYear);
			textYear.setText(mcursor.getString(5));
			//textYear.setOnClickListener(clickhandle);
			textYear.setCursorVisible(false);
			textYear.setTag(R.id.MAinfoYear);
			textYear.setBackgroundColor(Color.TRANSPARENT);
			textLastG = (EditText) findViewById(R.id.MAinfoLastG);
			textLastG.setText(mcursor.getString(6));
			textLastG.setTag(R.id.MAinfoLastG);
			//textLastG.setOnClickListener(clickhandle);
			textLastG.setCursorVisible(false);
			textLastG.setBackgroundColor(Color.TRANSPARENT);
			textLastS = (EditText) findViewById(R.id.MAinfoLastS);
			textLastS.setText(mcursor.getString(7));
			textLastS.setTag(R.id.MAinfoLastS);
			//textLastS.setOnClickListener(clickhandle);
			textLastS.setCursorVisible(false);
			textLastS.setBackgroundColor(Color.TRANSPARENT);
			Oilfilter = (EditText)findViewById(R.id.MAOilfil);
			Oilfilter.setCursorVisible(false);
			Oilfilter.setTag(R.id.MAOilfil);
			Oilfilter.setText(mcursor.getString(8));
			Oilfilter.setBackgroundColor(Color.TRANSPARENT);
			Transfilter = (EditText)findViewById(R.id.MATransfilter);
			Transfilter.setCursorVisible(false);
			Transfilter.setTag(R.id.MATransfilter);
			Transfilter.setText(mcursor.getString(9));
			Transfilter.setBackgroundColor(Color.TRANSPARENT);
			Hydfilter = (EditText)findViewById(R.id.MAHydfilter);
			Hydfilter.setCursorVisible(false);
			Hydfilter.setTag(R.id.MAHydfilter);
			Hydfilter.setText(mcursor.getString(10));
			Hydfilter.setBackgroundColor(Color.TRANSPARENT);
			Hydpump = (EditText)findViewById(R.id.MAHydpump);
			Hydpump.setCursorVisible(false);
			Hydpump.setText(mcursor.getString(11));
			Hydpump.setBackgroundColor(Color.TRANSPARENT);
			Hydpump.setTag(R.id.MAHydpump);
		}

		ActionBar actionBar = this.getActionBar();
		// add the custom view to the action bar
		actionBar.setCustomView(R.layout.machine_actionbar);
		((TextView)actionBar.getCustomView().findViewById(R.id.pgtitle)).setText("Machine Information");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
		Button edit_info = (Button) actionBar.getCustomView().findViewById(R.id.edit_MAinfo);
		actionBar.show();
		edit_info.setOnClickListener(editlistener);

		//Purchase = (EditText) findViewById(R.id.MAinfoPur);
		/*Purchase.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			TextView textPurch = (TextView)v.findViewById(R.id.MAinfoPur);
			//textPurch.setEnabled(false);
			//textPurch.setCursorVisible(false);

			textPurch.setVisibility(View.INVISIBLE);
			Purchase.requestFocus();
			Purchase.setVisibility(View.VISIBLE);
			Purchase.setCursorVisible(true);
			//Purchase.setBackgroundColor(COLOR.);
			Purchase.setOnEditorActionListener(new TextView.OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					if(actionId == EditorInfo.IME_ACTION_SEARCH ||
				            actionId == EditorInfo.IME_ACTION_DONE ||
				            event.getAction() == KeyEvent.ACTION_DOWN &&
				            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
						v.setText(Purchase.getText());
						//v.setEnabled(false);
						//v.setCursorVisible(false);
						Purchase.clearFocus();
						Toast.makeText(MachineActivity.this,(CharSequence) machine.get("Machine"), Toast.LENGTH_SHORT);
						String SQL = "UPDATE " +MachineTable.TABLE_NAME+" SET "+MachineTable.COLUMN_NAMES[3]+" WHERE "+MachineTable.COLUMN_NAMES[0]+" = "+machine.getString("Machine");
						MainActivity.datasource.database.execSQL(SQL);
						Purchase.setVisibility(View.INVISIBLE);
						v.setVisibility(View.VISIBLE);
					}
					return false;
				}
			});

			Purchase.setOnFocusChangeListener(fhanfler);
		}
	});*/

		// ListView mlistv = (ListView)findViewById(R.id.maintenancelistview);
		//ListAdapter mtlist_adapter = new ArrayAdapter<String>(null, 0);
		//mlistv.setAdapter(mtlist_adapter);
		Button backButton = (Button) findViewById(R.id.MAbackbutton);
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				//Closing SecondScreen Activity
				finish();
			}
		});
	}
	View.OnClickListener editlistener = new View.OnClickListener(){
		boolean edittable = false;
		@Override
		public void onClick(View v) {
			if(!edittable){
				edittable = true;
				((Button)v.findViewById(R.id.edit_MAinfo)).setText("Done");
				text.setBackgroundColor(color.white);
				text.setOnClickListener(clickhandle);
				textPurch.setOnClickListener(clickhandle);
				textPurch.setBackgroundColor(color.white);
				textYear.setOnClickListener(clickhandle);
				textYear.setBackgroundColor(color.white);
				textLastG.setOnClickListener(clickhandle);
				textLastG.setBackgroundColor(color.white);
				textLastS.setOnClickListener(clickhandle);
				textLastS.setBackgroundColor(color.white);			
				Oilfilter.setOnClickListener(clickhandle);
				Oilfilter.setBackgroundColor(color.white);
				Transfilter.setOnClickListener(clickhandle);
				Transfilter.setBackgroundColor(color.white);
				Hydfilter.setOnClickListener(clickhandle);
				Hydfilter.setBackgroundColor(color.widget_edittext_dark);
				Hydpump.setOnClickListener(clickhandle);
				Hydpump.setBackgroundColor(color.widget_edittext_dark);
			} else {
				edittable = false;
				text.setCursorVisible(false);
				text.setOnClickListener(null);
				textPurch.setCursorVisible(false);
				textPurch.setOnClickListener(null);
				textYear.setCursorVisible(false);
				textYear.setOnClickListener(null);
				textLastG.setCursorVisible(false);
				textLastG.setOnClickListener(null);
				textLastS.setCursorVisible(false);	
				textLastS.setOnClickListener(null);	
				Oilfilter.setCursorVisible(false);
				Oilfilter.setOnClickListener(null);
				Transfilter.setCursorVisible(false);
				Transfilter.setOnClickListener(null);
				Hydfilter.setCursorVisible(false);
				Hydfilter.setOnClickListener(null);
				Hydpump.setCursorVisible(false);
				Hydpump.setOnClickListener(null);
				((Button)v.findViewById(R.id.edit_MAinfo)).setText("Edit Info");
			}
			return;
		}
	};

	/* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.layout.machine_actionbar, menu);
      return true;
    }*/

	View.OnClickListener clickhandle = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			EditText text = (EditText)v.findViewById(Integer.parseInt(v.getTag().toString()));
			text.setBackgroundColor(color.widget_edittext_dark);
			//textPurch.setEnabled(false);
			//textPurch.setCursorVisible(false);
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			Cursor cur = MainActivity.datasource.database.rawQuery("SELECT * FROM "+MachineTable.TABLE_NAME+" WHERE "+MachineTable.COLUMN_NAMES[0]+" = '"+machine.getString("Machine")+"'",null);
			text.setVisibility(View.INVISIBLE);
			text.requestFocus();
			text.setCursorVisible(true);
			text.setVisibility(View.VISIBLE);
			text.setCursorVisible(true);
			text.setTag(v.getTag());
			//Purchase.setBackgroundColor(COLOR.);
			/*	Purchase.setOnEditorActionListener(new TextView.OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					if(actionId == EditorInfo.IME_ACTION_SEARCH ||
				            actionId == EditorInfo.IME_ACTION_DONE ||
				            event.getAction() == KeyEvent.ACTION_DOWN &&
				            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
						v.setText(Purchase.getText());
						//v.setEnabled(false);
						//v.setCursorVisible(false);
						Purchase.clearFocus();
						Toast.makeText(MachineActivity.this,(CharSequence) machine.get("Machine"), Toast.LENGTH_SHORT);
						String SQL = "UPDATE " +MachineTable.TABLE_NAME+" SET "+MachineTable.COLUMN_NAMES[3]+" WHERE "+MachineTable.COLUMN_NAMES[0]+" = "+machine.getString("Machine");
						MainActivity.datasource.database.execSQL(SQL);
						Purchase.setVisibility(View.INVISIBLE);
						v.setVisibility(View.VISIBLE);
					}
					return false;
				}
			});*/

			//text.setOnEditorActionListener(actionhandle);
			text.setOnFocusChangeListener(fhanfler);
		}
	};

	TextView.OnEditorActionListener actionhandle = new TextView.OnEditorActionListener() {

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			/*if(actionId == EditorInfo.IME_ACTION_SEARCH ||
		            actionId == EditorInfo.IME_ACTION_DONE ||
		            event.getAction() == KeyEvent.ACTION_DOWN &&
		            event.getKeyCode() == KeyEvent.KEYCODE_ENTER)*/if(actionId == 0) {
		            	Log.w(LOG_TAG,"Id = "+actionId);
		            	v.setText(v.getText());
		            	//v.setEnabled(false);
		            	//v.setCursorVisible(false);
		            	v.clearFocus();
		            	Toast.makeText(MachineActivity.this,(CharSequence) machine.get("Machine"), Toast.LENGTH_SHORT);
		            	int col = 18;
		            	//Log.w(LOG_TAG,"text checkk : MA.id.MAinfPur : "+R.id.MAinfoPur+"Tagged id : "+Integer.parseInt(v.getTag().toString()));
		            	if(Integer.parseInt(v.getTag().toString()) == R.id.MAinfoPur)
		            		col = 3;
		            	else if(Integer.parseInt(v.getTag().toString()) == R.id.MAinfoYear)
		            		col = 2;
		            	else if(Integer.parseInt(v.getTag().toString()) == R.id.MAinfoLastG)
		            		col = 4;
		            	else if(Integer.parseInt(v.getTag().toString()) == R.id.MAinfoLastS)
		            		col = 5;
		            	else if(Integer.parseInt(v.getTag().toString()) == R.id.MAOilfil)
		            		col = 7;
		            	else if(Integer.parseInt(v.getTag().toString()) == R.id.MATransfilter)
		            		col = 8;
		            	else if(Integer.parseInt(v.getTag().toString()) == R.id.MAHydfilter)
		            		col = 9;
		            	else if(Integer.parseInt(v.getTag().toString()) == R.id.MAHydpump)
		            		col = 10;
		            	else if(Integer.parseInt(v.getTag().toString()) == R.id.MAtitle)
		            		col = 0;
		            	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		            	//Log.w(LOG_TAG,"Check Purchase modification = "+MainActivity.this.datasource.database.rawQuery("SELECT "+MachineTable.COLUMN_NAMES[3]+" FROM "+MachineTable.TABLE_NAME+" WHERE "+MachineTable.COLUMN_NAMES[0]+" = "+machine.getString("machine"),null));
		            	String SQL = "UPDATE " +MachineTable.TABLE_NAME+" SET "+MachineTable.COLUMN_NAMES[col]+" = '"+v.getText().toString()+"' WHERE "+MachineTable.COLUMN_NAMES[0]+" = '"+machine.getString("Machine")+"'";
		            	MainActivity.datasource.database.execSQL(SQL);
		            	v.setVisibility(View.VISIBLE);
		            }
		            return false;
		}
	};

	View.OnFocusChangeListener fhanfler = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			/* When focus is lost check that the text field
			 * has valid values.*/
			if (!hasFocus) {
				TextView tv = (TextView)v.findViewById(Integer.parseInt(v.getTag().toString()));
				tv.setText(tv.getText());
				//v.setEnabled(false);
				//v.setCursorVisible(false);
				tv.clearFocus();
				Toast.makeText(MachineActivity.this,(CharSequence) machine.get("Machine"), Toast.LENGTH_SHORT);
				int col = 18;
				//Log.w(LOG_TAG,"text checkk : MA.id.MAinfPur : "+R.id.MAinfoPur+"Tagged id : "+Integer.parseInt(v.getTag().toString()));
				if(Integer.parseInt(v.getTag().toString()) == R.id.MAinfoPur)
					col = 3;
				else if(Integer.parseInt(v.getTag().toString()) == R.id.MAinfoYear)
					col = 2;
				else if(Integer.parseInt(v.getTag().toString()) == R.id.MAinfoLastG)
					col = 4;
				else if(Integer.parseInt(v.getTag().toString()) == R.id.MAinfoLastS)
					col = 5;
				else if(Integer.parseInt(v.getTag().toString()) == R.id.MAOilfil)
					col = 7;
				else if(Integer.parseInt(v.getTag().toString()) == R.id.MATransfilter)
					col = 8;
				else if(Integer.parseInt(v.getTag().toString()) == R.id.MAHydfilter)
					col = 9;
				else if(Integer.parseInt(v.getTag().toString()) == R.id.MAHydpump)
					col = 10;
				else if(Integer.parseInt(v.getTag().toString()) == R.id.MAtitle)
					col = 0;
				String SQL = "UPDATE " +MachineTable.TABLE_NAME+" SET "+MachineTable.COLUMN_NAMES[col]+" = '"+tv.getText().toString()+"' WHERE "+MachineTable.COLUMN_NAMES[0]+" = '"+machine.getString("Machine")+"'";
				MainActivity.datasource.database.execSQL(SQL);
				MainActivity.mAdapter.notifyDataSetChanged();
				tv.setVisibility(View.VISIBLE);
			}
		}
	};
}