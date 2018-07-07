package org.example.fogbeam.blackboard.callable;

import java.util.concurrent.Callable;

import org.alicebot.ab.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AIML_InterpreterSubsystemCallable implements Callable<String> 
{
	Logger logger = LoggerFactory.getLogger( AIML_InterpreterSubsystemCallable.class );
	
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
		logger.info( "AIML_InterpreterSubsystem handling input: " + message );
		
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

				logger.info("RESPONSE: " + response);
			}
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		return response;
	}
}