package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter12;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

// an ActiveObject that supports monitoring by an ExceptionListener
public class ExceptionCallback
{
	private Set<ExceptionListener> exceptionListeners;
	private Thread internalThread;
	private boolean noStopRequested;
	
	public ExceptionCallback( ExceptionListener[] initialGroup )
	{
		init( initialGroup );
	}
	
	public ExceptionCallback( ExceptionListener initialListener )
	{
		ExceptionListener[] group = new ExceptionListener[1];
		group[0] = initialListener;
		init( group );
	}
	
	public ExceptionCallback()
	{
		init( null );
	}
	
	private void init( ExceptionListener[] initialGroup )
	{
		System.out.println( "in constructor - initializing..." );
		
		exceptionListeners = Collections.synchronizedSet( new HashSet<ExceptionListener>() );
		
		// if any listeners should be added before the
		// thread starts, add them now
		if( initialGroup != null )
		{
			for( int i = 0; i < initialGroup.length; i++ )
			{
				addExceptionListener( initialGroup[i] );
			}
		}
		
		// this starts as true, so the thread has a chance to start working
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
					// in case any exception slips through
					sendException( e );
				}
			}
		};
		
		internalThread = new Thread( r );
		internalThread.start();
	}
	
	private void runWork()
	{
		try
		{
			makeConnection(); // will throw an IOException
		}
		catch( Exception e )
		{
			sendException( e );
		}
		
		String str = null;
		int len = determineLength( str ); // will throw a NPE
		
	}
	
	private void makeConnection() throws IOException 
	{
		// a NumberFormatException will be thrown when this
		// String is parsed
		String portStr = "j20";
		int port = 0;
		
		try
		{
			port = Integer.parseInt( portStr );
		}
		catch( NumberFormatException e )
		{
			sendException( e );
			port = 80; // use default
		}
		
		connectToPort( port );
	}
	
	private void connectToPort( int portNum ) throws IOException
	{
		throw new IOException( "Connection refused" );
	}
	
	private int determineLength( String s )
	{
		return s.length();
	}
	
	public void stopRequest()
	{
		noStopRequested = false;
		internalThread.interrupt();
	}
	
	public boolean isAlive()
	{
		return internalThread.isAlive();
	}
	
	private void sendException( Exception e )
	{
		if( exceptionListeners.size() == 0 )
		{
			// just dump the stacktrace to the console
			e.printStackTrace();
			return;
		}
		
		// use synchronized here to make sure that other
		// threads don't make changes while we're processing
		synchronized( exceptionListeners )
		{	
			Iterator<ExceptionListener> iter = exceptionListeners.iterator();
		
			while( iter.hasNext() )
			{
				ExceptionListener l = iter.next();
				l.exceptionOccurred( e, this );
			}
		}
	}
	
	public void addExceptionListener( final ExceptionListener exceptionListener )
	{
		// silently ignore any request to add a "null" listener
		if( exceptionListener != null )
		{
			exceptionListeners.add( exceptionListener );
		}
	}
	
	@SuppressWarnings("unused")
	private void removeExceptionListener( final ExceptionListener exceptionListener )
	{
		if( exceptionListener != null )
		{
			exceptionListeners.remove( exceptionListener );
		}
	}
	
	@Override
	public String toString()
	{
		return getClass().getName() + " [isAlive()=" + isAlive() + "]";
	}
}
