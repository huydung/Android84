package com.hdway.model;


public abstract class IDataManager extends Thread{
	private int thread_Key = 0;
	
	public abstract void addQuery(Query query, boolean forceRun);
	public abstract void addQuery(Query query);
	public IDataManager(){
		start();
	};
	
	public abstract Query getQueryById(long id);
	
	public abstract void close();
	
	public abstract void update();

	@Override
	public void run() {
		super.run();
		while(thread_Key==0) {
			update();
		}
		
	}
	
	
}
