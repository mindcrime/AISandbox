package org.example.fogbeam.blackboard;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Blackboard 
{
	private BlockingQueue<Conversation> blackboardQueue = new LinkedBlockingQueue<Conversation>();
	
	public Conversation take() throws InterruptedException
	{
		return blackboardQueue.take();
	}
	
}
