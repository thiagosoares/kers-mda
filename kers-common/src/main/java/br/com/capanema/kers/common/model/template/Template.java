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

import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Classe representará as informações de um template
 * 
 * template.xml
 * 
 * <template>
 * 
 * <name>SPIDER JEE JSF</name> <folder>jee-jsf</folder> <description>This
 * template was created by Gleyve Barros. It generates JEE code using JSF and
 * Facelets. Create your templates too!</description> <techGroup>JEE</techGroup>
 * <compatibilityDate>20091025111528</compatibilityDate>
 * <url>http://www.j2eespider.org</url> <internalOnly>false</internalOnly>
 * 
 * </template>
 * 
 * 
 */
@XStreamAlias("template_config")
public class Template {

	/*
	 * Descricao. Fonte para RepositoryTemplateInfo by xml
	 */
	private String name;
	private String description;
	private String version;
	private String techGroup;
	private String compatibilityDate;

	
	
	//Path Real do template. Configurado pela aplicacao
	private String realPath;
	private String rootFolder;
	private String repositoryName;
	
	/*
	 * Estrutura de arquivos
	 * 
	 * informaçoes levantadas pela aplicacao analizando o diretorio do template.
	 * Usa TemplateManager para a descoberta
	 */

	//Paths coringas 
	//private List<TemplateFile> coringasPath; //TODO traduzir
	
	// lista de folders do projeto
	private List<TemplateFolder> templateFolderes;

	// list tempalte files to replace e copy
	private List<TemplateFile> templateFiles;

	// list files to copy
	private List<TemplateFile> filesToCopy;

	
	private Map<String, List<TemplateFile>> mapTemplateFiles = null;

	public Template() {
		super();
		
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTechGroup() {
		return techGroup;
	}

	public void setTechGroup(String techGroup) {
		this.techGroup = techGroup;
	}


  public String getRealPath() {
    return realPath;
  }

  public void setRealPath(String realPath) {
    this.realPath = realPath;
  }

  public String getCompatibilityDate() {
    return compatibilityDate;
  }

  public void setCompatibilityDate(String compatibilityDate) {
    this.compatibilityDate = compatibilityDate;
  }

  public String getRepositoryName() {
    return repositoryName;
  }

  public void setRepositoryName(String repositoryName) {
    this.repositoryName = repositoryName;
  }

  public String getRootFolder() {
    return rootFolder;
  }

  public void setRootFolder(String rootFolder) {
    this.rootFolder = rootFolder;
  }

  public Map<String, List<TemplateFile>> getMapTemplateFiles() {
    return mapTemplateFiles;
  }

  public void setMapTemplateFiles(Map<String, List<TemplateFile>> mapTemplateFiles) {
    this.mapTemplateFiles = mapTemplateFiles;
  }

  public List<TemplateFolder> getTemplateFolderes() {
    return templateFolderes;
  }

  public void setTemplateFolderes(List<TemplateFolder> templateFolderes) {
    this.templateFolderes = templateFolderes;
  }

  public List<TemplateFile> getFilesToCopy() {
    return filesToCopy;
  }

  public void setFilesToCopy(List<TemplateFile> filesToCopy) {
    this.filesToCopy = filesToCopy;
  }

  public List<TemplateFile> getTemplateFiles() {
    return templateFiles;
  }

  public void setTemplateFiles(List<TemplateFile> templateFiles) {
    this.templateFiles = templateFiles;
  }

}