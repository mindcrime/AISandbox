package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SafeListCopy
{
	public static void printWords( String[] word )
	{
		System.out.println( "word.length = " + word.length );
		for( int i = 0; i < word.length; i++ )
		{
			System.out.println( "word[" + i + "] = " + word[i] );
		}

		System.out.println( "\n\n" );
	}
	
	public static void main( String[] args )
	{
		
		// to be safe, only keep a reference to the *synchronized* list
		// so that you are sure that all accesses are controlled
		List<String> wordList = Collections.synchronizedList( new ArrayList<String>() );
		
		wordList.add( "Synchronization" );
		wordList.add( "is" );
		wordList.add( "important" );
		
		// first technique (favorite)
		String[] wordA = (String[]) wordList.toArray( new String[0] );
		
		printWords( wordA );
		
		// second technique
		String[] wordB;
		synchronized( wordList )
		{
			int size = wordList.size();
			wordB = new String[size];
			wordList.toArray( wordB );
		}
		printWords( wordB );
		
		// third technique (the 'synchronized' *is* necessary)
		String wordC[];
		synchronized(wordList)
		{
			wordC = (String[]) wordList.toArray( new String[wordList.size()] );
		}
		printWords( wordC );
		
		System.out.println( "done" );
	}
}
