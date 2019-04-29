package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter3;

public class TwoThreadAlive extends Thread
{

	public void run() 
	{
		for( int i = 0; i < 10; i++ )
		{
			printMsg();
		}
	}
	
	public void printMsg()
	{
		// get a reference to the thread running this
		Thread t = Thread.currentThread();
		String name = t.getName();
		System.out.println( "name = " + name );
	}
	
	public static void main(String[] args) throws Exception
	{		
		TwoThreadAlive thread = new TwoThreadAlive();
		thread.setName( "My Worker Thread" );
		
		System.out.println( "Before start(), thread.isAlive() = " + thread.isAlive() );
		
		
		thread.start();
		
		System.out.println( "Just after start(), thread.isAlive() = " + thread.isAlive() );
		
		for( int i = 0; i < 10; i++ )
		{
			thread.printMsg();
		}
		
		
		System.out.println( "At end of main(), thread.isAlive() = " + thread.isAlive() );
		
	}

}
