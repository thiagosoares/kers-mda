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
package br.com.capanema.kers.common.util;

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

import br.com.capanema.kers.common.model.project.ProjectConfig;

//import org.j2eespider.ide.data.domain.ConfigSpider;
//import org.j2eespider.ide.editors.ConfigurationEditor;

/**
 * Class for read messages of the file of properties.
 * Very good for it return a String for to be used in an exception by example.  
 * ex: new Exception(PropertiesUtil.getMessage("erro.usuarioInvalido"))
 * 
 */
public class PropertiesFileUtil {
	
  /**
   * Load the template.properties of the Template.
   * @return
   * @throws Exception 
   */
  public static Properties loadPropertiesTemplate(String repositoryPath, String locale) throws Exception {
    
    String path = repositoryPath;
    
    File fileProperties = new File(path + File.separator+"template_"+locale+".properties");
    InputStream input = new FileInputStream(fileProperties);
    
    Properties cachePropertiesTemplate = new Properties();
    
    if (input == null) {
      throw new Exception("O bundle não foi localizado");
    }
    
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
   * @throws Exception 
   */
  public static Map<String, String> readTemplatePropertiesToMap(String repositoryPath, String language) throws Exception {
    //load bundle
    Properties properties = PropertiesFileUtil.loadPropertiesTemplate(repositoryPath, language);
    
    Map<String, String> keys = new HashMap<String, String>();
    for (Object key : properties.keySet()) {
      keys.put(key.toString(), properties.getProperty(key.toString()));
    }
    
    return keys;
  }
  
  
  public static String getMessage(String key, String arquivoProperties) {
    // check if template properties has this key
    String message = "";

    // get key from plug-in properties
    try {
      message = ResourceBundle.getBundle(arquivoProperties).getString(key);
      if (message == null) {
        message = key;
      }
    } catch (MissingResourceException mre) {
      mre.printStackTrace();
      message = key;
    }

    return message;
  }
	
	/*public static String getMessage(String key, Locale locale, String arquivoProperties) {
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
	}*/
	
	/*public static String getMessage(String key, String[] parametros, String arquivoProperties) {
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
	}*/
	
	/*public static String getMessage(String key, String[] parametros, Locale locale, String arquivoProperties) {
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
	}*/
	
	/**
	 * 	Check if template properties has this key
	 * @return
	 */
	/*private static String getMessageFromTemplate(String key) {
		try {
			Properties propertiesTemplate = loadPropertiesTemplate();
			if (propertiesTemplate.getProperty(key) != null) {
				return propertiesTemplate.getProperty(key);
			}
		} catch (FileNotFoundException e) {
		}
		
		return null;
	}*/
	
	/*public static Properties loadPropertiesTemplate() throws FileNotFoundException {
	  return loadPropertiesTemplate("", ""); 
	}*/
	
	
	
	/*public static String getProperty(Properties properties, String key) throws FileNotFoundException {
		String value = properties.getProperty(key);
		if (value == null) {
			value = key;
		}
		
		return value;
	}*/
	
}
