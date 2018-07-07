package org.example.fogbeam.xmpp;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class XmppMessageListener implements ConnectionListener
{
	Logger logger = LoggerFactory.getLogger( XmppMessageListener.class );
	
	boolean m_reconnect = false;
	
	public boolean isReconnect()
	{
		return m_reconnect;
	}
	
	@Override
	public void connectionClosedOnError(Exception arg0) {
		logger.info( "connectionClosedOnError called!");
		m_reconnect = true;
		Thread.currentThread().interrupt();
	}

	@Override
	public void authenticated(XMPPConnection arg0, boolean arg1) {
		logger.info( "authenticated called!");
		
	}

	@Override
	public void connected(XMPPConnection arg0) {
		logger.info( "connected called!");
		
	}

	@Override
	public void connectionClosed() {
		logger.info( "connectionClosed called!");
		
	}

	@Override
	public void reconnectingIn(int arg0) {
		logger.info( "reconnectingIn called!");
		
	}

	@Override
	public void reconnectionFailed(Exception arg0) {
		logger.info( "reconnectionFailed called!");
		
	}

	@Override
	public void reconnectionSuccessful() {
		logger.info( "reconnectionSuccessful called!");
		
	}
};		

