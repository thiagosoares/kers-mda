package org.j2eespider.ide.data.domain;

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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.j2eespider.util.PropertiesUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class is responsible for configuring the types of html fields of the SPIDER. 
 * Its data are in each template of code, in a XML (fieldConfig.xml).
 * 
 * @author bruno.braga
 */
@XStreamAlias("htmlType")
public class HtmlType {
	/**type of this html field. Ex: html.type.text */
	private String type;
	private List<String> mapTo;
	private List<String> styles;
	
	/**selected field style (changed in runtime)*/
	private String selectedStyle;
	/**
	 * @deprecated use selectedStyle
	 */
	private String fieldStyle;

	public String getType() {
		return type;
	}

	public String getTypeName() {
		try {
			Properties properties = PropertiesUtil.loadPropertiesTemplate();
			return PropertiesUtil.getProperty(properties, type);
		} catch (FileNotFoundException e) {
			return type;
		}
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public List<String> getMapTo() {
		return mapTo;
	}

	public void setMapTo(List<String> mapTo) {
		this.mapTo = mapTo;
	}
	
	public List<String> getStyles() {
		return styles;
	}

	public void setStyles(List<String> styles) {
		this.styles = styles;
	}

	public String getSelectedStyle() {
		//TODO: fieldStyle is deprecated. Remmove in v1.5
		if (selectedStyle == null) {
			selectedStyle = fieldStyle;
			fieldStyle = null;
		}
		
		return selectedStyle;
	}

	public void setSelectedStyle(String style) {
		this.selectedStyle = style;
	}

	@Override
	public String toString() {
		return getTypeName();
	}

	@Override
	public HtmlType clone() {
		HtmlType htmlType = new HtmlType();
		htmlType.setType(this.type);
		htmlType.setMapTo(this.mapTo);
		htmlType.setStyles(this.styles);
		
		return htmlType;
	}
	
}
