package org.fogbeam.experimental.reasoning.abductive.domain;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.SetAdapter;

public class Generator  implements Cloneable, Serializable
{
	private static int instanceCount = 0;
	private int id;
	private MutableSet<MutableSet<String>> explanationSets = SetAdapter.adapt( new LinkedHashSet<MutableSet<String>>() );
	
	public Generator()
	{
		this.id = instanceCount++;
	}
	
	public MutableSet<MutableSet<String>> getExplanationSets()
	{
		return this.explanationSets;
	}
	
	public String toString()
	{
		return this.id + " # " + this.explanationSets.makeString( "|" );
	}
	
	public int size()
	{
		return this.explanationSets.size();
	}

	public void addExplanationSet(MutableSet<String> q_kj) 
	{
		if( q_kj.isEmpty() )
		{
			System.out.println( "passed empty ExplanationSet, ignoring...");
			
			return;
		}
		
		this.explanationSets.add( q_kj );
	}

	public boolean isEmpty() 
	{
		return this.explanationSets.isEmpty();
	}

	public Generator removeSet(MutableSet<String> qsubj) 
	{
		this.explanationSets.remove( qsubj );
		
		return this;
	}
	
	public MutableSet<String> getArbitraryElement()
	{
		Iterator<MutableSet<String>> elementsIter = this.explanationSets.iterator();
		int size = this.explanationSets.size();
		
		SecureRandom rand = new SecureRandom();
		
		MutableSet<String> temp = null;
		int iters = rand.nextInt(size) + 1;
		for( int i = 0; i < iters; i++ )
		{
			temp = elementsIter.next();
		}
	
		return temp;
	}

	// for when we don't want the element to actually be arbitary.
	// useful for debugging when we want determinstic behavior
	public MutableSet<String> getArbitraryElement( int index )
	{
		List<MutableSet<String>> temp = new ArrayList<MutableSet<String>>();
		temp.addAll( this.explanationSets );
		
		return temp.get(index);
	}

	public void initEmpty( MutableSet<String> empty ) 
	{
		this.explanationSets.add( empty );
	}
}
