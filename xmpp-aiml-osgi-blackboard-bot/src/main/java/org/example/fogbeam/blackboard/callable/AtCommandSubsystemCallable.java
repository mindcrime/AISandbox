package org.example.fogbeam.blackboard.callable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

/* There's really nothing AI about this in its present incarnation.  The question is, can
 * we come up with a way for the bot to learn "@commands" dynamically?  Consider that an
 * open research project. In the meantime, hard-coded @commands give us a set of primitives
 * to use to control the bot, as well as a handy way to make it do actually useful stuff
 * until we figure out how to make it learn how.  
 */
public class AtCommandSubsystemCallable implements Callable<String>
{
	SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss zzz" );
	
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
		
		if( !message.isEmpty() && message.startsWith("@"))
		{
			switch( message )
			{
				case "@time":
					Date now = new Date();
					response = sdf.format( now );
					break;
				default:
					response = "Unknown command";
					break;
			}
		}
		
		System.out.println("RESPONSE: " + response);
				
		return response;	}	
}
