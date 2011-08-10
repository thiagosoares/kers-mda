#set ($package = ${data.config.mainPackage})
#set ($modelName = ${data.crud.name})
#set ($lowercasedModelName = $stringUtils.unCapitalizeFirstLetter($modelName))
#set ($uppercasedModelName = $modelName.toUpperCase())
package ${package}.service;

import java.util.List;

import javax.ejb.Local;

import ${package}.dto.${modelName}Dto;
import ${package}.dto.buscas.BuscasDto;

import br.gov.pa.muiraquita.exception.BusinessException;
import br.gov.pa.muiraquita.exception.SystemException;
import br.gov.pa.muiraquita.exception.security.InvalidPassportException;

@Local
public interface ${modelName}ServicoLocal {

  public ${modelName}Dto cadastrar(${modelName}Dto estadoDto) throws SystemException, BusinessException, InvalidPassportException;

  public ${modelName}Dto alterar(${modelName}Dto estadoDto) throws SystemException, BusinessException, InvalidPassportException;

  public List<${modelName}Dto> buscar(BuscasDto buscasDto) throws SystemException, BusinessException, InvalidPassportException;

  public void excluir(${modelName}Dto estadoDto) throws SystemException, BusinessException, InvalidPassportException;

}