package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter14;

public class EarlyReturnFix
{
	private volatile int value;
	
	
	public EarlyReturnFix( int value )
	{
		this.value = value;
	}
	
	public synchronized void setValue( int newValue )
	{
		if( value != newValue )
		{
			value = newValue;
			notifyAll();
		}
	}
	
	public synchronized boolean waitUntilAtLeast( int minValue, long msTimeout ) throws InterruptedException
	{
		System.out.println( "entering waitUntilAtLeast() - value = " 
							+ value + ", minValue = " + minValue + ", msTimeout = " + msTimeout );
		
		long endTime = System.currentTimeMillis() + msTimeout;
		long msRemaining = msTimeout;
		
		
		while( ( value < minValue ) && ( msRemaining > 0L ) )
		{
			System.out.println( "in waitUntilAtLeast() - about to: wait( " + msRemaining + ")" ); 
			
			wait( msRemaining );
			
			msRemaining = endTime - System.currentTimeMillis();
			
			System.out.println( "in waitUntilAtLeast() - back from wait(), new msRemaining = " + msRemaining );
		}
		
		System.out.println( "leaving waitUntilAtLeast() - value = " 
				+ value + ", minValue = " + minValue );
		
		return( value >= minValue );
	}
	
	public static void main( String[] args )
	{
		
		try
		{
			final EarlyReturnFix erf = new EarlyReturnFix( 0 );
			
			Runnable r = new Runnable() 
			{
				@Override
				public void run()
				{
					try
					{
						Thread.sleep(  1200 );
						erf.setValue( 2 );
						
						Thread.sleep(  500 );
						erf.setValue( 3 );
						
						Thread.sleep(  500 );
						erf.setValue( 4 );
						
						Thread.sleep(  700 );
						erf.setValue( 6 );
					}
					catch( Exception e )
					{
						e.printStackTrace();
					}
				}
			};
			
			Thread t = new Thread( r );
			
			t.start();
			
			System.out.println( "about to: waitUntilAtLeast( 5, 3000 )" );
			
			long startTime = System.currentTimeMillis();
			boolean retVal = erf.waitUntilAtLeast( 5, 3000 );
			long elapsedTime = System.currentTimeMillis() - startTime;
			
			System.out.println( "after " + elapsedTime + "ms, retVal = " + retVal );
		}
		catch( InterruptedException e )
		{
			e.printStackTrace();
		}
		
		System.out.println( "done" );
	}
}


