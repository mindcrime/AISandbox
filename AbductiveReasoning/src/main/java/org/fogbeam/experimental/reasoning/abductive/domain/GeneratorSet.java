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

public class GeneratorSet implements Cloneable, Serializable
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(GeneratorSet.class);
	
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
		logger.debug( "in addGenerator(), gen passed in = " + gen );
		logger.debug( "in addGenerator(), existing generators = " + this.generators );
	
		if( gen.isEmpty() )
		{
			logger.warn( "passed an empty Generator, ignoring it..." );
			return;
		}
		
		boolean result = this.generators.add( gen );
		if( !result )
		{
			logger.error( "failed to add in addGenerator() call!" );
			throw new RuntimeException( "failed to add in addGenerator() call!" );
		}
		
	}
	
	public void addAllGenerators( final MutableSet<Generator> generators )
	{	
		logger.debug( "in addAllGenerators, generators already existing = " + this.generators );
		logger.debug( "in addAllGenerators, generators supplied = " + generators );
		
		boolean result = this.generators.addAll( generators );
		if( !result )
		{
			logger.warn( "failed to add in addAllGenerators() call!" );
			// throw new RuntimeException( "failed to add in addAllGenerators() call!" );
		}
	}
	
	public void unionInto( GeneratorSet temp )
	{
		this.generators = this.generators.union(temp.getGenerators());
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
		try
		{
			GeneratorSet resultGS = this.clone();
		
			boolean removeSucceeded = resultGS.generators.remove( qsubJ );
		
			if( !removeSucceeded )
			{
				logger.warn( "Failed to remove element " + qsubJ + " from GeneratorSet " + resultGS );
				throw new RuntimeException( "Failed to remove element " + qsubJ + " from GeneratorSet " + resultGS );
			}
		
			return resultGS;
		}
		catch( CloneNotSupportedException e )
		{
			throw new RuntimeException( e );
		}
	}
	
	public  boolean isEmpty()
	{
		return this.generators.isEmpty();
	}
	
	public int size()
	{
		return this.generators.size();
	}
	
	public String toString()
	{
		return "GeneratorSet(" + this.id + ") |" + this.generators.size() + "| : " + this.generators.makeString( "{ ", " # ", " }" );
	}
	
	@Override
	public GeneratorSet clone() throws CloneNotSupportedException 
	{
		GeneratorSet clone = SerializationUtils.clone(this);
		
		return clone;
	}
}
