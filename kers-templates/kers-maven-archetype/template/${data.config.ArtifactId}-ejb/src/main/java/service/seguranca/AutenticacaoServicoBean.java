#set ($package = ${data.config.mainPackage})
package ${package}.service.seguranca;

import javax.ejb.Stateless;

import ${package}.business.SegurancaBusiness;
import ${package}.business.error.application.SecurityInternalErrorCode;
import ${package}.configuration.LocationsJndi;
import ${package}.dto.application.AlterarSenhaDto;
import ${package}.dto.application.LoggedUserDto;
import ${package}.service.MyAbstractServico;

import br.gov.pa.controleacesso.dto.UsuarioDto;
import br.gov.pa.controleacesso.facade.ControleAcessoFacadeRemote;

import br.gov.pa.muiraquita.converter.Converter;
import br.gov.pa.muiraquita.exception.BusinessException;
import br.gov.pa.muiraquita.exception.ThrowMachine;
import br.gov.pa.muiraquita.exception.business.NegocioException;
import br.gov.pa.muiraquita.exception.business.SistemaException;
import br.gov.pa.muiraquita.exception.security.PassaportInvalidoException;
import br.gov.pa.muiraquita.injection.annotation.Injection;
import br.gov.pa.muiraquita.locator.BusinessFactory;

@Stateless
public class AutenticacaoServicoBean extends MyAbstractServico implements AutenticacaoServicoLocal {

  @Injection
  private Converter converter;

  public LoggedUserDto login(String user, String password) throws BusinessException {

    br.gov.pa.controleacesso.dto.UsuarioDto usuarioCA = null;
    try {
      ControleAcessoFacadeRemote caRemote = (ControleAcessoFacadeRemote) BusinessFactory.getProxy(LocationsJndi.controleAcessoRemoteFacade);

      if (caRemote == null) {
        throw ThrowMachine.throwBusinessError(SecurityInternalErrorCode.USER_NOT_FOUND);
      }

      usuarioCA = caRemote.autenticarDtoSessao(37l, user, password);

    } catch (PassaportInvalidoException e) { // Override indeprecated exceptions of ControleAcesso
      throw ThrowMachine.throwBusinessError(SecurityInternalErrorCode.FAIL_LOGIN, e.getMessage());
    } catch (NegocioException e) {
      throw ThrowMachine.throwBusinessError(SecurityInternalErrorCode.FAIL_LOGIN, e.getMessage());
    } catch (SistemaException e) {
      throw ThrowMachine.throwSystemError(SecurityInternalErrorCode.FAIL_LOGIN, e.getMessage());
    }

    if (usuarioCA == null) {
      throw ThrowMachine.throwBusinessError(SecurityInternalErrorCode.USER_NOT_FOUND);
    }

    LoggedUserDto usuarioDto = new LoggedUserDto();
    usuarioDto.setId(usuarioCA.getId());
    usuarioDto.setNome(usuarioCA.getNome());
    usuarioDto.setLogin(usuarioCA.getLogin());
    usuarioDto.setCpf(usuarioCA.getCpf());
    usuarioDto.setPassaporte(usuarioCA.getPassaporte());
    usuarioDto.setCenarios(usuarioCA.getOperacoes());
    usuarioDto.setTrocouSenha(usuarioCA.getTrocouSenha().asBoolean());

    new SegurancaBusiness(usuarioDto).validarLogin();

    return usuarioDto;
  }

  public void logout(String passaport) {

  }

  public void alterarSenha(AlterarSenhaDto alterarSenhaDto) throws BusinessException {

    try {
      ControleAcessoFacadeRemote caRemote = (ControleAcessoFacadeRemote) BusinessFactory.getProxy(LocationsJndi.controleAcessoRemoteFacade);

      if (caRemote == null) {
        throw ThrowMachine.throwBusinessError(SecurityInternalErrorCode.USER_NOT_FOUND);
      }

      caRemote.alterarSenhaGlobal(new UsuarioDto(alterarSenhaDto.getLogin(), alterarSenhaDto.getNovaSenha()), alterarSenhaDto.getSenha());

    } catch (PassaportInvalidoException e) {
      throw ThrowMachine.throwBusinessError(SecurityInternalErrorCode.FAIL_LOGIN, e.getMessage());
    } catch (NegocioException e) {
      throw ThrowMachine.throwBusinessError(SecurityInternalErrorCode.FAIL_LOGIN, e.getMessage());
    } catch (SistemaException e) {
      throw ThrowMachine.throwSystemError(SecurityInternalErrorCode.FAIL_LOGIN, e.getMessage());
    }
  }

}