package org.fogbeam.experimental.reasoning.tangential;

import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

public class IntersectionMain 
{

	public static void main(String[] args) 
	{
	
		IntersectionMain main = new IntersectionMain();
		main.run();

		System.out.println( "done" );
		
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
		
		
		Set intersection = uSet1.intersect(uSet2);
		
		System.out.println( "intersection: " + intersection );
		
	}
	
}
