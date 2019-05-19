package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter7;

public class CleanRead
{
	private String fName;
	private String lName;
	
	public synchronized String getNames()
	{
		return fName + ", " + lName;
	}
	
	public synchronized void setNames( String firstName, String lastName )
	{
		print( "entering setNames()" );
		
		fName = firstName;
		
		try { Thread.sleep(  1000 ); } catch( Exception e ) {}		
		
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
		final CleanRead cr = new CleanRead();
		cr.setNames( "George", "Washington" );
		
		Runnable runA = new Runnable() 
		{
			public void run()
			{
				cr.setNames( "Abe", "Lincoln" );
			}
		};
		
		Thread threadA = new Thread( runA, "threadA" );
		threadA.start();
		
		try { Thread.sleep(  200 ); } catch( Exception e ) {}
		
		Runnable runB = new Runnable() 
		{
			public void run()
			{
				print( "getNames() = " + cr.getNames() );
			}
		};
		
		Thread threadB = new Thread( runB, "threadB" );
		threadB.start();
	}
}
