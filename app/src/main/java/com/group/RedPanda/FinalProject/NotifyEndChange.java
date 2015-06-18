package com.group.RedPanda.FinalProject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class NotifyEndChange extends BroadcastReceiver{

	NotificationCompat.Builder myNM;
	int id;
	String title;
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase database;
	
	@Override
	public void onReceive(Context context, Intent receivedIntent) {
		// TODO Auto-generated method stub
		Intent resultIntent = new Intent(context,SubScreen.class);
		//Log.i("at notifiy", "here");
		
     	databaseHelper = new DatabaseHelper(context);
		database = databaseHelper.getWritableDatabase();
		
				
		
		Bundle extras = receivedIntent.getExtras();
		id = (int) extras.getLong(DatabaseHelper.COLUMN_ROWID);
		   title = extras.getString(DatabaseHelper.COLUMN_TITLE);
		
		   ContentValues values = new ContentValues();
			values.put(DatabaseHelper.COLUMN_ISCOMPLETED, 2);
			database.update(DatabaseHelper.DATABASE_TABLE, values, DatabaseHelper.COLUMN_ROWID + "=?", new String[]{String.valueOf(id)});

		   
		   
		   resultIntent.putExtra(DatabaseHelper.COLUMN_ROWID, extras.getLong(DatabaseHelper.COLUMN_ROWID));
			resultIntent.putExtra(DatabaseHelper.COLUMN_TITLE, title);
			resultIntent.putExtra(DatabaseHelper.COLUMN_DESCRIPTION, extras.getString(DatabaseHelper.COLUMN_DESCRIPTION));
		   
		   
		   myNM = new NotificationCompat.Builder(context)
			 .setSmallIcon(R.drawable.ic_menu_info_details)
		    .setContentTitle(title)
		    .setContentText("Project Has Reached Its End Date");
		   

		   

			
			PendingIntent resultPendingIntent =
				    PendingIntent.getActivity(
				    context,
				    0,
				    resultIntent,
				    PendingIntent.FLAG_UPDATE_CURRENT
				);
		   
			myNM.setContentIntent(resultPendingIntent);
			
			NotificationManager mNotifyMgr = 
			        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

			myNM.setAutoCancel(true);
			
			mNotifyMgr.notify(id,myNM.build());
			//Log.i("at notifiy", "end");
		  	}

}
