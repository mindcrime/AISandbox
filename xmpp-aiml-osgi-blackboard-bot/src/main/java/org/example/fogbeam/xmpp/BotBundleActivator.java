package org.example.fogbeam.xmpp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.example.fogbeam.blackboard.ExecutiveRunnable;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.TLSUtils;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class BotBundleActivator implements BundleActivator
{
	
	AbstractXMPPConnection connection = null;
	
	@Override
	public void start(BundleContext context) throws Exception 
	{
		Properties props = new Properties();

		InputStream stream = new FileInputStream( "conf/aiml-xmpp-bot.properties" );
		props.load(stream);
		
		
		XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
		configBuilder.setUsernameAndPassword( props.getProperty("username"), props.getProperty( "password" ) );
		configBuilder.setResource("SomeResource");
		configBuilder.setServiceName("fogbeam.org");
		configBuilder.setHost( "www.fogbeam.org" );
		
		// accept all certificate - just for testing  
		try 
		{  
		    TLSUtils.acceptAllCertificates(configBuilder);  
		} 
		catch (NoSuchAlgorithmException e) {  
		
		} 
		catch (KeyManagementException e) {  
		
		}  
		
		connection = new XMPPTCPConnection(configBuilder.build());
		
	
	
		
		XmppMessageListener connectionListener = new XmppMessageListener();

		// make sure auto reconnect is turned on.
		ReconnectionManager.getInstanceFor(connection).enableAutomaticReconnection();
		
		BlockingQueue<String> inputMessageQueue = new LinkedBlockingQueue<String>();
		
		// ExecutiveRunnable blackboardExecutive = new ExecutiveRunnable( inputMessageQueue );
		// Thread blackboardThread = new Thread( blackboardExecutive );
		// blackboardThread.start();
		
		try
		{				
			System.out.println( "Connecting...");
				
					
			connection.addConnectionListener( connectionListener );
			
			// Connect to the server
			connection.connect();
	
			// Log into the server
			connection.login();
			System.out.println( "logged in to XMPP" );
			
			ChatManager chatManager = ChatManager.getInstanceFor(connection);
			BlackboardXmppChatListener chatListener = new BlackboardXmppChatListener( inputMessageQueue );
			chatManager.addChatListener(chatListener);	
		
			System.out.println( "bot running..." );
			
		}
		catch( Exception e )
		{
			System.err.println( "Uh oh... something broke!");
			e.printStackTrace();
		}
			
	}
	
	@Override
	public void stop(BundleContext context) throws Exception 
	{
		System.out.println( "Stopping bot...");
		connection.disconnect();
	}
}
