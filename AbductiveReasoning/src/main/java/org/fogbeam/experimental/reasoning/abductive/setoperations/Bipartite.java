package org.fogbeam.experimental.reasoning.abductive.setoperations;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.SetAdapter;
import org.fogbeam.experimental.reasoning.abductive.domain.Generator;
import org.fogbeam.experimental.reasoning.abductive.domain.GeneratorSet;
import org.fogbeam.experimental.reasoning.abductive.queries.QueryCauses;

public class Bipartite 
{
	
	private String dir;
	
	public Bipartite( final String dir ) 
	{
		this.dir = dir;
	}
	
	public GeneratorSet process(   // disorders
								   Set<String> disordersAll, 
								
								   // manifestations
								   Set<String> manifestationsAll, 
								
								   // present manifestations
								   MutableSet<String> mPlus
							    
			  					)  throws Exception
	{
		// hypothesis = {Θ}, so our GeneratorSet starts out containing one empty set (Generator)
		GeneratorSet hypothesis = new GeneratorSet();
		Generator gen = new Generator(); 
		// gen.initEmpty(SetAdapter.adapt( new LinkedHashSet<String>()));
		hypothesis.initEmpty(gen);
		
		
		for( String mNew : mPlus )
		{
			// overwrite the existing values in hypothesis since we take stuff out as well as add stuff? 
			// each time revise is called it returns the right set of stuff based on what's already there? 
			// or does this need to accumulate...  
			hypothesis = revise( hypothesis, causes( mNew ) );	
		}
		
		return hypothesis;
	}

	
	private MutableSet<String> causes( String manifestation )
	{
		QueryCauses causesQuery = new QueryCauses(dir);
		MutableSet<String> causesForManifestation = causesQuery.doQuery( manifestation );
		
		return causesForManifestation;
	}


	private GeneratorSet divideGeneratorSetByDisorderSet( GeneratorSet G, MutableSet<String> Hsub1 )
	{
		
		// dividing a GeneratorSet by a DisorderSet is the union of the
		// division of each Generator in the GeneratorSet, by the DisorderSet
		
		System.out.println( "in divideGeneratorSetByDisorderSet(), G = " + G );
		System.out.println( "in divideGeneratorSetByDisorderSet(), Hsub1 = " + Hsub1 );
		
		GeneratorSet result = new GeneratorSet();
				
		for( Generator GsubOne : G.getGenerators() )
		{
			GeneratorSet temp = divideGeneratorByDisorderSet(GsubOne, Hsub1);
			System.out.println( "in divideGeneratorSetByDisorderSet(), temp (result of divideGeneratorByDisorderSet) = " + temp );
			result = union( result, temp );
			System.out.println( "in divideGeneratorSetByDisorderSet(), result (after union of result and temp) = " + result );
		}
		
		return result;
	}

	
	private GeneratorSet divideGeneratorByDisorderSet( Generator GsubI, MutableSet<String> Hsub1 )
	{
		System.out.println( "in divideGeneratorByDisorderSet(), GsubI = " + GsubI );
		System.out.println( "in divideGeneratorByDisorderSet(), Hsub1 = " + Hsub1 );
		
		GeneratorSet result = new GeneratorSet();
		
		int n = GsubI.size(); // by definition, we can have at most n generators resulting from this division
		System.out.println( "in divideGeneratorByDisorderSet(), n = " + n );
				
		for( int k = 1; k <= n; k++ )
		{
			System.out.println( "in divideGeneratorByDisorderSet(), k = " + k );
			
			Iterator<MutableSet<String>> gsubiIterator = GsubI.getExplanationSets().iterator();
			Generator qk = new Generator();
			
			for( int j = 1; j <= n; j++ )
			{
				System.out.println( "in divideGeneratorByDisorderSet(), j = " + j );
				
				MutableSet<String> gsubj = gsubiIterator.next();
				
				if( j < k )
				{
					MutableSet<String> q_kj = gsubj.difference(Hsub1);
					
					// if q_kj is the empty set, don't include it
					if( !(q_kj.size() == 0) )
					{
						qk.addExplanationSet( q_kj );
					}
				}
				else if( j == k )
				{

					MutableSet<String> q_kj = gsubj.intersect(Hsub1);
					System.out.println( "in divideGeneratorByDisorderSet(), q_kj = " + q_kj );
					
					// if q_kj is the empty set, don't include it
					if( !(q_kj.size() == 0) )
					{
						System.out.println( "in divideGeneratorByDisorderSet(), adding disorderSet q_kj to generator qk" );
						qk.addExplanationSet( q_kj );
					}
					else
					{
						System.out.println( "in divideGeneratorByDisorderSet(), disorderSet q_kj is empty, so NOT adding it to generator qk" );
					}
				}
				else if( j > k )
				{
					MutableSet<String> q_kj = gsubj.clone();
					
					// if q_kj is the empty set, don't include it
					if( !(q_kj.size() == 0) )
					{
						qk.addExplanationSet( q_kj );
					}
				}
			}
			
			System.out.println( "in divideGeneratorByDisorderSet(), adding generator qk to result GeneratorSet" );
			result.addGenerator( qk );

		}
		
		System.out.println( "in divideGeneratorByDisorderSet(), returning result = " + result );
		
		return result;
	}
	
	
	private GeneratorSet augresGeneratorSetBySet( GeneratorSet G, MutableSet<String> Hsub1 )
	{
		
		System.out.println( "in augresGeneratorSetBySet, G = " + G );
		System.out.println( "in augresGeneratorSetBySet, Hsub1 = " + Hsub1 );
		
		
		GeneratorSet result = new GeneratorSet();
		
		// augres of a GeneratorSet by a disorder set is the union of
		// augres of each of the Generators in the GeneratorSet, by that
		// disorder set.
		
		for( Generator GsubOne : G.getGenerators() )
		{
			GeneratorSet temp = augresGeneratorBySet(GsubOne, Hsub1);
			System.out.println( "in augresGeneratorSetBySet, temp = " + temp );
			result = union( result, temp );
			System.out.println( "in augresGeneratorSetBySet, temp result = " + result );
			
		}
		
		System.out.println( "in augresGeneratorSetBySet, result = " + result );
		
		return result;
	}


