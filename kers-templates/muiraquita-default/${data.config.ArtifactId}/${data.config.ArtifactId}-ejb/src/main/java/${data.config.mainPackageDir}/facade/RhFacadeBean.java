/*
 * This code was generated by CalangoMDA (puppy) for use within the Prodepain
 * their products.The calangoMDA was created by Thiago Soares (tfs.capanema @
 * gmail.com)©CalangoDDA 2010
 */
package br.gov.pa.muiraquita.sample.rh.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.gov.pa.muiraquita.exception.BusinessException;
import br.gov.pa.muiraquita.exception.SystemException;
import br.gov.pa.muiraquita.exception.security.InvalidPassportException;
import br.gov.pa.muiraquita.facade.AbstractFacade;
import br.gov.pa.muiraquita.sample.rh.dto.BeneficioDto;
import br.gov.pa.muiraquita.sample.rh.dto.DepartamentoDto;
import br.gov.pa.muiraquita.sample.rh.dto.DependenteDto;
import br.gov.pa.muiraquita.sample.rh.dto.EmpresaDto;
import br.gov.pa.muiraquita.sample.rh.dto.EstadoDto;
import br.gov.pa.muiraquita.sample.rh.dto.FuncionarioDto;
import br.gov.pa.muiraquita.sample.rh.dto.HistoricoFuncionalDto;
import br.gov.pa.muiraquita.sample.rh.dto.MunicipioDto;
import br.gov.pa.muiraquita.sample.rh.dto.appInfo.AlterarSenhaDto;
import br.gov.pa.muiraquita.sample.rh.dto.appInfo.LoggedUserDto;
import br.gov.pa.muiraquita.sample.rh.dto.buscas.ApoioBuscasDto;
import br.gov.pa.muiraquita.sample.rh.service.BeneficioServicoLocal;
import br.gov.pa.muiraquita.sample.rh.service.DepartamentoServicoLocal;
import br.gov.pa.muiraquita.sample.rh.service.DependenteServicoLocal;
import br.gov.pa.muiraquita.sample.rh.service.EmpresaServicoLocal;
import br.gov.pa.muiraquita.sample.rh.service.EstadoServicoLocal;
import br.gov.pa.muiraquita.sample.rh.service.FuncionarioServicoLocal;
import br.gov.pa.muiraquita.sample.rh.service.HistoricoFuncionalServicoLocal;
import br.gov.pa.muiraquita.sample.rh.service.MunicipioServicoLocal;
import br.gov.pa.muiraquita.sample.rh.service.seguranca.AutenticacaoServicoLocal;

@Stateless
public class RhFacadeBean extends AbstractFacade implements RhFacadeLocal, RhFacadeRemote {

  @EJB
  private AutenticacaoServicoLocal autenticacaoServicoLocal;

  /* ----- Service de Beneficio ----- */
  @EJB
  private BeneficioServicoLocal beneficioServico;

  /* ----- Service de Departamento ----- */
  @EJB
  private DepartamentoServicoLocal departamentoServico;

  /* ----- Service de Dependente ----- */
  @EJB
  private DependenteServicoLocal dependenteServico;

  /* ----- Service de Empresa ----- */
  @EJB
  private EmpresaServicoLocal empresaServico;

  /* ----- Service de Estado ----- */
  @EJB
  private EstadoServicoLocal estadoServico;

  /* ----- Service de Funcionario ----- */
  @EJB
  private FuncionarioServicoLocal funcionarioServico;

  /* ----- Service de HistoricoFuncional ----- */
  @EJB
  private HistoricoFuncionalServicoLocal historicofuncionalServico;

  /* ----- Service de Municipio ----- */
  @EJB
  private MunicipioServicoLocal municipioServico;

  /**
   * 
   * =========== Rotinas ===========
   * 
   */

  /**
   * 
   * Rotinas de Login
   * 
   */

