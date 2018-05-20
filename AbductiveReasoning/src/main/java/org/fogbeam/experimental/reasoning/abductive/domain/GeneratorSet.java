package org.fogbeam.experimental.reasoning.abductive.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.SetAdapter;

public class GeneratorSet implements Cloneable
{
	private static int instanceCount = 0;
	private int id;
	private MutableSet<Generator> generators = SetAdapter.adapt( new LinkedHashSet<Generator>() );
	
	public GeneratorSet()
	{
		this.id = instanceCount++;
	}
	
	public MutableSet<Generator> getGenerators() 
	{
		return this.generators;
	}
	
	public void initEmpty( Generator gen )
	{
		this.generators.add( gen );
	}
	
	public void addGenerator( Generator gen )
	{
		System.out.println( "in addGenerator(), gen passed in = " + gen );
		System.out.println( "in addGenerator(), existing generators = " + this.generators );
	
		if( gen.isEmpty() )
		{
			System.out.println( "passed an empty Generator, ignoring it..." );
			return;
		}
		
		boolean result = this.generators.add( gen );
		if( !result )
		{
			System.err.println( "WARN: failed to add in addGenerator() call!" );
			throw new RuntimeException( "failed to add in addGenerator() call!" );
		}
		
	}
	
	public void addAllGenerators( final MutableSet<Generator> generators )
	{	
		System.out.println( "in addAllGenerators, generators already existing = " + this.generators );
		System.out.println( "in addAllGenerators, generators supplied = " + generators );
		
		boolean result = this.generators.addAll( generators );
		if( !result )
		{
			System.err.println( "WARN: failed to add in addAllGenerators() call!" );
			// throw new RuntimeException( "failed to add in addAllGenerators() call!" );
		}
	}
	
	public void unionInto( GeneratorSet temp )
	{
		this.generators.union(temp.getGenerators());
	}

	public Generator getArbitraryElement()
	{
		Iterator<Generator> generatorsIter = this.generators.iterator();
		int size = this.generators.size();
		
		SecureRandom rand = new SecureRandom();
		
		Generator temp = null;
		int iters = rand.nextInt(size) + 1;
		for( int i = 0; i < iters; i++ )
		{
			temp = generatorsIter.next();
		}
	
		return temp;
	}

	// for when we don't want the element to actually be arbitary.
	// useful for debugging when we want determinstic behavior
	public Generator getArbitraryElement( int index )
	{
		List<Generator> temp = new ArrayList<Generator>();
		temp.addAll( this.generators );
		
		return temp.get(index);
	}

	public GeneratorSet removeGenerator(Generator qsubJ) 
	{
		this.generators.remove( qsubJ );
		
		return this;
	}
	
	public  boolean isEmpty()
	{
		return this.generators.isEmpty();
	}
	
	public String toString()
	{
		return this.id + " : " + this.generators.makeString( "\\" );
	}	
}
