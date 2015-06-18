package com.group.RedPanda.FinalProject;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SettingsActivity extends ActionBarActivity implements OnItemClickListener{
	String[] settingValues = new String[]{"Account","About"};
	ArrayList<String> settingTextValues;
	ListView listSettings;
	Integer[] images={R.drawable.ic_action_accounts,R.drawable.ic_launcher};
	List<RowSettings> rowItems;
	MyArrayAdapterSettings adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		listSettings = (ListView) findViewById(R.id.list_settings);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		rowItems = new ArrayList<RowSettings>();
		
		for(int i =0;i<settingValues.length;i++)
		{
			RowSettings item = new RowSettings(images[i],settingValues[i]);
			rowItems.add(item);
		}
		
		adapter = new MyArrayAdapterSettings(this, R.layout.listview_settings, rowItems);
		
		listSettings.setAdapter(adapter);
		listSettings.setOnItemClickListener(this);
		
		
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
		
	}




	@Override
	public void onItemClick(AdapterView<?> parentView, View view, int position, long id) 
	{
		// TODO Auto-generated method stub
		if(position==0)
		{
			Intent account = new Intent(this,Account.class);
			this.startActivity(account);
		}
		else if(position==1)
		{
			Intent about = new Intent(this,AboutActivity.class);
			this.startActivity(about);
		}
	}
	
	
	
}
