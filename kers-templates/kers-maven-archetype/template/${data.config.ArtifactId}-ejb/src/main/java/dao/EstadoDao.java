#set ($package = ${data.config.mainPackage})
#set ($modelName = ${data.crud.name})
#set ($lowercasedModelName = $stringUtils.unCapitalizeFirstLetter($modelName))
#set ($uppercasedModelName = $modelName.toUpperCase())
package ${package}.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import ${package}.configuration.AppConfigurations;
import ${package}.dto.${modelName}Dto;
import ${package}.dto.buscas.BuscasDto;
import ${package}.entity.${modelName};
import ${package}.util.query.StrQuery;

import br.gov.pa.muiraquita.exception.BusinessException;
import br.gov.pa.muiraquita.exception.InternalException;
import br.gov.pa.muiraquita.exception.SystemException;
import br.gov.pa.muiraquita.persistence.dao.entity.AbstractEntityNonPaginatedDao;
import br.gov.pa.muiraquita.persistence.sql.TipoConsulta;

public class ${modelName}Dao extends AbstractEntityNonPaginatedDao<${modelName}, Integer> implements I${modelName}Dao {

  public ${modelName}Dao() {
    super(AppConfigurations.unidadePersistencia);
  }

  public ${modelName}Dao(String persistenceUnit) {
    super(persistenceUnit);
  }

  public ${modelName}Dao(EntityManager em) {
    super(em);
  }

  public List<${modelName}> findByParameters(BuscasDto parametros) throws SystemException, InternalException, BusinessException {
    StringBuffer srtQuery = new StringBuffer();
    srtQuery.append("SELECT e FROM ${modelName} e WHERE 0 = 0 ");

    if (parametros.getId() != null) {
      srtQuery.append(" AND e.id = :id");
    } else {
      if (!StringUtils.isEmpty(parametros.getDescricao())) {
        srtQuery.append(" AND UPPER(e.descricao) LIKE :descricao");
      }
      if (!StringUtils.isEmpty(parametros.getSigla())) {
        srtQuery.append(" AND e.sigla LIKE :sigla");
      }
    }
    srtQuery.append(" ORDER BY e.id ");

    Query query = createQuery(srtQuery.toString());

    if (parametros.getId() != null) {
      query.setParameter("id", parametros.getId());
    } else {
      if (!StringUtils.isEmpty(parametros.getDescricao())) {
        query.setParameter("descricao", StrQuery.filtra(parametros.getDescricao(), TipoConsulta.CONTENDO, StrQuery.ALFA_E_NUMEROS));
      }
      if (!StringUtils.isEmpty(parametros.getSigla())) {
        query.setParameter("sigla", parametros.getSigla());
      }

    }
    return query.getResultList();
  }

  public List<${modelName}> findForDuplications(${modelName}Dto parametros) throws SystemException, InternalException {
    StringBuffer srtQuery = new StringBuffer();
    srtQuery.append("SELECT e FROM ${modelName} e WHERE 0 <> 0 ");

    if (!StringUtils.isEmpty(parametros.getDescricao())) {
      srtQuery.append(" OR e.descricao LIKE :descricao");
    }
    if (!StringUtils.isEmpty(parametros.getSigla())) {
      srtQuery.append(" OR e.sigla LIKE :sigla");
    }

    Query query = createQuery(srtQuery.toString());

    if (!StringUtils.isEmpty(parametros.getDescricao())) {
      query.setParameter("descricao", parametros.getDescricao());
    }
    if (!StringUtils.isEmpty(parametros.getSigla())) {
      query.setParameter("sigla", parametros.getSigla());
    }

    return query.getResultList();
  }

}