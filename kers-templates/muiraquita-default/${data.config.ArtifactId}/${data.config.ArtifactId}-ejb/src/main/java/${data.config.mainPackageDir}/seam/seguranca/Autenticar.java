package br.gov.pa.muiraquita.sample.rh.seam.seguranca;

import javax.faces.application.FacesMessage;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Redirect;
import org.jboss.seam.security.Identity;

import br.gov.pa.muiraquita.exception.BusinessException;
import br.gov.pa.muiraquita.exception.SystemException;
import br.gov.pa.muiraquita.exception.ThrowMachine;
import br.gov.pa.muiraquita.exception.security.InvalidPassportException;
import br.gov.pa.muiraquita.locator.BusinessFactory;
import br.gov.pa.muiraquita.sample.rh.business.error.application.ApplicationErrorCode;
import br.gov.pa.muiraquita.sample.rh.configuration.LocationsJndi;
import br.gov.pa.muiraquita.sample.rh.dto.appInfo.AlterarSenhaDto;
import br.gov.pa.muiraquita.sample.rh.dto.appInfo.LoggedUserDto;
import br.gov.pa.muiraquita.sample.rh.dto.appInfo.SessionInformationsDto;
import br.gov.pa.muiraquita.sample.rh.facade.RhFacadeLocal;
import br.gov.pa.muiraquita.seam.SeamTreatment;

@Name("autenticador")
@Scope(ScopeType.EVENT)
public class Autenticar {

  @In
  private Identity identity;
  
  @In(required = false, create = true)
  @Out(required = false)
  SessionInformationsDto sessionInformations;
  
  @In(required = false, create = true)
  @Out(required = false)
  AlterarSenhaDto alterarSenhaDto;

  protected SeamTreatment seamTreatment;

  private static final String LOGIN_SENHA_PAGE = "/seguranca/login/login.xhtml";
  private static final String ALTERAR_SENHA_PAGE = "/seguranca/login/altera_senha.xhtml";

  public Autenticar() {
    super();
    seamTreatment = new SeamTreatment();
  }

  @Begin(join = true)
  public boolean login() {
    try {

      LoggedUserDto loggedUser =
              getAutenticacaoFacade().login(identity.getCredentials().getUsername(), identity.getCredentials().getPassword());

      this.sessionInformations = new SessionInformationsDto();
      this.sessionInformations.setUserLogin(loggedUser.getNome());
      this.sessionInformations.setUserName(loggedUser.getLogin());
      this.sessionInformations.setUserPasswChanged(true);
      this.sessionInformations.setPassaporte(loggedUser.getPassaporte());
      
      

      for(String op : loggedUser.getCenarios()) {
        identity.addRole(op);
      }
      
      loggedUser.setCenarios(null);
      
      if (!loggedUser.getTrocouSenha()) {
        FacesMessages.instance().add(FacesMessage.SEVERITY_INFO, "Favor trocar a senha.");
        
        alterarSenhaDto = new AlterarSenhaDto(loggedUser.getLogin());
        
        Redirect.instance().setViewId(ALTERAR_SENHA_PAGE);
        Redirect.instance().execute();
      }

      return true;
    } catch (Throwable e) {
      this.sessionInformations = null;
      seamTreatment.tratar(e);
      return false;
    }

  }

  @End
  public String logout() {

    try {
      Identity.instance().logout();
      FacesMessages.instance().add(FacesMessage.SEVERITY_INFO, "Você foi desconectado.");
    } catch (Throwable e) {
      this.sessionInformations = null;
      seamTreatment.tratar(e);
      return null;
    }
    return LOGIN_SENHA_PAGE;
  }

  public String alterarSenha() {
    try {
      getAutenticacaoFacade().alterarSenha(this.alterarSenhaDto);
      FacesMessages.instance().add(FacesMessage.SEVERITY_INFO, "A senha foi alterada com sucesso. Bem Vindo!");
      return "/seguranca/principal/principal.xhtml";
    } catch (Throwable e) {
      seamTreatment.tratar(e);
      return LOGIN_SENHA_PAGE;
    }
  }

  public void continuar() throws InvalidPassportException, BusinessException, SystemException {
    // this.sessionInformations.getUsuarioSispatDto().setWarnLogin(null);
  }

  private RhFacadeLocal getAutenticacaoFacade() {
    try {
      return (RhFacadeLocal) BusinessFactory.getProxy(LocationsJndi.MuiraquitaRHLocalFacade);
    } catch (Throwable e) {
      throw ThrowMachine.throwInternalError(ApplicationErrorCode.LOOKUP_FAIL, "facade not Found", e);
    }
  }

}