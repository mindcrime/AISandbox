package org.fogbeam.experimental.reasoning.tangential;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PowerSetExp 
{
	final static Logger logger = LoggerFactory.getLogger(PowerSetExp.class);
	
	public static void main(String[] args) 
	{

		Set<String> aSet =  new HashSet<String>();
		String[] data = { "a", "b", "c", "d", "e", "f" };
		aSet.addAll( Arrays.asList( data ) );
		
		Set<Set<String>> powerSet = powerSet( aSet );
		
		for( Set<String> subset : powerSet )
		{
			System.out.print( "{ ");
			subset.forEach( (k) -> { System.out.print( k + " " ); } ); 
			logger.debug( "}");
		}
		
		logger.debug( "done" );
		
	}
	
	
	public static Set<Set<String>> powerSet(Set<String> originalSet) {
	    Set<Set<String>> sets = new HashSet<Set<String>>();
	    if (originalSet.isEmpty()) {
	    	Set<String> empty = new HashSet<String>();
	    	sets.add(empty);
	        return sets;
	    }
	    
	    List<String> list = new ArrayList<String>(originalSet);
	    String head = list.get(0);
	    Set<String> rest = new HashSet<String>(list.subList(1, list.size())); 
	    for (Set<String> set : powerSet(rest)) {
	        Set<String> newSet = new HashSet<String>();
	        newSet.add(head);
	        newSet.addAll(set);
	        sets.add(newSet);
	        sets.add(set);
	    }       
	    
	    return sets;
	} 	
}