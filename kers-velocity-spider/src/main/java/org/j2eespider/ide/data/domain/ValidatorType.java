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

import java.io.FileNotFoundException;
import java.util.Properties;

import org.j2eespider.util.PropertiesUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class is responsible for configuring the types of validations of the SPIDER. 
 * Its data are in each template of code, in a XML (validatorConfig.xml).
 * 
 * @author bruno.braga
 */
@XStreamAlias("validatorType")
public class ValidatorType {
	/**type of this validator. Ex: validator.type.notNull */
	private String type;
	/**path that must be imported in the class. Ex: org.hibernate.validator.NotNull*/
	private String implementationClass;
	/**the name of parameters of this Validator. Ex: "validator.parameter.minLength","validator.parameter.maxLength" */
	private String[] parameters;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getClassName() {
		String[] temp = implementationClass.split("\\.");
		return temp[temp.length-1];
	}
	public String getImplementationClass() {
		return implementationClass;
	}
	public void setImplementationClass(String implementationClass) {
		this.implementationClass = implementationClass;
	}
	public void setParameters(String[] nameParameters) {
		this.parameters = nameParameters;
	}
	
	public String[] getParameters() {
		return this.parameters;
	}	
	
	public String[] getParametersDesc() {
		if (this.parameters != null) {
			try {
				Properties properties = PropertiesUtil.loadPropertiesTemplate();
				String[] translatedNameParameters = new String[this.parameters.length];
				int i=0;
				for (String name : this.parameters) {
					translatedNameParameters[i] = PropertiesUtil.getProperty(properties, name);
					i++;
				}
				return translatedNameParameters;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}	
	
	@Override
	public String toString() {
		try {
			Properties properties = PropertiesUtil.loadPropertiesTemplate();
			return PropertiesUtil.getProperty(properties, this.type);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.type;
	}
	
}
