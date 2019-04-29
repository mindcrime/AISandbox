package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter2;

public class TwoThread1 extends Thread
{

	public void run() 
	{
		for( int i = 0; i < 10; i++ )
		{
			System.out.println( "New Thread" );
			
			try 
			{
				Thread.sleep(500);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		Thread thread = new TwoThread1();
		thread.start();
		
		
		for( int i = 0; i < 10; i++ )
		{
			System.out.println( "Main Thread" );
			
			try 
			{
				Thread.sleep(500);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
		}
		
		System.out.println( "\n\ndone" );
		
	}

}
