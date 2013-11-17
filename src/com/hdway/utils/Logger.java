package com.hdway.utils;

public class Logger {
	
	public static final String TAG_APP = "Lib84";
	public static boolean logEnabled = true;
	public static void LogE(String tag, String message) {
		if(!logEnabled) return;
		android.util.Log.e(tag, message);
	}

	public static void LogD(String tag, String message) {
		if(!logEnabled) return;
		android.util.Log.d(tag, message);
	}

	public static void LogE(String message) {
		if(!logEnabled) return;
		android.util.Log.e(TAG_APP, message);
	}

	public static void LogD(String message) {
		if(!logEnabled) return;
		android.util.Log.d(TAG_APP, message);
	}
}
