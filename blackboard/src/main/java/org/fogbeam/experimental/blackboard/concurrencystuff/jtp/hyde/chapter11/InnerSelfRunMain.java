package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter11;

public class InnerSelfRunMain
{
	public static void main( String[] args )
	{
		InnerSelfRun isr = new InnerSelfRun();
		
		try
		{
			Thread.sleep( 15000 );
		}
		catch( InterruptedException e )
		{}
		
		isr.stopRequest();
		
		System.out.println( "done" );		
	}
}
