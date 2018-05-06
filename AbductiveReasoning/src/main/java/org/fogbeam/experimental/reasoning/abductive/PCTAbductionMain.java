package org.fogbeam.experimental.reasoning.abductive;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class PCTAbductionMain 
{

	public static void main(String[] args) throws Exception
	{

		// start our initial mPlus set
		UnifiedSet<String> mPlus = new UnifiedSet<String>();
		
		try( BufferedReader console = new BufferedReader( new InputStreamReader( System.in ) ) )
		{
		
		
			// prompt user for Manifestations
			// until user says 'q'
			String userInput = "";
			do
			{
				if( userInput != null && !userInput.isEmpty() )
				{
					mPlus.add(userInput);
				}
				
				System.out.println( "Enter a Manifestation ('q' to quit): " );
				userInput = console.readLine();
				
			} while( !userInput.equalsIgnoreCase("q"));
			
			
			// run through the PCT / BIPARTITE algorithm
			// generating all the plausible explanations
			
			
			
			
				
		}
		
		
		System.out.println( "Manifestations: " + mPlus );
		
		
		
		System.out.println( "done: " + MethodHandles.lookup().lookupClass() );
	}

}
