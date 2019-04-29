package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter5;

public class SleepInterrupt implements Runnable
{
	@Override
	public void run()
	{
		try
		{
			System.out.println( "in run() - about to sleep for 20 seconds" );
			
			Thread.sleep(  20000 );
			
			System.out.println( "in run() - woke up" );
			
		}
		catch( InterruptedException x )
		{
			System.out.println( "in run() - interrupted while sleeping" );
			
			return;
		}
	}

	public static void main( String[] args )
	{
		SleepInterrupt si = new SleepInterrupt();
		Thread t = new Thread( si );
		t.start();
		
		// Be sure the new Thread gets a chance to run for a while
		try { Thread.sleep( 2000 ); }
		catch( InterruptedException x )
		{}
		
		System.out.println( "in main() - interrupting other thread" );
		
		t.interrupt();
		
		System.out.println( "in main() - leaving..." );
		
	}
}
