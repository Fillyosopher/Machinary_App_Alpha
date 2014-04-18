package com.Machinery_App;

import java.util.ArrayList;
import java.util.List;

import com.Machinery_App.R;
import com.Machinery_App.db.CoreDataSource;
import com.Machinery_App.models.List2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MachineAdapter extends ArrayAdapter<String> {
	private static final String LOG_TAG = "ListAdapter";
	
	private static int mResource = R.layout.test_item_1;
	private static int mTextResId = android.R.id.text1;
	private static int mListResId = R.id.listview2;
	
	private LayoutInflater mInflater;
	
	private Button vAddButton;
	private Button vRemoveButton;
	
	private List<List2> hLists;
	
	private List<String> objects = new ArrayList<String>();
	private List<ListView> hVerListViews = new ArrayList<ListView>();
	private List<VerAdapter> hVerAdapters = new ArrayList<VerAdapter>();
	private List<Button> vButtonList = new ArrayList<Button>();
	
	private CoreDataSource coreDataSource;
	
	public MachineAdapter( Context context, List<String> objects, List<List2> lists, CoreDataSource database) {
		super( context, mResource, mTextResId, objects);
		hLists = lists;
		this.objects = objects;
		mInflater = LayoutInflater.from( context );
		coreDataSource = database;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public int getCount( ) {
		return hLists.size();
	}
	
	@Override
	public long getItemId( int position ) {
		return position;
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

	
	
	// TODO
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
			}
			Log.i( LOG_TAG, "Position: " + String.valueOf( position ) + "  List name: " + hLists.get(position).toString() );
			
			TextView Name = (TextView) convertView.findViewById(android.R.id.text1);
			Name.setText(objects.get(position));
			
			if(hVerListViews.size() <= position){	
				List<String> properCards = new ArrayList<String>();
				properCards = coreDataSource.getMachineNamesFromList(hLists.get(position));
				
				hVerAdapters.add(new VerAdapter(getContext(), properCards));
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
				Log.i( LOG_TAG, "Buttons at position " + String.valueOf( position ) );
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
		private static final String LOG_TAG = "VerAdapter";
		
		private List<String> strings = new ArrayList<String>();
		public VerAdapter(Context context, List<String> objects) {
			super(context, android.R.layout.simple_list_item_1, objects);
			for (int i=0; i<objects.size(); i++){
				Log.i(LOG_TAG, "Verticle Object " + i + ": " + objects.get(i));
			}
			strings = objects;
		}
		
		@Override
		public View getView( int position, View convertView, ViewGroup parent ) {
			if( null == convertView ) {
				convertView = mInflater.inflate( android.R.layout.simple_list_item_1, parent, false );
			}
			Log.i( LOG_TAG, "Position: " + String.valueOf( position ) + "  Object name: " + strings.get(position) );
			TextView Name = (TextView) convertView.findViewById(android.R.id.text1);
			Name.setText(strings.get(position));
			return convertView;
		}
		
		@Override
		public boolean hasStableIds() {
			return true;
		}
	}}