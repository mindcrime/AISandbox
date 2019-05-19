package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter8;

public class CubbyHole
{
	private Object slot;
	
	public CubbyHole()
	{
		slot = null;
	}
	
	public synchronized void putIn( Object obj ) throws InterruptedException
	{
		print( "in putIn() - entering" );
		
		while( slot != null )
		{
			print( "in putIn() - slot occupied, waiting" );
			wait();
			print( "in putIn() - notified. Back from wait" );
		}
		
		slot = obj;
		print( "in putIn() - filled slot with obj, about to notifyAll" );
		notifyAll();
		
		print( "in putIn() - leaving" );
	}
	
	public synchronized Object takeOut() throws InterruptedException
	{
		print( "in takeOut() - entering" );
		while( slot == null )
		{
			print( "in takeOut() - empty, about to wait" );
			wait(); // wait while the slot is empty
			print( "in takeOut() - notified, back from wait" );
		}
		
		Object obj = slot;
		slot = null; // make slot as empty
		print( "in takeOut() - emptied slot, about to notifyAll" );
		notifyAll();
		
		print( "in takeOut() - leaving" );
		
		return obj;
	}
	
	private static void print( String msg )
	{
		String name = Thread.currentThread().getName();
		System.out.println( name + ": " + msg );
	}
}
