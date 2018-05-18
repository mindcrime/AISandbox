package org.fogbeam.experimental.reasoning.abductive.setoperations;

import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.fogbeam.experimental.reasoning.abductive.domain.Generator;
import org.fogbeam.experimental.reasoning.abductive.domain.GeneratorSet;
import org.fogbeam.experimental.reasoning.abductive.queries.QueryCauses;

public class Bipartite 
{
	
	public GeneratorSet process(   // disorders
								   Set<String> disordersAll, 
								
								   // manifestations
								   Set<String> manifestationsAll, 
								
								   // present manifestations
								   UnifiedSet<String> mPlus)
	{
		// hypothesis = {Θ}, so our GeneratorSet starts out containing one empty set (Generator)
		GeneratorSet hypothesis = new GeneratorSet();
		
		for( String manifestation : mPlus )
		{
			// overwrite the existing values in hypothesis since we take stuff out as well as add stuff? 
			// each time revise is called it returns the right set of stuff based on what's already there? 
			// or does this need to accumulate...  
			hypothesis = revise( hypothesis, causes( manifestation ) );	
		}
		
		return hypothesis;
	}

	
	private UnifiedSet<String> causes( String manifestation )
	{
		QueryCauses causesQuery = new QueryCauses();
		UnifiedSet<String> causesForManifestation = causesQuery.doQuery( manifestation );
		
		return causesForManifestation;
	}


	private GeneratorSet divideGeneratorByDisorderSet( Generator GsubI, Set<String> Hsub1 )
	{
		GeneratorSet result = new GeneratorSet();
		
		if( true )
		{
			throw new RuntimeException( "divideGeneratorByDisorderSet() - Not implemented yet!");
		}
		
		return result;
	}


	private GeneratorSet divideGeneratorSetByDisorderSet( GeneratorSet G, Set<String> Hsub1 )
	{
		
		// dividing a GeneratorSet by a DisorderSet is the union of the
		// division of each Generator in the GeneratorSet, by the DisorderSet
		
		
		GeneratorSet result = new GeneratorSet();
				
		for( Generator GsubOne : G.getGenerators() )
		{
			GeneratorSet temp = divideGeneratorByDisorderSet(GsubOne, Hsub1);
			result = union( result, temp );
		}
		
		return result;
	}

	
	private GeneratorSet augresGeneratorSetBySet( GeneratorSet G, UnifiedSet<String> Hsub1 )
	{
		GeneratorSet result = new GeneratorSet();
		
		// augres of a GeneratorSet by a disorder set is the union of
		// augres of each of the Generators in the GeneratorSet, by that
		// disorder set.
		
		for( Generator GsubOne : G.getGenerators() )
		{
			GeneratorSet temp = augresGeneratorBySet(GsubOne, Hsub1);
			result = union( result, temp );
		}
		
		return result;
	}


	private GeneratorSet augresGeneratorBySet( Generator G, UnifiedSet<String> Hsub1 )
	{
		GeneratorSet result = new GeneratorSet();
		
		
		// the result is the set difference, in turn, between each explanation-set in the Generator and Hsub1,
		// plus A, where A is the set difference of HsubOne and the union of all the explanation-sets
		
		for( UnifiedSet<String> explanationSet : G.getExplanationSets() )
		{
			// for each of these, take the relative complement between it and Hsub1
			// and that gets added to the result GeneratorSet
		}
		
		// then we calculate A and add that to the result GeneratorSet
		// A is a Generator? 
		UnifiedSet explanationsUnion = new UnifiedSet();
		Generator A = setDifference( Hsub1, explanationsUnion );
		
		
		if( true )
		{
			throw new RuntimeException( "" );
		}
		
		
		return result;
	}

	
	private Generator setDifference( UnifiedSet<String> hsub1, UnifiedSet explanationsUnion) 
	{
		Generator result = new Generator();
		
		if( true )
		{
			throw new RuntimeException( "setDifference() - Not implemented yet!" );
		}
		
		return result;
	}


	private GeneratorSet resGeneratorSetByGeneratorSet( GeneratorSet Q, GeneratorSet F )
	{
		GeneratorSet result = new GeneratorSet();
		
		if( true )
		{
			throw new RuntimeException( "resGeneratorSetByGeneratorSet() - Not implemented yet!");
		}
		
		return result;
	}
	
	private GeneratorSet resGeneratorSetByGenerator( GeneratorSet Q, Generator F )
	{
		GeneratorSet result = new GeneratorSet();
		
		if( true )
		{
			throw new RuntimeException( "resGeneratorSetByGenerator() - Not implemented yet!");
		}
		
		return result;
	}

	private GeneratorSet resGeneratorByGenerator( Generator Q, Generator F )
	{
		GeneratorSet result = new GeneratorSet();
		
		if( true )
		{
			throw new RuntimeException( "resGeneratorByGenerator() - Not implemented yet!");
		}
		
		return result;
	}

	private GeneratorSet union( GeneratorSet F, GeneratorSet Q )
	{
		GeneratorSet result = new GeneratorSet();
		
		if( true )
		{
			throw new RuntimeException( "union() - Not implemented yet!");
		}
		
		return result;
	}
	
	
	private GeneratorSet revise( GeneratorSet G, UnifiedSet<String> Hsub1 )
	{
		// TODO: implement divideGeneratorByHypothesis()
		GeneratorSet F = divideGeneratorSetByDisorderSet( G, Hsub1 );
		
		System.out.println( "F = " + F );
		
		// TODO: implement augres()
		GeneratorSet Q = augresGeneratorSetBySet( G, Hsub1 );
		
		System.out.println( "Q = " + Q );
		
		// TODO: implement resGeneratorSetByGeneratorSet()
		GeneratorSet temp = resGeneratorSetByGeneratorSet( Q, F );
		
		System.out.println( "temp = " + temp );
		
		// TODO: implement union()
		GeneratorSet answer = union( F, temp );
		
		System.out.println( "answer = " + answer );
		
		return answer;
	}
	
}
