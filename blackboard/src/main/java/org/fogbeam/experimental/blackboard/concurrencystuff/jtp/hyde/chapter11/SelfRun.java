package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter11;

public class SelfRun implements Runnable
{
	private Thread internalThread;
	private boolean noStopRequested;
	
	public SelfRun()
	{
		System.out.println( "in constructor -- initializing..." );
		
		noStopRequested = true;
		internalThread = new Thread( this );
		internalThread.start();
	}

	@Override
	public void run()
	{
		if( Thread.currentThread() != internalThread )
		{
			throw new RuntimeException( "only the internal thread is allowed to invoke run()" );
		}
		
		while( noStopRequested )
		{
			System.out.println( "in run() -- still going..." );
			
			try
			{
				Thread.sleep(  7000 );
			}
			catch( InterruptedException e )
			{
				// for any blocking methods that follow...
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void stopRequest()
	{
		noStopRequested = false;
		internalThread.interrupt();
	}
	
	public boolean isAlive()
	{
		return internalThread.isAlive();
	}
}
