package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter11;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class Squish extends JComponent
{
	private static final long serialVersionUID = 1L;
	
	private Image[] frameList;
	private long msPerFrame;
	private volatile int currFrame;
	
	private Thread internalThread;
	private volatile boolean noStopRequested = true;
	
	public Squish( int width, int height, long msPerCycle, int framesPerSec, Color fgColor )
	{
		setPreferredSize( new Dimension( width, height ) );
		
		int framesPerCycle = (int) ( (framesPerSec * msPerCycle ) / 1000 );
		msPerFrame = 1000L / framesPerSec;
		
		frameList = buildImages( width, height, fgColor, framesPerCycle );
		
		currFrame = 0;
		
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
		internalThread.start();
	
	}
	
	private Image[] buildImages( int width, int height, Color fgColor, int count )
	{
		BufferedImage[] images = new BufferedImage[count];
		
		for( int i = 0; i < count; i++ )
		{
			images[i] = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
			
			double xShape = 0.0;
			double yShape = ( (double) (i*height) ) / ( (double)count );
		
			double wShape = width;
			double hShape = 2.0 * (height - yShape );
			
			Ellipse2D shape = new Ellipse2D.Double( xShape, yShape, wShape, hShape );
			Graphics2D g2 = images[i].createGraphics();
			g2.setColor( fgColor );
			g2.fill(  shape );
			g2.dispose();
		}

		return images;
	}
	
	private void runWork()
	{
		while( noStopRequested )
		{
			currFrame = (currFrame + 1 ) % frameList.length;
			repaint();
			
			try
			{
				Thread.sleep( msPerFrame );
			}
			catch( InterruptedException e )
			{
				// reassert interrupt and continue on
				Thread.currentThread().interrupt();
			}
		}
	}
	
	@Override
	public void paint( Graphics g )
	{
		g.drawImage( frameList[currFrame], 0, 0, this );
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
