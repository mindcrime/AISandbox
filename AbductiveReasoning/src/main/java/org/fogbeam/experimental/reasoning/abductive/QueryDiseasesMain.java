package org.fogbeam.experimental.reasoning.abductive;


import static org.fogbeam.experimental.reasoning.abductive.AbductionConstants1.TDB_DIR;

import java.util.Set;

import org.fogbeam.experimental.reasoning.abductive.queries.QueryDiseasesStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QueryDiseasesMain 
{
	
	final static Logger logger = LoggerFactory.getLogger(QueryDiseasesMain.class);

	public static void main(String[] args) 
	{
		
		QueryDiseasesStrategy query = new QueryDiseasesStrategy( TDB_DIR + "/trivial" );
		
		
		Set<String> diseasesAll = query.listAll();
		diseasesAll.forEach( (k) -> { logger.debug( k ); } );
		
		logger.debug( "-----------------------------");
		
		Set<String> diseases = query.doQuery( "m1" );
		
		diseases.forEach( (k) -> { logger.debug( k ); } );
		
		
		logger.debug( "done" );

	}

}
