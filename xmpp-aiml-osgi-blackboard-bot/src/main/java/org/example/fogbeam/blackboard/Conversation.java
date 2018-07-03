package org.example.fogbeam.blackboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.example.fogbeam.blackboard.agent.BlackboardAgent;

public class Conversation extends Observable
{	
	/* TODO: should we have a "mode" flag that lets us toggle
	 * something like a specific "teaching" mode, as a shortcut?
	 * The idea is to have a mode where we are just sending direct commands
	 * to the bot, which will be recognized by an agent that does
	 * something that doens't generate any response, so there's no
	 * need for interactivity.  
	 */
	
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
