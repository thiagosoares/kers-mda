package br.com.capanema.kers.velocity.template.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.capanema.kers.velocity.template.engine.CopyTemplateEngine;
import br.com.capanema.kers.velocity.template.engine.FreemarkerTemplateEngine;
import br.com.capanema.kers.velocity.template.engine.GroovyTemplateEngine;
import br.com.capanema.kers.velocity.template.engine.TemplateEngine;
import br.com.capanema.kers.velocity.template.engine.VelocityTemplateEngine;
import br.com.capanema.kers.velocity.template.engine.ZipTemplateEngine;

public class TemplateEngineFactory {
	private static Map<String, TemplateEngine> templateEngines;
	private static Map<String, String> extensions;
	private static Map<String, String> extensionsForIncrement;
	
	private static String ENG_VELOCITY = "VM";
	private static String ENG_GROOVY = "GROOVY";
	private static String ENG_FREEMARKER = "FTL";
	private static String ENG_ZIP = "ZIP";
	
	static {
		templateEngines = new HashMap<String, TemplateEngine>();
		templateEngines.put(ENG_VELOCITY, VelocityTemplateEngine.getInstance());
		templateEngines.put(ENG_GROOVY, GroovyTemplateEngine.getInstance());
		templateEngines.put(ENG_FREEMARKER, FreemarkerTemplateEngine.getInstance());
		templateEngines.put(ENG_ZIP, ZipTemplateEngine.getInstance());
	}
	
	static {
		extensions = new HashMap<String, String>();
		extensions.put("\\..*?\\.vm", ENG_VELOCITY);
		extensions.put("\\..*?\\.groovy", ENG_GROOVY);
		extensions.put("\\..*?\\.ftl", ENG_FREEMARKER);
		extensions.put(".*?\\.zip", ENG_ZIP);
		
		extensionsForIncrement = new HashMap<String, String>();
		extensionsForIncrement.put(".*?\\.vm", ENG_VELOCITY);
		extensionsForIncrement.put(".*?\\.groovy", ENG_GROOVY);
		extensionsForIncrement.put(".*?\\.ftl", ENG_FREEMARKER);		
	}
	
	public static TemplateEngine getEngine(String pathTemplateFile, boolean isIncrement) {
		Map<String, String> ext = null;
		if (!isIncrement) {
			ext = extensions;
		} else {
			ext = extensionsForIncrement;
		}
		
		for (String extension : ext.keySet()) {
	        Pattern pattern = Pattern.compile(extension); 
	        Matcher matcher = pattern.matcher(pathTemplateFile);
	        //matcher.reset(pathTemplateFile);	
			
	        if (matcher.find()) { //if velocity template
	        	String engineName = ext.get(extension);
	        	return templateEngines.get(engineName);
	        }
		}

		return CopyTemplateEngine.getInstance();
		
	}

}
