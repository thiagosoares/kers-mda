package br.com.capanema.kers.core.app;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Persistence;

import br.com.capanema.kers.common.configuration.KersConfig;
import br.com.capanema.kers.common.model.project.ProjectConfig;
import br.com.capanema.kers.common.model.repository.Repository;
import br.com.capanema.kers.common.model.template.Crud;
import br.com.capanema.kers.common.model.template.Template;
import br.com.capanema.kers.common.model.template.TemplateSumary;
import br.com.capanema.kers.core.build.BuildManager;
import br.com.capanema.kers.core.uc.UCLoader;
import br.com.capanema.kers.tempate.engine.xml.magager.TemplateRepositoryManager;
import br.com.capanema.kers.tempate.engine.xml.magager.XmlManager;
import br.com.capanema.kers.tempate.engine.xml.magager.XmlType;

public class Kers {
  
  private static Kers instance;
  private static KersConfig config;
  private static TemplateRepositoryManager repositoryManager;
  
  private Kers() {
    
  }
  
  private Kers(Map<KersConfigProp, Object> prop) {
    super();
    
    //Instanciar
    instance = new Kers();
    readConfig(prop);
    repositoryManager = TemplateRepositoryManager.instance();
  }

  /**
   * obtem os parametros de configuracao passados pelo usuario e
   * popula o KersConfig
   * @param prop
   */
  private void readConfig(Map<KersConfigProp, Object> prop) {
    
    config = KersConfig.instance();
    
    for (KersConfigProp key : prop.keySet()) {
      
      switch (key) {
      case TEMPLATE_REPOSITORY:
        config.getRepositories().add((String) prop.get(key));
        break;
        
      default:
        break;
      }
    }
    
  }
  
  
  public static Kers start(Map<KersConfigProp, Object> prop) throws Exception {
    if(instance != null) {
      throw new Exception("Já foi startado");
    }
    instance = new Kers(prop);
    
    instance.loadRepositories();
    
    return instance;
  }
  
  public static void stop() {
    
    //repositoryManager.close();  TODO metodo not implemented
    
    config = null;
    instance = null; 
  }


  /**
   * @see TemplateRepositoryManager#loadRepositories(List);
   */
  private void loadRepositories() {
    //  ler os repositorios de template (templateList.xml)
    repositoryManager.loadRepositories(config.getRepositories());
  }

  
  /*
   * Rotinas para build de projetos
   */
  
  public static KersConfig getConfig() {
    return config;
  }

  public static Kers getInstance() {
    return instance;
  }

  public void build(ProjectConfig projectConfig) throws Exception {
    
    BuildManager builder = new BuildManager(projectConfig);
    
    builder.buildEstructure();
    
    builder.buildMavenConfig();
    
    builder.buildProjectClasses();
    
    UCLoader loader = new UCLoader(projectConfig);
    
    if(projectConfig.getCruds() != null && !projectConfig.getCruds().isEmpty()) {
      builder.buildCRUDs(projectConfig.getCruds());
    } else {
      List<Crud> cruds = loader.readDefaultCrud();
      if(!cruds.isEmpty()) {
        builder.buildCRUDs(cruds);
      }
    }
  }
  
  public void buildEstructure(ProjectConfig projectConfig) throws Exception {
    new BuildManager(projectConfig).buildEstructure();
  }
  
  public void buildMavenConfig(ProjectConfig projectConfig) throws Exception {
    new BuildManager(projectConfig).buildMavenConfig();
  }
  
  public void buildLayout(ProjectConfig projectConfig) throws Exception {
    new BuildManager(projectConfig).buildLayout();
  }
  
  public void buildTech(ProjectConfig projectConfig) throws Exception {
    new BuildManager(projectConfig).buildTechConfig();
  }
  
  public void buildPackage(ProjectConfig projectConfig) throws Exception {
    new BuildManager(projectConfig).buildPackage();
  }
  
  public void buildCRUDs(ProjectConfig projectConfig) throws Exception {
    new BuildManager(projectConfig).buildCRUDs(projectConfig.getCruds());
  }
  
  public void buildMapping(ProjectConfig projectConfig) throws Exception {
    new BuildManager(projectConfig).buildMapping();
  }

  
  
  
  //Demais metodos disponiveis
  
  public enum KersConfigProp {
    
    TEMPLATE_REPOSITORY
    
  }
  
}