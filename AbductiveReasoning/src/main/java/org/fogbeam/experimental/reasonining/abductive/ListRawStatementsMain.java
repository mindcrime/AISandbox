package org.fogbeam.experimental.reasonining.abductive;

import static org.fogbeam.experimental.reasonining.abductive.AbductionConstants1.TDB_DIR;

import java.io.File;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.tdb.TDBFactory;

public class ListRawStatementsMain 
{

	public static void main(String[] args) 
	{
		
		File tdbDir = new File(TDB_DIR);
		if( !tdbDir.exists())
		{
			tdbDir.mkdirs();
		}
		
		Dataset ds = TDBFactory.createDataset(TDB_DIR);
		Model model = ds.getDefaultModel();
		ds.begin(ReadWrite.READ);
		
		
		StmtIterator sIter = model.listStatements();
		
		while( sIter.hasNext() )
		{
			Statement s = sIter.nextStatement();
			System.out.println( "s: " + s.toString() );
		}
		
		
		ds.close();
		
		System.out.println( "done");

	}
}
