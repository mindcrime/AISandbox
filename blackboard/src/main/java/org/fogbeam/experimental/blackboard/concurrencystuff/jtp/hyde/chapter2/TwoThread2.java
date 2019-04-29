package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter2;

public class TwoThread2 extends Thread
{
	
	private Thread creatorThread;
	
	public TwoThread2()
	{
		creatorThread = Thread.currentThread();
	}

	public void run() 
	{
		for( int i = 0; i < 10; i++ )
		{
			printMsg();
		}
	}
	
	public void printMsg()
	{
		// get a reference to the Thread currently running this
		Thread t = Thread.currentThread();
		
		if( t == creatorThread )
		{
			System.out.println( "Creator Thread!" );
		}
		else if( t == this )
		{
			System.out.println( "New Thread!" );
		}
		else
		{
			System.out.println( "Mystery Thread --- Unexpected!!");
		}
		
	}
	
	public static void main(String[] args) throws Exception
	{
		TwoThread2 thread = new TwoThread2();
		thread.start();
		
		
		for( int i = 0; i < 10; i++ )
		{
			thread.printMsg();
		}
		
		System.out.println( "\n\ndone" );
		
	}

}
