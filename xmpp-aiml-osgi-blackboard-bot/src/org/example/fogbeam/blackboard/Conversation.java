package org.example.fogbeam.blackboard;

import java.util.ArrayList;
import java.util.List;

public class Conversation 
{
	
	private List<String> messages = new ArrayList<String>();
	
	public void addMessage( final String message )
	{
		messages.add( message );
	}
	
	
	
}
