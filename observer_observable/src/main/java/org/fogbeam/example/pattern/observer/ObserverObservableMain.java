package org.fogbeam.example.pattern.observer;

import java.util.Observable;

public class ObserverObservableMain 
{

	public static void main(String[] args) throws Exception
	{
		DefaultObservable server = new DefaultObservable("DefaultServer");
		
		server.addObserver( new DefaultObserver() );
		server.addObserver( new DefaultObserver() );
		server.addObserver( new DefaultObserver() );
		server.addObserver( new DefaultObserver() );
	
		
		for( int i = 0; i < 10; i++ )
		{
			server.setChanged();
			server.notifyObservers( i );
			
			System.out.println( "\n\n" );
			
			Thread.sleep( 3000 );
			
		}
		
		System.out.println( "done" );
	}

}
