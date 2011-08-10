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

import org.j2eespider.util.PropertiesUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("validator")
public class CrudFieldValidator {
	/**type of this validator*/
	private ValidatorType validatorType;
	/**parameters to config this validator*/
	private String[] parameters;
	/**apply this validator if... (el expression)*/
	private String applyIf;
	
	public CrudFieldValidator(ValidatorType validatorType) {
		super();
		this.validatorType = validatorType;
	}
	
	public ValidatorType getValidatorType() {
		return validatorType;
	}
	public void setValidatorType(ValidatorType validatorType) {
		this.validatorType = validatorType;
	}

	public String[] getParameters() {
		return parameters;
	}
	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

	public String getApplyIf() {
		return applyIf;
	}
	public void setApplyIf(String applyIf) {
		this.applyIf = applyIf;
	}

/*	public enum VALIDATOR_TYPE {
		NOT_NULL("validator.type.notNull", null),
		NOT_EMPTY("validator.type.notEmpty", null),
		MAX_MIN_LENGHT("validator.type.length", new String[] {"validator.parameter.minLength","validator.parameter.maxLength"}),
		MAX_LENGHT("validator.type.maxLength", new String[] {"validator.parameter.maxLength"}),
		MIN_LENGHT("validator.type.minLength", new String[] {"validator.parameter.minLength"}),
		RANGE("validator.type.range", new String[] {"validator.parameter.minRange","validator.parameter.maxRange"}),
		DATE_PAST("validator.type.datePast", null),
		DATE_FUTURE("validator.type.dateFuture", null),
		PATTERN("validator.type.pattern", new String[] {"validator.parameter.regexp","validator.parameter.regexpFlag"}),
		SIZE("validator.type.size", new String[] {"validator.parameter.minSize","validator.parameter.maxSize"}),
		EMAIL("validator.type.email", null),
		CREDIT_CARD("validator.type.creditCard", null),
		DIGITS("validator.type.digits", null),
		EAN("validator.type.ean", null);
		
		private String keyName;
		private String[] nameParameters;
		
		private VALIDATOR_TYPE(String keyName, String[] nameParameters) {
			this.keyName = keyName;
			this.nameParameters = nameParameters;
		}
		
		@Override
		public String toString() {
			return PropertiesUtil.getMessage(this.keyName, "spider");
		}
		
		public String getKeyName() {
			return this.keyName;
		}
		
		public String[] getNameParameters() {
			if (this.nameParameters != null) {
				String[] translatedNameParameters = new String[this.nameParameters.length];
				int i=0;
				for (String name : this.nameParameters) {
					translatedNameParameters[i] = PropertiesUtil.getMessage(this.nameParameters[i], "spider");
					i++;
				}
				return translatedNameParameters;
			}
			
			return null;
		}
	}	
	
	public static String[] getStringValidatorTypes() {
		String[] types = new String[VALIDATOR_TYPE.values().length];
		int i=0;
		for (VALIDATOR_TYPE type : VALIDATOR_TYPE.values()) {
			types[i] = type.toString();
			i++;
		}
		
		return types;
	}
	
	public static VALIDATOR_TYPE getValidatorType(String value) {
		for (VALIDATOR_TYPE type : VALIDATOR_TYPE.values()) {
			if (type.toString().equals(value)) {
				return type;
			}
		}
		
		return null;
	}
*/
	public enum VALIDATOR_OPERATION {
		AND("validator.operation.and", " and "),
		OR("validator.operation.or", " or "),
		NOT("validator.operation.not", "!"),
		EQUALITY("validator.operation.equality", " == "),
		INEQUALITY("validator.operation.inequality", " != "),
		LESS_THAN("validator.operation.lessThan", " < "),
		LESS_THAN_OR_EQUAL("validator.operation.lessThanOrEqual", " <= "),
		GREATER_THAN("validator.operation.greaterThan", " > "),
		GREATER_THAN_OR_EQUAL("validator.operation.greaterThanOrEqual", " >= "),
		ADDITION("validator.operation.addition", " + "),
		SUBTRACTION("validator.operation.subtraction", " - "),
		MULTIPLICATION("validator.operation.multiplication", " * "),
		DIVISION("validator.operation.division", " / "),
		INTEGER_DIVISION("validator.operation.integerDivision", " div "),
		MODULUS("validator.operation.modulus", " mod ");
		
		private String name;
		private String operation;
		
		private VALIDATOR_OPERATION(String name, String operation) {
			this.name = name;
			this.operation = operation;
		}
		
		@Override
		public String toString() {
			return PropertiesUtil.getMessage(this.name, "spider");
		}
		
		public String getOperation() {
			return this.operation;
		}
	}
	
	public static VALIDATOR_OPERATION getValidatorOperation(String name) {
		for (VALIDATOR_OPERATION type : VALIDATOR_OPERATION.values()) {
			if (type.toString().equals(name)) {
				return type;
			}
		}
		
		return null;
	}
}
