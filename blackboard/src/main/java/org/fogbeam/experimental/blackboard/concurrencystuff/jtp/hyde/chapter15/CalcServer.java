package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter15;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CalcServer
{
	private ServerSocket ss;
	private List workerList;
	
	private Thread internalThread;
	private volatile boolean noStopRequested;
	
	public CalcServer( int port ) throws IOException
	{
		ss = new ServerSocket( port );
		workerList = new LinkedList();
		
		noStopRequested = true;
		
		Runnable r = new Runnable() 
		{
			@Override
			public void run() 
			{
				try
				{
					runWork();
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
				
			}
		};
		
		internalThread = new Thread( r, "InternalThread" );
		internalThread.start();
		
	}
	
	private void runWork()
	{
		System.out.println( "in CalcServer - ready to accept connections" );
		
		while( noStopRequested )
		{
			try
			{
				System.out.println( "in CalcServer - about to block, waiting for a new Connection" );
			
				Socket socket = ss.accept();
			
				System.out.println( "in CalcServer - received new Connection" );
			
				workerList.add( new CalcWorker( socket ) );
				
			}
			catch( IOException iox )
			{
				if( noStopRequested )
				{
					iox.printStackTrace();
				}
			}
		}
		
		// stop all the workers that were created
		System.out.println( "in CalcServer - putting in a stop request to all the workers!" );
		Iterator iter = workerList.iterator();
		while( iter.hasNext() )
		{
			CalcWorker worker = (CalcWorker)iter.next();
			worker.stopRequest();
			
		}
	}
	
	public void stopRequest()
	{
		System.out.println( "in CalcServer - entering stopRequest()" );
		
		noStopRequested = false;
		internalThread.interrupt();
		
		if( ss != null )
		{
			try
			{
				ss.close();
			}
			catch( Exception e )
			{
				// ignore...
			}
			finally
			{
				ss = null;
			}
		}
		
	}
	
	public boolean isAlive()
	{
		return internalThread.isAlive();
	}
	
	
	
	public static void main( String[] args )
	{	
		int port = 2001;
		
		try
		{
			CalcServer server = new CalcServer( port );
			Thread.sleep( 15000 );
			System.out.println( "in main() - about to send stopRequest" );
			server.stopRequest();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		catch( InterruptedException e )
		{
			
		}
	}
}
