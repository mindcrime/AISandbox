package org.example.fogbeam.xmpp;

import java.util.concurrent.BlockingQueue;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;

public class BlackboardXmppChatListener implements ChatManagerListener
{
	private BlockingQueue<String> inputMessageQueue;
	
	public BlackboardXmppChatListener( final BlockingQueue<String> inputMessageQueue )
	{
		System.out.println( "instantiating BlackboardXmppChatListener");
		this.inputMessageQueue = inputMessageQueue;
	}
	
	@Override
	public void chatCreated(Chat chat, boolean createdLocally)
	{
		if (!createdLocally)
		{
			System.out.println( "adding Blackboard_MessageListener");
			
			chat.addMessageListener(new Blackboard_MessageListener(this.inputMessageQueue));
		}
	}
}
