package fi.aalto.cs.drumbeat.ifc.convert;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

import fi.aalto.cs.drumbeat.ifc.common.IfcException;
import fi.aalto.cs.drumbeat.ifc.convert.RdfAsserter.FullResourceAsserter;
import fi.aalto.cs.drumbeat.ifc.convert.ifc2ld.Ifc2RdfConversionContext;
import fi.aalto.cs.drumbeat.ifc.convert.ifc2ld.Ifc2RdfConverter;
import fi.aalto.cs.drumbeat.ifc.data.IfcVocabulary;
import fi.aalto.cs.drumbeat.ifc.data.schema.IfcSchema;
import fi.aalto.cs.drumbeat.ifc.data.schema.IfcSelectTypeInfo;
import fi.aalto.cs.drumbeat.ifc.data.schema.DrbTypeInfo;

public class Test_Ifc2RdfConverter_Exporting_SelectTypes {
	
	private static IfcSchema ifcSchema;
	private Model jenaModel;
	private Ifc2RdfConversionContext context;
	private Ifc2RdfConverter converter;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DrumbeatTestHelper.init();
		ifcSchema = DrumbeatTestHelper.getTestIfcSchema();
	}
	

	@Before
	public void setUp() throws Exception {		
		context = new Ifc2RdfConversionContext();		
		converter = new Ifc2RdfConverter(context, ifcSchema);

		jenaModel = ModelFactory.createDefaultModel();		
		DrumbeatTestHelper.setNsPrefixes(jenaModel, converter);
	}
	
	private void compareWithExpectedResult(Resource actualTypeResource) throws IOException {
		
		boolean readExpectedModel = true;
		if (readExpectedModel) {
			String testFilePath = DrumbeatTestHelper.getTestFilePath(this, 2, true, "txt");
			Model expectedModel = DrumbeatTestHelper.readModel(testFilePath);
			Resource expectedTypeResource = expectedModel.getResource(actualTypeResource.getURI());
			assertNotNull(expectedTypeResource);
			
			FullResourceAsserter asserter = new FullResourceAsserter();
			asserter.assertEquals(expectedTypeResource, actualTypeResource);
		} else {
			String actualFilePath = DrumbeatTestHelper.getTestFilePath(this, 2, false, "txt");
			DrumbeatTestHelper.writeModel(jenaModel, actualFilePath);			
			throw new NotImplementedException("TODO: Compare with expected result");
		}
	}
	
	
	private void test_convert_IfcSelectTypeInfo(String typeName) throws IOException, IfcException {
		
		DrbTypeInfo typeInfo = ifcSchema.getNonEntityTypeInfo(typeName);
		
		assertEquals(IfcSelectTypeInfo.class, typeInfo.getClass());
		
		Resource typeResource = converter.convertSelectTypeInfo((IfcSelectTypeInfo)typeInfo, jenaModel);
		
		assertNotNull(typeResource);
		assertTrue(typeResource.isURIResource());
		assertEquals(typeResource.getLocalName(), typeInfo.getName());
		

		compareWithExpectedResult(typeResource);
	}
	


	@Test
	public void test_convert_IfcSelectTypeInfo_IfcValue() throws IOException, IfcException {		
		
		test_convert_IfcSelectTypeInfo("IfcValue");
		
	}	

}
