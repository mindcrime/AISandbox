package org.fogbeam.experimental.reasoning.abductive.setoperations;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.fogbeam.experimental.reasoning.abductive.domain.Generator;
import org.fogbeam.experimental.reasoning.abductive.domain.GeneratorSet;
import org.junit.Test;


public class BipartiteTest
{
	@Test
	public void testUnion()
	{
		Bipartite bipartite = new Bipartite("no_path");
		
		GeneratorSet lhs = new GeneratorSet();
		GeneratorSet rhs = new GeneratorSet();
		
		Generator gen1 = new Generator();
		MutableSet<String> set1 = new UnifiedSet<String>();
		set1.add( "set1");
		gen1.addExplanationSet( set1 );
		
		Generator gen2 = new Generator();
		MutableSet<String> set2 = new UnifiedSet<String>();
		set2.add( "set2");
		gen2.addExplanationSet( set2 );
		
		
		Generator gen3 = new Generator();
		MutableSet<String> set3 = new UnifiedSet<String>();
		set3.add( "set3");
		gen3.addExplanationSet( set3 );
		
		
		Generator gen4 = new Generator();
		MutableSet<String> set4 = new UnifiedSet<String>();
		set4.add( "set4");
		gen4.addExplanationSet( set4 );
		
		
		lhs.addGenerator(gen1);
		lhs.addGenerator(gen2);

		
		rhs.addGenerator(gen3);
		rhs.addGenerator(gen4);
		
		GeneratorSet union = bipartite.union( lhs, rhs );
		
		assertTrue( union != lhs );
		assertTrue( union != rhs );
		assertTrue( union.size() == 4 );
	}
	
	@Test
	public void testDivideEmptyGeneratorSetByDisorderSet()
	{
		
		Bipartite bipartite = new Bipartite("no_path");
		
		GeneratorSet emptyGeneratorSet = new GeneratorSet();
		Generator gen = new Generator(); 
		emptyGeneratorSet.initEmpty(gen);
		
		MutableSet<String> Hsub1 = new UnifiedSet<String>();
		Hsub1.add("d2");
		Hsub1.add("d3");
		
		/* we're dividing an empty generator set, so the resulting GeneratorSet should be empty
		 * no matter what we pass in Hsub1
		 */
		GeneratorSet result = bipartite.divideGeneratorSetByDisorderSet(emptyGeneratorSet, Hsub1);
		
		assertTrue( result.isEmpty() );
	}
	
	@Test
	public void testDivideEmptyGeneratorByDisorderSet()
	{
		Bipartite bipartite = new Bipartite("no_path");
		
		Generator emptyGenerator = new Generator(); 
		
		MutableSet<String> Hsub1 = new UnifiedSet<String>();
		Hsub1.add("d2");
		Hsub1.add("d3");
		
		/* we're dividing an empty generator, so the resulting GeneratorSet should be empty
		 * no matter what we pass in Hsub1
		 */
		GeneratorSet result = bipartite.divideGeneratorByDisorderSet(emptyGenerator, Hsub1);
		
		assertTrue( result.isEmpty() );
	}

	@Test
	public void testDivideGeneratorByDisorderSet1()
	{
		Bipartite bipartite = new Bipartite("no_path");
		
		Generator Gsub1 = new Generator(); 
		
		MutableSet<String> expSet1 = new UnifiedSet<String>();
		expSet1.add( "d3" );
		expSet1.add( "d4" );
		
		MutableSet<String> expSet2 = new UnifiedSet<String>();
		expSet2.add( "d8" );
		
		Gsub1.addExplanationSet( expSet1 );
		Gsub1.addExplanationSet( expSet2 );
		
		assertTrue( Gsub1.size() == 2 );
		
		MutableSet<String> Hsub1 = new UnifiedSet<String>();
		Hsub1.add( "d2" );
		Hsub1.add( "d3" );
		Hsub1.add( "d5" );
		Hsub1.add( "d6" );
		
		
		GeneratorSet result = bipartite.divideGeneratorByDisorderSet(Gsub1, Hsub1);
		
		System.out.println( "result: " + result );
		
		assertTrue( result.size() == 1 );
		
		MutableSet<Generator> generators = result.getGenerators();
		
		Generator temp = generators.getFirst();
		
		MutableSet<MutableSet<String>> expSets = temp.getExplanationSets();
		boolean d3Found = false;
		boolean d8Found = false;
		for( MutableSet<String> expSet : expSets )
		{
			if( expSet.contains("d3"))
			{
				d3Found = true;
			}
			
			if( expSet.contains("d8"))
			{
				d8Found = true;
			}
		}
		
		assertTrue( d3Found );
		assertTrue( d8Found );
		
	}
	

