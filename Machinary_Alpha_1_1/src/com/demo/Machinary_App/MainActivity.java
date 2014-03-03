package com.demo.Machinary_App;

import it.sephiroth.android.library.util.v11.MultiChoiceModeListener;
import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;

@TargetApi(11)
public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {
	
	private static final String LOG_TAG = "MainActivity";
	EditText mEdit;
	Button searchButton;
	HListView listView;
	Button mButton3;
	HorAdapter mAdapter;
	VerAdapter[] mSAdapters = {null, null, null, null, null, null, null, null, null, null};// TODO this is temporary and BAD CODE
	
	//Trello-like card strings
	String[] typestring = new String[] {"OS Types", "Dog Breeds", "Random Stuff", "Catagory 4", "Catagory 5"};
	String[][] cardstring = new String[][] { {"Android", "iPhone", "WindowsMobile",
		"Blackberry", "WebOS", "Ubuntu", "Max OS X", "Linux", "Ubuntu",
		"Windows7", "OS/2", "Ubuntu", "Other", "SUN Microsystem", "Dell", "Lorem", "Ipsum"},
		{"Husky", "Dalmation", "Pit Bull"}, {"Foo", "Bar", "Bip"}, {"Empty"}, {"Empty"}};
	String lasttype = "Button1type";
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		setContentView( R.layout.activity_main );
				
		List<String> items = new ArrayList<String>();
		for( int i = 0; i < typestring.length; i++ ) {
			items.add( typestring[i] );
		}
		items.add( lasttype );
		
		List<List<String>> cardlist = new ArrayList<List<String>>();
		for (int i = 0; i < cardstring.length; i++) {
			List<String> listy = new ArrayList<String>();
			for (int j = 0; j < cardstring[i].length; j++) {
				listy.add(cardstring[i][j]);
			}
			cardlist.add(listy);
		}
		
		mAdapter = new HorAdapter( this, R.layout.test_item_1, android.R.id.text1, R.id.listview2, items, cardlist );
		listView.setHeaderDividersEnabled( true );
		listView.setFooterDividersEnabled( true );
		
		listView.setAdapter( mAdapter );
		listView.setOnItemClickListener( this );
		
		mButton3.setOnClickListener( this );
	}
	
	@Override
	public void onContentChanged() {
		super.onContentChanged();
		listView = (HListView) findViewById( R.id.hListView1 );
		mButton3 = (Button) findViewById( R.id.button3 );
		mEdit = (EditText) findViewById(R.id.edittext);
		searchButton = (Button) findViewById(R.id.searchbutton);
	}

	@Override
	public void onClick( View v ) {
		final int id = v.getId();
		if( id == mButton3.getId() ) {
			scrollList();
		}
	}	
	
	private void scrollList() {
		listView.smoothScrollBy( 406, 0 );
	}
	
	private void addElements() {
		if (mAdapter.mItems.size() < 11) {
			ArrayList<String> list = new ArrayList<String>();
			list.add(0, "Empty");
			mAdapter.mCards.add( mAdapter.mCards.size()-1, list );
			mAdapter.mItems.add( mAdapter.mItems.size()-1, "Category " + String.valueOf( mAdapter.mItems.size() ) );
			mAdapter.notifyDataSetChanged();
			listView.smoothScrollBy( 406, 0 );
		}
		Log.i( LOG_TAG, "Added Element: " + String.valueOf( mAdapter.mItems.size()-1 ) );
	}
	
	private void removeElements() {
		listView.smoothScrollBy( -406, 0 );
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		    @Override
		    public void run() {
		    	if( mAdapter.mItems.size() > 1 & mAdapter.mItems.size() <= 11 ) {
					int spot = mAdapter.mItems.size()-2;
					mAdapter.mItems.remove(mAdapter.mItems.size()-1);
					mSAdapters[spot] = null;
				}
				mAdapter.notifyDataSetChanged();
		    }
		}, 50);     // TODO problem
		Log.i( LOG_TAG, "Removed an Element" );
	}
	
	private void addCards(int arg0) {
		List<String> list = mAdapter.mCards.get(arg0);
		list.add(list.size(), "New Card");
		mAdapter.notifyDataSetChanged();
	}
	
	private void removeCards(int arg0) {
		List<String> list = mAdapter.mCards.get(arg0);
		if(list.size() > 1){
			list.remove(list.size()-1);
			mAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onItemClick( AdapterView<?> parent, View veiw, int position, long id ) {
		Log.i( LOG_TAG, "onItemClick: " + position );
	}
		
	View.OnClickListener mHandler1 = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			addElements();
		}
	};
	
	View.OnClickListener mHandler2 = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			removeElements();
		}
	};

	View.OnClickListener mHandler3 = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			final Object tag = arg0.getTag();
			//turns tag object into readable int
			int i = Integer.parseInt(tag.toString().replace("tag",""));
			if(i % 2 == 1){
				removeCards((i-1)/2);
			} else if (i% 2 == 0){
				addCards(i/2);
			}
		}
	};
	
	//Horizontal ListView
	class HorAdapter extends ArrayAdapter<String> {
		
		List<String> mItems;
		LayoutInflater mInflater;
		int mResource;
		int mTextResId;
		int mListResId;
		List<List<String>> mCards;
		Button mButton1;
		Button mButton2;
		List<Button> mButtons = new ArrayList<Button>();
		List<ListView> mLists = new ArrayList<ListView>();
		
		public HorAdapter( Context context, int resourceId, int textViewResourceId, int listViewId, List<String> objects, List<List<String>> cards) {
			super( context, resourceId, textViewResourceId, objects);
			mInflater = LayoutInflater.from( context );
			mResource = resourceId;
			mTextResId = textViewResourceId;
			mListResId = listViewId;
			mItems = objects;
			mCards = cards;			
		}
		
		@Override
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
			int out  =  position != mItems.size()-1 ? 0 : 1 ;
			return out;
		}
		
		@Override
		public View getView( int position, View convertView, ViewGroup parent ) {
			int type = getItemViewType( position );
			if( type == 0 ) {	
				if( null == convertView ) {
					convertView = mInflater.inflate( mResource, parent, false );
				}

				TextView textView = (TextView) convertView.findViewById( mTextResId );
				textView.setText( mItems.get(position) );
				
				mSAdapters[position] = new VerAdapter(getContext(), android.R.layout.simple_list_item_1, mCards.get(position));
				
				if(mLists.size() <= position){
					mLists.add((ListView) convertView.findViewById( mListResId ));
				}
				// must be reset on new data
				mLists.get(position).setAdapter( mSAdapters[position] );
				
				if(mButtons.size() <= position*2){
					mButtons.add((Button) convertView.findViewById( R.id.button2add )) ;
					mButtons.add((Button) convertView.findViewById( R.id.button2rem ) );
				}
				// It's vital these are reset on new data
				mButtons.get(position*2).setTag(position*2);
				mButtons.get(position*2+1).setTag(position*2+1);
				mButtons.get(position*2).setOnClickListener( mHandler3 );
				mButtons.get(position*2+1).setOnClickListener( mHandler3 );
				
			} else {
				if( null == convertView ) {
					convertView = mInflater.inflate( R.layout.test_item_2, parent, false );
				}
				mButton1 = (Button) convertView.findViewById( R.id.button1add);
				mButton2 = (Button) convertView.findViewById( R.id.button1rem);
			
				mButton1.setOnClickListener( mHandler1 );
				mButton2.setOnClickListener( mHandler2 );
			}
			return convertView;
		}
	}
	
	//vertical List views
	private class VerAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
		
		public VerAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
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
		
	}
}
