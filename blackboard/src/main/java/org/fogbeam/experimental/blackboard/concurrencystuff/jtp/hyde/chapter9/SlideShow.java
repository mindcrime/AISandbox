package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter9;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.ThreadPrint;

public class SlideShow extends JComponent implements ThreadPrint
{
	private BufferedImage[] slides;
	private Dimension slideSize;
	private volatile int currSlide;
	
	private Thread internalThread;
	private volatile boolean noStopRequested;
	
	public SlideShow()
	{
		currSlide = 0;
		slideSize = new Dimension( 50, 50 );
		
		buildSlides();
		
		setMinimumSize( slideSize );
		setPreferredSize( slideSize );
		setMaximumSize( slideSize );
		setSize( slideSize );
		
		noStopRequested = true;
		Runnable r = new Runnable() 
		{
			public void run()
			{
				try
				{
					runWork();
				}
				catch( Exception x )
				{
					x.printStackTrace();
				}
			}
		};
		
		internalThread = new Thread( r, "SlideShow" );
		internalThread.start();		
		
	}
	
	private void buildSlides()
	{
		// request that the drawing be done with anti-aliasing on
		// and of high quality
		RenderingHints renderingHints = new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

		renderingHints.put( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );

		slides = new BufferedImage[20];
		
		Color rectColor = new Color( 100, 160, 250 );		// blue
		Color circleColor = new Color( 250, 250, 150 );		// yellow
		
		for( int i = 0; i < slides.length; i++ )
		{
			slides[i] = new BufferedImage( slideSize.width, slideSize.height, BufferedImage.TYPE_INT_RGB );
			
			Graphics2D g2 = slides[i].createGraphics();
			g2.setRenderingHints( renderingHints );
			
			g2.setColor( rectColor );
			g2.fillRect( 0, 0, slideSize.width, slideSize.height );
		
			g2.setColor( circleColor );
			
			int diameter = 0;
			if( i < (slides.length / 2 ) )
			{ 
				diameter = 5 + ( 8 * i );
			}
			else
			{
				diameter = 5 + ( 8 * ( slides.length - i ) );
			}
		
			int inset = ( slideSize.width - diameter ) / 2;
			g2.fillOval( inset, inset, diameter, diameter );
			
			g2.setColor( Color.black );
			g2.drawRect( 0, 0, slideSize.width - 1, slideSize.height - 1 );
		
			g2.dispose();
		}
	}
	
	public void paint( Graphics g )
	{
		g.drawImage(  slides[currSlide], 0, 0, this	);
	}
	
	private void runWork()
	{
		while( noStopRequested )
		{
			try
			{
				Thread.sleep(  250 );
				
				currSlide = ( currSlide + 1 ) % slides.length;
				
				// signal the event thread to call paint()
				repaint();
			}
			catch( InterruptedException e )
			{
				Thread.currentThread().interrupt();
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
		SlideShow ss = new SlideShow();
		
		JPanel p = new JPanel( new FlowLayout() );
		p.add( ss );
		
		JFrame f = new JFrame( "SlideShow demo" );
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
