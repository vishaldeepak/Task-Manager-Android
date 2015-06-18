package com.group.RedPanda.FinalProject;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.group.RedPanda.JSON.UserFunctions;

public class ShowRequests extends ActionBarActivity implements OnItemClickListener {
	Bundle extras;
	ArrayList<String> requests = new ArrayList<String>();
	ArrayList<String> online = new ArrayList<String>();
	ListView showRequests;
	AlertDialog.Builder builder;
	AlertDialog myDialog;
	String onlineId;
	UserFunctions functions = new UserFunctions();
	int mainId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showrequests);
		getSupportActionBar().setTitle("Requests");
		setUpDialog();
		extras = getIntent().getExtras();
		requests = extras.getStringArrayList("requests");
		online = extras.getStringArrayList("onlineid");
		showRequests=(ListView) findViewById(R.id.listview_showRequests);
		
		 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                 this, 
                     R.layout.listview_showrequest,R.id.showRequestText,
                 requests );
         showRequests.setAdapter(arrayAdapter);
		
         showRequests.setOnItemClickListener(this);
         
	}

	protected void setUpDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirm Request")
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) 
                   {
                       changeRequestStatus(1);
     //                  gotoMain();
                   }
               })
               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) 
                   {
                	   changeRequestStatus(0);
       //         	   gotoMain();
                   }
               });
        // Create the AlertDialog object and return it
        myDialog=    builder.create();

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
	{
		// TODO Auto-generated method stub
		onlineId = online.get(position);
		//Log.i("clicked", onlineId);
		myDialog.show();
	}
	
	public void changeRequestStatus(int id)
	{
		mainId=id;
		new asyncProgressAddDataBase().execute();
	
	}
	
	protected void gotoMain()
	{
		
	}
	
	
	private class asyncProgressAddDataBase extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... arg0) {
			functions.changeRequest(onlineId,mainId,ShowRequests.this);
			return null;
		}
		
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		
runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Intent startMain = new Intent(ShowRequests.this,MainScreen.class);
				ShowRequests.this.startActivity(startMain);
			}
		});
	}
	}
}
