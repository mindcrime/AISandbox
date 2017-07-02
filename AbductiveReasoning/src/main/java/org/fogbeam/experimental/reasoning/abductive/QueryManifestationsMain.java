package org.fogbeam.experimental.reasoning.abductive;

import java.util.Set;

import org.fogbeam.experimental.reasoning.abductive.queries.QueryManifestationsStrategy;

public class QueryManifestationsMain 
{

	public static void main(String[] args) 
	{

		QueryManifestationsStrategy query = new QueryManifestationsStrategy();

		Set<String> manifestationsAll = query.listAll();
		
		
		manifestationsAll.forEach( (k) -> { System.out.println( k ); } ); 
		
		
		System.out.println( "-------------------------------------" );
		
		Set<String> manifestations = query.doQuery( "d1" );
		
		manifestations.forEach( (k) -> { System.out.println( k ); } ); 
		
		System.out.println( "done" );

	}

}
