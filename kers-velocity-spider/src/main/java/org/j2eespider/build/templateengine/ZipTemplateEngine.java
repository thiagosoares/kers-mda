package org.j2eespider.build.templateengine;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.HashMap;

import org.j2eespider.util.ZipUtil;

public class ZipTemplateEngine implements TemplateEngine {
	private static TemplateEngine instance;
	
	public static TemplateEngine getInstance() {
		if (instance == null) {
			instance = new ZipTemplateEngine();
		}
		
		return instance;
	}
	
	public String getAction() {
		return "unzip file.......: ";
	}

	public void runFile(String pathTemplate, String pathWriterFile, HashMap<String, Object> mapVariables) throws Exception {
		FileInputStream fis = new FileInputStream(pathTemplate);
		BufferedInputStream buffInputStream = new BufferedInputStream(fis);
		ZipUtil.unZip(pathWriterFile, buffInputStream);
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
