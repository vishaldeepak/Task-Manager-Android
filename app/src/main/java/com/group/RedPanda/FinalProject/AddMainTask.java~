package com.group.RedPanda.FinalProject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddMainTask extends ActionBarActivity implements OnDateSetListener,OnTimeSetListener
{
	Button open;
	Calendar nowCal,myCal;
	
	private SQLiteDatabase database;
	private DatabaseHelper databaseHelper;
	private Button dateSet;
	private Button timeSet;
	private Button confirm;
	private Button setRegularTime;
	private boolean isRegularyChange;
	private EditText titleText;
	private ImageButton regularHint;
	private View mydialogView;
	private int cycle =1;
	private RadioGroup rg;
	private RadioGroup yesNo;
    private EditText numberText;
	private AlertDialog myDialog;
	private AlertDialog.Builder regularTimeDialog;
    private boolean dialogCheck=true ; // Is all the stuff in the dailog Box is appropriate or filled
    private long timestore = 1l;
    private CheckBox timelimit;
    private boolean isFixed=true;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
 		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addmaintask);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	
		
		dateSet = (Button) findViewById(R.id.date_set_addmaintask);
		timeSet = (Button) findViewById(R.id.time_set_addmaintask);
		confirm = (Button) findViewById(R.id.create_addmaintask);
		titleText = (EditText) findViewById(R.id.title_edit_addmaintask);
		regularHint = (ImageButton) findViewById(R.id.regularHint);
		setRegularTime = (Button) findViewById(R.id.regularTime_addmainTask);
		mydialogView = getLayoutInflater().inflate(R.layout.regulartimedialog_addmaintask, null);
		timelimit = (CheckBox) findViewById(R.id.addTask_limittime);
		yesNo = (RadioGroup)findViewById(R.id.radioGroup);
		
		timelimit.setChecked(true);
		
		setRegularTime.setEnabled(false);
		nowCal = Calendar.getInstance();
		myCal = Calendar.getInstance();
		
		isRegularyChange = false;
		
		
		setUpDialog();
		
		
		dateSet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dateConfig();
			}
		});
		
		timeSet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timeConfig();
			}
		});
		
		
		regularHint.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "To regularly update your project milestones", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		setRegularTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showTimeDialog();
			}
		});
		
		confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			addTaskToDataBase();
			  
			}
		});
		
		
		timelimit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					dateSet.setEnabled(true);
					timeSet.setEnabled(true);
					  isFixed=true;
					
				}
				else
				{
					dateSet.setEnabled(false);
					timeSet.setEnabled(false);
					isFixed=false;
				}
			}
		});
		
	}
	
	protected void addTaskToDataBase()
	{
			if(checkFields())
			  {
				  nowCal = Calendar.getInstance();
				 
				  timestore=2l;
				  int regularValue;
				  int fixedValue;
				  
				  if(isRegularyChange)
				  {
					regularValue=1;
					getCycleTime();
				  }
				  else
				  {
					  regularValue=0;
				  }
				  
				  
				  if(isFixed)
					fixedValue=1;  
				  else
					  fixedValue=0;
				  
				  
				  
				  databaseHelper = new DatabaseHelper(this);
					database= databaseHelper.getWritableDatabase();
				  ContentValues values = new ContentValues();
				  values.put(DatabaseHelper.COLUMN_TITLE, titleText.getText().toString());
				  values.put(DatabaseHelper.COLUMN_DESCRIPTION, "       Enter Description");
				  values.put(DatabaseHelper.COLUMN_DATE_TIME, myCal.getTimeInMillis());
				  values.put(DatabaseHelper.COLUMN_ONCREATE_DATE_TIME, nowCal.getTimeInMillis());
				  values.put(DatabaseHelper.COLUMN_ISCOMPLETED, 0);
				  values.put(DatabaseHelper.COLUMN_ISFIXED, fixedValue);
				  values.put(DatabaseHelper.COLUMN_ISREGULAR, regularValue);
				  values.put(DatabaseHelper.COLUMN_COMPLETE_PERCENTAGE, 0);
				  values.put(DatabaseHelper.COLUMN_IS_FIRST_OPEN, 0);
				  values.put(DatabaseHelper.COLUMN_REGULAR_TIME, timestore);
				  values.put(DatabaseHelper.COLUMN_ADMIN_UID, "empty");
				  values.put(DatabaseHelper.COLUMN_ISONLINE, 0);
				  values.put(DatabaseHelper.COLUMN_ONLINE_IDENTITY, "Not Set");
				  long returnId = database.insert(DatabaseHelper.DATABASE_TABLE, null, values);
				  Toast.makeText(getApplicationContext(), "Task Created", Toast.LENGTH_SHORT).show();
				  
			  
				  
				  
				  Log.i("value",String.valueOf(regularValue));
				  
				  if(isRegularyChange)
					  setUpTaskNotify(returnId);  
				  
				  if(isFixed)
				  finalDateNotify(returnId);
				  
				  database.close();
				  databaseHelper.close();
				  gotoMain();
				  
			  }
		  
	}
	
	
	
	protected void finalDateNotify(long id)
	{
		AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		Log.i("location", String.valueOf(id));
		
		Intent notifyCycle = new Intent(this,NotifyEndChange.class);
		notifyCycle.setData(Uri.parse("custom://" + id));
		notifyCycle.setAction(String.valueOf(id));
		notifyCycle.putExtra(DatabaseHelper.COLUMN_ROWID, id);
		notifyCycle.putExtra(DatabaseHelper.COLUMN_TITLE, titleText.getText().toString());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),(int)id, notifyCycle, PendingIntent.FLAG_UPDATE_CURRENT);
		am.set(AlarmManager.RTC_WAKEUP, myCal.getTimeInMillis(), pendingIntent);
	}
	
	protected void setUpTaskNotify(long id)
	{ 
		AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		Log.i("location", String.valueOf(id));
		
		Intent notifyCycle = new Intent(this,NotifyCycleChange.class);
		notifyCycle.setData(Uri.parse("custom://" + id));
		notifyCycle.setAction(String.valueOf(id));
		notifyCycle.putExtra(DatabaseHelper.COLUMN_ROWID, id);
		notifyCycle.putExtra(DatabaseHelper.COLUMN_TITLE, titleText.getText().toString());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),(int)id, notifyCycle, PendingIntent.FLAG_UPDATE_CURRENT);
		
		//long thirtySecondsFromNow = System.currentTimeMillis() + 30 * 1000;
		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()
                + 60*1000,timestore, pendingIntent);
		Log.i("At here", "successfully");
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
							}
						
							
			
						}
					}
				});
				
			}
		});
	
	}
	
	
	protected void dateConfig()
	{
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		DialogFragment df = new DatePickerFragment();
		Bundle extras = new Bundle();
		//nowCal = Calendar.getInstance();
		extras.putInt(Constants.YEAR,myCal.get(Calendar.YEAR));
		extras.putInt(Constants.MONTH,myCal.get(Calendar.MONTH));
		extras.putInt(Constants.DAY, myCal.get(Calendar.DAY_OF_MONTH));
		extras.putString(Constants.NAME_CLASS, Constants.CLASS_ADDMAINTASK);
		df.setArguments(extras);
		df.show(ft, "DatePicker");
		Log.i("here1", "here");
	}

	
	protected void timeConfig()
	{
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		DialogFragment df = new TimePickerFragment();
		Bundle args = new Bundle();
		//nowCal=Calendar.getInstance();
		args.putInt(Constants.HOUR,myCal.get(Calendar.HOUR_OF_DAY));
		args.putInt(Constants.MIN,myCal.get(Calendar.MINUTE));
		args.putString(Constants.NAME_CLASS, Constants.CLASS_ADDMAINTASK);
		df.setArguments(args);
		df.show(ft, "TimePicker");
		
	}
	

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		myCal.set(Calendar.YEAR, year);
		myCal.set(Calendar.MONTH, monthOfYear);
		myCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		String dateForButton = dateFormat.format(myCal.getTime());
		dateSet.setText(dateForButton);
		Log.i("here", dateForButton);
	}
	
	

	
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		myCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		myCal.set(Calendar.MINUTE, minute);
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.TIME_FORMAT);
		String dateForTime = dateFormat.format(myCal.getTime());
		timeSet.setText(dateForTime);
	}
	
	
	
	protected void showTimeDialog()
	{
		myDialog.show();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.actionbar_addmaintask, menu);
		return true;
	}


	
	protected boolean checkFields()
	{
		
		boolean isRight=true;
		String errorText = new String(); 
		
		
		if("".equals(titleText.getText().toString().trim()))
		{
			isRight=false;
			errorText = "Enter a Title";
		}
		
		if(isFixed)
		{
			if(Constants.isDateWrong(myCal))
			{
				isRight=false;
				errorText = errorText + "\nEnter a proper Date and Time";
			}
		}
			
		
		if(isRegularyChange)
		{
			if(!dialogCheck)
			{
				isRight=false;
				errorText = errorText + "\nEnter Proper Cycle Change Time";
			}
			
		}
		
		
		if(isRight)
		{
			return true;
		}
		else
		{
			Toast.makeText(getApplicationContext(), errorText, Toast.LENGTH_SHORT).show();
			return false;
			
		}
		
		
	}
	
	
	protected void getCycleTime()
    {
	
		int d = Integer.parseInt(numberText.getText().toString());
		
		
		Long base=1l;
		if(cycle==1)//daily
		base=1000l*60l*60l*24l;
		else
		if(cycle==0)//hourly
			base=1000l*60l*60l;
		else
			if(cycle==2)//monthly
				base=30l*1440l*60000l;
			//Log.i("Log value", String.valueOf(d));
		timestore= base * (long)d;
    }
	
	
	protected void gotoMain()
	{
		Intent i = new Intent(this,MainScreen.class);
		this.startActivity(i);
		
	}
	
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.yes_radio_addmaintask:
	            if (checked)
	                {
	            	isRegularyChange=true;
	            	setRegularTime.setEnabled(true);
	            	dialogCheck=false;
	                }
	            break;
	        case R.id.no_radio_addmaintask:
	            if (checked)
	            {
	                isRegularyChange=false;
	                setRegularTime.setEnabled(false);
	                dialogCheck=true;
	            }
	            break;
	    }
	    
	
	    
	    
	    
	}



	



	
	
}
