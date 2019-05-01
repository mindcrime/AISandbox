package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter6;

public class PriorityCompete
{
	
	private volatile long count;
	private boolean yield;
	private Thread internalThread;
	private volatile boolean noStopRequested;
	
	public PriorityCompete( String name, int priority, boolean yield ) 
	{
		count = 0;
		this.yield = yield;
		noStopRequested = true;
		
		Runnable r = new Runnable() 
		{
			public void run() 
			{
				try
				{
					// some dummy work...
					runWork();
				}
				catch( Exception x )
				{
					x.printStackTrace();
				}
			};
		};
		
		internalThread = new Thread( r, name );
		internalThread.setPriority( priority );
		
	}

	private void runWork()
	{
		Thread.yield();
		
		while( noStopRequested )
		{
			if( yield )
			{
				Thread.yield();
			}
			
			count++;
			
			for( int i = 0; i < 10000; i++ )
			{
				double x = i * Math.PI / Math.E;
			}
		}
	}
	
	public void startRequest()
	{
		internalThread.start();
	}
	
	public void stopRequest()
	{
		noStopRequested = false;
	}
	
	public long getCount()
	{
		return count;
	}
	
	public String getNameAndPriority()
	{
		return internalThread.getName() + " : priority = " + internalThread.getPriority();
	}
	
	private static void runSet( boolean yield )
	{
		PriorityCompete[] pc = new PriorityCompete[24];
		pc[0] = new PriorityCompete( "PC0", 1, yield );
		pc[1] = new PriorityCompete( "PC1", 1, yield );
		pc[2] = new PriorityCompete( "PC2", 1, yield );
		pc[3] = new PriorityCompete( "PC3", 1, yield );
		pc[4] = new PriorityCompete( "PC4", 4, yield );
		pc[5] = new PriorityCompete( "PC5", 4, yield );
		pc[6] = new PriorityCompete( "PC6", 4, yield );
		pc[7] = new PriorityCompete( "PC7", 4, yield );
		pc[8] = new PriorityCompete( "PC8", 4, yield );
		pc[9] = new PriorityCompete( "PC9", 4, yield );
		pc[10] = new PriorityCompete( "PC10", 4, yield );
		pc[11] = new PriorityCompete( "PC11", 4, yield );
		pc[12] = new PriorityCompete( "PC12", 4, yield );
		pc[13] = new PriorityCompete( "PC13", 4, yield );
		pc[14] = new PriorityCompete( "PC15", 4, yield );
		pc[15] = new PriorityCompete( "PC15", 4, yield );
		pc[16] = new PriorityCompete( "PC16", 4, yield );
		pc[17] = new PriorityCompete( "PC17", 4, yield );
		pc[18] = new PriorityCompete( "PC18", 4, yield );
		pc[19] = new PriorityCompete( "PC19", 4, yield );
		pc[20] = new PriorityCompete( "PC20", 4, yield );
		pc[21] = new PriorityCompete( "PC21", 4, yield );
		pc[22] = new PriorityCompete( "PC22", 9, yield );
		pc[23] = new PriorityCompete( "PC23", 9, yield );
		
		
		// let the dust settle for a bit before starting them up
		try
		{ Thread.sleep(  1000  ); }
		catch( Exception x )
		{}
		
		for( int i = 0; i < pc.length; i++ )
		{
			pc[i].startRequest();
		}
		
		long startTime = System.currentTimeMillis();
		
		try
		{ Thread.sleep(  30000  ); }
		catch( Exception x )
		{}
		
		for( int i = 0; i < pc.length; i++ )
		{
			pc[i].stopRequest();
		}
		
		long stopTime = System.currentTimeMillis();

		// let things settle down again
		try
		{ Thread.sleep(  1000  ); }
		catch( Exception x )
		{}
		
		long totalCount = 0;
		for( int i = 0; i < pc.length; i++ )
		{
			totalCount += pc[i].getCount();
		}

		System.out.println( "totalCount = " + totalCount 
							+ ", count/ms = " + roundTo( ( (double) totalCount ) / ( stopTime - startTime ) , 3 ) );
		
		for( int i = 0; i < pc.length; i++ )
		{
			double perc = roundTo( 100.0 * pc[i].getCount() / totalCount, 2 );
			System.out.println( pc[i].getNameAndPriority() + ", " + perc + "%, count = " + pc[i].getCount() );
		}
		
	}
	
	public static double roundTo( double val, int places )
	{
		double factor = Math.pow(  10, places );
		return ( (int) ( ( val * factor ) + 0.5 )  ) / factor;
	}
	
	public static void main( String[] args ) throws Exception
	{
		System.out.println( "Thread.MAX_PRIORITY = " + Thread.MAX_PRIORITY );
		
		Runnable r = new Runnable() 
		{
			
			public void run()
			{
				System.out.println( "Run without using yield" );
				System.out.println( "======================" );
				runSet( false );

				System.out.println();
				System.out.println( "Run using yield" );
				System.out.println( "======================" );
				runSet( true );
			}
		};
		
		Thread t = new Thread( r, "PriorityCompete" );
		t.setPriority( Thread.MAX_PRIORITY - 1 );
		t.start();
		
		t.join();
		System.out.println( "done" );
		
		
	}
}
