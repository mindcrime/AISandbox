package org.fogbeam.experimental.reasoning.tangential;

import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnionMain 
{
	final static Logger logger = LoggerFactory.getLogger(UnionMain.class);
	
	public static void main( String[] args ) 
	{
		UnionMain main = new UnionMain();
		
		main.run();

		logger.debug( "done" );
		
	}

	public void run()
	{
		UnifiedSet<String> uSet1 = new UnifiedSet<String>();
		uSet1.add( "a" );
		uSet1.add( "b" );
		uSet1.add( "c" );
		
		UnifiedSet<String> uSet2 = new UnifiedSet<String>();
		uSet2.add( "c" );
		uSet2.add( "d" );
		uSet2.add( "e" );
		
		Set<String> union = uSet1.union( uSet2 );
		
		logger.debug( "union: " + union );
		
	}
}
