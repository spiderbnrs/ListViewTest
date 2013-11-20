package com.example.listviewtest;

import net.sqlcipher.database.SQLiteDatabase;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class SMS_ListActivity extends ListActivity {
	
	private SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.v("begin", "listViewBegin");
		setContentView(R.layout.sms_list);
		SQLiteDatabase.loadLibs(this);
        MyDataBaseHelper dBaseHelper=new MyDataBaseHelper(this, "demo.db", null, 1);
        db =dBaseHelper.getWritableDatabase("1234");
        String[] columns=new String[]{"_id","thread_id","body","date"};
        Cursor cursor=(Cursor)db.query("secure_sms", columns, null, null, "thread_id", null, null);
        if (cursor!=null) {
			Log.v("ok", "we find something");
			while (cursor.moveToNext()) {
				Log.v("_id", cursor.getString(cursor.getColumnIndex("_id")));
				Log.v("thread_id",cursor.getString(cursor.getColumnIndex("thread_id")));
				Log.v("body", cursor.getString(cursor.getColumnIndex("body")));
				Log.v("date", cursor.getString(cursor.getColumnIndex("date")));
			}
		}
		SMS_CursorAdapter smsAdapter=new SMS_CursorAdapter(this, cursor, false);
		
		setListAdapter(smsAdapter);
		
	}
	

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Log.v("note", "you have clicked"+String.valueOf(position)+"item");
	}

	private static class SMS_CursorAdapter extends CursorAdapter{
		
		private LayoutInflater mInflater;
		private Context mContext;
		
		public SMS_CursorAdapter(Context context, Cursor c, boolean autoRequery) {
			super(context, c, autoRequery);
			// TODO Auto-generated constructor stub
			mContext=context;
			mInflater=LayoutInflater.from(mContext);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// TODO Auto-generated method stub
			//get the data
			String thread_idString=cursor.getString(cursor.getColumnIndex("thread_id"));
			String bodyString=cursor.getString(cursor.getColumnIndex("body"));
			String dateString=cursor.getString(cursor.getColumnIndex("date"));
			
			final ViewHolder holder = (ViewHolder) view.getTag();
			if (holder!=null)
			{
				holder.messageNameView.setText(thread_idString);
				holder.messageBodyView.setText(bodyString);
				holder.messageDaTeView.setText(dateString);
			}
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// TODO Auto-generated method stub
		    //initialize viewholder
			ViewHolder holder=null;
			holder=new ViewHolder();
			//get the newview
			View convertView=mInflater.inflate(R.layout.sms_item, parent, false);
		    //get the instance of the member of the viewholder,and set it to view's tag
			try{
			holder.messageNameView=(TextView)convertView.findViewById(R.id.sms_person);
			holder.messageBodyView=(TextView)convertView.findViewById(R.id.sms_body);
			holder.messageDaTeView=(TextView)convertView.findViewById(R.id.sms_date);
			
			convertView.setTag(holder);
			}catch (Exception e) {
				// TODO: handle exception
				Log.v("error", "what's wrong?");
			}
			
			return convertView;
		}
		
		public class ViewHolder{
			
			public TextView messageBodyView;
			public TextView messageDaTeView;
			public TextView messageNameView;
		}
	}
}
