package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter5;

public class PendingInterrupt
{
	public static void main( String[] args )
	{
		if( args.length > 0 )
		{
			Thread.currentThread().interrupt();
		}
		
		long startTime = System.currentTimeMillis();
		
		try
		{
			Thread.sleep(  2000  );
			System.out.println( "was NOT interrupted" );
		}
		catch( InterruptedException x )
		{
			System.out.println( "was interrupted" );
		}
		
		System.out.println( "elapsed time = " + ( System.currentTimeMillis() - startTime ) );
		
	}
}
