package fi.aalto.cs.drumbeat.data.bem.schema;

/**
 * Type information. Abstract class for all data types in DRUMBEAT EDM.
 *  
 * @author Nam Vu
 *
 */
public abstract class BemTypeInfo implements Comparable<BemTypeInfo> {
	
	private final BemSchema schema;
	private final String name;
	private boolean isDerivedType;
	private boolean isBuiltInType;
	
	/**
	 * Creates a new type info.
	 * 
	 */
	public BemTypeInfo(BemSchema schema, String name) {
		this.schema = schema;
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public BemSchema getSchema() {
		return schema;
	}
	
	public boolean isDerivedType() {
		return isDerivedType;
	}
	
	public void setDerivedType(boolean isDerivedType) {
		this.isDerivedType = isDerivedType;
	}
	
//	/**
//	 * Checks if the value is represented as a single value or as a collection of values.
//	 * @return True if the value is represented as a collection, otherwise False.
//	 */
//	public abstract boolean isCollectionType();
	
	/**
	 * Checks if the value type is entity or literal.
	 * @return True if the value is entity, False if the value is literal.
	 */
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
	
	@Override
	public int compareTo(BemTypeInfo o) {
		return name.compareTo(o.name);
	}
	
	public boolean isBuiltInType() {
		return this.isBuiltInType;
	}
	
	public void setBuiltInType(boolean isBuiltInType) {
		this.isBuiltInType = isBuiltInType;
	}
	
//	public abstract String getShortDescription(String typeNameFormat);
	
	public abstract BemValueKindEnum getValueKind();		
	
	
}
