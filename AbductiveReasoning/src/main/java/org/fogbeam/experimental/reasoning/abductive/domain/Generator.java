package org.fogbeam.experimental.reasoning.abductive.domain;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

public class Generator 
{
	private UnifiedSet<UnifiedSet<String>> explanationSets = new UnifiedSet<UnifiedSet<String>>();
	
	public UnifiedSet<UnifiedSet<String>> getExplanationSets()
	{
		return explanationSets;
	}
	
	public String toString()
	{
		return explanationSets.makeString( "|" );
	}
}
