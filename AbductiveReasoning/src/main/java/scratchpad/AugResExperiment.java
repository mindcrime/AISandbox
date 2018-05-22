package scratchpad;

import java.util.LinkedHashSet;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.SetAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AugResExperiment 
{
	final static Logger logger = LoggerFactory.getLogger(AugResExperiment.class);
	
	public static void main( String[] args )
	{
		MutableSet<String> gsubi = SetAdapter.adapt( new LinkedHashSet<String>() );
		
		LinkedHashSet<String> hsub1BackingSet = new LinkedHashSet<String>();
		MutableSet<String> hsub1 = SetAdapter.adapt( hsub1BackingSet );
		hsub1.add( "d1" );
		hsub1.add( "d2" );
		
		
		logger.debug( "g_i - H_1 = " + gsubi.difference( hsub1 ) );
		
		MutableSet<String> unioned = gsubi.union( SetAdapter.adapt( new LinkedHashSet<String>() ) );
		logger.debug( "unioned = " + unioned );
		
		MutableSet<String> A = hsub1.difference( unioned );
		
		logger.debug( "( A = hsub1 - unioned ) = " + A );
		
	}
}
