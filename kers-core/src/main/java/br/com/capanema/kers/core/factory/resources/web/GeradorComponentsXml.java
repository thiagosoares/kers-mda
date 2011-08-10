package br.com.capanema.kers.core.factory.resources.web;

import java.io.IOException;

import br.com.capanema.kers.common.configuration.Constants;
import br.com.capanema.kers.common.configuration.KersConfig;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorComponentsXml extends CalangoFactory {

	
	public static void gerarArquivoSeam20(EAProjectConfig projeto) throws IOException {
		
	  System.out.print("\n\t\tGerando arquivo Components.xml para: ");
	  
	  
	  String pomProjeto = FileManager.lerArquivo(KersConfig.instance().getRootPathProject() + "\\templates\\calangoMDA-core\\seam_conf_tpl\\web_resources_seam20\\WEB-INF\\components.xml");
    pomProjeto.replace("__projeto__", projeto.getNomeSistema());
    FileManager.createFife("components.xml", projeto.getPathProjeto(), pomProjeto);
	}
	
}
