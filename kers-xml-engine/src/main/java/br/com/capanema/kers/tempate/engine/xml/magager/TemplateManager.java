package br.com.capanema.kers.tempate.engine.xml.magager;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.capanema.kers.common.configuration.Constants;
import br.com.capanema.kers.common.model.template.Template;
import br.com.capanema.kers.common.model.template.TemplateFile;
import br.com.capanema.kers.common.model.template.TemplateFolder;
import br.com.capanema.kers.common.util.file.FileUtil;
import br.com.capanema.kers.common.util.string.FiltroString;
import br.com.capanema.kers.common.util.string.StringPathUtils;
import br.com.capanema.kers.common.util.string.StringUtil;


public class TemplateManager {

  
  private static StringUtil stringUtils;
  private static FiltroString filtroString;
  
  static {
    stringUtils = new StringUtil();
    filtroString = new FiltroString();
  }
  
  public TemplateManager() {
    super();
    //this.template = template;
    //this.path = path;
  }

  public Template discoveryEstructure(Template template) throws Exception {
    
    template.setTemplateFolderes(new LinkedList<TemplateFolder>());
    template.setFilesToCopy(new LinkedList<TemplateFile>());
    template.setTemplateFiles(new LinkedList<TemplateFile>());
    
    System.out.println("Path from templates " + template.getRealPath());
    
    File root = new File(template.getRealPath() + File.separator + template.getRootFolder());
    
    if(root.isDirectory()) {
      
      if(root.getName().equals(template.getRootFolder())) {
        recurciveDiscovery(template, root);
      } else {
        for(String children : root.list()) {
          if(children.equals(Constants.PROJECT_FOLDER)) {
            recurciveDiscovery(template, new File(root + File.separator + children));
          }
        }
      }
    } else {
      throw new Exception("Invalid root template path: " + root.getCanonicalPath());
    }
    
    return template;
  }
  
@SuppressWarnings("static-access")
private static void recurciveDiscovery(Template template, File rootFolder) throws IOException {
    
    System.out.println("Avaliable >> " + rootFolder.getPath());
    
    if (rootFolder.isDirectory()) {
      
      File[] children = rootFolder.listFiles();
      
      //addFolder(template, rootFolder.getPath());
      for (int i=0; i<children.length; i++) {
        
        File fChildren = children[i];
        
        //ignore CVS and SVN folders
        if (fChildren.getName().equals("CVS") || fChildren.getName().equals(".svn")) { 
          continue;
        }
        
        //System.out.println("\t IS FOLDER ");
        if(fChildren.isDirectory() && fChildren.list().length <= 1) {
          
          if(fChildren.list().length == 0) {
            System.out.println("Esta vazio ? " + fChildren.getAbsolutePath());
            stringUtils.addFolder(template, fChildren);
          } else {
            if(fChildren.list()[0].equals("CVS") || fChildren.list()[0].equals(".svn")) {
              System.out.println("Esta vazio ? com versionamento " + fChildren.getAbsolutePath());
              stringUtils.addFolder(template, fChildren);
            } else {
              System.out.println("tinha um pasata dentro");
              recurciveDiscovery(template, fChildren);
            }
          }
            
        } else {
        
          //recurciveDiscovery(template, new File(rootFolder, children[i]));
          recurciveDiscovery(template, fChildren);
        
        }
       }
    } else {
      
      //System.out.println("\t IS File ");
      
      Matcher matcher = filtroString.getMatcherFromPatternToCopy(rootFolder.getPath());
      if (!matcher.find()) { //copy all, except velocity template (spider) files    
        //System.out.println("\t\t TO COPY " + rootFolder.getPath());
        stringUtils.addFileToCopy(template, rootFolder);
      } else {
        //System.out.println("\t\t IS TEMPLATE " + rootFolder.getPath());
        stringUtils.addTemplateFile(template, rootFolder);
      }
    }
  }
  
  /*private static void recurciveDiscovery(Template template, File rootFolder) throws IOException {
    
    System.out.print("Avaliabale >> " + rootFolder.getPath());
    
    if (rootFolder.isDirectory()) {
      
      String[] children = rootFolder.list();
      
      //addFolder(template, rootFolder.getPath());
      for (int i=0; i<children.length; i++) {
        //ignore CVS and SVN folders
        if (children[i].equals("CVS") || children[i].equals(".svn")) { 
          continue;
        }
        
        System.out.println("\t IS FOLDER ");
        //template.getProjectFolderes().add(rootFolder.getPath().replace(rootPathTemplate, ""));
        
        recurciveDiscovery(template, new File(rootFolder, children[i]));
       }
    } else {
      
      //System.out.println("\t IS File ");
      
      Matcher matcher = patternToCopy.matcher(rootFolder.getPath());        
      if (!matcher.find()) { //copy all, except velocity template (spider) files    
        //System.out.println("\t\t TO COPY " + rootFolder.getPath());
        addFileToCopy(template, rootFolder.getPath());
      } else {
        //System.out.println("\t\t IS TEMPLATE " + rootFolder.getPath());
        addTemplateFile(template, rootFolder.getPath());
      }
    }
  }*/
  
  
}
