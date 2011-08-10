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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("template")
public class Template {
	//constants
	public static final String DISABLED_FEATURE_CRUD_LAYOUT = "crud.layout";
	public static final String DISABLED_FEATURE_CRUD_MODULE = "crud.module";
	public static final String DISABLED_FEATURE_CRUD_DATA_TYPE = "crud.dataType";	
	public static final String DISABLED_FEATURE_CRUD_VALIDATION_APPLYIF = "crud.validation.applyIf";
	
	private String folder;
	/**
	 * List of template technologies
	 */
	private LinkedHashMap<String, List> tech;
	/**
	 * Default selected technologies
	 */
	private LinkedHashMap<String, List> techSelected;
	private List<String> crudTypeConfig;
	private List<SiteLayout> siteLayout;
	private Map<String, List> techLayout;
	private List<String> enabledPages;
	private List<String> disabledFeatures;
	private List<PluginRequired> requiredPlugins;
	private List<String> languages;
	
	/**
	 * TODO: remove in v 1.5. Deprecated code.
	 * @deprecated use HtmlType / styles
	 */
	private List<String> fieldStyles;
	
	public Template(String name, String description, LinkedHashMap<String, List> tech, LinkedHashMap<String, List> techSelected,
			List<String> techDatabaseType, List<String> techOthers,
			List<String> crudTypeConfig, List<SiteLayout> siteLayout, List<String> enabledPages,
			Map<String, List> techLayout) {
		super();
		this.folder = name;
		this.tech = tech;
		this.techSelected = techSelected;
		this.crudTypeConfig = crudTypeConfig;
		this.siteLayout = siteLayout;
		this.enabledPages = enabledPages;
		this.techLayout = techLayout;
	}
	
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	
	public List<String> getTech(String techName) {
		return tech.get(techName);
	}
	
	public Map<String, List> getTech() {
		return tech;
	}	
	
	public List<String> getTechSelected(String techName) {
		return techSelected.get(techName);
	}
	
	public LinkedHashMap<String, List> getTechSelected() {
		return techSelected;
	}

	public void setTechSelected(LinkedHashMap<String, List> techSelected) {
		this.techSelected = techSelected;
	}

	public void setTech(LinkedHashMap<String, List> tech) {
		this.tech = tech;
	}

	public List<String> getCrudTypeConfig() {
		return crudTypeConfig;
	}

	public void setCrudTypeConfig(List<String> crudTypeConfig) {
		this.crudTypeConfig = crudTypeConfig;
	}

	public List<SiteLayout> getSiteLayout() {
		return siteLayout;
	}

	public void setSiteLayout(List<SiteLayout> siteLayout) {
		this.siteLayout = siteLayout;
	}
	
	public List<String> getEnabledPages() {
		return enabledPages;
	}

	public void setEnabledPages(List<String> enabledPages) {
		this.enabledPages = enabledPages;
	}
	
	public List<String> getDisabledFeatures() {
		return disabledFeatures;
	}

	public void setDisabledFeatures(List<String> disabledFeatures) {
		this.disabledFeatures = disabledFeatures;
	}

	public Map<String, List> getTechLayout() {
		return techLayout;
	}
	
	public void setTechLayout(Map<String, List> techLayout) {
		this.techLayout = techLayout;
	}

	public List<PluginRequired> getRequiredPlugins() {
		return requiredPlugins;
	}

	public void setRequiredPlugins(List<PluginRequired> requiredPlugins) {
		this.requiredPlugins = requiredPlugins;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	
}
