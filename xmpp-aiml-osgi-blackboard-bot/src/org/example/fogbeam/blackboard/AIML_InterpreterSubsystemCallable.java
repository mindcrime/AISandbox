package org.example.fogbeam.blackboard;

import java.util.concurrent.Callable;

public class AIML_InterpreterSubsystemCallable implements Callable<String> 
{

	private volatile boolean keepRunning = true;
	private String message;
	
	public AIML_InterpreterSubsystemCallable( String message )
	{
		this.message = message;
	}
	
	@Override
	public String call() 
	{
		System.out.println( "SubsystemTwo handling input: " + message );
		
		try 
		{
			Thread.sleep( 345 );
		} 
		catch (InterruptedException e)
		{
		}
		
		String response = "Gnarly, dude!";
		return response;
	}
}