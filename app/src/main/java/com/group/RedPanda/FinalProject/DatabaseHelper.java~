package com.group.RedPanda.FinalProject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION =13;
	private static final String DATABASE_NAME = "mydb";
	
	
	//First Table Main Table
	public static final String DATABASE_TABLE="mytables";
	public static final String COLUMN_ROWID= "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_DATE_TIME = "date_time";
	public static final String COLUMN_ONCREATE_DATE_TIME="on_create_data_time";
	public static final String COLUMN_ISCOMPLETED = "iscompleted";
	public static final String COLUMN_ISFIXED = "isfixed";
	public static final String COLUMN_ISREGULAR= "isRegular";
	public static final String COLUMN_REGULAR_TIME = "regular_time";
	public static final String COLUMN_IS_FIRST_OPEN = "firsttime";
	public static final String COLUMN_COMPLETE_PERCENTAGE="complete_percentage";
	public static final String COLUMN_ISONLINE="isonline";
	public static final String COLUMN_ADMIN_UID="adminUID";//This states this guy is the Admin of the Project and the Creator
	public static final String COLUMN_ONLINE_IDENTITY="onlineIdentity";
	
	private static final String DATABASE_CREATE = "create table "  
			+ DATABASE_TABLE + " (" + COLUMN_ROWID
			+ " integer primary key autoincrement, " + COLUMN_TITLE + " text not null, " 
			+ COLUMN_DESCRIPTION + " text not null, "
		+	COLUMN_ADMIN_UID + " text not null, "
		+	COLUMN_ONLINE_IDENTITY + " text not null, "
		+   COLUMN_ISREGULAR + " integer not null check(" + COLUMN_ISREGULAR + " in (0,1)), "
		+   COLUMN_ISONLINE + " integer not null check(" + COLUMN_ISONLINE + " in (0,1,2)), " 
			+ COLUMN_DATE_TIME +
			 " integer not null, " + COLUMN_ONCREATE_DATE_TIME + " integer not null, " + 
			COLUMN_ISCOMPLETED + " integer not null check(" +
					COLUMN_ISCOMPLETED + " in (0,1,2)), " + COLUMN_ISFIXED + " integer not null check(" +
					COLUMN_ISFIXED + " in (0,1)), "+ COLUMN_IS_FIRST_OPEN + " integer not null check(" +
							COLUMN_IS_FIRST_OPEN + " in (0,1)), " + COLUMN_REGULAR_TIME + " integer not null, " 
					+ COLUMN_COMPLETE_PERCENTAGE + " integer not null);";


	
	//Second Table - Sub Task Table
	public static final String DATABASE_SUB_TABLE="subtables";
	public static final String COLUMN_SUB_ROWID="_id";
	public static final String COLUMN_SUB_TITLE = "title";
	public static final String COLUMN_SUB_IS_COMPLETE="iscomplete";
	public static final String COLUMN_SUB_CONNECT_ID="connect" ;
	public static final String COLUMN_SUB_HASDATE="hasDate";
	public static final String COLUMN_SUB_DATE="completeDate";
	public static final String COLUMN_SUB_HASPROGRESS="hasprogress";
	public static final String COLUMN_SUB_PROGRESS="progress";
	//public static final String COLUMN_SUB_HASCOUNTER="hascounter";
	//public static final String COLUMN_SUB_COUNTER="counter";
	
	private static final String DATABASE_SUB_CREATE =  "create table "  
			+ DATABASE_SUB_TABLE + " (" + COLUMN_SUB_ROWID 
			+ " integer primary key autoincrement, " + COLUMN_SUB_TITLE + " text not null, " + 
			COLUMN_SUB_HASPROGRESS + " integer not null check(" + COLUMN_SUB_HASPROGRESS + " in (0,1)), " +
		//	COLUMN_SUB_HASCOUNTER + " integer not null check(" + COLUMN_SUB_HASCOUNTER + " in (0,1)), " +
			COLUMN_SUB_PROGRESS + " integer not null, " +
		//	COLUMN_SUB_COUNTER + " integer not null, " + 
			COLUMN_SUB_IS_COMPLETE + " integer not null check(" +
			COLUMN_SUB_IS_COMPLETE + " in (0,1)), " + COLUMN_SUB_CONNECT_ID + " integer not null);";
			
	
	//Task Request Table 
	public static final String DATABASE_REQUEST_TABLE="requesttable";
	public static final String COLUMN_REQUEST_ROWID="_id";
	public static final String COLUMN_REQUEST_UID= "uidUser"; 
	public static final String COLUMN_REQUEST_STATUS="iscomplete";
	public static final String COLUMN_REQUEST_CONNECTID="connectID";
	public static final String COLUMN_REQUEST_FROMID = "fromid";
    
	
	
	private static final String DATABASE_REQUEST_CREATE = "create table "
			+ DATABASE_REQUEST_TABLE + " (" + COLUMN_REQUEST_ROWID
			+ " integer primary key autoincrement, " + COLUMN_REQUEST_UID + " text not null, " + 
			 COLUMN_REQUEST_FROMID + " text not null, " +
			COLUMN_REQUEST_STATUS + " integer not null check(" +
			COLUMN_REQUEST_STATUS + " in (0,1,2)), " + COLUMN_REQUEST_CONNECTID + " text not null);";
	
	
	
	/*//Third Table - User Login and other Major details 
	public static final String DATABASE_LOGIN_TABLE="logintable";
	public static final String COLUMN_LOGIN_ROWID="_id";
	public static final String COLUMN_USERNAME="username";
	public static final String COLUMN_PASSWORD="password";
	public static final String COLUMN_ISLOGGED="islogged";
	
	private static final String DATABASE_LOGIN_CREATE = "create table "  
			+ DATABASE_LOGIN_TABLE + " (" + COLUMN_LOGIN_ROWID
			+ " integer primary key autoincrement, " + COLUMN_USERNAME + " text not null, " +
			 COLUMN_PASSWORD + " text not null, " + 
			COLUMN_ISLOGGED + " integer not null check(" +
			COLUMN_ISLOGGED + " in (0,1));";*/
	
	
	
	public DatabaseHelper(Context context)
	{
		super(context,DATABASE_NAME,null,DATABASE_VERSION);//get the exact meaning of null here
		
	}
	
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
		db.execSQL(DATABASE_SUB_CREATE);
		db.execSQL(DATABASE_REQUEST_CREATE);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_SUB_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_REQUEST_TABLE);
		db.execSQL(DATABASE_CREATE);	
		db.execSQL(DATABASE_SUB_CREATE);
		db.execSQL(DATABASE_REQUEST_CREATE);
	}
	
	
	
	
	
	
	
	
}
