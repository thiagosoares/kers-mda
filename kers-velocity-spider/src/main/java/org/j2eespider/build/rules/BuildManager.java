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
package org.j2eespider.build.rules;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.j2eespider.SpiderPlugin;
import org.j2eespider.build.dialogs.ConfirmDialog;
import org.j2eespider.build.jobs.Generate;
import org.j2eespider.build.jobs.GenerateCrudJob;
import org.j2eespider.build.jobs.GenerateFileJob;
import org.j2eespider.build.templateengine.TemplateEngine;
import org.j2eespider.build.templateengine.TemplateEngineFactory;
import org.j2eespider.build.templateengine.VelocityTemplateEngine;
import org.j2eespider.ide.data.domain.ConfigSpider;
import org.j2eespider.ide.data.domain.Crud;
import org.j2eespider.ide.data.domain.ProjectInfo;
import org.j2eespider.ide.data.domain.SimpleSiteLayout;
import org.j2eespider.ide.data.domain.Template;
import org.j2eespider.ide.data.domain.TemplateCommand;
import org.j2eespider.ide.data.domain.TemplateDataGroup;
import org.j2eespider.ide.data.domain.TemplateFile;
import org.j2eespider.ide.data.domain.TemplateFileIncrement;
import org.j2eespider.ide.data.rules.XmlManager;
import org.j2eespider.ide.data.rules.XmlManager.XML_TYPE;
import org.j2eespider.ide.editors.ConfigurationEditor;
//import org.j2eespider.ide.editors.pages.CrudByMappingPage;
//import org.j2eespider.ide.editors.pages.MappingPage;
import org.j2eespider.ide.editors.pages.PageName.PAGE_NAME;
import org.j2eespider.util.AntUtil;
import org.j2eespider.util.BuildManagerUtil;
import org.j2eespider.util.CommandLineUtil;
import org.j2eespider.util.DialogUtil;
import org.j2eespider.util.FileUtil;
import org.j2eespider.util.PropertiesUtil;
import org.j2eespider.util.StyleRangeUtil;
import org.j2eespider.util.ZipUtil;

public class BuildManager {
	
	Map<String, List<TemplateFile>> mapTemplateFiles = null;
	Map<String, Long> lastBuild = new LinkedHashMap<String, Long>();
	private String currentBuild;
	
	//private Shell shell;
	private boolean rememberMerge = false;
	private boolean rememberReplace = false;
	private boolean rememberSkip = false;
	private final String templateFileJarSuffix = ".JAR";
	private static boolean inBuild = false;
	
