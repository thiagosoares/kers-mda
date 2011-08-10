package br.com.capanema.kers.core.factory.resources;

import java.io.IOException;

import org.apache.commons.lang.NotImplementedException;

import br.com.capanema.kers.common.configuration.Constants;
import br.com.capanema.kers.common.configuration.KersConfig;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.factory.resources.web.GeradorComponentsXml;
import br.com.capanema.kers.core.factory.resources.web.GeradorManifest;
import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorResouces extends CalangoFactory {

	//N�o sei pra que
	public static void gerarArquivosDeConfiguracao(EAProjectConfig projeto) throws IOException {
		
		switch (projeto.getArchetype()) {
		case MAVEN_JAVA_WEB_SEAM_2_0:
		//	gerarParaWebSeam20(projeto);
			copyFilesWebSeam20(projeto);
			break;
		case MAVEN_JAVA_WEB_SEAM_2_1:
		  throw new NotImplementedException("GEra��o de arquivos de configuracao para Seam 2.1 n�o implementada");
		case MAVEN_JAVA_WEB_WEBBEAMS_1_0:
		  throw new NotImplementedException("GEra��o de arquivos de configuracao para Web Beans n�o implementada");
		case MAVEN_JAVA_JAR:
		  throw new NotImplementedException("GEra��o de arquivos de configuracao para Jar n�o implementada");
		case MAVEN_JAVA_SWING:
		  throw new NotImplementedException("GEra��o de arquivos de configuracao para Swing n�o implementada");
		case PHP:
		  throw new NotImplementedException("GEra��o de arquivos de configuracao para PHP n�o implementada");
		default:
			break;
		}
	}
	
	private static void gerarParaWebSeam20(EAProjectConfig projeto) throws IOException {
		GeradorComponentsXml.gerarArquivoSeam20(projeto);
		GeradorManifest.gerarArquivoSeam20(projeto);
	}
	
	
	private static void copyFilesWebSeam20(EAProjectConfig projeto) throws IOException { 
	  String common_tpl_path = KersConfig.instance().getRootPathProject() + "/" + Constants.COMMON_TEMPLATES_FOLDER;
	  String nomeSistema = projeto.getNomeSistema();
	  
	  //Pom do projeto
	  String pomProjeto = FileManager.lerArquivo(common_tpl_path + "/project_conf_tpl/pom_projeto.xml");
	  pomProjeto = pomProjeto.replace("__projeto__", nomeSistema);
	  FileManager.createFife("pom.xml", projeto.getPathProjeto(), pomProjeto);
	  
	  //Pom do ear
    String pomEar = FileManager.lerArquivo(common_tpl_path + "/project_conf_tpl/pom_ear.xml");
    pomEar = pomEar.replace("__projeto__", nomeSistema);
    FileManager.createFife("pom.xml", projeto.getPathModuloEAR(), pomEar);
    
    //Pom do ejb
    String pomEbj = FileManager.lerArquivo(common_tpl_path + "/project_conf_tpl/pom_ejb.xml");
    pomEbj = pomEbj.replace("__projeto__", nomeSistema);
    FileManager.createFife("pom.xml", projeto.getPathModuloEJB(), pomEbj);
    
    //Pom do web
    String pomWeb = FileManager.lerArquivo(common_tpl_path + "/project_conf_tpl/pom_web.xml");
    pomWeb = pomWeb.replace("__projeto__", nomeSistema);
    FileManager.createFife("pom.xml", projeto.getPathModuloWEB(), pomWeb);
    
	  
    /** Configuracoes ejb */
    //seam.properties
    FileManager.createFife("seam.properties", projeto.getPathModuloEJB() + "src/main/resources", "");
    
    //import.sql
    FileManager.createFife("import.sql", projeto.getPathModuloEJB() + "src/main/resources", "");
    
    //ejb-jar
    String ejb_jar = FileManager.lerArquivo(common_tpl_path + "/ejb_conf_tpl/java_resources/META-INF/ejb-jar.xml");
    ejb_jar = ejb_jar.replace("__projeto__", nomeSistema);
    FileManager.createFife("ejb-jar.xml", projeto.getPathModuloEJB() + "src/main/resources/META-INF/", ejb_jar);
    
    //jboss-app
    String jboss_app = FileManager.lerArquivo(common_tpl_path + "/ejb_conf_tpl/java_resources/META-INF/jboss-app.xml");
    jboss_app = jboss_app.replace("__projeto__", nomeSistema);
    FileManager.createFife("jboss-app.xml", projeto.getPathModuloEJB() + "src/main/resources/META-INF/", jboss_app);
    
    //persistence
    String persistence = FileManager.lerArquivo(common_tpl_path + "/ejb_conf_tpl/java_resources/META-INF/persistence.xml");
    persistence = persistence.replace("__projeto__", nomeSistema);
    FileManager.createFife("persistence.xml", projeto.getPathModuloEJB() + "src/main/resources/META-INF/", persistence);
    
    //projeto-orm
    String projeto_orm = FileManager.lerArquivo(common_tpl_path + "/ejb_conf_tpl/java_resources/META-INF/projeto-orm.xml");
    projeto_orm = projeto_orm.replace("__projeto__", nomeSistema);
    FileManager.createFife("projeto-orm.xml", projeto.getPathModuloEJB() + "src/main/resources/META-INF/", projeto_orm);
    
    //jndi.properties
    FileManager.copiarArquivo(common_tpl_path + "/ejb_conf_tpl/test_resources/jndi.properties", projeto.getPathModuloEJB() + "src/test/resources/jndi.properties");

    //log4j.properties
    FileManager.copiarArquivo(common_tpl_path + "/ejb_conf_tpl/test_resources/log4j.properties", projeto.getPathModuloEJB() + "src/test/resources/log4j.properties");

    //persistence
    String persistence_test = FileManager.lerArquivo(common_tpl_path + "/ejb_conf_tpl/test_resources/persistence.xml");
    persistence_test = persistence_test.replace("__projeto__", nomeSistema);
    FileManager.createFife("persistence.xml", projeto.getPathModuloEJB() + "src/test/resources/", persistence_test);
    
    /** Configuracoes web */
    /** resources **/
    //messages_pt_BR.properties
    FileManager.copiarArquivo(common_tpl_path + "/seam_conf_tpl/web_resources_seam20/messages_pt_BR.properties", projeto.getPathModuloWEB() + "/src/main/resources/messages_pt_BR.properties");
    
    //seam.properties
    FileManager.copiarArquivo(common_tpl_path + "/seam_conf_tpl/web_resources_seam20/seam.properties", projeto.getPathModuloWEB() + "/src/main/resources/seam.properties");
    
    //projetoSkinPadrao.skin.properties
    FileManager.copiarArquivo(common_tpl_path + "/seam_conf_tpl/web_resources_seam20/skinPadrao.skin.properties", projeto.getPathModuloWEB() + "/src/main/resources/skinPadrao.skin.properties");

    /** WEB-INF**/
    //MANIFEST.MF
    FileManager.copiarArquivo(common_tpl_path + "/seam_conf_tpl/web_resources_seam20/META-INF/MANIFEST.MF", projeto.getPathModuloWEB() + "/src/main/webapp/META-INF/MANIFEST.MF");
    
    /** WEB-INF**/
    //applicationContext.xml
    FileManager.copiarArquivo(common_tpl_path + "/seam_conf_tpl/web_resources_seam20/WEB-INF/applicationContext.xml", projeto.getPathModuloWEB() + "/src/main/webapp/WEB-INF/applicationContext.xml");
    
    //pages.xml
    FileManager.copiarArquivo(common_tpl_path + "/seam_conf_tpl/web_resources_seam20/WEB-INF/pages.xml", projeto.getPathModuloWEB() + "/src/main/webapp/WEB-INF/pages.xml");
    
    //faces-config.xml
    FileManager.copiarArquivo(common_tpl_path + "/seam_conf_tpl/web_resources_seam20/WEB-INF/faces-config.xml", projeto.getPathModuloWEB() + "/src/main/webapp/WEB-INF/faces-config.xml");
    

    //components.xml
    String components = FileManager.lerArquivo(common_tpl_path + "/seam_conf_tpl/web_resources_seam20/WEB-INF/components.xml");
    components = components.replace("__projeto__", nomeSistema);
    FileManager.createFife("components.xml", projeto.getPathModuloWEB() + "/src/main/webapp/WEB-INF/", components);

    
    //web.xml
    String web = FileManager.lerArquivo(common_tpl_path + "/seam_conf_tpl/web_resources_seam20/WEB-INF/web.xml");
    web = web.replace("__projeto__", nomeSistema);
    FileManager.createFife("web.xml", projeto.getPathModuloWEB() + "/src/main/webapp/WEB-INF/", web);

    
    /**templates web */
	  
	}

}
