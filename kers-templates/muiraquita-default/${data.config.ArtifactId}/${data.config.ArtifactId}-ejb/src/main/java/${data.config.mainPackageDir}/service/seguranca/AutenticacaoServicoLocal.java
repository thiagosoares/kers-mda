package br.gov.pa.muiraquita.sample.rh.service.seguranca;

import javax.ejb.Local;

import br.gov.pa.muiraquita.exception.BusinessException;
import br.gov.pa.muiraquita.sample.rh.dto.appInfo.AlterarSenhaDto;
import br.gov.pa.muiraquita.sample.rh.dto.appInfo.LoggedUserDto;

@Local
public interface AutenticacaoServicoLocal {

  
  public LoggedUserDto login(String user, String password) throws BusinessException;
  
  public void logout(String passaport);
  
  public void alterarSenha(AlterarSenhaDto alterarSenhaDto) throws BusinessException;
  
  public void continuar();
  
  
}
