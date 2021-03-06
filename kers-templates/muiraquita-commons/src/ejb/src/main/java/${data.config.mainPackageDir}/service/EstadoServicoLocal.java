/*
 * This code was generated by CalangoMDA (puppy) for use within the Prodepain
 * their products.The calangoMDA was created by Thiago Soares (tfs.capanema @
 * gmail.com)©CalangoDDA 2010
 */
package br.gov.pa.muiraquita.sample.rh.service;

import java.util.List;

import javax.ejb.Local;

import br.gov.pa.muiraquita.exception.BusinessException;
import br.gov.pa.muiraquita.exception.SystemException;
import br.gov.pa.muiraquita.exception.security.InvalidPassportException;
import br.gov.pa.muiraquita.sample.rh.dto.EstadoDto;

/**
 * Interface declarativa de rotinas com escopo local.
 */
@Local
public interface EstadoServicoLocal {

  public EstadoDto cadastrar(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException;

  public EstadoDto alterar(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException;

  public List<EstadoDto> buscar(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException;

  public void excluir(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException;

}