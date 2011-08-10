package br.com.capanema.kers.common.configuration;

import java.io.File;

public class Constants {

  private static Constants instance;
  
  private Constants() {
    super();
  }
  public static Constants instance() {
    if(instance == null) {
      instance = new Constants();
    }
    return instance;
  }
  
  private static String PREFERENCE_DELIMITER = ";";

  public static final String  GAMBI = "C:"+File.separator+"workspace"+File.separator+"calango"+File.separator+"CalangoMda"+File.separator+"calangoMDA-core"+File.separator+"";
  
  public static final String PROJECT_FOLDER = "project";
  public static final String TEMPLATE_SRC_FOLDER = "${data.config.mainPackageDir}"; 
  public static final String TEMPLATE_MODULE_FOLDER = "${data.config.ArtifactId}"; 
  
  public static final String COMMON_TEMPLATES_FOLDER = "common_templates";
  public static final String TEMPLATES_FOLDER = "templates";
  public static final String TEMPLATES_FOLDER_DEFAULT = "templates"+File.separator+"default";
  
  
  //The identifiers for the preferences
  public static final String PREFERENCE_REPOSITORIES = "repositories";
  public static final String PREFERENCE_REPOSITORY_TIMEOUT = "repository_timeout";
  public static final String PREFERENCE_TEMPLATE_PATH = "template_path";
  public static final String PREFERENCE_MERGETOOL_PATH = "mergetool_path";
	
  //The default values for the preferences
  public static final String DEFAULT_WEB_REPOSITORIES = "http://www.capanema.org/repository/repo.xml;http://www.capanema.org/repository/nightly/repo.xml;";
  public static final int DEFAULT_TIMEOUT_REPOSITORY = 3000;
  
  
  //Template-Files.xml
  public static final String KEY_BUILD_ESTRUCTURE = "STRUCTURE"; 
  public static final String KEY_BUILD_MAVEN_CONFIG = "MAVEN"; //"##buildMAVEN##"
  public static final String KEY_BUILD_FOR_CRUD = "FOR.CRUD";
  public static final String KEY_BUILD_FOR_PROJECT_CLASSES = "FOR.PROJECT";
  public static final String KEY_BUILD_LAYOUT = "##buildLAYOUT##";
  public static final String KEY_BUILD_TECH = "##buildTECH##";
  public static final String KEY_BUILD_PACKAGE = "##buildPACKAGE##";
  public static final String KEY_BUILD_MAPPING = "##buildMAPPING##";
  
  
}