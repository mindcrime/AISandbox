package org.example.fogbeam.blackboard.callable;

import java.util.concurrent.Callable;

import org.alicebot.ab.Bot;

public class AIML_InterpreterSubsystemCallable implements Callable<String> 
{
	private String message;
	private org.alicebot.ab.Chat chatSession;

	public AIML_InterpreterSubsystemCallable( String message )
	{
		String botname="mybot";
		String path=".";
		Bot bot = new Bot(botname, path);		
		chatSession = new org.alicebot.ab.Chat(bot);

		this.message = message;
	}
	
	@Override
	public String call() 
	{
		System.out.println( "AIML_InterpreterSubsystem handling input: " + message );
		
		String response = "";
		
		try
		{
			if( !message.isEmpty())
			{				
				
				if( !message.startsWith("@"))
				{
					// if it's a general input string, not an @command
					response = chatSession.multisentenceRespond(message);
				}
				else
				{
					// NOP, we have a separate subsystem for processing @commands
				}

				System.out.println("RESPONSE: " + response);
			}
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		return response;
	}
}