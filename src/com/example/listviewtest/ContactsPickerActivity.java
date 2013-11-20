package com.example.listviewtest;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author user
 *
 */
public class ContactsPickerActivity extends ListActivity {
	
	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		String[] projection=new String[]{"_id","display_name","sort_key"};
		cursor=getContentResolver().query(RawContacts.CONTENT_URI, projection, null, null, "sort_key");
		setListAdapter(new ContactAdapter(ContactsPickerActivity.this, cursor, false));
	}
	
	

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}



	private class ContactAdapter extends CursorAdapter{
		private LayoutInflater mInflater;
		private Context mContext;

		public ContactAdapter(Context context, Cursor c, boolean autoRequery) {
			super(context, c, autoRequery);
			// TODO Auto-generated constructor stub
			mContext=context;
			mInflater=LayoutInflater.from(mContext);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// TODO Auto-generated method stub
			final ViewHolder holder;
			if (view!=null) {
				holder=(ViewHolder)view.getTag();
				//set data
				holder.contactnameView.setText(cursor.getString(cursor.getColumnIndex("display_name")));
			}
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// TODO Auto-generated method stub
			 //initialize viewholder
			ViewHolder holder=null;
			holder=new ViewHolder();
			//get the newview
			View convertView=mInflater.inflate(R.layout.contact_picker_item, parent, false);
			try{
		    //get the instance of the member of the viewholder,and set it to view's tag
			if (convertView!=null) {
				holder.contactImageView=(ImageView)convertView.findViewById(R.id.contact_image);
				holder.contactnameView=(TextView)convertView.findViewById(R.id.contact_name);

				convertView.setTag(holder);
			}
			}catch (Exception e) {
				// TODO: handle exception
				Log.v("ok", "wrong");
			}
			return convertView;
		}
		
		public class ViewHolder{
			public ImageView contactImageView;
			public TextView contactnameView;
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		cursor.close();
	}
	
}
