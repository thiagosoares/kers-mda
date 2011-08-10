package br.com.capanema.kers.tempate.engine.xml.magager;


import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import br.com.capanema.kers.common.configuration.KersConfig;
import br.com.capanema.kers.common.model.repository.Repository;
import br.com.capanema.kers.common.model.template.Template;
import br.com.capanema.kers.common.model.template.TemplateFile;
import br.com.capanema.kers.common.model.template.TemplateFolder;
import br.com.capanema.kers.common.model.template.TemplateSumary;
import br.com.capanema.kers.common.util.string.FiltroString;
import br.com.capanema.kers.common.util.string.StringUtil;

/**
 * 
 * Mantera em memoria informacoes sobre os templates disponiveis no repositorio
 * em uma lista de templateSumary 
 * 
 * Xml: templateList.xml
 * 
 * <list>
 * <templateSumary>
 *   <name>SPIDER JEE JSF</name>
 *   <folder>jee-jsf</folder>
 *  <description>This template was created by Gleyve Barros. It generates JEE code using JSF and Facelets. Create your templates too!</description>
 *  <techGroup>JEE</techGroup>
 *   <compatibilityDate>20091025111528</compatibilityDate>
 *   <url>http://www.j2eespider.org</url>
 *   <internalOnly>false</internalOnly>
 * </templateSumary>
 * <templateSumary>
 *   <name>SPIDER JEE Dojo</name>
 *   <folder>jee-dojo</folder>
 *   <description>It generates JEE code using Servlets and Dojo. Create your templates too!</description>
 *  <techGroup>JEE</techGroup>
 *   <compatibilityDate>20091113211528</compatibilityDate>
 *   <url>http://www.j2eespider.org</url>
 *   <internalOnly>false</internalOnly>
 * </templateSumary>  
 * </list>
 */
public class TemplateRepositoryManager { 

  private TemplateManager manager;
  private static KersConfig config;
  private static TemplateRepositoryManager instance;
  

  private static Pattern patternToCoringas; //TODO Traduzir  

  /* Lista de coringas */
  private static Map<String, TemplateFile> pathsCoringa;
     
	/** All repositories available for user **/
	private static Map<String, Repository> repositories;
	
	/** All templates available for this user */
  private static Map<String, Template> templatesCache;
	private static Map<String, TemplateSumary> templatesProxies;
	
	
	 static {
	   patternToCoringas = Pattern.compile("(\\*)");
	 }
	
	private TemplateRepositoryManager() {
		super();
		
		manager = new TemplateManager();
		
		this.templatesCache = new LinkedHashMap<String, Template>();
		this.templatesProxies = new LinkedHashMap<String, TemplateSumary>();
		this.repositories = new LinkedHashMap<String, Repository>();
		
		this.pathsCoringa = new LinkedHashMap<String, TemplateFile>();
	}

	public static TemplateRepositoryManager instance() {
	  if(instance == null) {
	    instance = new TemplateRepositoryManager();
	  }
	  return instance;
	}

	/* Repositories */
	public Repository getRepository(String name) throws Exception {
    if(repositories.containsKey(name)) {
      return repositories.get(name);
    } else {
      throw new Exception("Repository nao localizado");
    }
  }
	
	
	/*
	 * TemplateSumary
	 */
	
	/**
	 * Return all templates (internal + external)
	 * @return
	 */
	//USADO EM getListTemplateInfo
 	public Map<String, TemplateSumary> getTemplateSumaries() {
		return templatesProxies;
	}
 	
 	

 	/**
 	 * Realiza a leitura do arquivo repository.xml para obter a relacao de templates
 	 * disponíveis;
 	 * 
 	 * @param repoPath
 	 */
  public void loadRepositories(List<String> repoPath) {
 	 
 	  for (String repo : repoPath) {
     
 	   Repository repository = (Repository) XmlManager.loadXml(repo, XmlType.REPOSITORY);
     repository.setPath(repo);
     
     for (TemplateSumary templateSumary : repository.getSumaries()) {
       templateSumary.setRealPath(repo + File.separator + templateSumary.getFolder());
       templateSumary.setRepositoryName(repository.getName());
       templatesProxies.put(templateSumary.getName(), templateSumary);
    }
     
     repositories.put(repository.getName(), repository);
     
   }
 	  
 	}
	
	public TemplateSumary getTemplateSumary(String name) throws Exception {
		
		if (name != null) {

		  if(templatesProxies.containsKey(name)) {
		    return templatesProxies.get(name);
		  } else {
		    throw new Exception("O template n�o faz parte do repositorio"); 
		  }
		    
			/*for (TemplateSumary templateSummary : templatesProxies) {
				if (!templateSummary.isInternalOnly()) { //exclude internal templates in this combo
					if (!templateSummary.getName().equals(name)) {
						return templateSummary;
					}
				}
			}	*/
		} else {
		  throw new Exception("Informe o nome do template");
		}
	}	

	
	/*
	 * Templates
	 */
	
