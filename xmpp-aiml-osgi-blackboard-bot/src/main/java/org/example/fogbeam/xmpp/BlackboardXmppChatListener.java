package org.example.fogbeam.xmpp;

import java.util.concurrent.BlockingQueue;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlackboardXmppChatListener implements ChatManagerListener
{
	Logger logger = LoggerFactory.getLogger( BlackboardXmppChatListener.class );
	
	private BlockingQueue<String> inputMessageQueue;
	
	public BlackboardXmppChatListener( final BlockingQueue<String> inputMessageQueue )
	{
		logger.info( "instantiating BlackboardXmppChatListener");
		this.inputMessageQueue = inputMessageQueue;
	}
	
	@Override
	public void chatCreated(Chat chat, boolean createdLocally)
	{
		if (!createdLocally)
		{
			logger.info( "adding Blackboard_MessageListener");
			
			chat.addMessageListener(new Blackboard_MessageListener(this.inputMessageQueue));
		}
	}
}
