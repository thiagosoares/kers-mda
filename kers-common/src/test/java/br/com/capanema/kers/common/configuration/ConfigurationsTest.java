package br.com.capanema.kers.common.configuration;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarFile;

import org.junit.Test;

public class ConfigurationsTest {

  @Test
  public void testGetRootPathProject() {
    //System.out.println(Constants.instance().getRootPathProject());
    
    //System.out.println(Constants.instance().getResourcesPathProject());
    
    //System.out.println(Constants.instance().getRootPathProjectTest());
    
    //System.out.println(Constants.instance().getResourcesPathProjectTest());
  }

//  @Test
  public void testeJar() throws IOException {
    JarFile jar = new JarFile("C:/workspace/calango/CalangoMda/calangoMDA-core/target/calangoMDA-core-1.0-SNAPSHOT.jar");
    
    FileInputStream fis = new FileInputStream("C:/workspace/calango/CalangoMda/calangoMDA-core/target/calangoMDA-core-1.0-SNAPSHOT.jar");
    BufferedInputStream buffInputStream = new BufferedInputStream(fis);
    //ZipUtil.unZip("E:\\Desenvolvimento\\TMP_TEST", buffInputStream);
  }
  
}
