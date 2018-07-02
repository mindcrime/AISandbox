package org.example.fogbeam.blackboard.agent;

import java.util.Observable;

import org.example.fogbeam.blackboard.Blackboard;
import org.example.fogbeam.blackboard.BlackboardFrame;
import org.example.fogbeam.blackboard.Conversation;

public abstract class SimpleBlackboardAgent implements BlackboardAgent 
{
	/* all the real work happens here */
	protected abstract void process( final Conversation conversation, final BlackboardFrame frame );
	
	@Override
	public void update(Observable observable, Object arg) 
	{
		if( observable instanceof Blackboard && arg instanceof Conversation )
		{	
			System.out.println( this.getClass().getName() + " - Registering as Observer of Conversation" );
			
			Conversation conversation = (Conversation)arg;
			conversation.addObserver(this);
			
		}
		else if( observable instanceof Conversation && arg instanceof BlackboardFrame )
		{
			System.out.println( this.getClass().getName() + " - sending Conversation/Frame to process() method" );

			Conversation conversation = (Conversation)observable;
			if( conversation.isPaused())
			{
				return;
			}
			
			BlackboardFrame frame = (BlackboardFrame) arg;
			if( !( frame.getSourceTag().equals(this.getClass().getName())) )
			{
				// don't process a frame that we generated
				process( conversation, frame );
			}
		}
		else
		{
			throw new IllegalArgumentException( "update() received illegal parameter combination: " 
												 + observable.getClass().getName() + " | " 
												 + arg.getClass().getName() );
		}
				
		
	}
	
	/**
	    NOTE: for now this is unused.  We still need to move to the model where each
	    Agent is running in its own thread, but for now we'll just let this run in the thread
	    of the Executive.  
	 */
	@Override
	public void run() 
	{
		while( true )
		{
			try 
			{
				Thread.sleep( 500 );
			}
			catch( Exception e )
			{
			}
		}
	}
}
