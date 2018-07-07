package org.example.fogbeam.blackboard.agent;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.example.fogbeam.blackboard.Conversation;
import org.example.fogbeam.blackboard.callable.AtCommandSubsystemCallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoSPARQLAgent implements Observer 
{
	Logger logger = LoggerFactory.getLogger( AutoSPARQLAgent.class );
	
	final ExecutorService executorService = Executors.newFixedThreadPool(1);

	@Override
	public void update(Observable o, Object input) 
	{
		Conversation conversation = (Conversation)input;
		
		logger.info( this.getClass().getName() + " : received input message: " + conversation.toString());
				
		AtCommandSubsystemCallable task = new AtCommandSubsystemCallable( conversation.toString() );
		Future<String> taskFuture = executorService.submit(task);
		
		while( true )
		{
			try 
			{
				String response = taskFuture.get( 75, TimeUnit.MILLISECONDS);
				if( response != null )
				{
					logger.info( this.getClass().getName() + " thinks the answer is: " + response );
					break;
				}
			} 
			catch (InterruptedException e) 
			{
			} 
			catch (ExecutionException e) 
			{
			} catch (TimeoutException e) 
			{
			}
			
		}
		
	}

}
