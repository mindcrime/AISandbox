package org.fogbeam.example.ruleinduction.apriori;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import ca.pfv.spmf.algorithms.frequentpatterns.apriori.AlgoApriori;
import ca.pfv.spmf.patterns.itemset_array_integers_with_count.Itemsets;

public class AprioriExample1Main 
{

	public static void main(String[] args) throws Exception 
	{
		String filePath = getPath( "contextPasquier99.txt" );
		String output = null;
		
		// System.out.println( "filePath: " + filePath );
		
		double minsup = 0.4;
		
		AlgoApriori algorithm = new AlgoApriori();
		
		// Uncomment the following line to set the maximum pattern length (number of items per itemset, e.g. 3 )
		// apriori.setMaximumPatternLength(3);
		
		Itemsets result = algorithm.runAlgorithm(minsup, filePath, output);
		
		algorithm.printStats();
		
		int databaseSize = algorithm.getDatabaseSize();
		
		System.out.println( "databaseSize: " + databaseSize );
		
		result.printItemsets( databaseSize );
		
		System.out.println( "done" );
	}

	
	public static String getPath(String filename) throws UnsupportedEncodingException
	{
		// System.out.println( "filename: " + filename );
		URL url = Runtime.getRuntime().getClass().getClassLoader().getSystemResource(filename);
		String filePath = url.getPath();
		
		return java.net.URLDecoder.decode(filePath,"UTF-8");
	}
	
}
