package fi.aalto.cs.drumbeat.data.bedm.schema;

public class DrbPrimitiveTypeInfo extends DrbSimpleNonCollectionTypeInfo {

	private final DrbTypeEnum valueType;
	
	public DrbPrimitiveTypeInfo(DrbSchema schema, String name, DrbTypeEnum valueType) {
		super(schema, name);
		this.valueType = valueType;
	}
	
	public DrbTypeEnum getValueType() {
		return valueType;
	}

}
