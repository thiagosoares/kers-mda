package br.com.capanema.kers.common.model.template;

public class CrudRelationship {
	
	private CrudClass relationshipTo;
	private CardinalityRelationship cardinality;
	
	public CrudRelationship() {
		super();
	}

	public CrudRelationship(CrudClass relationshipTo,
			CardinalityRelationship cardinality) {
		super();
		this.relationshipTo = relationshipTo;
		this.cardinality = cardinality;
	}

	public CrudClass getRelationshipTo() {
		return relationshipTo;
	}

	public void setRelationshipTo(CrudClass relationshipTo) {
		this.relationshipTo = relationshipTo;
	}

	public CardinalityRelationship getCardinality() {
		return cardinality;
	}

	public void setCardinality(CardinalityRelationship cardinality) {
		this.cardinality = cardinality;
	}
	
}
