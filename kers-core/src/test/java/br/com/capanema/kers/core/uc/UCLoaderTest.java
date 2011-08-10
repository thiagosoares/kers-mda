package br.com.capanema.kers.core.uc;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;

import br.com.capanema.kers.common.model.project.ProjectConfig;
import br.com.capanema.kers.common.model.template.Crud;
import br.com.capanema.kers.common.model.template.DomainSourceType;

public class UCLoaderTest {

  UCLoader loader;
  ProjectConfig parametros;
  
  @Before
  public void start() {
    
    parametros = new ProjectConfig();
    
    parametros.setDomainSourceType(DomainSourceType.FROM_XMI);
    
    parametros.setDomainPath(this.getClass().getResource("/") + "/xml/sispatweb.xml");
    
    try {
      loader = new UCLoader(parametros);
    } catch (ConfigurationException e) {
      fail(e.getMessage());
    } catch (IOException e) {
      fail(e.getMessage());
    }
    
  }
  
  @Test
  public void testReadDomain() {
    
    try {
      loader.readDomain();
    } catch (ConfigurationException e) {
      e.printStackTrace();
      fail("Not yet implemented");
    }
    
  }

  @Test
  public void testReadDefaultCrud() {
    try {
      List<Crud> cruds = loader.readDefaultCrud();
      
      for (Crud crud : cruds) {
        System.out.println(crud.getName());
      }
      
      
    } catch (ConfigurationException e) {
      e.printStackTrace();
      fail("Not yet implemented");
    }
 
  }

  @Test
  public void testOps() {
    fail("Not yet implemented");
  }

}
