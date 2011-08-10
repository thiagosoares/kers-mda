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

import org.j2eespider.ide.editors.ConfigurationEditor;
import org.j2eespider.util.PropertiesUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class is responsible for configuring the types of html fields of the SPIDER. 
 * Its data are in each template of code, in a XML (fieldConfig.xml).
 * 
 * @author bruno.braga
 */
@XStreamAlias("fieldLocation")
public class FieldLocation {
	/**the area to show a specific field, like search, insert */
	private String id;
	private boolean selectedByDefault;
	
	public FieldLocation() {
		super();
	}

	public FieldLocation(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isSelectedByDefault() {
		return selectedByDefault;
	}

	public void setSelectedByDefault(boolean selectedByDefault) {
		this.selectedByDefault = selectedByDefault;
	}

	public String getLocationName() {
		try {
			Properties properties = PropertiesUtil.loadPropertiesTemplate();
			return PropertiesUtil.getProperty(properties, id);
		} catch (FileNotFoundException e) {
			return id;
		}
	}

	public static List<FieldLocation> filterSelectedByDefault(List<FieldLocation> locations) {
		List<FieldLocation> locationsSelected = new ArrayList<FieldLocation>();
		
		for (FieldLocation location : locations) {
			if (location.isSelectedByDefault()) {
				locationsSelected.add(location);
			}
		}
		
		return locationsSelected;
	}
	
	
	@Override
	public String toString() {
		return getLocationName();
	}

	@Override
	public boolean equals(Object obj) {
		FieldLocation objLocation = (FieldLocation)obj;
		
		if (this.getId().equals(objLocation.getId())) {
			return true;
		} else {
			return false;
		}
	}
	
}
