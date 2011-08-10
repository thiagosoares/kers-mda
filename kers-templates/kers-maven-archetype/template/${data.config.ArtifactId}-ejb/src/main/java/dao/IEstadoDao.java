#set ($package = ${data.config.mainPackage})
#set ($modelName = ${data.crud.name})
#set ($lowercasedModelName = $stringUtils.unCapitalizeFirstLetter($modelName))
#set ($uppercasedModelName = $modelName.toUpperCase())
package ${package}.dao;

import java.util.List;

import ${package}.dto.${modelName}Dto;
import ${package}.dto.buscas.BuscasDto;
import ${package}.entity.${modelName};



import br.gov.pa.muiraquita.exception.BusinessException;
import br.gov.pa.muiraquita.exception.InternalException;
import br.gov.pa.muiraquita.exception.SystemException;
import br.gov.pa.muiraquita.persistence.dao.IDao;

/**
 * Interface declarativa de rotinas do Dao.
 */
public interface I${modelName}Dao extends IDao<${modelName}, Integer> {

  public List<${modelName}> findByParameters(BuscasDto buscasDto) throws SystemException, InternalException, BusinessException;
  
  public List<${modelName}> findForDuplications(${modelName}Dto parametros) throws SystemException, InternalException;

}