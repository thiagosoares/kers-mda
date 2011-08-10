package br.com.capanema.kers.common.model.project;

import static org.junit.Assert.fail;

import org.junit.Test;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.common.model.projectea.TemplateConfig;

import com.thoughtworks.xstream.XStream;

public class ProjectConfigTest {

  @Test
  public void testProjectConfig() {
    fail("Not yet implemented");
  }
  
  @Test
  public void testExportToXml() {
  
    
    XStream sxt = new XStream();
    sxt.alias("project", EAProjectConfig.class);
    System.out.println(sxt.toXML(new EAProjectConfig(getParam())));
    
    
    
  }

  private TemplateConfig getParam() {
    // TODO Auto-generated method stub
    return null;
  }

}
