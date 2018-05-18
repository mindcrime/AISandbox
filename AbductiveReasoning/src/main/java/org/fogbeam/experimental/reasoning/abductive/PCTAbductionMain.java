package org.fogbeam.experimental.reasoning.abductive;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.fogbeam.experimental.reasoning.abductive.domain.GeneratorSet;
import org.fogbeam.experimental.reasoning.abductive.queries.QueryDiseasesStrategy;
import org.fogbeam.experimental.reasoning.abductive.queries.QueryManifestationsStrategy;
import org.fogbeam.experimental.reasoning.abductive.setoperations.Bipartite;

public class PCTAbductionMain 
{

	public static void main(String[] args) throws Exception
	{
		
		QueryManifestationsStrategy manifestationsQuery = new QueryManifestationsStrategy();
		UnifiedSet<String> manifestationsAll = manifestationsQuery.listAll();
		
		
		QueryDiseasesStrategy diseasesQuery = new QueryDiseasesStrategy();
		UnifiedSet<String> diseasesAll = diseasesQuery.listAll();
		
		GeneratorSet hypothesis = new GeneratorSet();
		
		// start our initial mPlus set
		UnifiedSet<String> mPlus = new UnifiedSet<String>();
		
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
				
				System.out.println( "Enter a Manifestation ('q' to quit): " );
				userInput = console.readLine();
				
			} while( !userInput.equalsIgnoreCase("q"));
			

			System.out.println( "Manifestations: " + mPlus );

			
			// run through the PCT / BIPARTITE algorithm
			// generating all the plausible explanations
			Bipartite bipartite = new Bipartite();

			// D, M, M+
			hypothesis = bipartite.process(diseasesAll, manifestationsAll, mPlus);
		}
		
		
		System.out.println( "Hypothesis: " + hypothesis );
		
		
		System.out.println( "done: " + MethodHandles.lookup().lookupClass() );
	}

}
