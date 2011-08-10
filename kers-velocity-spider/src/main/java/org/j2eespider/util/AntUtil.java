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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class AntUtil {
	
	/**
	 * Run Ant Project
	 * @param pathBuildFile
	 * @param hashProperties
	 * @param pathLog
	 * @return String
	 */
	public static String runProject(String pathBuildFile, Map<String, String> hashProperties, String pathLog) {
		//date now
		Date dateNow = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String strDateNow = dateFormat.format(dateNow);
		
		PrintStream printStream = null;
		File loggerFile = new File(pathLog + File.separator + "build "+strDateNow+".log");
		//logger
		try {
			printStream = new PrintStream(loggerFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		//Ant Project
		File buildFile = new File(pathBuildFile);
		Project project = new Project();
		
		//properties
		project.setUserProperty("ant.file", buildFile.getAbsolutePath());
		for (String key : hashProperties.keySet()) {
			project.setProperty(key, hashProperties.get(key).toString());	
		}
		
		//Log
		DefaultLogger logger = new DefaultLogger();
		logger.setErrorPrintStream(printStream);
		logger.setOutputPrintStream(printStream);
		logger.setMessageOutputLevel(Project.MSG_INFO);
		
		project.addBuildListener(logger);
		
		try {
			//init ant
			project.fireBuildStarted();
			project.init();
			
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			project.addReference("ant.projectHelper", helper);
			helper.parse(project, buildFile);
			
			//execute ant
			project.executeTarget(project.getDefaultTarget());
			project.fireBuildFinished(null);
		} catch (BuildException e) {
			project.fireBuildFinished(e);
		}

		//return log
		StringBuffer buildLog = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new FileReader(loggerFile));
			String str;
	        while ((str = in.readLine()) != null) {
	        	buildLog.append(str).append("\n");
	        }
		} catch (FileNotFoundException e) {
			buildLog.append("Log file:" + pathLog + File.separator + "build "+strDateNow+".log" + "not found");
		} catch (IOException e) {
			buildLog.append(e.getMessage());
		}
		
		return buildLog.toString();
	}
}