	@Test
	public void testDivideGeneratorByDisorderSet2()
	{
		Bipartite bipartite = new Bipartite("no_path");
		
		Generator Gsub1 = new Generator(); 
		
		MutableSet<String> expSet1 = new UnifiedSet<String>();
		expSet1.add( "d1" );
		expSet1.add( "d2" );
		
		MutableSet<String> expSet2 = new UnifiedSet<String>();
		expSet2.add( "d7" );
		expSet2.add( "d8" );
		expSet2.add( "d9" );
		
		Gsub1.addExplanationSet( expSet1 );
		Gsub1.addExplanationSet( expSet2 );
		
		assertTrue( Gsub1.size() == 2 );
		
		MutableSet<String> Hsub1 = new UnifiedSet<String>();
		Hsub1.add( "d2" );
		Hsub1.add( "d3" );
		Hsub1.add( "d5" );
		Hsub1.add( "d6" );
		
		
		GeneratorSet result = bipartite.divideGeneratorByDisorderSet(Gsub1, Hsub1);
		
		System.out.println( "result: " + result );
		
		assertTrue( result.size() == 1 );
		
		MutableSet<Generator> generators = result.getGenerators();
		
		Generator temp = generators.getFirst();
		
		MutableSet<MutableSet<String>> expSets = temp.getExplanationSets();
		boolean d2Found = false;
		boolean d7Found = false;
		boolean d8Found = false;
		boolean d9Found = false;
		
		for( MutableSet<String> expSet : expSets )
		{
			System.out.println( "expSet: " + expSet );
			
			if( expSet.contains("d2") )
			{
				d2Found = true;
			}
			
			if( expSet.contains("d7"))
			{
				d7Found = true;
			}
			
			if( expSet.contains("d8"))
			{
				d8Found = true;
			}
			
			if( expSet.contains("d9"))
			{
				d9Found = true;
			}
		}
		
		assertTrue( d2Found );
		assertTrue( d7Found );
		assertTrue( d8Found );
		assertTrue( d9Found );
	
	}
	
	@Test
	public void testResGeneratorBySet1()
	{
		Bipartite bipartite = new Bipartite("no_path");
		
		Generator Gsub1 = new Generator(); 
		
		MutableSet<String> expSet1 = new UnifiedSet<String>();
		expSet1.add( "d3" );
		expSet1.add( "d4" );
		
		MutableSet<String> expSet2 = new UnifiedSet<String>();
		expSet2.add( "d8" );
		
		Gsub1.addExplanationSet( expSet1 );
		Gsub1.addExplanationSet( expSet2 );

		
		assertTrue( Gsub1.size() == 2 );
		
		MutableSet<String> Hsub1 = new UnifiedSet<String>();
		Hsub1.add( "d2" );
		Hsub1.add( "d3" );
		Hsub1.add( "d5" );
		Hsub1.add( "d6" );
		
		
		GeneratorSet result = bipartite.resGeneratorBySet(Gsub1, Hsub1);
		
		
		System.out.println( "result: " + result );

		
		MutableSet<Generator> generators = result.getGenerators();
		
		Generator temp = generators.getFirst();
		
		MutableSet<MutableSet<String>> expSets = temp.getExplanationSets();
		boolean d4Found = false;
		boolean d8Found = false;
		
		for( MutableSet<String> expSet : expSets )
		{
			System.out.println( "expSet: " + expSet );
			
			if( expSet.contains("d4") )
			{
				d4Found = true;
			}

			if( expSet.contains("d8") )
			{
				d8Found = true;
			}
		}		
		
		assertTrue( d4Found );
		assertTrue( d8Found );
		
	}

