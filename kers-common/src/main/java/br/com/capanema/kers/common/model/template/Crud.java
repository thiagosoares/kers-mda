package br.com.capanema.kers.common.model.template;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicitCollection;

@XStreamAlias("crud")
@XStreamImplicitCollection(value="classes", item="crudClass")
public class Crud {
	
  /*
   * Nome da classe responsável pelo crud
   */
	private String name;
	
	/*
	 * Modulo ao qual o crud pertence 
	 */
	private String moduleName;
	private CrudClass crudClass;
	private CrudRelationship crudRelationship;

	@Deprecated
	private String crudType;
	
	private HtmlLayout htmlLayout;
	
	public CrudClass getCrudClass() {
		return crudClass;
	}
	public void setCrudClass(CrudClass crudClass) {
		this.crudClass = crudClass;
	}
	public HtmlLayout getHtmlLayout() {
		return htmlLayout;
	}
	public void setHtmlLayout(HtmlLayout htmlLayout) {
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
	

}