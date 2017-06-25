package org.example.fogbeam.xmpp;

import java.util.concurrent.BlockingQueue;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;

public class BlackboardXmppChatListener implements ChatManagerListener
{
	private BlockingQueue<String> inputMessageQueue;
	
	public BlackboardXmppChatListener( final BlockingQueue<String> inputMessageQueue )
	{
		this.inputMessageQueue = inputMessageQueue;
	}
	
	@Override
	public void chatCreated(Chat chat, boolean createdLocally)
	{
		if (!createdLocally)
		{
			chat.addMessageListener(new Blackboard_MessageListener(this.inputMessageQueue));
		}
	}
}
