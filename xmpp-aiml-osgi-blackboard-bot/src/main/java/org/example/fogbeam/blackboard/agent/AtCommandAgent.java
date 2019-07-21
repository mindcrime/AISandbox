package org.example.fogbeam.blackboard.agent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.example.fogbeam.blackboard.BlackboardFrame;
import org.example.fogbeam.blackboard.Conversation;
import org.example.fogbeam.blackboard.SimpleBlackboardFrame;
import org.example.fogbeam.blackboard.callable.AtCommandSubsystemCallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AtCommandAgent extends SimpleBlackboardAgent
{

	Logger logger = LoggerFactory.getLogger( AtCommandAgent.class );
	
	final ExecutorService executorService = Executors.newFixedThreadPool(1);

	@Override
	protected void process( final Conversation conversation, final BlackboardFrame frame) 
	{	
		logger.info( this.getClass().getName() + " : received input message: " + frame.getContent() );
				
		AtCommandSubsystemCallable task = new AtCommandSubsystemCallable( frame.getContent() );
		Future<String> taskFuture = executorService.submit(task);
		
		try 
		{
			String response = taskFuture.get( 75, TimeUnit.MILLISECONDS);
			if( response != null && !response.isEmpty() )
			{
				logger.info( this.getClass().getName() + " thinks the answer is: " + response );
				
				SimpleBlackboardFrame newFrame = new SimpleBlackboardFrame( response );
				newFrame.setSourceTag( this.getClass().getName() );
				newFrame.setOutput(true);
				newFrame.setConfidence(100.0);
				conversation.addMessage(newFrame);
			}
		} 
		catch (InterruptedException e) 
		{
		} 
		catch (ExecutionException e) 
		{
		} 
		catch (TimeoutException e) 
		{
		}			
	}

}
