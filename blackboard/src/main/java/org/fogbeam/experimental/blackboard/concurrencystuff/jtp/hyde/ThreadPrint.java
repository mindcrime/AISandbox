package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde;

public interface ThreadPrint
{
	default void print( String msg )
	{
		String name = Thread.currentThread().getName();
		System.out.println( name + " : " + msg );
	}
	
	public static void printZ( String msg )
	{
		String name = Thread.currentThread().getName();
		System.out.println( name + " : " + msg );
	}
}
