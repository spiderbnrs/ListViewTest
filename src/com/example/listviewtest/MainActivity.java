package com.example.listviewtest;

import java.util.ArrayList;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import android.R.anim;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

	private SQLiteDatabase db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //load lib
        SQLiteDatabase.loadLibs(this);
        MyDataBaseHelper dBaseHelper=new MyDataBaseHelper(this, "demo.db", null, 1);
        db =dBaseHelper.getWritableDatabase("1234");
        Button SMSButton=(Button) findViewById(R.id.button1);
        Button contactButton=(Button)findViewById(R.id.button2);
        
        SMSButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    Intent intent=new Intent();
			    intent.setClass(MainActivity.this, ContactsPickerActivity.class);
			    startActivity(intent);
			}
		});
        
        
        contactButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, SMS_ListActivity.class);
				startActivity(intent);
				}
        });
    }
    /*
     * 数据库相关字段如下：
     * id:在数据库中的id
     * thread_id:对应某个联系人的id,对于某一个人，或者说某个手机号，thread_id唯一
     * address:联系人的手机号
     * person:对于通讯录中的某个人，但是此结果并不是常有。
     * date:发件时间
     * protocol:"0:SMS","1:MMS"
     * read:"0:未读","1:已读"
     * status:"-1:收到","0：complete"，"64:pending","128:fail"
     * type:"0:ALL","1:INBOX","2:SENT","3:DRAFT","4:OUTBOX","5:FAILED","6:QUEUED"
     * service_center: 短信服务中心号码编号 
     * subject:"主题"
     * body:短信的正文
     */
	public void Get_SMS(Context context,String address) {
		
		String[] projection=new String[]{
			"_id","thread_id","address","person","date",
			"protocol","read","status","type","service_center","subject","body"
		};
		//读出短信
		String uri="content://sms";
		android.database.Cursor cursor=getContentResolver().query(Uri.parse(uri), projection, "address"+"=", new String[]{address}, null);
		//将所有短信封装在contentlist里面
		ArrayList<ContentValues> contentList=new ArrayList<ContentValues>();
		if (cursor!=null) {
			Log.v("start", "let's start");
			if(cursor.moveToFirst())
			{
				int thread_idIndex=cursor.getColumnIndex("thread_id");
				int addressIndex=cursor.getColumnIndex("address");
				int dateIndex=cursor.getColumnIndex("date");
				int protocolIndex=cursor.getColumnIndex("protocol");
				int readIndex=cursor.getColumnIndex("read");
				int statusIndex=cursor.getColumnIndex("status");
				int typeIndex=cursor.getColumnIndex("type");
				int serviceIndex=cursor.getColumnIndex("service_center");
				int subjectIndex=cursor.getColumnIndex("subject");
				int bodyIndex=cursor.getColumnIndex("body");
				do {
					ContentValues values=new ContentValues();
					values.put("thread_id", cursor.getInt(thread_idIndex));
					values.put("address", cursor.getString(addressIndex));
					values.put("date", cursor.getLong(dateIndex));
					values.put("protocol", cursor.getString(protocolIndex));
					values.put("read", cursor.getInt(readIndex));
					values.put("status", cursor.getInt(statusIndex));
					values.put("type", cursor.getInt(typeIndex));
					values.put("service_center", cursor.getString(serviceIndex));
					values.put("subject", cursor.getString(subjectIndex));
					values.put("body", cursor.getString(bodyIndex));
					contentList.add(values);
					Log.v("_id", String.valueOf(cursor.getInt(cursor.getColumnIndex("_id"))));
				} while (cursor.moveToNext());
			}
			
			cursor.close();
			//然后将其写入自己的数据库中,使用事务
			db.beginTransaction();
			for (ContentValues contentValues:contentList) {
				db.insert("secure_sms", null, contentValues);
			}
			//设置事务成功
			db.setTransactionSuccessful();
			db.endTransaction();
			Log.v("end", "we are ending");
			//删除原来数据库的记录
			getContentResolver().delete(Uri.parse(uri), "address"+"=", new String[]{address});
			db.close();
		}
	}
	
	public void restoreSMS(Context context,String address)
	{
		//打开数据库
		MyDataBaseHelper dBaseHelper=new MyDataBaseHelper(this, "demo.db", null, 1);
        db =dBaseHelper.getWritableDatabase("1234");
        //
        //找出与address相同的短信目录
        Cursor cursor=db.query("secure_sms", null, "address"+"=", new String[]{address}, null, null, null); 
        if(cursor!=null)
        {
        	Log.v("messgage", "restore begin!");
        	if (cursor.moveToFirst())
        	{
        		int thread_idIndex=cursor.getColumnIndex("thread_id");
				int dateIndex=cursor.getColumnIndex("date");
				int protocolIndex=cursor.getColumnIndex("protocol");
				int readIndex=cursor.getColumnIndex("read");
				int statusIndex=cursor.getColumnIndex("status");
				int typeIndex=cursor.getColumnIndex("type");
				int serviceIndex=cursor.getColumnIndex("service_center");
				int subjectIndex=cursor.getColumnIndex("subject");
				int bodyIndex=cursor.getColumnIndex("body");
				
				
        	}
        }
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
