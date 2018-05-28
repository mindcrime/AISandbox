package scratchpad;

import java.util.LinkedHashSet;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.SetAdapter;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;

public class ToStringMain 
{

	public static void main(String[] args) 
	{
		MutableSet<MutableSet<String>> explanationSets = SetAdapter.adapt( new LinkedHashSet<MutableSet<String>>() );
		
		MutableSet<String> anExplanation = new UnifiedSet<String>();
		anExplanation.add( "d1" );
		anExplanation.add( "d2" );
		
		MutableSet<String> anotherExplanation = new UnifiedSet<String>();
		anotherExplanation.add( "d4" );
		anotherExplanation.add( "d6" );
		
		
		explanationSets.add( anExplanation );
		explanationSets.add( anotherExplanation );
		
		System.out.println( explanationSets.toString() );
		
		System.out.println( explanationSets.makeString( "{ ", " | ", " }" ));
		
		System.out.println( "done" );

	}

}