  public LoggedUserDto login(String user, String password) throws BusinessException {
    return autenticacaoServicoLocal.login(user, password);
  }

  public void logout(String passaport) {
    autenticacaoServicoLocal.logout(passaport);
  }

  public void alterarSenha(AlterarSenhaDto alterarSenhaDto) throws BusinessException {
     autenticacaoServicoLocal.alterarSenha(alterarSenhaDto);
  }

  /**
   * 
   * Rotinas de Beneficio
   * 
   */
  public BeneficioDto cadastrarBeneficio(BeneficioDto beneficioDto) throws SystemException, BusinessException, InvalidPassportException {
    return beneficioServico.cadastrar(beneficioDto);
  }

  public BeneficioDto alterarBeneficio(BeneficioDto beneficioDto) throws SystemException, BusinessException, InvalidPassportException {
    return beneficioServico.alterar(beneficioDto);
  }

  public List<BeneficioDto> buscarBeneficios(BeneficioDto beneficioDto) throws SystemException, BusinessException, InvalidPassportException {
    return beneficioServico.buscar(beneficioDto);
  }

  public void excluirBeneficio(BeneficioDto beneficioDto) throws SystemException, BusinessException, InvalidPassportException {
    beneficioServico.excluir(beneficioDto);
  }

  /**
   * 
   * Rotinas de Departamento
   * 
   */
  public DepartamentoDto cadastrarDepartamento(DepartamentoDto departamentoDto) throws SystemException, BusinessException, InvalidPassportException {
    return departamentoServico.cadastrar(departamentoDto);
  }

  public DepartamentoDto alterarDepartamento(DepartamentoDto departamentoDto) throws SystemException, BusinessException, InvalidPassportException {
    return departamentoServico.alterar(departamentoDto);
  }

  public List<DepartamentoDto> buscarDepartamentos(DepartamentoDto departamentoDto) throws SystemException, BusinessException, InvalidPassportException {
    return departamentoServico.buscar(departamentoDto);
  }

  public void excluirDepartamento(DepartamentoDto departamentoDto) throws SystemException, BusinessException, InvalidPassportException {
    departamentoServico.excluir(departamentoDto);
  }

  /**
   * 
   * Rotinas de Dependente
   * 
   */
  public DependenteDto cadastrarDependente(DependenteDto dependenteDto) throws SystemException, BusinessException, InvalidPassportException {
    return dependenteServico.cadastrar(dependenteDto);
  }

  public DependenteDto alterarDependente(DependenteDto dependenteDto) throws SystemException, BusinessException, InvalidPassportException {
    return dependenteServico.alterar(dependenteDto);
  }

  public List<DependenteDto> buscarDependentes(DependenteDto dependenteDto) throws SystemException, BusinessException, InvalidPassportException {
    return dependenteServico.buscar(dependenteDto);
  }

  public void excluirDependente(DependenteDto dependenteDto) throws SystemException, BusinessException, InvalidPassportException {
    dependenteServico.excluir(dependenteDto);
  }

  /**
   * 
   * Rotinas de Empresa
   * 
   */
  public EmpresaDto cadastrarEmpresa(EmpresaDto empresaDto) throws SystemException, BusinessException, InvalidPassportException {
    return empresaServico.cadastrar(empresaDto);
  }

  public EmpresaDto alterarEmpresa(EmpresaDto empresaDto) throws SystemException, BusinessException, InvalidPassportException {
    return empresaServico.alterar(empresaDto);
  }

  public List<EmpresaDto> buscarEmpresas(EmpresaDto empresaDto) throws SystemException, BusinessException, InvalidPassportException {
    return empresaServico.buscar(empresaDto);
  }

  public void excluirEmpresa(EmpresaDto empresaDto) throws SystemException, BusinessException, InvalidPassportException {
    empresaServico.excluir(empresaDto);
  }


