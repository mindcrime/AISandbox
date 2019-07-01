package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter13;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URLConnection;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter18.ObjectFIFO;

public class HttpWorker
{
	private static int nextWorkerId = 0;
	
	private File docRoot;
	
	private ObjectFIFO idleWorkers;
	private int workerId;
	private ObjectFIFO handoffBox;
	
	private Thread internalThread;
	private volatile boolean noStopRequested;
	
	
	
	public HttpWorker( File docRoot, int workerPriority, ObjectFIFO idleWorkers )
	{
		
		this.docRoot = docRoot;
		this.idleWorkers = idleWorkers;
		
		workerId = getNextWorkerId();
		
		handoffBox = new ObjectFIFO( 1 );
		
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
		
		internalThread = new Thread( r, "HttpWorker: " + workerId );
		internalThread.setPriority( workerPriority );
		internalThread.start();
	}

	public static synchronized int getNextWorkerId()
	{
		int id = nextWorkerId;
		nextWorkerId++;
		return id;
	}
	
	public void processRequest( Socket s ) throws InterruptedException
	{
		handoffBox.add( s );
	}
	
	private void runWork()
	{
		Socket s = null;
		InputStream in = null;
		OutputStream out = null;
		
		while( noStopRequested )
		{
			try
			{
				idleWorkers.add( this );
				
				s = (Socket) handoffBox.remove();
				
				in = s.getInputStream();
				out = s.getOutputStream();
				generateResponse( in, out );
				out.flush();
				
			}
			catch( IOException ioe )
			{
				System.err.println( "I/O Error while processing request. Ignoring and adding back to idle workers: " + workerId );
			}
			catch( InterruptedException ie )
			{
				// re-assert the interrupt
				Thread.currentThread().interrupt();
			}
			finally
			{
				if( in != null )
				{
					try
					{
						in.close();
					}
					catch( Exception e )
					{}
					finally 
					{
						in = null;
					}
				}
				
				if( out != null )
				{
					try
					{
						out.close();
					}
					catch( Exception e )
					{}
					finally
					{
						out = null;
					}
				}
				
				if( s != null )
				{
					try
					{
						s.close();
					}
					catch( Exception e )
					{}
					finally
					{
						s = null;
					}
				}
			}
			
			
		}	
	}
	
	private void generateResponse( InputStream in, OutputStream out ) throws IOException
	{
		BufferedReader reader = new BufferedReader( new InputStreamReader( in ) );
		
		String requestLine = reader.readLine();
		
		if( requestLine == null || ( requestLine.length() < 1 ) )
		{
			throw new IOException( "Could not read request!" );
		}
		
		System.out.println( "workerId: " + workerId + "\n" + "requestLine: " + requestLine );
		
		StringTokenizer sz = new StringTokenizer( requestLine );
		
		String fileName = null;
		
		try
		{
			// ignore GET
			sz.nextToken();
			
			fileName = sz.nextToken();
			
		}
		catch( NoSuchElementException e )
		{
			throw new IOException( "could not parse request line" );
		}
		
		File requestedFile = generateFile( fileName );
		
		BufferedOutputStream buffOut = new BufferedOutputStream( out );
	
		if( requestedFile.exists() )
		{
			System.out.println( "workerId = " + workerId + ", 200 OK" );
		
			
			int fileLen = (int)requestedFile.length();
			
			BufferedInputStream fileIn = new BufferedInputStream( new FileInputStream( requestedFile ) );
		
			String contentType = URLConnection.guessContentTypeFromStream( fileIn );
			
			byte[] headerBytes = createHeaderBytes( "HTTP/1.0 200 OK", fileLen, contentType );
			
			buffOut.write(  headerBytes );
			
			byte[] buff = new byte[2048];
			
			int blockLen = 0;
			
			while( ( blockLen = fileIn.read( buff ) ) != -1 )
			{
				buffOut.write(  buff, 0, blockLen );
			}
			
			fileIn.close();
		}
		else
		{
			System.out.println( "workerId = " + workerId + ", 404 Not Found: " + fileName );
			
			byte[] headerBytes = createHeaderBytes( "HTTP/1.0 404 Not Found", -1, null );
			
			buffOut.write( headerBytes );
		}
		
		try
		{
			Thread.sleep(  500 );
		}
		catch( InterruptedException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		buffOut.flush();
	}
	
	
	private File generateFile( String fileName )
	{
		File requestedFile = docRoot; // start at the root...
		
		StringTokenizer sz = new StringTokenizer( fileName, "/" );
		while( sz.hasMoreTokens() )
		{
			String token = sz.nextToken();
			
			if( token.contentEquals( ".." ))
			{
				// silently ignore
				continue;
			}
			
			// tack on additional components of the path as we encounter them
			requestedFile = new File( requestedFile, token );
		}
		
		if( requestedFile.exists() && requestedFile.isDirectory() )
		{
			requestedFile = new File( requestedFile, "index.html" );
		}
		
		return requestedFile;
	}
	
	private byte[] createHeaderBytes( String resp, int contentLen, String contentType ) throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( bos ) );
		
		writer.write( resp + "\r\n" );
		
		// if a length was specified, add it to the header
		if( contentLen != -1 )
		{
			writer.write( "Content-Length: " + contentLen + "\r\n" );
		}
		
		// if a type was specified, add it to the header
		if( contentType != null && !contentType.isEmpty() )
		{
			writer.write(  "Content-Type: " + contentType + "\r\n" );
		}
		
		// a blank line is required
		writer.write(  "\r\n" );
		writer.flush();
		
		byte[] data = bos.toByteArray();
		
		writer.close();
		
		return data;
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
	
}
