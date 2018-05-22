package org.fogbeam.experimental.reasoning.abductive;

import static org.fogbeam.experimental.reasoning.abductive.AbductionConstants1.TDB_DIR;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.SetAdapter;
import org.fogbeam.experimental.reasoning.abductive.queries.QueryCausesStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryCausesMain 
{

	final static Logger logger = LoggerFactory.getLogger(QueryCausesMain.class);
	
	public static void main(String[] args) 
	{
		
		QueryCausesStrategy query = new QueryCausesStrategy( TDB_DIR + "/trivial" );
		query.getClass();
		
		Set<String> mPlus = new HashSet<String>();
		mPlus.add( "m1" );
		mPlus.add( "m3" );
		mPlus.add( "m4" );
		
		LinkedHashSet<String> backingSet = new LinkedHashSet<String>();
		MutableSet<String> results = SetAdapter.adapt( backingSet );
		
		System.out.print( "{ ");
		
		for( String disorder : results )
		{
			System.out.print( disorder + " ");
			
		}

		logger.debug( " }\n");

		
		logger.debug( "----------------------------------------------------\n");
		
		
		logger.debug( "done" );
	}
}

/*
 	Set<String> masterList = new HashSet<String>();
		Set<String> a = new HashSet<String>();
		Set<String> b = new HashSet<String>();
		
		List<MutableSet<String>> resultsAsList = new ArrayList<MutableSet<String>>();
		resultsAsList.addAll(results);
		
		for( int i = 0; i < resultsAsList.size(); i++ )
		{
			if( i == 0 )
			{
				Set<String> temp = resultsAsList.get(i);
				a.addAll(temp);
				continue;
			}
			else if( i == 1 ) 
			{
				Set<String> temp = resultsAsList.get(i);
				b.addAll( temp );
				continue;
			}
			else
			{
				LazyIterable<Pair<String, String>> temp = Sets.cartesianProduct( a, b );
				Iterator<Pair<String,String>> tempIter = temp.iterator();
				while( tempIter.hasNext())
				{
					
				}
				
				
				b = resultsAsList.get(i);
			}
		}
		
		LazyIterable foo = Sets.cartesianProduct( a, b );
		
		
		
		
		for( Object o : foo )
		{
			logger.debug( o );
		}

*/