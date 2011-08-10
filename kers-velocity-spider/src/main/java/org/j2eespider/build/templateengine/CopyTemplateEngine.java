package org.j2eespider.build.templateengine;

import java.io.File;
import java.util.HashMap;

import org.j2eespider.util.FileUtil;

public class CopyTemplateEngine implements TemplateEngine {
	private static TemplateEngine instance;
	
	public static TemplateEngine getInstance() {
		if (instance == null) {
			instance = new CopyTemplateEngine();
		}
		
		return instance;
	}
	
	public String getAction() {
		return "copy file........: ";
	}

	public void runFile(String pathTemplate, String pathWriterFile, HashMap<String, Object> mapVariables) throws Exception {
		File fileTemplate = new File(pathTemplate);
		File fileGeneration = new File(pathWriterFile);
		FileUtil.copyTemplateDirectory(fileTemplate, fileGeneration);
	}

	public String runFile(String pathTemplate,
			HashMap<String, Object> mapVariables) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String runInLine(String lineTemplate,
			HashMap<String, Object> hashVariables) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
