package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter14;

public class FullWaitMain
{
	
	private FullWait fullWait;
	private Thread internalThread;
	private volatile boolean noStopRequested;
	
	public FullWaitMain( final FullWait fw )
	{
		this.fullWait = fw;
		noStopRequested = true;
		
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
		
		internalThread = new Thread( r );
		internalThread.start();
	}
	
	private void runWork()
	{
		int count = 6;
		
		while( noStopRequested )
		{
			fullWait.setValue( count );
			System.out.println( "Just set value to " + count );
			count++;
			
			try
			{
				Thread.sleep(  1000 );
			}
			catch( Exception e )
			{
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
	
	public static void waitFor( FullWait fw, int val, long limit )
		throws InterruptedException
	{
		System.out.println( "about to waitForAtLeast( val = " + val + ", limit = " + limit + ")" );
		
		long startTime = System.currentTimeMillis();
		boolean retVal = fw.waitUntilAtLeast( val, limit );
		long endTime = System.currentTimeMillis();
		
		
		System.out.println( "Waited for " + (endTime-startTime) + " milliseconds, retVal = " + retVal + "\n------------------------------------" );	
	}
	
	public static void main( String[] args )
	{
		try
		{
			FullWait fw = new FullWait( 5 );
			FullWaitMain main = new FullWaitMain( fw );
		
			Thread.sleep(  500 );
			
			// should return true before 10 seconds
			waitFor( fw, 10, 10000L );

			// should return true right away, already >= 6
			waitFor( fw, 6, 5000L );
			
			// should return true right away, already >= 6 (negative time ignored)
			waitFor( fw, 6, -10000L );
			
			// should return false - not there yet, and negative time
			waitFor( fw, 15, -1000L );
			
			// should return false after 5 seconds
			waitFor( fw, 999, 5000L );
			
			// should eventually return true
			waitFor( fw, 30, 0L );
			
			main.stopRequest();
			
		}
		catch( Exception e )
		{
			
		}
		
		
	}
}
