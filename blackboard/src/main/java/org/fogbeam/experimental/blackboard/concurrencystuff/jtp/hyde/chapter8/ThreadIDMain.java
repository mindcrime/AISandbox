package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter8;

public class ThreadIDMain implements Runnable
{
	private ThreadID var;
	
	private static void print( String msg )
	{
		String name = Thread.currentThread().getName();
		System.out.println( name + " : " + msg );
	}
	
	public ThreadIDMain( ThreadID var )
	{
		this.var = var;
	}
	
	@Override
	public void run()
	{
		try
		{
			print( "var.getThreadId() = " + var.getThreadId() );
			Thread.sleep(  2000  );
			print( "var.getThreadId() = " + var.getThreadId() );
		
		}
		catch( InterruptedException e )
		{}
	}
	
	public static void main( String[] args ) throws InterruptedException
	{
		ThreadID tid = new ThreadID();
		ThreadIDMain shared = new ThreadIDMain( tid );
		
		Thread threadA = new Thread( shared, "threadA" );
		threadA.start();
		
		Thread.sleep( 500 );
		
		Thread threadB = new Thread( shared, "threadB" );
		threadB.start();

		Thread.sleep( 500 );
		
		Thread threadC = new Thread( shared, "threadC" );
		threadC.start();
		
		{
			threadA.join(); threadB.join(); threadC.join();
		}
		
		System.out.println( "done" );
	}
}
