package org.example.fogbeam.blackboard;

public interface BlackboardFrame 
{
	/* Non FIPA-ACL fields */		
	
	public void setInput( boolean input );
	public boolean isInput();

	public void setOutput( boolean output );
	public boolean isOutput();
	
	public void setConfidence( double confidence );
	public double getConfidence();

	/* SourceChannel would be something like (Auditory|Visual|Haptic|etc)
	 * including "channels" for various kinds of sensors we might include
	 * in an embodied bot. Humidity sensor, temperature sensor, ultrasonic
	 * range detector, etc.  Also, should include something like "autonomic"
	 * or "internal" for "self talk"
	 */
	public void setSourceChannel( String sourceChannel );
	public String getSourceChannel();
	
	/* can replace with FIPA-ACL 'sender' field?? Or
	 * does this mean something else... */
	public void setSourceTag( String sourceTag );
	public String getSourceTag();
	
	/* FIPA_ACL fields */
		
	public String getPerformative();
	public void setPerformative(String performative);
	
	public String getSender();
	public void setSender( String sender );

	public String getReceiver();
	public void setReceiver( String receiver );
	
	public String getReplyTo();
	public void setReplyTo( String replyTo );
	
	public String getContent();
	public void setContent(String content);

	public String getLanguage();
	public void setLanguage( String language );
	
	public String getEncoding();
	public void setEncoding( String encoding );
	
	public String getOntology();
	public void setOntology(String ontology);
	
	public String getProtocol();
	public void setProtocol( String protocol );
	
	public String getConversationId();
	public void setConversationId( String conversationId );
	
	public String getReplyWith();
	public void setReplyWith( String replyWith );
	
	public String getInReplyTo();
	public void setInReplyTo( String inReplyTo );
	
	public String getReplyBy();
	public void setReplyBy(String replyBy);
	
}
