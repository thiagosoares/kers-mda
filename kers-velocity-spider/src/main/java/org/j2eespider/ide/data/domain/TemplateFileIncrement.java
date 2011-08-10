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

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class defines the increments of the code generation. 
 * Some files can be enhanced in place of the new generation.
 * 
 * @author bruno.braga
 */
@XStreamAlias("increment")
public class TemplateFileIncrement {
	private String pattern;
	private String path;
	
	/**Define any params to send to fragment.*/
	private Map<String, String> params;
	
	/**	If there is not yet fragments like this in the file, sets a any pattern for mark the point where it should be inserted fragment in file. */
	private String firstAfter;
	
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public String getFirstAfter() {
		return firstAfter;
	}
	public void setFirstAfter(String firstAfter) {
		this.firstAfter = firstAfter;
	}
}
