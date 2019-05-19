package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter7;

public class TwoObjects
{
	private String objId;
	
	public TwoObjects( String objId )
	{
		this.objId = objId;
	}
	
	public synchronized void doStuff( int val )
	{
		print( "entering doStuff()" );
		
		int num = val * 2 + objId.length();
		
		print( "in doStuff() - local variable num = " + num );
		
		// slow things down to make observations
		try  { Thread.sleep(  2000 ); } catch( Exception e ) {}
		
		
		print( "leaving doStuff()" );
	}
	
	public void print( String msg )
	{
		threadPrint( "objId = " + objId + " - " + msg );
	}
	
	public static void threadPrint( String msg )
	{
		String threadName = Thread.currentThread().getName();
		
		System.out.println( threadName + ": " + msg );
	}
	
	public static void main( String[] args ) throws InterruptedException
	{
		
		final TwoObjects two1 = new TwoObjects( "obj1" );
		final TwoObjects two2 = new TwoObjects( "obj2" );
		
		Runnable runA = new Runnable() 
		{
			public void run()
			{				
				two1.doStuff( 3 );
			}
		};
		
		Thread threadA = new Thread( runA, "threadA" );
		threadA.start();
		
		try  { Thread.sleep(  200 ); } catch( Exception e ) {}
		
		Runnable runB = new Runnable() 
		{
			public void run()
			{
				two2.doStuff( 7 );
			}
		};
		
		Thread threadB = new Thread( runB, "threadB" );
		threadB.start();
		
		threadB.join();
		threadA.join();
		
		System.out.println( "TwoObjects - done" );
	}
}
