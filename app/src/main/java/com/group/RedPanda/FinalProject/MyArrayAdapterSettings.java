package com.group.RedPanda.FinalProject;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapterSettings extends ArrayAdapter<RowSettings> 
{
  Context context;
	
	private class ViewHolder
	{
		ImageView imageView;
		TextView txtView;
	}
	
	public MyArrayAdapterSettings(Context context,int resourceId,List<RowSettings> items) {
		super(context,resourceId,items);
		this.context = context;
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		RowSettings rowItem = getItem(position);
		LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if(convertView==null)
		{
			convertView = mInflater.inflate(R.layout.listview_settings, null);
			holder = new ViewHolder();
			holder.txtView =(TextView) convertView.findViewById(R.id.text_settings_list);
			holder.imageView = (ImageView) convertView.findViewById(R.id.image_settings_list);
			convertView.setTag(holder);
		}
		else
		{
			holder =(ViewHolder)convertView.getTag();
		}
		
		holder.txtView.setText(rowItem.getTitle());
		holder.imageView.setImageResource(rowItem.getImageId());
		
		
		
		return convertView;
	}
	
	

}
