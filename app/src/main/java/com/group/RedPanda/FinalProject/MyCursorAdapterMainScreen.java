package com.group.RedPanda.FinalProject;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyCursorAdapterMainScreen extends CursorAdapter{

	Long createTime;
	Long deadTime;
	Calendar myCal = Calendar.getInstance();
	SimpleDateFormat dateF = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	
	private static class MainScreenViewHolder
	{
		public TextView titleView;
		public TextView deadlineDate;
		public TextView onCreateDate;
	}
	
	
	public MyCursorAdapterMainScreen(Context context,Cursor cursor,int flags)
    {
    	super(context,cursor,flags);
    }
    
    @Override
    public void changeCursor(Cursor cursor) {
        super.changeCursor(cursor);
    }
	
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		final MainScreenViewHolder holder = (MainScreenViewHolder)view.getTag(); 
		holder.titleView.setText(cursor.getString(0));
		int a= Integer.parseInt(cursor.getString(5));
		
		if(cursor.getInt(6)==2)
		{
			view.setBackgroundColor(Color.parseColor("#FA5882"));
		
		}
		
		deadTime = Long.parseLong(cursor.getString(1));
		createTime = Long.parseLong(cursor.getString(2));
		myCal.setTimeInMillis(deadTime);
		if(a==0)
			holder.deadlineDate.setText("");
		else
			holder.deadlineDate.setText(dateF.format(myCal.getTime()));
		
		myCal.setTimeInMillis(createTime);
		holder.onCreateDate.setText(dateF.format(myCal.getTime()));
		
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(context).inflate(R.layout.listview_mainscreen,parent,false);
		MainScreenViewHolder holder = new MainScreenViewHolder();
		holder.titleView = (TextView) v.findViewById(R.id.title_mainscreen);
		holder.deadlineDate = (TextView) v.findViewById(R.id.subtitle_dateComplete_mainscreen);
		holder.onCreateDate = (TextView) v.findViewById(R.id.subtitle_dateCreate_mainscreen);
		
		v.setTag(holder);
		
		
		return v;
	}

}