	private GeneratorSet augresGeneratorBySet( Generator G, MutableSet<String> Hsub1 )
	{
		System.out.println( "in augresGeneratorBySet, G = " + G );
		System.out.println( "in augresGeneratorBySet, Hsub1 = " + Hsub1 );
		
		GeneratorSet result = new GeneratorSet();
		
		// the result is the set difference, in turn, between each explanation-set in the Generator and Hsub1,
		// plus A, where A is the set difference of HsubOne and the union of all the explanation-sets
		
		Generator generator = new Generator();
		
		for( MutableSet<String> explanationSet : G.getExplanationSets() )
		{
			// for each of these, take the relative complement between it and Hsub1
			// and that gets added to the result GeneratorSet
			MutableSet<String> temp = explanationSet.difference(Hsub1);
			
			System.out.println( "in augresGeneratorBySet, temp = " + temp );
		
			if( !( temp.size() == 0 ) )
			{
				// do we make a new Generator for each of these, or ???
				generator.addExplanationSet( temp );
			}
			
			System.out.println( "in augresGeneratorBySet, temp generator = " + generator );
		}
		
		// then we calculate A and add that to the result GeneratorSet
		
		// A is an ExplanationSet? 
		MutableSet<String> explanationsUnion = SetAdapter.adapt( new LinkedHashSet<String>() );
		MutableSet<MutableSet<String>> explanationsInG = G.getExplanationSets();
		for( MutableSet<String> explanationSet : explanationsInG )
		{
			explanationsUnion = explanationsUnion.union(explanationSet);
		}
		
		System.out.println( "in augresGeneratorBySet, explanationsUnion = " + explanationsUnion );
		
		MutableSet<String> A = Hsub1.difference( explanationsUnion );
		
		System.out.println( "in augresGeneratorBySet, A = " + A );
		
		if( !( A.size() == 0 ))
		{
			generator.addExplanationSet( A );		
		}
		
		System.out.println( "in augresGeneratorBySet, final generator = " + generator );
		
		if( !generator.isEmpty() )
		{
			result.addGenerator( generator );
		}
		
		System.out.println( "in augresGeneratorBySet, returning result = " + result );
		
		return result;
	}

	/* 
	private Generator setDifference( MutableSet<String> hsub1, MutableSet<String> explanationsUnion) 
	{
		Generator result = new Generator();
		
		MutableSet<String> difference = hsub1.difference( explanationsUnion );
		
		result.addExplanationSet( difference );
		
		return result;
	}
	*/

	private GeneratorSet resGeneratorSetByGeneratorSet( GeneratorSet G, GeneratorSet Q )
	{
		GeneratorSet result = null;
		
		System.out.println( "in resGeneratorSetByGeneratorSet(), G = " + G );
		System.out.println( "in resGeneratorSetByGeneratorSet(), Q = " + Q );
		
		if( Q.isEmpty())
		{
			System.out.println( "returning G, since Q is empty set" );
			result = G;
		}
		else
		{	
			// otherwise...
			System.out.println( "selecting arbitrary Generator QsubJ from Q" );
			Generator QsubJ = Q.getArbitraryElement(0);
			
			System.out.println( "in resGeneratorSetByGeneratorSet(), QsubJ = " + QsubJ );
			GeneratorSet QminusQsubJ = Q.removeGenerator( QsubJ );
			
			System.out.println( "in resGeneratorSetByGeneratorSet(), QminusQsubJ = " + QminusQsubJ );
			GeneratorSet temp = resGeneratorSetByGenerator(G, QsubJ);
			
			System.out.println( "in resGeneratorSetByGeneratorSet(), temp = " + temp );
			result = resGeneratorSetByGeneratorSet( temp, QminusQsubJ );		
		}
		
		System.out.println( "in resGeneratorSetByGeneratorSet(), result = " + result );
		
		return result;
	}
	
