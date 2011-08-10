/*
 * This code was generated by CalangoMDA (puppy) for use within the Prodepain
 * their products.The calangoMDA was created by Thiago Soares (tfs.capanema @
 * gmail.com)©CalangoDDA 2010
 */
package br.gov.pa.muiraquita.sample.rh.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import br.gov.pa.muiraquita.exception.BusinessException;
import br.gov.pa.muiraquita.exception.InternalException;
import br.gov.pa.muiraquita.exception.SystemException;
import br.gov.pa.muiraquita.persistence.dao.entity.AbstractEntityNonPaginatedDao;
import br.gov.pa.muiraquita.sample.rh.dto.EstadoDto;
import br.gov.pa.muiraquita.sample.rh.entity.Estado;
import br.gov.pa.muiraquita.sample.rh.util.query.StrQuery;
import br.gov.pa.muiraquita.sample.rh.util.query.TipoConsulta;

public class EstadoDao extends AbstractEntityNonPaginatedDao<Estado, Integer> implements IEstadoDao {

  
  public EstadoDao() {
    super("muiraquitaRh");
  }
  
  public EstadoDao(String persistenceUnit) {
    super(persistenceUnit);
  }

  public EstadoDao(EntityManager em) {
    super(em);
  }

  public List<Estado> findByParameters(EstadoDto parametros) throws SystemException, InternalException, BusinessException {
    StringBuffer srtQuery = new StringBuffer();
    srtQuery.append("SELECT e FROM Estado e WHERE 0 = 0 ");

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
  
  public List<Estado> findForDuplications(EstadoDto parametros) throws SystemException, InternalException {
    StringBuffer srtQuery = new StringBuffer();
    srtQuery.append("SELECT e FROM Estado e WHERE 0 <> 0 ");

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