package org.fogbeam.experimental.reasonining.tangential;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PowerSetExp 
{

	public static void main(String[] args) 
	{

		String[] aSet =  { "a", "b", "c", "d", "e", "f" };
		
		Set<Set<String>> subsets = new HashSet<Set<String>>();
		
		int desiredCardinality = 3;
		int added = 0;
		List<Integer> skipList = new ArrayList<Integer>();
		
		System.out.println( "( aSet.length - desiredCardinality ) = " + ( aSet.length - desiredCardinality ));
		
		for( int i = 0; i <= ( aSet.length - desiredCardinality ); i++ )
		{
			System.out.println( "i = " + i );
			
			Set<String> subset = new HashSet<String>();
		
			skipList.clear();
			
			for( int k = 0; k < ( aSet.length - desiredCardinality ); k++ )
			{
			
			
			for( int j = i; j< aSet.length; j++ )
			{
				
				System.out.println( "j = " + j );
				
				if( skipList.contains(j) ) 
				{
					continue;
				}	

				// our "lookahead" variable, while i always defines
				// the starting point...
				subset.add(aSet[j]);	
				added++;
				if( added == desiredCardinality)
				{
					skipList.add(j);
					subsets.add( subset );
					subset = new HashSet<String>();
					// j = i;
					added = 0;
				}
									
			}
			}
		}
		
		
		for( Set<String> aSubset : subsets )
		{
			System.out.print( "{");
			aSubset.forEach( (k) -> { System.out.print( k + " " ); } );
			System.out.println( "}");
		}
		
		System.out.println( "done" );
		
	}
}
