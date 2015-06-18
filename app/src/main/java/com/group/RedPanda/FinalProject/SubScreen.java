package com.group.RedPanda.FinalProject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SubScreen extends ActionBarActivity implements OnItemClickListener{
	private ActionBar actionBar;
	private Bundle extras;
	private String titleTask;
	private Long maintitleid;
	private ListView subTasksListView;
	private SQLiteDatabase database;
	private DatabaseHelper databaseHelper;
	private Cursor data;
	private MyCursorAdapterSubScreen dataAdapter;
	private EditText descriprtionText;
	private String origDesc;
	private TextView actionTextTitle;
	private View mydialogView;
	private AlertDialog myDialog;
	private AlertDialog.Builder subTaskDialog;
	private String dialogSubTitle;
	private int progressValue;
	private int subId;
	private boolean hasProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscreen);
		
		
		//extras = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();
		
		//The above gets values from getIntent.getExtras() the first time the activity starts else gets the values from
		 //savedInstanceState and hence there is proper functioning of back Button when it is directed to this activity.
		
		
		 extras = getIntent().getExtras();//This is the means to receive the "putExtra" values
		
		titleTask = extras.getString(DatabaseHelper.COLUMN_TITLE);
		maintitleid = extras.getLong(DatabaseHelper.COLUMN_ROWID);
		//origDesc = extras.getString(DatabaseHelper.COLUMN_DESCRIPTION);
		
		
		  databaseHelper = new DatabaseHelper(this);
			database= databaseHelper.getWritableDatabase();
	
			data = database.query(DatabaseHelper.DATABASE_TABLE, Constants.FROM_SUB_SCREEN_DESC, DatabaseHelper.COLUMN_ROWID +  "=?", new String[]{String.valueOf(maintitleid)},null,null,null);
			data.moveToFirst();
			origDesc = data.getString(0);
			
		descriprtionText = (EditText) findViewById(R.id.subscreen_description);
		
		
		
		
		actionBar = getSupportActionBar();
		 //actionBar.setTitle(titleTask);
		
		
		
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(R.layout.actionbar_subscreen_title);
		
		subTasksListView = (ListView) findViewById(R.id.listview_subscreen);
		
		
		
		//descriprtionText.setOnFocusChangeListener(this);
		actionTextTitle = (TextView) findViewById(R.id.actionbar_subscreen_title);
		actionTextTitle.setText(titleTask);
		
		descriprtionText.setOnKeyListener(onSoftKeyboardDonePress);
		
		setUpListView();
		
		setUpDialogView();
		
	}
	
	
	private View.OnKeyListener onSoftKeyboardDonePress=new View.OnKeyListener()
	{
	    public boolean onKey(View v, int keyCode, KeyEvent event)
	    {
	        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.KEYCODE_BACK)
	        {
				new asyncAddDataBase().execute();
	            InputMethodManager hideKeyB = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	            hideKeyB.hideSoftInputFromWindow(getWindow().getCurrentFocus()
	                    .getWindowToken(), 0);
	        }
	        
	        
	        return false;
	    }
	};
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.subscreen_actionbar, menu);
		
		return true;
	}
	
	
	  @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			   switch(item.getItemId())
			   {
			   case R.id.addsubTask_subScreen:
				   startSubTask();
				   return true;
			   case R.id.subScreen_DoOnline:
				   startDoOnline();
			 default:
			return super.onOptionsItemSelected(item);
		   }
		}	
	  
	  
	  
	  protected void startDoOnline()
	  {
		  Intent startOnline = new Intent(this,DoOnlineActivity.class);
		  startOnline.putExtra(DatabaseHelper.COLUMN_ROWID, maintitleid);
		  startOnline.putExtra(DatabaseHelper.COLUMN_TITLE, titleTask);
		  this.startActivity(startOnline);
	  }
	  
	  protected void setUpListView()
	  {
		  
		  actionTextTitle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent startEdit = new Intent(SubScreen.this,EditMainTask.class);
				startEdit.putExtra(DatabaseHelper.COLUMN_TITLE, titleTask);
				startEdit.putExtra(DatabaseHelper.COLUMN_ROWID, maintitleid);
				(SubScreen.this).startActivity(startEdit);
			}
		});
		  
		  
		  registerForContextMenu(subTasksListView);
		  
		  subTasksListView.setOnItemClickListener(this);
		  
		  
		  
		  descriprtionText.setText(origDesc);
		
			data = database.query(DatabaseHelper.DATABASE_SUB_TABLE, Constants.FROM_SUBSCREEN, DatabaseHelper.COLUMN_SUB_CONNECT_ID + "=?", new String[]{String.valueOf(maintitleid)}, null, null, null);
			dataAdapter = new MyCursorAdapterSubScreen(this, data, 0);
			subTasksListView.setAdapter(dataAdapter);
			
	  }
	  
	  
	  protected void startSubTask()
	  {
		  Intent addsubTaskIntent = new Intent(this,AddSubTask.class);
		 addsubTaskIntent.putExtra(DatabaseHelper.COLUMN_ROWID, maintitleid);
		 addsubTaskIntent.putExtra(DatabaseHelper.COLUMN_TITLE, titleTask);
		  this.startActivity(addsubTaskIntent);
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
				
				deleteSubTask(info.id);//We will open a new edit menu which will ask for title
				return true;
				
			default:
			return super.onContextItemSelected(item);
			}
		} 
	  
	
	  
	  protected void deleteSubTask(long id)
	  {
			 database.delete(DatabaseHelper.DATABASE_SUB_TABLE, DatabaseHelper.COLUMN_SUB_ROWID +  "=?", new String[]{String.valueOf(id)});
			 data = database.query(DatabaseHelper.DATABASE_SUB_TABLE, Constants.FROM_SUBSCREEN, DatabaseHelper.COLUMN_SUB_CONNECT_ID + "=?", new String[]{String.valueOf(maintitleid)}, null, null, null);
				if(data!=null)
					data.moveToFirst();
				dataAdapter.changeCursor(data);
	  }
	  
	
	protected void setUpDialogView()
	{
		mydialogView = getLayoutInflater().inflate(R.layout.subtaskdialog_subscreen, null);
		
		
		
		subTaskDialog = new AlertDialog.Builder(this);
		subTaskDialog.setTitle("Sub Task Info");

		subTaskDialog.setView(mydialogView);
		subTaskDialog.setPositiveButton("Ok", null);//We want our own mechanism to handle the Postivie Click  so we pass null
		
		subTaskDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//subTaskDialog.
		
		myDialog=subTaskDialog.create();
		
		
		
		myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				// ``TODO Auto-generated method stub
				Button b = myDialog.getButton(AlertDialog.BUTTON_POSITIVE);

				TextView dialogSubTitle = (TextView) myDialog.findViewById(R.id.subScreen_SubTitle);
				dialogSubTitle.setText(SubScreen.this.dialogSubTitle);
				
				final SeekBar dailogSubProgress = (SeekBar) myDialog.findViewById(R.id.subScreen_Progress);
				
				if(hasProgress)
				{
				dailogSubProgress.setProgress(progressValue);
				dailogSubProgress.setVisibility(View.VISIBLE);
				}
				else
				dailogSubProgress.setVisibility(View.GONE);
				
				
				b.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(progressValue != dailogSubProgress.getProgress())
						{
						ContentValues values = new ContentValues();
						values.put(DatabaseHelper.COLUMN_SUB_PROGRESS,dailogSubProgress.getProgress() );
						database.update(DatabaseHelper.DATABASE_SUB_TABLE, values, DatabaseHelper.COLUMN_SUB_ROWID + "=?",new String[]{String.valueOf(subId)});
						myDialog.dismiss();
						progressValue=dailogSubProgress.getProgress();
						new asyncProgressAddDataBase().execute();
						}
					}
				});
				
			
				
			}
			
			
			
		});
		
		
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) 
	{
		// TODO Auto-generated method stub
		
	
		
		//data=database.query(Database, columns, selection, selectionArgs, groupBy, having, orderBy)
		Cursor temp = database.query(DatabaseHelper.DATABASE_SUB_TABLE, Constants.FROM_SUBSCREEN, DatabaseHelper.COLUMN_SUB_ROWID + "=?", new String[]{String.valueOf(id)}, null, null, null);
		temp.moveToFirst();
		if(temp.getInt(5)==1)
			hasProgress=true;
			else
		    hasProgress=false;
		
		subId = temp.getInt(2);
		progressValue = temp.getInt(4);		
		dialogSubTitle = temp.getString(0);
		myDialog.show();
	}
	

	
	
	
	private class asyncProgressAddDataBase extends AsyncTask<Void, Void, Void>
	{
		Cursor temp;
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			  
			ContentValues values = new ContentValues();
			values.put(DatabaseHelper.COLUMN_SUB_PROGRESS,progressValue );
			database.update(DatabaseHelper.DATABASE_SUB_TABLE, values, DatabaseHelper.COLUMN_SUB_ROWID + "=?",new String[]{String.valueOf(subId)});		
		
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			
			temp= database.query(DatabaseHelper.DATABASE_SUB_TABLE, Constants.FROM_SUBSCREEN, DatabaseHelper.COLUMN_SUB_CONNECT_ID + "=?", new String[]{String.valueOf(maintitleid)}, null, null, null);
			temp.moveToFirst();
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					dataAdapter.changeCursor(temp);
				}
			});
			
			super.onPostExecute(result);
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}

	
	
	
	
	private class asyncAddDataBase extends AsyncTask<Void, Void, Void>
	{

		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			  
			String newDesc = descriprtionText.getText().toString();
					

			if("".equals(newDesc))
			{
				newDesc="      Enter Description";
			}
			else
			if(newDesc!=origDesc)
			{
			
				database= databaseHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put(DatabaseHelper.COLUMN_DESCRIPTION, newDesc);
				database.update(DatabaseHelper.DATABASE_TABLE, values, DatabaseHelper.COLUMN_ROWID +  "=?", new String[]{String.valueOf(maintitleid)})	;
				}	
			return null;
		}
		
		
		
	}
	
	
}
	
	

