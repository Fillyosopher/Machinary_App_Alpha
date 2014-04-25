package com.demo.Machinary_App;

import com.demo.Machinary_App.*;

import it.sephiroth.android.library.util.v11.MultiChoiceModeListener;
import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import android.R.color;
import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//import ExpandableListAdapter.ViewHolder;
//Thumbnalils.UTIl


@TargetApi(11)
public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {
	
	private static final String LOG_TAG = "MainActivity";
	EditText mEdit;
	Button searchButton;
	HListView listView;
	Button mButton1;
	Button mButton2;
	Button mButton3;
	List<Machine> machinelist;
	static CoreDataSource datasource;
	List<String> items;
	List<List<String>> cardlist;
	public Filter expfilter;
	String sortby = null;

	Button[] mButtonexp = {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,};
	static TestAdapter mAdapter;
	ListViewListAdapter[] mSAdapters = {null, null, null, null, null, null, null, null, null, null};// this is temporary and BAD CODE
	List<HashMap<String, List<String>>> collection;// =  new LinkedHashMap<String, List<String>>();
	//Trello-like card strings
	String[] typestring = new String[] {"MachineType1", "MachineType2", "MachineType3", "MachineType4"};
	List<List2> machlist = new ArrayList<List2>();
	Button sortbyMachine, sortbyGrease, sortbyMain;
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		setContentView( R.layout.activity_main );
		datasource = new CoreDataSource(this);
		datasource.open();
		datasource.getWritableDatabase();
		/*datasource.database.execSQL("DROP TABLE IF EXISTS "+datasource.helper.tables[1].TABLE_NAME);
		datasource.database.execSQL("DROP TABLE IF EXISTS "+datasource.helper.tables[0].TABLE_NAME);
		datasource.helper.onCreate(datasource.database);*/
		//datasource.database.execSQL("Delete From "+datasource.helper.tables[1].TABLE_NAME);
		//datasource.helper.onUpgrade(datasource.database, 1, 2);
		//datasource.databaseMachine.delete()(datasource.databaseMachine,null,1);
		//datasource.databaseMachine.execSQL("Delete From "+datasource.dbMHelper.TABLE_NAME);
		//datasource.databaseMachine.rawQuery("Delete From "+datasource.dbMHelper.TABLE_NAME, null);
		items = new ArrayList<String>();
		Log.w(LOG_TAG,"List table size = "+datasource.getListsCount());
		for( int i = 0; i < typestring.length; i++ ) {
			//datasource.addList(new List2(i+1,typestring[i],"Machines"));
			items.add(datasource.getAllLists().get(i).getName());
		}
		
		
		String[][] machineinfo = new String[][]{ { "Hydraulic (big): CNH Ultra 84372057", "MachineType1","2008", "1-1-2013","2-13-2013","9-4-2013","xxxxxxxxx23","xxxxxx23","24462771xx","xyxyxx21"
	     }, { "Oil: CNH 504192850", "MachineType2", "2012", "1-3-2014","3-12-2014","4-1-2014","xxxxxxxxx23","xxxxxx23","24462771xx","xyxyxx21"}, { "Oil: Manutou J608773", "MachineType3", "2011","12-24-2011","21-5-2013","31-3-2014" ,"xxxxxxxxx23","xxxxxx23","24462771xx","xyxyxx21"}, {"Hub cap: SKF 1743", "MachineType4", "2013","3-3-2012","2-30-2014","6-15-2013","xxxxxxxxx23","xxxxxx23","24462771xx","xyxyxx21"}};
	
		
		/*TextView view = new TextView(this);
		view.setText("asdasd");
		PopupWindow popoup = new PopupWindow(view, 100, 100);
		        popoup.setWindowLayoutMode(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		        // set window at position
	        	popoup.showAtLocation(getWindow().getDecorView(), BIND_ABOVE_CLIENT, 100, 100);*/

		
		String cardstring[] = {"2008 STX 450 Quadtrack", "2012 Case 450 Quadtrack","H & Silage","2012 Manitou Telehandler"};
		
		/*for (int i = 0; i < cardstring.length; i++) {
				Machine machine =  new Machine(i, cardstring[i], typestring[i], Integer.parseInt(machineinfo[i][2]),machineinfo[i][3],machineinfo[i][4],machineinfo[i][5],"Nil",machineinfo[i][6],machineinfo[i][7],machineinfo[i][8],machineinfo[i][9] );
				datasource.addMachine(machine);
			}*/
			//Log.w(LOG_TAG,"Database Size = " + datasource.getMachinesCount());
			//Cursor mcursor = datasource.databaseMachine.rawQuery("SELECT "+datasource.dbMHelper.COLUMN_NAMES[0]+" FROM "+datasource.dbMHelper.TABLE_NAME+" WHERE "+datasource.dbMHelper.COLUMN_NAMES[1]+" = "+ typestring[0], null);
	
		mAdapter = new TestAdapter( this, R.layout.test_item_1, android.R.id.text1, R.id.listview, datasource, items, null, sortby);
		listView.setHeaderDividersEnabled( true );
		listView.setFooterDividersEnabled( true );
		
		sortbyMachine = (Button)findViewById(R.id.nameOrder);
		sortbyGrease = (Button)findViewById(R.id.greaseOrder);
		sortbyMain = (Button)findViewById(R.id.maintenanceOrder);
		sortbyMachine.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				sortbyGrease.setPressed(false);
				sortbyMain.setPressed(false);
				sortbyMachine.setPressed(true);
				//datasource.database.execSQL("ALTAER TABLE "+MachineTable.TABLE_NAME+" RENAME TO INTERIM");
				//datasource.database.exec
				mAdapter.msort = "N";
				mAdapter.notifyDataSetChanged();
				//datasource.database.execSQL("UPDATE "+MachineTable.TABLE_NAME+" ORDER BY "+MachineTable.COLUMN_NAMES[0]);
			}
			
		});
		sortbyGrease.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				sortbyGrease.setPressed(true);
				sortbyMain.setPressed(false);
				sortbyMachine.setPressed(false);
				mAdapter.msort = "G";
				mAdapter.notifyDataSetChanged();
				//datasource.database.execSQL("UPDATE "+MachineTable.TABLE_NAME+" ORDER BY "+MachineTable.COLUMN_NAMES[0]);
			}
			
		});
		sortbyMain.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				sortbyGrease.setPressed(false);
				sortbyMain.setPressed(true);
				sortbyMachine.setPressed(false);
				mAdapter.msort = "M";
				mAdapter.notifyDataSetChanged();
				//datasource.database.execSQL("UPDATE "+MachineTable.TABLE_NAME+" ORDER BY "+MachineTable.COLUMN_NAMES[0]);
			}
			
		});
		if( listView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE_MODAL ) {
			listView.setMultiChoiceModeListener( new MultiChoiceModeListener() {
				
				@Override
				public boolean onPrepareActionMode( ActionMode mode, Menu menu ) {
					return true;
				}
				
				@Override
				public void onDestroyActionMode( ActionMode mode ) {
				}
				
				@Override
				public boolean onCreateActionMode( ActionMode mode, Menu menu ) {
					menu.add( 0, 0, 0, "Delete" );
					return true;
				}
				
				@Override
				public boolean onActionItemClicked( ActionMode mode, MenuItem item ) {
					Log.d( LOG_TAG, "onActionItemClicked: " + item.getItemId() );
					
					final int itemId = item.getItemId();
					if( itemId == 0 ) {
						//deleteSelectedItems();
					}
					
					mode.finish();
					return false;
				}
				
				@Override
				public void onItemCheckedStateChanged( ActionMode mode, int position, long id, boolean checked ) {
					mode.setTitle( "What the fuck!" );
					mode.setSubtitle( "Selected items: " + listView.getCheckedItemCount() );
				}
			} );
		} else if( listView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE ) {
			listView.setOnItemClickListener( this );
		}
		
		listView.setAdapter( mAdapter );
		
		mButton1.setOnClickListener( this );
		mButton2.setOnClickListener( this );
		mButton3.setOnClickListener( this );
		//mEdit = (EditText) findViewById(R.id.edittext);
		mEdit.addTextChangedListener(watcher);
		Log.i( LOG_TAG, "choice mode: " + listView.getChoiceMode() );
	}
	
	@Override
	public void onContentChanged() {
		super.onContentChanged();
		listView = (HListView) findViewById( R.id.hListView1 );
		mButton1 = (Button) findViewById( R.id.button1 );
		mButton2 = (Button) findViewById( R.id.button2 );
		mButton3 = (Button) findViewById( R.id.button3 );
		mEdit = (EditText) findViewById(R.id.edittext);
		searchButton = (Button) findViewById(R.id.searchbutton);
	}

	TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence cs, int arg1, int arg2,int arg3) {
            // ((Filter) listAdapter.getFilter()).filter(cs);
           //MainActivity.mAdapter.getFilter().filter(cs.toString());
        	MainActivity.this.mAdapter.getFilter().filter(cs.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1,
                int arg2, int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable arg0) {
        	MainActivity.this.mAdapter.getFilter().filter(arg0.toString());
        }
	};
	
	public class expFilter extends Filter{
		/*List<String> mItems = new ArrayList<Strinag>(); 
    	List<List<String>> group = new ArrayList<List<String>>();
    	List<List<Integer>> expanded = new ArrayList<List<Integer>>();*/
		//CoreDataSource data;
		TestAdapter mAdapter;
		List<Machine> mlist; 
		List<String> mtypes;
		/*public expFilter(){
			data = new CoreDataSource(MainActivity.this);
		}*/
	    	@Override
			protected FilterResults performFiltering(CharSequence arg0) {
	    	//	data = new CoreDataSource(MainActivity.this);
	    		//data.open();
	    		//data.database.execSQL("DROP TABLE IF EXISTS "+ MachineTable.TABLE_NAME, null);
	    		//data.database.execSQL("DROP TABLE IF EXISTS "+ ListTable.TABLE_NAME, null);
	    		//data.helper.onCreate(data.database);
	    		mlist = new ArrayList<Machine>();
	    		mtypes = new ArrayList<String>();
				FilterResults results1 = new FilterResults(); 
				FilterResults results2 = new FilterResults(); 
				if(arg0 == null | arg0.length() == 0){
						/*group = cardlist;
				        results1.values = collection;
				        mItems = items;*/
				      //  data = MainActivity.this.datasource;
				        results1.values = datasource;
				        this.mtypes = items;
				        mlist = null;
				       // listView.setAdapter(MainActivity.this.mAdapter);
				    }
				    else {
				    	MainActivity.this.datasource.getReadableDatabase();
				        for(int i = 0; i < MainActivity.this.datasource.getMachinesCount(); i++){
				        	if(MainActivity.this.datasource.getAllMachines().get(i).getName().toUpperCase().startsWith(((String) arg0).toUpperCase())){
				        		//data.addMachine(MainActivity.this.datasource.getAllMachines().get(i));
				        		mlist.add(MainActivity.this.datasource.getAllMachines().get(i));
				        		Log.w(LOG_TAG,"Searchdatabase size = "+ mlist.size());
				        		if(!mtypes.contains(MainActivity.this.datasource.getAllMachines().get(i).getList())){
				        			mtypes.add(MainActivity.this.datasource.getAllMachines().get(i).getList());
				        			Log.w(LOG_TAG,"Searchdatabase list type size = "+ mtypes.size());
				        		//Log.w(LOG_TAG,"Searchfiltdatabase size = "+ data.database.rawQuery("SELECT * FROM "+MachineTable.TABLE_NAME,null).getCount());
				        		//if(data.database.rawQuery("SELECT * FROM "+MachineTable.TABLE_NAME+ " WHERE "+data.helper.tables[0].COLUMN_NAMES[1]+" = '"+MainActivity.this.datasource.getAllMachines().get(i).getList()+"'", null).getCount() == 0){	
				
				        			//data.addList(new List2(0,MainActivity.this.datasource.getAllMachines().get(i).getList(),"Machine"));}}
				        }}}
			  results1.values = datasource;
	    	}
				return results1;}
			@Override
			protected void publishResults(CharSequence arg0,
					FilterResults arg1) {
				//data = new CoreDataSource(MainActivity.this);
				//Log.w(LOG_TAG,"Searchdatabase size = "+ data.databaseMachine.rawQuery("SELECT * FROM "+data.dbMHelper.TABLE_NAME, null).getCount());
				//if((CoreDataSource)arg1.values.getMachinesCount() == MainActivity.this.datasource.getMachinesCount()){
					/*mAdapter.mCards = (List<HashMap<String,List<String>>>)arg1.values ;
					mAdapter.groups = group;
					mAdapter.mItems = mItems;
					mAdapter.expand = null;
					expanded = null;
					mAdapter.data = MainActivity.this.datasource;
					mAdapter.notifyDataSetChanged();*/
				//}else
				//{
				/*	mAdapter.mCards = (List<HashMap<String,List<String>>>) arg1.values;
					mAdapter.groups = group;
					mAdapter.mItems = mItems;
					mAdapter.expand = expanded;
					mAdapter.data = data;
					mAdapter.notifyDataSetChanged();*/
				//}
				//CoreDataSource dataf = new CoreDataSource(MainActivity.this);
				
				/*dataf.database.execSQL("DELETE FROM "+MachineTable.TABLE_NAME);
				dataf.database.execSQL("DELETE FROM "+ListTable.TABLE_NAME);
				for (int i = 0; i < mlist.size(); i++){
					dataf.addMachine(mlist.get(i));
					if(dataf.database.rawQuery("SELECT * FROM "+MachineTable.TABLE_NAME+ " WHERE "+dataf.helper.tables[0].COLUMN_NAMES[1]+" = '"+mlist.get(i).getList()+"'", null).getCount() == 0){
	        			dataf.addList(new List2(0,mlist.get(i).getList(),"Machine"));}
				}*/
				TestAdapter mAdapter = new TestAdapter(MainActivity.this, R.layout.test_item_1, android.R.id.text1, R.id.listview, datasource, mtypes, mlist, null);
				MainActivity.this.listView.setAdapter(mAdapter);
				Log.w(LOG_TAG,"The adapter has been modified");
		}
	}
	@Override
	public void onClick( View v ) {
		final int id = v.getId();
		
		if( id == mButton1.getId() ) {
			//addElements();
		} else if( id == mButton2.getId() ) {
			removeElements();
		} else if( id == mButton3.getId() ) {
			scrollList();
		} else if(id == searchButton.getId()){
		} else if(id == mButtonexp[1].getId()){
			//removeGroup(findcategory());
			removeGroup(1);
		}
	}	

	
	View.OnClickListener handler1 = new View.OnClickListener(){
		@Override
		public void onClick(View arg0) {
			final Object argTag = arg0.getTag();
			int i = Integer.parseInt(argTag.toString().replace("tag",""));
			if(i % 2 == 0 ){
				addGroup(i / 2, arg0);
			}
			else{
				removeGroup((i - 1) / 2);
			}
		}
	};
	
	private void removeGroup(int category) {
		//mAdapter.mCards.get(category).remove(mAdapter.groups.get(category).size() - 1);
		//mAdapter.groups.get(category).remove(mAdapter.groups.get(category).size() - 1);
		CoreDataSource cds =  MainActivity.this.datasource;
		cds.getReadableDatabase();
		Log.w(LOG_TAG,"List delete id = " + cds.getAllLists().get(0).getId());
		String query = "SELECT * FROM "+MachineTable.TABLE_NAME+" WHERE "+cds.helper.tables[0].COLUMN_NAMES[1]+ " = '"+cds.getList(category + 1).getName()+"'"; 
		int count = cds.database.rawQuery(query,null).getCount();
		Cursor cursor = cds.database.rawQuery(query,null);
		cursor.moveToLast();
		if(cds.database.rawQuery(query,null).getCount() != 0){
			cds.deleteMachine(cds.cursor2Machine(cursor));
			mAdapter.data = cds;
			mAdapter.notifyDataSetChanged();
			//cds.databaseMachine.delete(cds.dbMHelper.TABLE_NAME, cds.dbMHelper.COLUMN_ID[0] + " = ",cds.databaseMachine.rawQuery(query,null).moveToLast().);
		}
		
		//String group = MainActivity.this.cardlist.get(category).get(MainActivity.this.cardlist.get(category).size() - 1);
		//MainActivity.this.cardlist.get(category).remove(MainActivity.this.cardlist.get(category).size() - 1);
		//MainActivity.this.collection.get(category).remove(group);
		//mAdapter = new TestAdapter(MainActivity.this, R.layout.test_item_1, android.R.id.text1, R.id.expandl, mItems, (List<HashMap<String,List<String>>>)arg1.values, group, expanded);
		mAdapter.notifyDataSetChanged();	
		
	}

	private void scrollList() {
		listView.smoothScrollBy( 1500, 300 );
	}
	
	public void addGroup(int category, View expview){//,String machine){
		//List<String> list = new ArrayList<String>();
		//list.add("Info");
		/*int index = ((Integer)MainActivity.this.items.indexOf(mAdapter.mtypes.get(category)));
		int listsize = MainActivity.this.cardlist.get(index).size() + 1;*/
		/*if(index != category){
			mAdapter.groups.get(category).add("Random" + String.valueOf(listsize));
			mAdapter.mCards.get(category).put("Random" + String.valueOf(listsize), list);}*/
		//category = index;
		/*String newgroup = mSAdapters[category].addcard(expview);
		List<String> list = new ArrayList<String>();
		list.add("Info");
		MainActivity.this.cardlist.get(category).add(newgroup);
		MainActivity.this.collection.get(category).put(newgroup, list);*/
		//mAdapter.mCards.get(category).put("Random" + String.valueOf(mAdapter.groups.get(category).size()), list);
		//mAdapter.groups.get(category).add("Random" + String.valueOf(mAdapter.groups.get(category).size()));
		/*if(mAdapter.expand != null)
			mAdapter.expand.get(category).add(0);
		mAdapter.notifyDataSetChanged();*/
		String Query = "SELECT * FROM "+ MachineTable.TABLE_NAME;
		Cursor cursor = MainActivity.this.datasource.database.rawQuery(Query, null);
//Log.w(LOG_Tag)
		Machine nmach = new Machine(cursor.getCount() + 1, "New machine", MainActivity.this.datasource.getList(category + 1).getName(),2014,"Nil","Nil","No info","Nil","xxxxxxxxx","xxxxxxxxxxxxx","xxxxxxxxxxxx","xxxxxxxxxxx");
		MainActivity.this.datasource.addMachine(nmach);
		mAdapter.data = MainActivity.this.datasource;
		mAdapter.notifyDataSetChanged();
	}
	
	private void addElements() {
		/*HashMap<String,List<String>> list = new LinkedHashMap<String,List<String>>();
		List<String> l = new ArrayList<String>();
		l.add("empty");
		list.put(l.get(0), l);
		mAdapter.mCards.add(list);
		mAdapter.groups.add(l);
		mAdapter.mItems.add( "Category " + String.valueOf( mAdapter.mItems.size()+1 ) );
		mAdapter.notifyDataSetChanged();*/
		MainActivity.this.datasource.addList(new List2(0,"New Machine Type","Machine"));
		mAdapter.data = MainActivity.this.datasource;
		mAdapter.notifyDataSetChanged();
	}
	
	
	private void removeElements() {
		CoreDataSource cds = MainActivity.this.datasource;
		Cursor cursor = cds.database.rawQuery("SELECT * FROM "+ ListTable.TABLE_NAME, null);
		int count  = cursor.getCount();
		cursor.moveToLast();
		if( count > 0 & count < 10 ) {
			/*mAdapter.mItems.remove(mAdapter.mItems.size()-1);
			mAdapter.mCards.remove(mAdapter.mItems.size());
			mAdapter.groups.remove(mAdapter.mItems.size());
			mSAdapters[mAdapter.mItems.size() - 1] = null;*/
			cds.database.delete(ListTable.TABLE_NAME, cds.helper.tables[1].COLUMN_NAMES[0] + " = ?" , new String[] { String.valueOf(cursor.getString(1)) });
			mAdapter.data = cds;
			mAdapter.notifyDataSetChanged();
		}
		mAdapter.notifyDataSetChanged();
	}
	
	/*private void deleteSelectedItems() {
		SparseArrayCompat<Boolean> checkedItems = listView.getCheckedItemPositions();
		ArrayList<Integer> sorted = new ArrayList<Integer>( checkedItems.size() );
		
		Log.i( LOG_TAG, "deleting: " + checkedItems.size() );
		
		for( int i = 0; i < checkedItems.size(); i++ ) {
			if( checkedItems.valueAt( i ) ) {
				sorted.add( checkedItems.keyAt( i ) );
			}
		}

		Collections.sort( sorted );
		
		for( int i = sorted.size()-1; i >= 0; i-- ) {
			int position = sorted.get( i );
			Log.d( LOG_TAG, "Deleting item at: " + position );
			mAdapter.mItems.remove( position );
		}
		mAdapter.notifyDataSetChanged();
	}*/
	
	@Override
	public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
		Log.i( LOG_TAG, "onItemClick: " + position );
		Log.d( LOG_TAG, "checked items: " + listView.getCheckedItemCount() );
		Log.d( LOG_TAG, "checked positions: " + listView.getCheckedItemPositions() );
	}
		
	//Horizontal ListView
	class TestAdapter extends ArrayAdapter<String> implements Filterable  /*,OnItemClickListener*/{
		Button mButtonexp1; 
		Button mButtonexp2; 
		Button options;
		LinearLayout Popup;
		PopupWindow explistpp;
		List<Button> expb = new ArrayList<Button>();
		LayoutInflater mInflater;
		int mResource;
		int mTextResId;
		int mListResId;
		List<View> Views = new ArrayList<View>();
		Context context;
		CoreDataSource data;
		List<String> mtypes;
		List<String> mlist;
		List<List2> mtypesl;
		List<Machine> mfilter;
		HListView Hlist;
		String msort;
		public TestAdapter( Context context, int resourceId, int textViewResourceId, int listViewId, CoreDataSource datasource, List<String> mtypes, List<Machine> mfilter, String sortby)  {
			super( context, resourceId, mtypes);
			Popup = new LinearLayout(MainActivity.this);
			mInflater = LayoutInflater.from( context );
			mResource = resourceId;
			mTextResId = textViewResourceId;
			mListResId = listViewId;
			//mItems = objects;
			//mCards = cards;
			//this.groups = groups;
			this.context = context;
			msort = sortby;
			data = datasource;
			mtypesl = data.getAllLists();
			this.mtypes = mtypes;
			this.mfilter = mfilter;
		}
		
		public boolean hasStableIds() {
			return true;
		}
		
		@Override
		public long getItemId( int position ) {
			return getItem( position ).hashCode();
		}
		
		@Override
		public int getViewTypeCount() {
			return 2;
		}
		
	/*	@Override
		public int getItemViewType( int position ) {
			int out = position!=data.getListsCount() ? 1 : 2 ;
			return out;
		}*/
		
		
		//Later: add a final column with a plus and minus buttons
		@Override
		public View getView( int position, View convertView, ViewGroup parent ) {
			
			if(convertView  == null) {
				convertView = mInflater.inflate( mResource, parent, false );
			}
			
			TextView textView = (TextView) convertView.findViewById( mTextResId );
			textView.setText(mtypes.get(position));
			textView.setTypeface(null, Typeface.BOLD);
			options = (Button)convertView.findViewById(R.id.popupbutton);
			Views.add(convertView);
			convertView.setTag(position);
			//Views.add(this.getContentView());
			options.setTag(convertView);
			//options.setOnClickListener(listpopup);
			String tag = String.valueOf(position);
			/*if(expand != null)
				mSAdapters[position] = new ListViewListAdapter(getContext(), R.id.laptopg, mCards.get(position), groups.get(position), position, expand.get(position));
			else
				mSAdapters[position] = new ListViewListAdapter(getContext(), R.id.laptopg, mCards.get(position), groups.get(position), position, null);
			ExpandableListView expListView = (ExpandableListView)convertView.findViewById(mListResId);
			*/
			Cursor mcursor = null;
			mlist= new ArrayList<String>();
			if(mfilter == null){
				if(msort == "M"){
					mcursor = MainActivity.this.datasource.database.rawQuery("SELECT * FROM "+MachineTable.TABLE_NAME+" WHERE "+data.helper.tables[0].COLUMN_NAMES[1]+" = '"+ mtypesl.get(position).getName().toString()+"' ORDER BY "+MachineTable.COLUMN_NAMES, null);
				}else{
					mcursor = MainActivity.this.datasource.database.rawQuery("SELECT * FROM "+MachineTable.TABLE_NAME+" WHERE "+data.helper.tables[0].COLUMN_NAMES[1]+" = '"+ mtypesl.get(position).getName().toString()+"'", null);
				}
				mcursor.moveToFirst();
			Log.w(LOG_TAG,"Database Size = " + mcursor.getCount());
			for (int  i = 0; i < mcursor.getCount(); i++ ){
				mlist.add(mcursor.getString(1));
				mcursor.moveToNext();
			}}
			else{
				for (int  i = 0; i < mfilter.size(); i++ ){
					Log.w(LOG_TAG,"Vertical filtered  machine size = "+ mfilter.get(i).getList());
					if(mfilter.get(i).getList().equals(mtypes.get(position)))
						mlist.add(mfilter.get(i).getName());}
			}
			Log.w(LOG_TAG,"Vertical ListView position = "+ position);
			if(position <= mtypes.size()){
			mSAdapters[position] = new ListViewListAdapter(getContext(), R.id.laptopg, data, mListResId, position, mlist) ;
			ListView  lv = (ListView)convertView.findViewById(mListResId);
			lv.setAdapter(mSAdapters[position]);}
			if(position <= 10){
				expb.add((Button)convertView.findViewById(R.id.expbutton1));
				expb.add((Button)convertView.findViewById(R.id.expbutton2));
				expb.get(2*position).setTag(2*position);
				expb.get(2*position + 1).setTag(2*position + 1);
				expb.get(2*position).setOnClickListener(handler1);
				expb.get(2*position + 1).setOnClickListener(handler1);
			}
			 /*expListView.setOnChildClickListener(new OnChildClickListener() {
				 
		            public boolean onChildClick(ExpandableListView parent, View v, int gposition, int cpos, 
		                   long id) {
		                final String selected = (String) mSAdapters[1].getChild(gposition ,cpos);
		                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
		                        .show();
		 
		                return true;
		            }
		        });*/
			/*
			int type = getItemViewType( position );
			LayoutParams params = convertView.getLayoutParams();
			if( type == 0 ) {
				params.width = getResources().getDimensionPixelSize( R.dimen.item_size_1 );
			} else {
				params.width = getResources().getDimensionPixelSize( R.dimen.item_size_2 );
			}
			*/
			return convertView;
			
		}
		
		
		public void notifyDataSetInvalidated()
        {
            super.notifyDataSetInvalidated();
        }
		@Override
		public Filter getFilter() {
			if (expfilter == null)
		        expfilter = new expFilter();
		     
		   
				return expfilter;
		}
	}
	
	/*View.OnClickListener listpopup = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Context context = mAdapter.getContext();
			ILayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVCE);
			mAdapter.Popup.addView(delete,params);
			mAdapter.Popup.setOrientation(LinearLayout.VERTICAL);
			mAdapter.explistpp = new PopupWindow(context);
			View convertView = inflater.inflate( R.layout.popup, null, false );
			mAdapter.explistpp.setContentView(convertView);
			mAdapter.explistpp.setContentView(mAdapter.Popup);
			LinearLayout mainLayout = new LinearLayout(context);
			mAdapter.explistpp.showAtLocation(mAdapter.Popup, Gravity.BOTTOM, 10, 10);
		   // mAdapter.notifyAll();
			//LinearLayout Popup = new LinearLayout(MainActivity.this);
			//PopupWindow explistpp = new PopupWindow(MainActivity.this);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			Button delete = new Button(mAdapter.getContext());
			Context context = MainActivity.this.getBaseContext();
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//Popup.addView(delete,params);
			LinearLayout Popup = (LinearLayout)findViewById(R.id.pop);
			View layout  = getLayoutInflater().inflate(R.layout.popup,Popup);
			final PopupWindow explistpp = new PopupWindow(layout, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
			//Popup.setOrientation(LinearLayout.VERTICAL);
			explistpp.setAnimationStyle(android.R.style.Animation_Dialog);  
			//explistpp = new PopupWindow(context);
			//View convertView = inflater.inflate( R.layout.popup, null, false );
			//mAdapter.explistpp.setContentView(convertView);
			explistpp.setContentView(layout);
			explistpp.setWidth(200);
			explistpp.setHeight(190);
			//LinearLayout mainLayout = new LinearLayout(context);
			//explistpp.update(50, 50, 300, 80);
			explistpp.showAtLocation(layout, Gravity.CENTER, 10, 10);
			explistpp.setFocusable(true);
			layout.requestFocus();
			Button cancel=(Button)findViewById(R.id.cancel);
			explistpp.setBackgroundDrawable(getWallpaper());
			cancel.setOnClickListener(cancellisten); 
			cancel.setTag(explistpp);
			//List<View> Views = (List<View>)v.getTag();
			//((View)v.getTag()).setBackgroundColor(Color);
			Popup expPop = new Popup();
			expPop.showpopup((View)v.getTag());
			//int expnum = Integer.parseInt(v.getTag().toString().replace("tag",""));
		//Window expview = (Window)v.getTag();
			//expview.setBackgroundDrawable( new ColorDrawable(Color.BLUE) );
		}
	};
	
	public class Popup implements OnItemSelectedListener{
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout Popup = (LinearLayout)findViewById(R.id.pop);
		View layout  = getLayoutInflater().inflate(R.layout.popup,Popup);
		final PopupWindow explistpp = new PopupWindow(layout,100,100);
		Spinner dropdownsort = (Spinner)explistpp.getContentView().findViewById(R.id.dropdown);
		String droplist[] = {"Machine Name", "Date of Purchase","Last Greased", "Last Serviced"};
		
		public void showpopup(View V){
			explistpp.setAnimationStyle(android.R.style.Animation_Dialog);  
			explistpp.setContentView(layout);
			//explistpp.setWidth(200);
			//explistpp.setHeight(190);
			//LinearLayout mainLayout = new LinearLayout(context);
			//explistpp.update(50, 50, 300, 80);
			explistpp.showAtLocation(layout, Gravity.CENTER, 10, 10);
			explistpp.setFocusable(true);
			layout.requestFocus();
			for(int i = 0; i < MainActivity.this.items.size(); i++){
				mAdapter.Views.get(i).setBackgroundColor(color.holo_blue_light);
			}
			V.setBackgroundColor(color.background_light);
			getWindow().setBackgroundDrawable( new ColorDrawable(Color.DKGRAY) ); 
			Context context = layout.getContext();
			Button cancel=(Button)layout.findViewById(R.id.cancel);
			//Spinner dropdownsort = (Spinner)explistpp.getContentView().findViewById(R.id.dropdown);
			List<String> droplist = new ArrayList<String>();
			droplist.add("Machine Name");
			droplist.add("Date of Purchase");
			droplist.add("Last greased");
			droplist.add("Last serviced");
			V.requestFocus();
			layout.setTag(V);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(V.getContext(), android.R.layout.simple_spinner_item, droplist);
			layout.clearFocus();
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        dropdownsort.setAdapter(adapter);
	        dropdownsort.setTag(V);
	        dropdownsort.setOnItemSelectedListener(this);
			getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
			//.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
			explistpp.setFocusable(true);
			explistpp.setTouchInterceptor(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
					Log.v(LOG_TAG,"Touch Id = " + event.getAction());
					if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						explistpp.dismiss();
						return true;
					}
					return false;
			
				}
			});
			cancel.setOnClickListener(cancellisten); 
			cancel.setTag(explistpp);
		}

		@Override
		public void onItemSelected(android.widget.AdapterView<?> arg0,
				View arg1, int pos, long arg3) {
			dropdownsort.setSelection(pos);
			String selState = (String) dropdownsort.getSelectedItem();
			Log.w(LOG_TAG,"Touch Id = " + (mAdapter.Views.get(0)).toString());
			if(selState.compareTo(droplist[0]) == 0){
			//int position = Integer.parseInt(((View)((View)arg1.getTag()).getTag()).toString().replace("tag", ""));
			int position = Integer.parseInt(((View)layout.getTag()).getTag().toString().replace("tag", ""));
			String listname = MainActivity.this.items.get(position);
			String selectQuery = "SELECT * FROM "+ MainActivity.this.datasource.dbMHelper.TABLE_NAME + " WHERE " + MainActivity.this.datasource.dbMHelper.COLUMN_NAMES[1] + " = " + position + " ORDER BY " + MainActivity.this.datasource.dbMHelper.COLUMN_NAMES[0];
			Cursor cursor = MainActivity.this.datasource.databaseMachine.rawQuery(selectQuery, null);
			//datasource.databaseMachine.rawQuery("Delete From " + datasource.dbMHelper.TABLE_NAME, null);
			List<Machine> sortedmlist = new ArrayList<Machine>();
			if (cursor.moveToFirst()) {
			        do {
			            Machine machine = MainActivity.this.datasource.cursor2Machine(cursor);
			        	// Adding contact to list
			            sortedmlist.add(machine);
			        } while (cursor.moveToNext());
			    }
			Log.w(LOG_TAG,"Machine count = " + sortedmlist.size());
			List<String> groups = new ArrayList<String>();
			HashMap<String,List<String>> collection = new HashMap<String,List<String>>();
			for(int i = 0; i < sortedmlist.size() ; i++){
				groups.add(sortedmlist.get(i).getName());
				List<String> child = new ArrayList<String>();
				child.add(sortedmlist.get(i).getColor());
				collection.put(groups.get(i), child);
			}
			//mSAdapters[position] = new ExpandableListAdapter(mAdapter.getContext(), R.id.laptopg, R.id.laptop, collection, groups, position, null);
		Log.w(LOG_TAG,"Group count = " + groups.size());
		//Log.w(LOG_TAG," = " + (mAdapter.Views.get(0)).toString());
		mSAdapters[position].groups = groups;
		mSAdapters[position].objects = collection;
		mSAdapters[position].notifyDataSetChanged();
		}
		}
		@Override
		public void onNothingSelected(android.widget.AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	}*/
	
	
	View.OnClickListener cancellisten = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//((View) v.getTag()).setBackgroundColor(Color.WHITE);
			getWindow().setBackgroundDrawable( new ColorDrawable(Color.WHITE) );
			((PopupWindow)v.getTag()).dismiss();  //dismissing the popup
		}
	};
	

	
	//vertical Listviews
	private class ListViewListAdapter extends ArrayAdapter<String> {//Filterable{
		LayoutInflater inflater;
		Context context;
		int groupid;
		//int childid;
		//int expvnum;
		private SparseBooleanArray mSelectedItemsIds;
		//List<TextView> ctv = new ArrayList<TextView>();
		//List<EditText> cet = new ArrayList<EditText>();
		//HashMap<String,List<String>> collection;
		//List<Integer> listexpand;
		CoreDataSource cds;
		public int pos;
		List<String> machine;
		public ListViewListAdapter(Context context, int groupid, CoreDataSource cds, int viewid, int position, List<String> mlist) {
			super(context, viewid, mlist);
			mSelectedItemsIds = new SparseBooleanArray();
			inflater = LayoutInflater.from(context);
			this.context = context;
			this.groupid = groupid;
			this.cds = cds;
			machine = mlist;
			//cursor = cds.databaseMachine.rawQuery("SELECT * FROM "+cds.dbMHelper.TABLE_NAME+" WHERE "+cds.dbMHelper.COLUMN_NAMES[2]+" = "+cds.getList(position), null);
			/*this.objects = objects;
			groups = group;
			expvnum = expview;
			listexpand = expandstatus;*/
		}

		public class ViewHolder{
				TextView laptopTxt;
		}

		/*@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}*/

		@Override
		public boolean hasStableIds() {
			return true;
		}
		
		
	/*	@Override
		public View getChildView(final int groupPosition,final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ViewHolder holder;
			final String laptop = (String) getChild(groupPosition, childPosition);
		//	LayoutInflater inflater = (LayoutInflater) this.context
                    //.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        if (convertView == null) {
	        	convertView = inflater.inflate(R.layout.child_item, null);
	        	holder = new ViewHolder();
	           	holder.laptopTxt = (TextView) convertView
	                    .findViewById(childid);
	            convertView.setTag(holder);
	        }
	        else {
	        holder = (ViewHolder) convertView.getTag();
	        }
	   
	   //    holder.laptopTxt.setText(laptop);
	     //   convertView.setBackgroundColor(mSelectedItemsIds.get(childPosition + groupPosition) ? 0x9934B5E4 : Color.TRANSPARENT);
	       TextView txt = (TextView) convertView.findViewById(childid);
	       txt.setText(laptop);
	       EditText editch = (EditText)convertView.findViewById(R.id.edittextc);
	       String tag = String.valueOf(expvnum)+"-"+String.valueOf(groupPosition);
	       convertView.setTag(tag);
	       editch.setTag(tag);
	       txt.setOnClickListener(itemClickListener);
	       txt.setTag(convertView);
	       ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
	        delete.setOnClickListener(new OnClickListener() {
	 
	            public void onClick(View v) {
	                AlertDialog.Builder builder = new AlertDialog.Builder(context);
	                builder.setMessage("Do you want to remove?");
	                builder.setCancelable(false);
	                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                            public void onClick(DialogInterface dialog, int id) {
	                                List<String> child = objects.get(groups.get(groupPosition));
	                                child.remove(childPosition);
	                                notifyDataSetChanged();
	                            }
	                        });
	                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
	                            public void onClick(DialogInterface dialog, int id) {
	                                dialog.cancel();
	                            }
	                        });
	                AlertDialog alertDialog = builder.create();
	                alertDialog.show();
	            }
	        });
	        
	        /*else {
	        TextView txt = (TextView) convertView.getTag();
	        }
			// TODO Auto-generated method stub
			return convertView;
		}*/


	/* @Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return objects.get(groups.get(groupPosition)).size();
		}


		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return groups.get(groupPosition);
			//return null;
		}


		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groups.size();
	//		return 0;
		}


		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
			//return 0;
		}*/
	
		@Override
		  public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				this.pos = position;
				LayoutInflater inflater = (LayoutInflater) context
				        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.group_item,
	                    null);
				 TextView item = (TextView) convertView.findViewById(groupid);
				        item.setTypeface(null, Typeface.BOLD);
				        item.setText(machine.get(position));
				        item.setOnTouchListener(new View.OnTouchListener() {
							@Override
							public boolean onTouch(View arg0, MotionEvent arg1) {
								Log.w(LOG_TAG,"CHECK click = "+machine.get(pos));
				                Intent nextScreen = new Intent(getApplicationContext(), MachineActivity.class);
				                nextScreen.putExtra("Machine", machine.get(pos));
				                //nextScreen.putExtra("MainActivity", (CharSequence) MainActivity.this.mAdapter);
				                startActivity(nextScreen);
								return false;
							}});
				        EditText editg = (EditText)convertView.findViewById(R.id.edittextg);
					    //String tag = String.valueOf(expvnum)+"-"+String.valueOf(groupPosition);
					    //editg.setTag(tag);
					    //convertView.setTag(tag);
					    //item.setTag(convertView);
					    //item.setOnLongClickListener(longCLick);
					    }
			return convertView;
		} 
		
		/*public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			String laptopName = (String) getGroup(groupPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = inflater.inflate(R.layout.group_item,
	                    null);
	        }
	        try{
	        TextView item = (TextView) convertView.findViewById(groupid);
	        item.setTypeface(null, Typeface.BOLD);
	        item.setText(laptopName);
	        EditText editg = (EditText)convertView.findViewById(R.id.edittextg);
		    String tag = String.valueOf(expvnum)+"-"+String.valueOf(groupPosition);
		    editg.setTag(tag);
		    ExpandableListView elv = (ExpandableListView)parent;
		    if((listexpand != null) && (listexpand.get(groupPosition) == 1)){
		 
		    	elv.expandGroup(groupPosition);}
		    else if((listexpand != null) &&  (listexpand.get(groupPosition) == 0))
		    	elv.collapseGroup(groupPosition);
		    convertView.setTag(tag);
		    item.setTag(convertView);
		    item.setOnLongClickListener(longCLick);
		    return convertView;}
	        finally{
	        	
	        }
			//return null;
	    }*/
		
		/*public void add(String object){
			groups.add(object);
			notifyDataSetChanged();
		    Toast.makeText(context, groups.toString(), Toast.LENGTH_LONG).show();
		}*/
		
    
   // @Override
 
    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }
 
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }
 
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
 
        notifyDataSetChanged();
    }
 
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }
 
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }


	}
	
	View.OnLongClickListener longCLick = new View.OnLongClickListener(){

		@Override
		public boolean onLongClick(View v) {			
			View listItem = (View) v.getTag();
			EditText editch = (EditText)listItem.findViewById(R.id.edittextg);
			editch.setVisibility(View.VISIBLE);
			TextView txt = (TextView)listItem.findViewById(R.id.laptopg);
			txt.setVisibility(View.INVISIBLE);
	   		editch.requestFocus();
	   		editch.setText(txt.getText().toString());
	   		txt.setTag((View)v.getTag());
	   		editch.setTag(v.getTag());
	   		editch.addTextChangedListener(childwatch);	
	   		editch.setOnEditorActionListener(groupedit);
			Toast.makeText(getApplicationContext(), "You clicked me", Toast.LENGTH_LONG).show();
			return false;
		}
	};
	/*View.OnTouchListener touchhandler = new View.OnTouchListener() 
    {
        @Override
        public boolean onTouch(View v, MotionEvent event) 
        {
     	   	//if(getCurrentFocus() != null && getCurrentFocus() instanceof TextView){
     	   		String[] tag = v.getTag().toString().split("-");
 	   		TextView txt = mSAdapters[Integer.parseInt(tag[0])].ctv.get(Integer.parseInt(tag[1]));
 	   		EditText editch = mSAdapters[Integer.parseInt(tag[0])].cet.get(Integer.parseInt(tag[1]));
			//TextView txt = mSAdapters[1].ctv.get(2);
			//EditText editch = mSAdapters[1].cet.get(2);
 	   		//if(editch.hasFocus() == false){
 	   			txt.setVisibility(View.INVISIBLE);
 	   			editch.setVisibility(View.VISIBLE);
 	   			editch.setFocusable(true);
 	   			editch.setFocusableInTouchMode(true);
 	   			mEdit.clearFocus();
 	   			mEdit.setNextFocusDownId(editch.getId());
 	   			editch.requestFocus();
 	   			String child = editch.getText().toString();
 	   			txt.setText(child);
 	   			editch.clearFocus();
 	   			editch.setVisibility(View.INVISIBLE);
 	   			txt.setVisibility(View.VISIBLE);
 	   		mSAdapters[Integer.parseInt(tag[0])].notifyDataSetChanged();
     	   		
     	   	
               //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            //   imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                       //return false;
        //}*/
			View.OnClickListener itemClickListener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					/*String[] tag = v.getTag().toString().split("-");
		 	   		TextView txt = mSAdapters[Integer.parseInt(tag[0])].ctv.get(Integer.parseInt(tag[1]));
		 	   		EditText editch = mSAdapters[Integer.parseInt(tag[0])].cet.get(Integer.parseInt(tag[1]));
		 	   			editch.setVisibility(View.VISIBLE);
		 	   			editch.setFocusable(true);
		 	   			editch.setFocusableInTouchMode(true);
		 	   			mEdit.clearFocus();
		 	   			mEdit.setNextFocusDownId(editch.getId());
		 	   			editch.requestFocus();
		 	   			String child = editch.getText().toString();
		 	   			txt.setText(child);
		 	   			editch.clearFocus();
		 	   			editch.setVisibility(View.INVISIBLE);
		 	   			txt.setVisibility(View.VISIBLE);
		 	   		mSAdapters[Integer.parseInt(tag[0])].notifyDataSetChanged();*/
					View listItem = (View) v.getTag();
					EditText editch = (EditText) listItem.findViewById(R.id.edittextc);
					editch.setVisibility(View.VISIBLE);
					TextView txt = (TextView)listItem.findViewById(R.id.laptop);
					txt.setVisibility(View.INVISIBLE);
	 	   			editch.requestFocus();
	 	   			editch.setText(txt.getText().toString());
	 	   			txt.setTag((View)v.getTag());
	 	   			editch.setTag(v.getTag());
	 	   			editch.addTextChangedListener(childwatch);
	 	   			//InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	 	   			//imm.showSoftInput(f , InputMethodManager.SHOW_IMPLICIT);
	 	   			editch.setOnEditorActionListener(groupedit);
					//Toast.makeText(getApplicationContext(), "You clicked me", Toast.LENGTH_LONG).show();
				} 
			};
        

	TextWatcher childwatch = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable arg0) {
			arg0.toString();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}
		
	};

	TextView.OnEditorActionListener childedit = new TextView.OnEditorActionListener(){

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			Log.v(LOG_TAG,"Action Id = " + actionId);
			//if(actionId == EditorInfo.IME_ACTION_DONE){
			if(actionId == 0){
				View tag = (View)v.getTag();
				TextView txt = (TextView)tag.findViewById(R.id.laptop);
				EditText editch = (EditText)tag.findViewById(R.id.edittextc);
				txt.setText(editch.getText().toString());
				editch.setVisibility(View.INVISIBLE);
				txt.setVisibility(View.VISIBLE);
				String[] modtag = tag.getTag().toString().replace("tag","").split("-");
				int expnum  = Integer.parseInt(modtag[0]);
				int groupnum = Integer.parseInt(modtag[1]);
				//InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				//imm.hideSoftInputFromWindow(editch.getWindowToken(), 0);
				String groupname = MainActivity.this.cardlist.get(expnum).get(groupnum);
				MainActivity.this.collection.get(expnum).get(groupname).remove(0);
				MainActivity.this.collection.get(expnum).get(groupname).add(editch.getText().toString());
				mSAdapters[Integer.parseInt(modtag[0])].notifyDataSetChanged();
			}
			return false;
		}
		
	};

TextView.OnEditorActionListener groupedit = new TextView.OnEditorActionListener(){

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		Log.v(LOG_TAG,"Action Id = " + actionId);
		//if(actionId == EditorInfo.IME_ACTION_DONE){
		if(actionId == 0){
			View tag = (View)v.getTag();
			TextView txt = (TextView)tag.findViewById(R.id.laptopg);
			EditText editch = (EditText)tag.findViewById(R.id.edittextg);
			txt.setText(editch.getText().toString());
			editch.setVisibility(View.INVISIBLE);
			txt.setVisibility(View.VISIBLE);
			String[] modtag = tag.getTag().toString().replace("tag","").split("-");
			int expnum  = Integer.parseInt(modtag[0]);
			int groupnum = Integer.parseInt(modtag[1]);
			String groupname = MainActivity.this.cardlist.get(expnum).get(groupnum);
			List<String> child = MainActivity.this.collection.get(expnum).get(groupname);
			MainActivity.this.collection.get(expnum).remove(groupname);
			MainActivity.this.collection.get(expnum).put(txt.getText().toString(), child);
			mSAdapters[Integer.parseInt(modtag[0])].notifyDataSetChanged();
		}
		return false;
	}
	
};
}