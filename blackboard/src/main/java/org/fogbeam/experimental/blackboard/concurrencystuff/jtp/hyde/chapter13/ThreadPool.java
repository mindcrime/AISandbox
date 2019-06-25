package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter13;

import org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter18.ObjectFIFO;

public class ThreadPool
{
	private ObjectFIFO idleWorkers;
	private ThreadPoolWorker[] workerList;
	
	public ThreadPool( int numberOfThreads )
	{
		// make sure that it's at last one
		numberOfThreads = Math.max(  1,  numberOfThreads );
		
		idleWorkers = new ObjectFIFO(numberOfThreads);
		workerList = new ThreadPoolWorker[numberOfThreads];
		
		for( int i = 0; i < workerList.length; i++ )
		{
			workerList[i] = new ThreadPoolWorker( idleWorkers );
		}
		
	}
	
	public void execute( Runnable target ) throws InterruptedException
	{
		// block (up to forever) until a Worker is available
		ThreadPoolWorker worker = (ThreadPoolWorker)idleWorkers.remove();
		worker.process( target );
	}
	
	public void stopRequestIdleWorkers()
	{
		try
		{
			Object[] idle = idleWorkers.removeAll();
			for( int i = 0; i < idle.length; i++ )
			{
				( (ThreadPoolWorker)idle[i] ).stopRequest();
			}
		}
		catch( InterruptedException e )
		{
			// reassert
			Thread.currentThread().interrupt();
		}
	}
	
	public void stopRequestAllWorkers()
	{
		// stop the idle ones first
		stopRequestIdleWorkers();
		
		// give the idle workers time to die
		try { Thread.sleep(  500 ); } catch( InterruptedException e ) {}
		
		// step through the list of all workers
		for( int i = 0; i < workerList.length; i++ )
		{
			if( workerList[i].isAlive() )
			{
				try
				{
					workerList[i].stopRequest();
				}
				catch( InterruptedException e )
				{
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