	@Test
	public void testResGeneratorBySet2()
	{
		Bipartite bipartite = new Bipartite("no_path");
		
		Generator Gsub1 = new Generator(); 
		
		MutableSet<String> expSet1 = new UnifiedSet<String>();
		expSet1.add( "d1" );
		expSet1.add( "d2" );
		
		MutableSet<String> expSet2 = new UnifiedSet<String>();
		expSet2.add( "d7" );
		expSet2.add( "d8" );
		expSet2.add( "d9" );
		
		Gsub1.addExplanationSet( expSet1 );
		Gsub1.addExplanationSet( expSet2 );
		
		assertTrue( Gsub1.size() == 2 );
		
		MutableSet<String> Hsub1 = new UnifiedSet<String>();
		Hsub1.add( "d2" );
		Hsub1.add( "d3" );
		Hsub1.add( "d5" );
		Hsub1.add( "d6" );
		
		
		GeneratorSet result = bipartite.resGeneratorBySet(Gsub1, Hsub1);
		
		System.out.println( "result: " + result );

		MutableSet<Generator> generators = result.getGenerators();
		
		Generator temp = generators.getFirst();
		
		MutableSet<MutableSet<String>> expSets = temp.getExplanationSets();
		boolean d1Found = false;
		boolean d7Found = false;
		boolean d8Found = false;
		boolean d9Found = false;
		
		for( MutableSet<String> expSet : expSets )
		{
			System.out.println( "expSet: " + expSet );
			
			if( expSet.contains("d1") )
			{
				d1Found = true;
			}

			if( expSet.contains("d7") )
			{
				d7Found = true;
			}
			
			if( expSet.contains("d8") )
			{
				d8Found = true;
			}
			
			if( expSet.contains("d9") )
			{
				d9Found = true;
			}

		}		
		
		assertTrue( d1Found );
		assertTrue( d7Found );
		assertTrue( d8Found );
		assertTrue( d9Found );
		
	}

	
	@Test
	public void testAugresGeneratorBySet1()
	{
		Bipartite bipartite = new Bipartite("no_path");
		
		Generator Gsub1 = new Generator(); 
		
		MutableSet<String> expSet1 = new UnifiedSet<String>();
		expSet1.add( "d3" );
		expSet1.add( "d4" );
		
		MutableSet<String> expSet2 = new UnifiedSet<String>();
		expSet2.add( "d8" );
		
		Gsub1.addExplanationSet( expSet1 );
		Gsub1.addExplanationSet( expSet2 );

		
		assertTrue( Gsub1.size() == 2 );
		
		MutableSet<String> Hsub1 = new UnifiedSet<String>();
		Hsub1.add( "d2" );
		Hsub1.add( "d3" );
		Hsub1.add( "d5" );
		Hsub1.add( "d6" );
		
		
		GeneratorSet result = bipartite.augresGeneratorBySet(Gsub1, Hsub1);
		
		
		System.out.println( "result: " + result );

		
		 
		MutableSet<Generator> generators = result.getGenerators();
		
		Generator temp = generators.getFirst();
		
		MutableSet<MutableSet<String>> expSets = temp.getExplanationSets();
		
		
		assertTrue( expSets.size() == 3 );
		
		boolean d2Found = false;
		boolean d4Found = false;
		boolean d5Found = false;
		boolean d6Found = false;
		boolean d8Found = false;
		
		
		for( MutableSet<String> expSet : expSets )
		{
			System.out.println( "expSet: " + expSet );

			if( expSet.contains("d2") )
			{
				d2Found = true;
			}
			
			if( expSet.contains("d4") )
			{
				d4Found = true;
			}

			if( expSet.contains("d5") )
			{
				d5Found = true;
			}

			if( expSet.contains("d6") )
			{
				d6Found = true;
			}

			if( expSet.contains("d8") )
			{
				d8Found = true;
			}
		}		
		
		assertTrue( d2Found );
		assertTrue( d4Found );
		assertTrue( d5Found );
		assertTrue( d6Found );
		assertTrue( d8Found );
		

	}
	
