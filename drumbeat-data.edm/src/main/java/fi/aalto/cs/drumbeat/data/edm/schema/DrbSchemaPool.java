package fi.aalto.cs.drumbeat.data.edm.schema;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Container of {@link DrbSchema}s.
 * 
 * @author Nam Vu
 *
 */
@SuppressWarnings("serial")
public class DrbSchemaPool implements Serializable {
	
	private static Map<String, DrbSchema> schemas = new HashMap<>();
	
	/**
	 * Gets the map of schemas (schemaVersion --> schema) 
	 * @return
	 */
	public static Map<String, DrbSchema> getSchemas() {
		return schemas;
	}
	
	/**
	 * Gets the {@link DrbSchema} with the specified version.
	 * 
	 * @param version - the version of the schema 
	 * @return the {@link DrbSchema} with the specified version, or <code>null</code> if the version is not found.
	 */
	public static DrbSchema getSchema(String version) {
		return schemas.get(version);
	}
	
	
	/**
	 * Add a new {@link DrbSchema} using its default version string.
	 * 
	 * @param schema - the schema to be added.
	 */
	public static void addSchema(DrbSchema schema) {
		addSchema(schema.getName(), schema);
	}
	

	/**
	 * Add a new {@link DrbSchema} using the specified version string.
	 * 
	 * @param version - the version to be used as key of the schema.
	 * @param schema - the schema to be added.
	 */
	public static void addSchema(String version, DrbSchema schema) {
		schemas.put(version, schema);
	}
	
	
	/**
	 * Gets number of schemas
	 * @return
	 */
	public static int size() {
		return schemas.size();
	}
}
