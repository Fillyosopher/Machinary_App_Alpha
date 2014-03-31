package com.demo.Machinery_App;

import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.List;

import com.demo.Machinary_App.R;

import android.content.Context;
import android.os.Handler;
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
	
	private HListView mList;
	private Button vAddButton;
	private Button vRemoveButton;
	
	private List<String> hElements;
	private List<List<Machine>> vCards;
	
	private List<ListView> hVerListViews = new ArrayList<ListView>();
	private List<VerAdapter> hVerAdapters = new ArrayList<VerAdapter>();
	private List<Button> vButtonList = new ArrayList<Button>();
	
	private CoreDataSource mDataSource;
	
	public MachineAdapter( Context context, List<String> objects, List<List<Machine>> cards, HListView list, CoreDataSource database) {
		super( context, mResource, mTextResId, objects);
		mInflater = LayoutInflater.from( context );
		hElements = objects;
		vCards = cards;
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
		int out  =  position != hElements.size()-1 ? 0 : 1 ;
		return out;
	}

	private void addElement() {
		ArrayList<Machine> list = new ArrayList<Machine>();
		
		Machine newMachine = new Machine();
		newMachine.setName("Empty");
		list.add(0, newMachine);
		
		vCards.add( vCards.size()-1, list );
		
		//Generates the generic name for a new element "Category X"
		hElements.add( hElements.size()-1, "Category " + String.valueOf( hElements.size() ) );
		notifyDataSetChanged();
		mList.smoothScrollBy( 406, 0 );
		Log.i( LOG_TAG, "Added Element: " + String.valueOf( this.hElements.size()-1 ) );
	}
	
	private void removeElement() {
		mList.smoothScrollBy( -406, 0 );
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		    @Override
		    public void run() {
		    	if( hElements.size() > 1 ) {
					int spot = hElements.size()-2;
					hElements.remove(hElements.size()-1);
					hVerAdapters.remove(spot);
					hVerListViews.remove(spot);
				}
				notifyDataSetChanged();
		    }
		}, 50);     // TODO problem
		Log.i( LOG_TAG, "Removed an Element" );
	}
	
	private void addCards(int arg0) {
		//adds a new card to SQL and In-memory List
		// TODO fix this
		Machine newMachine = new Machine();
		mDataSource.addMachine(newMachine);
		
		vCards.get(arg0).add(vCards.get(arg0).size(), newMachine);

		hVerAdapters.get(arg0).notifyDataSetChanged();
	}
	
	private void removeCards(int arg0) {
		//removes as old card to SQL and In-memory List
		// TODO fix this
		Machine oldMachine = vCards.get(arg0).get(vCards.get(arg0).size()-1);
		mDataSource.deleteMachine(oldMachine);
		
		vCards.get(arg0).remove(vCards.get(arg0).size()-1);
		
		hVerAdapters.get(arg0).notifyDataSetChanged();
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
	
	//vertical List views
	private class VerAdapter extends ArrayAdapter<Machine> {
		public VerAdapter(Context context, int textViewResourceId, List<Machine> objects) {
			super(context, textViewResourceId, objects);
		}
		@Override
		public boolean hasStableIds() {
			return true;
		}
	}
}