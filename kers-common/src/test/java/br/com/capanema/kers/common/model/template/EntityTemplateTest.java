package br.com.capanema.kers.common.model.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

import org.junit.Test;

import br.com.capanema.kers.common.configuration.Constants;
import br.com.capanema.kers.common.model.template.EntityTemplate;

public class EntityTemplateTest {

  @Test
  public void testEntityTemplateFromXml() {
    
    List<String> list = new ArrayList<String>();
    list.add("@table");
    list.add("@id");
    
    EntityTemplate t = new EntityTemplate();
    t.setClassDeclaration("public class ${classe} extends AbstractEntity ");
    t.setIdAnnotations(list);
    t.setClassAnnotations(list);
    
    
  }
  
  @Test
  public void testEntityTemplateByXml() throws IOException {
    //EntityTemplate entity = (EntityTemplate) XmlManager.loadXml(Configurations.instance().getResourcesPathProject()+"templates/default/muiraquita/ejb/entity.xml", XmlType.GENERIC);
    //System.out.println(entity);
    
  }

}
