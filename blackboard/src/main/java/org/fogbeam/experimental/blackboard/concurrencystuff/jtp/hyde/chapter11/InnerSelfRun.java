package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter11;

public class InnerSelfRun
{
	private Thread internalThread;
	private boolean noStopRequested;
	
	public InnerSelfRun()
	{
		System.out.println( "(ISR) in constructor -- initializing..." );
		
		noStopRequested = true;
		
		Runnable r = new Runnable() 
		{
			@Override
			public void run()
			{				
				runWork();
			}
			
		};
		
		internalThread = new Thread( r );
		internalThread.start();
	}
	
	private void runWork()
	{
		while( noStopRequested )
		{
			System.out.println( "(ISR) in run() -- still going..." );
			
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
