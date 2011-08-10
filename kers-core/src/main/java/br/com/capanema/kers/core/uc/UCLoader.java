package br.com.capanema.kers.core.uc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import br.com.capanema.kers.common.model.domain.Association;
import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.project.ProjectConfig;
import br.com.capanema.kers.common.model.projectea.Archetype;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.common.model.projectea.TemplateConfig;
import br.com.capanema.kers.common.model.template.Crud;
import br.com.capanema.kers.common.model.template.CrudClass;
import br.com.capanema.kers.common.model.template.CrudField;
import br.com.capanema.kers.common.model.template.CrudRelationship;
import br.com.capanema.kers.common.model.template.DomainSourceType;
import br.com.capanema.kers.core.dao.XmlDiscovery;
import br.com.capanema.kers.core.factory.projects.GeradorEstruturaProjeto;
import br.com.capanema.kers.core.factory.resources.GeradorResouces;
import br.com.capanema.kers.core.util.configuration.ConfigurationProject;
import br.com.capanema.kers.core.util.reflection.BeanDiscovery;

public class UCLoader {
  
  protected ProjectConfig parametros;
  private List<Entity> entidades; //Entidades lidas da fonte
  
  public UCLoader(ProjectConfig parametros) throws ConfigurationException, IOException {
    super();
    this.parametros = parametros;
    entidades = new ArrayList<Entity>();
  }
  
  public List<Entity> readDomain() throws ConfigurationException {
    
    /**
     * Fazer leitura das classes do projeto
     */
    System.out.println("\tInicio da descoberta das classes:progess...");
    // ler apartir do Xmi
    if (parametros.getDomainSourceType().equals(DomainSourceType.FROM_XMI)) {
      entidades = readDomainFromXmi();
    } else {
      entidades = readDomainFromJar();
    }
    
    System.out.println("Leitura das classes realizada com sucesso. Encontradas " + entidades.size() + " entidades");
    for (Entity e : entidades) {
      System.out.println(e);
    }
    return entidades;
  }

  private List<Entity> readDomainFromXmi() throws ConfigurationException {
    XmlDiscovery discovery = new XmlDiscovery(parametros.getDomainPath(), null); //TODO se houver o diagrama, passar no contrutor
    return discovery.readClasses();
  }
  
  private List<Entity> readDomainFromJar()  {
    BeanDiscovery discovery = new BeanDiscovery(parametros.getDomainScope());
    return discovery.readClasses();
  }
  
  public List<Crud> readDefaultCrud() throws ConfigurationException {
    
    List<Entity> entities = readDomain();
    
    List<Crud> cruds = new ArrayList<Crud>();
    for (Entity e : entities) {
      Crud c = new Crud();
      c.setCrudType("DEFAULT");
      c.setModuleName(e.getNomeEntidade());
      c.setName(e.getNomeEntidade());
      
      for (Association rel : e.getAssociacoes()) {
        //TODO
        //CrudRelationship crudrel = new CrudRelationship();
      }
      
      
      CrudClass crudClass = new CrudClass(e.getPackageClasse() + "." +e.getNomeEntidade());
      c.setCrudClass(crudClass);

      for (Attribute attr : e.getAtributos()) {
        CrudField field = new CrudField(attr.getNome(), "className", attr.getTipo(), null, 0);
        crudClass.getFields().add(field);
      }
      cruds.add(c);
    }
    
    return cruds;
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
