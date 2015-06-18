package com.group.RedPanda.FinalProject;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.group.RedPanda.JSON.UserFunctions;

public class DoOnlineActivity extends ActionBarActivity {

	Bundle extras;
	boolean isOnline;
	Long mainTitleId;
	SessionManager session;
	ViewStub doOnlineFalse;
	ViewStub doOnlineTrue;
	TextView loggedinStatus;
	private SQLiteDatabase database;
	private DatabaseHelper databaseHelper;
	private Cursor data;
	String onlineIdentity;
	final ActionBarActivity myActivity = this;
	String first= "";
	String titleTask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//supportRequestWindowFeature(Window.FEATURE_PROGRESS);
		//supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//setSupportProgressBarIndeterminate(Boolean.TRUE);
		
		setContentView(R.layout.activity_doonline);
		
		
		
		extras = getIntent().getExtras();

	
		
		mainTitleId=extras.getLong(DatabaseHelper.COLUMN_ROWID);
		titleTask=extras.getString(DatabaseHelper.COLUMN_TITLE);
		session = new SessionManager(getApplicationContext());
		doOnlineFalse = (ViewStub) findViewById(R.id.doOnlineInflateFalse);
		doOnlineTrue = (ViewStub) findViewById(R.id.doOnlineInflateTrue);
		loggedinStatus = (TextView) findViewById(R.id.doOnline_status);
		
		
		
		
		databaseHelper = new DatabaseHelper(this);
		database = databaseHelper.getWritableDatabase();
		data = database.query(DatabaseHelper.DATABASE_TABLE, Constants.FROM_ISONLINE, DatabaseHelper.COLUMN_ROWID + "=?", new String[]{String.valueOf(mainTitleId)}, null, null, null);
		data.moveToFirst();
		
		if(data.getString(0).equalsIgnoreCase("0"))
			isOnline=false;
		else
			isOnline=true;
		
