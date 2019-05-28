package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter9;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.ThreadPrint;

public class DigitalTimer extends JLabel
{
	
	private volatile String timeText;
	
	private Thread internalThread;
	private volatile boolean noStopRequested;
	
	public DigitalTimer()
	{
		setBorder( BorderFactory.createLineBorder( Color.black ) );
		setHorizontalAlignment( SwingConstants.RIGHT );
		
		setFont( new Font( "SansSerif", Font.BOLD, 16 ) );
	
		setText( "000000.0" );
		
		setMinimumSize( getPreferredSize() );
		setPreferredSize( getPreferredSize() );
		setSize( getPreferredSize() );
		
		timeText = "0.0";
		setText( timeText );
		
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
		
		internalThread = new Thread( r, "DigitalTimer" );
		internalThread.start();
	}
	
	private void runWork()
	{
		long startTime = System.currentTimeMillis();
		int tenths = 0;
		long normalSleepTime = 100;
		long nextSleepTime = 100;
		
		DecimalFormat fmt = new DecimalFormat( "0.0" );
		
		Runnable updateText = new Runnable() 
		{
			@Override
			public void run()
			{
				setText( timeText );
			}
		};
		
		while( noStopRequested )
		{
			try
			{
				Thread.sleep( nextSleepTime );
				
				tenths++;
				
				long currTime = System.currentTimeMillis();
				long elapsedTime = currTime - startTime;
				
				nextSleepTime = normalSleepTime + ( ( tenths * 100 ) - elapsedTime );
				
				if( nextSleepTime < 0 )
				{
					nextSleepTime = 0;
				}
				
				timeText = fmt.format( elapsedTime / 1000.0 );
				
				SwingUtilities.invokeAndWait( updateText );
				
			}
			catch( InterruptedException e )
			{
				// stop running
				return;
			}
			catch( InvocationTargetException e )
			{
				e.printStackTrace();
			}
		}
	}
	
	private void stopRequest()
	{
		noStopRequested = false;
		internalThread.interrupt();
	}
	
	private boolean isAlive()
	{
		return internalThread.isAlive();
	}	
	
	public static void main( String[] args )
	{
		DigitalTimer dt = new DigitalTimer();
		
		JPanel p = new JPanel( new FlowLayout() );
		p.add( dt );
		
		JFrame f = new JFrame( "DigitalTimer demo" );
		f.setContentPane( p );
		f.setSize( 400, 100 );
		
		f.addWindowListener( new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{
				System.exit( 0 );
			};
		} );
		
		f.setVisible( true );		
		
		ThreadPrint.printZ( "done" );
	}
}
