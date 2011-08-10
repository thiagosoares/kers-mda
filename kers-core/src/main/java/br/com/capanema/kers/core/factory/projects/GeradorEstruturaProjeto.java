package br.com.capanema.kers.core.factory.projects;

import java.io.IOException;

import org.apache.commons.lang.NotImplementedException;

import br.com.capanema.kers.common.model.projectea.Archetype;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorEstruturaProjeto extends CalangoFactory {

	
	public static void gerarEstruturaProjeto(EAProjectConfig projeto) throws IOException {
		
		switch (projeto.getArchetype()) {
		case MAVEN_JAVA_WEB_SEAM_2_0:
			gerarEstruturaMavenWebSeam(projeto);
			break;

		case MAVEN_JAVA_WEB_SEAM_2_1:
			gerarEstruturaMavenWebSeam(projeto);
			break;
			
		case MAVEN_JAVA_WEB_WEBBEAMS_1_0:
			gerarEstruturaMavenWebSeam(projeto);
			break;
			
		case MAVEN_JAVA_SWING:
			gerarEstruturaMavenSwing(projeto);
			break;

		case MAVEN_JAVA_JAR:
			gerarEstruturaMavenJar(projeto);
			break;
		
		case PHP:
			gerarEstruturaPhp(projeto);
			break;
		}
		
	}
	
	
	private static void gerarEstruturaMavenWebSeam(EAProjectConfig projeto) throws IOException {
		
		FileManager.gerarDiretorio(projeto.getPathModuloEAR());
			FileManager.gerarDiretorio(projeto.getPathModuloEAR()+"src/main/application/META-INF");
		FileManager.gerarDiretorio(projeto.getPathModuloEJB());
			FileManager.gerarDiretorio(projeto.getPathModuloEJB() + "src/main/java");
			FileManager.gerarDiretorio(projeto.getPathModuloEJB() + "src/main/resources");
			FileManager.gerarDiretorio(projeto.getPathModuloEJB() + "src/test/java");
			FileManager.gerarDiretorio(projeto.getPathModuloEJB() + "src/test/resources");
		FileManager.gerarDiretorio(projeto.getPathModuloWEB());
			FileManager.gerarDiretorio(projeto.getPathModuloWEB() + "src/main/webapp");
			  FileManager.gerarDiretorio(projeto.getPathModuloWEB() + "src/main/webapp/WEB-INF");
			  FileManager.gerarDiretorio(projeto.getPathModuloWEB() + "src/main/webapp/META-INF");
			FileManager.gerarDiretorio(projeto.getPathModuloWEB() + "src/main/resources");	
		
	}

	private static void gerarEstruturaMavenSwing(EAProjectConfig projeto) throws IOException {
		throw new NotImplementedException("archetype Swing n�o implementado");
	}
	
	private static void gerarEstruturaMavenJar(EAProjectConfig projeto) throws IOException {
		throw new NotImplementedException("archetype Jar n�o implementado");
	}
	
	private static void gerarEstruturaPhp(EAProjectConfig projeto) throws IOException {
		throw new NotImplementedException("archetype PHP n�o implementado");
	}
	
}
