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
package org.j2eespider.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.j2eespider.ide.data.domain.ConfigSpider;
import org.j2eespider.ide.editors.ConfigurationEditor;

/**
 * Class for read messages of the file of properties.
 * Very good for it return a String for to be used in an exception by example.  
 * ex: new Exception(PropertiesUtil.getMessage("erro.usuarioInvalido"))
 * 
 */
public class PropertiesUtil {
	private static Properties cachePropertiesTemplate;
	private static long lastCachePropertiesTemplate;
	
	public static String getMessage(String key, String arquivoProperties) {
		//check if template properties has this key
		String message = getMessageFromTemplate(key);
		if (message != null) {
			return message;
		}
		
		//get key from plug-in properties
        try {
            message = ResourceBundle.getBundle(arquivoProperties).getString(key);
            if (message == null) {
                message = key;
            }                
        } catch(MissingResourceException mre) {
        	mre.printStackTrace();
            message = key;
        }
        
        return message;
	}
	
	public static String getMessage(String key, Locale locale, String arquivoProperties) {
		//check if template properties has this key
		String message = getMessageFromTemplate(key);
		if (message != null) {
			return message;
		}
		
		//get key from plug-in properties
        try {
            message = ResourceBundle.getBundle(arquivoProperties, locale).getString(key);
            if (message == null) {
                message = key;
            }                
        } catch(MissingResourceException mre) {
            message = key;
        }
        
        return message;
	}
	
	public static String getMessage(String key, String[] parametros, String arquivoProperties) {
		//check if template properties has this key
		String message = getMessageFromTemplate(key);
		
		//get key from plug-in properties
		if (message == null) {
			message = getMessage(key, arquivoProperties);
		}		
		
		for (int i=0; i<parametros.length; i++) {
			message = message.replaceAll("\\{"+String.valueOf(i)+"\\}", parametros[i]);
		}
		
		return message;
	}
	
	public static String getMessage(String key, String[] parametros, Locale locale, String arquivoProperties) {
		//check if template properties has this key
		String message = getMessageFromTemplate(key);
		
		//get key from plug-in properties
		if (message == null) {
			message = getMessage(key, locale, arquivoProperties);
		}
		
		for (int i=0; i<parametros.length; i++) {
			message = message.replaceAll("\\{"+String.valueOf(i)+"\\}", parametros[i]);
		}
		
		return message;
	}
	
	/**
	 * 	Check if template properties has this key
	 * @return
	 */
	private static String getMessageFromTemplate(String key) {
		try {
			Properties propertiesTemplate = loadPropertiesTemplate();
			if (propertiesTemplate.getProperty(key) != null) {
				return propertiesTemplate.getProperty(key);
			}
		} catch (FileNotFoundException e) {
		}
		
		return null;
	}
	
	/**
	 * Load the template.properties of the Template.
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static Properties loadPropertiesTemplate() throws FileNotFoundException {
		long timeNow = new Date().getTime();
		if (cachePropertiesTemplate == null || timeNow - lastCachePropertiesTemplate > (1000*30)) { //faz cache de 30 segundos no arquivo
			cachePropertiesTemplate = new Properties();
			lastCachePropertiesTemplate = timeNow;
		} else {
			return cachePropertiesTemplate;
		}
		
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider();
		
		File fileProperties = new File(BuildManagerUtil.getPathFolderTemplates()+File.separator+configSpider.getTemplateFolder()+File.separator+"template_"+configSpider.getLanguage()+".properties");
		InputStream input = new FileInputStream(fileProperties);
		
		/*
		if (input == null && configSpider.getLanguage().split("_").length > 2) { //if the properties will not pt_BR or en_OR by example
			int first = configSpider.getLanguage().indexOf("_");
			String language = configSpider.getLanguage().substring(0, first);
			input = getClass().getResourceAsStream("/templates/"+configSpider.getTemplateCodeName()+"/template_"+language+".properties");
		}
		*/
		
		if (input == null) {return cachePropertiesTemplate;}
		
		try {
			cachePropertiesTemplate.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cachePropertiesTemplate;
	}
	
	/**
	 * Read all key from template.properties and put in Map
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static Map<String, String> readTemplatePropertiesToMap() throws FileNotFoundException {
		//load bundle
		Properties properties = PropertiesUtil.loadPropertiesTemplate();
		
		Map<String, String> keys = new HashMap<String, String>();
		for (Object key : properties.keySet()) {
			keys.put(key.toString(), properties.getProperty(key.toString()));
		}
		
		return keys;
	}
	
	public static String getProperty(Properties properties, String key) throws FileNotFoundException {
		String value = properties.getProperty(key);
		if (value == null) {
			value = key;
		}
		
		return value;
	}
	
}