	private GeneratorSet resGeneratorSetByGenerator( GeneratorSet Q, Generator F )
	{
		System.out.println( "in resGeneratorSetByGenerator(), Q = " + Q );
		System.out.println( "in resGeneratorSetByGenerator(), F = " + F );
		
		GeneratorSet result = new GeneratorSet();

		
		MutableSet<Generator> generators = Q.getGenerators();
		for( Generator generator : generators )
		{
			GeneratorSet temp = resGeneratorByGenerator(generator, F);
			System.out.println( "in resGeneratorSetByGenerator(), temp = " + temp );
			
			result.unionInto( temp );
			System.out.println( "in resGeneratorSetByGenerator(), result = " + result );
		}
		
		return result;
	}
	
	
	private GeneratorSet resGeneratorByGenerator( Generator GsubI, Generator QsubJ )
	{
		GeneratorSet result = null;	
		
		if( QsubJ.isEmpty())
		{
			// if QsubJ is empty, we return an empty GeneratorSet by definition
			result = new GeneratorSet();
			return result;
		}
		else
		{
			MutableSet<String> qsubj = QsubJ.getArbitraryElement(0);
			GeneratorSet temp = resGeneratorBySet( GsubI, qsubj );			
			
			GeneratorSet temp2 = resGeneratorSetByGenerator( divideGeneratorByDisorderSet(GsubI, qsubj), QsubJ.removeSet( qsubj ) );
			
			temp.unionInto(temp2);
			
			result = temp;
		}
		
		return result;
	}

	
	private GeneratorSet resGeneratorBySet(Generator GsubI, MutableSet<String> qsubj) 
	{
		
		System.out.println( "in resGeneratorBySet(), GsubI = " + GsubI );
		System.out.println( "in resGeneratorBySet(), qsubj = " + qsubj );
		
		
		GeneratorSet result = new GeneratorSet();
				
		// this is a lot like augres, but without the additional 
		// A term being added.  For each g_i element in GsubI, you take
		// the difference of that and qsubj
		
		
		Generator generator = new Generator();
		
		for( MutableSet<String> explanationSet : GsubI.getExplanationSets() )
		{
			// for each of these, take the relative complement between it and qsubj
			// and that gets added to the result GeneratorSet
			MutableSet<String> temp = explanationSet.difference(qsubj);
			
			System.out.println( "in resGeneratorBySet, temp = " + temp );
		
			if( !( temp.size() == 0 ) )
			{
				// do we make a new Generator for each of these, or ???
				generator.addExplanationSet( temp );
			}
			
			System.out.println( "in resGeneratorBySet, temp generator = " + generator );
		}

		result.addGenerator(generator);
		
		return result;
	}


	private GeneratorSet union( GeneratorSet F, GeneratorSet Q )
	{
		
		System.out.println( "in union() F = " + F );
		System.out.println( "in union() Q = " + Q );
		
		GeneratorSet result = new GeneratorSet();
		
		MutableSet<Generator> union = F.getGenerators().union(Q.getGenerators());
		
		System.out.println( "in union() union = " + union + "union.size = " + union.size() );
		
		if( !union.isEmpty())
		{
			result.addAllGenerators( union );
		}
		
		System.out.println( "in union() result = " + result );
		
		return result;
	}
	
	
	private GeneratorSet revise( GeneratorSet G, MutableSet<String> Hsub1 ) throws Exception
	{
		if( Hsub1 == null || Hsub1.isEmpty() )
		{
			throw new RuntimeException( "no valid Hsub1 passed to revise!" );
		}
		
		System.out.println( "\n\n-----------------------------------------------------------\n");
		System.out.println( "in revise() current hypothesis G = " + G );
		System.out.println( "in revise() causes(m_new) Hsub1 = " + Hsub1 );
		
		
		GeneratorSet F = divideGeneratorSetByDisorderSet( G.clone(), Hsub1.clone() );
		
		System.out.println( "in revise() F = " + F );
		
		
		GeneratorSet Q = augresGeneratorSetBySet( G.clone(), Hsub1.clone() );
		
		System.out.println( "in revise() Q = " + Q );
		
		
		GeneratorSet temp = resGeneratorSetByGeneratorSet( Q.clone(), F.clone() );
		
		System.out.println( "in revise() temp = " + temp );
		System.out.println( "in revise() F = " + F );
		
		if( F.isEmpty() && temp.isEmpty() )
		{
			throw new RuntimeException( "this shouldn't happen: F and TEMP can't both be empty" );
		}
		
		GeneratorSet answer = union( F.clone(), temp.clone() );
		
		System.out.println( "in revise(() answer = " + answer );
		
		return answer;
	}
	
}
