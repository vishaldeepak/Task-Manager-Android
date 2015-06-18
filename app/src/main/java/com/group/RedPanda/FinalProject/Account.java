package com.group.RedPanda.FinalProject;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

public class Account extends ActionBarActivity
{
	SessionManager session;
	TextView statusLogged;
	TextView statusEmail;
	TextView statusId;
	ViewStub viewAccountDetails;
	ViewStub viewAccountRegister;
	HashMap<String, String> sessionDetails;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		session = new SessionManager(getApplicationContext());
		
		statusLogged =(TextView) findViewById(R.id.account_statusLogged);
		viewAccountDetails = (ViewStub) findViewById(R.id.AccountviewInflate);
		viewAccountRegister = (ViewStub) findViewById(R.id.AccountviewRegister);
		setUpScreen();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	protected void setUpScreen()
	{
		if(session.checkLogin())
		{
			statusLogged.setText("Logged In");
			View importPanel = viewAccountDetails.inflate();
			sessionDetails = session.getUserDetails();
			
			statusEmail=(TextView) importPanel.findViewById(R.id.account_emailid);
			statusId=(TextView) importPanel.findViewById(R.id.account_id);
			
			statusEmail.setText(sessionDetails.get(SessionManager.KEY_EMAIL));
			statusId.setText(sessionDetails.get(SessionManager.KEY_NAME));
			
		}
		else
		{	
			statusLogged.setText("Logged Out");
			View importPanel = viewAccountRegister.inflate();
			Button register = (Button) importPanel.findViewById(R.id.accountRegiser);
			register.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent startRegister = new Intent(Account.this,RegisterActivity.class);
					(Account.this).startActivity(startRegister);
				}
			});
			
		}	
	}
	
}
