package org.fogbeam.experimental.reasonining.abductive;

import static org.fogbeam.experimental.reasonining.abductive.AbductionConstants1.RESOURCE_BASE;
import static org.fogbeam.experimental.reasonining.abductive.AbductionConstants1.TDB_DIR;

import java.io.File;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.OWL;

public class LoadDataToTriplestoremain 
{

	public static void main(String[] args) throws Exception
	{
		File tdbDir = new File(TDB_DIR);
		if( !tdbDir.exists())
		{
			tdbDir.mkdirs();
		}
		
		Dataset ds = TDBFactory.createDataset(TDB_DIR);
		Model model = ds.getDefaultModel();
		ds.begin(ReadWrite.WRITE);
		
		// model.begin();
		
		// create a property "causes" which relates a Disease to a Manifestation
		Property pCauses = model.createProperty( RESOURCE_BASE + "#causes");
		
		// create a property "hasCause" which is the inverse of "causes" - that is, it relates a Manifestation to a Disease
		Property pHasCause = model.createProperty( RESOURCE_BASE + "#hasCause" );
		Statement sCauseInverseHasCause = model.createStatement(pCauses, OWL.inverseOf, pHasCause);
		model.add(sCauseInverseHasCause);
		
		// create a few Diseases
		Resource d1 = model.createResource( RESOURCE_BASE + "/disease#d1" ); 
		Resource d2 = model.createResource( RESOURCE_BASE + "/disease#d2" ); 
		Resource d3 = model.createResource( RESOURCE_BASE + "/disease#d3" ); 
		Resource d4 = model.createResource( RESOURCE_BASE + "/disease#d4" ); 
		Resource d5 = model.createResource( RESOURCE_BASE + "/disease#d5" ); 
		
		// create a few Manifestations
		Resource m1 = model.createResource( RESOURCE_BASE + "/manifestation#m1" ); 
		Resource m2 = model.createResource( RESOURCE_BASE + "/manifestation#m2" ); 
		Resource m3 = model.createResource( RESOURCE_BASE + "/manifestation#m3" ); 
		Resource m4 = model.createResource( RESOURCE_BASE + "/manifestation#m4" ); 
		Resource m5 = model.createResource( RESOURCE_BASE + "/manifestation#m5" ); 
		
		
		// create the set C of "causes" that links a Disease to the Manifestations it can cause
		// (and, by the inverse relationship, the Manifestations to the Diseases that can cause them)
		
		Statement sD1CausesM1 = model.createStatement( d1, pCauses, m1 );
		model.add( sD1CausesM1 );
		
		Statement sD1CausesM2 = model.createStatement( d1, pCauses, m2 );
		model.add( sD1CausesM2 );
		
		
		Statement sD2CausesM1 = model.createStatement( d2, pCauses, m1 );
		model.add( sD2CausesM1 );
		
		Statement sD2CausesM4 = model.createStatement( d2, pCauses, m4 );
		model.add( sD2CausesM4 );
		
		Statement sD3CausesM2 = model.createStatement( d3, pCauses, m2 );
		model.add( sD3CausesM2 );
		
		Statement sD3CausesM4 = model.createStatement( d3, pCauses, m4 );
		model.add( sD3CausesM4 );
		
		
		Statement sD4CausesM3 = model.createStatement( d4, pCauses, m3 );
		model.add( sD4CausesM3 );
		
		Statement sD4CausesM5 = model.createStatement( d4, pCauses, m5 );
		model.add( sD4CausesM5 );
		
		Statement sD5CausesM4 = model.createStatement( d5, pCauses, m4 );
		model.add( sD5CausesM4 );
		
		Statement sD5CausesM5 = model.createStatement( d5, pCauses, m5 );
		model.add( sD5CausesM5 );
		
		
		ds.commit();
		ds.close();
		
		System.out.println( "done");

	}

}