  public Template getTemplate(String templateName) throws Exception {
    if(templatesCache.containsKey(templateName)) {
      return templatesCache.get(templateName);
    } else {
      return loadTemplate(templateName);
    }
  }
  
  /**
   * Fará o carregamento do template para sua lista
   * @throws Exception 
   */
  public synchronized Template loadTemplate(String templateName) throws Exception {
    
    TemplateSumary info = getTemplateSumary(templateName);
    
    //Leitura das informacoes do xml : template.xml
    Template template = (Template) XmlManager.loadXml(info.getRealPath(), XmlType.TEMPLATE);
    template.setRealPath(info.getRealPath());
    template.setRepositoryName(info.getRepositoryName());
    
    //carrega a estrutura de pastas e arquivos com base no template real
    template = manager.discoveryEstructure(template);
    
    
    Map<String, List<TemplateFile>> mapTemplateFiles = (Map<String, List<TemplateFile>>)XmlManager.loadXml(info.getRealPath(), XmlType.TEMPLATE_FILES);
    for (String key : mapTemplateFiles.keySet()) {
      for (TemplateFile templateFile : mapTemplateFiles.get(key)) {
        
        if(StringUtil.isEmpty(templateFile.getPathGenerationFile())) {
          templateFile.setPathGenerationFile(FiltroString.removeTemplateFileExtentions(templateFile.getPathTemplateFile()));
        }
        
        templateFile.setPathGenerationFile(StringUtil.formatPathTemplate(template, templateFile.getPathGenerationFile()));
        
        System.out.println(templateFile);
        
      }
    }
    
    /*  Seria utilizado para uso de coringas
    //Converte os path para o formado adequado ao SO local
    for (String key : mapTemplateFiles.keySet()) {
      for (TemplateFile templateFile : mapTemplateFiles.get(key)) {
       
        templateFile.setPathTemplateFile(StringUtil.toValidLocalPath(templateFile.getPathTemplateFile()));
        templateFile.setPathGenerationFile(StringUtil.toValidLocalPath(templateFile.getPathGenerationFile()));
        
      }
    }
    
    //Procura por coringas e preenche os pathGenerationFile nulos
    for (String key : mapTemplateFiles.keySet()) {
      for (TemplateFile templateFile : mapTemplateFiles.get(key)) {
        if (patternToCoringas.matcher(templateFile.getPathTemplateFile()).find()) {
          //add to coringas
          pathsCoringa.put(templateFile.getPathTemplateFile().replace("*", ""), templateFile);
        } else  //Se o pathGenerationFile não for informado, será setado automaticamento
          if(templateFile.getPathGenerationFile() == null || templateFile.getPathGenerationFile().equals("")) {
            templateFile.setPathGenerationFile(templateFile.getPathTemplateFile().replace(".vm", ""));
          }
      }
    }
    
    //Para cada coringa, procura por suas ocorrencias dentros de todos os paths declarados do templateFiles.xml
    for (TemplateFile coringa : pathsCoringa.values()) {
      
      for (String key : mapTemplateFiles.keySet()) {

        List<TemplateFile> templates = mapTemplateFiles.get(key);
        
        if(templates.contains(coringa)) {
          templates.remove(coringa);
        }
        for (TemplateFile templateFile : templates) {
          //Se o pathGenerationFile não for informado, será setado automaticamento
          if(templateFile.getPathTemplateFile().contains(coringa.getPathTemplateFile().replace("*",""))) {
            templateFile.setPathGenerationFile(templateFile.getPathGenerationFile().replace(coringa.getPathTemplateFile().replace("*",""), coringa.getPathGenerationFile()));
          }
        }
        mapTemplateFiles.put(key, templates);
      }
    }
    

    //Para cada coringa, procura por suas ocorrencias dentros de todos os paths descobertos no template
    for (TemplateFile coringa : pathsCoringa.values()) {
      
      for(TemplateFolder tFolder : template.getTemplateFolderes()) {
        System.out.print(tFolder);
        if(tFolder.getPathGenerationFolder().contains(File.separator + coringa.getPathTemplateFile().replace("*",""))) {
          tFolder.setPathGenerationFolder(tFolder.getPathGenerationFolder().replace(coringa.getPathTemplateFile().replace("*",""), coringa.getPathGenerationFile()));
        }
        System.out.println(" >>> " + tFolder);
      }
      
      for(TemplateFile tFile : template.getFilesToCopy()) {
        System.out.print(tFile);
        if(tFile.getPathGenerationFile().contains(File.separator + coringa.getPathTemplateFile().replace("*",""))) {
          tFile.setPathGenerationFile(tFile.getPathGenerationFile().replace(coringa.getPathTemplateFile().replace("*",""), coringa.getPathGenerationFile()));
        }
        System.out.println(" >>> " + tFile);
      }
    }*/
    
    template.setMapTemplateFiles(mapTemplateFiles);
    
    //armazena o template na cache
    this.templatesCache.put(info.getName(), template);
    
    return template;
    
  }
    

  
  
