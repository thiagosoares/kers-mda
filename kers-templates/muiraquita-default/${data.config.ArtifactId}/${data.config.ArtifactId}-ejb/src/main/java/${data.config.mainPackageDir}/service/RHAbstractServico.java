package br.gov.pa.muiraquita.sample.rh.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.gov.pa.muiraquita.converter.Converter;
import br.gov.pa.muiraquita.injection.annotation.Injection;
import br.gov.pa.muiraquita.sample.rh.configuration.AppConfigurations;
import br.gov.pa.muiraquita.service.AbstractService;

/**
 * 
 * TODO: descrever classe
 * <p>
 * 
 * Exemplo de uso:
 * 
 * <pre>
 * 	<code>
 *  java BookmarksAbstractServico.java
 *  </code>
 * </pre>
 * 
 * @author Thiago Soares [at PRODPEPA]
 * @since Mar 22, 2011
 * 
 */
public abstract class RHAbstractServico extends AbstractService {

  @PersistenceContext(unitName = AppConfigurations.unidadePersistencia)
  protected EntityManager em;

  @Injection
  protected Converter converter;

  
  
  
  // /@PersistenceContext(unitName = "acesso")
  // protected EntityManager em2;

  protected void buscarUsuarioSessao(String passaporte) {
    // TODO Auto-generated method stub
  }

}
