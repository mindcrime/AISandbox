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

public class BalanceLookupCantCancel extends JPanel
{
	private JTextField acctTF;
	private JTextField pinTF;
	private JButton searchB;
	private JButton cancelB;
	private JLabel balanceL;
	
	public BalanceLookupCantCancel()
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
		
		String bal = lookupBalance( acct, pin );
		setBalance( bal );
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
	
	private void setBalance( String balance )
	{
		// better be called by EventThread!
		balanceL.setText( balance );
		cancelB.setEnabled( false );
		searchB.setEnabled( true );
	}
	
	private void cancelSearch()
	{
		System.out.println( "in cancelSearch" );
		
		// here's where the code to cancel would go if
		// this could ever be called
	}
	
	
	public static void main( String[] args )
	{
		BalanceLookupCantCancel bl = new BalanceLookupCantCancel();
		
		JFrame f = new JFrame( "Balance Lookup - Can't Cancel" );
		
		f.addWindowListener( new WindowAdapter() 
			{
				public void windowClosing(WindowEvent e) 
				{
					System.exit( 0 );
				};
		} );
		
		f.setContentPane( bl );
		f.setSize( 400, 150 );
		f.setVisible( true );
		
		System.out.println( "done" );
	}
}
