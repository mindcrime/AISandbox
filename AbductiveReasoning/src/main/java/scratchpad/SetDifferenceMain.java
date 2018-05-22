package scratchpad;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetDifferenceMain 
{
	final static Logger logger = LoggerFactory.getLogger(SetDifferenceMain.class);
	
	public static void main(String[] args) 
	{
		
		UnifiedSet<String> A = new UnifiedSet<String>();
		UnifiedSet<String> B = new UnifiedSet<String>();
		
		A.add( "A" );
		A.add( "B" );
		A.add( "C" );
		A.add( "D" );
		A.add( "E" );
		
		
		B.add( "B" );
		B.add( "C" );
		
		UnifiedSet<String> C = (UnifiedSet<String>) A.difference(B);
		
		logger.debug( "C = " + C );
		
		logger.debug( "done" );

	}

}
