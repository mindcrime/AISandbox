package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter10;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

public class ThreadViewerTableModel extends AbstractTableModel
{
	private Object dataLock;
	private int rowCount;
	private Object[][] cellData;
	private Object[][] pendingCellData;
	
	// the column information remains constant
	private final int columnCount;
	private final String[] columnName;
	private final Class[] columnClass;
	
	// for self-running object
	private Thread internalThread;
	private volatile boolean noStopRequested;
	
	public ThreadViewerTableModel()
	{
		rowCount = 0;
		cellData = new Object[0][0];
		
		String[] names = {
				"Priority", "Alive", "Daemon", "Interrupted", "ThreadGroup", "Thread Name"
		};
		columnName = names;
		
		Class[] classes = {
				Integer.class, Boolean.class, Boolean.class, Boolean.class, String.class, String.class
		};
		columnClass = classes;
		
		columnCount = columnName.length;
		
		dataLock = new Object();
		
		noStopRequested = true;
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
		
		internalThread = new Thread( r, "ThreadViewer" );
		internalThread.setPriority( Thread.MAX_PRIORITY - 2 );
		internalThread.setDaemon( true );
		internalThread.start();
	}
	

	private void runWork()
	{
		Runnable transferPending = new Runnable() 
		{
			@Override
			public void run()
			{
				transferPendingCellData();
				
				fireTableDataChanged();
			}
		};
		
		
		while( noStopRequested )
		{
			try
			{
				createPendingCellData();
				SwingUtilities.invokeAndWait( transferPending );
				Thread.sleep(  2500 );
			}
			catch( InvocationTargetException e )
			{
				e.printStackTrace();
				stopRequest();
			}
			catch( InterruptedException e )
			{
				Thread.currentThread().interrupt();
			}
		}
		
	}
	
	public boolean isAlive()
	{
		return internalThread.isAlive();
	}
	
	private void createPendingCellData()
	{
		Thread[] threads = findAllThreads();
		Object[][] cell = new Object[threads.length][columnCount];
		
		for( int i = 0; i < threads.length; i++ )
		{
			Thread t = threads[i];
			
			Object[] rowCell = cell[i];
			rowCell[0] = new Integer( t.getPriority() ); 
			rowCell[1] = new Boolean( t.isAlive() );
			rowCell[2] = new Boolean( t.isDaemon() );
			rowCell[3] = new Boolean( t.isInterrupted() );
			rowCell[4] = t.getThreadGroup().getName();
			rowCell[5] = t.getName(); 
		}
		
		synchronized( dataLock )
		{
			pendingCellData = cell;
		}
	}
	
	private void transferPendingCellData()
	{
		synchronized( dataLock )
		{
			cellData = pendingCellData;
			rowCount = cellData.length;
		}
	}
	
	public static Thread[] findAllThreads()
	{
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		
		ThreadGroup topGroup = group;
		
		// traverse the ThreadGroup tree
		while( group != null )
		{
			topGroup = group;
			group = group.getParent();
		}
		
		// create a destination group that is about twice as big as should be
		// needed to avoid any accidental clipping
		int estimatedSize = topGroup.activeCount() * 2;
		Thread[] slackList = new Thread[estimatedSize];
		
		// load the Thread references into the oversized array
		// the actual number of Threads is returned
		int actualSize = topGroup.enumerate( slackList );
		
		Thread[] list = new Thread[actualSize];
		
		System.arraycopy( slackList, 0, list, 0, actualSize );
		
		return list;
	}
	
	public void stopRequest()
	{
		noStopRequested = false;
		internalThread.interrupt();
	}

	
	@Override
	public int getRowCount()
	{
		return rowCount;
	}

	@Override
	public int getColumnCount()
	{
		return columnCount;
	}

	@Override
	public Object getValueAt( int rowIndex, int columnIndex )
	{
		return cellData[rowIndex][columnIndex];
	}	
	
	public Class getColumnClass( int columnIndex )
	{
		return columnClass[columnIndex];
	}
	
	public String getColumnName( int columnIndex )
	{
		return columnName[columnIndex];
	}
}
