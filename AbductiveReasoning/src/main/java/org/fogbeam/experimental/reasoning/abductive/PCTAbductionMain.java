package org.fogbeam.experimental.reasoning.abductive;

import static org.fogbeam.experimental.reasoning.abductive.AbductionConstants1.TDB_DIR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.util.LinkedHashSet;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.SetAdapter;
import org.fogbeam.experimental.reasoning.abductive.domain.GeneratorSet;
import org.fogbeam.experimental.reasoning.abductive.queries.QueryDiseasesStrategy;
import org.fogbeam.experimental.reasoning.abductive.queries.QueryManifestationsStrategy;
import org.fogbeam.experimental.reasoning.abductive.setoperations.Bipartite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PCTAbductionMain 
{
	final static Logger logger = LoggerFactory.getLogger(PCTAbductionMain.class);
	
	public static void main(String[] args) throws Exception
	{	
		String tdbDir = TDB_DIR + "/trivial";
		
		QueryManifestationsStrategy manifestationsQuery = new QueryManifestationsStrategy( tdbDir );
		MutableSet<String> manifestationsAll = manifestationsQuery.listAll();
		
		
		QueryDiseasesStrategy diseasesQuery = new QueryDiseasesStrategy( tdbDir );
		MutableSet<String> diseasesAll = diseasesQuery.listAll();
		
		GeneratorSet hypothesis = new GeneratorSet();
		
		// start our initial mPlus set
		LinkedHashSet<String> mPlusBackingSet = new LinkedHashSet<String>();
		MutableSet<String> mPlus = SetAdapter.adapt( mPlusBackingSet );
		
		try( BufferedReader console = new BufferedReader( new InputStreamReader( System.in ) ) )
		{
		
			// prompt user for Manifestations
			// until user says 'q'
			String userInput = "";
			do
			{
				if( userInput != null && !userInput.isEmpty() )
				{
					mPlus.add(userInput);
				}
				
				logger.debug( "Enter a Manifestation ('q' to quit): " );
				userInput = console.readLine();
				
			} while( !userInput.equalsIgnoreCase("q"));
			

			logger.debug( "Manifestations: " + mPlus );

			
			// run through the PCT / BIPARTITE algorithm
			// generating all the plausible explanations
			Bipartite bipartite = new Bipartite( tdbDir );

			// D, M, M+
			hypothesis = bipartite.process(diseasesAll, manifestationsAll, mPlus);
			
			console.close();
		}
		
		
		logger.debug( "\n\n*********************************************\n");
		logger.debug( "Hypothesis: " + hypothesis );
		logger.debug( "\n\n*********************************************\n\n\n");
		
		
		logger.debug( "done: " + MethodHandles.lookup().lookupClass() );
	}

}
