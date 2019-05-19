package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter8;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EarlyNotifyFix
{
	private List<String> list;
	
	public EarlyNotifyFix()
	{
		list = Collections.synchronizedList(  new LinkedList<String>() );
	}
	
	public String removeItem() throws InterruptedException
	{
		print( "in removeItem() - entering" );
		
		synchronized( list )
		{
			while( list.isEmpty() ) // dangerous to use if!
			{
				print( "in removeItem() - about to wait" );
				list.wait();
				print( "in removeItem() - done with wait" );
			}
			
			// extract the new first item
			String item = list.remove( 0 );
			
			print( "in removeItem() - leaving" );
			return item;
		}
		
	}
	
	public void addItem( String item )
	{
		print( "in addItem() - entering" );
		
		synchronized( list )
		{
			// there will always be room to add to the
			// list, since it expands as needed
			list.add(  item  );
			
			print( "in addItem() - just added: " + item );
			
			// after adding, notify any and all waiting threads that
			// the list has changed
			list.notifyAll();
			
			print( "in addItem() - just notified" );
		}
		
		print( "in addItem() - leaving" );
	}
	
	public static void print( String msg )
	{
		String name = Thread.currentThread().getName();
		System.out.println( name + ": " + msg );
		
	}
	
	
	public static void main( String[] args )
	{
		EarlyNotifyFix enf = new EarlyNotifyFix();
		
		Runnable runA = new Runnable() 
		{
			@Override
			public void run()
			{
				try
				{
					String item = enf.removeItem();
					print( "in run() - returned: '" + item + "'" );
					
				}
				catch( InterruptedException x )
				{
					print( "interrupted" );
				}
				catch( Exception e )
				{
					print( "threw Exception!!\n" + e );
				}
			}
		};
		
		Runnable runB = new Runnable() 
		{
			@Override
			public void run()
			{
				enf.addItem( "Hello" );
			}
		};
		
		try
		{
			Thread threadA1 = new Thread( runA, "ThreadA1" );
			threadA1.start();
			
			Thread.sleep(  250 );
			
			Thread threadA2 = new Thread( runA, "ThreadA2" );
			threadA2.start();
			
			Thread.sleep(  500 );
			
			Thread threadB = new Thread( runB, "ThreadB" );
			threadB.start();
			
			Thread.sleep(  10000 ); // sleep 10 seconds
			
			threadA1.interrupt();
			threadA2.interrupt();
		
		}
		catch( Exception e )
		{
			// ignore
		}
		
		System.out.println( MethodHandles.lookup().lookupClass().getSimpleName() + ": done" );
	}
}
