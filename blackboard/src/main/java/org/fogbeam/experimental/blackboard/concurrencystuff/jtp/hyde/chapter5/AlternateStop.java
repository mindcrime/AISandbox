package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter5;

public class AlternateStop implements Runnable
{
	private volatile boolean stopRequested;
	private Thread runThread;
	
	
	public void run()
	{
		runThread = Thread.currentThread();
		stopRequested = false;
		
		int count = 0;
		
		while( !stopRequested )
		{
			System.out.println( "running... count = " + count );
			count++;
			
			try
			{
				Thread.sleep(  300 );
			}
			catch( Exception e )
			{
				Thread.currentThread().interrupt(); // reassert
			}
		
		}
		
	}
	
	public void stopRequest()
	{
		stopRequested = true;
		
		if( runThread != null )
		{
			runThread.interrupt();
		}
	}
	
	public static void main( String[] args )
	{
		AlternateStop as = new AlternateStop();
		Thread t = new Thread( as );
		t.start();
		
		try
		{
			Thread.sleep( (long)(Math.random() * 8300) );
		}
		catch( Exception e )
		{
		}
		
		as.stopRequest();
		
	}
}
