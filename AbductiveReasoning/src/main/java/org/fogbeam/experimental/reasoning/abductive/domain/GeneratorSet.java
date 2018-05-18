package org.fogbeam.experimental.reasoning.abductive.domain;

import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

public class GeneratorSet 
{
	private UnifiedSet<Generator> generators = new UnifiedSet<Generator>();
	
	public GeneratorSet()
	{
		// the GeneratorSet starts out containing one - empty - Generator
		generators.add( new Generator() );
	}
	
	public UnifiedSet<Generator> getGenerators() 
	{
		return generators;
	}
	
	public void addGenerator( Generator gen )
	{
		generators.add( gen );
	}
	
	public void addAllGenerators( Set<Generator> generators )
	{
		generators.addAll( generators );
	}
	
	public String toString()
	{
		return generators.makeString( "\\" );
	}
	
}
