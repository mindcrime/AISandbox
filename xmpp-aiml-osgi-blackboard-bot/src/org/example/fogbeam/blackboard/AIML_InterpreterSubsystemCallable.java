package org.example.fogbeam.blackboard;

import java.util.concurrent.Callable;

public class AIML_InterpreterSubsystemCallable implements Callable<String> 
{
	private String message;
	
	public AIML_InterpreterSubsystemCallable( String message )
	{
		this.message = message;
	}
	
	@Override
	public String call() 
	{
		System.out.println( "AIML_InterpreterSubsystem handling input: " + message );
		
		// TODO: invoke AIML interpreter here
		
		String response = "";
		return response;
	}
}