package com.hdway.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;

public class TextValidator implements TextWatcher {
	private EditText editText;
	private boolean isValid;
	private String errMsg;

	public TextValidator(final EditText editText, String errorMsg) {
		this.editText = editText;
		this.errMsg = errorMsg;
		this.isValid = true;
		this.editText.setOnKeyListener(new OnKeyListener() {                 
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if(arg1 == KeyEvent.KEYCODE_DEL && editText.getText().toString().isEmpty()){  
	                 //this is for backspace
					editText.setError(errMsg);
					isValid = false;
	            }else{
	            	editText.setError(null);
	            	isValid = true;	
	            }
				return false;
			}					
	    });
	}

	@Override
	final public void afterTextChanged(Editable s) {
		if(!s.toString().isEmpty())
		{
			editText.setError(null);
			isValid = true;
		}
	}

	@Override
	final public void beforeTextChanged(CharSequence s, int start, int count,
			int after) { /* Don't care */
	}

	@Override
	final public void onTextChanged(CharSequence s, int start, int before,
			int count) { /* Don't care */
		
	}

	final public boolean isValid() {
		return isValid;
	}
}