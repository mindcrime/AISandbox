package org.fogbeam.experimental.reasoning.tangential;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.collections.api.set.UnsortedSetIterable;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
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
		
		UnifiedSet<String> uSet = new UnifiedSet<String>();
		uSet.addAll(manifestations);
		
		Set<UnsortedSetIterable<String>> powerSet = uSet.powerSet(); 
		
		for( UnsortedSetIterable<String> setInPowerSet : powerSet )
		{
			System.out.print( "{ ");
			setInPowerSet.forEach( (k) -> { System.out.print( StringUtils.substringAfter(k, "#") + " " ); } );
			System.out.println( "}\n" );
		}
	}
}
