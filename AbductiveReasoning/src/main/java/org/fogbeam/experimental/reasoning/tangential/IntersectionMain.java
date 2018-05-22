package org.fogbeam.experimental.reasoning.tangential;

import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntersectionMain 
{
	final static Logger logger = LoggerFactory.getLogger(IntersectionMain.class);

	public static void main(String[] args) 
	{
	
		IntersectionMain main = new IntersectionMain();
		main.run();

		logger.debug( "done" );
		
	}

	public void run()
	{
		UnifiedSet<String> uSet1 = new UnifiedSet<String>();
		uSet1.add( "a" );
		uSet1.add( "b" );
		uSet1.add( "c" );
		uSet1.add( "q" );
		
		UnifiedSet<String> uSet2 = new UnifiedSet<String>();
		uSet2.add( "c" );
		uSet2.add( "d" );
		uSet2.add( "e" );
		uSet2.add( "q" );
		
		
		Set<String> intersection = uSet1.intersect(uSet2);
		
		logger.debug( "intersection: " + intersection );
		
	}
	
}
