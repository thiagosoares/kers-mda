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

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Define as caracteristicas de um dos templates contidos em um repositorio
 * 
 * <templateSumary>
 *   <name>SPIDER JEE JSF</name>
 *   <folder>jee-jsf</folder>
 *   <description>This template was created by Gleyve Barros. It generates JEE code using JSF and Facelets. Create your templates too!</description>
 *   <techGroup>JEE</techGroup>
 *   <compatibilityDate>20091025111528</compatibilityDate>
 *   <url>http://www.j2eespider.org</url>
 *   <internalOnly>false</internalOnly>
 * </templateSumary> 
 * 
 * @author bruno.braga
 * 
 */
@XStreamAlias("templateSumary")
public class TemplateSumary {
	
	private String name;
	private String folder;
	private String description;
	private String version;
	private Boolean internalOnly;
	
	private String techGroup;  //????

	private String repositoryName;
	private String realPath;
	
	//Indica se este eh o template default do sistema
	private boolean isDefault;
	
	//TODO REMOVER
	//private long zipSize;
	private String compatibilityDate;
	
	
  public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTechGroup() {
		return techGroup;
	}
	public void setTechGroup(String techGroup) {
		this.techGroup = techGroup;
	}
	public String getCompatibilityDate() {
		return compatibilityDate;
	}
	public void setCompatibilityDate(String compatibilityDate) {
		this.compatibilityDate = compatibilityDate;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getRealPath() {
    return realPath;
  }
  public void setRealPath(String realPath) {
    this.realPath = realPath;
  }
	public Boolean isInternalOnly() {
		return internalOnly;
	}
	public void setInternalOnly(Boolean internalOnly) {
		this.internalOnly = internalOnly;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public Boolean getInternalOnly() {
		return internalOnly;
	}
  public String getRepositoryName() {
    return repositoryName;
  }
  public void setRepositoryName(String repositoryName) {
    this.repositoryName = repositoryName;
  }
	
}
