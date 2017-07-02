package org.fogbeam.experimental.reasoning.abductive;

import java.util.Set;

import org.fogbeam.experimental.reasoning.abductive.queries.QueryDiseasesStrategy;

public class QueryDiseasesMain 
{

	public static void main(String[] args) 
	{
		
		QueryDiseasesStrategy query = new QueryDiseasesStrategy();
		
		
		Set<String> diseasesAll = query.listAll();
		diseasesAll.forEach( (k) -> { System.out.println( k ); } );
		
		System.out.println( "-----------------------------");
		
		Set<String> diseases = query.doQuery( "m1" );
		
		diseases.forEach( (k) -> { System.out.println( k ); } );
		
		
		System.out.println( "done" );

	}

}
