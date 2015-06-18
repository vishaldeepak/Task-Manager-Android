package com.group.RedPanda.FinalProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	
	SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		session = new SessionManager(getApplicationContext());
		
		if(session.isLoggedIn())
		{		
		    startMainScreen();
		}
		else
		{
		    startRegisterScreen();
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(session.isLoggedIn())
		{
			
		startMainScreen();
		}
		else
		{
		startRegisterScreen();	
		}
		
	}
	
	
	public void startRegisterScreen()
	{
		Intent start = new Intent(this,RegisterActivity.class);
		this.startActivity(start);
	}
	
	public void startMainScreen()
	{
		Intent start = new Intent(this,MainScreen.class);
		this.startActivity(start);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
