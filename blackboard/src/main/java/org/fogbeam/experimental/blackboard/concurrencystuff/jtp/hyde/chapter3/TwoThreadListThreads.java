package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter3;

public class TwoThreadListThreads extends Thread
{

	@Override
	public void run() 
	{
		for( int i = 0; i < 10; i++ )
		{
			System.out.println( "New Thread!" );
			try
			{
				Thread.sleep( 750 );
			}
			catch( Exception e)
			{}
		}
	}
	
	public static void main(String[] args) 
	{
		TwoThreadListThreads thread = new TwoThreadListThreads();
		thread.start();
		
		
		
		Thread[] threads = new Thread[Thread.activeCount()*2];
		Thread.enumerate(threads);
		
		for( Thread t : threads )
		{
			if( t != null )
			{
				System.out.println( "Thread found: " + t.getId() + " - " + t.getName() );
			}
			else
			{
				System.out.println( "No Thread!" );
			}
		}
	}
}
