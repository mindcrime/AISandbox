package org.fogbeam.experimental.reasoning.abductive;

import static org.fogbeam.experimental.reasoning.abductive.AbductionConstants1.TDB_DIR;

import java.util.Set;

import org.fogbeam.experimental.reasoning.abductive.queries.QueryManifestationsStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QueryManifestationsMain 
{
	final static Logger logger = LoggerFactory.getLogger(QueryManifestationsMain.class);
	
	
	public static void main(String[] args) 
	{

		QueryManifestationsStrategy query = new QueryManifestationsStrategy( TDB_DIR + "/trivial" );

		Set<String> manifestationsAll = query.listAll();
		
		
		manifestationsAll.forEach( (k) -> { logger.debug( k ); } ); 
		
		
		logger.debug( "-------------------------------------" );
		
		Set<String> manifestations = query.doQuery( "d1" );
		
		manifestations.forEach( (k) -> { logger.debug( k ); } ); 
		
		logger.debug( "done" );

	}

}