    /*String templateFolder = TempateFactory.getConfigSpider().getTemplateFolder();
    String pathTemplateFile = BuildManagerUtil.getPathFolderTemplates()+File.separator+templateFolder+File.separator+XmlType.TEMPLATE_CONFIG.getFileName();
    
    //n�o tem template selecionado, pega o primeiro da lista
    if (templateFolder == null || !(new File(pathTemplateFile).exists())) {
      //get the first template (exclude internal templates)
      for (RepositoryTemplateInfo templateInfo : TempateFactory.getListTemplateInfo()) {
        if (templateInfo != null && !templateInfo.isInternalOnly()) {
          TempateFactory.getConfigSpider().setTemplateFolder(templateInfo.getFolder());
          break;
        }
      }
      
      templateFolder = TempateFactory.getConfigSpider().getTemplateFolder();
      pathTemplateFile = BuildManagerUtil.getPathFolderTemplates()+File.separator+templateFolder+File.separator+XmlType.TEMPLATE_CONFIG.getFileName();
    }*/
    
    /*currentTemplate = (Template)XmlManager.loadXml(pathTemplateFile, XML_TYPE.TEMPLATE);
    //Make this attribute optional
    if (currentTemplate.getLanguages() == null || currentTemplate.getLanguages().size() == 0) {
      LinkedList<String> listLanguageCode = new LinkedList<String>();
      listLanguageCode.add("en");
      listLanguageCode.add("pt");     
      currentTemplate.setLanguages(listLanguageCode);
    }
  }*/


	private void loadValidatorType() {
		/*if (BuildManagerUtil.getPathFolderTemplates() == null || BuildManagerUtil.getPathFolderTemplates().equals("")) {return;} //n�o tem templates, n�o carrega os validators
		
		String templateFolder = ConfigurationEditor.getConfigSpider().getTemplateFolder();
		String pathValidatorConfigFile = BuildManagerUtil.getPathFolderTemplates()+File.separator+templateFolder+File.separator+XML_TYPE.VALIDATOR_TYPE.getFileName();
		validatorTypes = (java.util.List<ValidatorType>)XmlManager.loadXml(pathValidatorConfigFile, XML_TYPE.VALIDATOR_TYPE);*/
	}	
	
	private void loadHtmlType() {
		/*if (BuildManagerUtil.getPathFolderTemplates() == null || BuildManagerUtil.getPathFolderTemplates().equals("")) {return;} //n�o tem templates, n�o carrega os validators
		
		String templateFolder = ConfigurationEditor.getConfigSpider().getTemplateFolder();
		String pathValidatorConfigFile = BuildManagerUtil.getPathFolderTemplates()+File.separator+templateFolder+File.separator+XML_TYPE.HTML_TYPE.getFileName();
		htmlTypes = (java.util.List<HtmlType>)XmlManager.loadXml(pathValidatorConfigFile, XML_TYPE.HTML_TYPE);*/
	}	

	private void loadFieldLocations() {
		/*if (BuildManagerUtil.getPathFolderTemplates() == null || BuildManagerUtil.getPathFolderTemplates().equals("")) {return;} //n�o tem templates, n�o carrega os validators
		
		String templateFolder = ConfigurationEditor.getConfigSpider().getTemplateFolder();
		String pathValidatorConfigFile = BuildManagerUtil.getPathFolderTemplates()+File.separator+templateFolder+File.separator+XML_TYPE.FIELD_LOCATION.getFileName();
		fieldLocations = (java.util.List<FieldLocation>)XmlManager.loadXml(pathValidatorConfigFile, XML_TYPE.FIELD_LOCATION);*/
	}		
	
	/* (non-Javadoc)
	 * Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}
	
	
	
		

	/**
	 * Return template path.
	 * by SpiderPlugin class
	 * @return
	 */
	//USADO EM BuildManagerUtil
	public String getTemplatePathPreference() {
		return "";//getPreferenceStore().getString(PREFERENCE_TEMPLATE_PATH);
	}
	

}