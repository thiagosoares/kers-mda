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
 * Template Repository Definition.
 * 
 * @author bruno.braga
 */
@XStreamAlias("repositoryLink")
public class RepositoryLink {
	private String urlTemplateInfo;
	private List urlTemplateInfoRequired;
	
	public String getUrlTemplateInfo() {
		return urlTemplateInfo;
	}
	public void setUrlTemplateInfo(String urlTemplateInfo) {
		this.urlTemplateInfo = urlTemplateInfo;
	}
	public List getUrlTemplateInfoRequired() {
		return urlTemplateInfoRequired;
	}
	public void setUrlTemplateInfoRequired(List urlTemplateInfoRequired) {
		this.urlTemplateInfoRequired = urlTemplateInfoRequired;
	}
}
