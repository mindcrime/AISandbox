package org.fogbeam.experimental.reasoning.abductive.domain;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.SetAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Generator  implements Cloneable, Serializable
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(Generator.class);
	
	private static int instanceCount = 0;
	private int id;
	private MutableSet<MutableSet<String>> explanationSets = SetAdapter.adapt( new LinkedHashSet<MutableSet<String>>() );
	
	private boolean flagged;
	
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
		return "Generator(" + this.id + ") |" + this.explanationSets.size() + "| % " + this.explanationSets.makeString( "( ", " | ", " )" );
	}
	
	public int size()
	{
		return this.explanationSets.size();
	}

	public void addExplanationSet(MutableSet<String> q_kj) 
	{
		if( q_kj.isEmpty() )
		{
			logger.warn( "passed empty ExplanationSet, ignoring...");
			
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
	
	public void setFlagged(boolean flagged) 
	{
		this.flagged = flagged;
	}
	
	public boolean isFlagged() 
	{
		return flagged;
	}
	
	@Override
	public boolean equals( Object rhs ) 
	{
		if( !(rhs instanceof Generator))
		{
			return false;
		}
		Generator other = (Generator)rhs;
		
		return this.id == other.id;
	}
	
	@Override
	public int hashCode() 
	{
		return Integer.hashCode(this.id);
	}
	
	public Generator clone() throws CloneNotSupportedException
	{
		Generator clone = SerializationUtils.clone(this);
		
		return clone;
	}
}
