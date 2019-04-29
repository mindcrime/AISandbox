package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter5;

public class AlternateSuspendResume implements Runnable
{
	private volatile int firstVal;
	private volatile int secondVal;
	private volatile boolean suspended;
	
	public boolean areValuesEqual()
	{
		return ( firstVal == secondVal );
	}
	
	private void workMethod() throws InterruptedException
	{
		int val = 1;
		
		while( true )
		{
			// blocks only if suspended is true
			waitWhileSuspended();
			
			stepOne( val );
			stepTwo( val );
			val++;
			
			// blocks only if suspended is true
			waitWhileSuspended();
			
			Thread.sleep( 200 );
		}
	}
	
	private void stepOne( int newVal ) throws InterruptedException
	{
		firstVal = newVal;
		
		// simulate some other long running process
		Thread.sleep(  350 );
	}
	
	private void stepTwo( int newVal )
	{
		secondVal = newVal;
	}
	
	public void suspendRequest()
	{
		suspended = true;
	}
	
	public void resumeRequest()
	{
		suspended = false;
	}
	
	private void waitWhileSuspended() throws InterruptedException
	{
		// busy wait - don't do this in real life
		while( suspended )
		{
			Thread.sleep(  200 );
		}
	}
	
	public void run()
	{
		try
		{
			suspended = false;
			firstVal = 0;
			secondVal = 0;
			workMethod();
		}
		catch( InterruptedException x )
		{
			System.out.println( "interrupted while in workMethod" );
		}
	}
	
	
	public static void main( String[] args )
	{
		AlternateSuspendResume asr = new AlternateSuspendResume();
		Thread t = new Thread( asr );
		t.start();
		
		// let the other Thread get going and run for a while
		try
		{
			Thread.sleep(  1500 );
		}
		catch( Exception e )
		{}
		
		for( int i = 0; i < 10; i++ )
		{
			
			asr.suspendRequest();
			
			// give the Thread a chance to notice the suspend request
			try
			{
				Thread.sleep(  300 );
			}
			catch( Exception e )
			{}
			
			System.out.println( "asr.areValuesEqual() = " + asr.areValuesEqual() );
		
			asr.resumeRequest();
			
			// pause a random amount of time between 0 and 2 seconds
			try
			{
				Thread.sleep(  (long)(Math.random()*2000.0) );
			}
			catch( Exception e )
			{}
		
		}
		
		// abruptly terminate the application
		System.exit( 0 );
	}
}
