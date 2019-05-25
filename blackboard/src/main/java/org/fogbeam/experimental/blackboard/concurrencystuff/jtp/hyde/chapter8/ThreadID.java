package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter8;

public class ThreadID extends ThreadLocal<Integer>
{
	private int nextId;
	
	public ThreadID()
	{
		this.nextId = 10001;
	}
	
	private synchronized Integer getNewId()
	{
		Integer id = new Integer( nextId );
		nextId++;
		return id;
	}
	
	@Override
	protected Integer initialValue()
	{
		print( "in initialValue()");
		return getNewId();
	}
	
	public int getThreadId()
	{
		// call get() on ThreadLocal to get calling Thread's
		// unique ID
		Integer id = get();
		return id;
	}
	
	private static void print( String msg )
	{
		String name = Thread.currentThread().getName();
		System.out.println( name + " : " + msg );
	}
}
