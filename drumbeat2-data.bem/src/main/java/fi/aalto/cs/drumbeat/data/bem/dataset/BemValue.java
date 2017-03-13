package fi.aalto.cs.drumbeat.data.bem.dataset;

public abstract class BemValue {
	
	public boolean isSimpleValue() {
		return this instanceof BemSimpleValue;
	}
	
	public boolean isComplexValue() {
		return this instanceof BemComplexValue;
	}
	
	public boolean isSpecialVaue() {
		return this instanceof BemSpecialValue;
	}

	public boolean isCollectionValue() {
		return this instanceof BemCollectionValue;
	}

	public boolean isEntity() {
		return this instanceof BemEntity;
	}

}
