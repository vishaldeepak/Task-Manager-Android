package com.group.RedPanda.FinalProject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class AboutActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_about);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
	}
}
