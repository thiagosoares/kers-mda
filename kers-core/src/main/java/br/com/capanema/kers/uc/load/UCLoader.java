package br.com.capanema.kers.uc.load;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.project.ProjectConfig;
import br.com.capanema.kers.common.model.projectea.Archetype;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.common.model.projectea.TemplateConfig;
import br.com.capanema.kers.common.model.template.Crud;
import br.com.capanema.kers.common.model.template.CrudClass;
import br.com.capanema.kers.common.model.template.CrudField;
import br.com.capanema.kers.common.model.template.DomainSourceType;
import br.com.capanema.kers.core.dao.XmlDiscovery;
import br.com.capanema.kers.core.factory.projects.GeradorEstruturaProjeto;
import br.com.capanema.kers.core.factory.resources.GeradorResouces;
import br.com.capanema.kers.core.util.configuration.ConfigurationProject;
import br.com.capanema.kers.uc.util.reflection.BeanDiscovery;

public class UCLoader {
  
  protected EAProjectConfig projeto;
  
  protected ProjectConfig parametros;
  
  public UCLoader(ProjectConfig parametros) throws ConfigurationException, IOException {
    super();
    this.parametros = parametros;
  }
  
  public void readDomain() throws ConfigurationException {
    
    /**
     * Fazer leitura das classes do projeto
     */
    System.out.println("\tInicio da descoberta das classes:progess...");
    // ler apartir do Xmi
    if (parametros.getDomainSourceType().equals(DomainSourceType.FROM_XMI)) {
      readDomainFromXmi();
    } else {
      readDomainFromJar();
    }
    
    System.out.println("Leitura das classes realizada com sucesso.");
    
    for (Entity e : projeto.getEntidades()) {
      System.out.println(e);
    }
    
  }

  public List<Crud> readDefaultCrudFromXmi() throws ConfigurationException {
    
    List<Entity> entities = readDomainFromXmi();
    return null;
  }
  
  public List<Entity> readDomainFromXmi() throws ConfigurationException {
    XmlDiscovery discovery = new XmlDiscovery(parametros.getDomainPath(), "parametros.getDiagramaIteracao()");
    System.out.println(">> Discovery.XmlDiscovery: ok " + projeto.getEntidades().size() + " Entidades Recuperadas do XMI");
    return discovery.readClasses();
  }
  
  
  public List<Crud> readDefaultCrudFromJar() throws ConfigurationException {
    
    List<Entity> entities = readDomainFromJar();
    
    List<Crud> cruds = new ArrayList<Crud>();
    for (Entity e : entities) {
      Crud c = new Crud();
      c.setCrudType("DEFAULT");
      c.setModuleName(e.getNomeEntidade());
      c.setName(e.getNomeEntidade());
      
      CrudClass crudClass = new CrudClass(e.getPackageClasse() + "." +e.getNomeEntidade());
      for (Attribute attr : e.getAtributos()) {
        //CrudField field = new CrudField(name, className, dataType, defaultAccessKey, defaultOrder);
        
      }
      
      c.setCrudClass(crudClass);
      
      
      cruds.add(c);
    }
    
    return cruds;
  }
  
  public List<Entity> readDomainFromJar()  {
    BeanDiscovery discovery = new BeanDiscovery(parametros.getDomainScope());
    System.out.println("\t>> Discovery.BeanDiscovery: ok " + projeto.getEntidades().size() + " Entidades Recuperadas do Jar");
    return discovery.readClasses();
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