  /**
   * 
   * Rotinas de Estado
   * 
   */
  public EstadoDto cadastrarEstado(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException {
    return estadoServico.cadastrar(estadoDto);
  }

  public EstadoDto alterarEstado(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException {
    return estadoServico.alterar(estadoDto);
  }

  public List<EstadoDto> buscarEstados(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException {
    return estadoServico.buscar(estadoDto);
  }

  public void excluirEstado(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException {
    estadoServico.excluir(estadoDto);
  }

  /**
   * 
   * Rotinas de Funcionario
   * 
   */
  public FuncionarioDto cadastrarFuncionario(FuncionarioDto funcionarioDto) throws SystemException, BusinessException, InvalidPassportException {
    return funcionarioServico.cadastrar(funcionarioDto);
  }

  public FuncionarioDto alterarFuncionario(FuncionarioDto funcionarioDto) throws SystemException, BusinessException, InvalidPassportException {
    return funcionarioServico.alterar(funcionarioDto);
  }

  public List<FuncionarioDto> buscarFuncionarios(FuncionarioDto funcionarioDto) throws SystemException, BusinessException, InvalidPassportException {
    return funcionarioServico.buscar(funcionarioDto);
  }

  public void excluirFuncionario(FuncionarioDto funcionarioDto) throws SystemException, BusinessException, InvalidPassportException {
    funcionarioServico.excluir(funcionarioDto);
  }

  /**
   * 
   * Rotinas de HistoricoFuncional
   * 
   */
  public HistoricoFuncionalDto cadastrarHistoricoFuncional(HistoricoFuncionalDto historicoFuncionalDto) throws SystemException, BusinessException, InvalidPassportException {
    return historicofuncionalServico.cadastrar(historicoFuncionalDto);
  }

  public HistoricoFuncionalDto alterarHistoricoFuncional(HistoricoFuncionalDto historicoFuncionalDto) throws SystemException, BusinessException, InvalidPassportException {
    return historicofuncionalServico.alterar(historicoFuncionalDto);
  }

  public List<HistoricoFuncionalDto> buscarHistoricoFuncionals(HistoricoFuncionalDto historicoFuncionalDto) throws SystemException, BusinessException, InvalidPassportException {
    return historicofuncionalServico.buscar(historicoFuncionalDto);
  }

  public void excluirHistoricoFuncional(HistoricoFuncionalDto historicoFuncionalDto) throws SystemException, BusinessException, InvalidPassportException {
    historicofuncionalServico.excluir(historicoFuncionalDto);
  }

  /**
   * 
   * Rotinas de Municipio
   * 
   */
  public MunicipioDto cadastrarMunicipio(MunicipioDto municipioDto) throws SystemException, BusinessException, InvalidPassportException {
    return municipioServico.cadastrar(municipioDto);
  }

  public MunicipioDto alterarMunicipio(MunicipioDto municipioDto) throws SystemException, BusinessException, InvalidPassportException {
    return municipioServico.alterar(municipioDto);
  }

  public List<MunicipioDto> buscarMunicipios(ApoioBuscasDto municipioDto) throws SystemException, BusinessException, InvalidPassportException {
    return municipioServico.buscar(municipioDto);
  }
  
  public Integer countMunicipios(ApoioBuscasDto apoioBuscasDto) throws SystemException, BusinessException, InvalidPassportException {
    return municipioServico.count(apoioBuscasDto);
  }
  
  public List<MunicipioDto> buscarPaginasMunicipios(ApoioBuscasDto apoioBuscasDto, Integer inicio, Integer pageSize) throws SystemException, BusinessException, InvalidPassportException {
    return municipioServico.buscarPaginas(apoioBuscasDto, inicio, pageSize);
  }

  public void excluirMunicipio(MunicipioDto municipioDto) throws SystemException, BusinessException, InvalidPassportException {
    municipioServico.excluir(municipioDto);
  }

  public String select() {
    return "OK!!!!!!!!!!!!!!";
  }

}