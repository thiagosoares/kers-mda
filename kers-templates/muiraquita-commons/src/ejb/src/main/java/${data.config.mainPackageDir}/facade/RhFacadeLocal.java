package br.gov.pa.muiraquita.sample.rh.facade;

import java.util.List;

import javax.ejb.Local;

import br.gov.pa.muiraquita.exception.BusinessException;
import br.gov.pa.muiraquita.exception.SystemException;
import br.gov.pa.muiraquita.exception.security.InvalidPassportException;
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

@Local
public interface RhFacadeLocal {

  /**
   * 
   * Assinaturas de Seguranca
   * @return
   * @throws BusinessException
   * 
   */
  public LoggedUserDto login(String user, String password) throws BusinessException;

  public void logout(String passaport);

  public void alterarSenha(AlterarSenhaDto alterarSenhaDto) throws BusinessException;

  /**
   * 
   * Assinaturas de Beneficio
   * 
   */
  public BeneficioDto cadastrarBeneficio(BeneficioDto beneficioDto) throws SystemException, BusinessException, InvalidPassportException;

  public BeneficioDto alterarBeneficio(BeneficioDto beneficioDto) throws SystemException, BusinessException, InvalidPassportException;

  public List<BeneficioDto> buscarBeneficios(BeneficioDto beneficioDto) throws SystemException, BusinessException, InvalidPassportException;

  public void excluirBeneficio(BeneficioDto beneficioDto) throws SystemException, BusinessException, InvalidPassportException;

  /**
   * 
   * Assinaturas de Departamento
   * 
   */
  public DepartamentoDto cadastrarDepartamento(DepartamentoDto departamentoDto) throws SystemException, BusinessException, InvalidPassportException;

  public DepartamentoDto alterarDepartamento(DepartamentoDto departamentoDto) throws SystemException, BusinessException, InvalidPassportException;

  public List<DepartamentoDto> buscarDepartamentos(DepartamentoDto departamentoDto) throws SystemException, BusinessException, InvalidPassportException;

  public void excluirDepartamento(DepartamentoDto departamentoDto) throws SystemException, BusinessException, InvalidPassportException;

  /**
   * 
   * Assinaturas de Dependente
   * 
   */
  public DependenteDto cadastrarDependente(DependenteDto dependenteDto) throws SystemException, BusinessException, InvalidPassportException;

  public DependenteDto alterarDependente(DependenteDto dependenteDto) throws SystemException, BusinessException, InvalidPassportException;

  public List<DependenteDto> buscarDependentes(DependenteDto dependenteDto) throws SystemException, BusinessException, InvalidPassportException;

  public void excluirDependente(DependenteDto dependenteDto) throws SystemException, BusinessException, InvalidPassportException;

  /**
   * 
   * Assinaturas de Empresa
   * 
   */
  public EmpresaDto cadastrarEmpresa(EmpresaDto empresaDto) throws SystemException, BusinessException, InvalidPassportException;

  public EmpresaDto alterarEmpresa(EmpresaDto empresaDto) throws SystemException, BusinessException, InvalidPassportException;

  public List<EmpresaDto> buscarEmpresas(EmpresaDto empresaDto) throws SystemException, BusinessException, InvalidPassportException;

  public void excluirEmpresa(EmpresaDto empresaDto) throws SystemException, BusinessException, InvalidPassportException;

  /**
   * 
   * Assinaturas de Estado
   * 
   */
  public EstadoDto cadastrarEstado(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException;

  public EstadoDto alterarEstado(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException;

  public List<EstadoDto> buscarEstados(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException;

  public void excluirEstado(EstadoDto estadoDto) throws SystemException, BusinessException, InvalidPassportException;

  /**
   * 
   * Assinaturas de Funcionario
   * 
   */
  public FuncionarioDto cadastrarFuncionario(FuncionarioDto funcionarioDto) throws SystemException, BusinessException, InvalidPassportException;

  public FuncionarioDto alterarFuncionario(FuncionarioDto funcionarioDto) throws SystemException, BusinessException, InvalidPassportException;

  public List<FuncionarioDto> buscarFuncionarios(FuncionarioDto funcionarioDto) throws SystemException, BusinessException, InvalidPassportException;

  public void excluirFuncionario(FuncionarioDto funcionarioDto) throws SystemException, BusinessException, InvalidPassportException;

  /**
   * 
   * Assinaturas de HistoricoFuncional
   * 
   */
  public HistoricoFuncionalDto cadastrarHistoricoFuncional(HistoricoFuncionalDto historicoFuncionalDto) throws SystemException, BusinessException, InvalidPassportException;

  public HistoricoFuncionalDto alterarHistoricoFuncional(HistoricoFuncionalDto historicoFuncionalDto) throws SystemException, BusinessException, InvalidPassportException;

  public List<HistoricoFuncionalDto> buscarHistoricoFuncionals(HistoricoFuncionalDto historicoFuncionalDto) throws SystemException, BusinessException, InvalidPassportException;

  public void excluirHistoricoFuncional(HistoricoFuncionalDto historicoFuncionalDto) throws SystemException, BusinessException, InvalidPassportException;

  /**
   * 
   * Assinaturas de Municipio
   * 
   */
  public MunicipioDto cadastrarMunicipio(MunicipioDto municipioDto) throws SystemException, BusinessException, InvalidPassportException;

  public MunicipioDto alterarMunicipio(MunicipioDto municipioDto) throws SystemException, BusinessException, InvalidPassportException;

  public List<MunicipioDto> buscarMunicipios(ApoioBuscasDto municipioDto) throws SystemException, BusinessException, InvalidPassportException;
  
  public Integer countMunicipios(ApoioBuscasDto apoioBuscasDto) throws SystemException, BusinessException, InvalidPassportException;
  
  public List<MunicipioDto> buscarPaginasMunicipios(ApoioBuscasDto apoioBuscasDto, Integer inicio, Integer pageSize) throws SystemException, BusinessException, InvalidPassportException;

  public void excluirMunicipio(MunicipioDto municipioDto) throws SystemException, BusinessException, InvalidPassportException;

  // Testes
  public String select();
}