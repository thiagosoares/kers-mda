package br.com.capanema.kers.core.app;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.SystemUtils;
import org.junit.Test;

import schemacrawler.schemacrawler.Config;

import br.com.capanema.kers.common.model.project.ProjectConfig;
import br.com.capanema.kers.common.model.template.Crud;
import br.com.capanema.kers.common.model.template.CrudClass;
import br.com.capanema.kers.common.model.template.CrudField;
import br.com.capanema.kers.core.app.Kers;
import br.com.capanema.kers.core.app.Kers.KersConfigProp;

public class KersTest {

  
  //@Test
  public void testStart() throws Exception {
    Kers.start(getProps());
    Kers.stop();
  }
  
  //@Test
  public void testBuildMuiraquitaJEE() throws Exception {
       
    Kers k = Kers.start(getProps());
    
    ProjectConfig config = getProjectConfig("Muiraquita JEE"); 
    
    config.getCruds().add(getCrudRamal());
    //config.getCruds().add(getCrudSetor());

    k.build(config);
    
    k.stop();
  }
  
  @Test
  public void testBuildMavenArchetypeStyle() throws Exception {
       
    Kers k = Kers.start(getProps());
    
    ProjectConfig config = getProjectConfig("kers-maven-archetype");
    
    config.getCruds().add(getCrudRamal());
    //config.getCruds().add(getCrudSetor());

    k.build(config);
    
    k.stop();
  }
  
  //@Test
  public void testBuildSeparated() throws Exception {
       
    Kers k = Kers.start(getProps());
    
    ProjectConfig config = getProjectConfig("Muiraquita JEE"); 
    
    k.buildEstructure(config);
    
    //k.buildMavenConfig(config);
    
    //config.getCruds().add(getCrudRamal());
    
    //k.buildCRUDs(config);
    
    k.stop();
  }
  
  
  
  //@Test
  public void testBuildCRUDs() {
	  fail("Not yet implemented");
  }
  
  //@Test
  public void testBuildProjectEstructure() throws Exception {
    Kers k = Kers.start(getProps());
    
    k.buildEstructure(getProjectConfig("Muiraquita JEE"));
  }


  
  //@Test
  public void testBuildLayout() throws IOException {

    File f = new File(new File("").getCanonicalPath().replace("/kers-core", "") + "/kers-templates");
    
    System.out.println(f.getCanonicalPath());
    
    for(String ff : f.list()) {
      System.out.println(ff);
    }
    
    
  }

  //@Test
  public void testBuildTech() {
    fail("Not yet implemented");
  }

  //@Test
  public void testBuildPackage() {
    fail("Not yet implemented");
  }


  //@Test
  public void testBuildMapping() {
    fail("Not yet implemented");
  }

  
  
  
  /*
   * Util
   */
  
  private String getTemplateRepositoryFolder() throws IOException {
    return new File(new File("").getCanonicalPath().replace(File.separator + "kers-core", "") + File.separator +  "kers-templates").getCanonicalPath();
  }
  
  private Map<KersConfigProp, Object> getProps() throws IOException {
    Map<KersConfigProp, Object> prop = new LinkedHashMap<KersConfigProp, Object>();
    
    prop.put(KersConfigProp.TEMPLATE_REPOSITORY, getTemplateRepositoryFolder());
     
    /*
    if(SystemUtils.IS_OS_LINUX) {
      //prop.put(KersConfigProp.TEMPLATE_REPOSITORY, "/home/thiago/workspace/kers/kers/kers-templates");
      prop.put(KersConfigProp.TEMPLATE_REPOSITORY, "/home/thiago/workspace/kers/kers/kers-templates");
    } else {
      prop.put(KersConfigProp.TEMPLATE_REPOSITORY, "C:\\workspace\\kers\\kers\\kers-templates");
    }*/
    return prop;
  }
  
  private ProjectConfig getProjectConfig(String archetypeName) {
    ProjectConfig conf = new ProjectConfig();
    
    //Maven
    conf.setGroupId("br.gov.pa");
    conf.setArtifactId("teste");
    conf.setVersion("1.0");
    
    //Descricao
    conf.setName("teste");
    conf.setDescription("Sistema de testes");
    conf.setMainPackage("br.gov.pa.test");
    conf.setLanguage("pt");
    //conf.setModulesName()
    
    conf.setDbUsername("root");
    conf.setDbPassword("******");
    conf.setDbName("teste_db");
    conf.setDbSchema("teste");
    
    conf.setTempateName(archetypeName);
    
   
    if(SystemUtils.IS_OS_LINUX) {
      conf.setSourceDestFolder("/home/thiago/TMP");
    } else {
      conf.setSourceDestFolder("E:\\Desenvolvimento\\TMP_TEST");
    }
    
    return conf;
  }
  
  private Crud getCrudRamal() {
    
    Crud crud = new Crud();
    
    crud.setName("Ramal");
    crud.setCrudType("crud.tabular");
    crud.setModuleName("m1");
    
    CrudClass crudClass = new CrudClass("br.gov.pa.test.entity.Ramal");
    crudClass.getFields().add(new CrudField("id", "br.gov.pa.test.entity.Ramal", "Long", 'i', 0));
    crudClass.getFields().add(new CrudField("nome", "br.gov.pa.test.entity.Ramal", "String", 'i', 1));
    crudClass.getFields().add(new CrudField("endereco", "br.gov.pa.test.entity.Ramal", "String", 'i', 2));
    crud.setCrudClass(crudClass);
    return crud;
    
  }
  
  private Crud getCrudSetor() {
    
    Crud crud = new Crud();
    
    crud.setName("Setor");
    crud.setCrudType("crud.mestre.detalhe");
    crud.setModuleName("m1");
    
    CrudClass crudClass = new CrudClass("br.gov.pa.test.entity.Setor");
    crudClass.getFields().add(new CrudField("id", "br.gov.pa.test.entity.Setor", "Long", 'i', 0));
    crudClass.getFields().add(new CrudField("nome", "br.gov.pa.test.entity.Setor", "String", 'i', 1));
    crudClass.getFields().add(new CrudField("endereco", "br.gov.pa.test.entity.Setor", "String", 'i', 2));
    crud.setCrudClass(crudClass);
    return crud;
    
  }
  
  
}
