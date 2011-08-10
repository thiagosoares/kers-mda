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

@XStreamAlias("command")
public class TemplateCommand {
	/** Command to exec	 */
	private String exec;
	/** Dir to exec this command */
	private String dir;
	private Map<String, String> interact;
	
	public String getExec() {
		return exec;
	}
	public void setExec(String exec) {
		this.exec = exec;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public Map<String, String> getInteract() {
		return interact;
	}
	public void setInteract(Map<String, String> interact) {
		this.interact = interact;
	}
}
