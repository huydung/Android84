package com.hdway.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.hdway.utils.ImageUtils;
import com.hdway.utils.Logger;
import com.hdway.utils.Utils;

public class Query extends BaseEntity {
	public static final transient String TYPE_CREATE = "CREATE";
	public static final transient String TYPE_BATCH_CREATE = "BATCH_CREATE";
	public static final transient String TYPE_READ_ONE = "READ_ONE";
	public static final transient String TYPE_UPDATE = "UPDATE";
	public static final transient String TYPE_DELETE = "DELETE";
	public static final transient String TYPE_UPLOAD = "UPLOAD";
	public static final transient String TYPE_FREEFORM = "FREEFORM";
	public static final transient String TYPE_LIST = "LIST";
	public static final transient String TYPE_LOGIN = "LOGIN";

	public static final transient String STATE_QUEUE = "QUEUE";
	public static final transient String STATE_SENT = "SENT";
	public static final transient String STATE_SUCCEED = "SUCCEED";
	public static final transient String STATE_FAILED = "FAILED";
	public static final transient String STATE_NOTIFIED = "NOTIFIED";
	public transient long timestamp;

	public void copyFrom(Query query) {
		this.id = query.id;
		this.entityType = query.entityType;
		this.inputData = query.inputData;
		this.inputDataRevert = query.inputDataRevert;
		this.inputSchema = query.inputSchema;
		this.outputSchema = query.outputSchema;
		this.resultData = query.resultData;
		this.sqlStatement = query.sqlStatement;
		this.state = query.state;
		this.type = query.type;
		this.whereStatement = query.whereStatement;
	}
	
	// Query Request properties
	public String type = TYPE_FREEFORM;
	public String inputData;	
	public String inputDataRevert;
	public String state = STATE_QUEUE;

	public String sqlStatement = null;
	public String entityType;
	public String inputSchema;
	public String outputSchema;
	public String whereStatement = null;
	public String resultData = null;
	public String inputArrayData = null;
	public transient IOnQueryFinishListener listener;

	public static Query newUploadQuery(String filePath, String inputSchema, Class<?> entityType, IOnQueryFinishListener listener) {
		Query query = new Query();
		query.entityType = entityType.getName();
		query.type = TYPE_UPLOAD;
		query.inputSchema = inputSchema;
		query.listener = listener;
		query.inputData = "{file:\""+ImageUtils.getImagePath(filePath)+"\"}";
		return query;
	}
	
	public static Query newQuery(String type, String inputSchema,
			Class<?> entityType, IOnQueryFinishListener listener) {
		return new Query(type, inputSchema, entityType, listener);
	}
	
	public static Query newQuery(String type, String inputSchema, String whereStatement,
			Class<?> entityType, IOnQueryFinishListener listener) {
		Query query =  new Query(type, inputSchema, entityType, listener);
		query.whereStatement = whereStatement;
		return query;
	}

	public static Query newDeleteQuery(String data, String inputSchema,
			Class<?> entityType, IOnQueryFinishListener listener) {
		Query query = new Query(TYPE_DELETE, inputSchema, entityType, listener);
		query.inputData = query.inputDataRevert = data;
		return query;
	}
	
	private Query() {
		super();
	}

	public static Query newFreeformQuery(String sqlStatement,
			String outputSchema, Class<?> entityType,
			IOnQueryFinishListener listener) {
		return new Query(TYPE_FREEFORM, sqlStatement, outputSchema, entityType,
				listener);
	}

	private Query(String type, String inputSchema, Class<?> entityType,
			IOnQueryFinishListener listener) {
		super();
		this.type = type;
		this.inputSchema = inputSchema;
		this.entityType = entityType.getName();
		this.listener = listener;
	}

	private Query(String type, String sqlStatement, String outputSchema,
			Class<?> entityType, IOnQueryFinishListener listener) {
		super();
		this.type = type;
		this.outputSchema = outputSchema;
		this.sqlStatement = sqlStatement;
		this.entityType = entityType.getName();
		this.listener = listener;
	}

	public EntityList parseResultList() {
		EntityList entities = new EntityList();
		if (resultData != null) {			
			try {
				Class entityClass = Class.forName(entityType);
				Gson gson = new Gson();
				JSONArray resultDataArr = new JSONArray(resultData);
				for (int i = 0; i < resultDataArr.length(); i++) {
					JSONObject obj = resultDataArr.getJSONObject(i);
					BaseEntity entity = (BaseEntity) gson.fromJson(
							obj.toString(), entityClass);
					entities.add(entity);
				}
				return entities;
			} catch (Exception e) {
				Logger.LogE(
						"(" + e.getMessage()
								+ "): Failed to parse result data for: "
								+ resultData);
			}
		} else {
			Logger.LogE("parseResultList(): resultData = null");
		}
		return entities;
	}

	public BaseEntity parseResultObject() {
		if (resultData != null) {
			try {
				Class entityClass = Class.forName(entityType);
				Gson gson = new Gson();
					JSONObject obj = new JSONArray(resultData).getJSONObject(0);
					return (BaseEntity) gson.fromJson(obj.toString(), entityClass);					
			} catch (Exception e) {
				Logger.LogE(
						"(" + e.getMessage()
								+ "): Failed to parse result data for: "
								+ resultData.toString());
			}
		} else {
			Logger.LogE("parseResultList(): resultData = null");
		}
		return null;
	}
	
	public long parseResultSingleId() {
		if (resultData != null) {
			if (resultData.length() > 0) {
				JSONObject obj;
				try {
					obj = new JSONArray(resultData).getJSONObject(0);
					return obj.getLong("id");
				} catch (JSONException e) {
					e.printStackTrace();
					Logger.LogE("Exception parsing the result ID");
				}
			}
		}
		return -1;
	}

	public String toString() {
		return type + " " + entityType;
	}
}
