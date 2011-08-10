package br.com.capanema.kers.core.engine;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;

import br.com.capanema.kers.common.model.projectea.TemplateConfig;
import br.com.capanema.kers.core.factory.pages.form.FormPageBuilder;
import br.com.capanema.kers.core.factory.pages.list.ListPAgeBuilder;
import br.com.capanema.kers.core.factory.pages.update.UpdatePageBuilder;
import br.com.capanema.kers.core.factory.source.business.BusinessBuilder;
import br.com.capanema.kers.core.factory.source.businessError.BusinessErrorBuilder;
import br.com.capanema.kers.core.factory.source.dao.DaoBuilder;
import br.com.capanema.kers.core.factory.source.entity.EntityBuilder;
import br.com.capanema.kers.core.factory.source.facade.FacadeBuilder;
import br.com.capanema.kers.core.factory.source.localizacoesCdu.LocationsCDUBuilder;
import br.com.capanema.kers.core.factory.source.seam.SeamBuilder;
import br.com.capanema.kers.core.factory.source.service.ServiceBuilder;

public class EngineMuiraquitaLigth extends AbstractEngine {

  
  public EngineMuiraquitaLigth(TemplateConfig parametros) throws ConfigurationException, IOException {
    super(parametros);
  }

  public void execute() throws Exception {
    
	    //Gerar Codigo
    
    EntityBuilder.build(projeto);
    if(!projeto.getSomenteEntidades()) {
      BusinessErrorBuilder.build(projeto);
      BusinessBuilder.build(projeto);
      FacadeBuilder.build(projeto);
	    
      DaoBuilder.build(projeto);
	    SeamBuilder.build(projeto);
	    ServiceBuilder.build(projeto);
	    LocationsCDUBuilder.build(projeto);

	    // View
	    FormPageBuilder.build(projeto);
	    UpdatePageBuilder.build(projeto);
      ListPAgeBuilder.build(projeto);
    }
  }

}
