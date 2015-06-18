package com.group.RedPanda.FinalProject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditMainTask extends ActionBarActivity implements OnDateSetListener,OnTimeSetListener{
	private SQLiteDatabase database;
	private DatabaseHelper databaseHelper;
	private Cursor data;
	Bundle extras;
	Long mainId;
	EditText title;
	CheckBox isDateSet;
	CheckBox isRegularSet;
	ViewStub stubDateSet;
	ViewStub stubRegularSet;
	boolean origDateSetValue;
	boolean origRegularSetValue;
	View inflatedDate;
	View inflatedRegular;
	TextView Date;
	TextView Time;
	SimpleDateFormat dateF = new SimpleDateFormat(Constants.DATE_FORMAT);
	SimpleDateFormat timeF = new SimpleDateFormat(Constants.TIME_FORMAT);
	Long taskTime;
	Calendar curCal = Calendar.getInstance();
	TextView regularFreq;
	Long regularTime;
	//Calendar myCal;
	private AlertDialog myDialog;
	private AlertDialog.Builder regularTimeDialog;
	private View mydialogView;
	private int cycle =1;
	private RadioGroup rg;
	private EditText numberText;
	private boolean dialogCheck=true ;
	private Button saveButton;
	private Button backButton;
	private int isFixed;
	private int isRegular;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editmaintask);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		extras = getIntent().getExtras();
		mainId = extras.getLong(DatabaseHelper.COLUMN_ROWID);

		getSupportActionBar().setTitle(extras.getString(DatabaseHelper.COLUMN_TITLE));
		
		databaseHelper = new DatabaseHelper(this);
		database = databaseHelper.getWritableDatabase();
		
		data=database.query(DatabaseHelper.DATABASE_TABLE, Constants.FROM_EDIT_MAINSCREEN, DatabaseHelper.COLUMN_ROWID + "=?", new String[]{String.valueOf(mainId)}, null, null, null);
		data.moveToFirst();
	
		mydialogView = getLayoutInflater().inflate(R.layout.regulartimedialog_addmaintask, null);
		
		setUpDialog();
		
		setUpView();		
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_editmaintask, menu);
		return true;
	}*/


	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
	    switch (menuItem.getItemId()) {
	    case android.R.id.home:
	    	startSubStart();
			break;
	    }
	  return (super.onOptionsItemSelected(menuItem));
	}
	
	
	private void startSubStart()
	{
		Intent subStart = new Intent(this,SubScreen.class);
    	subStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	subStart.putExtra(DatabaseHelper.COLUMN_ROWID, mainId);
		subStart.putExtra(DatabaseHelper.COLUMN_TITLE, extras.getString(DatabaseHelper.COLUMN_TITLE));
		this.startActivity(subStart);
	}
	private void setUpView()
	{
		backButton = (Button) findViewById(R.id.editmaintask_back);
		saveButton = (Button) findViewById(R.id.editmaintask_save);
		title = (EditText) findViewById(R.id.editmainTask_title);
		isDateSet = (CheckBox) findViewById(R.id.editmaintask_isFixed);
		isRegularSet = (CheckBox) findViewById(R.id.editmaintask_isRegular);
		stubDateSet = (ViewStub) findViewById(R.id.editDateSet);
		stubRegularSet = (ViewStub) findViewById(R.id.editRegularSet);
		
		
		regularTime = Long.parseLong(data.getString(7));

		//myCal = Calendar.getInstance();
		
		dateStubActivate();
		
		title.setText(data.getString(0));
		
		
		isFixed=Integer.parseInt(data.getString(5));
		isRegular=Integer.parseInt(data.getString(6));
		
		if(Integer.parseInt(data.getString(5))==1)
		{
			isDateSet.setChecked(true);
			origDateSetValue=true;
		}
		else
			{
			isDateSet.setChecked(false);
			origDateSetValue=false;
			inflatedDate.setVisibility(View.GONE);
			}
		
		
		Log.i("value",data.getString(6));
		
		if(Integer.parseInt(data.getString(6))==1)
		{
			isRegularSet.setChecked(true);
			origRegularSetValue=false;
			calRegularTime(regularTime);
		}
		else
		{
			isRegularSet.setChecked(false);
			inflatedRegular.setVisibility(View.GONE);
			origRegularSetValue=true;
		}
		
		
		
		isDateSet.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
				// TODO Auto-generated method stub
				if(isChecked)
				{
					inflatedDate.setVisibility(View.VISIBLE);
				}
				else
				{
					inflatedDate.setVisibility(View.GONE);
				}
			}
		});
		
		
		
			isRegularSet.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
				// TODO Auto-generated method stub
				if(isChecked)
				{
					inflatedRegular.setVisibility(View.VISIBLE);
					dialogCheck=false;
				}
				else
				{
					inflatedRegular.setVisibility(View.GONE);
					dialogCheck=true;
				}
			}
		});
		
	
			saveButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finalSaveCheck();
				}
			});
			
			backButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startSubStart();
				}
			});
			
	}
	
	protected void dateStubActivate()
	{
		
		inflatedRegular = stubRegularSet.inflate();
		inflatedDate = stubDateSet.inflate();
		Date = (TextView) inflatedDate.findViewById(R.id.editmainTask_date);
		Time = (TextView) inflatedDate.findViewById(R.id.editmainTask_time);
		regularFreq = (TextView) inflatedRegular.findViewById(R.id.editmainTask_regularFreq);
		
	    taskTime = Long.parseLong(data.getString(1));
		Date.setText(dateF.format(taskTime));
		Time.setText(timeF.format(taskTime));
		
		curCal.setTimeInMillis(taskTime);
		
		Date.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dateEdit();
			}
		});
		
		Time.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timeEdit();
			}
		});
		
		regularFreq.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myDialog.show();
			}
		});
		
	}


	

	protected void dateEdit()
	{
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		DialogFragment df = new DatePickerFragment();
		Bundle extras = new Bundle();
		//nowCal = Calendar.getInstance();
		extras.putInt(Constants.YEAR,curCal.get(Calendar.YEAR));
		extras.putInt(Constants.MONTH,curCal.get(Calendar.MONTH));
		extras.putInt(Constants.DAY, curCal.get(Calendar.DAY_OF_MONTH));
		extras.putString(Constants.NAME_CLASS, Constants.CLASS_EDITMAINTASK);
		df.setArguments(extras);
		df.show(ft, "DatePicker");
	}
	
	
	protected void timeEdit()
	{
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		DialogFragment df = new TimePickerFragment();
		Bundle args = new Bundle();
		//nowCal=Calendar.getInstance();
		args.putInt(Constants.HOUR,curCal.get(Calendar.HOUR_OF_DAY));
		args.putInt(Constants.MIN,curCal.get(Calendar.MINUTE));
		args.putString(Constants.NAME_CLASS, Constants.CLASS_EDITMAINTASK);
		
		df.setArguments(args);
		df.show(ft, "TimePicker");
	
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		curCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		curCal.set(Calendar.MINUTE, minute);
	    Time.setText(timeF.format(curCal.getTime()));
		
	}


	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		curCal.set(Calendar.YEAR, year);
		curCal.set(Calendar.MONTH, monthOfYear);
		curCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		Date.setText(dateF.format(curCal.getTime()));
	}
	
	protected void calRegularTime(long time)
	{
	
		
		//myCal.setTimeInMillis(time);
		
		
		if((time/(30l*1440l*60000l)) >= 1)	    
				regularFreq.setText(String.valueOf((time/(30l*1440l*60000l)) + " Month"));
		else
			if((time/(1000l*60l*60l*24l)) >= 1)
					regularFreq.setText(String.valueOf((time/(1000l*60l*60l*24l))) + " Day");
			else
				regularFreq.setText(String.valueOf((time/(1000l*60l*60l))) + " Hour");
		long val = time/86400000l;
		//Log.i("the val", String.valueOf(val));
		
		
		regularTime = time;
		//regularFreq.setText(String.valueOf(val));
		
		/*if(myCal.get(Calendar.MONTH) >0)
			regularFreq.setText(String.valueOf(myCal.get(Calendar.MONTH)) + " Month");
		else
		if(myCal.get(Calendar.DAY_OF_MONTH)-1 >0)
			regularFreq.setText(String.valueOf(myCal.get(Calendar.DAY_OF_MONTH)) + " Day");
		else
			//regularFreq.setText(String.valueOf(TimeUnit.MILLISECONDS.toHours(time)));
			regularFreq.setText(String.valueOf(myCal.get(Calendar.HOUR_OF_DAY)) + " Hour");*/
	}
	
	
	protected void setUpDialog()
	{
		rg = (RadioGroup) mydialogView.findViewById(R.id.regularRadioGroup);
		numberText = (EditText) mydialogView.findViewById(R.id.time_dialog_addmaintask);
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(checkedId)
				{
				
				case R.id.daily_regular_radiobutton:
					cycle=1;
					break;
				case R.id.hourly_regular_radiobutton:
					cycle=0;
					break;
				case R.id.monthly_regular_radiobutton:
					cycle=2;
					break;
				}
			}});
		

		regularTimeDialog = new AlertDialog.Builder(this);
		regularTimeDialog.setTitle("Set Time Cycle");

		regularTimeDialog.setView(mydialogView);
		regularTimeDialog.setPositiveButton("Ok", null);
		
		
		
		
		regularTimeDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
		myDialog=regularTimeDialog.create();		

		myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				// TODO Auto-generated method stub
				Button b = myDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				b.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String text = numberText.getText().toString();
						boolean value = false;
						dialogCheck=false;
						
						if(text.length()==0)
						{	
							Toast.makeText(getApplicationContext(),"Enter value in field", Toast.LENGTH_SHORT).show();
						}
						else
						{
							int d = Integer.parseInt(numberText.getText().toString());
							
							if(cycle==0)
							{
					            if(d<1 || d>23)
					            	Toast.makeText(getApplicationContext(),"Enter value between 1-23", Toast.LENGTH_SHORT).show();
					            else
					            	value=true;
							}
							else if(cycle==1)
							{
								if(d<1 || d>30)
									Toast.makeText(getApplicationContext(),"Enter value between 1-30", Toast.LENGTH_SHORT).show();
								else
					            	value=true;
							}
							else if(cycle==2)
							{
								if(d<1 || d>12)
									Toast.makeText(getApplicationContext(),"Enter value between 1-12", Toast.LENGTH_SHORT).show();
								else
					            	value=true;
							}

							
							if(value)
							{
								dialogCheck=true;
								myDialog.dismiss();
								calRegularTime(getCycleTimePrec());
							}
						
							
			
						}
					}
				});
				
			}
		});
	
		
		
	}
	
	protected void finalSaveCheck()
	{
		boolean allIsWell=true;
		String errorText = new String();
		
		if("".equals(title.getText().toString().trim()))
		{
			allIsWell=false;
			errorText = "Enter a Title";
		}
		
		
			if(isDateSet.isChecked())// If Date is Set
			{
				Calendar nowCal = Calendar.getInstance();
				if(curCal.getTimeInMillis() < nowCal.getTimeInMillis())
				{
					errorText = errorText + "\nEnter a proper Date and Time";
					allIsWell=false;
				}
			}
			
			if(!dialogCheck)
			{
				errorText = errorText + "\nEnter Proper Cycle Change Time";
				allIsWell=false;
			}
			
			
			if(!allIsWell)
			{
				Toast.makeText(getApplicationContext(), errorText, Toast.LENGTH_SHORT).show();
			}
			else
			{
				makePermanentChange();
			}
			
		
			
	}
	
	protected Long getCycleTimePrec()
	{
		int d = Integer.parseInt(numberText.getText().toString());
		
		
		
		Long base=1l;
		if(cycle==1)//daily
		base=86400000l;
		else
		if(cycle==0)//hourly
			base=3600000l;
		else
			if(cycle==2)//monthly
				base=30l*1440l*60000l;
			//Log.i("Log value", String.valueOf(d));
		return(base * (long)d);
	}
	
	
	protected void makePermanentChange()
	{
		ContentValues values = new ContentValues();
		
		if(origDateSetValue!=isDateSet.isChecked())
		{
			if(isDateSet.isChecked())
			{
				isFixed = 1;
				createDateNotify();
			}
			else
			{
				isFixed= 0;
				deleteDateNotfiy();
				values.put(DatabaseHelper.COLUMN_ISCOMPLETED, isFixed);
			}
		}
		
		if(origRegularSetValue!=isRegularSet.isChecked())
		{
			if(isRegularSet.isChecked())
			{
				isRegular=1;
				createRegularNotify();
			}
			else
			{
				isRegular=0;
				deleteRegularNotify();
			}
		}
		
		
		
		values.put(DatabaseHelper.COLUMN_TITLE, title.getText().toString());
		values.put(DatabaseHelper.COLUMN_ISFIXED, isFixed);
		values.put(DatabaseHelper.COLUMN_ISREGULAR, isRegular);
		values.put(DatabaseHelper.COLUMN_DATE_TIME, curCal.getTimeInMillis());
		values.put(DatabaseHelper.COLUMN_REGULAR_TIME, regularTime);
		
		database.update(DatabaseHelper.DATABASE_TABLE, values,DatabaseHelper.COLUMN_ROWID +  "=?", new String[]{String.valueOf(mainId)});
		Toast.makeText(getApplicationContext(), "Updated Task Details", Toast.LENGTH_SHORT).show();
		startSubStart();
	}
	
	
	protected void createDateNotify()
	{
		long id = mainId;
		AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);	
		Intent notifyCycle = new Intent(this,NotifyEndChange.class);
		notifyCycle.setData(Uri.parse("custom://" + mainId));
		notifyCycle.setAction(String.valueOf(mainId));
		notifyCycle.putExtra(DatabaseHelper.COLUMN_ROWID, mainId);
		notifyCycle.putExtra(DatabaseHelper.COLUMN_TITLE, title.getText().toString());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),(int)id, notifyCycle, PendingIntent.FLAG_UPDATE_CURRENT);
		am.set(AlarmManager.RTC_WAKEUP, curCal.getTimeInMillis(), pendingIntent);
	}
	
	
	protected void deleteDateNotfiy()
	{
		long id = mainId;
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

	}
	
	protected void createRegularNotify()
	{
		long id = mainId;
		AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		Log.i("location", String.valueOf(id));
		
		Intent notifyCycle = new Intent(this,NotifyCycleChange.class);
		notifyCycle.setData(Uri.parse("custom://" + id));
		notifyCycle.setAction(String.valueOf(id));
		notifyCycle.putExtra(DatabaseHelper.COLUMN_ROWID, id);
		notifyCycle.putExtra(DatabaseHelper.COLUMN_TITLE, title.getText().toString());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),(int)id, notifyCycle, PendingIntent.FLAG_UPDATE_CURRENT);
		
		//long thirtySecondsFromNow = System.currentTimeMillis() + 30 * 1000;
		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()
                + 60*1000,regularTime, pendingIntent);
		Log.i("At here", "successfully");
		
	}
	
	
	protected void deleteRegularNotify()
	{
		    
		AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
			long id =mainId;
		    Intent notifyCycle = new Intent(this, NotifyEndChange.class);
		    notifyCycle.setData(Uri.parse("custom://" + id));
			notifyCycle.setAction(String.valueOf(id));
		    PendingIntent pendingUpdateIntent = PendingIntent.getBroadcast(getApplicationContext(),(int)id, notifyCycle, PendingIntent.FLAG_UPDATE_CURRENT);
		    
		    
		    
		    
		    // Cancel alarms
		    try {
		        alarmManager.cancel(pendingUpdateIntent);
		    } catch (Exception e) {
		        Log.e("TAG", "AlarmManager update was not canceled. " + e.toString());
		    }
	}
	
}
