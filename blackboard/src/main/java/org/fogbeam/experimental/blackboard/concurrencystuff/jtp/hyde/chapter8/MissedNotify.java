package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter8;

public class MissedNotify
{
	private Object proceedLock;
	
	public MissedNotify()
	{
		print( "in MissedNotify()");
		proceedLock = new Object();
	}
	
	public void waitToProceed() throws InterruptedException
	{
		print( "in waitToProceed() - entering" );
		
		synchronized( proceedLock )
		{
			print( "in waitToProceed() - about to wait()" );
			proceedLock.wait();
			print( "in waitToProceed() - back from wait()" );
		}
		
		print( "in waitToProceed() - leaving" );
	}
	
	public void proceed()
	{
		print( "in proceed() - entered" );
		
		synchronized( proceedLock )
		{
			print( "in proceed() - about to notifyAll()" );
			proceedLock.notifyAll();
			print( "in proceed() - back from notifyAll()" );
		}
		
		print( "in proceed() - leaving" );
	}
	
	public static void print( String msg )
	{
		String threadName = Thread.currentThread().getName();
		System.out.println( threadName + ": " + msg );
	}
	
	public static void main( String[] args ) throws InterruptedException
	{
		try
		{
			final MissedNotify mn = new MissedNotify();
			
			Runnable r1 = new Runnable() {
				public void run()
				{	
					try
					{
						Thread.sleep( 1000 );
						mn.waitToProceed();
					}
					catch( InterruptedException x )
					{
						x.printStackTrace();
					}
				}
			};
			Thread t1 = new Thread( r1, "Thread1" );
			t1.start();
			
	
			Runnable r2 = new Runnable() {
				public void run()
				{	
					try
					{
						Thread.sleep(  500 );
						mn.proceed();
					}
					catch( InterruptedException x )
					{}
				}
			};
			Thread t2 = new Thread( r2, "Thread2" );
			t2.start();
	
			try
			{
				Thread.sleep(  4000 );
			}
			catch( Exception e )
			{}
			
			print( "about to invoke interrupted on Thread1" );
			t1.interrupt();
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			Thread.sleep(  750  );
			System.out.println( "done" );
		}
	}
}
