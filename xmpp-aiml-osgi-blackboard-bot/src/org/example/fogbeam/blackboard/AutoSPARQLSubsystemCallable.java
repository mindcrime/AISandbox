package org.example.fogbeam.blackboard;

import java.util.concurrent.Callable;

public class AutoSPARQLSubsystemCallable implements Callable<String>
{

	private volatile boolean keepRunning = true;
	
	private String message;
	
	public AutoSPARQLSubsystemCallable( String message )
	{
		this.message = message;
	}
	
	@Override
	public String call() 
	{
		System.out.println( "SubsystemOne handling input: " + message );
		
		try 
		{
			Thread.sleep( 750 );
		} 
		catch (InterruptedException e)
		{
		}
		
		String response = "Yeah, right!";
		
		return response;
	}	
}
