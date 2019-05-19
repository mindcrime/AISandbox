package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter8;

import java.lang.invoke.MethodHandles;

public class CubbyHoleMain
{
	
	private static void print( String msg )
	{
		String name = Thread.currentThread().getName();
		System.out.println( name + ": " + msg );
	}
	
	public static void main( String[] args ) throws InterruptedException
	{
		final CubbyHole cubby = new CubbyHole();
		
		Runnable runA = new Runnable() 
		{
			@Override
			public void run()
			{
				try
				{
					String str;
					Thread.sleep(  500  );
					
					str = "multithreaded";
					cubby.putIn( str );
					print( "in run() - just putIn: '" + str + "'" );
					
					str = "programming";
					cubby.putIn( str );
					print( "in run() - just putIn: '" + str + "'" );
					
					str = "with Java";
					cubby.putIn( str );
					print( "in run() - just putIn: '" + str + "'" );
					
				}
				catch( InterruptedException e )
				{
					e.printStackTrace();
				}
			}
		};
		
		Runnable runB = new Runnable() 
		{
			@Override
			public void run()
			{
				try
				{
					Object obj;
					
					obj = cubby.takeOut();
					print( "in run() - just tookOut: '" + obj + "'" );
				
					Thread.sleep( 500 );
				
					obj = cubby.takeOut();
					print( "in run() - just tookOut: '" + obj + "'" );
					
					obj = cubby.takeOut();
					print( "in run() - just tookOut: '" + obj + "'" );

				}
				catch( InterruptedException e )
				{
					e.printStackTrace();
				}
			}
		};
		
		
		Thread threadA = new Thread( runA, "ThreadA" );
		threadA.start();
		
		Thread threadB = new Thread( runB, "ThreadB" );
		threadB.start();
		
		threadA.join();
		threadB.join();
		
		System.out.println( MethodHandles.lookup().lookupClass().getSimpleName() + ": done" );
	}
}
