package org.example.fogbeam.blackboard;

public interface BlackboardFrame 
{

	public String getContents();
	public void setContents(String contents);
	
	public void setSourceTag( String sourceTag );
	public String getSourceTag();
	
	public void setInput( boolean input );
	public boolean isInput();

	public void setOutput( boolean output );
	public boolean isOutput();
	
	public void setConfidence( double confidence );
	public double getConfidence();


}
