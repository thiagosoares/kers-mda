/*
 * This code was generated by CalangoMDA (puppy) for use within the Prodepain
 * their products.The calangoMDA was created by Thiago Soares (tfs.capanema @
 * gmail.com)©CalangoDDA 2010
 */
package br.gov.pa.muiraquita.sample.rh.service;

import java.util.List;

import javax.ejb.Stateless;

import br.gov.pa.muiraquita.exception.BusinessException;
import br.gov.pa.muiraquita.exception.SystemException;
import br.gov.pa.muiraquita.exception.security.InvalidPassportException;
import br.gov.pa.muiraquita.injection.annotation.Injection;
import br.gov.pa.muiraquita.sample.rh.business.EstadoBusiness;
import br.gov.pa.muiraquita.sample.rh.dao.IEstadoDao;
import br.gov.pa.muiraquita.sample.rh.dto.EstadoDto;
import br.gov.pa.muiraquita.sample.rh.entity.Estado;

//@UcStereotype(type = UcType.CRUD_TABULAR)
@Stateless
public class EstadoServicoBean extends RHAbstractServico implements EstadoServicoLocal {

  @Injection
  private IEstadoDao dao;

  public EstadoDto cadastrar(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException {
    buscarUsuarioSessao(estadoDto.getPassaporte());
    new EstadoBusiness(estadoDto).validarInclusao();
    
    Estado estado = (Estado) converter.getEntity(estadoDto);
    dao.create(estado);
    return converter.getDto(EstadoDto.class, estado);
  }

  public EstadoDto alterar(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException {
    buscarUsuarioSessao(estadoDto.getPassaporte());
    Estado estado = dao.findById(estadoDto.getId());
    new EstadoBusiness(estado, estadoDto).validarAlteracao();
    converter.updateEntity(estadoDto, estado);
    dao.update(estado);
    return converter.getDto(EstadoDto.class, estado);
  }

  public List<EstadoDto> buscar(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException {
    buscarUsuarioSessao(estadoDto.getPassaporte());
    // new EstadoBusiness(estadoDto).validarBuscas();
    List<Estado> estadoLista = dao.findByParameters(estadoDto);
    return converter.getDtoList(EstadoDto.class, estadoLista);
  }

  public void excluir(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException {
    buscarUsuarioSessao(estadoDto.getPassaporte());
    Estado estado = dao.findById(estadoDto.getId());
    new EstadoBusiness(estado, estadoDto).validarExclusao();
    dao.delete(estado);
  }

}