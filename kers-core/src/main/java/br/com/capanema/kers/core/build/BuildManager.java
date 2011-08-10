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
package br.com.capanema.kers.core.build;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.capanema.kers.common.configuration.Constants;
import br.com.capanema.kers.common.model.project.ProjectConfig;
import br.com.capanema.kers.common.model.template.Crud;
import br.com.capanema.kers.common.model.template.SimpleSiteLayout;
import br.com.capanema.kers.common.model.template.Template;
import br.com.capanema.kers.common.model.template.TemplateDataGroup;
import br.com.capanema.kers.common.model.template.TemplateFile;
import br.com.capanema.kers.common.model.template.TemplateFolder;
import br.com.capanema.kers.common.util.PropertiesFileUtil;
import br.com.capanema.kers.common.util.file.FileUtil;
import br.com.capanema.kers.common.util.string.StringUtil;
//import br.com.capanema.kers.core.build.jobs.Generate;
//import br.com.capanema.kers.core.build.jobs.GenerateCrudJob;
//import br.com.capanema.kers.core.build.jobs.GenerateFileJob;
import br.com.capanema.kers.tempate.engine.xml.magager.TemplateRepositoryManager;
import br.com.capanema.kers.velocity.template.engine.TemplateEngine;
import br.com.capanema.kers.velocity.template.engine.VelocityTemplateEngine;
import br.com.capanema.kers.velocity.template.factory.TemplateEngineFactory;
import br.com.capanema.kers.velocity.util.BuildManagerUtil;

public class BuildManager {

  private final String templateFileJarSuffix = ".JAR";

  private Template template;
  private ProjectConfig projectConfig;

  private TemplateDataGroup dataGroup;
  
  private String currentBuild;
	private Map<String, Long> lastBuild = new LinkedHashMap<String, Long>();
	
	private BuildStateMachine stateMachine;
	
	private Logger logger = Logger.getLogger(getClass());
	
	public BuildManager(ProjectConfig projectConfig) throws Exception {
	  this.template = TemplateRepositoryManager.instance().getTemplate(projectConfig.getTempateName());

	  this.projectConfig = projectConfig;
	  
	  
		String templateRealPath = TemplateRepositoryManager.instance().getRepository(template.getRepositoryName()).getPath();
		this.projectConfig.setTemplateRealPath(templateRealPath);
		
		validatedProjectConfig();
		
		this.dataGroup = initializeTemplateDataGroup();
		
	}

	private void validatedProjectConfig() {
	  
	  this.projectConfig.setMainPackageDir(this.projectConfig.getMainPackage().replace(".", File.separator));
	}
	
	private TemplateDataGroup initializeTemplateDataGroup() {
	  TemplateDataGroup dataGroup = new TemplateDataGroup();
	  
	  dataGroup.setConfig(this.projectConfig);
	  
	  return dataGroup;
	}
	

