package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter8;

import java.util.Random;

public class WaitNotifyDemo
{
	private int value = 0;
	
	public void doWork()
	{
		value = new Random().nextInt( 3000 );
		synchronized( this )
		{
			this.notify();
		}
	}
	
	public int getValue()
	{
		return value;
	}
	
	public static void main( String[] args )
	{
		WaitNotifyDemo wnd = new WaitNotifyDemo();
		
		Runnable r1 = new Runnable() 
		{
			public void run() 
			{
				synchronized( wnd )
				{
					System.out.println( "wnd.getValue() = " + wnd.getValue() );
					
					try
					{
						wnd.wait();
						
						System.out.println( "Notified that wnd is ready!" );
						
						System.out.println( "wnd.getValue() = " + wnd.getValue() );
						
					}
					catch( InterruptedException e )
					{
						System.out.println( "Interrupted while waiting" );
					}
				}
			};
		};
		Thread t1 = new Thread( r1, "Thread1" );
		t1.start();
		
		try { Thread.sleep(  2500 ); } catch( Exception e ) {}
		
		Runnable r2 = new Runnable() 
		{
			public void run() 
			{
				wnd.doWork();
			};
		};
		Thread t2 = new Thread( r2, "Thread2" );
		t2.start();
		
		try
		{
			t1.join();
			t2.join();
		}
		catch( Exception e ) {}
		
		System.out.println( "done" );
	}
}
