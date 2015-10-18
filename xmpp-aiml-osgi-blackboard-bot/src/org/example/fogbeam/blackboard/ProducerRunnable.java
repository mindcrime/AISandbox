package org.example.fogbeam.blackboard;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class ProducerRunnable implements Runnable 
{
	private volatile boolean keepRunning = true;
	
	private BlockingQueue<String> workQueue;
	
	public ProducerRunnable( BlockingQueue<String> workQueue )
	{
		this.workQueue = workQueue;
	}
	
	@Override
	public void run() 
	{
		while( keepRunning )
		{
			try
			{
				Thread.sleep( 5000 );
			}
			catch( Exception e )
			{}
			
			String message = UUID.randomUUID().toString();
			System.out.println( "producing some work: " + message );
			
			try 
			{
				workQueue.put(message);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println( "ProducerRunnable stopped");
	}
	
	public void stop()
	{
		this.keepRunning = false;
	}
}
