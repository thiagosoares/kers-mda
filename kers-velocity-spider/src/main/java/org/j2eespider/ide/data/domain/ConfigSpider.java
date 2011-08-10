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
package org.j2eespider.ide.data.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("configSpider")
public class ConfigSpider implements Cloneable {
	/**
	 * Language of the UI.
	 */
	private String language;
	
	/**
	 * Name of the Project
	 */
	private String appName;
	
	/**
	 * new.pkg.name of the ant build
	 */
	private String mainPackage;
	
	/**
	 * Name of each Module of the Project
	 */
	private List<String> modulesName;
	
	/**
	 * Database username.
	 */
	private String dbUsername;
	
	/**
	 * Database password
	 */
	private String dbPassword;
	
	/**
	 * Database host
	 */
	private String dbHost;
	
	/**
	 * Database port
	 */
	private int dbPort = -1;
	
	/**
	 * Database name
	 */
	private String dbName;
	
	/**
	 * Techs of this project
	 */
	private Map<String, String> tech;
	
	/**
	 * Others techs of the Project.
	 */
	private List<String> techOthers;
	
	/**
	 * Define what framework of layout will be used.
	 */
	private String techLayout;
	
	/**
	 * Location of template used for generate the Project
	 */
	private String templateFolder;
	
	/**
	 * Folder where is the tool put the source
	 */
	private String sourceFolder;
	
	/**
	 * Name of one or more skins for your site.
	 */
	private List<SimpleSiteLayout> siteLayout;
	
	/**
	 * List of CRUDs
	 */
	private List<Crud> cruds = new ArrayList<Crud>();
	
	/**
	 * Database mapping details.
	 */
	private DatabaseMapping databaseMapping;
	
