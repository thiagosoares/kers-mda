package br.com.capanema.kers.common.configuration;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import br.com.capanema.kers.common.model.repository.Repository;

public class KersConfig {

  private static KersConfig instance;
  
  private List<String> repositories; 

  public static final String  GAMBI = "C:"+File.separator+"workspace"+File.separator+"calango"+File.separator+"kers"+File.separator+"calangoMDA-core"+File.separator+"";
  
  private KersConfig() {
    super();
    repositories = new LinkedList<String>();
  }
  
  public static KersConfig instance() {
    if(instance == null) {
      instance = new KersConfig();
    }
    return instance;
  }
  
  
  public String getEARPath() {
    return "";
  }
  
  /**
   * exemplo: C:\workspace\calango\CalangoMda\calangoMDA-core\src\main\java
   * @return
   */
  public String getRootPathProject() {
    //return Configurations.class.getResource(".").getPath().replace("br/pa/calangoMda/common/configuration/", "");
    //return new File(".").getAbsolutePath().replace(".", "src"+File.separator+"main"+File.separator+"java");
    return GAMBI+"src"+File.separator+"main"+File.separator+"java";
  }
  
  /**
   * exemplo: C:\workspace\calango\CalangoMda\calangoMDA-core\src\main\resources\
   * 
   * @return
   */
  public String getResourcesPathProject() {
    //return Configurations.class.getResource(".").getPath().replace("br/pa/calangoMda/common/configuration/", "");
    //return new File(".").getAbsolutePath().replace(".", "src"+File.separator+"main"+File.separator+"resources"+File.separator+"");
    return GAMBI+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"";
  }
  
  
  public String getRootPathProjectTest() {
    //return Configurations.class.getResource(".").getPath().replace("br/pa/calangoMda/common/configuration/", "");
    //return new File(".").getAbsolutePath().replace(".", "src"+File.separator+"test"+File.separator+"java");
    return GAMBI+"src"+File.separator+"test"+File.separator+"java";
  }
  
  /**
   * exemplo: C:\workspace\calango\CalangoMda\calangoMDA-core\src\main\resources\
   * 
   * @return
   */
  public String getResourcesPathProjectTest() {
    //return Configurations.class.getResource(".").getPath().replace("br/pa/calangoMda/common/configuration/", "");
    //return new File(".").getAbsolutePath().replace(".", "src"+File.separator+"test"+File.separator+"resources"+File.separator+"");
    return GAMBI + "src"+File.separator+"test"+File.separator+"resources"+File.separator+"";
  }
  
  
  //TODO
  public String getSOType() {
    return System.getenv(""); 
  }

  public List<String> getRepositories() {
    return repositories;
  }

  public void setRepositories(List<String> repositories) {
    this.repositories = repositories;
  }
  
  
}
