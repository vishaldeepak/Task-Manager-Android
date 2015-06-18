package com.group.RedPanda.FinalProject;

import java.util.Calendar;



public class Constants{
	
	public static final String NAME_CLASS = "class_Name";
	public static final String CLASS_ADDMAINTASK="AddMainTask";
	public static final String CLASS_EDITMAINTASK="EditMainTask";
	
	public static final String YEAR="year";
	public static final String MONTH="month";
	public static final String DAY="day";
	public static final String HOUR ="hour";
	public static final String MIN = "min";
	public static final String TIME_FORMAT="kk:mm";
	public static final String DATE_FORMAT="dd-MM-yyyy";
	public static final String FROM_MAINSCREEN[] ={DatabaseHelper.COLUMN_TITLE,DatabaseHelper.COLUMN_DATE_TIME
		,DatabaseHelper.COLUMN_ONCREATE_DATE_TIME,"_id",DatabaseHelper.COLUMN_DESCRIPTION,DatabaseHelper.COLUMN_ISFIXED,DatabaseHelper.COLUMN_ISCOMPLETED};
	
	public static final String FROM_EDIT_MAINSCREEN[] ={DatabaseHelper.COLUMN_TITLE,DatabaseHelper.COLUMN_DATE_TIME
		,DatabaseHelper.COLUMN_ONCREATE_DATE_TIME,"_id",DatabaseHelper.COLUMN_DESCRIPTION,DatabaseHelper.COLUMN_ISFIXED
		,DatabaseHelper.COLUMN_ISREGULAR,DatabaseHelper.COLUMN_REGULAR_TIME};
	
	public static final String FROM_SUB_SCREEN_DESC[]={DatabaseHelper.COLUMN_DESCRIPTION,"_id"};
	
	
	public static final String FROM_ISONLINE[] = {DatabaseHelper.COLUMN_ISONLINE,DatabaseHelper.COLUMN_ONLINE_IDENTITY,"_id"};
	public static final String FROM_REQUEST[] ={DatabaseHelper.COLUMN_REQUEST_UID,"_id"};
	
	public static final int TO_MAINSCREEN[] = {R.id.title_mainscreen,R.id.subtitle_dateComplete_mainscreen,
		R.id.subtitle_dateCreate_mainscreen};
	public static final String FROM_SUBSCREEN[] = {DatabaseHelper.COLUMN_SUB_TITLE,DatabaseHelper.COLUMN_SUB_IS_COMPLETE,
		DatabaseHelper.COLUMN_SUB_ROWID,DatabaseHelper.COLUMN_SUB_CONNECT_ID,DatabaseHelper.COLUMN_SUB_PROGRESS,DatabaseHelper.COLUMN_SUB_HASPROGRESS};
	//public static final String FROM_EDITMAINSCREEN[]={DatabaseHelper.COLUMN_TITLE,DatabaseHelper.COLUMN_DATE_TIME,"_id"};
	
	
	public static final String All_MAINTASKP[] = {DatabaseHelper.COLUMN_ADMIN_UID,DatabaseHelper.COLUMN_COMPLETE_PERCENTAGE,DatabaseHelper.COLUMN_DATE_TIME,
		DatabaseHelper.COLUMN_DESCRIPTION,DatabaseHelper.COLUMN_IS_FIRST_OPEN,DatabaseHelper.COLUMN_ISCOMPLETED,DatabaseHelper.COLUMN_ISFIXED
		,DatabaseHelper.COLUMN_ISONLINE,DatabaseHelper.COLUMN_ISREGULAR,DatabaseHelper.COLUMN_ONCREATE_DATE_TIME,DatabaseHelper.COLUMN_ONLINE_IDENTITY,
		DatabaseHelper.COLUMN_REGULAR_TIME,DatabaseHelper.COLUMN_ROWID,DatabaseHelper.COLUMN_TITLE};
	
	public static boolean isDateWrong(Calendar myCal)
	{
		
		Calendar now = Calendar.getInstance();
		if(myCal.getTimeInMillis() < now.getTimeInMillis())
			return true;
		else
			return false;
		
	}
	
	
	
}
