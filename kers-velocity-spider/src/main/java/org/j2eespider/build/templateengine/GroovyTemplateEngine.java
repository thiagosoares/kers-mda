package org.j2eespider.build.templateengine;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.HashMap;

public class GroovyTemplateEngine implements TemplateEngine {
	private static TemplateEngine instance;
	
	public static TemplateEngine getInstance() {
		if (instance == null) {
			instance = new GroovyTemplateEngine();
		}
		
		return instance;
	}
	
	public String getAction() {
		return "generate file....: ";
	}

	public void runFile(String pathTemplate, String pathWriterFile, HashMap<String, Object> mapVariables) throws Exception {
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		
		File fileTemplate = new File(pathTemplate);
		Template template = engine.createTemplate(fileTemplate);
 
		Writable writable = template.make(mapVariables);
		
		FileWriter fileWriter = new FileWriter(pathWriterFile);
		writable.writeTo(fileWriter);

        fileWriter.flush();
        fileWriter.close();
	}

	public String runFile(String pathTemplate, HashMap<String, Object> mapVariables) throws Exception {
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		
		File fileTemplate = new File(pathTemplate);
		Template template = engine.createTemplate(fileTemplate);
 
		Writable writable = template.make(mapVariables);
 
		StringWriter stringWriter = new StringWriter();
		writable.writeTo(stringWriter);
		
		return stringWriter.toString();
	}

	public String runInLine(String lineTemplate, HashMap<String, Object> mapVariables) throws Exception {
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		
		Template template = engine.createTemplate(lineTemplate);
 
		Writable writable = template.make(mapVariables);
 
		StringWriter stringWriter = new StringWriter();
		writable.writeTo(stringWriter);
		
		return stringWriter.toString();
	}

}