	/**
	 * Initial contructor
	 */
	public ConfigSpider() {
		this.language = "en";
		this.mainPackage = "org.j2eespider";
		
		List<String> modules = new ArrayList<String>();
		modules.add("m1");
		modules.add("m2");
		this.modulesName = modules;
		
		this.templateFolder = null;
		
		this.databaseMapping = new DatabaseMapping();
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getMainPackage() {
		return mainPackage;
	}
	
	public String getMainPackageDir() {
		return mainPackage.replaceAll("\\.", "/");
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

	public String getTemplateFolder() {
		return templateFolder;
	}

	public void setTemplateFolder(String templateFolder) {
		this.templateFolder = templateFolder;
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
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
	
	public Map<String, String> getTech() {
		if (tech == null) {return new HashMap<String, String>();}
		return tech;
	}
	
	public String getTech(String techName) {
		if (tech == null) {return null;}
		return tech.get(techName);
	}

	public void setTech(Map<String, String> tech) {
		this.tech = tech;
	}

	public List<String> getTechOthers() {
		if (techOthers == null) {return new ArrayList<String>();}
		return techOthers;
	}

	public void setTechOthers(List<String> techOthers) {
		this.techOthers = techOthers;
	}

	public String getTechLayout() {
		return techLayout;
	}

	public void setTechLayout(String techLayout) {
		this.techLayout = techLayout;
	}

	public List<SimpleSiteLayout> getSiteLayout() {
		return siteLayout;
	}

	public void setSiteLayout(List<SimpleSiteLayout> siteLayout) {
		this.siteLayout = siteLayout;
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

	public DatabaseMapping getDatabaseMapping() {
		return databaseMapping;
	}

	public void setDatabaseMapping(DatabaseMapping databaseMapping) {
		this.databaseMapping = databaseMapping;
	}

	/**
	 * Copy of siteLayout
	 * @return
	 */
	public List<SimpleSiteLayout> getSiteLayoutClone() {
		if (siteLayout == null || siteLayout.size() == 0) {
			return null;
		}
		
		List<SimpleSiteLayout> listSimpleSiteLayout = new ArrayList<SimpleSiteLayout>();
		for (SimpleSiteLayout simpleSiteLayout : siteLayout) {
			listSimpleSiteLayout.add(simpleSiteLayout.clone());
		}
		
		return listSimpleSiteLayout;
	}
	
	public ConfigSpider clone() {
	   try {
		  if (this.getDatabaseMapping() == null) { //TODO: precisou disso para manter compatibilidade com o .spider quando não tinha o database mapping, tirar no futuro
			  this.setDatabaseMapping(new DatabaseMapping());
		  }
		  ConfigSpider configSpider = (ConfigSpider) super.clone();
		  
		  DatabaseMapping databaseMapping = configSpider.getDatabaseMapping().clone();
		  configSpider.setDatabaseMapping(databaseMapping);
		  
		  if (configSpider.getTech() != null && configSpider.getTech().size() > 0) {
			  Map<String, String> tech = (Map<String, String>) ((HashMap)configSpider.getTech()).clone();
			  configSpider.setTech(tech);	  
		  }
		  
		  if (configSpider.getTechOthers() != null && configSpider.getTechOthers().size() > 0) {
			  List<String> techOthers =  (List<String>) ((ArrayList)configSpider.getTechOthers()).clone();
			  configSpider.setTechOthers(techOthers);  
		  }
		  
		  if (configSpider.getModulesName() != null && configSpider.getModulesName().size() > 0) {
			  List<String> moduleName =  (List<String>) ((ArrayList)configSpider.getModulesName()).clone();
			  configSpider.setModulesName(moduleName);	  
		  }
		  
		  if (configSpider.getSiteLayout() != null && configSpider.getSiteLayout().size() > 0) {
			  configSpider.setSiteLayout(this.getSiteLayoutClone());	  
		  }
		  
	      return configSpider;
	   } catch (CloneNotSupportedException e) {
	      return null;
	   }
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((appName == null) ? 0 : appName.hashCode());
		result = PRIME * result + ((language == null) ? 0 : language.hashCode());
		result = PRIME * result + ((mainPackage == null) ? 0 : mainPackage.hashCode());
		result = PRIME * result + ((modulesName == null) ? 0 : modulesName.hashCode());
		result = PRIME * result + ((siteLayout == null) ? 0 : siteLayout.hashCode());
		result = PRIME * result + ((tech == null) ? 0 : tech.hashCode());
		result = PRIME * result + ((techOthers == null) ? 0 : techOthers.hashCode());
		result = PRIME * result + ((templateFolder == null) ? 0 : templateFolder.hashCode());
		result = PRIME * result + ((cruds == null) ? 0 : cruds.hashCode());
		result = PRIME * result + ((techLayout == null) ? 0 : techLayout.hashCode());
		result = PRIME * result + ((dbHost == null) ? 0 : dbHost.hashCode());
		result = PRIME * result + ((dbName == null) ? 0 : dbName.hashCode());
		result = PRIME * result + ((dbPassword == null) ? 0 : dbPassword.hashCode());
		result = PRIME * result + dbPort;
		result = PRIME * result + ((dbUsername == null) ? 0 : dbUsername.hashCode());
		result = PRIME * result + ((databaseMapping == null) ? 0 : databaseMapping.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ConfigSpider other = (ConfigSpider) obj;
		if (appName == null) {
			if (other.appName != null)
				return false;
		} else if (!appName.equals(other.appName))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (mainPackage == null) {
			if (other.mainPackage != null)
				return false;
		} else if (!mainPackage.equals(other.mainPackage))
			return false;
		if (modulesName == null) {
			if (other.modulesName != null)
				return false;
		} else if (!modulesName.equals(other.modulesName))
			return false;
		if (siteLayout == null) {
			if (other.siteLayout != null)
				return false;
		} else if (!siteLayout.equals(other.siteLayout))
			return false;
		if (tech == null) {
			if (other.tech != null)
				return false;
		} else if (!tech.equals(other.tech))
			return false;
		if (techOthers == null) {
			if (other.techOthers != null)
				return false;
		} else if (!techOthers.equals(other.techOthers))
			return false;
		if (templateFolder == null) {
			if (other.templateFolder != null)
				return false;
		} else if (!templateFolder.equals(other.templateFolder))
			return false;
		if (cruds == null) {
			if (other.cruds != null)
				return false;
		} else if (!cruds.equals(other.cruds))
			return false;		
		if (techLayout == null) {
			if (other.techLayout != null)
				return false;
		} else if (!techLayout.equals(other.techLayout))
			return false;
		if (dbHost == null) {
			if (other.dbHost != null)
				return false;
		} else if (!dbHost.equals(other.dbHost))
			return false;
		if (dbName == null) {
			if (other.dbName != null)
				return false;
		} else if (!dbName.equals(other.dbName))
			return false;
		if (dbPassword == null) {
			if (other.dbPassword != null)
				return false;
		} else if (!dbPassword.equals(other.dbPassword))
			return false;
		if (dbPort != other.dbPort)
			return false;
		if (dbUsername == null) {
			if (other.dbUsername != null)
				return false;
		} else if (!dbUsername.equals(other.dbUsername))
			return false;
		if (databaseMapping == null) {
			if (other.databaseMapping != null)
				return false;
		} else if (!databaseMapping.equals(other.databaseMapping))
			return false;
		if (sourceFolder == null) {
			if (other.sourceFolder != null)
				return false;
		} else if (!sourceFolder.equals(other.sourceFolder))
			return false;		
		
		return true;
	}
	
}
