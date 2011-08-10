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
	 * Esse atributo � usado para criar dependencia entre um agrupamento de templates.
	 * Exemplo: um criar um agrupamento de jars jakarta-commons � usado em v�rias tecnologias. Ent�o � poss�vel declarar os jars s� uma vez. Evitando repetir e espalhar refencia ao mesmo JAR. 
	 */
	private String entryDepends;
	
	//@XStreamImplicit
	//private List<TemplateFileIncrement> increments;
	
	public TemplateFile(String pathTemplateFile) {
    super();
    this.pathTemplateFile = pathTemplateFile;
    this.pathGenerationFile = pathTemplateFile;
  }
	
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

	@Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((entryDepends == null) ? 0 : entryDepends.hashCode());
    result = prime * result + (excludeForce ? 1231 : 1237);
    result = prime * result + (excludeIfEmpty ? 1231 : 1237);
    result = prime * result + ((pathGenerationFile == null) ? 0 : pathGenerationFile.hashCode());
    result = prime * result + ((pathTemplateFile == null) ? 0 : pathTemplateFile.hashCode());
    result = prime * result + (run ? 1231 : 1237);
    result = prime * result + (skipIfExists ? 1231 : 1237);
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
    TemplateFile other = (TemplateFile) obj;
    if (entryDepends == null) {
      if (other.entryDepends != null)
        return false;
    } else if (!entryDepends.equals(other.entryDepends))
      return false;
    if (excludeForce != other.excludeForce)
      return false;
    if (excludeIfEmpty != other.excludeIfEmpty)
      return false;
    if (pathGenerationFile == null) {
      if (other.pathGenerationFile != null)
        return false;
    } else if (!pathGenerationFile.equals(other.pathGenerationFile))
      return false;
    if (pathTemplateFile == null) {
      if (other.pathTemplateFile != null)
        return false;
    } else if (!pathTemplateFile.equals(other.pathTemplateFile))
      return false;
    if (run != other.run)
      return false;
    if (skipIfExists != other.skipIfExists)
      return false;
    return true;
  }

  @Override
	public String toString() {
	  return "Template: " + pathTemplateFile + " To " + pathGenerationFile;
	}
	
}