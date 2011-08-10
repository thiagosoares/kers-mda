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
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("templateFile")
public class TemplateFile {
	private String pathTemplateFile;
	private String pathGenerationFile;
	private boolean skipIfExists;
	private boolean excludeIfEmpty;
	private boolean excludeForce;
	private boolean run;
	/**
	 * Esse atributo é usado para criar dependencia entre um agrupamento de templates.
	 * Exemplo: um criar um agrupamento de jars jakarta-commons é usado em várias tecnologias. Então é possível declarar os jars só uma vez. Evitando repetir e espalhar refencia ao mesmo JAR. 
	 */
	private String entryDepends;
	
	@XStreamImplicit
	private List<TemplateFileIncrement> increments;
	
	public TemplateFile(String pathTemplateFile, String pathGenerationFile) {
		super();
		this.pathTemplateFile = pathTemplateFile;
		this.pathGenerationFile = pathGenerationFile;
	}
	
	public String getPathGenerationFile() {
		return pathGenerationFile;
	}
	public void setPathGenerationFile(String pathGenerationFile) {
		this.pathGenerationFile = pathGenerationFile;
	}
	public String getPathTemplateFile() {
		if (pathTemplateFile != null) {
			return pathTemplateFile;
		} else {
			return "";
		}
	}
	public void setPathTemplateFile(String pathTemplateFile) {
		this.pathTemplateFile = pathTemplateFile;
	}
	public boolean isSkipIfExists() {
		return skipIfExists;
	}
	public void setSkipIfExists(boolean skipIfExists) {
		this.skipIfExists = skipIfExists;
	}
	public boolean isExcludeIfEmpty() {
		return excludeIfEmpty;
	}
	public void setExcludeIfEmpty(boolean excludeIfEmpty) {
		this.excludeIfEmpty = excludeIfEmpty;
	}
	public boolean isExcludeForce() {
		return excludeForce;
	}
	public void setExcludeForce(boolean excludeForce) {
		this.excludeForce = excludeForce;
	}
	public boolean isRun() {
		return run;
	}
	public void setRun(boolean run) {
		this.run = run;
	}
	public String getEntryDepends() {
		return entryDepends;
	}
	public void setEntryDepends(String entryDepends) {
		this.entryDepends = entryDepends;
	}
	public List<TemplateFileIncrement> getIncrements() {
		return increments;
	}
	public void setIncrements(List<TemplateFileIncrement> increments) {
		this.increments = increments;
	}
}
