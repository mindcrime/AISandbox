package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter14;

public class FullWait
{
	private volatile int value;
	
	
	public FullWait( int value )
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

		// never time out if the specified timeout value is 0
		if( msTimeout == 0L )
		{
			while( value < minValue )
			{
				wait();
			}
			
			return true;
		}
		
		// otherwise, proceed as normal...
		
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

}