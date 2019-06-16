package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter12;

public class ExceptionCallbackMain implements ExceptionListener
{
	private int exceptionCount = 0;
	
	
	@Override
	public void exceptionOccurred( Exception x, Object source )
	{
		exceptionCount++;
		System.err.println( "EXCEPTION # " + exceptionCount + ", source = " + source );
		x.printStackTrace();		
	}
	
	public static void main( String[] args )
	{
		ExceptionListener listener = new ExceptionCallbackMain();
		ExceptionCallback ec = new ExceptionCallback( listener );
		
		while( ec.isAlive() )
		{
			try
			{
				Thread.sleep( 1000 );
			}
			catch( Exception e )
			{}
		}
		
		System.out.println( "done" );
	}

}
