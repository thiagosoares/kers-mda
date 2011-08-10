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


public class TemplateFolder {
  
	private String pathTemplateFolder;
	private String pathGenerationFolder;
	private boolean skipIfExists;
	private boolean excludeIfEmpty;
	private boolean excludeForce;
	
	public TemplateFolder(String folder, String folderDest) {
		super();
		this.pathTemplateFolder = folder;
		this.pathGenerationFolder = folderDest;
	}
	
	public String getPathGenerationFolder() {
		return pathGenerationFolder;
	}
	public void setPathGenerationFolder(String pathGenerationFolder) {
		this.pathGenerationFolder = pathGenerationFolder;
	}
	public String getPathTemplateFolder() {
		if (pathTemplateFolder != null) {
			return pathTemplateFolder;
		} else {
			return "";
		}
	}
	public void setPathTemplateFolder(String pathTemplateFolder) {
		this.pathTemplateFolder = pathTemplateFolder;
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

	@Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (excludeForce ? 1231 : 1237);
    result = prime * result + (excludeIfEmpty ? 1231 : 1237);
    result = prime * result + ((pathGenerationFolder == null) ? 0 : pathGenerationFolder.hashCode());
    result = prime * result + ((pathTemplateFolder == null) ? 0 : pathTemplateFolder.hashCode());
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
    TemplateFolder other = (TemplateFolder) obj;
    if (excludeForce != other.excludeForce)
      return false;
    if (excludeIfEmpty != other.excludeIfEmpty)
      return false;
    if (pathGenerationFolder == null) {
      if (other.pathGenerationFolder != null)
        return false;
    } else if (!pathGenerationFolder.equals(other.pathGenerationFolder))
      return false;
    if (pathTemplateFolder == null) {
      if (other.pathTemplateFolder != null)
        return false;
    } else if (!pathTemplateFolder.equals(other.pathTemplateFolder))
      return false;
    if (skipIfExists != other.skipIfExists)
      return false;
    return true;
  }

  @Override
	public String toString() {
	  return "Template: " + pathTemplateFolder + " To " + pathGenerationFolder;
	}
	
}