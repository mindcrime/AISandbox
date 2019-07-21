package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter16;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SureStop 
{
	// nested internal class for stop request entries
	private static class Entry
	{
		private Thread thread;
		private long stopTime;
		
		private Entry( Thread t, long stop )
		{
			thread = t;
			stopTime = stop;
		}
	}
	
	// static reference to the singleton instance
	private static SureStop ss = new SureStop();
	
	private List stopList;
	private List pendingList;
	private Thread internalThread;
	
	private SureStop()
	{
		stopList = new LinkedList();
		pendingList = new ArrayList(20);
		
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
		
		internalThread = new Thread( r );
		internalThread.setDaemon( true );
		internalThread.setPriority( Thread.MAX_PRIORITY );
		internalThread.start();
		
	}
	
	private void runWork()
	{
		try
		{
			while( true )
			{
				Thread.sleep( 500 );
				
				// stop expired threads and determine the amount of time
				// until the next thread is due to expire
				long sleepTime = checkStopList();
				synchronized( pendingList )
				{
					if( pendingList.size() < 1 )
					{
						pendingList.wait( sleepTime );
					}
					else
					{
						// copy into stopList and then remove from pendingList
						stopList.addAll( pendingList );
						pendingList.clear();
					}
				}
			}
		}
		catch( InterruptedException x )
		{
			// ignore...
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	private long checkStopList()
	{
		long currTime = System.currentTimeMillis();
		long minTime = Long.MAX_VALUE;
		
		Iterator iter = stopList.iterator();
		while( iter.hasNext() )
		{
			Entry entry = (Entry)iter.next();
			
			if( entry.thread.isAlive() )
			{
				if( entry.stopTime < currTime )
				{
					// timed out, stop it abruptly right now
					try
					{
						System.out.println("Killing Thread " + entry.thread.getName() + " now" );
						entry.thread.stop();
					}
					catch( SecurityException s )
					{
						System.err.println( "SureStop was not permitted to stop Thread!" );
						s.printStackTrace();
					}
					
					// remove it from stopList
					iter.remove();
				}
				else
				{
					// not yet expired, check to see if this is the new minimum
					minTime = Math.min( entry.stopTime, minTime );
				}				
			}
			else
			{
				System.out.println( "Thread " + entry.thread.getName() + " died on its own" );
				// Thread died on its own, remove from stopList
				iter.remove();
			}
		}
		
		long sleepTime = minTime - System.currentTimeMillis();
		
		sleepTime = Math.max( 50, sleepTime );
		
		return sleepTime;
	}
	
	private void addEntry( Entry entry )
	{
		synchronized( pendingList )
		{
			pendingList.add( entry );
			pendingList.notify();
			
		}
	}
	
	public static void ensureStop( Thread t, Long msGracePeriod )
	{
		if( !t.isAlive())
		{
			System.out.println( "Thread " + t.getName() + " was already dead, nothing to do!" );
			// nothing to do, return immediately
			return;
		}
		
		long stopTime = System.currentTimeMillis() + msGracePeriod;
		
		Entry entry = new Entry( t, stopTime );
		
		ss.addEntry( entry );
	}
}
