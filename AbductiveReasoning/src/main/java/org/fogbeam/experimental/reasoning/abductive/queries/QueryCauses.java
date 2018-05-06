package org.fogbeam.experimental.reasoning.abductive.queries;

import static org.fogbeam.experimental.reasoning.abductive.AbductionConstants1.RESOURCE_BASE;
import static org.fogbeam.experimental.reasoning.abductive.AbductionConstants1.TDB_DIR;

import java.io.File;
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
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.tdb.TDBFactory;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;

public class QueryCauses 
{
	public UnifiedSet<String> doQuery( final Set<String> manifestations )
	{
		UnifiedSet<String> causes = new UnifiedSet<String>();
		
		
		File tdbDir = new File(TDB_DIR);
		if( !tdbDir.exists())
		{
			tdbDir.mkdirs();
		}
		
		Dataset ds = TDBFactory.createDataset(TDB_DIR);
		Model model = ds.getDefaultModel();
		
		
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		InfModel infmodel = ModelFactory.createInfModel(reasoner, model );		
		

		for( String manifestation : manifestations )
		{
			
			UnifiedSet<String> disorders = new UnifiedSet<String>();
			
			// build up our SPARQL query for all entries in mPlus
			
			String sparqlQuery = "select ?disorder where {" + ( " ?disorder <" + RESOURCE_BASE + "#causes>" + " <" + RESOURCE_BASE + "/manifestation#" + manifestation + "> . }" );
					
		
			// System.out.println( "query: \n" + sparqlQuery + "\n" );
		
			// execute the query
			Query query = QueryFactory.create(sparqlQuery);
			QueryExecution qexec = QueryExecutionFactory.create(query, infmodel) ;
	
			try
			{
			    ResultSet resultSet = qexec.execSelect() ;
			    for ( ; resultSet.hasNext() ; )
			    {
			    	QuerySolution soln = resultSet.nextSolution() ;
			    	Resource m = soln.getResource("disorder");
			      
			    	String res = m.toString();
			    	// System.out.println( res );
		
					// plug query results in our results
			    	causes.add( res );
			    
			    }
			}
			finally
			{
				qexec.close();
			}			
		}
		
		infmodel.close();	

		ds.close();
		
		return causes;
	}
}
