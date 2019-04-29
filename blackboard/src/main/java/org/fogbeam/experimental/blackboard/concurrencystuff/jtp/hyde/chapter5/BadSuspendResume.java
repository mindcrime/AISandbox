package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter5;

public class BadSuspendResume implements Runnable
{
	@Override
	public void run()
	{
		long iteration = 0;
		while( true )
		{
			iteration++;
			System.out.println( "Doing iteration: " + iteration );
			
			try
			{
				Thread.sleep( 723 );
			}
			catch( InterruptedException e )
			{}
		}
	}

	public static void main( String[] args ) throws Exception
	{
		BadSuspendResume bsr = new BadSuspendResume();
		Thread t = new Thread( bsr );
		t.start();
		
		Thread.sleep(  4000 );
		
		t.suspend();
		
		Thread.sleep(  8000 );
		
		t.resume();
		
		Thread.sleep( 4000 );
		
		t.stop();
		
		System.out.println( "done" );
		
	}
}
