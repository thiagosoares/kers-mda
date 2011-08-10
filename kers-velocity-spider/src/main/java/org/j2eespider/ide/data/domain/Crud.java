package org.j2eespider.ide.data.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicitCollection;

@XStreamAlias("crud")
@XStreamImplicitCollection(value="classes", item="crudClass")
public class Crud {
	private String name;
	private String crudType;
	private CrudClass crudClass;
	private HTML_LAYOUT htmlLayout;
	private String moduleName;
	private CrudRelationship crudRelationship;
	
	public CrudClass getCrudClass() {
		return crudClass;
	}
	public void setCrudClass(CrudClass crudClass) {
		this.crudClass = crudClass;
	}
	public HTML_LAYOUT getHtmlLayout() {
		return htmlLayout;
	}
	public void setHtmlLayout(HTML_LAYOUT htmlLayout) {
		this.htmlLayout = htmlLayout;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCrudType() {
		return crudType;
	}
	public void setCrudType(String crudType) {
		this.crudType = crudType;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public CrudRelationship getCrudRelationship() {
		return crudRelationship;
	}
	public void setCrudRelationship(CrudRelationship crudRelationship) {
		this.crudRelationship = crudRelationship;
	}
	
	public enum HTML_LAYOUT {
		LL_1C,
		LT_1C,
		LL_2C,
		LT_2C;
	}

}