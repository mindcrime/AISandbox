package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter8;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.invoke.MethodHandles;

public class PipedBytes
{
	public static void writeStuff( final OutputStream outputStream )
	{
		try
		{
			DataOutputStream out = new DataOutputStream( new BufferedOutputStream( outputStream ) );
			
			int[] data = { 82, 105, 99, 104, 97, 114, 100, 32,
							72, 121, 100, 101 };
			
			for( int i = 0; i < data.length; i++ )
			{
				out.writeInt( data[i] );
			}
			
			out.flush();
			out.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public static void readStuff( final InputStream inputStream )
	{
		try
		{
			DataInputStream in = new DataInputStream( new BufferedInputStream( inputStream ) );
		
			boolean eof = false;
			
			while( !eof )
			{
				try
				{
					int i = in.readInt();
					System.out.println( "Just read: " + i );
				}
				catch( EOFException eofX )
				{
					eof = true;
				}
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	
	
	public static void main( String[] args ) throws Exception
	{
		final PipedOutputStream pipeOut = new PipedOutputStream();
		final PipedInputStream pipeIn = new PipedInputStream( pipeOut );
		
		Runnable runA = new Runnable() 
		{
			@Override
			public void run()
			{
				writeStuff( pipeOut );
			}
		};
		
		Runnable runB = new Runnable() 
		{
			@Override
			public void run()
			{
				readStuff( pipeIn );	
			}
		};
		
		Thread threadA = new Thread( runA, "ThreadA" );
		threadA.start();

		
		Thread threadB = new Thread( runB, "ThreadB" );
		threadB.start();
		
		threadA.join();
		threadB.join();
		
		System.out.println( MethodHandles.lookup().lookupClass().getSimpleName() + ": done" );
	}
}
