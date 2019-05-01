package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter6;

public class SetPriority
{
	private static Runnable makeRunnable()
	{
		Runnable r = new Runnable() {
			
			public void run()
			{
				for( int i = 0; i < 5; i++ )
				{
					Thread t = Thread.currentThread();
					System.out.println( "in run) - priority = " 
										+ t.getPriority() 
										+ ", name = " + t.getName() );
					
					try
					{
						Thread.sleep(  2000 );
					}
					catch( Exception x )
					{}
				}
			}
		};
		
		return r;
	}
	
	
	public static void main( String[] args )
	{
		System.out.println( "in main() - Thread.currentThread().getPriority() = " 
							+ Thread.currentThread().getPriority() );
		
		System.out.println( "in main() - Thread.currentThread().getName() = " 
				+ Thread.currentThread().getName() );
		
		Thread threadA = new Thread( makeRunnable(), "ThreadA" );
		threadA.setPriority( 8 );
		threadA.start();
		
		Thread threadB = new Thread( makeRunnable(), "ThreadB" );
		threadB.setPriority( 2 );
		threadB.start();
		
		Runnable r = new Runnable() 
		{
			public void run()
			{
				Thread threadC = new Thread( makeRunnable(), "ThreadC" );
				threadC.start();
			}
		};
		Thread threadD = new Thread( r, "ThreadD" );
		threadD.setPriority( 7 );
		threadD.start();
		
		
		try
		{
			Thread.sleep(  4000 );
		}
		catch( Exception x )
		{}
		
		threadA.setPriority( 3 );
		
		System.out.println( "in main() - threadA.getPriority() = " 
							+ threadA.getPriority() );

	}
}
