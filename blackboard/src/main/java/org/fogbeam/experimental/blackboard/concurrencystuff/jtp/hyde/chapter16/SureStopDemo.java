package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter16;

public class SureStopDemo 
{
	
	private static Thread launch( final String name, final long lifetime )
	{
		final int loopCount = (int)( lifetime / 1000 );
		Runnable r = new Runnable() 
		{
			@Override
			public void run() 
			{
				try
				{
					for( int i = 0; i < loopCount; i++ )
					{
						Thread.sleep( 1000 );
						System.out.println( "Running --> " + name );
					}
				}
				catch( InterruptedException ie )
				{
					// ignore...
				}
			}
		};
		
		Thread t = new Thread( r, name );
		t.start();
		
		return t;
	}

	public static void main(String[] args) 
	{
		Runtime.getRuntime().addShutdownHook(new Thread() 
	    { 
	      public void run() 
	      { 
	        System.out.println("Done"); 
	      } 
	    });
		
		Thread t0 = SureStopDemo.launch( "T0", 1000 );
		Thread t1 = SureStopDemo.launch( "T1", 5000 );
		Thread t2 = SureStopDemo.launch( "T2", 15000 );
		
		try
		{
			Thread.sleep( 2000 );
		}
		catch( Exception e )
		{}
		
		SureStop.ensureStop(t0, 9000L);
		SureStop.ensureStop(t1, 1000L);
		SureStop.ensureStop(t2, 12000L);

		try
		{
			Thread.sleep( 20000 );
		}
		catch( Exception e )
		{}

		Thread t3 = SureStopDemo.launch( "T3", 15000 );
		SureStop.ensureStop(t3, 5000L);
		
		try
		{
			Thread.sleep( 1000 );
		}
		catch( Exception e )
		{}

		Thread t4 = SureStopDemo.launch( "T4", 5000 );
		SureStop.ensureStop(t4, 13000L);
		
		try
		{
			Thread.sleep( 10000 );
		}
		catch( Exception e )
		{}

		
	}

}
