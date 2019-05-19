package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SafeCollectionIteration
{
	public static void main( String[] args )
	{
		
		// to be safe, only keep a reference to the *synchronized* list
		// so that you are sure that all accesses are controlled
		List<String> wordList = Collections.synchronizedList( new ArrayList<String>() );
		
		wordList.add( "Iterators" );
		wordList.add( "require" );
		wordList.add( "special" );
		wordList.add( "handling" );
		
		// all of this must be done in a synchronized block to
		// block other threads from modifying wordList while
		// the iteration is in progress
		synchronized( wordList )
		{
			Iterator<String> iter = wordList.iterator();
			while( iter.hasNext() )
			{
				String s = iter.next();
				System.out.println( "found string: " + s + ", length = " + s.length() );
			}
		}
		
		System.out.println( "done" );
	}
}
