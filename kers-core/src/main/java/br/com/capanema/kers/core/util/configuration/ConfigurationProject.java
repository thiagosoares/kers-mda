package br.com.capanema.kers.core.util.configuration;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.common.model.projectea.TemplateConfig;
import br.com.capanema.kers.common.model.template.DomainSourceType;

public class ConfigurationProject {

	
	public static EAProjectConfig configurarProjeto(TemplateConfig parametrosProjeto) {
		
		System.out.println("\t Configura��o: progress....");
		
		verificarParametrosConfiguracao(parametrosProjeto);
		
		EAProjectConfig projeto = new EAProjectConfig(parametrosProjeto);
		
		return projeto;
	}
	
	private static void verificarParametrosConfiguracao(TemplateConfig param) {
		System.out.println("\t \t Validando parametros de configuracao o projeto ....");
		
		if(param.getArchetype() == null) {
			throw new IllegalArgumentException("Informe o archetype!");
		}
		if(param.getTipoFonteProjeto() == null) {
			throw new IllegalArgumentException("Informe o tipo de fonte do projeto ");
		}
		if(param.getNomeSistema() == null) {
			throw new IllegalArgumentException("Informe o nome do sistema");
		}
		if(param.getLocalDestinoProjeto() == null) {
			throw new IllegalArgumentException("Informe o destino do c�digo gerado!");
		}
		if(param.getSomenteEntidades() == null) {
			param.setSomenteEntidades(false);
		}
		
		if(param.getPackageRoot() == null) {  //TODO tenho que redescubrir essa porra!
			throw new IllegalArgumentException("Informe o pacote da classes");
		}
		if(param.getTipoFormularioWeb() == null) {
			throw new IllegalArgumentException("Informe o tipo de formuil�rio web que ser� gaerado!");
		}
		if(param.getTipoArquitetura() == null) {
			throw new IllegalArgumentException("Informe o tipo de arquitetura que ser� gerado!");
		}
		
		if(param.getTipoFonteProjeto().equals(DomainSourceType.FROM_XMI)) {
			if(param.getLocalSourceXmi() == null) {
				throw new IllegalArgumentException("Informe o local da fonte XMI do projeto !");
			}
			if(param.getDiagramaIteracao() == null) { //TODO Remover quando suportar geracao para todos os diagramas
	      throw new IllegalArgumentException("Informe o nome do diagrama!");
	    }
		} else if(param.getTipoFonteProjeto().equals(DomainSourceType.FROM_JAR)) {
			/*if(param.get == null) {
				throw new IllegalArgumentException("");
			}*/
		}
	}
}