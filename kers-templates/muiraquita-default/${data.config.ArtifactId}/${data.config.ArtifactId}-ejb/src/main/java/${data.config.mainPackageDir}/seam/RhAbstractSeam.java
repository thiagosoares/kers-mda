package br.gov.pa.muiraquita.sample.rh.seam;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;

import br.gov.pa.muiraquita.exception.BusinessException;
import br.gov.pa.muiraquita.exception.SystemException;
import br.gov.pa.muiraquita.locator.BusinessFactory;
import br.gov.pa.muiraquita.sample.rh.configuration.LocationsJndi;
import br.gov.pa.muiraquita.sample.rh.dto.appInfo.SessionInformationsDto;
import br.gov.pa.muiraquita.sample.rh.facade.RhFacadeLocal;
import br.gov.pa.muiraquita.seam.AbstractSeam;

public abstract class RhAbstractSeam extends AbstractSeam {

  
  private static final String START_PAGE = "/rh/empresa/empresa_list.xhtml";
  
  /** Objetos de sessao **/
  @In(required = true, scope = ScopeType.SESSION)
  protected SessionInformationsDto sessionInformations;

  
  protected boolean showConfirmCadastro = false;
  
  protected RhFacadeLocal getRhFacadeLocal() throws SystemException, BusinessException {
    return (RhFacadeLocal) BusinessFactory.getProxy(LocationsJndi.MuiraquitaRHLocalFacade);
  }

  public boolean isShowConfirmCadastro() {
    return showConfirmCadastro;
  }

  public void setShowConfirmCadastro(boolean showConfirmCadastro) {
    this.showConfirmCadastro = showConfirmCadastro;
  }

  
}