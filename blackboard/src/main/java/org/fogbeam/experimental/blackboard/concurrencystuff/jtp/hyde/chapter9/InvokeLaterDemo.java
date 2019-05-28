package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter9;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.ThreadPrint;

public class InvokeLaterDemo implements ThreadPrint
{
	public static void main( String[] args )
	{
		InvokeLaterDemo demo = new InvokeLaterDemo();
		demo.run();
	}
	
	public void run()
	{
		final JLabel label = new JLabel( "----");
		
		JPanel panel = new JPanel( new FlowLayout() );
		panel.add(  label );
		
		JFrame f = new JFrame( "InvokeLaterDemo" );
		f.setContentPane( panel );
		f.setSize( 300, 100 );
		f.setVisible( true );
		
		try
		{
			print( "Sleeping for 3 seconds..." );
		
			Thread.sleep(  6000 );
			
			print( "creating code block for event thread");
		
			
			Runnable setTextRun = new Runnable() 
			{
				
				@Override
				public void run()
				{
					try
					{
						Thread.sleep(  8000 ); // for emphasis
						
						print( "about to do setText()" );
						label.setText(  "New text!!" );
						print( "done with setText()");
					}
					catch( Exception e )
					{}
				}
			};
			
			print( "about to invokeLater()");
			SwingUtilities.invokeLater( setTextRun );
			print( "back from invokeLater()");
			
		}
		catch( InterruptedException e )
		{
			print( "interrupted while waiting on invokeAndWait()" );
		}
		
		print( "done" );
	}
}
