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
package br.com.capanema.kers.common.model.template;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.com.capanema.kers.common.model.project.ProjectConfig;

/**
 * Group of data to be used in the template.
 * @author bruno.braga
 */
public class TemplateDataGroup {
	
	private ProjectConfig config;
	
	private Crud crud;
	private Map databaseTypeConfig;
	private Map<String, String> bundle;
	private String pathTemplate;
	private Map<String, String> pathAllTemplates;
	
	private String pathFolderRepository;
	private String pathFolderGeneration;
	
	private List listBuilds;
	//private Map<String, List<String>> cacheAnnotatedClasses = new HashMap<String, List<String>>();
	//private static long lastCacheInformation;
	
	
	public ProjectConfig getConfig() {
		return config;
   	}
	
	public void setConfig(ProjectConfig configSpider) {
		this.config = configSpider;
	}
	public Crud getCrud() {
		return crud;
	}
	public void setCrud(Crud crud) {
		this.crud = crud;
	}
	public void setDatabaseTypeConfig(Map databaseTypeConfig) {
		this.databaseTypeConfig = databaseTypeConfig;
	}
	
	/*public String getConfigForDatabaseType(String nameConfig) {
		String config = databaseTypeConfig.get(nameConfig).toString();
		
		//pattern
        String patternStr = "\\[(.*?) default=(.*?)\\]";
        // Create the pattern
        Pattern pattern = Pattern.compile(patternStr, Pattern.DOTALL);
        Matcher matcher = pattern.matcher("");	

		matcher.reset(config);
		
		StringBuffer newConfig = new StringBuffer();
		while (matcher.find()) {
			if (matcher.group(1).equals("host")) {
				if (getConfig().getDbHost() != null && !getConfig().getDbHost().equals("")) {
					matcher.appendReplacement(newConfig, getConfig().getDbHost());
				} else {
					matcher.appendReplacement(newConfig, matcher.group(2)); //default value
				}
			}
			
			if (matcher.group(1).equals("name")) {
				if (getConfig().getDbName() != null && !getConfig().getDbName().equals("")) {
					matcher.appendReplacement(newConfig, getConfig().getDbName());
				} else {
					matcher.appendReplacement(newConfig, matcher.group(2)); //default value
				}
			}
			
			if (matcher.group(1).equals("port")) {
				if (getConfig().getDbPort() != -1) {
					matcher.appendReplacement(newConfig, String.valueOf(getConfig().getDbPort()));
				} else {
					matcher.appendReplacement(newConfig, matcher.group(2)); //default value
				}
			}
			
		}
		matcher.appendTail(newConfig);
		
		return newConfig.toString();
	}*/
	
	public Map<String, String> getBundle() {
		return bundle;
	}
	public void setBundle(Map<String, String> bundle) {
		this.bundle = bundle;
	}
	public String getPathTemplate() {
		return pathTemplate;
	}
	public void setPathTemplate(String pathTemplate) {
		this.pathTemplate = pathTemplate;
	}
	public String getPathTemplate(String folderName) {
		return pathAllTemplates.get(folderName);
	}
	public void setPathAllTemplates(Map<String, String> pathAllTemplates) {
		this.pathAllTemplates = pathAllTemplates;
	}
	public String getPathFolderRepository() {
		return pathFolderRepository;
	}
	public void setPathFolderRepository(String pathFolderTemplates) {
		this.pathFolderRepository = pathFolderTemplates;
	}
	public String getPathFolderGeneration() {
		return pathFolderGeneration;
	}
	public void setPathFolderGeneration(String pathFolderGeneration) {
		this.pathFolderGeneration = pathFolderGeneration;
	}
	public List getListBuilds() {
		return listBuilds;
	}
	public void setListBuilds(List listBuilds) {
		this.listBuilds = listBuilds;
	}

  public Map getDatabaseTypeConfig() {
    return databaseTypeConfig;
  }

  public Map<String, String> getPathAllTemplates() {
    return pathAllTemplates;
  }

  /**
	 * Search for annotated class in this project.
	 * @param annotation
	 * @return
	 */
	public List<String> getAnnotatedClasses(String annotation) {
		//check object cache
		//if (cacheAnnotatedClasses.get(annotation) != null) {
		//	return cacheAnnotatedClasses.get(annotation);
		//}
		
		List<String> listClass = new LinkedList<String>();
		
		//populate the filter
		ArrayList<String> listAnnotations = new ArrayList<String>();
		if (annotation != null && !annotation.equals("")) {
			listAnnotations.add(annotation);
		}
		
		//search for classes in the project
		//LinkedList<ResolvedSourceType> listClassFile = ClassSearchUtil.searchJavaFiles(listAnnotations);
		
		//get class full name
		//for (ResolvedSourceType classFile : listClassFile) {
		//	listClass.add(classFile.getFullyQualifiedName());
		//}		
		
		//cacheAnnotatedClasses.put(annotation, listClass);
		
		return listClass;
	}
}
