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
package org.j2eespider.ide.jobs;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.j2eespider.SpiderPlugin;
import org.j2eespider.ide.data.domain.Repository;
import org.j2eespider.ide.data.rules.XmlManager;
import org.j2eespider.ide.data.rules.XmlManager.XML_TYPE;
import org.j2eespider.ide.tabletree.TemplateTableTree;
import org.j2eespider.util.PropertiesUtil;

public class LoadTemplateRepositoryJob extends Job {
	Logger logger = Logger.getLogger(getClass());
	private String urlErrorConnection;
	private List<Repository> repositories = new ArrayList<Repository>();
	
	public LoadTemplateRepositoryJob(String name) {
		super(name);
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		final int BUFFER_SIZE = 16;
		StringBuffer urlExceptions = new StringBuffer();
    	
    	//get urls from preferences
    	String[] urlRepos = SpiderPlugin.getDefault().getRepositoryPreference();
    	
    	//download initial repository contents
    	int timeout = SpiderPlugin.getDefault().getRepositoryTimeoutPreference();		
				
		try {
	        monitor.beginTask(PropertiesUtil.getMessage("dialog.progressLoadRepository.connecting", "spider"), 800);
			
	        for (String repo : urlRepos) {
				//check for user cancel
		        if (monitor.isCanceled()) {
		        	return Status.CANCEL_STATUS;   
		        }	        	
	        	
				//status
				monitor.subTask(PropertiesUtil.getMessage("dialog.progressLoadRepository.status", new String[] {repo}, "spider"));		        
		        
				BufferedInputStream buffInputStream = null;
				
				try {
					URL url = new URL(repo);
					
					//get stream file
					URLConnection connection = url.openConnection();
					connection.setConnectTimeout(timeout);
					connection.setReadTimeout(timeout);
					
					buffInputStream = new BufferedInputStream(connection.getInputStream(), BUFFER_SIZE);
				} catch (IOException e) {
					urlExceptions.append(repo).append("\n");
					continue; //go to next repo
				}
				
				monitor.setTaskName(PropertiesUtil.getMessage("dialog.progressLoadRepository.loading", "spider"));
				
				//transform inputstream in stringbuffer
		        StringBuffer repositoyXml = new StringBuffer();
		        try {
		            byte[] buf = new byte[BUFFER_SIZE];

		            // fetch file from stream, update monitor, watch for cancel
		            int readcount = 0;
		            while ((readcount = buffInputStream.read(buf)) != -1) {
		                repositoyXml.append(new String(buf, 0, readcount));
		                monitor.worked(readcount); //update progress
		            }

		        } finally {
		        	buffInputStream.close();
		        }
				
		        //load xml
				Repository repository = (Repository)XmlManager.loadXmlFromString(repositoyXml.toString(), XML_TYPE.REPOSITORY);
    			repositories.add(repository);
	        }	        					
		} catch (Exception e) {
			logger.error(e);
			return new Status(Status.ERROR, SpiderPlugin.ID, e.getMessage());
		} finally {
			if (urlExceptions != null && urlExceptions.length() > 0) {
		    	this.urlErrorConnection = urlExceptions.toString();
				
				return new Status(Status.ERROR, SpiderPlugin.ID, urlExceptions.toString());				
			}
			
			monitor.done();
		}

		return Status.OK_STATUS;
	}

	public String getUrlErrorConnection() {
		return urlErrorConnection;
	}

	public List<Repository> getRepositories() {
		return repositories;
	}
	
}
