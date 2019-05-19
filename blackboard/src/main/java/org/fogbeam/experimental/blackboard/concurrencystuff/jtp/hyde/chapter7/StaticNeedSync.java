package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter7;

public class StaticNeedSync
{
	private static int nextSerialNum = 1001;
	
	
	public static int getNextSerialNum()
	{
		int sn = nextSerialNum;
		
		// simulate a delay that is possible if the thread scheduler
		// chooses to swap this thread off the processor at this point.
		// The delay is exaggerated for demonstration purposes.
		
		try { Thread.sleep(  1500 ); } catch( Exception e ) {}
		
		nextSerialNum++;
		
		return sn;
		
	}
	
	private static void print( String msg )
	{
		String threadName = Thread.currentThread().getName();
		System.out.println( threadName + ": " + msg );
	}
	
	public static void main( String[] args ) throws InterruptedException
	{
		Runnable r = new Runnable() 
		{
			public void run()
			{
				print( "getNextSerialNum() = " + getNextSerialNum() );
			}
		};
		
		Thread threadA = new Thread( r, "threadA" );
		threadA.start();
		
		Thread.sleep(  1500 );
		
		
		Thread threadB = new Thread( r, "threadB" );
		threadB.start();
		
		Thread.sleep(  500 );
		
		Thread threadC = new Thread( r, "threadC" );
		threadC.start();
		
		Thread.sleep(  2500 );
		
		Thread threadD = new Thread( r, "threadD" );
		threadD.start();
		
	}
}
