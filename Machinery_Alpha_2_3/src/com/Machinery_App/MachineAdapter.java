package com.Machinery_App;

import java.util.ArrayList;
import java.util.List;

import com.Machinery_App.R;
import com.Machinery_App.db.CoreDataSource;
import com.Machinery_App.models.List2;
import com.Machinery_App.models.Machine;

import android.content.Context;
import android.content.Intent;
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

	private CoreDataSource datasource;

	public MachineAdapter( Context context, List<String> objects, List<List2> lists, CoreDataSource database) {
		super( context, mResource, mTextResId, objects);
		hLists = lists;
		this.objects = objects;
		mInflater = LayoutInflater.from( context );
		datasource = database;
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

	private View.OnClickListener onClickListener1 = new View.OnClickListener() {
		// TODO
		@Override
		public void onClick(final View v) {
			final Object tag = v.getTag();
			//turns tag object into readable int
			int i = Integer.parseInt(tag.toString().replace("tag",""));
			Log.i(LOG_TAG, "add/rem card button clicked");
			if(i % 2 == 1){
				Log.i(LOG_TAG, "rem card button clicked");
				Machine deleteme = datasource.getMachinesFromList(datasource.getList((i-1)/2+1)).get(0);
				datasource.deleteMachine(deleteme);
				hVerAdapters.get((i-1)/2).strings.remove(0);
				hVerAdapters.get((i-1)/2).notifyDataSetChanged();
			} else if (i % 2 == 0){
				Log.i(LOG_TAG, "add card button clicked");
				datasource.addMachine(new Machine(0,"New Machine",i/2+1));
				hVerAdapters.get(i/2).strings.add("New Machine");
				hVerAdapters.get(i/2).notifyDataSetChanged();
			}
		}
	};

	private View.OnClickListener onClickListener2 = new View.OnClickListener() {
		//TODO
		@Override
		public void onClick(final View v) {
			Log.i(LOG_TAG, "add/rem list");
			switch (v.getId()){
			case R.id.button1add:
				Log.i(LOG_TAG, "add list");
				datasource.addList(new List2(0,"New List","Name"));
				objects.add("New List");
				hLists.add(datasource.getLastList());
				notifyDataSetChanged();
				break;
			case R.id.button1rem:
				Log.i(LOG_TAG, "rem list");
				List2 deleteme = datasource.getAllLists().get(0);
				datasource.deleteList(deleteme);
				objects.remove(hLists.size()-1);
				hLists.remove(hLists.size()-1);
				notifyDataSetChanged();
				break;
			}
		}
	};


	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		/**position starts at 0 and is linear
		 * List indexes start at 0 and are linear
		 * Table IDs start at 1 and are pseudo linear
		 * */
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
				properCards = datasource.getMachineNamesFromList(hLists.get(position));

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
			vButtonList.get(position*2).setOnClickListener( onClickListener1 );
			vButtonList.get(position*2+1).setOnClickListener( onClickListener1 );

		} else {
			if( null == convertView ) {
				convertView = mInflater.inflate( R.layout.test_item_2, parent, false );
				Log.i( LOG_TAG, "Buttons at position " + String.valueOf( position ) );
			}
			vAddButton = (Button) convertView.findViewById( R.id.button1add);
			vRemoveButton = (Button) convertView.findViewById( R.id.button1rem);

			vAddButton.setOnClickListener( onClickListener2 );
			vRemoveButton.setOnClickListener( onClickListener2 );
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