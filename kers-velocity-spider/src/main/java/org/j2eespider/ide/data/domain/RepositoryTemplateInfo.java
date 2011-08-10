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

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class define a new Template for SPIDER.
 * 
 * @author bruno.braga
 */
@XStreamAlias("repositoryTemplateInfo")
public class RepositoryTemplateInfo {
	private String name;
	private String folder;
	private String description;
	private String techGroup;
	public List<String> getTechs() {
		return techs;
	}
	public void setTechs(List<String> techs) {
		this.techs = techs;
	}
	private List<String> techs;
	private String compatibilityDate;
	private String version;
	private String url;
	private String urlMirror;
	private long zipSize;
	private Boolean internalOnly;
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlMirror() {
		return urlMirror;
	}
	public void setUrlMirror(String urlMirror) {
		this.urlMirror = urlMirror;
	}
	public long getZipSize() {
		return zipSize;
	}
	public void setZipSize(long zipSize) {
		this.zipSize = zipSize;
	}
	public Boolean isInternalOnly() {
		return internalOnly;
	}
	public void setInternalOnly(Boolean internalOnly) {
		this.internalOnly = internalOnly;
	}
}
