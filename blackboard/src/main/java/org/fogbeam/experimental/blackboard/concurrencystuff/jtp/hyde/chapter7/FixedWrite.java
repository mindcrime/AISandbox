package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter7;

public class FixedWrite
{
	private String fName;
	private String lName;
	
	public synchronized void setNames( String firstName, String lastName )
	{
		print( "entering setNames()" );
		
		fName = firstName;
		
		// a Thread might be swapped out here and
		// may stay out for a varying amount of time
		// the different sleep times exaggerate this
		
		if( fName.length() < 5 )
		{
			try { Thread.sleep(  1000 ); } catch( Exception e ) {}
		}
		else
		{
			try { Thread.sleep(  2000 ); } catch( Exception e ) {}
		}
		
		lName = lastName;
		
		print( "leaving setNames() - " + fName + ", " + lName );
	}
	
	public static void print( String msg )
	{
		String threadName = Thread.currentThread().getName();
		System.out.println( threadName + ": " + msg );
	}
	
	public static void main( String[] args )
	{
		final FixedWrite fw = new FixedWrite();
		
		Runnable runA = new Runnable() 
		{
			public void run()
			{
				fw.setNames( "George", "Washington" );
			}
		};
		
		Thread threadA = new Thread( runA, "threadA" );
		threadA.start();
		
		try { Thread.sleep(  200 ); } catch( Exception e ) {}
		
		Runnable runB = new Runnable() 
		{
			public void run()
			{
				fw.setNames( "Abe", "Lincoln" );
			}
		};
		
		Thread threadB = new Thread( runB, "threadB" );
		threadB.start();
	}
}
