package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter9;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class BalanceLookup extends JPanel
{
	private JTextField acctTF;
	private JTextField pinTF;
	private JButton searchB;
	private JButton cancelB;
	private JLabel balanceL;
	
	private volatile Thread lookupThread;
	
	public BalanceLookup()
	{
		buildGUI();
		hookupEvents();
	}
	
	private void buildGUI()
	{
		JLabel acctL = new JLabel( "Account Number:" );
		JLabel pinL = new JLabel( "PIN: " );
		
		acctTF = new JTextField( 12 );
		pinTF = new JTextField( 4 );
		
		JPanel dataEntryP = new JPanel();
		dataEntryP.setLayout( new FlowLayout( FlowLayout.CENTER ) );
		dataEntryP.add( acctL );
		dataEntryP.add( acctTF );
		dataEntryP.add( pinL );
		dataEntryP.add( pinTF );
		
		searchB = new JButton( "Search" );
		cancelB = new JButton( "Cancel Search" );
		cancelB.setEnabled( false );
		
		JPanel innerButtonP = new JPanel();
		innerButtonP.setLayout( new GridLayout( 1, -1, 5, 5 ) );
		
		innerButtonP.add(  searchB );
		innerButtonP.add(  cancelB );
		
		JPanel buttonP = new JPanel();
		buttonP.setLayout( new FlowLayout( FlowLayout.CENTER ) );
		buttonP.add(  innerButtonP );
		
		JLabel balancePrefixLbl = new JLabel( "Account Balance:" );
		balanceL = new JLabel( "Balance Unknown" );
		
		JPanel balanceP = new JPanel();
		balanceP.setLayout( new FlowLayout( FlowLayout.CENTER ) );
		balanceP.add(  balancePrefixLbl );
		balanceP.add(  balanceL );
		
		JPanel northP = new JPanel();
		northP.setLayout(  new GridLayout( -1, 1, 5, 5 ) );
		northP.add(  dataEntryP );
		northP.add(  buttonP );
		northP.add(  balanceP );
		
		setLayout( new BorderLayout() );
		add( northP, BorderLayout.NORTH );
	}
	
	private void hookupEvents()
	{
		searchB.addActionListener( new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				search();
			};
			
		} );
	
		cancelB.addActionListener( new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				cancelSearch();
			};
			
		} );
	}

	private void search()
	{
		searchB.setEnabled( false );
		cancelB.setEnabled( true );
		
		balanceL.setText( "Searching..." );
		
		// get a snapshot of this info in case it changes
		String acct = acctTF.getText();
		String pin = pinTF.getText();

		
		// String bal = lookupBalance( acct, pin );
		// setBalance( bal );
	
		lookupAsync( acct, pin );
	}
	
	private void lookupAsync( String acct, String pin )
	{
		// called by event thread, but can safely be called by any thread
		
		Runnable lookupRun = new Runnable() 
		{
			@Override
			public void run()
			{
				String bal = lookupBalance( acct, pin );
				setBalanceSafely( bal );
			}
		};
		
		lookupThread = new Thread( lookupRun, "lookupThread" );
		lookupThread.start();
	}
	
	private String lookupBalance( String acct, String pin )
	{
		// simulate a lengthy search that takes 5 seconds
		try
		{
			Thread.sleep( 5000 );
			
			// result "retrieved", return it
			return "1,234.56";
		}
		catch( InterruptedException x )
		{
			return "SEARCH CANCELLED";
		}
	}
	
	private void setBalanceSafely( String balance )
	{
		// called by lookup thread, but can safely be called
		// by any thread
		
		final String newBalance = balance;
		
		Runnable r = new Runnable() 
		{
			@Override
			public void run()
			{
				setBalance( newBalance );
			}
		};
		
		SwingUtilities.invokeLater( r );
	}
	
	private void setBalance( String balance )
	{
		// better be called by EventThread!
		ensureEventThread();
		
		balanceL.setText( balance );
		cancelB.setEnabled( false );
		searchB.setEnabled( true );
	}
	
	private void ensureEventThread()
	{
		// throws an Exception if not invoked by the Event Thread
		if( SwingUtilities.isEventDispatchThread() )
		{
			return;
		}
		else
		{
			throw new RuntimeException( "Only the event thread should invoke this method" );
		}
	}
	
	private void cancelSearch()
	{
		System.out.println( "in cancelSearch" );
		
		ensureEventThread();
		
		cancelB.setEnabled( false ); // prevent additional cancels
		
		if( lookupThread != null )
		{
			lookupThread.interrupt();
		}
	}
	
	
	public static void main( String[] args )
	{
		BalanceLookup bl = new BalanceLookup();
		
		JFrame f = new JFrame( "Balance Lookup" );
		
		f.addWindowListener( new WindowAdapter() 
			{
				public void windowClosing(WindowEvent e) 
				{
					System.out.println( "Exiting..." );
					System.exit( 0 );
				};
		} );
		
		f.setContentPane( bl );
		f.setSize( 400, 150 );
		f.setVisible( true );
		
		System.out.println( "done" );
	}
}
