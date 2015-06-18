
package com.group.RedPanda.JSON;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.group.RedPanda.FinalProject.DatabaseHelper;
import com.group.RedPanda.FinalProject.SessionManager;

public class UserFunctions {
	
	private JSONParser jsonParser;
	private SQLiteDatabase database;
	private DatabaseHelper databaseHelper;
	
	private static String loginURL = "http://www.manage.freeserver.me/manage/index.php";
	private static String registerURL = "http://www.manage.freeserver.me/manage/index.php";
	private static String requestUrl = "http://www.manage.freeserver.me/manage/index.php";
	private static String hasRequestUrl ="http://www.manage.freeserver.me/manage/taskhandle.php";
	private static String uploadUrl = "http://www.manage.freeserver.me/manage/uploadTask.php";
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String request_tag = "request";
	private static String getRequest_tag = "getRequest";
	private static String changeRequestPos_tag = "changeRequestPos";
	private static String changeRequestNeg_tag = "changeRequestNeg";
	private static String uploadOnline_tag = "uploadOnline";
	
	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * @return 
	 * */
	/*public JSONObject loginUser(String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

		// return json
		// Log.e("JSON", json.toString());
		return json;
	}*/
	
	
	public JSONObject uploadTask(Cursor data)
	{
		JSONObject obj;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", uploadOnline_tag));
		
		if(data.moveToFirst())
		{
			
			params.add(new BasicNameValuePair(DatabaseHelper.COLUMN_ADMIN_UID, data.getString(0)));
			params.add(new BasicNameValuePair(DatabaseHelper.COLUMN_COMPLETE_PERCENTAGE, data.getString(1)));
			params.add(new BasicNameValuePair(DatabaseHelper.COLUMN_DATE_TIME, data.getString(2)));
			params.add(new BasicNameValuePair(DatabaseHelper.COLUMN_DESCRIPTION, data.getString(3)));
			params.add(new BasicNameValuePair(DatabaseHelper.COLUMN_IS_FIRST_OPEN, data.getString(4)));
			params.add(new BasicNameValuePair(DatabaseHelper.COLUMN_ISCOMPLETED, data.getString(5)));
			params.add(new BasicNameValuePair(DatabaseHelper.COLUMN_ISFIXED, data.getString(6)));
			params.add(new BasicNameValuePair(DatabaseHelper.COLUMN_ISONLINE, data.getString(7)));
			params.add(new BasicNameValuePair(DatabaseHelper.COLUMN_ISREGULAR, data.getString(8)));
			params.add(new BasicNameValuePair(DatabaseHelper.COLUMN_ONCREATE_DATE_TIME, data.getString(9)));
			params.add(new BasicNameValuePair(DatabaseHelper.COLUMN_ONLINE_IDENTITY, data.getString(10)));
			params.add(new BasicNameValuePair(DatabaseHelper.COLUMN_REGULAR_TIME, data.getString(11)));
			params.add(new BasicNameValuePair(DatabaseHelper.COLUMN_TITLE, data.getString(13)));
			
		}
		obj = jsonParser.getJSONFromUrl(uploadUrl, params);
		return obj;
	}
	
	public JSONObject hasRequest(String uidUser)
	{
		JSONObject obj;
		//String response;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getRequest_tag));
		params.add(new BasicNameValuePair("uidUser", uidUser));
		
		obj = jsonParser.getJSONFromUrl(hasRequestUrl, params);
		return obj;
		
	}
	
	
	public void changeRequest(String onlineId,int id,Context context)
	{
		JSONObject json;
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if(id==1)
		{
		params.add(new BasicNameValuePair("tag", changeRequestPos_tag));
		}
		else
		{
		params.add(new BasicNameValuePair("tag", changeRequestNeg_tag));
		}
			
		params.add(new BasicNameValuePair("onlineid", onlineId));
	
		
		if(id==1)
		{
			json = jsonParser.getJSONFromUrl(hasRequestUrl, params);
			try
			{
			  JSONObject obj =	json.getJSONObject("Task");
			  addTaskDB(obj,context);
			
			}catch(JSONException e) {
				e.printStackTrace();
			}
		}
		else
			jsonParser.getJSONFromUrl(hasRequestUrl, params);
		
		
		
	}
	
	
	
	
	public void requestUser(Cursor data,Context context,String onlineId)
	{
		JSONObject obj;
		String response;
		SessionManager session;
		
		session = new SessionManager(context);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", request_tag));
		params.add(new BasicNameValuePair("fromid", session.getUserid()));
		params.add(new BasicNameValuePair("onlineId", onlineId));
		
		if(data.moveToFirst())
		{
			do
			{
			 params.add(new BasicNameValuePair("uidUser[]",data.getString(0)));
			 
			}while(data.moveToNext());
		}
		
		
		obj = jsonParser.getJSONFromUrl(requestUrl, params);  
		
	}
	
	
	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String name, String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		// return json
		return json;
	}
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
	
	public void addTaskDB(JSONObject obj,Context context)
	{
		databaseHelper = new DatabaseHelper(context);
		database= databaseHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		try
		{
		  values.put(DatabaseHelper.COLUMN_TITLE, obj.getString(DatabaseHelper.COLUMN_TITLE));
		  values.put(DatabaseHelper.COLUMN_DESCRIPTION, obj.getString(DatabaseHelper.COLUMN_DESCRIPTION));
		  values.put(DatabaseHelper.COLUMN_DATE_TIME, obj.getLong(DatabaseHelper.COLUMN_DATE_TIME));
		  values.put(DatabaseHelper.COLUMN_ONCREATE_DATE_TIME, obj.getLong(DatabaseHelper.COLUMN_ONCREATE_DATE_TIME));
		  values.put(DatabaseHelper.COLUMN_ISCOMPLETED, obj.getInt(DatabaseHelper.COLUMN_ISCOMPLETED));
		  values.put(DatabaseHelper.COLUMN_ISFIXED, obj.getInt(DatabaseHelper.COLUMN_ISFIXED));
		  values.put(DatabaseHelper.COLUMN_ISREGULAR, obj.getInt(DatabaseHelper.COLUMN_ISREGULAR));
		  values.put(DatabaseHelper.COLUMN_COMPLETE_PERCENTAGE, obj.getInt(DatabaseHelper.COLUMN_COMPLETE_PERCENTAGE));
		  values.put(DatabaseHelper.COLUMN_IS_FIRST_OPEN, 0);
		  values.put(DatabaseHelper.COLUMN_REGULAR_TIME, DatabaseHelper.COLUMN_REGULAR_TIME);
		  values.put(DatabaseHelper.COLUMN_ADMIN_UID, obj.getString(DatabaseHelper.COLUMN_ADMIN_UID));
		  values.put(DatabaseHelper.COLUMN_ISONLINE, obj.getString(DatabaseHelper.COLUMN_ISONLINE));
		  values.put(DatabaseHelper.COLUMN_ONLINE_IDENTITY,obj.getString(DatabaseHelper.COLUMN_ONLINE_IDENTITY));
		  database.insert(DatabaseHelper.DATABASE_TABLE, null, values);
		}catch(JSONException e)
		{
			e.printStackTrace();
		}
	}
	
}
