package org.example.fogbeam.blackboard;

import java.util.Observable;

public class SimpleBlackboardAgent implements BlackboardAgent 
{
	
	@Override
	public void update(Observable o, Object arg) {
		
		// set the Conversation and kick our thread
		
		
	}
	
	@Override
	public void run() 
	{
		while( true )
		{
			
			// check the Conversation and see if we have anything to contribute
			// if so, put our contribution in so the other agents can
			// access it.
			
			
			// otherwise, just sleep for a bit until something happens
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
