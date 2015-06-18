package com.group.RedPanda.FinalProject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyCursorAdapterSubScreen extends CursorAdapter{

	Cursor data;
	private SQLiteDatabase database;
	private DatabaseHelper databaseHelper;
	Context myContext;
	
	private static class SubScreenViewHolder
	{
		public CheckBox isComplete;
		public TextView content; 
		public ProgressBar proBar;
	}
	
	
	public MyCursorAdapterSubScreen(Context context,Cursor cursor,int flags)
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
		SubScreenViewHolder holder = (SubScreenViewHolder) view.getTag();
		holder.content.setText(cursor.getString(0)); 
		
	if(cursor.getString(1).equalsIgnoreCase("0"))
		holder.isComplete.setChecked(false);
	else
		holder.isComplete.setChecked(true);
	
	if(cursor.getInt(5)==1)
	{
		holder.proBar.setVisibility(View.VISIBLE);
		holder.proBar.setProgress(cursor.getInt(4));
	}
	else
		holder.proBar.setVisibility(View.GONE);
	
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View v = LayoutInflater.from(context).inflate(R.layout.listview_subscreen,parent, false);
		final SubScreenViewHolder holder = new SubScreenViewHolder();
		holder.content = (TextView) v.findViewById(R.id.text_subScreen);
		holder.isComplete=(CheckBox) v.findViewById(R.id.checkBox_subScreen);
		holder.proBar = (ProgressBar)v.findViewById(R.id.subscreeb_progress);
		final String subRowid = cursor.getString(2);
		final String subTitle = cursor.getString(0);
		databaseHelper = new DatabaseHelper(context);
		database= databaseHelper.getWritableDatabase();
		final Context myContext = context;
		
		
		holder.isComplete.setOnCheckedChangeListener( new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				boolean check = isChecked;
				
				holder.isComplete.setEnabled(false);
				ContentValues changesOne = new ContentValues();
				if(check)
				changesOne.put(DatabaseHelper.COLUMN_SUB_IS_COMPLETE,"1" );
				else
				changesOne.put(DatabaseHelper.COLUMN_SUB_IS_COMPLETE,"0" );
				
				
				database.update(DatabaseHelper.DATABASE_SUB_TABLE,changesOne,DatabaseHelper.COLUMN_SUB_ROWID + "=?",new String[]{subRowid});
				
				holder.isComplete.setEnabled(true);
			}
		});
		
		
	/*	holder.content.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
			}
		});*/
		
		
		v.setTag(holder);
		
		return v;
	}
	

	
}
