package br.com.capanema.kers.core.engine;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.configuration.ConfigurationException;

import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.Archetype;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.common.model.projectea.TemplateConfig;
import br.com.capanema.kers.common.model.template.DomainSourceType;
import br.com.capanema.kers.core.dao.XmlDiscovery;
import br.com.capanema.kers.core.factory.projects.GeradorEstruturaProjeto;
import br.com.capanema.kers.core.factory.resources.GeradorResouces;
import br.com.capanema.kers.core.util.configuration.ConfigurationProject;
import br.com.capanema.kers.core.util.reflection.BeanDiscovery;

@Deprecated
public abstract class AbstractEngine {
  
  protected EAProjectConfig projeto;
  
  protected TemplateConfig parametros;
  
  public AbstractEngine(TemplateConfig parametros) throws ConfigurationException, IOException {
    super();
    this.parametros = parametros;
    
    inicitalize();
  }


  public abstract void execute() throws Exception;
  
  
  public void inicitalize() throws IOException, ConfigurationException {

    configureProject(parametros);
    
    buildProjectEstructure();
    
    buildProjectResources();
    
    readClassModel(parametros);
    
    
  }
  
  public void configureProject(TemplateConfig parametros) {

    System.out.println("Inicio da criacao do projeto.");
    System.out.println();

    System.out.println("Configure");
    projeto = ConfigurationProject.configurarProjeto(parametros);
  }
  
  public void buildProjectEstructure() throws IOException {

    System.out.print("\tGerando estrutura de diretorios para archetype: " + projeto.getArchetype().getDescricao());
    GeradorEstruturaProjeto.gerarEstruturaProjeto(projeto);

  }
  
  public void buildProjectResources() throws IOException {
    
    System.out.print("\tGerando arquivos de configuracao do projeto para o archetype: " + projeto.getArchetype().getDescricao());
    GeradorResouces.gerarArquivosDeConfiguracao(projeto);

  }    

  public void readClassModel(TemplateConfig parametros) throws ConfigurationException {
    
    /**
     * Fazer leitura das classes do projeto
     */
    System.out.println("");
    System.out.println("\tInicio da descoberta das classes:progess...");
    // ler apartir do Xmi
    if (parametros.getTipoFonteProjeto().equals(DomainSourceType.FROM_XMI)) {
      readClassModelFromXmi(parametros);
    } else {
      readClassModelFromClasModel(parametros);
    }
    
    System.out.println("Leitura das classes realizada com sucesso.");
    
    for (Entity e : projeto.getEntidades()) {
      System.out.println(e);
    }
    
  }
  
  
  public void readClassModelFromXmi(TemplateConfig parametros) throws ConfigurationException {
    XmlDiscovery discovery = new XmlDiscovery(parametros.getLocalSourceXmi(), parametros.getDiagramaIteracao());
    System.out.println(">> Discovery.XmlDiscovery: ok");

    // Carregar as classes
    projeto.setEntidades(discovery.readClasses());

    System.out.println("\t" + projeto.getEntidades().size() + " Entidades Recuperadas do XMI");
    
    
  }
  
  
  public void readClassModelFromClasModel(TemplateConfig parametros)  {
    BeanDiscovery beanDiscovery = null; //new BeanDiscovery(parametros.getEntidadesStr()); TODO Rever esse contrutor q foi alterado 

    System.out.println("\t>> Discovery.BeanDiscovery: ok");

    // Carregar as classes
    projeto.setEntidades(beanDiscovery.readClasses());

    System.out.println("\t" + projeto.getEntidades().size() + " Entidades Recuperadas do Jar"); 
  }
  
  
  protected void ops(Throwable e) throws Exception { 
    
    if(e instanceof IllegalAccessException) {
      throw new Exception(e);
    } else {
      throw new Exception(e);
    }
    
    ///.....
    
  }
  
  
}
