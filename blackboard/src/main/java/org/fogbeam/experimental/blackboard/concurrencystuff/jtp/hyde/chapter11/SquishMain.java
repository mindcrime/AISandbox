package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter11;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SquishMain extends JPanel
{
	public SquishMain()
	{
		Squish redSquish = new Squish( 150, 150,  2500L, 15, Color.RED );
		Squish blueSquish = new Squish( 250, 200, 2500L, 15, Color.BLUE );
		
		this.setLayout( new FlowLayout() );
		this.add( redSquish );
		this.add( blueSquish );
	}
	
	public static void main( String[] args )
	{
		SquishMain sm = new SquishMain();
		
		JFrame f = new JFrame( "Squish Main" );
		f.setContentPane( sm );
		f.setSize( 450, 250 );
		f.setVisible( true );
		
		f.addWindowListener( new WindowAdapter() 
		{
			public void windowClosing(java.awt.event.WindowEvent e) 
			{
				System.out.println( "done" );
				
				try
				{
					Thread.sleep(  1500 );
				}
				catch( InterruptedException e1 )
				{
				}
				
				System.exit( 0 );
			};
		} );
		
		
	}
}
