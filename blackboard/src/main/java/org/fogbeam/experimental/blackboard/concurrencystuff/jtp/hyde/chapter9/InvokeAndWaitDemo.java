package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter9;

import java.awt.FlowLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.ThreadPrint;

public class InvokeAndWaitDemo implements ThreadPrint
{
	public static void main( String[] args )
	{
		InvokeAndWaitDemo demo = new InvokeAndWaitDemo();
		demo.run();
	}
	
	public void run()
	{
		final JLabel label = new JLabel( "----");
		
		JPanel panel = new JPanel( new FlowLayout() );
		panel.add(  label );
		
		JFrame f = new JFrame( "InvokeAndWaitDemo" );
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
					print( "about to do setText()" );
					label.setText(  "New text!!" );
				}
			};
			
			print( "about to invokeAndWait()");
			SwingUtilities.invokeAndWait( setTextRun );
			print( "back from invokeAndWait()");
			
		}
		catch( InterruptedException e )
		{
			print( "interrupted while waiting on invokeAndWait()" );
		}
		catch( InvocationTargetException x )
		{
			print( "Exception thrown from run()" );
		}
		
		print( "done" );
	}
}
