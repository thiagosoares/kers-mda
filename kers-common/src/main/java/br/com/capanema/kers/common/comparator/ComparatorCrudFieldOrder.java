package br.com.capanema.kers.common.comparator;

import java.util.Comparator;

import br.com.capanema.kers.common.model.template.CrudField;

public class ComparatorCrudFieldOrder implements Comparator<CrudField> {

	public int compare(CrudField crudField1, CrudField crudField2) {
		if (crudField1.getOrder() < crudField2.getOrder()) {
			return -1;
		} else if (crudField1.getOrder() > crudField2.getOrder()) {
			return +1;
		} else {
			return 0;
		}
	}

}
