package org.fogbeam.experimental.reasoning.abductive.setoperations;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.SetAdapter;
import org.fogbeam.experimental.reasoning.abductive.domain.Generator;
import org.fogbeam.experimental.reasoning.abductive.domain.GeneratorSet;
import org.fogbeam.experimental.reasoning.abductive.queries.QueryCausesStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bipartite 
{
	final static Logger logger = LoggerFactory.getLogger(Bipartite.class);
	
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
		hypothesis.initEmpty(gen);
		
		for( String mNew : mPlus )
		{
			logger.info( "In main loop, processing mNew: " + mNew );
			MutableSet<String> causesMNew = causes( mNew );
			logger.info( "In main loop, processing causes(mNew): " + causesMNew );
			
			// overwrite the existing values in hypothesis since we take stuff out as well as add stuff? 
			// each time revise is called it returns the right set of stuff based on what's already there? 
			// or does this need to accumulate...  
			hypothesis = revise( hypothesis, causesMNew );
			logger.info( "In main loop, interim hypothesis =  " + hypothesis );
		}
		
		return hypothesis;
	}

	
	MutableSet<String> causes( String manifestation )
	{
		QueryCausesStrategy causesQuery = new QueryCausesStrategy(dir);
		MutableSet<String> causesForManifestation = causesQuery.doQuery( manifestation );
		
		return causesForManifestation;
	}


	GeneratorSet divideGeneratorSetByDisorderSet( GeneratorSet G, MutableSet<String> Hsub1 )
	{
		
		// dividing a GeneratorSet by a DisorderSet is the union of the
		// division of each Generator in the GeneratorSet, by the DisorderSet
		logger.info( "Entering divideGeneratorSetByDisorderSet()");
		logger.debug( "in divideGeneratorSetByDisorderSet(), G = " + G );
		logger.debug( "in divideGeneratorSetByDisorderSet(), Hsub1 = " + Hsub1 );
		
		GeneratorSet result = new GeneratorSet();
				
		for( Generator GsubOne : G.getGenerators() )
		{
			GeneratorSet temp = divideGeneratorByDisorderSet(GsubOne, Hsub1);
			logger.debug( "in divideGeneratorSetByDisorderSet(), temp (result of divideGeneratorByDisorderSet) = " + temp );
			result = union( result, temp );
			logger.debug( "in divideGeneratorSetByDisorderSet(), result (after union of result and temp) = " + result );
		}
		
		return result;
	}

	
	GeneratorSet divideGeneratorByDisorderSet( Generator GsubI, MutableSet<String> Hsub1 )
	{
		logger.info( "Entering divideGeneratorByDisorderSet()");
		logger.debug( "in divideGeneratorByDisorderSet(), GsubI = " + GsubI );
		logger.debug( "in divideGeneratorByDisorderSet(), Hsub1 = " + Hsub1 );
		
		GeneratorSet result = new GeneratorSet();
		
		int n = GsubI.size(); // by definition, we can have at most n generators resulting from this division
		logger.debug( "in divideGeneratorByDisorderSet(), n = " + n );
			
		
		
		for( int k = 1; k <= n; k++ )
		{
			logger.trace( "in divideGeneratorByDisorderSet(), k = " + k );
			Iterator<MutableSet<String>> gsubiIterator = GsubI.getExplanationSets().iterator();
			
			Generator qk = new Generator();
			
			for( int j = 1; j <= n; j++ )
			{
				logger.trace( "in divideGeneratorByDisorderSet(), j = " + j );
				
				MutableSet<String> gsubj = gsubiIterator.next();
				
				if( j < k )
				{
					MutableSet<String> q_kj = gsubj.difference(Hsub1);
					
					// if q_kj is the empty set, don't include it
					if( !(q_kj.size() == 0) )
					{
						qk.addExplanationSet( q_kj );
					}
					else
					{
						// flag this generator since there's a "bad" set we would have been including
						qk.setFlagged(true);
					}
				}
				else if( j == k )
				{

					MutableSet<String> q_kj = gsubj.intersect(Hsub1);
					logger.debug( "in divideGeneratorByDisorderSet(), q_kj = " + q_kj );
					
					// if q_kj is the empty set, don't include it
					if( !(q_kj.size() == 0) )
					{
						logger.debug( "in divideGeneratorByDisorderSet(), adding disorderSet q_kj to generator qk" );
						qk.addExplanationSet( q_kj );
					}
					else
					{
						logger.debug( "in divideGeneratorByDisorderSet(), disorderSet q_kj is empty, so NOT adding it to generator qk" );
						// flag this generator to skip, since there's a "bad" set we would have been inserting
						qk.setFlagged(true);
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
					else
					{
						throw new RuntimeException("this shouldn't happen!");
					}
				}
			}
			
			logger.debug( "in divideGeneratorByDisorderSet(), adding generator qk to result GeneratorSet" );
			if( !qk.isFlagged() )
			{
				result.addGenerator( qk );
			}
		}
		
		logger.debug( "in divideGeneratorByDisorderSet(), returning result = " + result );	
		return result;
	}
	
	
	GeneratorSet augresGeneratorSetBySet( GeneratorSet G, MutableSet<String> Hsub1 )
	{
		logger.info( "Entering augresGeneratorSetBySet()");
		logger.debug( "in augresGeneratorSetBySet, G = " + G );
		logger.debug( "in augresGeneratorSetBySet, Hsub1 = " + Hsub1 );
		
		
		GeneratorSet result = new GeneratorSet();
		
		// augres of a GeneratorSet by a disorder set is the union of
		// augres of each of the Generators in the GeneratorSet, by that
		// disorder set.
		
		for( Generator GsubOne : G.getGenerators() )
		{
			GeneratorSet temp = augresGeneratorBySet(GsubOne, Hsub1);
			logger.debug( "in augresGeneratorSetBySet, temp = " + temp );
			result = union( result, temp );
			logger.debug( "in augresGeneratorSetBySet, temp result = " + result );
			
		}
		
		logger.debug( "in augresGeneratorSetBySet, result = " + result );
		
		return result;
	}


	GeneratorSet augresGeneratorBySet( Generator G, MutableSet<String> Hsub1 )
	{
		logger.info( "Entering augresGeneratorBySet()");
		logger.debug( "in augresGeneratorBySet, G = " + G );
		logger.debug( "in augresGeneratorBySet, Hsub1 = " + Hsub1 );
		
		GeneratorSet result = new GeneratorSet();
		
		// the result is the set difference, in turn, between each explanation-set in the Generator and Hsub1,
		// plus A, where A is the set difference of HsubOne and the union of all the explanation-sets
		
		Generator generator = new Generator();
		
		for( MutableSet<String> explanationSet : G.getExplanationSets() )
		{
			// for each of these, take the relative complement between it and Hsub1
			// and that gets added to the result GeneratorSet
			MutableSet<String> temp = explanationSet.difference(Hsub1);
			
			logger.debug( "in augresGeneratorBySet, temp = " + temp );
		
			if( !( temp.size() == 0 ) )
			{
				// do we make a new Generator for each of these, or ???
				generator.addExplanationSet( temp );
			}
			else
			{
				generator.setFlagged(true);
				// throw new RuntimeException( "Nope, shouldn't happen either");
			}
			
			logger.debug( "in augresGeneratorBySet, temp generator = " + generator );
		}
		
		// then we calculate A and add that to the result GeneratorSet
		
		// A is an ExplanationSet? 
		MutableSet<String> explanationsUnion = SetAdapter.adapt( new LinkedHashSet<String>() );
		MutableSet<MutableSet<String>> explanationsInG = G.getExplanationSets();
		for( MutableSet<String> explanationSet : explanationsInG )
		{
			explanationsUnion = explanationsUnion.union(explanationSet);
		}
		
		logger.debug( "in augresGeneratorBySet, explanationsUnion = " + explanationsUnion );
		
		MutableSet<String> A = Hsub1.difference( explanationsUnion );
		
		logger.debug( "in augresGeneratorBySet, A = " + A );
		
		if( !( A.size() == 0 ))
		{
			generator.addExplanationSet( A );		
		}
		else
		{
			throw new RuntimeException( "Bad, bad, bad...");
		}
		
		logger.debug( "in augresGeneratorBySet, final generator = " + generator );
		
		if( !generator.isEmpty() && !generator.isFlagged() )
		{
			result.addGenerator( generator );
		}
		
		logger.debug( "in augresGeneratorBySet, returning result = " + result );
		
		return result;
	}


	GeneratorSet resGeneratorSetByGeneratorSet( GeneratorSet G, GeneratorSet Q )
	{
		logger.info( "Entering resGeneratorSetByGeneratorSet()");
		
		GeneratorSet result = null;
		
		logger.debug( "in resGeneratorSetByGeneratorSet(), G = " + G );
		logger.debug( "in resGeneratorSetByGeneratorSet(), Q = " + Q );
		
		if( Q.isEmpty())
		{
			logger.debug( "returning G, since Q is empty set" );
			try
			{
				result = G.clone();
			}
			catch( CloneNotSupportedException e )
			{
				throw new RuntimeException( e );
			}
		}
		else
		{	
			// otherwise...
			logger.debug( "selecting arbitrary Generator QsubJ from Q" );
			
			Generator QsubJ = Q.getArbitraryElement(0);
			logger.debug( "in resGeneratorSetByGeneratorSet(), QsubJ = " + QsubJ );
			
			GeneratorSet QminusQsubJ = Q.removeGenerator( QsubJ );
			logger.debug( "in resGeneratorSetByGeneratorSet(), QminusQsubJ = " + QminusQsubJ );
			
			GeneratorSet temp = resGeneratorSetByGenerator(G, QsubJ);
			logger.debug( "in resGeneratorSetByGeneratorSet(), temp = " + temp );
			
			result = resGeneratorSetByGeneratorSet( temp, QminusQsubJ );		
		}
		
		logger.debug( "in resGeneratorSetByGeneratorSet(), result = " + result );
		
		return result;
	}
	
	GeneratorSet resGeneratorSetByGenerator( GeneratorSet Q, Generator F )
	{
		logger.info( "Entering resGeneratorSetByGenerator()");
		logger.debug( "in resGeneratorSetByGenerator(), Q = " + Q );
		logger.debug( "in resGeneratorSetByGenerator(), F = " + F );
		
		GeneratorSet result = new GeneratorSet();
		
		MutableSet<Generator> generators = Q.getGenerators();
		for( Generator generator : generators )
		{
			Generator clonedF = null;
			try
			{
				clonedF = F.clone();
			}
			catch( CloneNotSupportedException e )
			{
				throw new RuntimeException( e );
			}
			
			GeneratorSet temp = resGeneratorByGenerator(generator, clonedF );
			logger.debug( "in resGeneratorSetByGenerator(), temp = " + temp );
			
			result.unionInto( temp );
			logger.debug( "in resGeneratorSetByGenerator(), result = " + result );
		}
		
		return result;
	}
	
	
	GeneratorSet resGeneratorByGenerator( Generator GsubI, Generator QsubJ )
	{
		logger.info( "Entering resGeneratorByGenerator()");
		
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
			logger.debug( "qsubj" + qsubj );
			
			GeneratorSet tempResOfGsubI = resGeneratorBySet( GsubI, qsubj );			
			logger.debug( "tempResOfGsubI: " + tempResOfGsubI );
			// this should be GeneratorSet(0) |1| : { Generator(2) |4| % ( [d3, d4] | [d5] | [d9, d7] | [d8] ) } 
			// in our test case, and it is.  So we're good down to here. Next...
			
			GeneratorSet tempResultOfDivide = divideGeneratorByDisorderSet(GsubI, qsubj);
			logger.debug( "tempResultOfDivide: " + tempResultOfDivide );
			
			
			Generator QsubJAfterRemoveElement = QsubJ.removeSet( qsubj );
			logger.debug( "QsubJAfterRemoveElement: " + QsubJAfterRemoveElement );
			
			GeneratorSet tempResGeneratorSetByGenerator = resGeneratorSetByGenerator( tempResultOfDivide, QsubJAfterRemoveElement );
			logger.debug( "tempResGeneratorSetByGenerator: " + tempResGeneratorSetByGenerator );
			
			tempResOfGsubI.unionInto( tempResGeneratorSetByGenerator );
			logger.debug( "tempResOfGsubI after unionInto() = " + tempResOfGsubI );
			
			result = tempResOfGsubI;
		}
		
		return result;
	}

	
	GeneratorSet resGeneratorBySet(Generator GsubI, MutableSet<String> qsubj) 
	{
		logger.info( "Entering resGeneratorBySet()");
		logger.debug( "in resGeneratorBySet(), GsubI = " + GsubI );
		logger.debug( "in resGeneratorBySet(), qsubj = " + qsubj );
		
		
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
			
			logger.debug( "in resGeneratorBySet, temp = " + temp );
		
			if( !( temp.size() == 0 ) )
			{
				// do we make a new Generator for each of these, or ???
				generator.addExplanationSet( temp );
			}
			else
			{
				generator.setFlagged(true);
			}
			
			logger.debug( "in resGeneratorBySet, temp generator = " + generator );
		}

		if( !generator.isFlagged())
		{
			result.addGenerator(generator);
		}
		
		logger.debug( "resGeneratorBySet(), returning: " + result);
		return result;
	}


	GeneratorSet union( GeneratorSet F, GeneratorSet Q )
	{
		logger.info( "Entering union()");
		logger.debug( "in union() F = " + F );
		logger.debug( "in union() Q = " + Q );
		
		GeneratorSet result = new GeneratorSet();
		
		MutableSet<Generator> union = F.getGenerators().union(Q.getGenerators());
		
		logger.debug( "in union() union = " + union + "union.size = " + union.size() );
		
		if( !union.isEmpty())
		{
			result.addAllGenerators( union );
		}
		
		logger.debug( "in union() result = " + result );
		
		return result;
	}
	
	
	GeneratorSet revise( GeneratorSet G, MutableSet<String> Hsub1 ) throws Exception
	{
		logger.info( "Entering revise()");
		
		if( Hsub1 == null || Hsub1.isEmpty() )
		{
			throw new RuntimeException( "no valid Hsub1 passed to revise!" );
		}
		
		logger.debug( "\n\n-----------------------------------------------------------\n");
		logger.debug( "in revise() current hypothesis G = " + G );
		logger.debug( "in revise() causes(m_new) Hsub1 = " + Hsub1 );
		
		
		GeneratorSet F = divideGeneratorSetByDisorderSet( G.clone(), Hsub1.clone() );
		
		logger.debug( "in revise() F = " + F );
		
		
		GeneratorSet Q = augresGeneratorSetBySet( G.clone(), Hsub1.clone() );
		
		logger.debug( "in revise() Q = " + Q );
		
		
		GeneratorSet temp = resGeneratorSetByGeneratorSet( Q.clone(), F.clone() );
		
		logger.debug( "in revise() temp = " + temp );
		logger.debug( "in revise() F = " + F );
		
		if( F.isEmpty() && temp.isEmpty() )
		{
			throw new RuntimeException( "this shouldn't happen: F and TEMP can't both be empty" );
		}
		
		GeneratorSet answer = union( F.clone(), temp.clone() );
		
		logger.debug( "in revise(() answer = " + answer );
		
		return answer;
	}
	
}
