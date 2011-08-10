package br.com.capanema.kers.core.facade;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.common.model.projectea.TemplateConfig;
import br.com.capanema.kers.common.model.template.DomainSourceType;
import br.com.capanema.kers.core.dao.XmlDiscovery;
import br.com.capanema.kers.core.engine.EngineCalango;
import br.com.capanema.kers.core.engine.EngineMuiraquitaFull;
import br.com.capanema.kers.core.engine.EngineMuiraquitaLigth;
import br.com.capanema.kers.core.factory.projects.GeradorEstruturaProjeto;
import br.com.capanema.kers.core.factory.resources.GeradorResouces;
import br.com.capanema.kers.core.util.configuration.ConfigurationProject;
import br.com.capanema.kers.uc.util.reflection.BeanDiscovery;

public class CalangoFacade implements ICalangoFacade {

  private Logger log = Logger.getLogger(CalangoFacade.class);

  public CalangoFacade() {
    super();
  }

  public void buildAllProject(TemplateConfig parametros) throws Exception {

    /**
     * Gerar fontes e view do projeto
     */
    System.out.println("Iniciando geracao de codigo fonte......");
    switch (parametros.getArchetype()) {
    case MAVEN_JAVA_WEB_SEAM_2_0:
      switch (parametros.getTipoArquitetura()) {
      case CALANGO:
        new EngineCalango(parametros).execute();
        break;
      case MUIRAQUITA_FULL:
        new EngineMuiraquitaFull(parametros).execute();
        break;

      case MUIRAQUITA_LIGTH:
        new EngineMuiraquitaLigth(parametros).execute();
        break;
      }
      break;
    case MAVEN_JAVA_WEB_SEAM_2_1:
      switch (parametros.getTipoArquitetura()) {
      case CALANGO:
        new EngineCalango(parametros).execute();
        break;
      case MUIRAQUITA_FULL:
        new EngineCalango(parametros).execute();
        break;

      case MUIRAQUITA_LIGTH:
        new EngineCalango(parametros).execute();
        break;
      }
      break;
    case MAVEN_JAVA_WEB_WEBBEAMS_1_0:
      switch (parametros.getTipoArquitetura()) {
      case CALANGO:
        new EngineCalango(parametros).execute();
        break;
      case MUIRAQUITA_FULL:
        new EngineCalango(parametros).execute();
        break;

      case MUIRAQUITA_LIGTH:
        new EngineCalango(parametros).execute();
        break;
      }
      break;
    case MAVEN_JAVA_SWING:
      // new EngineCalango().execute(projeto);
      break;
    case MAVEN_JAVA_JAR:
      // EngineJarProject.gerar(projeto);
      break;
    case PHP:
      // EnginePHP.gerar(projeto);
      break;
    }
    System.out.println("\n\n\n ");
    System.out.println("********** Sucefull build completed ********");
    System.out.println("********** Te vira com o resto!!!!!!!! ******** :) :) :) :)  ");
  }
  
  
  /*public void buildAllProject(ParametrosProjeto parametros) throws ConfigurationException, IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

    System.out.println("Inicio da criacao do projeto.");

    Projeto projeto = ConfigurationProject.configurarProjeto(parametros);

    System.out.println("\tConfigura��o: ok ");

    *//**
     * Gerar projeto
     * 
     *//*
    System.out.print("\tGerando estrutura de diretorios para archetype: " + projeto.getArchetype().getDescricao());
    GeradorEstruturaProjeto.gerarEstruturaProjeto(projeto);

    System.out.print("\tGerando arquivos de configuracao do projeto para o archetype: " + projeto.getArchetype().getDescricao());
    GeradorResouces.gerarArquivosDeConfiguracao(projeto);

    *//**
     * Fazer leitura das classes do projeto
     *//*
    System.out.println("");
    System.out.println("\tInicio da descoberta das classes:progess...");
    // ler apartir do Xmi
    if (parametros.getTipoFonteProjeto().equals(TipoFonteProjeto.XMI)) {

      XmlDiscovery discovery = new XmlDiscovery(parametros.getLocalSourceXmi(), parametros.getDiagramaIteracao());
      System.out.println("\t>> Discovery.XmlDiscovery: ok");

      // Carregar as classes
      projeto.setEntidades(discovery.readClasses());

      System.out.println("\t" + projeto.getEntidades().size() + " Entidades Recuperadas do XMI");

    } else { // Ler apartir de pojos das entidades no classPath

      BeanDiscovery beanDiscovery = new BeanDiscovery(parametros.getEntidadesStr());

      System.out.println("\t>> Discovery.BeanDiscovery: ok");

      // Carregar as classes
      projeto.setEntidades(beanDiscovery.readClasses());

      System.out.println("\t" + projeto.getEntidades().size() + " Entidades Recuperadas do Jar");
    }

    System.out.println("Leitura das classes realizada com sucesso.");

    *//**
     * Gerar fontes e view do projeto
     *//*
    System.out.println("Iniciando geracao de codigo fonte......");
    switch (parametros.getArchetype()) {
    case MAVEN_JAVA_WEB_SEAM_2_0:
      switch (parametros.getTipoArquitetura()) {
      case CALANGO:
        EngineCalango.gerar(projeto);
        break;
      case MUIRAQUITA_FULL:
        EngineMuiraquitaFull.gerar(projeto);
        break;

      case MUIRAQUITA_LIGTH:
        EngineMuiraquitaLigth.gerar(projeto);
        break;
      }
      break;
    case MAVEN_JAVA_WEB_SEAM_2_1:
      switch (parametros.getTipoArquitetura()) {
      case CALANGO:
        EngineCalango.gerar(projeto);
        break;
      case MUIRAQUITA_FULL:
        EngineCalango.gerar(projeto);
        break;

      case MUIRAQUITA_LIGTH:
        EngineCalango.gerar(projeto);
        break;
      }
      break;
    case MAVEN_JAVA_WEB_WEBBEAMS_1_0:
      switch (parametros.getTipoArquitetura()) {
      case CALANGO:
        EngineCalango.gerar(projeto);
        break;
      case MUIRAQUITA_FULL:
        EngineCalango.gerar(projeto);
        break;

      case MUIRAQUITA_LIGTH:
        EngineCalango.gerar(projeto);
        break;
      }
      break;
    case MAVEN_JAVA_SWING:
      // EngineCalango.gerar(projeto);
      break;
    case MAVEN_JAVA_JAR:
      // EngineJarProject.gerar(projeto);
      break;
    case PHP:
      // EnginePHP.gerar(projeto);
      break;
    }
    System.out.println("\n\n\n ");
    System.out.println("********** Gera��o completada com sucesso ********");
    System.out.println("********** Te vira com o resto!!!!!!!! ********  :)");
  }*/

}
