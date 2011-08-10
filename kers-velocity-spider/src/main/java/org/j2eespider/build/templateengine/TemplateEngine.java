package org.j2eespider.build.templateengine;

import java.util.HashMap;

public interface TemplateEngine {

	/**
	 * Run template file and write to file system.
	 * @param pathTemplate
	 * @param pathWriterFile
	 * @param mapVariables
	 * @throws Exception
	 */
	public void runFile(String pathTemplate, String pathWriterFile, HashMap<String, Object> mapVariables) throws Exception;
	
	/**
	 * Run template file and return content to String.
	 * @param pathTemplate
	 * @param mapVariables
	 * @return
	 * @throws Exception
	 */
	public String runFile(String pathTemplate, HashMap<String, Object> mapVariables) throws Exception;
	
	/**
	 * Run template from String in line and return content.
	 * @param lineTemplate
	 * @param hashVariables
	 * @return
	 * @throws Exception
	 */
	public String runInLine(String lineTemplate, HashMap<String, Object> mapVariables) throws Exception;
	
	/**
	 * Get action executed in this engine (copy file, generate file, etc).
	 * @return
	 */
	public String getAction();
}