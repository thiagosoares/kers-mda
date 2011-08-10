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
package org.j2eespider.build.templateengine;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.view.XMLToolboxManager;
import org.j2eespider.ide.data.domain.TemplateDataGroup;
import org.j2eespider.util.BuildManagerUtil;

public class VelocityTemplateEngine implements TemplateEngine {
	private static TemplateEngine instance;
	
	public static TemplateEngine getInstance() {
		if (instance == null) {
			instance = new VelocityTemplateEngine();
		}
		
		return instance;
	}
	
	public String getAction() {
		return "generate file....: ";
	}
	
	/**
	 * Run Velocity, and write a result File
	 * @param pathTemplate Full Path of the Template
	 * @param pathWriterFile Full Path of the result File
	 * @param mapVariables Hash of the variables used in template
	 * @throws Exception
	 */
	public void runFile(String pathTemplate, String pathWriterFile, HashMap<String, Object> mapVariables) throws Exception {
		//trunc WriterFile
		int lastSeparator = pathWriterFile.lastIndexOf("/");
		if (lastSeparator == -1) {lastSeparator = pathWriterFile.lastIndexOf("\\");}
		String dirWriter = pathWriterFile.substring(0, lastSeparator);
		//create dir of WriterFile
		new File(dirWriter).mkdirs();
		
		//trunc pathTemplate
		lastSeparator = pathTemplate.lastIndexOf("/");
		if (lastSeparator == -1) {lastSeparator = pathTemplate.lastIndexOf("\\");}
		String nameTemplate = pathTemplate.substring(lastSeparator+1);
		pathTemplate = pathTemplate.substring(0, lastSeparator);
		
		//properties to load file
		Properties  props = new Properties();
		props.setProperty("resource.loader", "file");
		props.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		props.setProperty("file.resource.loader.path", pathTemplate);
		
		//init velocity
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init(props);
        
        //getting template
        Template template = velocityEngine.getTemplate(nameTemplate);

        //toolbox for improvement features
        TemplateDataGroup dataGroup = (TemplateDataGroup)mapVariables.get("data");
        XMLToolboxManager toolboxManager = new XMLToolboxManager();
        toolboxManager.load(dataGroup.getPathFolderTemplates()+File.separator+"velocityToolbox.xml");
        Map mapToolBox = toolboxManager.getToolbox(null);
        
		//set var
        VelocityContext context = new VelocityContext(mapToolBox);
		for (String key : mapVariables.keySet()) {
			context.put(key, mapVariables.get(key));   
		}
         
		FileWriter fileWriter = new FileWriter(pathWriterFile);
		
        //merge template and context (vars)
        template.merge(context, fileWriter);
        
        fileWriter.flush();
        fileWriter.close();
	}
	
	/**
	 * Run Velocity, and return a result content
	 * @param pathTemplate Full Path of the Template
	 * @param mapVariables Hash of the variables used in template
	 * @throws Exception
	 */
	public String runFile(String pathTemplate, HashMap<String, Object> mapVariables) throws Exception {		
		//trunc pathTemplate
		int lastSeparator = pathTemplate.lastIndexOf("/");
		if (lastSeparator == -1) {lastSeparator = pathTemplate.lastIndexOf("\\");}
		
		String nameTemplate = pathTemplate.substring(lastSeparator+1);
		pathTemplate = pathTemplate.substring(0, lastSeparator);
		
		//properties to load file
		Properties  props = new Properties();
		props.setProperty("resource.loader", "file");
		props.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		props.setProperty("file.resource.loader.path", pathTemplate);
		
		//init velocity
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init(props);
        
        //getting template
        Template template = velocityEngine.getTemplate(nameTemplate);

        //toolbox for improvement features
        TemplateDataGroup dataGroup = (TemplateDataGroup)mapVariables.get("data");
        //this runVelocity may not have dataGroup
        if (dataGroup == null) {
        	dataGroup = new TemplateDataGroup();
			dataGroup.setPathFolderTemplates(BuildManagerUtil.getPathFolderTemplates());
			mapVariables.put("data", dataGroup);
        }

        XMLToolboxManager toolboxManager = new XMLToolboxManager();
        toolboxManager.load(dataGroup.getPathFolderTemplates()+File.separator+"velocityToolbox.xml");
        Map mapToolBox = toolboxManager.getToolbox(null);
        
		//set var
        VelocityContext context = new VelocityContext(mapToolBox);
		for (String key : mapVariables.keySet()) {
			context.put(key, mapVariables.get(key));   
		}
		
		StringWriter stringWriter = new StringWriter(); 

        //merge template and context (vars)
        template.merge(context, stringWriter);
        
        return stringWriter.toString();
	}
	
	/**
	 * Run Velocity, and return a result content
	 * @param lineTemplate Template in line
	 * @param hashVariables Hash of the variables used in template
	 * @throws Exception
	 */
	public String runInLine(String lineTemplate, HashMap<String, Object> hashVariables) throws Exception {		
		//init velocity
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        //toolbox for improvement features
        TemplateDataGroup dataGroup = (TemplateDataGroup)hashVariables.get("data");
        //this runVelocity may not have dataGroup
        if (dataGroup == null) {
        	dataGroup = new TemplateDataGroup();
			dataGroup.setPathFolderTemplates(BuildManagerUtil.getPathFolderTemplates());
			hashVariables.put("data", dataGroup);
        }

        XMLToolboxManager toolboxManager = new XMLToolboxManager();
        toolboxManager.load(dataGroup.getPathFolderTemplates()+File.separator+"velocityToolbox.xml");
        Map mapToolBox = toolboxManager.getToolbox(null);
        
		//set var
        VelocityContext context = new VelocityContext(mapToolBox);
		for (String key : hashVariables.keySet()) {
			context.put(key, hashVariables.get(key));   
		}
		
		StringWriter stringWriter = new StringWriter(); 

		velocityEngine.evaluate(context, stringWriter, "",  lineTemplate);
        
        return stringWriter.toString();
	}
	
}
