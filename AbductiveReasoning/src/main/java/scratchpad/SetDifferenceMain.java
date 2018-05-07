package scratchpad;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

public class SetDifferenceMain 
{

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
		
		System.out.println( "C = " + C );
		
		System.out.println( "done" );

	}

}
