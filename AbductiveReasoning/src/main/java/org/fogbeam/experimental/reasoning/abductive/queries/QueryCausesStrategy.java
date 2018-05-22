package org.fogbeam.experimental.reasoning.abductive.queries;

import static org.fogbeam.experimental.reasoning.abductive.AbductionConstants1.RESOURCE_BASE;

import java.io.File;
import java.util.LinkedHashSet;
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
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.SetAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryCausesStrategy 
{
	final static Logger logger = LoggerFactory.getLogger(QueryCausesStrategy.class);
	private String dir;
	
	public QueryCausesStrategy( final String dir )
	{
		this.dir = dir;
	}
	
	public MutableSet<String> doQuery( final String manifestation )
	{
		LinkedHashSet<String> backingSet = new LinkedHashSet<String>();
		MutableSet<String> causes = SetAdapter.adapt(backingSet);
	
		File tdbDir = new File(dir);
		if( !tdbDir.exists())
		{
			tdbDir.mkdirs();
		}
		
		Dataset ds = TDBFactory.createDataset(dir);
		Model model = ds.getDefaultModel();
		
		
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		InfModel infmodel = ModelFactory.createInfModel(reasoner, model );
		
		// build up our SPARQL query for all entries in mPlus
		
		String sparqlQuery = "select ?disorder where {" + ( " ?disorder <" + RESOURCE_BASE + "#causes>" + " <" + RESOURCE_BASE + "/manifestation#" + manifestation + "> . }" );
				
	
		// logger.debug( "query: \n" + sparqlQuery + "\n" );
	
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
		    	// logger.debug( res );
	
				// plug query results in our results
		    	causes.add( res );
		    
		    }
		}
		finally
		{
			qexec.close();
		}			
	
		infmodel.close();	

		ds.close();

	
		return causes;
	
	}
	
	public MutableSet<String> doQuery( final Set<String> manifestations )
	{
		LinkedHashSet<String> backingSet = new LinkedHashSet<String>();
		MutableSet<String> causes = SetAdapter.adapt(backingSet);		

		for( String manifestation : manifestations )
		{
			causes.addAll( this.doQuery( manifestation ) );
		}
		
		return causes;
	}
}
