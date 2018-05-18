package org.fogbeam.experimental.reasoning.abductive.queries;

import static org.fogbeam.experimental.reasoning.abductive.AbductionConstants1.RESOURCE_BASE;
import static org.fogbeam.experimental.reasoning.abductive.AbductionConstants1.TDB_DIR;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

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
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.tdb.TDBFactory;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;

public class QueryDiseasesStrategy 
{
	
	public UnifiedSet<String> listAll()
	{
		UnifiedSet<String> diseases = new UnifiedSet<String>();

		
		File tdbDir = new File(TDB_DIR);
		if( !tdbDir.exists())
		{
			tdbDir.mkdirs();
		}
		
		Dataset ds = TDBFactory.createDataset(TDB_DIR);
		Model model = ds.getDefaultModel();
		
		
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		InfModel infmodel = ModelFactory.createInfModel(reasoner, model );
		
		Property pCauses = model.createProperty(RESOURCE_BASE + "#causes" );
		
		ResIterator resIter = infmodel.listResourcesWithProperty(pCauses);
		
		while( resIter.hasNext() )
		{
			Resource d = resIter.nextResource();
			String res = d.toString();
			diseases.add(res);
		}

		infmodel.close();

		ds.close();

		
		return diseases;
	}
	
	public Set<String> doQuery( final String manifestation )
	{
		Set<String> diseases = new HashSet<String>();
		
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
		String queryString = "SELECT ?disease WHERE { <" + RESOURCE_BASE + "/manifestation#" + manifestation + "> <" + RESOURCE_BASE + "#hasCause> ?disease }" ;


		/* Now create and execute the query using a Query object */
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, infmodel) ;

		try
		{
		    ResultSet results = qexec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		    	QuerySolution soln = results.nextSolution() ;
		    	Resource d = soln.getResource("disease");
		      
		    	String res = d.toString();
		    	// System.out.println( res );
		    	diseases.add(res);
		    }
		}
		finally
		{
			qexec.close();
		}
			
		infmodel.close();

		ds.close();
		
		return diseases;
	}
}
