package org.fogbeam.experimental.reasoning.abductive.kbmanagement;

import static org.fogbeam.experimental.reasoning.abductive.AbductionConstants1.RESOURCE_BASE;
import static org.fogbeam.experimental.reasoning.abductive.AbductionConstants1.TDB_DIR;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.tdb.TDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisualizeAbstractKBMain 
{

	final static Logger logger = LoggerFactory.getLogger(VisualizeAbstractKBMain.class);
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		
		File tdbDir = new File( TDB_DIR + "/abstract" );
		if( !tdbDir.exists())
		{
			tdbDir.mkdirs();
		}
		
		Dataset ds = TDBFactory.createDataset( TDB_DIR + "/abstract" );
		Model model = ds.getDefaultModel();
		ds.begin(ReadWrite.READ);

		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		InfModel infmodel = ModelFactory.createInfModel(reasoner, model );		

		
		// find all the causal links in the KB		
		String sparqlQuery = "select ?disorder ?manifestation where {" + ( " ?disorder <" + RESOURCE_BASE + "#causes>" + " ?manifestation . }" );
		
		
		// logger.debug( "query: \n" + sparqlQuery + "\n" );
	
		// execute the query
		Query query = QueryFactory.create(sparqlQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, infmodel) ;

		// get all the resources on the LHS (the disorders)		
		List<String> disorders = new ArrayList<String>();
		
		// get all the resources on the RHS (the manifestations)
		List<String> manifestations = new ArrayList<String>();
		
		Set<Link> links = new HashSet<Link>();
		
		
		try
		{
		    ResultSet resultSet = qexec.execSelect() ;
		    for ( ; resultSet.hasNext() ; )
		    {
		    	QuerySolution soln = resultSet.nextSolution() ;
		    	Resource d = soln.getResource( "disorder" );
		    	Resource m = soln.getResource( "manifestation") ;
		    	String disorder = d.toString();
		    	String manifestation = m.toString();
		    	
		    	logger.debug( disorder + " causes " + manifestation );
		    	
		    	if( !disorders.contains( disorder ) )
		    	{
		    		disorders.add( disorder );
		    	}
		    	
		    	if( !manifestations.contains( manifestation ) )
		    	{
		    		manifestations.add( manifestation );
		    	}
		    	
		    	Link link = new Link( disorder, manifestation );
		    	links.add( link );
		    }
		}
		finally
		{
			qexec.close();
		}			
	
		logger.debug( "Links: " + links );
		
	
		
		
		infmodel.close();	

		ds.close();
		
		File dotFile = new File( "./abstract.dot" );
		if( dotFile.exists() )
		{
			dotFile.delete();
		}
		
		
		try( FileWriter dotFileWriter = new FileWriter( dotFile ) )
		{
		
			
			
			dotFileWriter.write( "digraph \"abstract\" {\n" );
			
			for( String disorder: disorders )
			{
				logger.debug( "adding DOT vertex for disorder: " + disorder );
				String disorderId = StringUtils.substringAfter(disorder, "#");
				dotFileWriter.write( disorderId + " [id=\"" + disorderId + "\",label=\"" + disorderId + "\"]"+"\n" );
				dotFileWriter.flush();
			}
			
			for( String manifestation : manifestations )
			{
				logger.debug( "adding DOT vertext for manifestation: " + manifestation );
				String manifestationId = StringUtils.substringAfter(manifestation, "#");
				dotFileWriter.write( manifestationId + " [id=\"" + manifestationId + "\",label=\"" + manifestationId + "\"]"+"\n" );
				dotFileWriter.flush();
				
			}
			
			for( Link link : links )
			{
				dotFileWriter.write( StringUtils.substringAfter(link.getLeft(), "#" ) + " -> " + 
									 StringUtils.substringAfter(link.getRight(), "#" ) + "\n");
			}
			
			
			dotFileWriter.write( "}" );
			
			dotFileWriter.flush();
		
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		
		
		// create a DOT file with vertices for all disorders and manifestations
		// and edges pointing from the disorders to the manifestations
		
		// invoke a graphviewer on the dot file we just created
		// kgraphviewer, etc.
		ProcessBuilder pb = new ProcessBuilder();
		pb.command( "kgraphviewer", "./abstract.dot");
		Process kgraphProcess = pb.start();

		kgraphProcess.waitFor();
		
		logger.debug( "done: " + MethodHandles.lookup().lookupClass() );

	}

	static class Link extends Pair<String,String>
	{
		
		private static final long serialVersionUID = 1L;
		private String left;
		private String right;
		
		public Link( final String left, final String right )
		{
			this.left = left;
			this.right = right;
		}
		
		@Override
		public String getLeft() 
		{
			return this.left;
		}

		@Override
		public String getRight() 
		{
			return this.right;
		}

		@Override
		public String setValue(String value) 
		{
			this.right = value;
			return value;
		}
	}
}
