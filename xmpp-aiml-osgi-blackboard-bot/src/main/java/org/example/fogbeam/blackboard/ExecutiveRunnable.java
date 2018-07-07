package org.example.fogbeam.blackboard;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;

import org.example.fogbeam.blackboard.agent.AIML_InterpreterAgent;
import org.example.fogbeam.blackboard.agent.AtCommandAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutiveRunnable implements Runnable,Observer 
{
	Logger logger = LoggerFactory.getLogger( ExecutiveRunnable.class );
	
	private volatile boolean keepRunning = true;
	
	private BlockingQueue<String> inputMessageQueue;
	private Blackboard blackboard = new Blackboard();		
	
	private String response = null;
	
	
	public ExecutiveRunnable( final BlockingQueue<String> inputMessageQueue )
	{
		this.inputMessageQueue = inputMessageQueue;
		this.blackboard.addObserver( new AtCommandAgent());
		this.blackboard.addObserver( new AIML_InterpreterAgent() );
		this.blackboard.addObserver( this );
	}
	
	public void offerMessage( final String message )
	{
		this.inputMessageQueue.offer(message);
	}
	
	
	// The executive has to monitor the blackboard and look for the
	// available responses.  If there's at least one frame that meets the minimum
	// threshold to send, use the highest scoring such frame for our response.
	// if not, keep waiting until the timeout to give up and say "I don't know" (or whatever)
	// additionally, the executive should always wait at least some minimum period to
	// respond, so that a barely-adequate response from a faster responding agent doesn't
	// trump a higher-scoring response that takes longer to arrive on the blackboard.

	
	public String getResponse()
	{
		return response;
	}
	
	@Override
	public void run() 
	{
		while( keepRunning )
		{
			try 
			{
				String input = inputMessageQueue.take();
				
				if( input == null || input.isEmpty() )
				{
					continue;
				}
				
				logger.info( "Executive received message: " + input );
				
				// is there a currently valid conversation?  If so, add this input to that
				// conversation, otherwise, start a new one
				
				// NOTE: do we *really* need the "Conversation" concept?  Could we just have
				// the Blackboard keep track of the messages and let it effectively
				// *be* the "Conversation"?  What does this buy us? 
				
				// The Conversation concept allows the bot to carry on multiple independent
				// conversations simultaneously.  But given this, how will we know which
				// conversation a given input message is meant for?  Hmm... things to ponder.
				// for now let's cheat and pretend there can only be one Conversation at a time.
	
				List<Conversation> extantConversations = blackboard.getExtantConversations();
				
				if( extantConversations == null || extantConversations.isEmpty() )
				{
					logger.info( "Starting new Conversation" );
					
					Conversation conversation = new Conversation();
					BlackboardFrame newFrame = new SimpleBlackboardFrame(input);
					newFrame.setSourceTag(this.getClass().getName());
					newFrame.setInput( true );
					
					blackboard.offer( conversation );
				
					conversation.addMessage( newFrame );
				}
				else
				{
					logger.info( "Found existing Conversation" );
					
					Conversation conversation = extantConversations.get(extantConversations.size()-1);
				
					BlackboardFrame newFrame = new SimpleBlackboardFrame(input);
					newFrame.setSourceTag(this.getClass().getName());
					newFrame.setInput( true );
					conversation.setPaused(false); // resume this conversation
					conversation.addMessage( newFrame );
				}
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		logger.info( "DispatcherRunnable stopped..." );
	}
	
	public void stop()
	{
		this.keepRunning = false;
	}

	
	
	/* all the real work happens here */
	protected void process( final Conversation conversation, final BlackboardFrame frame )
	{
		// TODO: find a way to ignore "left over" frames from an earlier part of the
		// conversation, that show up too late to be useful.  We don't want to accidentally
		// emit a response to something that has nothing to do with the current
		// percept. 
		
		// here the Executive monitors the Conversation and decides when something
		// suitable for output has appeared.
		
		// somebody also needs a way to mark a Conversation as
		// "completed" and remove it from the Blackboard
		
		List<BlackboardFrame> frames = conversation.getFrames();
		
		if( frames == null )
		{
			throw new RuntimeException( "no frames list in Conversation");
		}
		
		// walk backwards down this list until we encounter the first frame that
		// we sent.  If any frame in that subset is both marked for output
		// AND has a sufficiently high confidence score, then send it as
		// our response.
		
		BlackboardFrame currentHighScore = null;
		
		for( int i = ( frames.size() - 1 ); i >= 0; i-- )
		{
			BlackboardFrame candidateFrame = frames.get(i);
			
			if( candidateFrame == null )
			{
				throw new RuntimeException( "Error getting candidate frame from frame list!");
			}
			
			if( candidateFrame.getSourceTag().equals(this.getClass().getName()))
			{
				// we've reached a frame we sent, so stop
				if( currentHighScore != null && currentHighScore.getConfidence() > 35.0 )
				{
					logger.info( "ExecutiveRunnable - setting response!" );
					response = currentHighScore.getContents();
					conversation.setPaused(true);
				}
				
				break;
			}
			else
			{
				if( candidateFrame.isOutput() && currentHighScore == null )
				{
					currentHighScore = candidateFrame;
				}
				else if( candidateFrame.isOutput() && currentHighScore != null )
				{
					if( candidateFrame.getConfidence() > currentHighScore.getConfidence() )
					{
						logger.info( "swapping currentHighScore for candidateFrame" );
						currentHighScore = candidateFrame;
					}
				}
			}
		}
	}
	
	@Override
	public void update(Observable observable, Object arg) 
	{
		logger.info( "ExecutiveRunnable - got update from observable" );
		
		if( observable instanceof Blackboard && arg instanceof Conversation )
		{
			logger.info( "ExecutiveRunnable - registering as Observer for Conversation" );
			
			Conversation conversation = (Conversation)arg;
			conversation.addObserver(this);
		}
		else if( observable instanceof Conversation && arg instanceof BlackboardFrame )
		{
			logger.info( "ExecutiveRunnable - processing Conversation" );
			BlackboardFrame frame = (BlackboardFrame) arg;
			if( !frame.getSourceTag().equals(this.getClass().getName()))
			{
				process( (Conversation)observable, frame );
			}
		}
		else
		{
			throw new IllegalArgumentException( "update() received illegal parameter combination: " 
												 + observable.getClass().getName() + " | " 
												 + arg.getClass().getName() );
		}
		
		
	}	
}
