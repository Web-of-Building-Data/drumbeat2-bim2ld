package fi.aalto.cs.drumbeat.data.edm.schema;

public abstract class DrbNonCollectionTypeInfo extends DrbTypeInfo {

	public DrbNonCollectionTypeInfo(DrbSchema schema, String name) {
		super(schema, name);
	}
	
	@Override
	public boolean isCollectionType() {
		return false;
	}

}
