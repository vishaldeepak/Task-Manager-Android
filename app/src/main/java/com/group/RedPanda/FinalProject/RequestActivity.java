package com.group.RedPanda.FinalProject;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.group.RedPanda.JSON.UserFunctions;

public class RequestActivity extends ActionBarActivity{

	Button requestButton;
	SessionManager session;
	JSONObject json;
	UserFunctions functions = new UserFunctions();
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activty_request);
		session = new SessionManager(getApplicationContext());
		
		setUpActivity();
	}
	
	private void setUpActivity()
	{
		requestButton = (Button) findViewById(R.id.request_getRequest);
		requestButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isNetworkAvailable())
				{
			   performRequest();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Enable Internet Connectivity",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	
	protected void performRequest()
	{
			new asyncProgressAddDataBase().execute();
		
			}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	

	private class asyncProgressAddDataBase extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) 
		{
			// TODO Auto-generated method stub
			Log.i("here", session.getUserid());
			//json = functions.hasRequest("yummi");
			json = functions.hasRequest(session.getUserid());
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {
				
		
				public void run() {
					try
					{
						String res = json.getString(KEY_SUCCESS);
						ArrayList<String> requests = new ArrayList<String>();
						ArrayList<String> onlineId = new ArrayList<String>();
						if(Integer.parseInt(res) == 1)
						{
							JSONArray arr = json.getJSONArray("Requests");
							for(int i = 0; i < arr.length(); i++)
							{
					            JSONObject Jobj = arr.getJSONObject(i);
					            requests.add(Jobj.getString("fromid"));
					            onlineId.add(Jobj.getString("onlineid"));
							}
							
							Intent startSR=new Intent(RequestActivity.this,ShowRequests.class);
							startSR.putStringArrayListExtra("requests", requests);
							startSR.putStringArrayListExtra("onlineid", onlineId);
							RequestActivity.this.startActivity(startSR);
						}
						else
						{
							Toast.makeText(getApplicationContext(), "No Requests Founds", Toast.LENGTH_SHORT).show();
						}
					 
						
						
						
					}catch (JSONException e) {
						e.printStackTrace();
					}
					

				}
			});
		
			
			
			
			super.onPostExecute(result);
			
		}
		
	}
	
}
