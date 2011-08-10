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

@XStreamAlias("repository")
public class Repository {
	private String name;
	private List<RepositoryLink> links;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<RepositoryLink> getLinks() {
		return links;
	}
	public void setLinks(List<RepositoryLink> links) {
		this.links = links;
	}
}
