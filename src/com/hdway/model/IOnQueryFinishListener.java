package com.hdway.model;

public interface IOnQueryFinishListener {
	public void OnQuerySucceed(Query query);
	public void OnQueryFailed(Query query);
}
