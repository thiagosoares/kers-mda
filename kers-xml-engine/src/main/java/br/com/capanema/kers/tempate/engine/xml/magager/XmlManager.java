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
package br.com.capanema.kers.tempate.engine.xml.magager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.common.model.projectea.TemplateConfig;
import br.com.capanema.kers.common.model.repository.Repository;
import br.com.capanema.kers.common.model.template.EntityTemplate;
import br.com.capanema.kers.common.model.template.Template;
import br.com.capanema.kers.common.model.template.TemplateSumary;
import br.com.capanema.kers.tempate.engine.xml.converter.TemplateFileMapConverter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;

public class XmlManager {	
	
	public static void writeXml(Object data, String pathArquivo, XmlType xmlType) {
		XStream xstream = getXStream(xmlType);
		
		String xml = xstream.toXML(data);
		
		File spiderFile = new File(pathArquivo);
		try {
			spiderFile.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(spiderFile);
			fileOutputStream.write(xml.getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public static Object loadXml(String pathFile, XmlType xmlType) {
		XStream xstream = getXStream(xmlType);
		
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(pathFile + File.separator + xmlType.getFileName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}	
		
		return xstream.fromXML(fileReader);
	}
	
	public static Object loadXml(InputStream inputStream, XmlType xmlType) {
		XStream xstream = getXStream(xmlType);
		
		return xstream.fromXML(inputStream);
	}

	public static Object loadXmlFromString(String xmlContent, XmlType xmlType) {
		XStream xstream = getXStream(xmlType);
		
		return xstream.fromXML(xmlContent);
	}	
	
	public static Object loadXmlFromUrl(URL url, int timeout, XmlType xmlType) throws IOException {		
		//get file
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(timeout);
		connection.setReadTimeout(timeout);
		 
		BufferedInputStream buffInputStream = new BufferedInputStream(connection.getInputStream());
		
		return loadXml(buffInputStream, xmlType);
	}	
	
	/**
	 * Load XML of TemplateCode 
	 * @param templateFolder (XML_TYPE.TEMPLATE_FILES or XML_TYPE.TEMPLATE_FILES_JAR)
	 * @param xmlType
	 * @return
	 * @throws IOException
	 */
	public static Object loadTemplateCodeXml(String templateFolder, XmlType xmlType) throws IOException {
		
		String pathDatabaseTypeConfig =  templateFolder + File.separator + xmlType.getFileName();
		  
		//TEMPLATE_FILES_JAR is optional
		if (xmlType == XmlType.TEMPLATE_FILES_JAR && new File(pathDatabaseTypeConfig).exists() == false) {
			return null;
		}
		
		return loadXml(pathDatabaseTypeConfig, xmlType);
	}
	
	public static XStream getXStream() {
	  return getXStream(XmlType.GENERIC);
	}
	
	/**
	 * Instance and configure XStream
	 * @return
	 */
	public static XStream getXStream(XmlType xmlType) {
		XStream xstream = new XStream();
		//configure rules
		if (xmlType == XmlType.TEMPLATE_FILES_JAR || xmlType == XmlType.TEMPLATE_FILES) {
			xstream.useAttributeFor("entryDepends", String.class);
			xstream.registerConverter(new TemplateFileMapConverter(xstream.getMapper()));
		} else if (xmlType == XmlType.TEMPLATE) {
			//xstream.registerConverter(new TemplateFileMapConverter(xstream.getMapper()));
			//xstream.addImplicitCollection(ArrayList.class, "list"); //dentro do map de techLayout tem um list, isso deixa o <list> implicito
		} else if (xmlType == XmlType.REPOSITORY || xmlType == XmlType.REPOSITORY_TEMPLATE_INFO) {
			//xstream.addImplicitCollection(ArrayList.class, "list");
		} else if (xmlType == XmlType.TEMPLATE_COMMAND) {
			//xstream.registerConverter(new TemplateFileMapConverter(xstream.getMapper()));
		}
		
		//xstream.addImplicitCollection(ArrayList.class, "list"); //dentro do map de techLayout tem um list, isso deixa o <list> implicito
		
		//configure alias
		
		xstream.processAnnotations(EAProjectConfig.class);
		xstream.processAnnotations(Repository.class);
		xstream.processAnnotations(TemplateSumary.class);
		xstream.processAnnotations(Template.class);
		xstream.processAnnotations(EntityTemplate.class);
		
		//Annotations.configureAliases(xstream, EAProjectConfig.class);
		//Annotations.configureAliases(xstream, TemplateConfig.class);
		
		//Annotations.configureAliases(xstream, Repository.class);
		///Annotations.configureAliases(xstream, TemplateSumary.class);
		//Annotations.configureAliases(xstream, Template.class);
		//Annotations.configureAliases(xstream, EntityTemplate.class);
		
		/*
		Annotations.configureAliases(xstream, SiteLayout.class);
		Annotations.configureAliases(xstream, Template.class);
		Annotations.configureAliases(xstream, TemplateFile.class);
		Annotations.configureAliases(xstream, TemplateFileIncrement.class);
		Annotations.configureAliases(xstream, TemplateCommand.class);
		Annotations.configureAliases(xstream, Crud.class);
		Annotations.configureAliases(xstream, CrudClass.class);
		Annotations.configureAliases(xstream, CrudField.class);
		Annotations.configureAliases(xstream, CrudFieldValidator.class);
		Annotations.configureAliases(xstream, HtmlType.class);
		Annotations.configureAliases(xstream, ValidatorType.class);
		Annotations.configureAliases(xstream, PluginRequired.class);
		Annotations.configureAliases(xstream, RepositoryTemplateInfo.class);
		Annotations.configureAliases(xstream, RepositoryLink.class);
		Annotations.configureAliases(xstream, FieldLocation.class);*/
		
		xstream.setMode(XStream.NO_REFERENCES); //no references for attribute data
		
		return xstream;
	}
	
}
