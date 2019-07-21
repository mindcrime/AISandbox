package org.example.fogbeam.xmpp;

import java.util.concurrent.BlockingQueue;

import org.example.fogbeam.blackboard.ExecutiveRunnable;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Blackboard_MessageListener implements ChatMessageListener 
{
	Logger logger = LoggerFactory.getLogger( Blackboard_MessageListener.class );
	
	private BlockingQueue<String> inputMessageQueue;
	private ExecutiveRunnable executive;
	private Thread executiveThread;
	
	public Blackboard_MessageListener( final BlockingQueue<String> inputMessageQueue )
	{
		this.inputMessageQueue = inputMessageQueue;
		this.executive = new ExecutiveRunnable( this.inputMessageQueue );
		this.executiveThread = new Thread( this.executive );
		this.executiveThread.start();
		
		logger.info( "Started executive thread..." );
	}
	
	
	@Override
	public void processMessage(Chat chat, Message message) 
	{
		
		logger.info( "Blackboard_MessageListener received message: " + message.getBody());
		
		// This is the highest level input / output system, which exists
		// just to bridge between XMPP and the executive that manages the
		// various processing sub-systems.
		
		// Once we receive a message here, it is passed off to all of the
		// available processing modules, any (or more) of which can generate
		// a response to the input.  The executive mitigates which response(s)
		// to utilize, cancels no-longer-needed requests, etc.
		
		String messageBody = message.getBody();
		if( messageBody == null || messageBody.isEmpty() )
		{
			return;
		}
		
		String response = "";

		// hand message to the blackboard system and wait for a response.
		this.executive.offerMessage( messageBody );

		
		// a little initial sleep while while the Blackboard system "thinks"
		// the problem we're trying to solve here is the inherently asynchronous nature of the
		// individual agents.  We don't want one low confidence (barely above the threshold) response
		// from a fast agent, to trump a much higher confidence response from an agent that takes
		// longer to generate an answer.
		try
		{
			Thread.sleep( 1850 );
		}
		catch(Exception e )
		{
			logger.error("Error waiting for Executive", e );
		}

		int waitPeriod = 200;
		int elapsedTime = 0;
		
		while( ( response = this.executive.getResponse() ) == null )
		{

			
			elapsedTime += waitPeriod;
			if( elapsedTime > 5000 )
			{
				// if the executive hasn't responded after 5 seconds, give up.
				// later we have to figure out how to have the system learn to
				// ask for more time.  Think of the way a human might go "uuuhhh..." 
				// to stall, or go "hang on a second", etc.
				break;
			}
		}
		
		if( response == null || response.isEmpty() )
		{
			response = "I have no response for that";
		}
		
		try
		{
			chat.sendMessage( response );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}		
	}

}
