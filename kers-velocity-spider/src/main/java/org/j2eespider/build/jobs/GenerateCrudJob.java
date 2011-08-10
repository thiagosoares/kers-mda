package org.j2eespider.build.jobs;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
//import org.eclipse.jdt.core.JavaModelException;
import org.j2eespider.build.rules.BuildManager;
import org.j2eespider.ide.data.domain.ConfigSpider;
import org.j2eespider.ide.data.domain.Crud;
import org.j2eespider.ide.data.domain.TemplateDataGroup;
import org.j2eespider.ide.data.domain.TemplateFile;
import org.j2eespider.ide.data.rules.XmlManager;
import org.j2eespider.ide.data.rules.XmlManager.XML_TYPE;
import org.j2eespider.ide.editors.ConfigurationEditor;
import org.j2eespider.util.BuildManagerUtil;
import org.j2eespider.util.CommandLineUtil;
import org.j2eespider.util.PropertiesUtil;

public class GenerateCrudJob extends Job implements Generate {
	Logger logger = Logger.getLogger(getClass());

	private Map<Crud, List<String>> crudTemplateFiles;
	private BuildManager buildManager;
	private StringBuffer generateLog;
	
	public GenerateCrudJob(String name, BuildManager buildManager, Map<Crud, List<String>> crudTemplateFiles) {
		super(name);
		this.buildManager = buildManager;
		this.crudTemplateFiles = crudTemplateFiles;
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {		
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider();
		
		//conta a quantidade de arquivos
		int totalTemplateFiles = 0;
		for (Crud crud : crudTemplateFiles.keySet()) {
			List<String> listKeyGenerate = crudTemplateFiles.get(crud);
			//count template files
			for (String keyGen : listKeyGenerate) { //loop in all keys of template
				List<TemplateFile> listTemplate = buildManager.getMapTemplateFiles().get(keyGen); //get Template Files, for this key
				if (listTemplate != null) {
					totalTemplateFiles += listTemplate.size();
				}
			}
		}
		
		//coloca tb o total de classe que tem q ser alteradas (annotations de valida��o)
		totalTemplateFiles = totalTemplateFiles + crudTemplateFiles.keySet().size(); //cada crud tem 1 classe, o keySet tem a lista dos cruds
		
		int fTotalTemplateFiles = totalTemplateFiles;
		
        try {
        	//infinite process destroy
        	DestroyJobThread destroyJobThread = new DestroyJobThread(monitor);
        	destroyJobThread.start();
        	
        	//starting new process
        	monitor.beginTask(PropertiesUtil.getMessage("dialog.progressBuild.description", "spider"), fTotalTemplateFiles);
           
            //my generate rules
			TemplateDataGroup dataGroup = new TemplateDataGroup();
            
            //database driver, dialect, connection string, etc
			Map databaseTypeConfig = new HashMap();
			databaseTypeConfig = (Map<String, Object>)XmlManager.loadTemplateCodeXml(configSpider.getTemplateFolder(), XML_TYPE.DATABASE_TYPE_CONFIG);
			dataGroup.setDatabaseTypeConfig(databaseTypeConfig);
			
			//path to all templates
			Map<String, String> pathAllTemplates = BuildManagerUtil.getPathAllTemplates();
			dataGroup.setPathAllTemplates(pathAllTemplates);
           
			generateLog = new StringBuffer();
			Map<String, Integer> countItensLog = new HashMap<String, Integer>();
			
			for (Crud crud : crudTemplateFiles.keySet()) {
				List<String> listKeyGenerate = crudTemplateFiles.get(crud);
				dataGroup.setCrud(crud); //VARIAVEL PARA IDENTIFICAR O CRUD QUE EST� SENDO GERADO
				
				//update pojo annotations
				BuildManagerUtil.replaceAnnotationsInMethods(crud);
				monitor.worked(1);
				
				for (String keyGen : listKeyGenerate) { //loop in all keys ("selected") - da um loop em todos os itens selecionados para gera��o
					List<TemplateFile> listTemplate = buildManager.getMapTemplateFiles().get(keyGen); //get Template Files, for this key - para cada key, procura os arquivo de template
					if (listTemplate != null) { //if exist files... - se existir arquivos para a key, inicia a gera��o desses arquivos
						for (TemplateFile templateFile : listTemplate) {
							//check cancel
				            if (monitor.isCanceled()) {
				            	return Status.CANCEL_STATUS;   
				            }
				            //check templateFile type
				            if (templateFile.getEntryDepends() != null && !templateFile.getEntryDepends().equals("")) {
				            	continue; //if this file will be a reference to another entry, continue in the next file.
				            	//entry reference already was include in listKeyGenerate.
				            }
				            
				            String fileName = templateFile.getPathGenerationFile();
				            if (fileName == null || fileName.equals("")) { //this template doesn't have a path to generate the file
				            	monitor.worked(1);
				            	continue;
				            }				            
				            if (fileName.length() > 15) {
				            	fileName = BuildManagerUtil.getFileNameFromPath(fileName);
				            }
							monitor.subTask(PropertiesUtil.getMessage("dialog.progressBuild.status", "spider") + " " + fileName);
							//generate
							String log = buildManager.generateFile(configSpider.getTemplateFolder(), templateFile, dataGroup, countItensLog);
							generateLog.append(log);

							monitor.worked(1);
						}
					}
				}
			}
			
			//realiza um clean no projeto para compilar todos os arquivos (que foram alterados e os novos)
			try {
				destroyJobThread.stopCheck(); //stop thread
				
				monitor.subTask(PropertiesUtil.getMessage("dialog.progressBuild.refreshing", "spider"));
				ConfigurationEditor.refreshWorkspace();
				ConfigurationEditor.activeProject.build(IncrementalProjectBuilder.CLEAN_BUILD, new SubProgressMonitor(monitor, 1));
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//increment count itens in log
			generateLog.append("\n");
			for (String type : countItensLog.keySet()) {
				generateLog.append(type).append(countItensLog.get(type)).append("\n");
			}
         } catch (IOException e) {
        	 logger.error(e);
         //} catch (JavaModelException e) {
         } catch (RuntimeException e) {
        	 logger.error(e);
		} finally {
            monitor.done();
         }
         return Status.OK_STATUS;
	}

	public String getGenerateLog() {
		return generateLog.toString();
	}

}
