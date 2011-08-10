#set ($package = ${data.config.mainPackage})
package ${package}.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ${package}.configuration.AppConfigurations;



import br.gov.pa.muiraquita.converter.Converter;
import br.gov.pa.muiraquita.injection.annotation.Injection;
import br.gov.pa.muiraquita.service.AbstractService;

public abstract class MyAbstractServico extends AbstractService {

  @PersistenceContext(unitName = AppConfigurations.unidadePersistencia)
  protected EntityManager em;

  @Injection
  protected Converter converter;

}
