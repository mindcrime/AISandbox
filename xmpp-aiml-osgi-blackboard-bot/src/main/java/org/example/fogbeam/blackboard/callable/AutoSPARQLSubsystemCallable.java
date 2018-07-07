package org.example.fogbeam.blackboard.callable;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoSPARQLSubsystemCallable implements Callable<String>
{
	Logger logger = LoggerFactory.getLogger( AutoSPARQLSubsystemCallable.class );
	
	private String message;
	
	public AutoSPARQLSubsystemCallable( String message )
	{
		this.message = message;
	}
	
	@Override
	public String call() 
	{
		logger.info( "AutoSPARQLSubsystem handling input: " + message );
				
		String response = "Yeah, right!";
		
		// TODO: invoke AutoSPARQL subsystem here
		
		return response;
	}	
}
