package com.hdway.utils;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;


public class Utils {
	
	
	private static Context context;
	
	public static void setContext(Context context_) {
		context = context_;
	}
	
	public static Context getContext() {
		return context;
	}

	private static String EXTERNAL_DIR = null;
	
	public static String getExternalDir() {
		if( EXTERNAL_DIR == null ){
			EXTERNAL_DIR = getContext().getExternalFilesDir(null).getAbsolutePath()
					+ File.separatorChar;
		}
		return EXTERNAL_DIR;
	}
	
	public static void copyFile(String from, String to) {
		File file = new File(from);
		file.renameTo(new File(to));
	}
	
	public static void showInfoDialog(Activity activity, String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		builder.setTitle(title);
		builder.setMessage(message);
		builder.setCancelable(true);
		builder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		builder.create().show();
	}

}
