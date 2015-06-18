package com.group.RedPanda.FinalProject;




import java.lang.reflect.Field;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;



public class MainScreen extends ActionBarActivity implements OnQueryTextListener,OnCloseListener,OnItemClickListener{

	private SQLiteDatabase database;
	private DatabaseHelper databaseHelper;
	Cursor data;
	ListView listView;
	MyCursorAdapterMainScreen dataAdapter;
	 SearchView msearchView;
	 private ActionMode mActionMode;
	 private ActionMode.Callback mActionModeCallback;
	 Context context=this;
	String temp;
	 Cursor curse;
	SessionManager session;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainscreen);
		listView = (ListView) findViewById(R.id.listview_mainscreen);

		registerForContextMenu(listView);
		getOverflowMenu();
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setTitle("Task");
		setListViewMainScreen();
	}
	
	
	private void getOverflowMenu() {

	     try {
	        ViewConfiguration config = ViewConfiguration.get(this);
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	 @Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			super.onCreateContextMenu(menu, v, menuInfo);
			MenuInflater mi = getMenuInflater();
			mi.inflate(R.menu.contextmenu_mainscreen, menu);
		}
	
	 
	 @Override
		public boolean onContextItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			switch(item.getItemId())
			{
			case R.id.mainscreen_delete:
				
				deleteTask(info.id);//We will open a new edit menu which will ask for title
				return true;
				
			default:
			return super.onContextItemSelected(item);
			}
		}
	 
	 
	 private void deleteTask(long id)
	 {
		 	
			 AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);

			    Intent notifyCycle = new Intent(this, NotifyCycleChange.class);
			    notifyCycle.setData(Uri.parse("custom://" + id));
				notifyCycle.setAction(String.valueOf(id));
			    PendingIntent pendingUpdateIntent = PendingIntent.getBroadcast(getApplicationContext(), (int) id, notifyCycle, PendingIntent.FLAG_UPDATE_CURRENT);
			    
			    
			    
			    
			    // Cancel alarms
			    try {
			        alarmManager.cancel(pendingUpdateIntent);
			    } catch (Exception e) {
			        Log.e("TAG", "AlarmManager update was not canceled. " + e.toString());
			    }
			    

			     notifyCycle = new Intent(this, NotifyEndChange.class);
			    notifyCycle.setData(Uri.parse("custom://" + id));
				notifyCycle.setAction(String.valueOf(id));
			    pendingUpdateIntent = PendingIntent.getBroadcast(getApplicationContext(), (int) id, notifyCycle, PendingIntent.FLAG_UPDATE_CURRENT);
			    
			    
			    
			    
			    // Cancel alarms
			    try {
			        alarmManager.cancel(pendingUpdateIntent);
			    } catch (Exception e) {
			        Log.e("TAG", "AlarmManager update was not canceled. " + e.toString());
			    }

			    
			
			    
			    //Delete all sub Tasks also ---Done 
			    
			    database=databaseHelper.getWritableDatabase();
				 database.delete(DatabaseHelper.DATABASE_TABLE, DatabaseHelper.COLUMN_ROWID +  "=?", new String[]{String.valueOf(id)});
				 database.delete(DatabaseHelper.DATABASE_SUB_TABLE, DatabaseHelper.COLUMN_SUB_CONNECT_ID + "=?", new String[]{String.valueOf(id)});
				 
				 data = database.query(DatabaseHelper.DATABASE_TABLE, Constants.FROM_MAINSCREEN, null ,null, null, null, null);
					if(data!=null)
						data.moveToFirst();
					dataAdapter.changeCursor(data);
				
			    
	 }
	 
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		moveTaskToBack(true);//This enables the last working activity in the stack of activity manager to work, 
							 //and this activity is moved in back of the stack
	}
	
	protected void setListViewMainScreen()
	{
		databaseHelper = new DatabaseHelper(this);
		database= databaseHelper.getReadableDatabase();
		data = database.query(DatabaseHelper.DATABASE_TABLE, Constants.FROM_MAINSCREEN, null ,null, null, null, null);
		if(data!=null)
			data.moveToFirst();
		dataAdapter = new MyCursorAdapterMainScreen(this, data, 0);
		listView.setAdapter(dataAdapter);
		
		
		listView.setOnItemClickListener(this);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		
		
		/*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() 
		{
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				if(mActionMode !=null)
					return false;
				
				mActionMode = startSupportActionMode(mActionModeCallback);
				view.setSelected(true);
				listView.setItemChecked(position, true);
				return true;
				
			}
			
		});
		
	         mActionModeCallback = new ActionMode.Callback() {
		    @Override
		    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		        MenuInflater inflater = mode.getMenuInflater();
		        inflater.inflate(R.menu.context_actionmenu_mainscreen, menu);
		        return true;
		    }

		    @Override
		    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		        return false;
		    }

		    @Override
		    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

		        switch (item.getItemId()) {
		            case R.id.delete_context_actionmenu_mainscreen:
		                mode.finish();//close action mode
		                return true;
		            
		            default:
		                return false;
		        }
		    }

		    @Override
		    public void onDestroyActionMode(ActionMode mode) {
		        mActionMode = null;
		    }
		};
		
		*/
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) 
	{
		// TODO Auto-generated method stub
		Intent subStart = new Intent(this,SubScreen.class);
		
		Cursor temp = (Cursor) listView.getItemAtPosition(position);
		

		subStart.putExtra(DatabaseHelper.COLUMN_ROWID, id);
		subStart.putExtra(DatabaseHelper.COLUMN_TITLE, temp.getString(0));
		//subStart.putExtra(DatabaseHelper.COLUMN_DESCRIPTION, temp.getString(4));
		this.startActivity(subStart);
		
		
	}
	
	
	protected void startnext()
	{
		Intent i = new Intent(this,AddMainTask.class);
		this.startActivity(i);
		
	}
	
	
	
	
	
	   @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		   switch(item.getItemId())
		   {
		   case R.id.addTask_MainScreen_AB:
			   startnext();
			   return true;
		   case R.id.settings_MainScreen:
			   
			   Intent start = new Intent(this,SettingsActivity.class);
				this.startActivity(start);
			   
				return true;
		   case R.id.request_MainScreen:
			   session = new SessionManager(getApplicationContext());
			   if(session.isLoggedIn())
			   {
				   Intent startReg = new Intent(this,RequestActivity.class);
					this.startActivity(startReg);
				   
			   }
			   else
			   {
				   Toast.makeText(getApplicationContext(), "Please Log In", Toast.LENGTH_SHORT).show();
			   }
			   return true;
		 default:
		return super.onOptionsItemSelected(item);
	   }
	}	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_mainscreen, menu);
		final MenuItem searchItem = (MenuItem) menu.findItem(R.id.search_MainScreen);
	    msearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		
		
	  
		msearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus)
				{
					MenuItemCompat.collapseActionView(searchItem);
					msearchView.setQuery("", false);
				}
				
			}
		});
	
		
		msearchView.setOnCloseListener(this);
		msearchView.setOnQueryTextListener(this);
		
		return true;//We have handled all that is needed
	}
	

	@Override 
	public boolean onQueryTextSubmit(String query) {
        // Don't care about this.	
		
        return true;
    }
	
	/*DatabaseHelper databaseHelper = new DatabaseHelper(this);
	SQLiteDatabase database = databaseHelper.getReadableDatabase();
	Cursor cursor = database.query(DatabaseHelper.DATABASE_TABLE, Constants.FROM_MAINSCREEN, databaseHelper.COLUMN_TITLE +" LIKE ?", new String[] { newText + "%"}, null, null, null);
	if(cursor==null)
	{
		return true;
	}
	else
	{
		cursor.moveToFirst();
    dataAdapter.changeCursor(cursor);
	}*/


    @Override
    public boolean onClose() {
        if (!TextUtils.isEmpty(msearchView.getQuery())) {
            msearchView.setQuery(null, true);
        }
        return true;
    }
    
    public boolean onQueryTextChange(String newText) 
	{
    	temp=newText;
    	new asyncSearch().execute();
    	return true;
	}

	private class asyncSearch extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			/*
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
			});*/
			curse = getWordMatches(temp,Constants.FROM_MAINSCREEN);
			return null;
			
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(curse==null)
			{
			}
			else
			{
				curse.moveToFirst();
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						dataAdapter.changeCursor(curse);
					}
				});
				
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		

	    public Cursor getWordMatches(String query,String[] columns)
		{
			String 	selection =DatabaseHelper.COLUMN_TITLE + " LIKE ?";
			String[] selectionArgs = new String[]{query + "%"};
			return myQuery(selection,selectionArgs,columns);
		}
		
		private Cursor  myQuery(String selection,String[] selectionArgs,String[] columns)
		{
			DatabaseHelper databaseHelper = new DatabaseHelper(MainScreen.this);
			
			SQLiteDatabase database = databaseHelper.getReadableDatabase();
			
			//Cursor cursor = builder.query(databaseHelper.getReadableDatabase(), columns, selection, selectionArgs, null, null, null);
			Cursor cursor = database.query(DatabaseHelper.DATABASE_TABLE, columns, selection, selectionArgs, null, null, null);
			 if (cursor == null) {
			        return null;
			    }
			    return cursor;

		}
		
		

		
	}
	
}




      

