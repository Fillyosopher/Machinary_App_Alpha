package com.demo.Machinery_App;

import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.List;

import com.demo.Machinary_App.R;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MachineAdapter extends ArrayAdapter<List2> {
	private static final String LOG_TAG = "ListAdapter";
	
	private static int mResource = R.layout.test_item_1;
	private static int mTextResId = android.R.id.text1;
	private static int mListResId = R.id.listview2;
	
	private LayoutInflater mInflater;
	
	private HListView mList;
	private Button vAddButton;
	private Button vRemoveButton;
	
	private List<List2> hLists;
	
	private List<ListView> hVerListViews = new ArrayList<ListView>();
	private List<VerAdapter> hVerAdapters = new ArrayList<VerAdapter>();
	private List<Button> vButtonList = new ArrayList<Button>();
	
	private CoreDataSource mDataSource;
	
	public MachineAdapter( Context context, List<List2> objects, HListView list, CoreDataSource database) {
		super( context, mResource, mTextResId, objects);
		hLists = objects;
		mInflater = LayoutInflater.from( context );
		mList = list;
		mDataSource = database;
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
		int out  =  position != hLists.size()-1 ? 0 : 1 ;
		return out;
	}

	View.OnClickListener mHandler1 = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			//addElement();
		}
	};

	View.OnClickListener mHandler2 = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			//removeElement();
		}
	};
	

	View.OnClickListener mHandler3 = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			final Object tag = arg0.getTag();
			//turns tag object into readable int
			int i = Integer.parseInt(tag.toString().replace("tag",""));
			if(i % 2 == 1){
				//removeCards((i-1)/2);
			} else if (i% 2 == 0){
				//addCards(i/2);
			}
		}
	};
	

	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		int type = getItemViewType( position );
		if( type == 0 ) {	
			if( null == convertView ) {
				convertView = mInflater.inflate( mResource, parent, false );
				Log.i( LOG_TAG, "Position: " + String.valueOf( position ) );
				Log.i( LOG_TAG, "List name: " + hLists.get(position).toString() );
			}
			
			TextView textView = (TextView) convertView.findViewById( mTextResId );
			textView.setText( hLists.get(position).toString() );
			
			if(hVerListViews.size() <= position){	
				List<String> properCards = new ArrayList<String>();
				properCards = mDataSource.getMachineNamesFromList(hLists.get(position));
				
				hVerAdapters.add(new VerAdapter(getContext(), android.R.layout.simple_list_item_1, properCards));
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
				Log.i( LOG_TAG, "Buttons: " + String.valueOf( position ) );
			}
			vAddButton = (Button) convertView.findViewById( R.id.button1add);
			vRemoveButton = (Button) convertView.findViewById( R.id.button1rem);
			
			vAddButton.setOnClickListener( mHandler1 );
			vRemoveButton.setOnClickListener( mHandler2 );
		}
		return convertView;
	}
	
	//vertical List views
	private class VerAdapter extends ArrayAdapter<String> {
		public VerAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
			Log.i( LOG_TAG, "Card name: " + objects.get(0) );
		}
		
		@Override
		public boolean hasStableIds() {
			return true;
		}
	}
}