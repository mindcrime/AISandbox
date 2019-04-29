package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JComponent;

public class SecondCounterInaccurate extends JComponent implements Runnable
{
	private static final long serialVersionUID = 1L;

	private volatile boolean keepRunning;
	private Font paintFont;
	private volatile String timeMsg;
	private volatile int arcLen;
	
	public SecondCounterInaccurate()
	{
		paintFont = new Font( "SansSerif", Font.BOLD, 14 );
		timeMsg = "never started";
		arcLen = 0;
	}
	
	public void stopClock()
	{
		keepRunning = false;
	}
	
	@Override
	public void run()
	{
		runClock();
	}
	
	public void runClock()
	{
		System.out.println( "Thread running runClock() is " + Thread.currentThread().getName() );
	
		DecimalFormat fmt = new DecimalFormat( "0.000" );
		long normalSleepTime = 100;
		
		int counter = 0;
		long startTime = System.currentTimeMillis();
		
		keepRunning = true;
		
		while( keepRunning )
		{
			try
			{
				Thread.sleep(  normalSleepTime );
			}
			catch( Exception e )
			{ 
				// intentional NOP 
			}
			
			counter++;
			double counterSecs = counter / 10.0;
			
			double elapsedSecs = ( System.currentTimeMillis() - startTime ) / 1000.0;
			double diffSecs = counterSecs - elapsedSecs;
			
			
			timeMsg = fmt.format( counterSecs ) 
						+ " - " 
						+ fmt.format( elapsedSecs ) 
						+ " = " 
						+ fmt.format(  diffSecs );
			
			arcLen = ( ( (int)counterSecs ) %60 ) * ( 360/60 );
			repaint();
		}
	
	}
	
	public void paint( Graphics g )
	{
		System.out.println( "Thread that invoked paint is " + Thread.currentThread().getName() );
		
		g.setColor( Color.black );
		g.setFont( paintFont );
		g.drawString( timeMsg, 0, 15 );
		
		g.fillOval( 0, 20, 100, 100 );
		
		g.setColor(  Color.white  );
		g.fillOval(  3, 23, 94, 94 );
		
		g.setColor( Color.blue );
		g.fillArc( 2, 22, 96, 96, 90, -arcLen );
	}
}
