package com.hdway.model;

import com.google.gson.Gson;
import com.hdway.utils.Logger;
import com.hdway.utils.Utils;


public abstract class BaseEntity {
	public static final String FIELD_INT = "integer";
	public static final String FIELD_LONG = "long";
	public static final String FIELD_TEXT = "text";
	public static final String FIELD_MD5 = "text_md5";
	public static final String FIELD_DATETIME = "datetime";
	public static final String FIELD_FILEPATH = "filepath";
	
	public long id = -1;
		
	//Serialize and deserialize
	public String toJSON(){
		Gson gson = new Gson();
		try{
			return gson.toJson(this).toString();
		} catch (Exception e) {
			e.printStackTrace();
			Logger.LogE("Failed in toJSON()");
			return null;
		}
	};
	
	public static BaseEntity fromJSON(String object, Class<?> classType){
		Gson gson = new Gson();
		try {
			return (BaseEntity)gson.fromJson(object, classType);
		} catch (Exception e) {
			return null;
		}
	};
	

}
