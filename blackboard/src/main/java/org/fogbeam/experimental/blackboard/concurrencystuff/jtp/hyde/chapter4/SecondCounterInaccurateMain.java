package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter4;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SecondCounterInaccurateMain extends JPanel
{
	
	private static final long serialVersionUID = 1L;
	
	private SecondCounterInaccurate sci;
	private Button startButton;
	private Button stopButton;
	
	public SecondCounterInaccurateMain()
	{
		sci = new SecondCounterInaccurate();
		startButton = new Button( "Start" );
		stopButton = new Button( "Stop" );
		
		stopButton.setEnabled( false );
		
		startButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e )
			{
				// disable to stop more "start" requests
				startButton.setEnabled( false );
				
				Thread counterThread = new Thread( sci, "Counter Thread" );
				counterThread.start();
				
				stopButton.setEnabled( true );
				stopButton.requestFocus();
			}
		} );
		
		stopButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e )
			{
				stopButton.setEnabled( false );
				sci.stopClock();
				startButton.setEnabled( true );
				startButton.requestFocus();
			}
			
		} );
		
		JPanel innerButtonPanel = new JPanel();
		innerButtonPanel.setLayout( new GridLayout( 0, 1, 0, 3 ) );
		innerButtonPanel.add( startButton );
		innerButtonPanel.add( stopButton );
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout( new BorderLayout() );
		buttonPanel.add(  innerButtonPanel, BorderLayout.NORTH );
		
		this.setLayout( new BorderLayout( 10, 10 ) );
		this.setBorder( new EmptyBorder( 20, 20, 20, 20 ) );
		this.add(  buttonPanel, BorderLayout.WEST );
		this.add(  sci, BorderLayout.CENTER );
		
	}
	
	
	public static void main(String[] args) 
	{
		SecondCounterInaccurateMain scrMain = new SecondCounterInaccurateMain();
		
		JFrame f = new JFrame( "Second Counter Runnable" );
		
		f.setContentPane( scrMain );
		f.setSize( 320, 200 );
		f.setVisible( true );
		f.addWindowListener( new WindowAdapter()
			{
			 	public void windowClosing(java.awt.event.WindowEvent e) 
			 	{
			 		System.exit( 0 );
			 	};
			});
		
		System.out.println( "done" );
	}
}
