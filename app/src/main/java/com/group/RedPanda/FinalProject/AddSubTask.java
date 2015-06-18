package com.group.RedPanda.FinalProject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddSubTask extends ActionBarActivity {
	private ActionBar actionBar;
	private Bundle extras;
	private EditText subTaskText;
	private Button addSubTaskButton;
	private SQLiteDatabase database;
	private DatabaseHelper databaseHelper;
	private Long mainId;
	private String titleTask;
	boolean isProgress;
	//boolean isCounter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actionBar = getSupportActionBar();
		actionBar.setTitle("Add Sub Task");
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_addsubtask);
		 
		isProgress = false;
		//isCounter = false;
		
		extras =getIntent().getExtras();
		mainId= extras.getLong(DatabaseHelper.COLUMN_ROWID);
		titleTask=extras.getString(DatabaseHelper.COLUMN_TITLE);
		subTaskText = (EditText) findViewById(R.id.subtasktext_addsubTask);
		addSubTaskButton = (Button) findViewById(R.id.subtaskButton_addsubTask);
		
		
		addSubTaskButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addSubTaskDatabase();
			}
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.actionbar_addsubtask, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
	    switch (menuItem.getItemId()) {
	    case android.R.id.home:
	    	Intent subStart = new Intent(this,SubScreen.class);
	    	subStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	subStart.putExtra(DatabaseHelper.COLUMN_ROWID, mainId);
			subStart.putExtra(DatabaseHelper.COLUMN_TITLE, titleTask);
			this.startActivity(subStart);
	   
	    }
	  return (super.onOptionsItemSelected(menuItem));
	}
	
	
	
	
	
	protected void addSubTaskDatabase()
	{
		databaseHelper = new DatabaseHelper(this);
		database= databaseHelper.getWritableDatabase();
		
	
		
		if(checkFields())
		{
			
			int valid=5;
		
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_SUB_TITLE, subTaskText.getText().toString());
		values.put(DatabaseHelper.COLUMN_SUB_CONNECT_ID,mainId);
		values.put(DatabaseHelper.COLUMN_SUB_IS_COMPLETE, 0);
		
		if(isProgress)
			valid=1;
		else
			valid=0;
		values.put(DatabaseHelper.COLUMN_SUB_HASPROGRESS,valid);
		values.put(DatabaseHelper.COLUMN_SUB_PROGRESS, 0);
		
	/*	if(isCounter)
			valid=1;
		else
			valid=0;
	//	values.put(DatabaseHelper.COLUMN_SUB_HASCOUNTER, valid);
		*/
		
		database.insert(DatabaseHelper.DATABASE_SUB_TABLE, null, values);
		Toast.makeText(getApplicationContext(), "Sub task Added", Toast.LENGTH_SHORT).show();
		database.close();
		gotoSubScreen();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Error-Empty Title", Toast.LENGTH_SHORT).show();
			
		}
		
	}
	
	protected boolean checkFields()
	{

		if("".equals(subTaskText.getText().toString().trim()))
		{
			return false;
		}
		else
		{
			return true;
		}
		
		
	}
	
	
	protected void gotoSubScreen()
	{
		Intent subScreenStart = new Intent(this,SubScreen.class);
		subScreenStart.putExtra(DatabaseHelper.COLUMN_ROWID, mainId);
		subScreenStart.putExtra(DatabaseHelper.COLUMN_TITLE, titleTask);
		this.startActivity(subScreenStart);
	}
	
	public void onCheckBoxClicked(View view)
	{
		boolean isChecked = ((CheckBox) view).isChecked();
		
		switch(view.getId())
		{
		case R.id.addSubTask_checkProgress:
				isProgress = isChecked;
			break;
	/*	case R.id.addSubTask_checkCounter:
				isCounter = isChecked;
			break;*/
		}
	}
	
}
