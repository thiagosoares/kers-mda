package br.com.capanema.kers.core.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.junit.Test;

import br.com.capanema.kers.common.configuration.Constants;
import br.com.capanema.kers.common.configuration.KersConfig;
import br.com.capanema.kers.core.util.file.FileManager;

public class FileManagerTest {

  
  @Test
  public void testfind() throws IOException {
    
    File f2 = new File("teste.txt");
    System.out.println(f2.getAbsolutePath());
    System.out.println(f2.getCanonicalPath());
    
    File f = new File("./common_templates/project_conf_tpl/pom_projeto.xml");
    System.out.println(f.exists());
       
    
    File f3 = new File(KersConfig.instance().getRootPathProject() + "/resources/common_templates/project_conf_tpl/pom_projeto.xml");
    System.out.println(f3.exists());
    
  }
  
  //@Test
  public void testCopiarArquivo2() {
     try {
      FileManager.lerArquivo("E:\\Desenvolvimento\\WorkSpaces\\projeto_eduarda\\calangoMDA\\calangoMDA-core\\templates\\seam_conf_tpl\\web_resources_seam20\\WEB-INF\\components.xml");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

   public static void copy()  {  
        
         File fl = new File("E:\\A");  
           
         try  
         {  
            if(!fl.exists())  
            {  
                 
               System.out.println(" ");  
               System.out.println("Confirma a criacao do diretorio ?");  
                     System.out.println("pressione ENTER");  
                     System.in.read(new byte[50]);  
                     fl.mkdirs();  
                     System.out.println("Diretorio Criado em "+fl.getPath());  
                }  
                       
                fl.renameTo(new File("E:\\B"));//aki vc renomiea para outro diretorio  
                fl.delete();  
                fl = new File("E:\\B");  
                fl.createNewFile();  
                System.out.println(" ");  
                System.out.println("\nNovo Nome :\t"+"  "+fl.getPath());  
                System.out.println("Nome do arquivo :"+fl.getName());  
                System.out.println("Propriedades de :\t"+fl.getPath());  
                System.out.println("Leitura permitida :\t"+fl.canRead());  
                System.out.println("Escrita permitida :\t"+fl.canWrite());  
                System.out.println("Diretorio ? :\t\t"+fl.isDirectory());     
                System.out.println("Arquivo ? :\t\t"+fl.isFile());  
             }  
         catch(IOException ioex)  
         {  
           ioex.printStackTrace();
         System.out.println("Erro ocorrido !");  
         }  
      } 
  
  
  /**
   * Copia arquivos de um local para o outro
   * 
   * @param origem - Arquivo de origem
   * 
   * @param destino - Arquivo de destino
   * 
   * @param overwrite - Confirma��o para sobrescrever os arquivos
   * 
   * @throws IOException
   */
  public static void copy(File origem, File destino, boolean overwrite) throws IOException {
    if (destino.exists() && !overwrite) {
      System.err.println(destino.getName() + " j� existe, ignorando...");
      return;
    }
    FileInputStream fisOrigem = new FileInputStream(origem);
    FileOutputStream fisDestino = new FileOutputStream(destino);
    FileChannel fcOrigem = fisOrigem.getChannel();
    FileChannel fcDestino = fisDestino.getChannel();
    fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);
    fisOrigem.close();
    fisDestino.close();
  }

  /**
   * Copia todos os arquivos de dentro de uma pasta para outra
   * @param origem - Diret�rio onde est�o os arquivos a serem copiados
   * @param destino - Diret�rio onde os arquivos ser�o copiados
   * @param overwrite - Confirma��o para sobrescrever os arquivos
   * @throws IOException
   */
  public static void copyAll(File origem, File destino, boolean overwrite) throws IOException {
    if (!destino.exists())
      destino.mkdir();
    if (!origem.isDirectory())
      throw new UnsupportedOperationException("Origem deve ser um diret�rio");
    if (!destino.isDirectory())
      throw new UnsupportedOperationException("Destino deve ser um diret�rio");
    File[] files = origem.listFiles();
    for (File file : files) {
      if (file.isDirectory())
        copyAll(file, new File(destino + "\\" + file.getName()), overwrite);
      else {
        System.out.println("Copiando arquivo: " + file.getName());
        copy(file, new File(destino + "\\" + file.getName()), overwrite);
      }
    }
  }

}
