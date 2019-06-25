package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter13;

import org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter18.ObjectFIFO;

public class ThreadPoolWorker
{
	
	private static int nextWorkerId = 0;
	private ObjectFIFO idleWorkers;
	private int workerId;
	private ObjectFIFO handoffBox;
	
	private Thread internalThread;
	private volatile boolean noStopRequested = true;
	
	public ThreadPoolWorker()
	{
		
	}
	
	public ThreadPoolWorker( ObjectFIFO idleWorkers )
	{
		this.idleWorkers = idleWorkers;
		workerId = getNextWorkerId();
		handoffBox = new ObjectFIFO(1); // only one slot
		
		Runnable r = new Runnable() 
		{
			@Override
			public void run()
			{
				try
				{
					runWork();
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
				
			}
		};
		
		internalThread = new Thread( r, "ThreadPoolWorker internalThread" );
		internalThread.start();
	}
	
	public static synchronized int getNextWorkerId()
	{
		int id = nextWorkerId;
		nextWorkerId++;
		return id;
	}
	
	public void process( Runnable target ) throws InterruptedException
	{
		handoffBox.add( target );
	}
	
	private void runWork()
	{
		while( noStopRequested )
		{
			try 
			{
				System.out.println( "workerId = " + workerId + ", ready for work!" );
				idleWorkers.add( this );
				
				// wait here until the server adds a request
				Runnable r = (Runnable)handoffBox.remove();
				
				System.out.println( "workerId = " + workerId + ", starting execution of new Runnable " + r );
				
				runIt( r );
			
			}
			catch( InterruptedException e )
			{
				Thread.currentThread().interrupt(); // re-assert
			}
		}
	}
	

	private void runIt( Runnable r )
	{
		try
		{
			r.run();
		}
		catch( Exception e )
		{
			System.err.println( "Uncaught exception fell through from run()" );
			e.printStackTrace();
		}
		finally
		{
			// clear the interrupted flag (in case it comes in set) so that 
			// if the loop goes again handoffBox.remove() does not throw an 
			// InterruptedException
			Thread.interrupted();
		}
	}
	
	public void stopRequest() throws InterruptedException
	{
		System.out.println( "workerId = " + workerId + ", stopRequest() received!" );
		noStopRequested = false;
		internalThread.interrupt();
	}
	
	public boolean isAlive()
	{
		return internalThread.isAlive();
	}
	

}