	@Test
	public void testAugresGeneratorBySet2()
	{
		Bipartite bipartite = new Bipartite("no_path");
		
		Generator Gsub1 = new Generator(); 
		
		MutableSet<String> expSet1 = new UnifiedSet<String>();
		expSet1.add( "d1" );
		expSet1.add( "d2" );
		
		MutableSet<String> expSet2 = new UnifiedSet<String>();
		expSet2.add( "d7" );
		expSet2.add( "d8" );
		expSet2.add( "d9" );
		
		Gsub1.addExplanationSet( expSet1 );
		Gsub1.addExplanationSet( expSet2 );
		
		assertTrue( Gsub1.size() == 2 );
		
		MutableSet<String> Hsub1 = new UnifiedSet<String>();
		Hsub1.add( "d2" );
		Hsub1.add( "d3" );
		Hsub1.add( "d5" );
		Hsub1.add( "d6" );
		
		
		GeneratorSet result = bipartite.augresGeneratorBySet(Gsub1, Hsub1);
		
		System.out.println( "result: " + result );

		
		MutableSet<Generator> generators = result.getGenerators();
		
		Generator temp = generators.getFirst();
		
		MutableSet<MutableSet<String>> expSets = temp.getExplanationSets();
		
		assertTrue( expSets.size() == 3 );

		boolean d1Found = false;
		boolean d3Found = false;
		boolean d5Found = false;
		boolean d6Found = false;
		boolean d7Found = false;
		boolean d8Found = false;
		boolean d9Found = false;
		
		for( MutableSet<String> expSet : expSets )
		{
			System.out.println( "expSet: " + expSet );
			
			if( expSet.contains("d1") )
			{
				d1Found = true;
			}

			if( expSet.contains("d3") )
			{
				d3Found = true;
			}

			if( expSet.contains("d5") )
			{
				d5Found = true;
			}

			if( expSet.contains("d6") )
			{
				d6Found = true;
			}

			
			if( expSet.contains("d7") )
			{
				d7Found = true;
			}
			
			if( expSet.contains("d8") )
			{
				d8Found = true;
			}
			
			if( expSet.contains("d9") )
			{
				d9Found = true;
			}

		}		
		
		assertTrue( d1Found );
		assertTrue( d3Found );
		assertTrue( d5Found );
		assertTrue( d6Found );
		assertTrue( d7Found );
		assertTrue( d8Found );
		assertTrue( d9Found );

	}

	
	@Test
	public void testDivideGeneratorSetBySet()
	{
		Bipartite bipartite = new Bipartite("no_path");

		GeneratorSet genSet = new GeneratorSet();
		
		Generator Gsub1 = new Generator(); 
		
		MutableSet<String> expSet1 = new UnifiedSet<String>();
		expSet1.add( "d3" );
		expSet1.add( "d4" );
		
		MutableSet<String> expSet2 = new UnifiedSet<String>();
		expSet2.add( "d8" );
		
		Gsub1.addExplanationSet( expSet1 );
		Gsub1.addExplanationSet( expSet2 );
		
		assertTrue( Gsub1.size() == 2 );
		
		
		Generator Gsub2 = new Generator(); 
		
		MutableSet<String> expSet3 = new UnifiedSet<String>();
		expSet3.add( "d1" );
		expSet3.add( "d2" );
		
		MutableSet<String> expSet4 = new UnifiedSet<String>();
		expSet4.add( "d7" );
		expSet4.add( "d8" );
		expSet4.add( "d9" );
		
		Gsub2.addExplanationSet( expSet3 );
		Gsub2.addExplanationSet( expSet4 );
		
		assertTrue( Gsub2.size() == 2 );

		genSet.addGenerator(Gsub1);
		genSet.addGenerator(Gsub2);
		
		
		
		MutableSet<String> Hsub1 = new UnifiedSet<String>();
		Hsub1.add( "d2" );
		Hsub1.add( "d3" );
		Hsub1.add( "d5" );
		Hsub1.add( "d6" );
		
		
		GeneratorSet result = bipartite.divideGeneratorSetByDisorderSet(genSet, Hsub1);
		
		System.out.println( "result: " + result );
		
		assertTrue( result.size() == 2 );
				
	}
	
	
	@Test
	public void testAugresGeneratorSetBySet()
	{
		Bipartite bipartite = new Bipartite("no_path");
		
		GeneratorSet genSet = new GeneratorSet();
		
		Generator Gsub1 = new Generator(); 
		
		MutableSet<String> expSet1 = new UnifiedSet<String>();
		expSet1.add( "d3" );
		expSet1.add( "d4" );
		
		MutableSet<String> expSet2 = new UnifiedSet<String>();
		expSet2.add( "d8" );
		
		Gsub1.addExplanationSet( expSet1 );
		Gsub1.addExplanationSet( expSet2 );

		
		assertTrue( Gsub1.size() == 2 );
		
		Generator Gsub2 = new Generator(); 
		
		MutableSet<String> expSet3 = new UnifiedSet<String>();
		expSet3.add( "d1" );
		expSet3.add( "d2" );
		
		MutableSet<String> expSet4 = new UnifiedSet<String>();
		expSet4.add( "d7" );
		expSet4.add( "d8" );
		expSet4.add( "d9" );
		
		Gsub2.addExplanationSet( expSet3 );
		Gsub2.addExplanationSet( expSet4 );
		
		assertTrue( Gsub2.size() == 2 );

		genSet.addGenerator(Gsub1);
		genSet.addGenerator(Gsub2);
		
		MutableSet<String> Hsub1 = new UnifiedSet<String>();
		Hsub1.add( "d2" );
		Hsub1.add( "d3" );
		Hsub1.add( "d5" );
		Hsub1.add( "d6" );
		
		
		GeneratorSet result = bipartite.augresGeneratorSetBySet(genSet, Hsub1);
		
		System.out.println( "result = " + result );
		
		assertTrue( result.size() == 2 );
		
	}
	
