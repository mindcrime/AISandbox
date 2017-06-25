package org.fogbeam.experimental.reasonining.abductive;

import static org.fogbeam.experimental.reasonining.abductive.AbductionConstants1.*;

import java.io.File;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.tdb.TDBFactory;

public class QueryModelMain1 
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
		
		
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		InfModel infmodel = ModelFactory.createInfModel(reasoner, model );

		
		/* Do a SPARQL Query over the data in the model */
		String queryString = "SELECT ?manifestation WHERE { <" + RESOURCE_BASE + "/disease#d1> <" + RESOURCE_BASE + "#causes> ?manifestation }" ;


		/* Now create and execute the query using a Query object */
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, infmodel) ;

		try
		{
		    ResultSet results = qexec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		    	QuerySolution soln = results.nextSolution() ;
		    	Resource m = soln.getResource("manifestation");
		      
		    	String res = m.toString();
		    	System.out.println( res );
		    }
		}
		finally
		{
			qexec.close();
		}
			
		infmodel.close();
		
		System.out.println( "\n---------------\n" );		

		ds.close();
		
		System.out.println( "done" );

	}

}
