package com.hdway.utils;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.res.Configuration;

public class LocaleUtils {
	public static String ENGLISH = "en";
	public static String VIETNAMESE = "vi";
	
	private static ArrayList<ILocalizable> uis = new ArrayList<ILocalizable>();
	private static String currentLocale = ENGLISH;
	
	public static String getCurrentLocale() {
		return currentLocale;
	}
	
	protected static void switchLocale(String lang) {
		java.util.Locale locale = new java.util.Locale(lang);
		java.util.Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		Utils.getContext().getResources().updateConfiguration(config, Utils.getContext().getResources().getDisplayMetrics());
	}

	public static void registerLocalizable(ILocalizable localizable) {
		uis.add(localizable);
		localizable.updateTexts();
	}
	
	public static boolean removeLocalizable(ILocalizable localizable) {
		return uis.remove(localizable);
	}
	
	public static void setLocale(String locale) {
		currentLocale = locale; 
		switchLocale(locale);
		for( ILocalizable ui : uis ) {
			ui.updateTexts();
		}
	}
}
