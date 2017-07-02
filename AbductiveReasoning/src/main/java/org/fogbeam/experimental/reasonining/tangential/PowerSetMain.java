package org.fogbeam.experimental.reasonining.tangential;

import java.util.HashSet;
import java.util.Set;

import org.fogbeam.experimental.reasoning.abductive.queries.QueryManifestationsStrategy;

public class PowerSetMain {

	public static void main(String[] args) 
	{

		PowerSetMain main = new PowerSetMain();
		main.run();

		System.out.println( "done" );
		
	}

	// generate the power-set of our set
	public void run() 
	{
		QueryManifestationsStrategy query = new QueryManifestationsStrategy();
		
		Set<String> manifestations = query.listAll();
		
		Set<Set<String>> powerSet = this.powerSet(manifestations);
		
		for( Set<String> setInPowerSet : powerSet )
		{
			setInPowerSet.forEach( (k) -> { System.out.println( k ); } );
			System.out.println( "\n" );
		}
		
		
	}
	
	public Set<Set<String>> powerSet( final Set<String> baseSet )
	{
		Set<Set<String>> powerSet = new HashSet<Set<String>>();

		int cardinalityBase = baseSet.size();
		
		for( int i = cardinalityBase; i > 0; i-- )
		{
			// find subsets of cardinality i
			// and add those
			Set<Set<String>> subsetsOfSpecificCardinality = findSubsets(baseSet, i );
			powerSet.addAll(subsetsOfSpecificCardinality);
		}
		
		Set<String> theta = new HashSet<String>();
		theta.add("θ");
		powerSet.add(theta);
		
		return powerSet;
	}

	private Set<Set<String>> findSubsets(Set<String> baseSet, int i) 
	{
		Set<Set<String>> subsets = new HashSet<Set<String>>();
		
	
		
		
		return subsets;
	}
	
}
