#set ($package = ${data.config.mainPackage})
#set ($modelName = ${data.crud.name})
#set ($lowercasedModelName = $stringUtils.unCapitalizeFirstLetter($modelName))
#set ($uppercasedModelName = $modelName.toUpperCase())
package ${package}.service;

import java.util.List;

import javax.ejb.Stateless;

import ${package}.business.${modelName}Business;
import ${package}.dao.I${modelName}Dao;
import ${package}.dto.${modelName}Dto;
import ${package}.dto.buscas.BuscasDto;
import ${package}.entity.${modelName};

import br.gov.pa.muiraquita.exception.BusinessException;
import br.gov.pa.muiraquita.exception.SystemException;
import br.gov.pa.muiraquita.exception.security.InvalidPassportException;
import br.gov.pa.muiraquita.injection.annotation.Injection;

@Stateless
public class ${modelName}ServicoBean extends MyAbstractServico implements ${modelName}ServicoLocal {

  @Injection
  private I${modelName}Dao dao;

  public ${modelName}Dto cadastrar(${modelName}Dto ${lowercasedModelName}Dto) throws SystemException, BusinessException, InvalidPassportException {
    new ${modelName}Business(${lowercasedModelName}Dto).validarInclusao();
    ${modelName} ${lowercasedModelName} = (${modelName}) converter.getEntity(${lowercasedModelName}Dto);
    dao.create(${lowercasedModelName});
    return converter.getDto(${modelName}Dto.class, ${lowercasedModelName});
  }

  public ${modelName}Dto alterar(${modelName}Dto ${lowercasedModelName}Dto) throws SystemException, BusinessException, InvalidPassportException {
    ${modelName} ${lowercasedModelName} = dao.findById(${lowercasedModelName}Dto.getId());
    new ${modelName}Business(${lowercasedModelName}, ${lowercasedModelName}Dto).validarAlteracao();
    ${lowercasedModelName} = converter.updateEntity(${lowercasedModelName}Dto, ${lowercasedModelName});
    ${lowercasedModelName} = dao.update(${lowercasedModelName});
    return converter.getDto(${modelName}Dto.class, ${lowercasedModelName});
  }

  public List<${modelName}Dto> buscar(BuscasDto buscasDto) throws SystemException, BusinessException, InvalidPassportException {
    // new ${modelName}Business(${lowercasedModelName}Dto).validarBuscas();
    List<${modelName}> ${lowercasedModelName}Lista = dao.findByParameters(buscasDto);
    return converter.getDtoList(${modelName}Dto.class, ${lowercasedModelName}Lista);
  }

  public void excluir(${modelName}Dto ${lowercasedModelName}Dto) throws SystemException, BusinessException, InvalidPassportException {
    ${modelName} ${lowercasedModelName} = dao.findById(${lowercasedModelName}Dto.getId());
    new ${modelName}Business(${lowercasedModelName}, ${lowercasedModelName}Dto).validarExclusao();
    dao.delete(${lowercasedModelName});
  }
}