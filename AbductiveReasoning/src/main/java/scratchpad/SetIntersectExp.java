package scratchpad;

import java.lang.invoke.MethodHandles;
import java.util.LinkedHashSet;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.SetAdapter;

public class SetIntersectExp 
{

	public static void main(String[] args) 
	{
		LinkedHashSet<String> setOneBackingSet = new LinkedHashSet<String>();
		LinkedHashSet<String> setTwoBackingSet = new LinkedHashSet<String>();

		
		MutableSet<String> setOne = SetAdapter.adapt( setOneBackingSet );
		MutableSet<String> setTwo = SetAdapter.adapt( setTwoBackingSet );
		
		setOne.add( "d1" );
		setOne.add( "d2" );
		
		setTwo.add( "d1" );
		setTwo.add( "d3" );
		
		System.out.println( "setOne ∩ setTwo = " + setOne.intersect( setTwo ) );
		
		
		System.out.println( "\ndone - " +  MethodHandles.lookup().lookupClass() );
		
		
	}

}
