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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.j2eespider.ide.data.domain.TemplateCommand;

/**
 * Exec commands in command line, parse the questions and reply with new commands.
 *
 * @author bruno.braga
 */
public class CommandLineUtil {
	private static Process currentProcess = null;
	
	public static String exec(TemplateCommand command) throws IOException, InterruptedException {
        StringBuffer sbLog = new StringBuffer();
        
        //TODO: support/consider quotes in command. Split for blank space not solve all situations
        ProcessBuilder processBuilder = new ProcessBuilder(command.getExec().split(" "));
        processBuilder.redirectErrorStream(true);
        currentProcess = processBuilder.start();
        InputStream is = currentProcess.getInputStream();
        OutputStream os = currentProcess.getOutputStream();
        
        InputStreamAnalyser inPumper = new InputStreamAnalyser(is, os, command, sbLog);
        Thread threadIn = new Thread(inPumper);
        threadIn.start();
        
        currentProcess.waitFor();
        
        currentProcess = null;
        
        return sbLog.toString();
	}
	
	public static void cancelProcess() {
		if (currentProcess != null) {
			currentProcess.destroy();
		}
	}
			
}

/**
 * Parse a command line and reply the command questions.
 *
 * @author bruno.braga
 */
class InputStreamAnalyser implements Runnable {

    public static final int SLEEPING_TIME = 100;

    private final InputStream is;
    
    private final OutputStream os;
    
    private final PrintWriter pw;
    
    private final TemplateCommand command;
    
    private final StringBuffer log;
    
    public static final String NL = System.getProperty("line.separator");

    public InputStreamAnalyser(final InputStream is, final OutputStream os, final TemplateCommand command, final StringBuffer log) {
        this.is = is;
        this.os = os;
        this.command = command;
        this.pw = new PrintWriter( os, true );
        this.log = log;
    }

    public void run() {
        try {
        	/*
            while (!stop) {
                String input = "";
                while (is.available() > 0 && !stop) {
                    int i = is.read();
                    if ( i < 0 ) {
                    	writeResponse(input);
                    }
                    
                    char c = ( char )i;
                    input += c;
                    if ( is.available() == 0 ) {
                    	writeResponse(input);
                    }                                 	
                }
                Thread.sleep(SLEEPING_TIME);
            }
            */
            
        	StringBuffer lastCommand = new StringBuffer();
            for (int i; (i = is.read()) != -1; ) {
            	char c = ( char )i;
            	lastCommand.append((char)c);
            	log.append((char)c);

                if (i == 10 || is.available() == 0) { //if get \n or inputStream is over
                	writeResponse(lastCommand.toString());
                	lastCommand = new StringBuffer();
                }
            } 
            
            
        } catch (Exception e) {
            e.printStackTrace();
            log.append(e.toString());
            Thread.currentThread().interrupt();
        } finally {
        }
    }

    /**
     * Analyse the process input and reply commands if necessary.
     * @param input
     */
    private void writeResponse(String input) {
    	String response = null;
		for (String question : command.getInteract().keySet()) {
			String key = question; //save the map key
			question = StringUtil.scapeRegexp(question); //scape characters
			question = question.replaceAll("\\\\\\*", ".*?"); //replace * to .*?
			
			// Create the pattern
	        Pattern pattern = Pattern.compile(question.trim(), Pattern.DOTALL);
	        Matcher matcher = pattern.matcher("");
	        matcher.reset(input.trim());
	        if (matcher.find()) { //if the question is found in input
	        	//TODO: fix the xstream config, the map command.getInteract() is <String, String>, not <String, List>
	        	Object r = command.getInteract().get(key);
	        	if (r instanceof List) {
					response = ((List)r).get(0).toString();	
	        	} else {
	        		response = r.toString();
	        	}
				break;
	        }
		}
		
		if (response != null) {
		    pw.println(response);
		    log.append(response).append(NL);
		}
    }

}
