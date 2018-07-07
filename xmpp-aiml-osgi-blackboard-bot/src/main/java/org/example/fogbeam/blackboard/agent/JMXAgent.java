package org.example.fogbeam.blackboard.agent;

import org.example.fogbeam.blackboard.BlackboardFrame;
import org.example.fogbeam.blackboard.Conversation;

/* an agent that uses JMX to introspect the running JVM and
   any other registered MBeans.  The closest thing we have
   so far to the idea of a hard-coded "self node". 

   An open question is, can the sytem learn to associate a notion of 
   "self" with the information this Agent can collect, so we can ask it,
   for example, "How much memory are you using?"
*/
public class JMXAgent extends SimpleBlackboardAgent 
{

	@Override
	protected void process(Conversation conversation, BlackboardFrame frame) 
	{
		// TODO Auto-generated method stub
		
	}

}
