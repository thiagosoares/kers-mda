package br.com.capanema.kers.core.trash;

import org.apache.commons.configuration.ConfigurationException;

import org.apache.commons.configuration.XMLConfiguration;

import br.com.capanema.kers.common.model.domain.Entity;

public class XmlConfigaration {
	
	
	public static void readyXml(Entity discovery) {
		
		try {
			XMLConfiguration config = new XMLConfiguration("D:/ECLIPSE-IDE/projeto_eduarda/eduardaCodeGenerator/config/configuracao.xml");
			
			discovery.setPageLista_sem_resultados(config.getBoolean("paginas.lista.display-sem-resultados"));
			
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

}
