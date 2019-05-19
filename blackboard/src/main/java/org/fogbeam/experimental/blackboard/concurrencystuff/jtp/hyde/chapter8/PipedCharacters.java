package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.invoke.MethodHandles;

public class PipedCharacters
{
	public static void writeStuff( final Writer outputWriter )
	{
		try
		{
			BufferedWriter out = new BufferedWriter( outputWriter );
			
			String[][] line = { 
								{ "Java", "has", "nice", "features" }, 
								{ "Pipes", "are", "interesting" }, 
								{ "Threads", "are", "fun", "in", "Java." }, 
								{ "Don't", "you", "think", "so?" } 
							};
			
			for( int i = 0; i < line.length; i++ )
			{
				String[] words = line[i];
				
				for( int j = 0; j < words.length; j++ )
				{
					if( j > 0 )
					{
						out.write(  " " ); // put a space between words
					}
					
					out.write(  words[j] );
				}
			
				out.newLine();
			}
			
			out.flush();
			out.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public static void readStuff( final Reader inputReader )
	{
		try
		{
			BufferedReader in = new BufferedReader( inputReader );
			
			String line = "";
			
			while( ( line = in.readLine() ) != null )
			{
				System.out.println( "Read line: " + line );
			}

			System.out.println( "Read all data from the pipe!" );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	
	
	public static void main( String[] args ) throws Exception
	{
		final PipedWriter pipeOut = new PipedWriter();
		final PipedReader pipeIn = new PipedReader( pipeOut );
		
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
