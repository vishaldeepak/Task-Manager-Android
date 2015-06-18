package com.group.RedPanda.FinalProject;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class MyCheckBox extends CheckBox {

	public MyCheckBox(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyCheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPressed(boolean pressed) {
		// TODO Auto-generated method stub
		if (pressed && getParent() instanceof View && ((View) getParent()).isPressed()) 
		{
            return;
            
        }
		
		super.setPressed(pressed);
	}
	
	

}
