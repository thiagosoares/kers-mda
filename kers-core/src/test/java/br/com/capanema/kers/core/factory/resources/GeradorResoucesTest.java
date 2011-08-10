package br.com.capanema.kers.core.factory.resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorResoucesTest {

  @Test
  public void testcopyFiles() throws IOException {
  }
  
  //@Test
  public void testGerarArquivosDeConfiguracao() throws IOException {
    
    Properties prop = System.getProperties();
    String pathCompleto = prop.getProperty("java.class.path", null);
    int indice = pathCompleto.indexOf("\\calangoMDA");
    //GeradorResouces.copyFiles(pathCompleto.substring(0, indice + 11));
  }
 
 // @Test
  public void testPAth() throws IOException {
    Properties prop = System.getProperties();
    String pathCompleto = prop.getProperty("java.class.path", null);
    int indice = pathCompleto.indexOf("\\calangoMDA");
    System.out.println(indice + " : " + pathCompleto.substring(0, indice + 11));
    
    
    
  }
  @Test
  public void testLerEscrever() throws IOException {
    
    BufferedReader in = new BufferedReader(new FileReader("E:/Desenvolvimento/WorkSpaces/projeto_eduarda/calangoMDA/calangoMDA-core/templates/project_conf_tpl/pom_ear.xml"));
    String str;
    String str2 = "";
    while ((str = in.readLine()) != null) {
        str2 += "\n" + str;
    }
    in.close();
    
    str2.replace("__projeto__", "teste");
    
    BufferedWriter out = new BufferedWriter(new FileWriter("E:/pom.xml"));
    out.write(str2);
    out.close();

    
    
  }
  
}
