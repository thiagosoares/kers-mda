#set ($package = ${data.config.mainPackage})
package ${package}.service.seguranca;

import javax.ejb.Local;

import ${package}.dto.application.AlterarSenhaDto;
import ${package}.dto.application.LoggedUserDto;



import br.gov.pa.muiraquita.exception.BusinessException;

@Local
public interface AutenticacaoServicoLocal {

  public LoggedUserDto login(String user, String password) throws BusinessException;
  
  public void logout(String passaport);
  
  public void alterarSenha(AlterarSenhaDto alterarSenhaDto) throws BusinessException;
  
}
