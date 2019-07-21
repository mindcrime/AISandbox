package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter15;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CalcWorker
{
	private InputStream socketIn;
	private OutputStream socketOut;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private Socket socket;
	
	private Thread internalThread;
	private volatile boolean noStopRequested;
	
	
	public CalcWorker( Socket socket ) throws IOException
	{
		this.socket = socket;
		
		socketIn = socket.getInputStream();
		socketOut = socket.getOutputStream();
		
		dataIn = new DataInputStream( new BufferedInputStream( socketIn ) );
		dataOut = new DataOutputStream( new BufferedOutputStream( socketOut ) );
		
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
		while( noStopRequested )
		{
			try
			{
				System.out.println( "in CalcWorker - about to block waiting to read a double" );
				double val = dataIn.readDouble();
				System.out.println( "in CalcWorker - read a double" );
				dataOut.writeDouble( Math.sqrt( val ) );
				dataOut.flush();
			}
			catch( IOException ioe )
			{
				if( noStopRequested )
				{
					ioe.printStackTrace();
					stopRequest();
				}
			}
		}

		closeResources();
		
		System.out.println( "in CalcWorker - leaving runWork" );
	
	}
	
	private void closeResources()
	{
		try
		{
			if( dataIn != null )
			{
				dataIn.close();
				dataIn = null;
			}
		}
		catch( Exception e )
		{}
		
		try
		{
			if( dataOut != null )
			{
				dataOut.close();
				dataOut = null;
			}
		}
		catch( Exception e )
		{}
		
		try
		{
			if( socket != null )
			{
				socket.close();
				socket = null;
			}
		}
		catch( Exception e )
		{}
		
	}
	
	public void stopRequest()
	{
		System.out.println( "in CalcWorker - entering stopRequest()" );
		
		noStopRequested = false;
		internalThread.interrupt();
		
		closeResources();
		
		System.out.println( "in CalcWorker - leaving stopRequest()" );
	}
	
	public boolean isAlive()
	{
		return internalThread.isAlive();
	}
}
