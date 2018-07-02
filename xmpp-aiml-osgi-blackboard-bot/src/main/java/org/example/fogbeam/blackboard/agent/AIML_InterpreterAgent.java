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
import org.example.fogbeam.blackboard.callable.AIML_InterpreterSubsystemCallable;

public class AIML_InterpreterAgent extends SimpleBlackboardAgent
{

	final ExecutorService executorService = Executors.newFixedThreadPool(1);
	
	@Override
	protected void process(Conversation conversation, BlackboardFrame frame) 
	{	
		System.out.println( this.getClass().getName() + " : received input message: " + frame.getContents() );
		
		AIML_InterpreterSubsystemCallable task = new AIML_InterpreterSubsystemCallable( frame.getContents() );
		Future<String> taskFuture = executorService.submit(task);
		
		try 
		{
			String response = taskFuture.get( 75, TimeUnit.MILLISECONDS);
			if( response != null && !response.isEmpty() )
			{
				System.out.println( this.getClass().getName() + " thinks the answer is: " + response );

				SimpleBlackboardFrame newFrame = new SimpleBlackboardFrame( response );
				newFrame.setSourceTag( this.getClass().getName() );
				newFrame.setOutput(true);
				
				if( response.contains("no answer" ))
				{
					newFrame.setConfidence(60.0);
				}
				else
				{
					newFrame.setConfidence(95.0);
				}
				
				conversation.addMessage(newFrame);
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
