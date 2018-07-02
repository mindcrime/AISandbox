package org.example.fogbeam.blackboard.callable;

import java.util.concurrent.Callable;

public class AutoSPARQLSubsystemCallable implements Callable<String>
{
	
	private String message;
	
	public AutoSPARQLSubsystemCallable( String message )
	{
		this.message = message;
	}
	
	@Override
	public String call() 
	{
		System.out.println( "AutoSPARQLSubsystem handling input: " + message );
				
		String response = "Yeah, right!";
		
		// TODO: invoke AutoSPARQL subsystem here
		
		return response;
	}	
}
