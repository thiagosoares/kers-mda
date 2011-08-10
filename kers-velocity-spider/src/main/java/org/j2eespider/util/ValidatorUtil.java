package org.j2eespider.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.j2eespider.build.templateengine.TemplateEngine;
import org.j2eespider.build.templateengine.VelocityTemplateEngine;
import org.j2eespider.ide.data.domain.CrudFieldValidator;
import org.j2eespider.ide.data.domain.TemplateDataGroup;
import org.j2eespider.ide.data.domain.ValidatorType;
import org.j2eespider.ide.editors.ConfigurationEditor;

public class ValidatorUtil {
	
	/**
	 * Find and return validatorType using key.
	 * @param type
	 * @return
	 */
	public static ValidatorType getValidatorTypeByKey(String type) {
		List<ValidatorType> validatorTypes = ConfigurationEditor.getValidatorTypes();
		
		for (ValidatorType validatorType : validatorTypes) {
			if (validatorType.getType().equals(type)) {
				return validatorType;
			}
		}
		
		return null;
	}
	
	/**
	 * Find and retur validatorType using name (label).
	 * @param name
	 * @return
	 */
	public static ValidatorType getValidatorTypeByName(String name) {
		List<ValidatorType> validatorTypes = ConfigurationEditor.getValidatorTypes();
		
		for (ValidatorType validatorType : validatorTypes) {
			if (validatorType.toString().equals(name)) {
				return validatorType;
			}
		}
		
		return null;
	}
	
	public static List<ValidatorType> getValidators() {
		List<ValidatorType> validatorTypes = ConfigurationEditor.getValidatorTypes();

		return validatorTypes;
	}
	
	public static boolean containsValidator(List<ValidatorType> validators, String commandName) {
		//tratamento do comando (annotation)
		commandName = commandName.replaceAll("@", ""); //tira o @ da annotation
		if (commandName.contains("(")) {
			String temp[] = commandName.split("\\(");
			commandName = temp[0];
		}
		String fullCommand = commandName;
		if (commandName.contains(".")) {
			String temp[] = commandName.split("\\.");
			commandName = temp[temp.length-1];
		}
		
		for (ValidatorType validator : validators) {
			if (validator.getClassName().equals(commandName) || validator.getImplementationClass().equals(fullCommand)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Pega no template o codigo para cada Validator. E retorna um String com esse source.
	 * @param validators
	 * @return
	 */
	public static String getValidatorsSource(List<CrudFieldValidator> validators) {
		StringBuffer sourceValidators = new StringBuffer();
		try {
			String pathHibernateValidator = BuildManagerUtil.getPathFolderTemplates()+File.separator+"common"+File.separator+"tech"+File.separator+"hibernateValidator"+File.separator;
			
			if (validators != null) { 
				for (CrudFieldValidator validator : validators) {
					ValidatorType validatorType = validator.getValidatorType();
					//váriaveis velocity
					HashMap<String, Object> velocityVariables = new HashMap<String, Object>();
					
					if (validatorType.getParameters() != null) { //se tiver parametros
						int i=0;
						for (String parameter : validatorType.getParameters()) {
							if (parameter.contains(".")) { //se tiver ponto, pega o último nome
								String temp[] = parameter.split("\\.");
								parameter = temp[temp.length-1];
							}
							velocityVariables.put(parameter, validator.getParameters()[i]);
							i++;
						}	
						velocityVariables.put("count", i);
					}
					
					//rodando velocity
					TemplateEngine templateEngine = VelocityTemplateEngine.getInstance();
					String thisSource = templateEngine.runFile(pathHibernateValidator+validatorType.getClassName().toLowerCase()+".vm", velocityVariables);
					
					sourceValidators.append(thisSource.trim()+"\n\t");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return sourceValidators.toString();
	}
}
