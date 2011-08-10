package br.com.capanema.kers.core.facade;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.configuration.ConfigurationException;

import br.com.capanema.kers.common.model.projectea.TemplateConfig;

public interface ICalangoFacade {

	public void buildAllProject(TemplateConfig parametros) throws Exception;
	
}
