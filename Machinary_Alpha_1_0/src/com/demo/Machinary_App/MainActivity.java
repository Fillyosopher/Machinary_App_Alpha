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
	Button mButton1;
	Button mButton2;
	Button mButton3;
	TestAdapter mAdapter;
	StableArrayAdapter[] mSAdapters = {null, null, null, null, null, null, null, null, null, null};// this is temporary and BAD CODE
	
	//Trello-like card strings
	String[] typestring = new String[] {"OS Types", "Dog Breeds", "Random Stuff", "Catagory 4", "Catagory 5"};
	String[][] cardstring = new String[][] { {"Android", "iPhone", "WindowsMobile",
		"Blackberry", "WebOS", "Ubuntu", "Max OS X", "Linux", "Ubuntu",
		"Windows7", "OS/2", "Ubuntu", "Other", "SUN Microsystem", "Dell", "Lorem", "Ipsum"},
		{"Husky", "Dalmation", "Pit Bull"}, {"Foo", "Bar", "Bip"}, {"Empty"}, {"Empty"}};
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		setContentView( R.layout.activity_main );
				
		List<String> items = new ArrayList<String>();
		for( int i = 0; i < typestring.length; i++ ) {
			items.add( typestring[i] );
		}
		
		List<List<String>> cardlist = new ArrayList<List<String>>();
		for (int i = 0; i < cardstring.length; i++) {
			List<String> listy = new ArrayList<String>();
			for (int j = 0; j < cardstring[i].length; j++) {
				listy.add(cardstring[i][j]);
			}
			cardlist.add(listy);
		}
		
		mAdapter = new TestAdapter( this, R.layout.test_item_1, android.R.id.text1, R.id.listview2, items, cardlist );
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

	@Override
	public void onClick( View v ) {
		final int id = v.getId();
		
		if( id == mButton1.getId() ) {
			addElements();
		} else if( id == mButton2.getId() ) {
			removeElements();
		} else if( id == mButton3.getId() ) {
			scrollList();
		}
	}	
	
	private void scrollList() {
		listView.smoothScrollBy( 1500, 300 );
	}
	
	private void addElements() {
		if (mAdapter.mItems.size() < 10) {
			ArrayList<String> list = new ArrayList<String>();
			list.add(0, "Empty");
			mAdapter.mCards.add( list );
			mAdapter.mItems.add( "Category " + String.valueOf( mAdapter.mItems.size()+1 ) );
			mAdapter.notifyDataSetChanged();
		}
	}
	
	private void removeElements() {
		if( mAdapter.mItems.size() > 0 & mAdapter.mItems.size() <= 10 ) {
			mAdapter.mItems.remove(mAdapter.mItems.size()-1);
			mSAdapters[mAdapter.mItems.size()-1] = null;
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
	class TestAdapter extends ArrayAdapter<String> {
		
		List<String> mItems;
		LayoutInflater mInflater;
		int mResource;
		int mTextResId;
		int mListResId;
		List<List<String>> mCards;

		public TestAdapter( Context context, int resourceId, int textViewResourceId, int listViewId, List<String> objects, List<List<String>> cards) {
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
			
			mSAdapters[position] = new StableArrayAdapter(getContext(), android.R.layout.simple_list_item_1, mCards.get(position));
			
			ListView listView_temp = (ListView) convertView.findViewById( mListResId );
			listView_temp.setAdapter( mSAdapters[position] );
			
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
	}
	
	//vertical Listviews
	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
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
