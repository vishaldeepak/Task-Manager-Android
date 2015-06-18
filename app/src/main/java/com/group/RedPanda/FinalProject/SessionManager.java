package com.group.RedPanda.FinalProject;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;
//This class manages the session using SharedPreferences
public class SessionManager {
	SharedPreferences pref;
	Editor editor;
	Context gotContext;
	int PRIVATE_MODE=0;//check this
	private static final String PREF_NAME = "LoginPref";
	//keys
	private static final String IS_LOGIN = "IsLoggedIn";
	public static final String KEY_NAME = "name";
	// Email address (make variable public to access from outside)
	public static final String KEY_EMAIL = "email";
	public static final String KEY_UID = "uid";
	
	
	
	public SessionManager(Context context){
		gotContext = context;
		pref = gotContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	public void createLoginSession(String name, String email,String uid){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		
		// Storing name in pref
		editor.putString(KEY_NAME, name);
		
		// Storing email in pref
		editor.putString(KEY_EMAIL, email);
		
		editor.putString(KEY_UID, uid);
		// commit changes
		editor.commit();
	}	
	
	
	/*public void loggedInUser()
	{
		editor.putBoolean(IS_LOGIN, true);
	}*/
	
	
	public boolean checkLogin(){
		// Check login status
		if(!this.isLoggedIn())
			// user is not logged in redirect him to Login Activity
			return false;
		else
			return true;
		
	}
	
	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}
	
	public void logout()
	{
		editor.putBoolean(IS_LOGIN, false);
		editor.commit();
		Toast.makeText(gotContext, "Logged Out", Toast.LENGTH_SHORT).show();
	}
	
	   public HashMap<String, String> getUserDetails(){
	        HashMap<String, String> user = new HashMap<String, String>();
	        // user name
	        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
	         
	        // user email id
	        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
	        
	        user.put(KEY_UID, pref.getString(KEY_UID, null));
	         
	        // return user
	        return user;
	    }
	   
	   public String getUserid()
	   {
		   return pref.getString(KEY_EMAIL, null);
	   }
	
	
	
	
	
}
