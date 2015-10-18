package org.example.fogbeam.blackboard;

import java.util.concurrent.Callable;

public class AtCommandSubsystemCallable implements Callable<String>
{
	
	private String message;
	
	public AtCommandSubsystemCallable( String message )
	{
		this.message = message;
	}
	
	@Override
	public String call() 
	{
		System.out.println( "AtCommandSubsystem handling input: " + message );
		
		String response = "";

		// TODO: invoke @command subsystem here
		
		return response;
	}	
}
