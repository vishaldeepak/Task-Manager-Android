package com.group.RedPanda.FinalProject;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.group.RedPanda.JSON.DatabaseHandler;
import com.group.RedPanda.JSON.UserFunctions;

public class RegisterActivity extends Activity {
	Button btnRegister;
	EditText inputFullName;
	EditText inputEmail;
	EditText inputPassword;
	TextView registerErrorMsg;
	SessionManager session;
	Button skip;
	JSONObject json;
	UserFunctions userFunction = new UserFunctions();
	
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	private boolean success=false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		// Importing all assets like buttons, text fields
		inputFullName = (EditText) findViewById(R.id.registerName);
		inputEmail = (EditText) findViewById(R.id.registerEmail);
		inputPassword = (EditText) findViewById(R.id.registerPassword);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		skip = (Button) findViewById(R.id.btnSkip);
		
		
		registerErrorMsg = (TextView) findViewById(R.id.register_error);
		
		
		skip.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startMainScreen();
			}
		});
		
		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View view) {
				if(isNetworkAvailable())
				{
				Toast.makeText(getApplicationContext(), "Please Wait",Toast.LENGTH_SHORT).show();
				new asyncProgressAddDataBase().execute();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Enable Internet Connectivity",Toast.LENGTH_SHORT).show();
				}
				
			}
		});

	}
	

	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	public void startMainScreen()
	{
		Intent start = new Intent(this,MainScreen.class);
		this.startActivity(start);
	}
	
	private class asyncProgressAddDataBase extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... arg0) {
			
			// TODO Auto-generated method stub
			
			String name = inputFullName.getText().toString();
			String email = inputEmail.getText().toString();
			String password = inputPassword.getText().toString();
			
			
			json = userFunction.registerUser(name, email, password);
			

			// check for login response
			
			
			return null;
		}
	
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			
	runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						if (json.getString(KEY_SUCCESS) != null) {
							registerErrorMsg.setText("");
							String res = json.getString(KEY_SUCCESS); 
							if(Integer.parseInt(res) == 1){
								// user successfully registred
								// Store user details in SQLite Database
								DatabaseHandler db = new DatabaseHandler(getApplicationContext());
								JSONObject json_user = json.getJSONObject("user");
								
								// Clear all previous data in database
								userFunction.logoutUser(getApplicationContext());
								db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
								
								session = new SessionManager(getApplicationContext());
								session.createLoginSession(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json_user.getString(KEY_EMAIL));
								success=true;
								// Launch Dashboard Screen
								
							}else{
								// Error in registration
								registerErrorMsg.setText("Error occured in registration");
								success=false;
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					
					
					if(success==true)
					{
					Intent i= new Intent(RegisterActivity.this,MainScreen.class);
			           (RegisterActivity.this).startActivity(i);
			           Toast.makeText(getApplicationContext(), "You have Successfully Logged In", Toast.LENGTH_SHORT).show();
					
					
					// Close Registration Screen
					finish();
					}
				}
			});
			super.onPostExecute(result);
		}
	}
}
