package fi.aalto.cs.drumbeat.convert.bem2rdf;

import java.io.StringWriter;
import java.util.List;

import org.apache.jena.rdf.model.Model;

import fi.aalto.cs.drumbeat.common.meta.MetaClassUtils;
import fi.aalto.cs.drumbeat.data.bem.parsers.util.BemParserUtil;
import fi.aalto.cs.drumbeat.data.bem.schema.BemSchema;
import fi.aalto.cs.drumbeat.data.bem.schema.BemSchemaPool;
import fi.aalto.cs.drumbeat.data.ifc.parsers.IfcSchemaParser;

public class Test_Base {

	public static final boolean ALL_TESTS_WRITE_ACTUAL_DATASETS = true;
	public static final boolean ALL_TESTS_COMPARE_WITH_EXPECTED_DATASETS = false;

	public static final String TEST_RESOURCES_PATH ="src/test/resources/"; 
	
	public static final double DOUBLE_DELTA = 1e-15;
	
	public static final String CONFIG_FILE_PATH = "src/test/java/ifc2rdf-config.xml";
	public static final String LOGGER_CONFIG_FILE_PATH = "src/test/java/log4j.xml";
	
	public static final String TEST_SOURCE_RESOURCES_PATH = TEST_RESOURCES_PATH + "source/";

	public static final String TEST_IFC_SCHEMAS_FILE_PATH = TEST_SOURCE_RESOURCES_PATH + "schemas";
	public static final String TEST_IFC_MODEL_FILE_PATH = TEST_SOURCE_RESOURCES_PATH + "models/sample.ifc";
	
	public static final String TEST_TARGET_RESOURCES_PATH = TEST_RESOURCES_PATH + "target/";
	
	public static final String TEST_SCHEMA_NAME = "TestSchema";

	public static final String BUILT_IN_ONTOLOGY_NAMESPACE_PREFIX_FORMAT = "${Ontology.Language}";
	public static final String BUILT_IN_ONTOLOGY_NAMESPACE_URI_FORMAT = "http://drumbeat.cs.hut.fi/owl/${Ontology.Language}#";
	

	public static final String ONTOLOGY_NAMESPACE_PREFIX_FORMAT = "${Ontology.Name}";
	public static final String ONTOLOGY_NAMESPACE_URI_FORMAT = "http://drumbeat.cs.hut.fi/owl/${Ontology.Name}#";
	
	public static final String MODEL_NAMESPACE_PREFIX = "model";
	public static final String MODEL_NAMESPACE_URI_FORMAT = "http://architectural.drb.cs.hut.fi/collection1/model1/";
	
	public static final String MODEL_BLANK_NODE_NAMESPACE_URI_FORMAT = MODEL_NAMESPACE_URI_FORMAT + "B/";
	
	private static boolean initialized = false;
	
	public static void init() throws Exception {
		if (!initialized) {
			initialized = true;
//			DOMConfigurator.configure(LOGGER_CONFIG_FILE_PATH);			
//			ConfigurationDocument.load(CONFIG_FILE_PATH);
//			BemParserUtil.parseSchemas(TEST_IFC_SCHEMAS_FILE_PATH);
			
			BemParserUtil.getSchemaParsers().clear();
			BemParserUtil.registerSchemaParser(new IfcSchemaParser());
			List<BemSchema> schemas = BemParserUtil.parseSchemas(TEST_IFC_SCHEMAS_FILE_PATH, null, true, true);
			for (BemSchema schema : schemas) {
				BemSchemaPool.add(schema);			
			}
		}
	}
	
	private String getTestFilePath(int callingMethodCallShift, boolean isExpected, String extension) {
		return String.format("%s%s/%s/%s.%s",
				TEST_TARGET_RESOURCES_PATH,
				isExpected ? "expected" : "actual",
				getClass().getSimpleName(),
				MetaClassUtils.getCallingMethodName(callingMethodCallShift + 1),
				extension);
	}
	
	protected String getExpectedTestFilePath(int callingMethodCallShift, String extension) {
		return getTestFilePath(callingMethodCallShift + 1, true, extension);
	}
	
	protected String getActualTestFilePath(int callingMethodCallShift, String extension) {
		return getTestFilePath(callingMethodCallShift + 1, false, extension);
	}

//	public static BemSchema createTestBemSchema() {
//		return BemSchemaPool.getSchema(TEST_SCHEMA_NAME);
//	}
	
	protected void startTest(int callingMethodCallShift) {
		System.out.printf("%n[%s::%s()]%n",
				getClass().getSimpleName(),
				MetaClassUtils.getCallingMethodName(callingMethodCallShift + 1));		
	}
	
	protected StringBuffer writeAndCompareModel(
			int callingMethodCallShift,
			Model actualModel,
			boolean writeActualModel,
			boolean compareWithExpectedModel) throws Exception
	{
		
		writeActualModel &= ALL_TESTS_WRITE_ACTUAL_DATASETS;
		compareWithExpectedModel &= ALL_TESTS_COMPARE_WITH_EXPECTED_DATASETS;

		String actualModelFilePath = getActualTestFilePath(callingMethodCallShift + 1, "txt");
		String expectedModelFilePath = getExpectedTestFilePath(callingMethodCallShift + 1, "txt");
		
		
		StringWriter actualModelWriter = writeActualModel ? new StringWriter() : null;
		
		if (writeActualModel) {
			System.out.println("Writing Jena model: " + actualModelFilePath);
			TestHelper.writeModel(actualModel, actualModelFilePath, actualModelWriter);
		}
		
		
		if (compareWithExpectedModel) {
			Model expectedModel = TestHelper.readModel(expectedModelFilePath);
			RdfAsserter rdfAsserter = new RdfAsserter(r -> r.isAnon());
			rdfAsserter.assertEquals(expectedModel, actualModel);
		} else {
			String reminderMessage = String.format("Reminder: To compare files '%s' and '%s'", expectedModelFilePath, actualModelFilePath);
			System.err.println(reminderMessage);
//			throw new NotImplementedException(reminderMessage);
		}
		
		return actualModelWriter != null ? actualModelWriter.getBuffer() : null;
	}
	

}
