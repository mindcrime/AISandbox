package org.example.fogbeam.blackboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.example.fogbeam.blackboard.agent.BlackboardAgent;

public class Conversation extends Observable
{	
	private boolean paused;
	
	private List<BlackboardFrame> frames = new ArrayList<BlackboardFrame>();
	
	public void addMessage( final BlackboardFrame message )
	{
		System.out.println( "Conversation - got new BlackboardFrame" );
		
		frames.add( message );
		this.setChanged();
		
		// notify all the agents that the conversation has been modified
		this.notifyObservers(message);
	}
	
	public List<BlackboardFrame> getFrames() 
	{
		return frames;
	}

	public void forceUpdate() 
	{
		this.setChanged();
		
		// notify all the agents that the conversation has been modified
		this.notifyObservers();
	}
	
	public boolean isPaused() 
	{
		return paused;
	}
	
	public void setPaused(boolean paused) 
	{
		this.paused = paused;
	}
}
