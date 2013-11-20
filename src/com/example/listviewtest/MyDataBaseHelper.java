package com.example.listviewtest;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabase.CursorFactory;
import net.sqlcipher.database.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper {

	//建立短信数据表
	public static final String CREATE_SECURE_SMS_TABLE="create table secure_sms(_id integer PRIMARY KEY" +
			",thread_id integer,address varchar,person integer,date long,date_sent interger,protocol text," +
			"read integer,status integer,type integer,subject text,body text,service_center text,locked integer," +
			"displayName)";
	//建立联系人数据表
	public static final String CREATE_SECURE_CONTACT_TABLE="create table secure_contacts(" +
			"_id integer PRIMARY　KEY," +
			"";
	
	public MyDataBaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_SECURE_SMS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
