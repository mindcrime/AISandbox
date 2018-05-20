package org.fogbeam.experimental.reasoning.abductive.kbmanagement;

import static org.fogbeam.experimental.reasoning.abductive.AbductionConstants1.TDB_DIR;

import java.io.File;
import java.lang.invoke.MethodHandles;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.tdb.TDBFactory;

public class ListTrivialKBRawStatementsMain 
{

	public static void main(String[] args) 
	{
		
		File tdbDir = new File( TDB_DIR + "/trivial" );
		if( !tdbDir.exists())
		{
			tdbDir.mkdirs();
		}
		
		Dataset ds = TDBFactory.createDataset( TDB_DIR + "/trivial" );
		Model model = ds.getDefaultModel();
		ds.begin(ReadWrite.READ);
		
		
		StmtIterator sIter = model.listStatements();
		
		while( sIter.hasNext() )
		{
			Statement s = sIter.nextStatement();
			System.out.println( "s: " + s.toString() );
		}
		
		
		ds.close();
		
		System.out.println( "done: " + MethodHandles.lookup().lookupClass() );

	}
}
