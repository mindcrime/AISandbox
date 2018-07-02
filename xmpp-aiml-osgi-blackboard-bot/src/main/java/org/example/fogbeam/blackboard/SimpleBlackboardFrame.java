package org.example.fogbeam.blackboard;

public class SimpleBlackboardFrame implements BlackboardFrame 
{
	private String contents;
	private String sourceTag;
	private double confidence;
	boolean input;
	boolean output;
	
	public SimpleBlackboardFrame( String contents )
	{
		this.contents = contents;
	}
	
	@Override
	public String getContents() 
	{
		return contents;
	}
	
	@Override
	public void setContents(String contents) 
	{
		this.contents = contents;
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
	
	
	
}