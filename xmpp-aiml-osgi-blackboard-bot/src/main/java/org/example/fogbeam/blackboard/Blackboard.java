package org.example.fogbeam.blackboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Blackboard extends Observable
{
	private BlockingQueue<Conversation> blackboardQueue = new LinkedBlockingQueue<Conversation>();	
	private List<Conversation> extantConversations = new ArrayList<Conversation>();
	
	
	public List<Conversation> getExtantConversations() 
	{
		return extantConversations;
	}
	
	
	public void offer( final Conversation conversation )
	{
		blackboardQueue.offer(conversation);
		this.setChanged();
		
		this.extantConversations.add(conversation);		
		this.notifyObservers(conversation);

	}

	
	private Conversation take() throws InterruptedException
	{
		Conversation conversation = blackboardQueue.take();
		return conversation;
	}

	
	/* 
	public void run() throws InterruptedException
	{
		while( true )
		{
			try 
			{
				Conversation conversation = take();
				
				// we may be able to delete this, since the agent will register itself
				// as an observer of the Conversation once it sees it
				this.extantConversations.add(conversation);
				
				this.notifyObservers(conversation);
				
				// slow things down while we're working out the mechanics of how all this
				// works
				Thread.sleep( 500 );
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}	
	}
	*/
}
