package org.fogbeam.example.pattern.observer;

import java.util.Observable;

public class DefaultObservable extends Observable 
{
	private String id;
	
	public DefaultObservable( final String id )
	{
		this.id = id;
	}
	
	public void setChanged()
	{
		super.setChanged();
	}
	
	@Override
	public String toString() 
	{
		return this.id;
	}
}