	/**
	 * 
	 * @throws Exception
	 */
	public void buildEstructure() throws Exception {
	  
	  this.currentBuild = Constants.KEY_BUILD_ESTRUCTURE;
    lastBuild.put(this.currentBuild, new Date().getTime());
    stateMachine.valiadateBuild(currentBuild);
	  
    System.out.println("\n ##### Gerando estrutura do projeto"); 
    
	  try {
	    for (TemplateFolder dir : template.getTemplateFolderes()) {
        String[] dirs = BuildManagerUtil.processPathVariables(dir.getPathGenerationFolder(), dataGroup);
        for (String destDir : dirs) {
          System.out.println("\t folder: " + destDir);
          FileUtil.createDir(this.projectConfig.getSourceDestFolder() + destDir);
        }
	    }
	    
	    System.out.println("\n ##### Copiando arquivos do projeto"); 

	    makeFiles(template.getFilesToCopy());
	    
	    /*for (TemplateFile dir : template.getFilesToCopy()) {
        
	      
        String[] dirs = BuildManagerUtil.processPathVariables(dir.getPathGenerationFile(), dataGroup);
        for (String destDir : dirs) {
          System.out.println("\t File: " + destDir);
          FileUtil.copyFile(template.getRealPath() + dir.getPathTemplateFile(), this.projectConfig.getSourceDestFolder() + destDir);
        }
      }*/
	    
	    //TODO e os templates que não são por CRUD? Ex persistence.xml ?????
	    
  	  /*for (String dir : template.getProjectFolderes()) {
  	      dir = dir.replace(Constants.TEMPLATE_MODULE_FOLDER, projectConfig.getArtifactId());
          FileUtil.createDir(this.projectConfig.getSourceDestFolder() + dir);
  	  }
      
      for (String dir : template.getFilesToCopy()) {
        String destDir = dir.replace(Constants.TEMPLATE_MODULE_FOLDER, projectConfig.getArtifactId());
        FileUtil.copyFile(template.getRealPath() + dir, this.projectConfig.getSourceDestFolder() + destDir);
      }*/
	  } catch (IOException e) {
	    e.printStackTrace();
	    throw new Exception(e);
	  }
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void buildMavenConfig() throws Exception {
	  this.currentBuild = Constants.KEY_BUILD_MAVEN_CONFIG;
	  lastBuild.put(this.currentBuild, new Date().getTime());
	  stateMachine.valiadateBuild(currentBuild);
    
    List<TemplateFile> listKeyGenerate = template.getMapTemplateFiles().get(Constants.KEY_BUILD_MAVEN_CONFIG);
    makeFiles(listKeyGenerate); 
	}
	
	/**
	 * 
	 * Fara o build dos templates com escopo de projeto. 
	 * 
	 * TODO 
	 * 
	 */
	public void buildProjectClasses() {
	   
	    this.currentBuild = Constants.KEY_BUILD_FOR_PROJECT_CLASSES;
	    lastBuild.put(this.currentBuild, new Date().getTime());
	    stateMachine.valiadateBuild(this.currentBuild);

	    List<TemplateFile> listKeyGenerate = template.getMapTemplateFiles().get(Constants.KEY_BUILD_FOR_PROJECT_CLASSES);
	    
	    makeFiles(listKeyGenerate);
	    
	    
	    //List<String> listKeyGenerate = new LinkedList<String>();
	    //listKeyGenerate.add("CONFIG");
	    //listKeyGenerate.add("CONFIG.LAYOUT");
	    //for (SimpleSiteLayout siteLayout : projectConfig.getSiteLayout()) {
	    // listKeyGenerate.add("CONFIG.LAYOUT."+siteLayout.getName().toUpperCase());
	    //}
	    //add keys for JAR (templateFiles-jar.xml)
	    //listKeyGenerate = addSuffixToKeyGenerate(listKeyGenerate, templateFileJarSuffix);
	    //listKeyGenerate = addTemplateFileDependence(listKeyGenerate); //processa os atributos entryDepends
	    //call monitor and build
	    //monitorGenerateFile(listKeyGenerate); 
	  }
	
	/**
	 * Build configuration of layout.
	 */
	public void buildLayout() {
	
	  this.currentBuild = Constants.KEY_BUILD_LAYOUT;
		lastBuild.put(this.currentBuild, new Date().getTime());
		stateMachine.valiadateBuild(this.currentBuild);
		
		List<String> listKeyGenerate = new LinkedList<String>();
		listKeyGenerate.add("CONFIG");
		listKeyGenerate.add("CONFIG.LAYOUT");
		for (SimpleSiteLayout siteLayout : projectConfig.getSiteLayout()) {
			listKeyGenerate.add("CONFIG.LAYOUT."+siteLayout.getName().toUpperCase());
		}
		
		//add keys for JAR (templateFiles-jar.xml)
		listKeyGenerate = addSuffixToKeyGenerate(listKeyGenerate, templateFileJarSuffix);
		//listKeyGenerate = addTemplateFileDependence(listKeyGenerate); //processa os atributos entryDepends
		
		//call monitor and build
		//monitorGenerateFile(listKeyGenerate);	
	}
	
	/**
	 * Build configuration of tech
	 */
	public void buildTechConfig() {
		
	  this.currentBuild = Constants.KEY_BUILD_TECH;
		lastBuild.put(this.currentBuild, new Date().getTime());
		stateMachine.valiadateBuild(this.currentBuild);
		
		List<String> listKeyGenerate = new LinkedList<String>();
		listKeyGenerate.add("TECH");
		
		//selected techs //PROBLEM
		/*for (String keyTech : configSpider.getTech()) {
			//listKeyGenerate.add("TECH."+keyTech.toUpperCase());	
			//listKeyGenerate.add("TECH."+keyTech.toUpperCase()+"."+configSpider.getTechLayout(keyTech).toUpperCase());
		}*/
	
		listKeyGenerate.add("TECH.OTHERS");
		/*for (String techOther : configSpider.getTechOthers()) {
			listKeyGenerate.add("TECH.OTHERS."+techOther.toUpperCase());
		}*/
		//add keys for JAR (templateFiles-jar.xml)
		listKeyGenerate = addSuffixToKeyGenerate(listKeyGenerate, templateFileJarSuffix);
		//listKeyGenerate = addTemplateFileDependence(listKeyGenerate); //processa os atributos entryDepends
		
		//call monitor and build
		//monitorGenerateFile(listKeyGenerate);
	}
	
	/**
	 * Build configuration of package
	 */
	public void buildPackage() {

	  this.currentBuild = Constants.KEY_BUILD_PACKAGE;
	  lastBuild.put(this.currentBuild, new Date().getTime());
	  stateMachine.valiadateBuild(this.currentBuild);
		
		List<String> listKeyGenerate = new LinkedList<String>();
		listKeyGenerate.add("PACKAGE");
		
		//call monitor and build
		//monitorGenerateFile(listKeyGenerate);
		//makeFiles(listKeyGenerate);
	}
	
	/**
	 * Build configuration of CRUD
	 */
	public void buildCRUDs(List<Crud> cruds) {
		
	  this.currentBuild = Constants.KEY_BUILD_FOR_CRUD;
	  lastBuild.put(this.currentBuild, new Date().getTime());
		stateMachine.valiadateBuild(this.currentBuild);
		
		try {
			Map<Crud, List<TemplateFile>> crudTemplateFiles = new HashMap<Crud, List<TemplateFile>>();
			for (Crud crud : cruds) {
				crudRules(crud, crudTemplateFiles);
			}
			//monitorGenerateCrud(crudTemplateFiles); //build do crud
			makeCruds(crudTemplateFiles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Build configuration of CRUD
	 */
	/*public void buildOneCRUD(Crud crud) {
		lastBuild.put(Constants.KEY_BUILD_CRUD, new Date().getTime()); //grava que j� fez build de CRUD alguma vez
		this.currentBuild = Constants.KEY_BUILD_CRUD;
		
		try {
			Map<Crud, List<TemplateFile>> crudTemplateFiles = new HashMap<Crud, List<TemplateFile>>();
			crudRules(crud, crudTemplateFiles);
			
			monitorGenerateCrud(crudTemplateFiles); //build do crud
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/
	
	/**
	 * Build configuration of mapping
	 */
	public void buildMapping() {
		
	  this.currentBuild = Constants.KEY_BUILD_MAPPING;
		lastBuild.put(this.currentBuild, new Date().getTime());
		this.stateMachine.valiadateBuild(this.currentBuild);
		
		List<String> listKeyGenerate = new LinkedList<String>();
		listKeyGenerate.add("MAPPING");
		//PROBLEM
		/*if (configSpider.getTech("Database Type") != null) {
			listKeyGenerate.add("MAPPING."+configSpider.getTech("Database Type").toUpperCase());			
		}*/
		
		//add keys for JAR (templateFiles-jar.xml)
		listKeyGenerate = addSuffixToKeyGenerate(listKeyGenerate, templateFileJarSuffix);
		//listKeyGenerate = addTemplateFileDependence(listKeyGenerate); //processa os atributos entryDepends
		
		//call monitor and build
		//monitorGenerateFile(listKeyGenerate);	
	}

	
	/*
	 * Internal Build
	 */
	
	/**
	 * Internal Crud Rules - replace annotations, make list of template files, etc...
	 * @param crud
	 * @param crudTemplateFiles
	 * @throws Exception 
	 */
	private void crudRules(Crud crud, Map<Crud, List<TemplateFile>> crudTemplateFiles) throws Exception {
		
	  List<TemplateFile> listKeyGenerate = template.getMapTemplateFiles().get(Constants.KEY_BUILD_FOR_CRUD);

		crudTemplateFiles.put(crud, listKeyGenerate);
	}
	
  private void makeFiles(final List<TemplateFile> listKeyGenerate) {
    // path to all templates
    // Map<String, String> pathAllTemplates =
    // BuildManagerUtil.getPathAllTemplates();
    // dataGroup.setPathAllTemplates(pathAllTemplates);

    // se existir arquivos para a key, inicia a geracao desses arquivos
    if (listKeyGenerate != null) {
      for (TemplateFile templateFile : listKeyGenerate) {
        if (templateFile.getEntryDepends() != null && !templateFile.getEntryDepends().equals("")) {
          continue; // if this file will be a reference to another entry,
          // continue in the next file. entry reference already was include in
          // listKeyGenerate.
        }

        String fileName = templateFile.getPathGenerationFile();
        // this template doesn't have a path to generate the file
        if (fileName == null || fileName.equals("")) {
          // fileName = templateFile.getPathTemplateFile().replace(".vm", "");
          // fileName = BuildManagerUtil.getFileNameFromPath(fileName);
          continue;
        }
        // generate
        generateFile(templateFile);
        // generateLog.append(log);
        // monitor.worked(1);
      }
    }
  }
	
  private void makeCruds(Map<Crud, List<TemplateFile>> crudTemplateFiles) {
    try {
      // path to all templates
      // Map<String, String> pathAllTemplates =
      // BuildManagerUtil.getPathAllTemplates();
      // dataGroup.setPathAllTemplates(pathAllTemplates);

      for (Crud crud : crudTemplateFiles.keySet()) {

        List<TemplateFile> listTemplate = crudTemplateFiles.get(crud);
        dataGroup.setCrud(crud); // CURRENT CRUD

        // update pojo annotations
        BuildManagerUtil.replaceAnnotationsInMethods(crud);

        if (listTemplate != null) { //se existir arquivos, inicia a geracao
          for (TemplateFile templateFile : listTemplate) {
            // check templateFile type
            if (templateFile.getEntryDepends() != null && !templateFile.getEntryDepends().equals("")) {
              continue; // if this file will be a reference to another entry, continue in the next file.
                        // entry reference already was include in listKeyGenerate.
            }

            String fileName = templateFile.getPathGenerationFile();
            // this template doesn't have a path to generate the file
            if (fileName == null || fileName.equals("")) { 
              continue;
            }
            /*if (fileName.length() > 15) {
              fileName = BuildManagerUtil.getFileNameFromPath(fileName);
            }*/
            // generate
            generateFile(templateFile);
          }
        }
      }
    } catch (RuntimeException e) {
      e.printStackTrace();
      logger.error(e);
    } finally {
      // monitor.done();
    }
  }
	
	/**
   * Generate File from template
   * @param templateName
   * @param pathTemplateFile
   * @param pathGenerationFile
   */
  public String generateFile(TemplateFile templateFile) {
    StringBuffer generateFileLog = new StringBuffer();
    try {    
      HashMap<String, Object> velocityVariables = new HashMap<String, Object>(); //map for velocity variables
        
      String fullPathFileTemplate = "";
      
      List listBuilds = BuildManagerUtil.getListBuilds(lastBuild);//????
      
      //make full path template file
      if (templateFile.getPathTemplateFile() != null && !templateFile.getPathTemplateFile().equals("")) {
        
        fullPathFileTemplate =  template.getRealPath() + File.separator + templateFile.getPathTemplateFile();
        
        //verifica se o pathTemplate existe
        if (!new File(fullPathFileTemplate).exists()) {
          generateFileLog.append("file not found...: ").append(templateFile.getPathTemplateFile()).append("\n");
          return generateFileLog.toString();
        }   
      } 
      
      //a path can return an or more paths, according to variables
      String pathRepo = template.getRealPath();
      dataGroup.setConfig(this.projectConfig);//MExido
      dataGroup.setBundle(PropertiesFileUtil.readTemplatePropertiesToMap(pathRepo, projectConfig.getLanguage()));
      
      
      String[] arrayPathGeneration = BuildManagerUtil.processPathVariables(templateFile.getPathGenerationFile(), dataGroup);
      for (String finalPathGeneration : arrayPathGeneration) { //loop in generation paths
        
        //IFile ifile = ConfigurationEditor.activeProject.getFile(finalPathGeneration);
        //String pathWriterFile = ifile.getLocation().toOSString();
        String pathWriterFile = projectConfig.getSourceDestFolder() + finalPathGeneration;
        
        if(pathWriterFile.endsWith(".vm")) {
          pathWriterFile = pathWriterFile.substring(0, pathWriterFile.length() - 3);
        }
        
        if(pathWriterFile.contains("${")) { //TODO Foi gambi
          //TODO automatizar e substituir todos. Acho q o velocity faz
          
          pathWriterFile = pathWriterFile.replace(Constants.TEMPLATE_MODULE_FOLDER, projectConfig.getArtifactId());
          
          pathWriterFile = pathWriterFile.replace(Constants.TEMPLATE_SRC_FOLDER, projectConfig.getMainPackage());
          
        }
        
        
        
        File file = new File(pathWriterFile);

        dataGroup.setPathTemplate(template.getRealPath());
        dataGroup.setPathFolderRepository(TemplateRepositoryManager.instance().getRepository(template.getRepositoryName()).getPath());
        dataGroup.setPathFolderGeneration(projectConfig.getSourceDestFolder() + File.separator);
        dataGroup.setListBuilds(listBuilds);
        
        //verifica se o arquivo existe, e se tem incrementos (n�o gerar o arquivo todo de novo)
        /*if (file.exists() && template.getIncrements() != null && template.getIncrements().size() > 0) {
          for (TemplateFileIncrement increment : template.getIncrements()) {
            BuildManagerUtil.incrementSourceCode(pathWriterFile, pathTemplate+increment.getPath(), increment.getPattern(), increment.getFirstAfter(), increment.getParams(), dataGroup);
          }
          
          //save build date of files
          BuildManagerUtil.addFileToLastBuild(lastBuild, pathWriterFile, finalPathGeneration);
          generateFileLog.append("incremental merge: ").append(finalPathGeneration).append("\n");
          countLog("incremental merge: ", countItensLog);
          continue;
        }*/
        
        //verifica se � necess�rio apagar o arquivo
        if (file.exists() && templateFile.isSkipIfExists()) { //skip file if exists and template waiting for it
          generateFileLog.append("skip file........: ").append(finalPathGeneration).append("\n");
          //countLog("skip file........: ", countItensLog);
          continue;
        } else if (file.exists() && templateFile.isExcludeIfEmpty() && file.isDirectory() && file.list().length == 0) { //delete folder if is empty
          file.delete();
          generateFileLog.append("delete file......: ").append(finalPathGeneration).append("\n");
          //countLog("delete file......: ", countItensLog);
          continue;
        } else if (file.exists() && file.isDirectory() && templateFile.isExcludeForce()) { //delete folder (force)
          FileUtil.deleteDir(file);
          generateFileLog.append("delete dir.......: ").append(finalPathGeneration).append("\n");
          //countLog("delete file......: ", countItensLog);
          continue;
        } else if (!file.exists() && (templateFile.isExcludeIfEmpty() || templateFile.isExcludeForce())) { //o arquivo n�o existe, ent�o n�o precisa ser excluido... s� vai para o pr�ximo arquivo
          continue;
        }

        //se tem algum arquivo para gerar
        if (templateFile.getPathTemplateFile() != null && !templateFile.getPathTemplateFile().equals("")) {
          //-------merge check
          boolean merge = false;
          if (lastBuild.get(finalPathGeneration) != null) { //if already had generation of the file
            merge = verifyMergeNeed(pathWriterFile, lastBuild.get(finalPathGeneration)); //merge need?
          }
          
          if (merge) { //if merge, this file is a new file
            pathWriterFile = pathWriterFile+".new";
          }
          //-------merge check
          
          //generate file
          velocityVariables.put("data", dataGroup); //conjunto de v�riaveis que podem ser usados no template
          
          TemplateEngine templateEngine = TemplateEngineFactory.getEngine(templateFile.getPathTemplateFile(), false);
          templateEngine.runFile(fullPathFileTemplate, pathWriterFile, velocityVariables);
          
          generateFileLog.append(templateEngine.getAction()).append(finalPathGeneration).append("\n");
          //countLog(templateEngine.getAction(), countItensLog);

          //merge, if necessary
          if (merge == true) {//if (merge == true && rememberReplace == false) { //merge, no replace!
            String pathWriterOldFile = pathWriterFile.replaceAll(".new", "");
            mergeFile(pathWriterOldFile, pathWriterFile);
            generateFileLog.append("manual merge.....: ").append(finalPathGeneration).append("\n");
            //countLog("manual merge.....: ", countItensLog);
          }
          
          //save build date of files
          BuildManagerUtil.addFileToLastBuild(lastBuild, pathWriterFile, finalPathGeneration);  
        } else if ((templateFile.getPathTemplateFile() == null || templateFile.getPathTemplateFile().equals("")) && !file.exists() && file.getName().indexOf(".") == -1) {
          //n�o tem arquivo de templates, mas � diret�rio e tem que criar os packages
          File folderGeneration = new File(pathWriterFile);
          folderGeneration.mkdirs();
          generateFileLog.append("create package...: ").append(finalPathGeneration).append("\n");
          //countLog("create package...: ", countItensLog);
        }

        //verifica se � necess�rio executar o arquivo
        /*if (file.exists() && template.isRun()) {
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
            TemplateCommand command = (TemplateCommand)XmlManager.loadXml(ProjectInfo.getLocation()+finalPathGeneration, XmlType.TEMPLATE_COMMAND);
            generateFileLog.append("run script.......: ").append(finalPathGeneration).append("\n");
            countLog("run script.......: ", countItensLog);
            generateFileLog.append(CommandLineUtil.exec(command));
            generateFileLog.append("\n");
          } else {
            generateFileLog.append("invalid script...: ").append(finalPathGeneration).append("\n");
            countLog("invalid script...: ", countItensLog);
          }
        }*/
      }
    } catch (Exception e) {
      generateFileLog.append("template error...: ").append(templateFile.getPathTemplateFile()).append("\n");
      //countLog("template error...: ", countItensLog);
      logger.error("Error generating file: "+templateFile.getPathGenerationFile(), e);
      e.printStackTrace();
    }
    
    return generateFileLog.toString();
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
//      if (!mappingPath.equals("")) {
//        File mappingFolder = new File(ConfigurationEditor.activeProject.getLocation().toOSString()+mappingPath);
//        if (mappingFolder.exists() && mappingFolder.isDirectory()) { //delete mapping folder
//          FileUtil.deleteDir(mappingFolder);
//        }
//      }
      
      ConfigurationEditor.refreshWorkspace(); //refresh to read the files generated (mapping)
      
      //refresh classes for crud
      CrudPage crudPage = (CrudPage)ConfigurationEditor.getInstance().getPage(PAGE_NAME.CRUD);
      crudPage.refreshComboAnnotation();
    }
    
    return buildLog.toString();
  }
*/  

  
  /**
   * Cria uma lista de arquivos de template (que devem ser processado) para o CRUD.
   * @param crud
   * @param projectConfig
   * @return
   * @throws Exception 
   */
  /*private List<TemplateFile> makeListTemplateFilesForCrud(Crud crud) throws Exception {
    
    
    //Template template = TemplateRepositoryManager.instance().getTemplate("TODO Nome do template");
    
    String crudType = crud.getCrudType();
    if (crud.getCrudType().contains(".")) { //se tem . (ponto), pega o �ltimo nome
      String temp[] = crud.getCrudType().split("\\.");
      crudType = temp[temp.length-1];
    }
    
    List<String> listKeyGenerate = new LinkedList<String>();
    listKeyGenerate.add("CRUD");
    listKeyGenerate.add("CRUD."+crudType.toUpperCase());
    
    for (String techName : template.getTechs()) {
      if (techName != null) {
        listKeyGenerate.add("CRUD."+techName.toUpperCase());
        listKeyGenerate.add("CRUD."+crudType.toUpperCase()+"."+techName.toUpperCase());       
      }
    }
    
    //add keys for JAR (templateFiles-jar.xml)
    listKeyGenerate = addSuffixToKeyGenerate(listKeyGenerate, templateFileJarSuffix);
    //listKeyGenerate = addTemplateFileDependence(listKeyGenerate); //processa os atributos entryDepends
    
    return listKeyGenerate;
  }*/
  
  /**
   * Config internal Job (build job).
   * @param job
   */
  /*private void configJob(Generate job, final String currentBuild) {
      job.addJobChangeListener(
              new JobChangeAdapter() {
             public void done(IJobChangeEvent event) {
               //job finish - SUCESSFULL
               if (event.getResult().isOK()) {
                 Generate jobGenerate = (Generate)job;
                 if (currentBuild.equals(Constants.KEY_BUILD_MAPPING)) {
                   //refresh crud page
                   CrudByMappingPage crudPage = (CrudByMappingPage)ConfigurationEditor.getInstance().getPage(PAGE_NAME.CRUDByMapping);
                   crudPage.refreshComboAnnotation();
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
  }*/
  
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
    /*String command = SpiderPlugin.getDefault().getMergeToolPreference() +" "+pathFile+" "+pathNewFile+" "+pathFile;
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
    }*/
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
      if (template.getFilesToCopy().contains(jarKey)) { 
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
	/*
	 * private List<String> addTemplateFileDependence(List<String> listKeyGenerate) {
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
	}*/
	
}