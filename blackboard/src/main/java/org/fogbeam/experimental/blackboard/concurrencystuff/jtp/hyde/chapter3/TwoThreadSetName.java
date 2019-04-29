package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter3;

public class TwoThreadSetName extends Thread
{

	public void run() 
	{
		for( int i = 0; i < 10; i++ )
		{
			printMsg();
			
			try
			{
				Thread.sleep( 250 );
			}
			catch( Exception e )
			{}
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
		TwoThreadSetName thread = new TwoThreadSetName();
		thread.setName( "My Worker Thread!" );
		thread.start();
		
		
		for( int i = 0; i < 10; i++ )
		{
			thread.printMsg();
			
			try
			{
				Thread.sleep( 250 );
			}
			catch( Exception e )
			{}
			
		}
		
		thread.join();
		
		System.out.println( "\n\ndone" );
		
	}

}
