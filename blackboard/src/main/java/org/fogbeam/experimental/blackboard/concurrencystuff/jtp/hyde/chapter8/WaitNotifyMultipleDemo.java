package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter8;

import java.util.Random;

public class WaitNotifyMultipleDemo
{
	private int value = 0;
	
	public void doWork()
	{
		threadPrint( "entering doWork()" );
		value = new Random().nextInt( 3000 );
		synchronized( this )
		{
			this.notifyAll();
		}
	}
	
	public int getValue()
	{
		return value;
	}
	
	public static void threadPrint( String msg )
	{
		String threadName = Thread.currentThread().getName();
		System.out.println( threadName + ": " + msg );
	}
	
	public static void main( String[] args )
	{
		WaitNotifyMultipleDemo wnd = new WaitNotifyMultipleDemo();
		
		Runnable r1 = new Runnable() 
		{
			public void run() 
			{
				synchronized( wnd )
				{
					threadPrint( "wnd.getValue() = " + wnd.getValue() );
					
					try
					{
						threadPrint( "waiting..." );
						
						wnd.wait();
						
						threadPrint( "Notified that wnd is ready!" );
						
						threadPrint( "wnd.getValue() = " + wnd.getValue() );
						
					}
					catch( InterruptedException e )
					{
						threadPrint( "Interrupted while waiting" );
					}
				}
			};
		};
		Thread t1 = new Thread( r1, "Thread1" );
		t1.start();
		
		try { Thread.sleep(  200 ); } catch( Exception e ) {}
		
		Runnable r2 = new Runnable() 
		{
			public void run() 
			{
				synchronized( wnd )
				{
					threadPrint( "wnd.getValue() = " + wnd.getValue() );
					
					try
					{
						threadPrint( "waiting..." );
						
						wnd.wait();
						
						threadPrint( "Notified that wnd is ready!" );
						
						threadPrint( "wnd.getValue() = " + wnd.getValue() );
						
					}
					catch( InterruptedException e )
					{
						threadPrint( "Interrupted while waiting" );
					}
				}
			};
		};
		Thread t2 = new Thread( r2, "Thread2" );
		t2.start();
		
		try { Thread.sleep(  2500 ); } catch( Exception e ) {}
		
		Runnable r3 = new Runnable() 
		{
			public void run() 
			{
				wnd.doWork();
			};
		};
		Thread t3 = new Thread( r3, "Thread3" );
		t3.start();
		
		try
		{
			t1.join();
			t2.join();
			t3.join();
		}
		catch( Exception e ) {}
		
		System.out.println( "done" );
	}
}
