package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter7;

public class Deadlock
{
	private String objId;
	
	public Deadlock( String objId )
	{
		this.objId = objId;
	}
	
	public synchronized void checkOther( Deadlock other )
	{
		print( "entering checkOther" );
		
		// simulate some lengthy process
		try { Thread.sleep( 2000 ); } catch( InterruptedException e ) {}
	
		print( "in checkOther - about to invoke 'other.action()'" );
		
		other.action();
		
		print( "leaving checkOther()" );
		
	}
	
	public synchronized void action()
	{
		print( "entering action()");
		
		// simulate some work here
		try { Thread.sleep( 500 ); } catch( InterruptedException e ) {}
		
		print( "leaving action()" );
	}
	
	public void print( String msg )
	{
		threadPrint( msg );
	}
	
	public void threadPrint( String msg )
	{
		String threadName = Thread.currentThread().getName();
		System.out.println( threadName + ": " + msg );
	}
	
	public static void main( String[] args )
	{
		final Deadlock obj1 = new Deadlock( "obj1" );
		final Deadlock obj2 = new Deadlock( "obj2" );
		
		Runnable runA = new Runnable() 
		{
			public void run() 
			{
				obj1.checkOther( obj2 );
			};
		};
		
		Thread threadA = new Thread( runA, "ThreadA" );
		threadA.start();
		
		try { Thread.sleep( 250 ); } catch( InterruptedException e ) {}
		
		Runnable runB = new Runnable() 
		{
			public void run() 
			{
				obj2.checkOther( obj1 );
			};
		};
		
		Thread threadB = new Thread( runB, "ThreadB" );
		threadB.start();
		try
		{
			threadA.join();
			threadB.join();
		}
		catch( InterruptedException x )
		{}
		
		System.out.println( "DEADLOCKED. THIS WILL NEVER PRINT!" );
		
		System.out.println( "done" );
	}
}
