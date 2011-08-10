package br.com.capanema.kers.tempate.engine.xml.magager;

public enum XmlType {

	
	GENERIC("project.xml"),
	TEMPLATE("template.xml"), 
	PROJECT_CONFIG("project.xml"), 
	TEMPLATE_LIST("templateList.xml"),
	REPOSITORY("repository.xml"),
	TEMPLATE_FILES("templateFiles.xml"), 
		
		TEMPLATE_FILES_JAR("templateFiles-jar.xml"),
		TEMPLATE_COMMAND(""), 
		DATABASE_TYPE_CONFIG("databaseTypeConfig.xml"),  
		SPIDER_BUILD(".spiderBuild"),
		VALIDATOR_TYPE("validatorConfig.xml"),
		HTML_TYPE("fieldConfig.xml"),
		FIELD_LOCATION("fieldLocation.xml"),
		REPOSITORY_TEMPLATE_INFO("");
	
	private String fileName;
	private XmlType(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}
}
