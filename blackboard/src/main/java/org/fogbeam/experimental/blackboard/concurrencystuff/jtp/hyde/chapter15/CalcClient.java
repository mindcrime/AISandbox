package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter15;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class CalcClient
{
	public static void main( String[] args )
	{
		String hostName = "localhost";
		int port = 2001;
		
		try
		{
			Socket socket = new Socket( hostName, port );
			DataInputStream in = new DataInputStream( new BufferedInputStream( socket.getInputStream() ) );
			DataOutputStream out = new DataOutputStream( new BufferedOutputStream( socket.getOutputStream() ) );
			
			double val = 27.0;
			out.writeDouble( val );
			out.flush();
			
			double sqrt = in.readDouble();
			
			System.out.println( "Sent value: " + val + ", got back answer: " + sqrt );
			
			// don't ever send another request, but stay alive in this perpetually blocked state
			Object lock = new Object();
			while( true )
			{
				synchronized( lock )
				{
					lock.wait();
				}
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		System.out.println( "done" );
	}
}
