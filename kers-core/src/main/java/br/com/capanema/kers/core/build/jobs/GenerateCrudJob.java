package br.com.capanema.kers.core.build.jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.capanema.kers.common.model.project.ProjectConfig;
import br.com.capanema.kers.common.model.template.Crud;
import br.com.capanema.kers.common.model.template.Template;
import br.com.capanema.kers.common.model.template.TemplateDataGroup;
import br.com.capanema.kers.common.model.template.TemplateFile;
import br.com.capanema.kers.core.build.BuildManager;
import br.com.capanema.kers.velocity.util.BuildManagerUtil;

//TODO Remover
@Deprecated
public class GenerateCrudJob implements Generate {
  Logger logger = Logger.getLogger(getClass());

  private ProjectConfig configSpider;
  private Template template;

  private Map<Crud, List<TemplateFile>> crudTemplateFiles;
  private BuildManager buildManager;
  //private StringBuffer generateLog;

  public GenerateCrudJob(ProjectConfig configSpider, Template template, BuildManager buildManager, Map<Crud, List<TemplateFile>> crudTemplateFiles) {
    super();
    this.configSpider = configSpider;
    this.template = template;

    this.buildManager = buildManager;
    this.crudTemplateFiles = crudTemplateFiles;
  }

  public void run() {


    try {
      // my generate rules
      TemplateDataGroup dataGroup = new TemplateDataGroup();

      // database driver, dialect, connection string, etc
      // Map databaseTypeConfig = new HashMap();
      // databaseTypeConfig = (Map<String, Object>)XmlManager.loadTemplateCodeXml(template.getRealPath(),
      // XmlType.DATABASE_TYPE_CONFIG);
      //dataGroup.setDatabaseTypeConfig(databaseTypeConfig);
      
      //path to all templates
      //Map<String, String> pathAllTemplates = BuildManagerUtil.getPathAllTemplates();
      //dataGroup.setPathAllTemplates(pathAllTemplates);

      for (Crud crud : crudTemplateFiles.keySet()) {

        List<TemplateFile> listTemplate = crudTemplateFiles.get(crud);
        dataGroup.setCrud(crud); //VARIAVEL PARA IDENTIFICAR O CRUD QUE EST� SENDO GERADO
        
        //update pojo annotations
        BuildManagerUtil.replaceAnnotationsInMethods(crud);
        
          if (listTemplate != null) { //if exist files... - se existir arquivos para a key, inicia a gera��o desses arquivos
            for (TemplateFile templateFile : listTemplate) {
              //check cancel
                    
                    //check templateFile type
                    if (templateFile.getEntryDepends() != null && !templateFile.getEntryDepends().equals("")) {
                      continue; //if this file will be a reference to another entry, continue in the next file.
                      //entry reference already was include in listKeyGenerate.
                    }
                    
                    String fileName = templateFile.getPathGenerationFile();
                    if (fileName == null || fileName.equals("")) { //this template doesn't have a path to generate the file
                      continue;
                    }                   
                    if (fileName.length() > 15) {
                      fileName = BuildManagerUtil.getFileNameFromPath(fileName);
                    }
              //generate
              //buildManager.generateFile(templateFile, dataGroup);
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

  public String getGenerateLog() {
    return "";//generateLog.toString();
  }

}