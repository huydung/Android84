package com.hdway.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefUtils {
	public static String SP_NAME = "Lib84";

	public static String getStringPref(String key,
			String defaultValue) {
		SharedPreferences sf = Utils.getContext().getSharedPreferences(SP_NAME, 0);
		return sf.getString(key, defaultValue);
	}

	public static void setStringPref(String key,
			String value) {
		SharedPreferences sf = Utils.getContext().getSharedPreferences(SP_NAME, 0);
		Editor editor = sf.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static boolean getBooleanPref(String key,
			boolean defaultValue) {
		SharedPreferences sf = Utils.getContext().getSharedPreferences(SP_NAME, 0);
		return sf.getBoolean(key, defaultValue);
	}

	public static void setBooleanPref(String key,
			boolean value) {
		SharedPreferences sf = Utils.getContext().getSharedPreferences(SP_NAME, 0);
		Editor editor = sf.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static int getIntPref(String key,
			int defaultValue) {
		SharedPreferences sf = Utils.getContext().getSharedPreferences(SP_NAME, 0);
		return sf.getInt(key, defaultValue);
	}

	public static void setIntPref(String key, int value) {
		SharedPreferences sf = Utils.getContext().getSharedPreferences(SP_NAME, 0);
		Editor editor = sf.edit();
		editor.putInt(key, value);
		editor.commit();
	}
}
