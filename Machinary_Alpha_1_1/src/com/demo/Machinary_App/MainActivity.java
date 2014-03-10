package com.demo.Machinary_App;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.List;
import android.database.sqlite.*;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
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
	
	//Main View Declarations
	EditText searchEditText;
	Button searchButton;
	HListView mHorListView;
	Button scrollButton;
	HorAdapter mHorAdapter;
	
	//Pre-loaded data strings
	// TODO remove these when the SQLite is added
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
		
		//HorListView onCreate
		List<String> elementList = new ArrayList<String>();
		for( int i = 0; i < typestring.length; i++ ) {
			elementList.add( typestring[i] );
		}
		elementList.add( lasttype ); //this allows the add/remove element buttons to appear
		List<List<String>> cardList = new ArrayList<List<String>>();
		for (int i = 0; i < cardstring.length; i++) {
			List<String> cardlist = new ArrayList<String>();
			for (int j = 0; j < cardstring[i].length; j++) {
				cardlist.add(cardstring[i][j]);
			}
			cardList.add(cardlist);
		}
		mHorAdapter = new HorAdapter( this, R.layout.test_item_1, android.R.id.text1, R.id.listview2, elementList, cardList );
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
	
	private void addElement() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(0, "Empty");
		mHorAdapter.vCards.add( mHorAdapter.vCards.size()-1, list );
		//Generates the generic name for a new element "Catagory X"
		mHorAdapter.hElements.add( mHorAdapter.hElements.size()-1, "Category " + String.valueOf( mHorAdapter.hElements.size() ) );
		mHorAdapter.notifyDataSetChanged();
		mHorListView.smoothScrollBy( 406, 0 );
		Log.i( LOG_TAG, "Added Element: " + String.valueOf( mHorAdapter.hElements.size()-1 ) );
	}
	
	private void removeElement() {
		mHorListView.smoothScrollBy( -406, 0 );
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		    @Override
		    public void run() {
		    	if( mHorAdapter.hElements.size() > 1 & mHorAdapter.hElements.size() <= 11 ) {
					int spot = mHorAdapter.hElements.size()-2;
					mHorAdapter.hElements.remove(mHorAdapter.hElements.size()-1);
					mHorAdapter.hVerAdapters.remove(spot);
				}
				mHorAdapter.notifyDataSetChanged();
		    }
		}, 50);     // TODO problem
		Log.i( LOG_TAG, "Removed an Element" );
	}
	
	private void addCards(int arg0) {
		//adds a new card to the end of both lists
		mHorAdapter.vCards.get(arg0).add(mHorAdapter.vCards.get(arg0).size(), "New Card");
		mHorAdapter.hVerAdapters.get(arg0).vCardList.add(mHorAdapter.hVerAdapters.get(arg0).vCardList.size(), "New Card");
		mHorAdapter.hVerAdapters.get(arg0).notifyDataSetChanged();
	}
	
	private void removeCards(int arg0) {
		List<String> list = mHorAdapter.vCards.get(arg0);
		if(list.size() > 1){
			list.remove(list.size()-1);
			mHorAdapter.hVerAdapters.get(arg0).vCardList.remove(mHorAdapter.hVerAdapters.get(arg0).vCardList.size()-1);
			mHorAdapter.hVerAdapters.get(arg0).notifyDataSetChanged();	
		}
	}
	
	@Override
	public void onItemClick( AdapterView<?> parent, View veiw, int position, long id ) {
		Log.i( LOG_TAG, "onItemClick: " + position );
	}
		
	View.OnClickListener mHandler1 = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			addElement();
		}
	};
	
	View.OnClickListener mHandler2 = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			removeElement();
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
		
		List<String> hElements;
		LayoutInflater mInflater;
		int mResource;
		int mTextResId;
		int mListResId;
		List<List<String>> vCards;
		Button vAddButton;
		Button vRemoveButton;
		List<Button> vButtonList = new ArrayList<Button>();
		List<ListView> hVerListViews = new ArrayList<ListView>();
		List<VerAdapter> hVerAdapters = new ArrayList<VerAdapter>();
		
		public HorAdapter( Context context, int resourceId, int textViewResourceId, int listViewId, List<String> objects, List<List<String>> cards) {
			super( context, resourceId, textViewResourceId, objects);
			mInflater = LayoutInflater.from( context );
			mResource = resourceId;
			mTextResId = textViewResourceId;
			mListResId = listViewId;
			hElements = objects;
			vCards = cards;			
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
			int out  =  position != hElements.size()-1 ? 0 : 1 ;
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
				textView.setText( hElements.get(position) );
				
				if(hVerListViews.size() <= position){	
					hVerAdapters.add(new VerAdapter(getContext(), android.R.layout.simple_list_item_1, vCards.get(position)));
					hVerListViews.add((ListView) convertView.findViewById( mListResId ));
				}
				// must be reset on new data
				hVerListViews.get(position).setAdapter( hVerAdapters.get(position) );
				
				if(vButtonList.size() <= position*2){
					vButtonList.add((Button) convertView.findViewById( R.id.button2add )) ;
					vButtonList.add((Button) convertView.findViewById( R.id.button2rem ) );
				}
				// It's vital these are reset on new data
				vButtonList.get(position*2).setTag(position*2);
				vButtonList.get(position*2+1).setTag(position*2+1);
				vButtonList.get(position*2).setOnClickListener( mHandler3 );
				vButtonList.get(position*2+1).setOnClickListener( mHandler3 );
				
			} else {
				if( null == convertView ) {
					convertView = mInflater.inflate( R.layout.test_item_2, parent, false );
				}
				vAddButton = (Button) convertView.findViewById( R.id.button1add);
				vRemoveButton = (Button) convertView.findViewById( R.id.button1rem);
			
				vAddButton.setOnClickListener( mHandler1 );
				vRemoveButton.setOnClickListener( mHandler2 );
			}
			return convertView;
		}
	}
	
	//vertical List views
	private class VerAdapter extends ArrayAdapter<String> {
		List<String> vCardList = new ArrayList<String>();
		
		public VerAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				vCardList.add(objects.get(i));
			}
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}
	}
}
