package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter18;

public class SimpleObjectFIFO
{
	private Object[] queue;
	int capacity;
	int size;
	int head;
	int tail;
	
	public SimpleObjectFIFO( int capacity )
	{
		this.capacity = ( capacity > 0 ) ? capacity : 1; // at least one
		queue = new Object[capacity];
		head = 0;
		tail = 0;
		size = 0;
	}
	
	public synchronized int getSize()
	{
		return size;
	}
	
	public synchronized boolean isFull()
	{
		return ( size == capacity );
	}
	
	public synchronized void add( Object obj ) throws InterruptedException
	{
		while( isFull() )
		{
			wait();
		}
		
		queue[head] = obj;
		head = ( head + 1 ) % capacity;
		size++;
		
		notifyAll();
	}
	
	public synchronized Object remove() throws InterruptedException
	{
		while( size == 0 )
		{
			wait();
		}
		
		Object obj = queue[tail];
		queue[tail] = null;
		tail = ( tail + 1 ) % capacity;
		size--;
		
		notifyAll();
		
		return obj;
	}
	
	public synchronized void printState()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(  "SimpleObjectFIFO:\n"  );
		sb.append(  "            capacity = " + capacity + "\n" );
		sb.append(  "                size = " + size );
		
		if( isFull() )
		{
			sb.append(  " - FULL" );
		}
		else if( size == 0 )
		{
			sb.append(  " - EMPTY" );
		}
		
		sb.append(  "\n" );
		
		sb.append(  "                head = " + head + "\n" );
		sb.append(  "                tail = " + tail + "\n" );

		for( int i = 0; i < queue.length; i++ )
		{
			sb.append( "            queue[" + i + "] = " + queue[i] + "\n" );
		}
		
		System.out.print(  sb.toString() );
		
	}
	
}
