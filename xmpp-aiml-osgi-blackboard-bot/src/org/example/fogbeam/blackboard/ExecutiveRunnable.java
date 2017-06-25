package org.example.fogbeam.blackboard;

import java.util.HashSet;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class ExecutiveRunnable implements Runnable 
{
	private volatile boolean keepRunning = true;
	
	private BlockingQueue<String> inputMessageQueue;
	private Blackboard blackboard = new Blackboard();	
	private Set<BlackboardAgent> blackboardAgents = new HashSet<BlackboardAgent>();
	
	
	public ExecutiveRunnable( final BlockingQueue<String> inputMessageQueue )
	{
		this.inputMessageQueue = inputMessageQueue;
	}
	
	public void offerMessage( final String message )
	{
		this.inputMessageQueue.offer(message);
	}
	
	public String getResponse()
	{
		String response = "";

		// TODO: how does the executive know when a response is ready? 
		
		
		return response;
	}
	
	@Override
	public void run() 
	{
		while( keepRunning )
		{
			try 
			{
				String input = inputMessageQueue.take();
				
				System.out.println( "Executive received message: " + input );
				
				// is there a currently valid conversation?  If so, add this input to that
				// conversation, otherwise, start a new one
				
				// NOTE: do we *really* need the "Conversation" concept?  Could we just have
				// the Blackboard keep track of the messages and let it effectively
				// *be* the "Conversation"?  What does this buy us? 
				
				Conversation conversation = new Conversation();
				conversation.addMessage( input );
				
				blackboard.offer( conversation );
				
				// we received an input message, deliver it to all of the registered
				// subsystems
				for( Observer blackboardAgent: blackboardAgents )
				{
					blackboardAgent.update(null, blackboard );
				}
				
			} 
			catch (InterruptedException e) 
			{
				
			}
		}
		
		System.out.println( "DispatcherRunnable stopped..." );
	}

	
	public void registerSubsystem( BlackboardAgent blackboardAgent )
	{
		this.blackboardAgents.add( blackboardAgent );
	}
	
	public void stop()
	{
		this.keepRunning = false;
	}

	
}
