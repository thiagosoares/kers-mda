package br.com.capanema.kers.core.build.jobs;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.capanema.kers.common.model.project.ProjectConfig;
import br.com.capanema.kers.common.model.template.Template;
import br.com.capanema.kers.common.model.template.TemplateDataGroup;
import br.com.capanema.kers.common.model.template.TemplateFile;
import br.com.capanema.kers.core.build.BuildManager;
import br.com.capanema.kers.tempate.engine.xml.magager.XmlManager;
import br.com.capanema.kers.tempate.engine.xml.magager.XmlType;
import br.com.capanema.kers.velocity.util.BuildManagerUtil;

//TODO Remover
@Deprecated
public class GenerateFileJob implements Generate {
	Logger logger = Logger.getLogger(getClass());

	private ProjectConfig config;
	private Template template; 
	
	private List<TemplateFile> listKeyGenerate;
	private BuildManager buildManager;
	//private StringBuffer generateLog;
	
	public GenerateFileJob(ProjectConfig configSpider, Template template, BuildManager buildManager, List<TemplateFile> listKeyGenerate) {
		super();
		this.config = configSpider;
		this.template = template;
		
		this.buildManager = buildManager;
		this.listKeyGenerate = listKeyGenerate;
	}

  public void run() {

    try {
      // my generate rules
      TemplateDataGroup dataGroup = new TemplateDataGroup();
      dataGroup.setConfig(this.config);

      // database driver, dialect, connection string, etc
      // Map databaseTypeConfig = new HashMap();
      // databaseTypeConfig = (Map<String, Object>)XmlManager.loadTemplateCodeXml("TODO configSpider.getTemplateFolder()",
      // XmlType.DATABASE_TYPE_CONFIG);
      // dataGroup.setDatabaseTypeConfig(databaseTypeConfig);

      // path to all templates
      // Map<String, String> pathAllTemplates = BuildManagerUtil.getPathAllTemplates();
      // dataGroup.setPathAllTemplates(pathAllTemplates);

      if (listKeyGenerate != null) { //se existir arquivos
                                     // para a key, inicia a geracao desses arquivos
        for (TemplateFile templateFile : listKeyGenerate) {
          if (templateFile.getEntryDepends() != null && !templateFile.getEntryDepends().equals("")) {
            continue; // if this file will be a reference to another entry,
                      // continue in the next file. entry reference already was include in listKeyGenerate.
          }

          String fileName = templateFile.getPathGenerationFile();
          if (fileName == null || fileName.equals("")) { // this template doesn't have a path to generate the file
            
            //fileName = templateFile.getPathTemplateFile().replace(".vm", "");
            //fileName = BuildManagerUtil.getFileNameFromPath(fileName);
            continue;
          }
          if (fileName.length() > 15) {
            //fileName = BuildManagerUtil.getFileNameFromPath(fileName);
          }
          // generate
         //buildManager.generateFile(templateFile, dataGroup);
          //generateLog.append(log);
          // monitor.worked(1);
        }
      }

      // increment count itens in log
      //generateLog.append("\n");
      //for (String type : countItensLog.keySet()) {
      //  generateLog.append(type).append(countItensLog.get(type)).append("\n");
      //}
    } finally {
      // monitor.done();
    }
  }

	public String getGenerateLog() {
		return "";
	}

}
