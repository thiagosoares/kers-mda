package org.j2eespider.ide.data.domain;

public class CrudRelationship {
	private CrudClass relationshipTo;
	private CARDINALITY cardinality;
	
	public enum CARDINALITY  {
		ONE_TO_ONE;
	}
}
