package org.j2eespider.ide.data.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/*import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.internal.core.ResolvedSourceType;*/
import org.j2eespider.ide.data.comparator.ComparatorCrudFieldOrder;
import org.j2eespider.util.ClassSearchUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicitCollection;

@XStreamAlias("crudClass")
@XStreamImplicitCollection(value="fields", item="crudField")
public class CrudClass {
	private String fullName;
	private List<CrudField> fields = new LinkedList<CrudField>();

	public CrudClass(String fullName) {
		super();
		this.fullName = fullName;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getName() {
    	int posDiv = fullName.lastIndexOf(".");
    	
    	return fullName.substring(posDiv+1); //last word
	}
	public String getPackage() {
    	int posDiv = fullName.lastIndexOf(".");
    	
    	return fullName.substring(0, posDiv);
	}
	public List<CrudField> getFields() {
		return fields;
	}
	/**
	 * Returns the fields in order.
	 * @return
	 */
	public List<CrudField> getFieldsInOrder() {
		Comparator<CrudField> comparator = new ComparatorCrudFieldOrder();
		Collections.sort(fields, comparator);
		
		return fields;
	}
	
	/**
	 * Returns the fields in order filtering by location
	 * @return
	 */
	public List<CrudField> getFieldsInOrderByLocation(String[] locationIds) {
		List<CrudField> fieldsInLocation = new ArrayList<CrudField>();
		
		//filter		
		for (String locationId : locationIds) {
			FieldLocation location = new FieldLocation(locationId);
			
			for (CrudField field : fields) {
				if (field.getLocations().contains(location) && !fieldsInLocation.contains(field)) {
					fieldsInLocation.add(field);
				}
			}			
		}
		
		//sort
		Comparator<CrudField> comparator = new ComparatorCrudFieldOrder();
		Collections.sort(fieldsInLocation, comparator);
		
		return fieldsInLocation;
	}
	
	public void setFields(List<CrudField> fields) {
		this.fields = fields;
	}
	
	/**
	 * Return the CrudField of primary key of this CrudClass
	 * @return
	 */
	public CrudField getPrimaryCrudField() {
		for (CrudField field : fields) {
			if (field.isPrimaryKey()) {
				return field;
			}
		}
		
		return null;
	}
	
	public void addField(CrudField crudField) {
		if (this.fields == null) {
			this.fields = new ArrayList<CrudField>();
		}
		
		this.fields.add(crudField);
	}
	public void removeField(CrudField crudField) {
		if (this.fields == null) {
			return;
		}
		
		this.fields.remove(crudField);
	}
	
	/**
	 * Synchronize and removes from array fields that were removed from the class.
	 */
	public void syncFields() {
		/*try {
		    //procura a classe
		    ResolvedSourceType classFile = ClassSearchUtil.searchExactJavaFile(fullName);
		    if (classFile != null) {
		    	List<CrudField> listToRemove = new ArrayList<CrudField>();
	    		for (CrudField crudField : fields) {
	    			boolean itemFound = false;
		    		for (IField field : classFile.getFields()) {
		    			if (crudField.getName().equals(field.getElementName())) {
		    				itemFound = true;
		    				break;
		    			}
		    		}
		    		if (!itemFound) {
		    			listToRemove.add(crudField);
		    		}
	    		}
	    		
	    		//remove fields that were not found in class
	    		for (CrudField crudField : listToRemove) {
	    			removeField(crudField);
	    		}
		    }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}*/
	}
	
	/**
	 * Calc and return default access key for field.
	 * The access key may not repeat, then the keys of all field are analyzed.
	 * 
	 * @param fieldName
	 * @return
	 */
	public Character getFieldAccessKeyDefault(String fieldName) {
		char[] alphabet = new char[] {'a','b','c','d','e','f','g','h','i','j','l','k','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		List<String> lettersFound = new ArrayList<String>();
		//create list with letters found
		for (CrudField field : fields) {
			if (field.getAccessKey() != null) {
				lettersFound.add(field.getAccessKey().toString());
			}
		}
		//search letters of fieldName in List
		for (char letter : fieldName.toCharArray()) {
			if (!lettersFound.contains(String.valueOf(letter))) {
				return new Character(String.valueOf(letter).toLowerCase().charAt(0));
			}
		}
		//all letters of fieldName are being used. Define new letter
		for (char letter : alphabet) {
			if (!lettersFound.contains(String.valueOf(letter))) {
				return new Character(letter);
			}
		}
		
		return null;
	}
	
	/**
	 * Check if a accessKey already in use.
	 * @param accessKey
	 * @return
	 */
	public boolean isFieldAccessKeyInUse(Character accessKey) {
		for (CrudField field : fields) {
			if (field.getAccessKey() != null && field.getAccessKey().equals(accessKey)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Check if a order already in use.
	 * @param order
	 * @return
	 */
	public boolean isFieldOrderInUse(int order) {
		for (CrudField field : fields) {
			if (field.getOrder() == order) {
				return true;
			}
		}
		
		return false;
	}
}
