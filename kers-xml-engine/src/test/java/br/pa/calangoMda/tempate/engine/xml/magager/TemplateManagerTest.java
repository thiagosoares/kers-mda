package br.pa.calangoMda.tempate.engine.xml.magager;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.SystemUtils;
import org.junit.Test;

import br.com.capanema.kers.common.configuration.KersConfig;
import br.com.capanema.kers.common.model.template.Template;
import br.com.capanema.kers.common.model.template.TemplateFile;
import br.com.capanema.kers.common.model.template.TemplateFolder;
import br.com.capanema.kers.common.util.file.FileUtil;
import br.com.capanema.kers.tempate.engine.xml.magager.TemplateManager;

public class TemplateManagerTest {

  //@Test
  public void testReadTemplate() throws IOException {
   
    //FileUtil.copyTemplateDirectory(new File(Configurations.instance().getResourcesPathProject() + "templates/default"), new File("E:"+File.separator+"Desenvolvimento"+File.separator+"TMP_TEST"));
    
    if(SystemUtils.IS_OS_LINUX) {
      FileUtil.copyFile("C:\\workspace\\calango\\CalangoMda\\calangoMDA-core\\src\\main\\resources\\", "E:\\Desenvolvimento\\TMP_TEST\\mui");
    } else {
      FileUtil.copyFile("C:\\workspace\\calango\\CalangoMda\\calangoMDA-core\\src\\main\\resources\\", "E:\\Desenvolvimento\\TMP_TEST\\mui");
    }
      
  }

  @Test
  public void testEstructureDiscovery() throws Exception {

    //TemplateManager tm = new TemplateManager(KersConfig.instance().getResourcesPathProject() + "templates"+File.separator+"default");
    
    Template template = new Template();
    if(SystemUtils.IS_OS_LINUX) {
      template.setRealPath("/home/thiago/workspace/kers/kers/kers-templates/kers-maven-archetype");
      template.setRootFolder("template");
    } else {
      template.setRealPath("");
    }
    
    template = new TemplateManager().discoveryEstructure(template);
    
    System.out.println();
    
    System.out.println("Estrutura");
    for (TemplateFolder s : template.getTemplateFolderes()) {
      System.out.println(s.getPathTemplateFolder());
    }
    
    System.out.println();
    System.out.println("Copy");
    for (TemplateFile s : template.getFilesToCopy()) {
      System.out.println(s);
    }
    System.out.println();
    System.out.println("Tempates");
    for (TemplateFile s : template.getTemplateFiles()) {
      System.out.println(s);
    }


    //tm.createProjectEstructure(template, "E:"+File.separator+"Desenvolvimento"+File.separator+"TMP_TEST");
    
  }
  
  

}
