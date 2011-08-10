package br.com.capanema.kers.common.model.template;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicitCollection;

@XStreamAlias("crudField")
@XStreamImplicitCollection(value="validators", item="validator")
public class CrudField {
	
	/*
	 * caracteristicas do campo
	 */
	private String name;
	
	/*
	 * nome da classe que esse campo pertence
	 */
	private String className;
	
	/*
	 * Tipo do campo
	 */
	private String dataType;

	/*
	 * new 
	 * Annotacoes
	 */
	private List<String> annotations;
	
	/**Default is Text.*/
	
	private int order;
	private boolean primaryKey = false;
	private boolean required = false;

	/**HTML access key*/
	private Character accessKey;
	
	//onde sera exibido
	private List<FieldLocation> locations = new ArrayList<FieldLocation>();
	
	//validacao
	//private List<CrudFieldValidator> validators = new ArrayList<CrudFieldValidator>();

	public CrudField(String name, String className, String dataType, Character defaultAccessKey, int defaultOrder) {
		super();
		this.name = name;
		this.className = className;
		this.dataType = dataType;
		this.accessKey = defaultAccessKey;
		this.order = defaultOrder;
		
		//set htmlType using dataType
		/*for (HtmlType htmlType : ConfigurationEditor.getHtmlTypes()) {
			if (htmlType.getMapTo() != null && (htmlType.getMapTo().contains(dataType.toLowerCase()) || htmlType.getMapTo().contains(dataType.toUpperCase()))) {
				this.htmlType = htmlType;
				break;
			}
		}*/
		
		//if the map from dataType to htmlType not found, set the first htmltype as default
		/*if (this.htmlType == null) {
			htmlType = ConfigurationEditor.getHtmlTypes().get(0);
		}*/
		
		/*if (this.getStylesAllowed().length > 0 && htmlType != null) {
			htmlType.setSelectedStyle(this.getStylesAllowed()[0]);
		}*/
		
		//get locations selected by default
		//locations = FieldLocation.filterSelectedByDefault(ConfigurationEditor.getFieldLocations());
	}

	public String getDataType() {
		return dataType;
	}
	
	public String getClassDataType() {
		if (dataType.equals("int")) {
			return "java.lang.Integer";
		}
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public List<FieldLocation> getLocations() {
		return locations;
	}

	public void setLocations(List<FieldLocation> locations) {
		this.locations = locations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Character getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(Character accessKey) {
		this.accessKey = accessKey;
	}
	
	public List<String> getAnnotations() {
    
	  annotations = new ArrayList<String>();
	  annotations.add("@id");
	  annotations.add("@colunm");
	  return annotations;
  }

  public void setAnnotations(List<String> annotations) {
    this.annotations = annotations;
  }

	/**
	 * Convert List to Array and return HTML Types
	 * @return
	 */
	/*public static String[] getNamesLocation() {
		List<FieldLocation> locations = ConfigurationEditor.getFieldLocations();
		
		String[] arrayLocations = new String[locations.size()];
		int i=0;
		for (FieldLocation location : locations) {
			arrayLocations[i] = location.getLocationName();
			i++;
		}
		
		return arrayLocations;
	}*/
	
	/*public static FieldLocation getLocationByName(String name) {
		for (FieldLocation location : ConfigurationEditor.getFieldLocations()) {
			if (location.getLocationName().equals(name)) {
				return location;
			}
		}
		
		return null;
	}*/	

	/**
	 * Convert List to Array and return HTML Types
	 * @return
	 */
	/*public static String[] getNamesHtmlType() {
		List<HtmlType> htmlTypes = ConfigurationEditor.getHtmlTypes();
		
		String[] types = new String[htmlTypes.size()];
		int i=0;
		for (HtmlType htmlType : htmlTypes) {
			types[i] = htmlType.getTypeName();
			i++;
		}
		
		return types;
	}*/
	

	/*public String[] getDataTypesAllowed() {
		List<String> dataType = new ArrayList<String>();
		dataType = this.htmlType.getMapTo();

		if (dataType == null || dataType.size() == 0) {
			return new String[] {};
		}
		
		String[] arrayDataTypes = new String[dataType.size()];
		return dataType.toArray(arrayDataTypes);
	}*/	
	
	/*public static String[] getAllDataTypes() {
		List<HtmlType> htmlTypes = ConfigurationEditor.getHtmlTypes();
		List<String> dataType = new ArrayList<String>();
		
		for (HtmlType htmlType : htmlTypes) {
			dataType.addAll(htmlType.getMapTo());
		}
		
		if (dataType == null || dataType.size() == 0) {
			return new String[] {};
		}		
		
		String[] arrayDataTypes = new String[dataType.size()];
		return dataType.toArray(arrayDataTypes);
	}	*/	
	
	/*public static HtmlType getHtmlType(String name) {
		for (HtmlType htmlType : ConfigurationEditor.getHtmlTypes()) {
			if (htmlType.getTypeName().equals(name)) {
				return htmlType;
			}
		}
		
		return null;
	}*/

	/*public String[] getStylesAllowed() {
		List<String> styles = new ArrayList<String>();
		styles = this.htmlType.getStyles();

		if (styles == null || styles.size() == 0) {
			return new String[] {};
		}
		
		String[] arrayStyles = new String[styles.size()];
		return styles.toArray(arrayStyles);
	}*/	
	
	

  /**
	 * Convert List into Array of Styles
	 * @return
	 */
	/*public static String[] getAllStyles() {
		List<HtmlType> htmlTypes = ConfigurationEditor.getHtmlTypes();
		List<String> styles = new ArrayList<String>();
		
		for (HtmlType htmlType : htmlTypes) {
			styles.addAll(htmlType.getStyles());
		}
		
		if (styles == null || styles.size() == 0) {
			return new String[] {};
		}		
		
		String[] arrayDataTypes = new String[styles.size()];
		return styles.toArray(arrayDataTypes);
	}*/
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CrudField) {
			CrudField objField = (CrudField)obj;
			
			if (this.getClassName().equals(objField.getClassName()) && this.getName().equals(objField.getName())) {
				return true;
			}
		}
		
		return false;
	}	
	
	/**
	 * Transforms "name" in "getName", "car" in "getCar"
	 * @return
	 */
	public String makeGetMethodName() {
		return "get"+name.substring(0, 1).toUpperCase()+name.substring(1);
	}
}