	Logger logger = Logger.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	public BuildManager() {
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider();
		
		try {
			//load xml - Template Files
			//templateFiles.xml
			mapTemplateFiles = (Map<String, List<TemplateFile>>)XmlManager.loadTemplateCodeXml(configSpider.getTemplateFolder(), XML_TYPE.TEMPLATE_FILES);
			
			//templateFiles-jar.xml is optional
			Object templateFilesJar = XmlManager.loadTemplateCodeXml(configSpider.getTemplateFolder(), XML_TYPE.TEMPLATE_FILES_JAR);
			if (templateFilesJar != null) {
				mapTemplateFiles.putAll((Map<String, List<TemplateFile>>)templateFilesJar);
			}
			
			//load last build
			IFile fileSpiderBuild = ConfigurationEditor.activeProject.getFile(".spiderBuild");
			if (fileSpiderBuild.exists()) {
				lastBuild = (Map<String, Long>)XmlManager.loadXml(fileSpiderBuild.getLocation().toOSString(), XML_TYPE.SPIDER_BUILD);
			} else {
				lastBuild = new HashMap<String, Long>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Build configuration of layout.
	 */
	public void buildLayout() {
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider();
	
		lastBuild.put(BuildManagerUtil.KEY_BUILD_LAYOUT, new Date().getTime()); //grava que j� fez build de CONFIG alguma vez
		this.currentBuild = BuildManagerUtil.KEY_BUILD_LAYOUT;
		
		List<String> listKeyGenerate = new LinkedList<String>();
		listKeyGenerate.add("CONFIG");
		listKeyGenerate.add("CONFIG.LAYOUT");
		for (SimpleSiteLayout siteLayout : configSpider.getSiteLayout()) {
			listKeyGenerate.add("CONFIG.LAYOUT."+siteLayout.getName().toUpperCase());
		}
		//add keys for JAR (templateFiles-jar.xml)
		listKeyGenerate = addSuffixToKeyGenerate(listKeyGenerate, templateFileJarSuffix);
		listKeyGenerate = addTemplateFileDependence(listKeyGenerate); //processa os atributos entryDepends
		
		//call monitor and build
		monitorGenerateFile(listKeyGenerate);	
	}
	
	/**
	 * Build configuration of tech
	 */
	public void buildTech() {
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider();
		
		lastBuild.put(BuildManagerUtil.KEY_BUILD_TECH, new Date().getTime()); //grava que j� fez build de TECH alguma vez
		this.currentBuild = BuildManagerUtil.KEY_BUILD_TECH;
		
		List<String> listKeyGenerate = new LinkedList<String>();
		listKeyGenerate.add("TECH");
		
		//selected techs
		for (String keyTech : configSpider.getTech().keySet()) {
			listKeyGenerate.add("TECH."+keyTech.toUpperCase());	
			listKeyGenerate.add("TECH."+keyTech.toUpperCase()+"."+configSpider.getTech(keyTech).toUpperCase());
		}
	
		listKeyGenerate.add("TECH.OTHERS");
		for (String techOther : configSpider.getTechOthers()) {
			listKeyGenerate.add("TECH.OTHERS."+techOther.toUpperCase());
		}
		//add keys for JAR (templateFiles-jar.xml)
		listKeyGenerate = addSuffixToKeyGenerate(listKeyGenerate, templateFileJarSuffix);
		listKeyGenerate = addTemplateFileDependence(listKeyGenerate); //processa os atributos entryDepends
		
		//call monitor and build
		monitorGenerateFile(listKeyGenerate);
	}
	
	/**
	 * Build configuration of package
	 */
	public void buildPackage() {
		lastBuild.put(BuildManagerUtil.KEY_BUILD_PACKAGE, new Date().getTime()); //grava que j� fez build de PACKAGE alguma vez
		this.currentBuild = BuildManagerUtil.KEY_BUILD_PACKAGE;
		
		List<String> listKeyGenerate = new LinkedList<String>();
		listKeyGenerate.add("PACKAGE");
		
		//call monitor and build
		monitorGenerateFile(listKeyGenerate);
	}
	
	/**
	 * Build configuration of CRUD
	 */
	public void buildAllCRUDs() {
		lastBuild.put(BuildManagerUtil.KEY_BUILD_CRUD, new Date().getTime()); //grava que j� fez build de CRUD alguma vez
		this.currentBuild = BuildManagerUtil.KEY_BUILD_CRUD;
		
		try {
			//gera as valida��es no pojo
			ConfigSpider configSpider = ConfigurationEditor.getConfigSpider();
			List<Crud> cruds = configSpider.getCruds(); //todo os cruds desse projeto
			
			Map<Crud, List<String>> crudTemplateFiles = new HashMap<Crud, List<String>>();
			for (Crud crud : cruds) {
				crudRules(crud, crudTemplateFiles);
			}
			
			monitorGenerateCrud(crudTemplateFiles); //build do crud
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * Build configuration of CRUD
	 */
	public void buildOneCRUD(Crud crud) {
		lastBuild.put(BuildManagerUtil.KEY_BUILD_CRUD, new Date().getTime()); //grava que j� fez build de CRUD alguma vez
		this.currentBuild = BuildManagerUtil.KEY_BUILD_CRUD;
		
		try {
			Map<Crud, List<String>> crudTemplateFiles = new HashMap<Crud, List<String>>();
			crudRules(crud, crudTemplateFiles);
			
			monitorGenerateCrud(crudTemplateFiles); //build do crud
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Build configuration of mapping
	 */
	public void buildMapping() {
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider();
		
		lastBuild.put(BuildManagerUtil.KEY_BUILD_MAPPING, new Date().getTime()); //grava que j� fez build de MAPPING alguma vez
		this.currentBuild = BuildManagerUtil.KEY_BUILD_MAPPING;
		
		List<String> listKeyGenerate = new LinkedList<String>();
		listKeyGenerate.add("MAPPING");
		if (configSpider.getTech("Database Type") != null) {
			listKeyGenerate.add("MAPPING."+configSpider.getTech("Database Type").toUpperCase());			
		}
		
		//add keys for JAR (templateFiles-jar.xml)
		listKeyGenerate = addSuffixToKeyGenerate(listKeyGenerate, templateFileJarSuffix);
		listKeyGenerate = addTemplateFileDependence(listKeyGenerate); //processa os atributos entryDepends
		
		//call monitor and build
		monitorGenerateFile(listKeyGenerate);	
	}

	/**
	 * Method run after the build mapping.
	 * It is responsible for running the ant script of mapping.
	 */
/*	
	private String postBuildMapping() {
		StringBuffer buildLog = new StringBuffer();
		
		//run hibernate tools ant
		List<TemplateFile> listTemplate = mapTemplateFiles.get("MAPPING"); //get Template Files, for this key
		if (listTemplate != null) {
			Map<String, String> parameters = new HashMap<String, String>();
			
			String mappingPath = "";
			for (TemplateFile templateFile : listTemplate) {				
				if (templateFile.getPathGenerationFile().lastIndexOf("build.xml") == templateFile.getPathGenerationFile().length()-9) {
					File buildFile = new File(ProjectInfo.getLocation()+templateFile.getPathGenerationFile());
					if (buildFile.exists()) {
						int lastDir = templateFile.getPathGenerationFile().lastIndexOf("/");
						mappingPath = templateFile.getPathGenerationFile().substring(1, lastDir);
						
						parameters.put("mappingPath", ProjectInfo.getLocation()+File.separator+mappingPath);
						parameters.put("projectPath", ProjectInfo.getLocation());
						buildLog.append(AntUtil.runProject(ProjectInfo.getLocation()+templateFile.getPathGenerationFile(), parameters, ProjectInfo.getLocation()+File.separator+mappingPath));
					} else {
						buildLog.append("The file "+buildFile.getPath()+" does not exits.");
						//PropertiesUtil.getMessage("build.msg.fileNotExists", new String[] {buildFile.getPath()},  "spider")
					}
				}
			}
			
			//delete mapping folder
//			if (!mappingPath.equals("")) {
//				File mappingFolder = new File(ConfigurationEditor.activeProject.getLocation().toOSString()+mappingPath);
//				if (mappingFolder.exists() && mappingFolder.isDirectory()) { //delete mapping folder
//					FileUtil.deleteDir(mappingFolder);
//				}
//			}
			
			ConfigurationEditor.refreshWorkspace(); //refresh to read the files generated (mapping)
			
			//refresh classes for crud
			CrudPage crudPage = (CrudPage)ConfigurationEditor.getInstance().getPage(PAGE_NAME.CRUD);
			crudPage.refreshComboAnnotation();
		}
		
		return buildLog.toString();
	}
*/	
	/**
	 * Internal Crud Rules - replace annotations, make list of template files, etc...
	 * @param crud
	 * @param crudTemplateFiles
	 */
	private void crudRules(Crud crud, Map<Crud, List<String>> crudTemplateFiles) {
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider();
		List<String> listKeyGenerate = makeListTemplateFilesForCrud(crud, configSpider);

		crudTemplateFiles.put(crud, listKeyGenerate);
	}
	
	/**
	 * Cria uma lista de arquivos de template (que devem ser processado) para o CRUD.
	 * @param crud
	 * @param configSpider
	 * @return
	 */
	private List<String> makeListTemplateFilesForCrud(Crud crud, ConfigSpider configSpider) {
		Template template = ConfigurationEditor.getCurrentTemplate();
		
		String crudType = crud.getCrudType();
		if (crud.getCrudType().contains(".")) { //se tem . (ponto), pega o �ltimo nome
			String temp[] = crud.getCrudType().split("\\.");
			crudType = temp[temp.length-1];
		}
		
		List<String> listKeyGenerate = new LinkedList<String>();
		listKeyGenerate.add("CRUD");
		listKeyGenerate.add("CRUD."+crudType.toUpperCase());
		
		for (String techName : template.getTech().keySet()) {
			if (configSpider.getTech(techName) != null) {
				listKeyGenerate.add("CRUD."+configSpider.getTech(techName).toUpperCase());
				listKeyGenerate.add("CRUD."+crudType.toUpperCase()+"."+configSpider.getTech(techName).toUpperCase());				
			}
		}
		
		//add keys for JAR (templateFiles-jar.xml)
		listKeyGenerate = addSuffixToKeyGenerate(listKeyGenerate, templateFileJarSuffix);
		listKeyGenerate = addTemplateFileDependence(listKeyGenerate); //processa os atributos entryDepends
		
		return listKeyGenerate;
	}
	
	/**
	 * Generate File from template
	 * @param templateName
	 * @param pathTemplateFile
	 * @param pathGenerationFile
	 */
	public String generateFile(String templateName, TemplateFile template, TemplateDataGroup dataGroup, Map<String, Integer> countItensLog) {
		StringBuffer generateFileLog = new StringBuffer();
		
    	try {    
    		HashMap<String, Object> velocityVariables = new HashMap<String, Object>(); //map for velocity variables
    		
    		//path template
			String pathTemplate = BuildManagerUtil.getPathFolderTemplates()+File.separator+templateName;
			
			String fullPathFileTemplate = "";
			
			List listBuilds = BuildManagerUtil.getListBuilds(lastBuild);
			
			//make full path template file
			if (template.getPathTemplateFile() != null && !template.getPathTemplateFile().equals("")) {
				fullPathFileTemplate = BuildManagerUtil.getFullPathTemplateFile(template.getPathTemplateFile(), pathTemplate);
				
				//verifica se o pathTemplate existe
				if (!new File(fullPathFileTemplate).exists()) {
					generateFileLog.append("file not found...: ").append(template.getPathTemplateFile()).append("\n");
					countLog("file not found...: ", countItensLog);
					//System.out.println(PropertiesUtil.getMessage("build.msg.templateFileNotExists", new String[] {template.getPathTemplateFile()},  "spider"));
					return generateFileLog.toString();
				}		
			}
			
			//a path can return an or more paths, according to variables
			dataGroup.setConfig(ConfigurationEditor.getConfigSpider());
			dataGroup.setBundle(PropertiesUtil.readTemplatePropertiesToMap());
			String[] arrayPathGeneration = BuildManagerUtil.processPathVariables(template.getPathGenerationFile(), dataGroup); 
			
			for (String finalPathGeneration : arrayPathGeneration) { //loop in generation paths
				IFile ifile = ConfigurationEditor.activeProject.getFile(finalPathGeneration);
				String pathWriterFile = ifile.getLocation().toOSString();
				File file = new File(pathWriterFile);

				dataGroup.setPathTemplate(pathTemplate);
				dataGroup.setPathFolderTemplates(BuildManagerUtil.getPathFolderTemplates());
				dataGroup.setPathFolderGeneration(ProjectInfo.getLocation()+File.separator);
				dataGroup.setListBuilds(listBuilds);
				
				//verifica se o arquivo existe, e se tem incrementos (n�o gerar o arquivo todo de novo)
				if (file.exists() && template.getIncrements() != null && template.getIncrements().size() > 0) {
					for (TemplateFileIncrement increment : template.getIncrements()) {
						String fullPathIncremental = BuildManagerUtil.getFullPathTemplateFile(increment.getPath(), pathTemplate);
						BuildManagerUtil.incrementSourceCode(pathWriterFile, fullPathIncremental, increment.getPattern(), increment.getFirstAfter(), increment.getParams(), dataGroup);
					}
					
					//save build date of files
					BuildManagerUtil.addFileToLastBuild(lastBuild, pathWriterFile, finalPathGeneration);
					generateFileLog.append("incremental merge: ").append(finalPathGeneration).append("\n");
					countLog("incremental merge: ", countItensLog);
					continue;
				}
				
				//verifica se � necess�rio apagar o arquivo
				if (ifile.exists() && template.isSkipIfExists()) { //skip file if exists and template waiting for it
					generateFileLog.append("skip file........: ").append(finalPathGeneration).append("\n");
					countLog("skip file........: ", countItensLog);
					continue;
				} else if (file.exists() && template.isExcludeIfEmpty() && file.isDirectory() && file.list().length == 0) { //delete folder if is empty
					file.delete();
					generateFileLog.append("delete file......: ").append(finalPathGeneration).append("\n");
					countLog("delete file......: ", countItensLog);
					continue;
				} else if (file.exists() && file.isDirectory() && template.isExcludeForce()) { //delete folder (force)
					FileUtil.deleteDir(file);
					generateFileLog.append("delete dir.......: ").append(finalPathGeneration).append("\n");
					countLog("delete file......: ", countItensLog);
					continue;
				} else if (!file.exists() && (template.isExcludeIfEmpty() || template.isExcludeForce())) { //o arquivo n�o existe, ent�o n�o precisa ser excluido... s� vai para o pr�ximo arquivo
					continue;
				}

				//se tem algum arquivo para gerar
				if (template.getPathTemplateFile() != null && !template.getPathTemplateFile().equals("")) {
					//-------merge check
					boolean merge = false;
					if (lastBuild.get(finalPathGeneration) != null) { //if already had generation of the file
						merge = verifyMergeNeed(pathWriterFile, lastBuild.get(finalPathGeneration)); //merge need?
					}
					//merge need, what do?
					if (merge == true && rememberReplace == false && rememberMerge == false && rememberSkip == false) {
						
						//ConfirmDialog dialog = new ConfirmDialog(ConfigurationEditor.shell, finalPathGeneration);
						//DialogUtil.openSyncDialog("dialog");
						
						/*if (dialog.getReturnCode() == dialog.MERGE_ID) { //if merge, write new file
							if (dialog.isRemember()) {rememberMerge = true;}
						} else if (dialog.getReturnCode() == dialog.SKIP_ID) {
							if (dialog.isRemember()) {rememberSkip = true;}
							merge = false;
							continue; //skip this file, continue next
						} else {
							if (dialog.isRemember()) {rememberReplace = true;}
							merge = false;
						}*/
					}
					
					if (merge) { //if merge, this file is a new file
						pathWriterFile = pathWriterFile+".new";
					}
					//-------merge check
					
					//generate file
					velocityVariables.put("data", dataGroup); //conjunto de v�riaveis que podem ser usados no template
					
					TemplateEngine templateEngine = TemplateEngineFactory.getEngine(template.getPathTemplateFile(), false);
					templateEngine.runFile(fullPathFileTemplate, pathWriterFile, velocityVariables);
					
					generateFileLog.append(templateEngine.getAction()).append(finalPathGeneration).append("\n");
					countLog(templateEngine.getAction(), countItensLog);

					//merge, if necessary
					if (merge == true && rememberReplace == false) { //merge, no replace!
						String pathWriterOldFile = pathWriterFile.replaceAll(".new", "");
						mergeFile(pathWriterOldFile, pathWriterFile);
						generateFileLog.append("manual merge.....: ").append(finalPathGeneration).append("\n");
						countLog("manual merge.....: ", countItensLog);
					}
					
					//save build date of files
					BuildManagerUtil.addFileToLastBuild(lastBuild, pathWriterFile, finalPathGeneration);	
				} else if ((template.getPathTemplateFile() == null || template.getPathTemplateFile().equals("")) && !file.exists() && file.getName().indexOf(".") == -1) {
					//n�o tem arquivo de templates, mas � diret�rio e tem que criar os packages
					File folderGeneration = new File(pathWriterFile);
					folderGeneration.mkdirs();
					generateFileLog.append("create package...: ").append(finalPathGeneration).append("\n");
					countLog("create package...: ", countItensLog);
				}

				//verifica se � necess�rio executar o arquivo
				if (file.exists() && template.isRun()) {
					if (template.getPathGenerationFile().lastIndexOf("build.xml") == template.getPathGenerationFile().length()-9) {
						int lastDir = finalPathGeneration.lastIndexOf("/");
						String pathLog = "";
						if (lastDir != 0) {
							pathLog = finalPathGeneration.substring(1, lastDir);
						}
						
						Map<String, String> parameters = new HashMap<String, String>();	
						parameters.put("projectPath", ProjectInfo.getLocation());
						generateFileLog.append("run script.......: ").append(finalPathGeneration).append("\n");
						
						String logAnt = AntUtil.runProject(ProjectInfo.getLocation()+finalPathGeneration, parameters, ProjectInfo.getLocation()+File.separator+pathLog);
						generateFileLog.append(logAnt);
						
						//check ant result
						if (logAnt.indexOf("BUILD FAILED") != -1) {
							countLog("run failed.......: ", countItensLog);
						} else {
							countLog("run script.......: ", countItensLog);
						}
					} else if (template.getPathGenerationFile().lastIndexOf(".sc") == template.getPathGenerationFile().length()-3) {
						TemplateCommand command = (TemplateCommand)XmlManager.loadXml(ProjectInfo.getLocation()+finalPathGeneration, XML_TYPE.TEMPLATE_COMMAND);
						generateFileLog.append("run script.......: ").append(finalPathGeneration).append("\n");
						countLog("run script.......: ", countItensLog);
						generateFileLog.append(CommandLineUtil.exec(command));
						generateFileLog.append("\n");
					} else {
						generateFileLog.append("invalid script...: ").append(finalPathGeneration).append("\n");
						countLog("invalid script...: ", countItensLog);
					}
				}
			}
		} catch (Exception e) {
			generateFileLog.append("template error...: ").append(template.getPathTemplateFile()).append("\n");
			countLog("template error...: ", countItensLog);
			logger.error("Error generating file: "+template.getPathGenerationFile(), e);
			e.printStackTrace();
		}
		
		return generateFileLog.toString();
	}

	/**
	 * Create one monitor, and start generate of CRUDs
	 * @param crudTemplateFiles
	 */
	private void monitorGenerateCrud(final Map<Crud, List<String>> crudTemplateFiles) {
		if (crudTemplateFiles == null || crudTemplateFiles.size() == 0) {return;}
		
		setInBuild(true); //build running now
		GenerateCrudJob job = new GenerateCrudJob(PropertiesUtil.getMessage("dialog.progressBuild.title", "spider"), this, crudTemplateFiles);
		configJob(job, currentBuild);
	}
	
	/**
	 * Create one monitor, and start generate of File.
	 * @param listKeyGenerate
	 */
	private void monitorGenerateFile(final List<String> listKeyGenerate) {
		setInBuild(true); //build running now
		GenerateFileJob job = new GenerateFileJob(PropertiesUtil.getMessage("dialog.progressBuild.title", "spider"), this, listKeyGenerate);
		
		configJob(job, currentBuild);
	}
	
	/**
	 * Config internal Job (build job).
	 * @param job
	 */
	private void configJob(final Job job, final String currentBuild) {
	    job.addJobChangeListener(new JobChangeAdapter() {
	           public void done(IJobChangeEvent event) {
	        	   //job finish - SUCESSFULL
	        	   if (event.getResult().isOK()) {
        			   Generate jobGenerate = (Generate)job;
	        		   if (currentBuild.equals(BuildManagerUtil.KEY_BUILD_MAPPING)) {
	        			   //refresh crud page
	        			   //CrudByMappingPage crudPage = (CrudByMappingPage)ConfigurationEditor.getInstance().getPage(PAGE_NAME.CRUDByMapping);
	        			   //crudPage.refreshComboAnnotation();
	        		   }

        			   //show message
        			   String buildLog = jobGenerate.getGenerateLog();
        			   if (buildLog.indexOf("BUILD FAILED") != -1) {
        				   DialogUtil.showMessageDetail(PropertiesUtil.getMessage("dialog.progressBuild.message.title", "spider"), PropertiesUtil.getMessage("dialog.progressBuild.message.fail", "spider"), buildLog, StyleRangeUtil.generateFilesStyles(), true, DialogUtil.MESSAGE_TYPE.ERROR);
        			   } else {   
        				   DialogUtil.showMessageDetail(PropertiesUtil.getMessage("dialog.progressBuild.message.title", "spider"), PropertiesUtil.getMessage("dialog.progressBuild.message.success", "spider"), buildLog, StyleRangeUtil.generateFilesStyles(), true, DialogUtil.MESSAGE_TYPE.INFO);
        			   }	   
	        		   
	        	   //job finish - ERROR
	        	   } else {
	        		   DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.progressBuild.message.title", "spider"), PropertiesUtil.getMessage("dialog.progressBuild.message.fail", "spider"), DialogUtil.MESSAGE_TYPE.ERROR);
	        	   }
	        	   
	        	   //actions after job
	        	   writeLastBuild(); //write last build
	        	   ConfigurationEditor.refreshWorkspace();
	        	   setInBuild(false); //build stoped now
	           }
		});   
	    job.setRule(ResourcesPlugin.getWorkspace().getRoot());
	    job.setUser(true);
	    job.setPriority(Job.SHORT);
	    job.schedule();
	}
	
	private boolean verifyMergeNeed(String pathFile, long lastBuild) {
		if (lastBuild == 0) {return false;} //nunca gerou build // never generate build
		
		File fileGeneration = new File(pathFile);
		if (fileGeneration.exists()) { //verify, if file exists
			if (fileGeneration.lastModified() > lastBuild) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Merge two files
	 * @param pathFile
	 * @param pathNewFile
	 */
	private void mergeFile(String pathFile, String pathNewFile) {
		String command = SpiderPlugin.getDefault().getMergeToolPreference() +" "+pathFile+" "+pathNewFile+" "+pathFile;
        try {
			Process child = Runtime.getRuntime().exec(command);
			child.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//delete new file, after merge, he is not necessary more
		File newFile = new File(pathNewFile);
		newFile.delete();		
		
		//delete merge backup file
		File backupFile = new File(pathNewFile.replaceAll(".new", ".bak"));
		if (backupFile.exists()) {
			backupFile.delete();
		}
	}
	
	/**
	 * Count log itens by type
	 * @param type
	 * @param countItensLog
	 */
	private void countLog(String type, Map<String, Integer> countItensLog) {
		Integer count = countItensLog.get(type);
		if (count == null) {
			count = 0;
		}
		count++;
		countItensLog.put(type, count);
	}
		
	/**
	 * Add suffix to all keyGenerate.
	 * If JAR key, check JAR dependence.
	 * Adiciona um sufixo para todas as keys. E cada item � adicionado no List, para expandir a quantidade de keys (do arquivo de template) que tem que ser processadas.
	 * @param listKeyGenerate
	 * @param suffix
	 * @return
	 */
	private List<String> addSuffixToKeyGenerate(List<String> listKeyGenerate, String suffix) {
		List<String> listKeyGenerateFinal = new LinkedList<String>(listKeyGenerate);
		for (String keyGen : listKeyGenerate) {
			String jarKey = keyGen+suffix;
			if (mapTemplateFiles.containsKey(jarKey)) {
				listKeyGenerateFinal.add(0, jarKey);
			}
		}
		
		return listKeyGenerateFinal;
	}
	
	/**
	 * Processa o entryDepends do templateFiles e templateFiles-jar.  Add entry of entryDepends to list of keys.
	 * @param listKeyGenerate
	 * @return
	 */
	private List<String> addTemplateFileDependence(List<String> listKeyGenerate) {
		List<String> listKeyGenerateFinal = new LinkedList<String>(listKeyGenerate);
		for (String keyGen : listKeyGenerate) {
	
			List<TemplateFile> listTemplate = mapTemplateFiles.get(keyGen); //get Template Files, for this key - para cada key, procura os arquivo de template
			if (listTemplate != null) { //if exist files... - se existir arquivos para a key, inicia a gera��o desses arquivos
				for (TemplateFile templateFile : listTemplate) {
					if (templateFile.getEntryDepends() != null && !templateFile.getEntryDepends().equals("")) {
						if (templateFile.getEntryDepends().indexOf(templateFileJarSuffix) > 0) {
							listKeyGenerateFinal.add(0, templateFile.getEntryDepends());						
						} else {
							listKeyGenerateFinal.add(templateFile.getEntryDepends());
						}
					}
				}
			}
			
		}
		
		return listKeyGenerateFinal;
	}
	
	/**
	 * Write last build file
	 */
	private void writeLastBuild() {
		IFile fileSpiderBuild = ConfigurationEditor.activeProject.getFile(".spiderBuild");
		XmlManager.writeXml(lastBuild, fileSpiderBuild.getLocation().toOSString(), XML_TYPE.SPIDER_BUILD);
	}

	public Map<String, List<TemplateFile>> getMapTemplateFiles() {
		return mapTemplateFiles;
	}

	/**
	 * Return status of build.
	 * true = build running
	 * false = no build in this time
	 * @return
	 */
	public static boolean isInBuild() {
		return inBuild;
	}

	private static void setInBuild(boolean inBuild) {
		if (inBuild == false) { //call refresh tree after build
			//MappingPage mappingPage = (MappingPage)ConfigurationEditor.getInstance().getPage(PAGE_NAME.Mapping);
			//if mapping page is active
			//if (mappingPage != null) {
			//	mappingPage.refreshTree();				
			//}
		}
		
		BuildManager.inBuild = inBuild;
	}
	
}
