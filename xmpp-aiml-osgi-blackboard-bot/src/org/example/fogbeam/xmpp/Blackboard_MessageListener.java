package org.example.fogbeam.xmpp;

import java.util.concurrent.BlockingQueue;

import org.example.fogbeam.blackboard.ExecutiveRunnable;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

public class Blackboard_MessageListener implements ChatMessageListener 
{

	private BlockingQueue<String> inputMessageQueue;
	private ExecutiveRunnable executive;
	private Thread executiveThread;
	
	public Blackboard_MessageListener( final BlockingQueue<String> inputMessageQueue )
	{
		this.inputMessageQueue = inputMessageQueue;
		this.executive = new ExecutiveRunnable( this.inputMessageQueue );
		this.executiveThread = new Thread( this.executive );
		this.executiveThread.start();
	}
	
	
	@Override
	public void processMessage(Chat chat, Message message) 
	{
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
		
		while( ( response = this.executive.getResponse() ) == null )
		{
			// sleep a little while while the Blackboard system "thinks"
			try
			{
				Thread.sleep( 1000 );
			}
			catch(Exception e )
			{}
		}
		
		if( response.isEmpty() )
		{
			response = "I have no response for that";
		}
		else
		{
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

}
