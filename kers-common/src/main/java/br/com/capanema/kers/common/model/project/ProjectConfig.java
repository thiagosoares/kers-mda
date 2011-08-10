/*******************************************************************************
 * Copyright (c) 2006 Bruno G. Braga.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno G. Braga - initial API and implementation
 *******************************************************************************/
package br.com.capanema.kers.common.model.project;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import br.com.capanema.kers.common.model.template.Crud;
import br.com.capanema.kers.common.model.template.DomainSourceType;
import br.com.capanema.kers.common.model.template.SimpleSiteLayout;
import br.com.capanema.kers.common.model.template.Template;

import com.thoughtworks.xstream.annotations.XStreamAlias;



/**
 * Classe que manterá as configurações de um determinado projeto;
 * 
 * @author thiago 
 * 
 * adaptacao de ConfigSpider
 *
 */
@XStreamAlias("projectConfig")
public class ProjectConfig implements Cloneable {
	
	private String language;
	private String mainPackage;
	private String mainPackageDir;
	private List<String> modulesName;
	
	/*
	 * maven
	 */
	private String name;
	private String description;
	private String groupId;
	private String artifactId;
	private String version;
	
	/*
	 * DataBase Config
	 */
	
	private String dbUsername;
	private String dbPassword;
	private String dbHost;
	private int dbPort = -1;
	private String dbName;
	private String dbSchema;
	
	/*
	 * Techs
	 */
	
	private LinkedHashMap<String, List<String>> techs;    //TODO sera um enum
	private List<SimpleSiteLayout> siteLayout;  //TODO sera um enum
	
	/*
	 * Tempate Config
	 */
	private String tempateName;
	private String templateRealPath;
	private String sourceDestFolder;
	
	
	/*
	 * Domain Source
	 */
	private DomainSourceType domainSourceType; 
	private String domainPath;
	private List<Class<?>> domainScope;
	
	/**
	 * List of CRUDs 
	 * Sera setado pela aplicacao.
	 */
	private List<Crud> cruds = new ArrayList<Crud>();
	
	
	/**
	 * Initial contructor
	 */
	public ProjectConfig() {
		this.language = "en";
		this.mainPackage = "org.calando";
		
		List<String> modules = new ArrayList<String>();
		modules.add("m1");
		modules.add("m2");
		this.modulesName = modules;
		
		//this.databaseMapping = new DatabaseMapping();
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public void setArtifactId(String artifactId) {
    this.artifactId = artifactId;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getMainPackage() {
		return mainPackage;
	}
	
	public String getMainPackageDir() {
    //return mainPackageDir;
    return mainPackage.replaceAll("\\.", "/");
  }

  public void setMainPackageDir(String mainPackageDir) {
    this.mainPackageDir = mainPackageDir;
  }

  public void setMainPackage(String mainPackage) {
		this.mainPackage = mainPackage;
	}

	public List<String> getModulesName() {
		return modulesName;
	}

	public void setModulesName(List<String> modulesName) {
		this.modulesName = modulesName;
	}

	public String getSourceDestFolder() {
    return sourceDestFolder;
  }

  public void setSourceDestFolder(String sourceDestFolder) {
    this.sourceDestFolder = sourceDestFolder;
  }

  public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbHost() {
		return dbHost;
	}

	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	public int getDbPort() {
		return dbPort;
	}

	public void setDbPort(int dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	

	public String getTempateName() {
		return tempateName;
	}

	public void setTempateName(String tempateName) {
		this.tempateName = tempateName;
	}


	public LinkedHashMap<String, List<String>> getTechs() {
    return techs;
  }

  public void setTechs(LinkedHashMap<String, List<String>> techs) {
    this.techs = techs;
  }

  public List<Crud> getCruds() {
		//TODO: precisou disso para manter compatibilidade com o .spider antes do crud... Quando carregava esse arquivo, setava o atributo crud para null via reflection, tirar no futuro
		if (this.cruds == null) {
			this.cruds = new ArrayList<Crud>();
		}
		
		return this.cruds;
	}

	public void setCruds(List<Crud> cruds) {
		this.cruds = cruds;
	}

  public String getDbSchema() {
    return dbSchema;
  }

  public void setDbSchema(String dbSchema) {
    this.dbSchema = dbSchema;
  }

  public List<SimpleSiteLayout> getSiteLayout() {
    return siteLayout;
  }

  public void setSiteLayout(List<SimpleSiteLayout> siteLayout) {
    this.siteLayout = siteLayout;
  }

  public String getTemplateRealPath() {
    return templateRealPath;
  }

  public void setTemplateRealPath(String templateRealPath) {
    this.templateRealPath = templateRealPath;
  }

  public DomainSourceType getDomainSourceType() {
    return domainSourceType;
  }

  public void setDomainSourceType(DomainSourceType domainSourceType) {
    this.domainSourceType = domainSourceType;
  }

  public String getDomainPath() {
    return domainPath;
  }

  public void setDomainPath(String domainPath) {
    this.domainPath = domainPath;
  }

  public List<Class<?>> getDomainScope() {
    return domainScope;
  }

  public void setDomainScope(List<Class<?>> domainScope) {
    this.domainScope = domainScope;
  }


  
	
}
