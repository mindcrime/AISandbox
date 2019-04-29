package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter2;

public class TwoThread extends Thread
{

	public void run() 
	{
		for( int i = 0; i < 10; i++ )
		{
			System.out.println( "New Thread" );
			
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		Thread thread = new TwoThread();
		thread.start();
		
		
		for( int i = 0; i < 10; i++ )
		{
			System.out.println( "Main Thread" );
		}
		
		System.out.println( "\n\ndone" );
		
	}

}
