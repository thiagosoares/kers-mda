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

@XStreamAlias("layout")
public class SiteLayout {
	private String name;
	private String keyDescription;
	private boolean isSetFirstColor;
	private boolean isSetSecondColor;
	private boolean isSetThirdColor;
	private boolean isSetTextColor;
	
	private List<LayoutColor> layoutColor;

	public SiteLayout(String name, String description, boolean isSetFirstColor, boolean isSetSecondColor, boolean isSetThirdColor, boolean isSetTextColor, List<LayoutColor> layoutColor) {
		super();
		this.name = name;
		this.keyDescription = description;
		this.isSetFirstColor = isSetFirstColor;
		this.isSetSecondColor = isSetSecondColor;
		this.isSetThirdColor = isSetThirdColor;
		this.isSetTextColor = isSetTextColor;
		this.layoutColor = layoutColor;
	}
	

	public String getKeyDescription() {
		return keyDescription;
	}
	public void setKeyDescription(String keyDescription) {
		this.keyDescription = keyDescription;
	}
	public boolean isSetFirstColor() {
		return isSetFirstColor;
	}
	public void setSetFirstColor(boolean isSetFirstColor) {
		this.isSetFirstColor = isSetFirstColor;
	}
	public boolean isSetSecondColor() {
		return isSetSecondColor;
	}
	public void setSetSecondColor(boolean isSetSecondColor) {
		this.isSetSecondColor = isSetSecondColor;
	}
	public boolean isSetTextColor() {
		return isSetTextColor;
	}
	public void setSetTextColor(boolean isSetTextColor) {
		this.isSetTextColor = isSetTextColor;
	}
	public boolean isSetThirdColor() {
		return isSetThirdColor;
	}
	public void setSetThirdColor(boolean isSetThirdColor) {
		this.isSetThirdColor = isSetThirdColor;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<LayoutColor> getLayoutColor() {
		return layoutColor;
	}
	public void setLayoutColor(List<LayoutColor> layoutColor) {
		this.layoutColor = layoutColor;
	}

}
