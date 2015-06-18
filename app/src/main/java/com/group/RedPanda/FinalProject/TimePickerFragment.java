package com.group.RedPanda.FinalProject;



import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TimePickerFragment extends DialogFragment{
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle args= getArguments();
		String classCheck = args.getString(Constants.NAME_CLASS);
		if(classCheck==Constants.CLASS_ADDMAINTASK)
		return new TimePickerDialog(getActivity(),(AddMainTask)getActivity(),args.getInt(Constants.HOUR),
				args.getInt(Constants.MIN),true);
		else
		return new TimePickerDialog(getActivity(),(EditMainTask)getActivity(),args.getInt(Constants.HOUR),
				args.getInt(Constants.MIN),true);
		
	}
	

}
