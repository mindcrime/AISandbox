package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter9;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.ThreadPrint;

public class ScrollText extends JComponent implements ThreadPrint
{
	private BufferedImage image;
	private Dimension imageSize;
	private volatile int currOffset;
	
	private Thread internalThread;
	private volatile boolean noStopRequested;
	
	public ScrollText( String text )
	{
		currOffset = 0;
		buildImage( text );
		
		setMinimumSize( imageSize );
		setPreferredSize( imageSize );
		setMaximumSize( imageSize );
		setSize( imageSize );
	
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
		
		internalThread = new Thread( r, "ScrollText" );
		internalThread.start();
	}
	
	private void buildImage( String text )
	{
		// request that the drawing be done with anti-aliasing on
		// and of high quality
		RenderingHints renderingHints = new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

		renderingHints.put( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
		
		// create a scratch image for use in determining the text dimensions
		BufferedImage scratchImage = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_RGB );
		
		Graphics2D scratchG2 = scratchImage.createGraphics();
		scratchG2.setRenderingHints( renderingHints );
		
		Font font = new Font( "Serif", Font.BOLD | Font.ITALIC, 24 );
		
		FontRenderContext frc = scratchG2.getFontRenderContext();
		TextLayout tl = new TextLayout( text, font, frc );
		
		Rectangle2D textBounds = tl.getBounds();
		
		int textWidth = (int) Math.ceil( textBounds.getWidth() );
		int textHeight = (int) Math.ceil( textBounds.getHeight() );
	
		int horizontalPad = 10;
		int verticalPad = 6;
		
		imageSize = new Dimension( textWidth + horizontalPad, textHeight + verticalPad );
		
		// create the properly sized image
		image = new BufferedImage( imageSize.width, imageSize.height, BufferedImage.TYPE_INT_RGB );
		
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHints( renderingHints );
		
		int baselineOffset = ( verticalPad / 2 ) - ( (int) textBounds.getY() );
		
		g2.setColor( Color.white );
		g2.fillRect( 0, 0, imageSize.width, imageSize.height );
		
		g2.setColor( Color.blue );
		tl.draw( g2, 0, baselineOffset );
		
		// free up resources right away, but keep image for
		// animation
		scratchG2.dispose();
		scratchImage.flush();
		g2.dispose();
	
	}
	
	public void paint( Graphics g )
	{
		// make sure to clip the edges, regardless of current size
		g.setClip( 0, 0, imageSize.width, imageSize.height );
		
		int localOffset = currOffset; // in case it changes
		
		g.drawImage( image, -localOffset, 0, this );
		g.drawImage( image, imageSize.width - localOffset, 0, this	);
		
		g.setColor( Color.BLACK );
		g.drawRect( 0, 0, imageSize.width -1, imageSize.height -1 );
	
	}
	
	public void runWork()
	{
		while( noStopRequested )
		{
			try
			{
				Thread.sleep( 10 );
				
				// adjust the scroll position
				currOffset = ( currOffset + 3 ) % imageSize.width;
			
				// print( "calling repaint" );
				repaint();
			}
			catch( InterruptedException e )
			{
				Thread.currentThread().interrupt();
			}
		}
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
	
	public static void main( String[] args )
	{
		
		ScrollText st = new ScrollText( "Java can do animation!" );
		
		JPanel p = new JPanel( new FlowLayout() );
		p.add(  st );
		
		JFrame f = new JFrame( "ScrollText demo" );
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
