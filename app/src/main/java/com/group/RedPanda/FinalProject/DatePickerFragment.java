package com.group.RedPanda.FinalProject;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment{
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		Bundle args=getArguments();
		
		String classCheck = args.getString(Constants.NAME_CLASS);
		if(classCheck==Constants.CLASS_ADDMAINTASK)
		return new DatePickerDialog(getActivity(),(AddMainTask)getActivity(),args.getInt(Constants.YEAR),
				args.getInt(Constants.MONTH),
				args.getInt(Constants.DAY));
		else
			//if(classCheck==Constants.CLASS_EDITMAINTASK)
			return new DatePickerDialog(getActivity(),(EditMainTask)getActivity(),args.getInt(Constants.YEAR),
					args.getInt(Constants.MONTH),
					args.getInt(Constants.DAY));
				
		
	}
	

}
