package org.fogbeam.experimental.reasoning.tangential;

import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

public class UnionMain 
{
	public static void main( String[] args ) 
	{
		UnionMain main = new UnionMain();
		
		main.run();

		System.out.println( "done" );
		
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
		
		Set union = uSet1.union( uSet2 );
		
		System.out.println( "union: " + union );
		
	}
}