		onlineIdentity = data.getString(1);
		//Log.i("id", onlineIdentity);
	    if(session.isLoggedIn())
	    	loggedinStatus.setText("Logged In");
	    else
	    	loggedinStatus.setText("Not Logged In");
	    
		
		inflateRightView();
	}
	
	
	private void inflateRightView()
	{
		
				
		if(isOnline)
		{
			if(data.getString(0).equalsIgnoreCase("1"))
				inflateViewTrue();
			//else
				//Toast.makeText(getApplicationContext(), "you have send requests", Toast.LENGTH_SHORT).show();
		}
		else
		{
			TextView message;
			Button confirmButton;
			
		
			
			View onlineFalse = doOnlineFalse.inflate();
			message  = (TextView)onlineFalse.findViewById(R.id.doOnline_false_ConfirmText);
			confirmButton =(Button)onlineFalse.findViewById(R.id.doOnline_false_ConfirmButton);
			
			
			
			if(session.isLoggedIn())
			{
		
			message.setText("To manager Task with friends press the Go Online Button");
			confirmButton.setEnabled(true);
			confirmButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					ContentValues values = new ContentValues();
					values.put(DatabaseHelper.COLUMN_ISONLINE, 1);
					values.put(DatabaseHelper.COLUMN_ADMIN_UID,session.getUserid());
					
					
					String mainTitleString = String.valueOf(mainTitleId);
					onlineIdentity = titleTask + session.getUserid();
					values.put(DatabaseHelper.COLUMN_ONLINE_IDENTITY, onlineIdentity);
					
					database.update(DatabaseHelper.DATABASE_TABLE, values, DatabaseHelper.COLUMN_ROWID + "=?",new String[]{mainTitleString});
					
					//send Online here
					if(isNetworkAvailable())
					{
					uploadOnline();
					
					
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Please Enable Internet Connectivity",Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			}
			else
			{
				message.setText("Please Log In to access Online feature");
				confirmButton.setEnabled(false);
			}
			
		}
	}
	
	
	protected void uploadOnline()
	{
		new asyncProgressAddDataBase().execute();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
	    switch (menuItem.getItemId()) {
	    case android.R.id.home:
	    	Intent subStart = new Intent(this,SubScreen.class);
	    	subStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	subStart.putExtra(DatabaseHelper.COLUMN_ROWID, mainTitleId);
			subStart.putExtra(DatabaseHelper.COLUMN_TITLE, titleTask);
			this.startActivity(subStart);
	   
	    }
	  return (super.onOptionsItemSelected(menuItem));
	}
	
	
	
	private void inflateViewTrue()
	{
		View onlineTrue = doOnlineTrue.inflate();
		final TextView addedUsers;
		final EditText addUser;
		Button addUserButton;
		Button sendRequest;
	
		
		addUser = (EditText)onlineTrue.findViewById(R.id.viewstub_doOnline_true_addUserText);
		addUserButton =(Button)onlineTrue.findViewById(R.id.viewstub_donOnline_true_addUser);
		addedUsers  = (TextView)onlineTrue.findViewById(R.id.viewstub_doOnline_true_addedUsers);
		sendRequest = (Button)onlineTrue.findViewById(R.id.viewstub_doOnline_true_sendRequest);
		
		addUserButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String emailId=addUser.getText().toString();
			   first = first + emailId + "\n" ;
				addedUsers.setText(first);
			    addUser.setText("");
			    ContentValues values = new ContentValues();
			    values.put(DatabaseHelper.COLUMN_REQUEST_FROMID, session.getUserid());
			    values.put(DatabaseHelper.COLUMN_REQUEST_STATUS, 2);
			    values.put(DatabaseHelper.COLUMN_REQUEST_UID,emailId);
			    onlineIdentity=titleTask + session.getUserid();
			    Log.i("basd", onlineIdentity);
			    values.put(DatabaseHelper.COLUMN_REQUEST_CONNECTID, onlineIdentity);
			    database.insert(DatabaseHelper.DATABASE_REQUEST_TABLE, null, values);
			}
		});
		
		sendRequest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isNetworkAvailable())
				{
				ContentValues values = new ContentValues();
				values.put(DatabaseHelper.COLUMN_ISONLINE, 2);
				database.update(DatabaseHelper.DATABASE_TABLE, values, DatabaseHelper.COLUMN_ROWID + "=?",new String[]{String.valueOf(mainTitleId)});
				
				
				makeOnlineRequest();
				
				
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Enable Internet Connectivity",Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}
	
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	private void makeOnlineRequest()
	{
		new asyncProgressRequest().execute();
	  //  setProgressBarIndeterminateVisibility(false);
		
	}
	
	
	private class asyncProgressAddDataBase extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Cursor data = database.query(DatabaseHelper.DATABASE_TABLE, Constants.All_MAINTASKP, DatabaseHelper.COLUMN_ROWID + "=?", new String[]{String.valueOf(mainTitleId)}, null, null, null);
			UserFunctions userFunctions = new UserFunctions();
			userFunctions.uploadTask(data);
			return null;
		}
		
		
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			
	runOnUiThread(new Runnable() {
		
		public void run() 
		{

			Toast.makeText(DoOnlineActivity.this, "Task is Now Online", Toast.LENGTH_SHORT).show();
			Intent myIntent = getIntent();
			finish();
			DoOnlineActivity.this.startActivity(myIntent);
			
		}
	});
	}
	}

		private class asyncProgressRequest extends AsyncTask<Void, Void, Void>
		{

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				UserFunctions userFunctions = new UserFunctions();
				 onlineIdentity=titleTask + session.getUserid();
				data = database.query(DatabaseHelper.DATABASE_REQUEST_TABLE, Constants.FROM_REQUEST, DatabaseHelper.COLUMN_REQUEST_CONNECTID + "=?", new String[]{String.valueOf(onlineIdentity)}, null, null, null);
			    userFunctions.requestUser(data,DoOnlineActivity.this,onlineIdentity);
			    
				return null;
			}

		
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				
		runOnUiThread(new Runnable() {
			
			public void run() 
			{
				Toast.makeText(getApplicationContext(), "Request Send", Toast.LENGTH_SHORT).show();
				Intent myIntent = getIntent();
				finish();
				DoOnlineActivity.this.startActivity(myIntent);
				
			}
			});
		}
			
		}
		
	
	
}
