package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter11;

public class SelfRunMain
{
	public static void main( String[] args )
	{
		SelfRun sr = new SelfRun();
		
		try
		{
			Thread.sleep( 15000 );
		}
		catch( InterruptedException e )
		{}
		
		sr.stopRequest();
		
		System.out.println( "done" );
	}
}
