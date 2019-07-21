package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter15;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

public class DefiantStream
{
	public static void main( String[] args ) throws Exception
	{
		final InputStream in = System.in;
		
		Runnable r = new Runnable() 
		{
			@Override
			public void run()
			{
				try
				{
					System.err.println( "About to try to read from in" );
				
					in.read();
					
					System.err.println( "Just read from in..." );
					
					if( false )
						throw new InterruptedException();
				}
				catch( InterruptedIOException iioe )
				{
					iioe.printStackTrace();
				}
				catch( IOException ioe )
				{
					ioe.printStackTrace();
				}
				catch( InterruptedException ie )
				{
					ie.printStackTrace();
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
				finally 
				{
					Thread currThread = Thread.currentThread();
					
					System.err.println( "inside finally:\n"
							+ "currThread = " + currThread.getId() + " - " + currThread.getName() + "\n" 
							+ "currThread.isAlive() = " + currThread.isAlive() );
					System.err.flush();
				}
				
			}
		};
		
		Thread t = new Thread( r );
		
		t.start();
		
		Thread.sleep( 2000 );
		
		System.err.println( "About to interrupt Thread!" );
		t.interrupt();
		System.err.println( "Just interrupted Thread!" );
		
		Thread.sleep( 3000 );
		
		System.err.println( "About to stop Thread!" );
		t.stop();
		System.err.println( "Just stopped Thread!" );
		
		Thread.sleep( 3000 );
		
		System.err.println( "t.isAlive() = " + t.isAlive() );
		
		System.out.println( "done" );
	}
}
