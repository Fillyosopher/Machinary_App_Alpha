package com.demo.Machinary_App;

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
//Extract Thumbnail

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
	CoreDataSource datasource;
	List<String> items;
	List<List<String>> cardlist;
	public Filter expfilter;
	
	Button[] mButtonexp = {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,};
	TestAdapter mAdapter;
	ExpandableListAdapter[] mSAdapters = {null, null, null, null, null, null, null, null, null, null};// this is temporary and BAD CODE
	List<HashMap<String, List<String>>> collection;// =  new LinkedHashMap<String, List<String>>();
	//Trello-like card strings
	String[] typestring = new String[] {"OS Types", "Dog Breeds", "Random Stuff", "Catagory 4", "Catagory 5"};
	String[][] cardstring = new String[][] { {"Android", "iPhone", "WindowsMobile",
		"Blackberry", "WebOS", "Ubuntu", "Max OS X", "Linux", "Ubuntu",
		"Windows7", "OS/2", "Ubuntu", "Other", "SUN Microsystem", "Dell", "Lorem", "Ipsum"},
		{"Husky", "Dalmation", "Pit Bull"}, {"Foo", "Bar", "Bip"}, {"Empty"}, {"Empty"} };
	
	String[] hpModels = { "HP Pavilion G6-2014TX", "ProBook HP 4540",
    "HP Envy 4-1025TX" };
	String[] hclModels = { "HCL S2101", "HCL L2102", "HCL V2002" };
	String[] lenovoModels = { "IdeaPad Z Series", "Essential G Series",
    "ThinkPad X Series", "Ideapad Z Series" };
	String[] sonyModels = { "VAIO E Series", "VAIO Z Series",
    "VAIO S Series", "VAIO YB Series" };
	String[] dellModels = { "Inspiron", "Vostro", "XPS" };
	String[] samsungModels = { "NP Series", "Series 5", "SF Series" };

	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		setContentView( R.layout.activity_main );
		datasource = new CoreDataSource(this);
		datasource.open();
		//atasource.databaseMachine.delete()(datasource.databaseMachine,null,1);
		datasource.databaseMachine.execSQL("Delete From "+datasource.dbMHelper.TABLE_NAME);
		//datasource.databaseMachine.rawQuery("Delete From "+datasource.dbMHelper.TABLE_NAME, null);
		items = new ArrayList<String>();
		for( int i = 0; i < typestring.length; i++ ) {
			items.add( typestring[i] );
		}
		
		String[][] laptops = new String[][]{ { "HP Pavilion G6-2014TX", "ProBook HP 4540",
	    "HP Envy 4-1025TX", "A", "B", "C", "D" ,"C", "E", "F", "G", "H", "I", "J", "K", "L","M" }, { "HCL S2101", "HCL L2102", "HCL V2002" }, { "IdeaPad Z Series", "Essential G Series",
	        "ThinkPad X Series", "Ideapad Z Series" }, { "HCL S2101", "HCL L2102", "HCL V2002" }, { "Inspiron", "Vostro", "XPS" }};
		
	
		
		/*TextView view = new TextView(this);
		view.setText("asdasd");
		PopupWindow popoup = new PopupWindow(view, 100, 100);
		        popoup.setWindowLayoutMode(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		        // set window at position
	        	popoup.showAtLocation(getWindow().getDecorView(), BIND_ABOVE_CLIENT, 100, 100);

		popoup.update();*/
		
		collection = new ArrayList<HashMap<String, List<String>>>();
		for (int j = 0; j < items.size(); j++){
				HashMap<String, List<String>> collect = new LinkedHashMap<String, List<String>>();
				for(int k = 0; k < cardstring[j].length; k++ ){
					List<String> l = new ArrayList<String>();
					l.add(laptops[j][k]);
					collect.put(cardstring[j][k],l);
				}
				collection.add(collect);
 		}
		
		cardlist = new ArrayList<List<String>>();
		for (int i = 0; i < cardstring.length; i++) {
			List<String> listy = new ArrayList<String>();
			for (int j = 0; j < cardstring[i].length; j++) {
				Machine machine =  new Machine(j + i + 1,cardstring[i][j],i,0,0,0,collection.get(i).get(cardstring[i][j]).get(0));
				datasource.addMachine(machine);
				listy.add(cardstring[i][j]);
			}
			cardlist.add(listy);
			Log.w(LOG_TAG,"Database Size = " + datasource.databaseMachine.rawQuery("SELECT * FROM " + datasource.dbMHelper.TABLE_NAME, null).getCount());
			
		}
	/*	for(int i = 0; i < cardstring.length; i++ ){
			collection[i] = new LinkedHashMap<String, List<String>>();
			for(int k = 0; k < cardstring[i].length; k++){
				collection[i].put(cardstring[i][k], laptops[])*/
		
		mAdapter = new TestAdapter( this, R.layout.test_item_1, android.R.id.text1, R.id.expandl, items, collection, cardlist, null);
		listView.setHeaderDividersEnabled( true );
		listView.setFooterDividersEnabled( true );
		
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
						deleteSelectedItems();
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
        public void onTextChanged(CharSequence cs, int arg1, int arg2,
                int arg3) {
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
		List<String> mItems = new ArrayList<String>(); 
    	List<List<String>> group = new ArrayList<List<String>>();
    	List<List<Integer>> expanded = new ArrayList<List<Integer>>();
	    	@Override
			protected FilterResults performFiltering(CharSequence arg0) {
				FilterResults results1 = new FilterResults(); 
				FilterResults results2 = new FilterResults(); 
				if(arg0 == null | arg0.length() == 0){
						group = cardlist;
				        results1.values = collection;
				        mItems = items;
				    }
				    else {
				        // We perform filtering operation
				        List<HashMap<String,List<String>>> ncollectionList = new ArrayList<HashMap<String,List<String>>>();
				        List<List<String>> grp = new ArrayList<List<String>>();
				        List<String> Items = new ArrayList<String>();
				        List<List<Integer>> expinterim = new ArrayList<List<Integer>>();
				        //for (HashMap<String, List<String>> M : MainActivity.collection) {
				        for(int i = 0; i < collection.size(); i++){
				        	HashMap<String,List<String>> interim = new LinkedHashMap<String,List<String>>();
				        	List<String> list = new ArrayList<String>();
					        List<Integer> listexpand = new ArrayList<Integer>();
			        		int check = 0;
				        	for(int j = 0; j < collection.get(i).size(); j++){
	 			        	//for (HashMap.Entry<String, List<String>> e : mAdapter.mCards.get(i).entrySet()) {
				        		String group = cardlist.get(i).get(j);
				        		if (cardlist.get(i).get(j).toUpperCase().startsWith(((String) arg0).toUpperCase())){
				        			list.add(group);
					                interim.put(cardlist.get(i).get(j),collection.get(i).get(cardlist.get(i).get(j)));
					                check = 0;
					                }
				        		for(int k = 0; k < collection.get(i).get(group).size(); k++){
				        			if(collection.get(i).get(group).get(k).toUpperCase().startsWith(((String) arg0).toUpperCase())){
				        				if(!list.contains(group)){
				        					list.add(group);
				        					interim.put(group, collection.get(i).get(group));
				        					check = 1;}
				        				else
				        					check = 1;
				        				}
				        			}
				        		listexpand.add(check);
				        		}
				        	    //and to get value
				        	if (interim.size() == 0){
				        		/*List<String> l = new ArrayList<String>();
				        		l.add("Empty");
				        		interim.put("Empty",l);
				        		ncollectionList.add(interim);
				        		grp.add(l);*/
				        	}
				        	else
				        	{
				        		/*if(items.get(i).toUpperCase().startsWith(((String) arg0).toUpperCase())){
				        			Items.add(items.get(i));
					        		grp.add(group.get(i));
					        		ncollectionList.add(collection.get(i));
					        		expinterim.add(null);
				        		}*/
				        		//else{
				        		Items.add(items.get(i));
				        		grp.add(list);
				        		ncollectionList.add(interim);
				        		expinterim.add(listexpand);//}
				        	}
				        }
				        results1.values = ncollectionList;
				        results1.count = ncollectionList.size();
				        results2.values = grp;
				        group = grp;
				        mItems = Items;
				        expanded = expinterim;
				    }
				    return results1;}

			@Override
			protected void publishResults(CharSequence arg0,
					FilterResults arg1) {
				if((List<HashMap<String,List<String>>>)arg1.values == collection){
				//if(arg0 == null |arg0.length() == 0){
					mAdapter.mCards = (List<HashMap<String,List<String>>>)arg1.values ;
					mAdapter.groups = group;
					mAdapter.mItems = mItems;
					mAdapter.expand = null;
					expanded = null;
					//mAdapter.notifyDataSetChanged();
				}else
				{
					mAdapter.mCards = (List<HashMap<String,List<String>>>) arg1.values;
					mAdapter.groups = group;
					mAdapter.mItems = mItems;
					mAdapter.expand = expanded;
				}
				mAdapter = new TestAdapter(MainActivity.this, R.layout.test_item_1, android.R.id.text1, R.id.expandl, mItems, (List<HashMap<String,List<String>>>)arg1.values, group, expanded);
				listView.setAdapter( mAdapter );
				// TODO Auto-generated method stub
			}
	}
	@Override
	public void onClick( View v ) {
		final int id = v.getId();
		
		if( id == mButton1.getId() ) {
			addElements();
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
	
/*	public void searchexplist(){
		String input = mEdit.getText().toString();
		List<List<String>> sgroups = new ArrayList<List<String>>();
		List<String> sitems = new ArrayList<String>();
		List<HashMap<String,List<String>>> scollection = new ArrayList<HashMap<String,List<String>>>();
		for(int j = 0; j < collection.size(); j++){
				
	}*/
		
	
	View.OnClickListener handler1 = new View.OnClickListener(){
		@Override
		public void onClick(View arg0) {
			final Object argTag = arg0.getTag();
			int i = Integer.parseInt(argTag.toString().replace("tag",""));
			if(i % 2 == 0 ){
				// addgroup = (EditText)findViewById(R.id.addcard);
				//String machine = addgroup.getText().toString();
				addGroup(i / 2, arg0);// machine);
			}
			else{
				removeGroup((i - 1) / 2);
			}
		}
	};
	
	private void removeGroup(int category) {
		//mAdapter.mCards.get(category).remove(mAdapter.groups.get(category).size() - 1);
		//mAdapter.groups.get(category).remove(mAdapter.groups.get(category).size() - 1);
		String group = MainActivity.this.cardlist.get(category).get(MainActivity.this.cardlist.get(category).size() - 1);
		MainActivity.this.cardlist.get(category).remove(MainActivity.this.cardlist.get(category).size() - 1);
		MainActivity.this.collection.get(category).remove(group);
		//mAdapter = new TestAdapter(MainActivity.this, R.layout.test_item_1, android.R.id.text1, R.id.expandl, mItems, (List<HashMap<String,List<String>>>)arg1.values, group, expanded);
		mAdapter.notifyDataSetChanged();	
		
	}

	private void scrollList() {
		listView.smoothScrollBy( 1500, 300 );
	}
	
	public void addGroup(int category, View expview){//,String machine){
		List<String> list = new ArrayList<String>();
		list.add("Info");
		int index = ((Integer)MainActivity.this.items.indexOf(mAdapter.mItems.get(category)));
		int listsize = MainActivity.this.cardlist.get(index).size() + 1;
		if(index != category){
			mAdapter.groups.get(category).add("Random" + String.valueOf(listsize));
			mAdapter.mCards.get(category).put("Random" + String.valueOf(listsize), list);}
		//category = index;
		/*String newgroup = mSAdapters[category].addcard(expview);
		List<String> list = new ArrayList<String>();
		list.add("Info");
		MainActivity.this.cardlist.get(category).add(newgroup);
		MainActivity.this.collection.get(category).put(newgroup, list);*/
		//mAdapter.mCards.get(category).put("Random" + String.valueOf(mAdapter.groups.get(category).size()), list);
		//mAdapter.groups.get(category).add("Random" + String.valueOf(mAdapter.groups.get(category).size()));
		if(mAdapter.expand != null)
			mAdapter.expand.get(category).add(0);
		mAdapter.notifyDataSetChanged();
		MainActivity.this.cardlist.get(index).add("Random" + String.valueOf(listsize));
		MainActivity.this.collection.get(index).put("Random" + String.valueOf(listsize), list);
		//String group = MainActivity.this.cardlist.get(category).get(MainActivity.this.cardlist.get(category).size() - 1);
	}
	
	private void addElements() {
		HashMap<String,List<String>> list = new LinkedHashMap<String,List<String>>();
		List<String> l = new ArrayList<String>();
		l.add("empty");
		list.put(l.get(0), l);
		mAdapter.mCards.add(list);
		mAdapter.groups.add(l);
		mAdapter.mItems.add( "Category " + String.valueOf( mAdapter.mItems.size()+1 ) );
		mAdapter.notifyDataSetChanged();
	}
	
	private void removeElements() {
		if( mAdapter.mItems.size() > 0 & mAdapter.mItems.size() < 10 ) {
			mAdapter.mItems.remove(mAdapter.mItems.size()-1);
			mAdapter.mCards.remove(mAdapter.mItems.size());
			mAdapter.groups.remove(mAdapter.mItems.size());
			mSAdapters[mAdapter.mItems.size() - 1] = null;
		}
		mAdapter.notifyDataSetChanged();
	}
	
	private void deleteSelectedItems() {
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
	}
	
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
		List<String> mItems;
		LayoutInflater mInflater;
		int mResource;
		int mTextResId;
		int mListResId;
		List<View> Views = new ArrayList<View>();
		Context context;
		List<List<String>> groups;
		List<HashMap<String, List<String>>> mCards;
		List<List<Integer>> expand;
		HListView Hlist;
		public TestAdapter( Context context, int resourceId, int textViewResourceId, int listViewId, List<String> objects, List<HashMap<String,List<String>>> cards, List<List<String>> groups, List<List<Integer>> expandstatus)  {
			super( context, resourceId, textViewResourceId, objects);
			Popup = new LinearLayout(MainActivity.this);
			mInflater = LayoutInflater.from( context );
			mResource = resourceId;
			mTextResId = textViewResourceId;
			mListResId = listViewId;
			mItems = objects;
			mCards = cards;
			this.groups = groups;
			this.context = context;
			expand = expandstatus;
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
		
		@Override
		public int getItemViewType( int position ) {
			int out = position!=mItems.size() ? 1 : 2 ;
			return out;
		}
		
		
		//Later: add a final column with a plus and minus buttons
		@Override
		public View getView( int position, View convertView, ViewGroup parent ) {
			
			if( null == convertView ) {
				convertView = mInflater.inflate( mResource, parent, false );
			}
			
			TextView textView = (TextView) convertView.findViewById( mTextResId );
			textView.setText( mItems.get(position) );
			textView.setTypeface(null, Typeface.BOLD);
			options = (Button)convertView.findViewById(R.id.popupbutton);
			Views.add(convertView);
			convertView.setTag(position);
			//Views.add(this.getContentView());
			options.setTag(convertView);
			options.setOnClickListener(listpopup);
			String tag = String.valueOf(position);
			if(expand != null)
				mSAdapters[position] = new ExpandableListAdapter(getContext(), R.id.laptopg, R.id.laptop, mCards.get(position), groups.get(position), position, expand.get(position));
			else
				mSAdapters[position] = new ExpandableListAdapter(getContext(), R.id.laptopg, R.id.laptop, mCards.get(position), groups.get(position), position, null);
			ExpandableListView expListView = (ExpandableListView)convertView.findViewById(mListResId);
			
			expListView.setAdapter(mSAdapters[position]);
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
	
	View.OnClickListener listpopup = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			/*Context context = mAdapter.getContext();
			ILayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVCE);
			mAdapter.Popup.addView(delete,params);
			mAdapter.Popup.setOrientation(LinearLayout.VERTICAL);
			mAdapter.explistpp = new PopupWindow(context);
			View convertView = inflater.inflate( R.layout.popup, null, false );
			mAdapter.explistpp.setContentView(convertView);
			mAdapter.explistpp.setContentView(mAdapter.Popup);
			LinearLayout mainLayout = new LinearLayout(context);
			mAdapter.explistpp.showAtLocation(mAdapter.Popup, Gravity.BOTTOM, 10, 10);*/
		   // mAdapter.notifyAll();
			//LinearLayout Popup = new LinearLayout(MainActivity.this);
			//PopupWindow explistpp = new PopupWindow(MainActivity.this);
			/*LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
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
			cancel.setTag(explistpp);*/
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
			/*for(int i = 0; i < MainActivity.this.items.size(); i++){
				mAdapter.Views.get(i).setBackgroundColor(color.holo_blue_light);
			}*/
			V.setBackgroundColor(color.background_light);
			getWindow().setBackgroundDrawable( new ColorDrawable(Color.DKGRAY) ); 
			Context context = layout.getContext();
			Button cancel=(Button)layout.findViewById(R.id.cancel);
			//Spinner dropdownsort = (Spinner)explistpp.getContentView().findViewById(R.id.dropdown);
			/*List<String> droplist = new ArrayList<String>();
			droplist.add("Machine Name");
			droplist.add("Date of Purchase");
			droplist.add("Last greased");
			droplist.add("Last serviced");*/
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
	}
	
	/*AdapterView.setOnItemSelectedListener droplistItemlistener = new AdapterView.OnItemSelectedClickListener(
			) {
	};*/
	
	View.OnClickListener cancellisten = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//((View) v.getTag()).setBackgroundColor(Color.WHITE);
			getWindow().setBackgroundDrawable( new ColorDrawable(Color.WHITE) );
			((PopupWindow)v.getTag()).dismiss();  //dismissing the popup
		}
	};
	
	private class ListViewAdapter extends ArrayAdapter<String>{//Filterable{

		HashMap<String, List<String>> objects;
		LayoutInflater inflater;
		List<String> groups; 
		Context context;
		int groupid;
		int childid;
		int expvnum;
		private SparseBooleanArray mSelectedItemsIds;
		List<TextView> ctv = new ArrayList<TextView>();
		List<EditText> cet = new ArrayList<EditText>();
		//HashMap<String,List<String>> collection;
		List<Integer> listexpand;
		public ExpandableListAdapter(Context context, int groupid, int childId,
				HashMap<String,List<String>> objects, List<String> group, int expview, List<Integer> expandstatus) {
			super();
			mSelectedItemsIds = new SparseBooleanArray();
			inflater = LayoutInflater.from(context);
			this.context = context;
			this.groupid = groupid;
			childid = childId;
			this.objects = objects;
			groups = group;
			expvnum = expview;
			listexpand = expandstatus;
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


		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return objects.get(groups.get(groupPosition)).get(childPosition);
			//return mIdMap.get(childPosition)
			//return null;
		}


		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition + groupPosition;
			//return 0;
		}

		public String addcard(View expview){
			EditText txt = (EditText)(View)((Activity) context).findViewById(groupid);
			String newgroup = txt.getText().toString();
			return newgroup;
		}
		@Override
		public View getChildView(final int groupPosition,final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ViewHolder holder;
			final String laptop = (String) getChild(groupPosition, childPosition);
		//	LayoutInflater inflater = (LayoutInflater) this.context
                    //.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        if (convertView == null) {
	        	convertView = inflater.inflate(R.layout.child_item, null);
	        	/*holder = new ViewHolder();
	           	holder.laptopTxt = (TextView) convertView
	                    .findViewById(childid);
	            convertView.setTag(holder);
	        */}/*
	        else {
	        holder = (ViewHolder) convertView.getTag();
	        }
*/	   
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
	       /*ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
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
	        }*/
			// TODO Auto-generated method stub
			return convertView;
		}


		@Override
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
		}


		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			String laptopName = (String) getGroup(groupPosition);
	        if (convertView == null) {
	          /*  LayoutInflater infalInflater = (LayoutInflater) context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
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
		    /*else if((listexpand != null) &&  (listexpand.get(groupPosition) == 0))
		    	elv.collapseGroup(groupPosition);*/
		    convertView.setTag(tag);
		    item.setTag(convertView);
		    item.setOnLongClickListener(longCLick);
		    return convertView;}
	        finally{
	        	
	        }
			//return null;
	    }
		public void add(String object){
			groups.add(object);
			notifyDataSetChanged();
		    Toast.makeText(context, groups.toString(), Toast.LENGTH_LONG).show();
		}
		
    
   // @Override
    public void remove(String object) {
        // super.remove(object);
        groups.remove(object);
        notifyDataSetChanged();
    }
 
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
    
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
		}

	}
	
	//vertical Listviews
	/*private class ExpandableListAdapter extends BaseExpandableListAdapter{//Filterable{

		HashMap<String, List<String>> objects;
		LayoutInflater inflater;
		List<String> groups; 
		Context context;
		int groupid;
		int childid;
		int expvnum;
		private SparseBooleanArray mSelectedItemsIds;
		List<TextView> ctv = new ArrayList<TextView>();
		List<EditText> cet = new ArrayList<EditText>();
		//HashMap<String,List<String>> collection;
		List<Integer> listexpand;
		public ExpandableListAdapter(Context context, int groupid, int childId,
				HashMap<String,List<String>> objects, List<String> group, int expview, List<Integer> expandstatus) {
			super();
			mSelectedItemsIds = new SparseBooleanArray();
			inflater = LayoutInflater.from(context);
			this.context = context;
			this.groupid = groupid;
			childid = childId;
			this.objects = objects;
			groups = group;
			expvnum = expview;
			listexpand = expandstatus;
		}

		public class ViewHolder{
				TextView laptopTxt;
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}


		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return objects.get(groups.get(groupPosition)).get(childPosition);
			//return mIdMap.get(childPosition)
			//return null;
		}


		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition + groupPosition;
			//return 0;
		}

		public String addcard(View expview){
			EditText txt = (EditText)(View)((Activity) context).findViewById(groupid);
			String newgroup = txt.getText().toString();
			return newgroup;
		}
		@Override
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
		}


		@Override
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
		}


		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
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
	    }
		public void add(String object){
			groups.add(object);
			notifyDataSetChanged();
		    Toast.makeText(context, groups.toString(), Toast.LENGTH_LONG).show();
		}
		
    
   // @Override
    public void remove(String object) {
        // super.remove(object);
        groups.remove(object);
        notifyDataSetChanged();
    }
 
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
    
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
		}

	}*/
	
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