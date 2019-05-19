package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter7;

public class Volatile implements Runnable
{
	
	// not marked as 'volatile' but it should be!
	private int value;
	
	private volatile boolean missedIt;
	
	// doesn't need to be volatile since it doesn't change
	private long creationTime;
	
	public Volatile()
	{
		value = 10;
		missedIt = false;
		creationTime = System.currentTimeMillis();
	}
	
	@Override
	public void run()
	{
		print( "Entering run()" );
		
		// System.out.println( "initial value = " + value );
		
		// each time, check to see if "value" is different
		while( value < 20 )
		{
			// used to break out of the loop
			// if change to value is missed
			if( missedIt )
			{
				int currValue = value;
				
				// simply execute a synchronized statement on an arbitrary object
				// to see the effect
				Object lock = new Object();
				synchronized( lock )
				{
					// do nothing!
				}
				
				int valueAfterSync = value;
				
				print( "in run() - see value = " + currValue + " but rumor has it that it changed" );
				
				print( "in run() - valueAfterSync = " + valueAfterSync );
				
				break;
			}
			
		}
		
		// System.out.println( "final value = " + value );
		print( "leaving run()" );
		
	}
	
	private void print( String msg )
	{
		long interval = System.currentTimeMillis() - creationTime;
		
		String tempStr = "    " + (interval / 1000.0 ) + "000";
		int pos = tempStr.indexOf( "." );
		
		String secStr = tempStr.substring( (pos-2), (pos+4) );
		
		String nameStr = "          " + Thread.currentThread().getName();
		
		nameStr = nameStr.substring( nameStr.length() - 8, nameStr.length() );
		
		System.out.println( secStr + "  " + nameStr + ": " + msg );
	}
	
	public void workMethod() throws InterruptedException
	{
		print( "entering workMethod()" );
		
		print( "in workMethod() - about to sleep for 2 seconds" );
		
		Thread.sleep(  2000 );
		
		value = 50;
		
		print( "in workMethod() - just set value = " + value );
		
		print( "in workMethod() - about to sleep for 5 seconds" );
		
		Thread.sleep(  5000 );
		
		missedIt = true;
		
		print( "in workMethod() - just set missedIt = " + missedIt );
		
		print( "in workMethod() - about to sleep for 3 seconds" );
		
		Thread.sleep(  3000 );
		
		print( "leaving workMethod" );
	}
	
	public static void main( String[] args )
	{
		Volatile vol = new Volatile();
		try
		{
			// slight pause to let some time elapse
			Thread.sleep(  150 );
			
			Thread t = new Thread( vol );
			t.start();
			
			// slight pause to allow run() to go first
			Thread.sleep(  3700 );
			
			vol.workMethod();
			
		}
		catch( InterruptedException e )
		{
			System.err.println( "One of the sleeps was interrupted" );
		}
		
		
		System.out.println( "done" );
	}
}
