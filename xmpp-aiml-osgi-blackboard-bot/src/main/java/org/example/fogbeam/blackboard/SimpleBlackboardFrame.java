package org.example.fogbeam.blackboard;

public class SimpleBlackboardFrame implements BlackboardFrame 
{
	/* Non FIPA-ACL fields */
	private String sourceTag;
	private double confidence;
	boolean input;
	boolean output;
	
	/* FIPA-ACL fields */
	private String performative;
	private String sender;
	private String receiver;
	private String replyTo;
	private String content;
	private String language;
	private String encoding;
	private String ontology;
	private String protocol;
	private String conversationId;
	private String replyWith;
	private String inReplyTo;
	private String replyBy;
	
	
	
	
	public SimpleBlackboardFrame( String content )
	{
		this.content = content;
	}
		
	@Override
	public String getSourceTag() 
	{
		return this.sourceTag;
	}
	
	@Override
	public void setSourceTag(String sourceTag) 
	{
		this.sourceTag = sourceTag;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) 
	{
		this.confidence = confidence;
	}

	public boolean isInput() 
	{
		return input;
	}

	public void setInput(boolean input) 
	{
		this.input = input;
	}

	public boolean isOutput() 
	{
		return output;
	}

	public void setOutput(boolean output) 
	{
		this.output = output;
	}
	
	public String getPerformative() 
	{
		return performative;
	}

	public void setPerformative(String performative) 
	{
		this.performative = performative;
	}

	public String getSender() 
	{
		return sender;
	}

	public void setSender(String sender) 
	{
		this.sender = sender;
	}

	public String getReceiver() 
	{
		return receiver;
	}

	public void setReceiver(String receiver) 
	{
		this.receiver = receiver;
	}

	public String getReplyTo() 
	{
		return replyTo;
	}

	public void setReplyTo(String replyTo) 
	{
		this.replyTo = replyTo;
	}

	public String getLanguage() 
	{
		return language;
	}

	public void setLanguage(String language) 
	{
		this.language = language;
	}

	public String getEncoding() 
	{
		return encoding;
	}

	public void setEncoding(String encoding) 
	{
		this.encoding = encoding;
	}

	public String getOntology() 
	{
		return ontology;
	}

	public void setOntology(String ontology) 
	{
		this.ontology = ontology;
	}

	public String getProtocol() 
	{
		return protocol;
	}

	public void setProtocol(String protocol) 
	{
		this.protocol = protocol;
	}

	public String getConversationId() 
	{
		return conversationId;
	}

	public void setConversationId(String conversationId) 
	{
		this.conversationId = conversationId;
	}

	public String getReplyWith() 
	{
		return replyWith;
	}

	public void setReplyWith(String replyWith) 
	{
		this.replyWith = replyWith;
	}

	public String getInReplyTo() 
	{
		return inReplyTo;
	}

	public void setInReplyTo(String inReplyTo) 
	{
		this.inReplyTo = inReplyTo;
	}

	public String getReplyBy() 
	{
		return replyBy;
	}

	public void setReplyBy(String replyBy) 
	{
		this.replyBy = replyBy;
	}

	@Override
	public String getContent() 
	{
		return content;
	}
	
	@Override
	public void setContent(String content) 
	{
		this.content = content;
	}

	
}