	@Test
	public void testResGeneratorByGenerator()
	{
		Bipartite bipartite = new Bipartite("no_path");
		
		Generator Gsub1 = new Generator(); 
		
		MutableSet<String> expSet1 = new UnifiedSet<String>();
		expSet1.add( "d3" );
		expSet1.add( "d4" );
		
		MutableSet<String> expSet2 = new UnifiedSet<String>();
		expSet2.add( "d5" );
		
		MutableSet<String> expSet3 = new UnifiedSet<String>();
		expSet3.add( "d7" );
		expSet3.add( "d9" );
		
		MutableSet<String> expSet4 = new UnifiedSet<String>();
		expSet4.add( "d2" );
		expSet4.add( "d8" );
		
		
		Gsub1.addExplanationSet( expSet1 );
		Gsub1.addExplanationSet( expSet2 );
		Gsub1.addExplanationSet( expSet3 );
		Gsub1.addExplanationSet( expSet4 );

		
		assertTrue( Gsub1.size() == 4 );
		
		Generator Gsub2 = new Generator(); 
		
		MutableSet<String> expSet5 = new UnifiedSet<String>();
		expSet5.add( "d2" );
		
		MutableSet<String> expSet6 = new UnifiedSet<String>();
		expSet6.add( "d7" );
		expSet6.add( "d8" );

		
		Gsub2.addExplanationSet( expSet5 );
		Gsub2.addExplanationSet( expSet6 );
		
		assertTrue( Gsub2.size() == 2 );
		
		GeneratorSet result = bipartite.resGeneratorByGenerator(Gsub1, Gsub2);
		
		System.out.println( result );
		
		assertTrue( result.size() == 2 );
		Iterator<Generator> genIter = result.getGenerators().iterator();
		Generator gen1 = genIter.next();
		assertTrue( gen1.size() == 4 );
		Generator gen2 = genIter.next();
		assertTrue( gen2.size() == 4 );
	}
	
	@Test
	public void testResGeneratorSetByGeneratorSet()
	{
		Bipartite bipartite = new Bipartite("no_path");

		GeneratorSet genSet = new GeneratorSet();
		
		Generator Gsub1 = new Generator(); 
		
		MutableSet<String> expSet1 = new UnifiedSet<String>();
		expSet1.add( "d3" );
		expSet1.add( "d4" );
		
		MutableSet<String> expSet2 = new UnifiedSet<String>();
		expSet2.add( "d8" );
		
		Gsub1.addExplanationSet( expSet1 );
		Gsub1.addExplanationSet( expSet2 );
		
		assertTrue( Gsub1.size() == 2 );
		
		
		Generator Gsub2 = new Generator(); 
		
		MutableSet<String> expSet3 = new UnifiedSet<String>();
		expSet3.add( "d1" );
		expSet3.add( "d2" );
		
		MutableSet<String> expSet4 = new UnifiedSet<String>();
		expSet4.add( "d7" );
		expSet4.add( "d8" );
		expSet4.add( "d9" );
		
		Gsub2.addExplanationSet( expSet3 );
		Gsub2.addExplanationSet( expSet4 );
		
		assertTrue( Gsub2.size() == 2 );

		genSet.addGenerator(Gsub1);
		genSet.addGenerator(Gsub2);
		
		
		MutableSet<String> Hsub1 = new UnifiedSet<String>();
		Hsub1.add( "d2" );
		Hsub1.add( "d3" );
		Hsub1.add( "d5" );
		Hsub1.add( "d6" );
		
		
		GeneratorSet QsubJ = bipartite.divideGeneratorSetByDisorderSet(genSet, Hsub1);
		System.out.println( "QsubJ = " + QsubJ );
		
		GeneratorSet QsubL = bipartite.augresGeneratorSetBySet(genSet, Hsub1);
		System.out.println( "QsubL = " + QsubL );
		
		GeneratorSet result = bipartite.resGeneratorSetByGeneratorSet(QsubL, QsubJ);
		
		System.out.println( "result = " + result );
	
		assertTrue( result.size() == 3 );
		
	}
	
}
