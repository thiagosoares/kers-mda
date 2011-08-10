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

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("plugin")
public class PluginRequired {
	private String shortName;
	private String minimumVersion;
	private String id;
	private String url;
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMinimumVersion() {
		if (minimumVersion == null || minimumVersion.equals("")) {
			return "0.0";
		}
		
		return minimumVersion;
	}
	public void setMinimumVersion(String minimumVersion) {
		this.minimumVersion = minimumVersion;
	}
}
