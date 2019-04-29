package org.fogbeam.example.pattern.observer;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

public class DefaultObserver implements Observer 
{
	private String id;
	
	public DefaultObserver()
	{
		this.id = UUID.randomUUID().toString();
	}
	
	@Override
	public void update(Observable o, Object arg) 
	{
		System.out.println( "update() called: this = " + id + ", o = " + o + ", arg = " + arg );
	}
}