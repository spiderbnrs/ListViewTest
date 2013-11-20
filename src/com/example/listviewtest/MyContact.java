package com.example.listviewtest;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MyContact extends ListActivity {
	private ArrayList<HashMap<String, Object>> mData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyContactdApter adapter=new MyContactdApter(this, mData);
		setListAdapter(adapter);
	}

	private static class MyContactdApter extends BaseAdapter{

	private ArrayList<HashMap<String, Object>> list;
		
	
		public class ViewHolder{
			public TextView contactName;
			public ImageView contactPic;
		}
		
		private Context mContext;
		private ArrayList<HashMap<String, Object>> mList;
		private String[] contacts={};
		//�������벼��;
		private LayoutInflater inflater = null;
		//ѡ�����;
		private HashMap<Integer, Boolean> isSelected;
			
			
		public  MyContactdApter(Context context,ArrayList<HashMap<String, Object>> list) {
			mContext=context;
			mList=list;
			//���뵱ǰ����
			inflater=LayoutInflater.from(mContext);
			//��ʼ��HashMap
			isSelected=new HashMap<Integer, Boolean>();
			//�����а�ť��ʼ��δѡ��
			init();
		}
		private void init() {
			for (int i = 0; i < isSelected.size(); i++) {
				isSelected.put(i, false);
			}
		}
			
		public HashMap<Integer, Boolean> getIsSelected(){
			return isSelected;
		}
		
		//ʵ�ַ���
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder=null;
			if (convertView==null) {
				viewHolder=new ViewHolder();
				//���벼�ַָ�converView
				convertView=inflater.inflate(R.layout.mycontact_item, null);
				viewHolder.contactName=(TextView)convertView.findViewById(R.id.contact_name);
				viewHolder.contactPic=(ImageView)convertView.findViewById(R.id.contact_image);
				
				convertView.setTag(viewHolder);
			}
			else {
				viewHolder=(ViewHolder)convertView.getTag();
			}
			viewHolder.contactName.setText((String)list.get(position).get("name"));
			viewHolder.contactPic.setImageBitmap((Bitmap)list.get(position).get("pic"));
			
			
			return convertView;
		}
		
		
	}
}
