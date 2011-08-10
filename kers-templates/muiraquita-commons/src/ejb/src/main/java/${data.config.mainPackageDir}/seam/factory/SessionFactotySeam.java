package br.gov.pa.muiraquita.sample.rh.seam.factory;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.gov.pa.muiraquita.seam.AbstractSeam;

@Name("sessionFactorySeam")
@Scope(ScopeType.SESSION)
public class SessionFactotySeam extends AbstractSeam {

  /*
   * Factorys de Enums
   */

  /*
   * @Factory("tipoMovimentacaoDevolucao") public TipoMovimentacao[]
   * tipoMovimentacoesDevolucao() {
   * if(!sessionInformations.getUsuarioSispatDto()
   * .getEntidadeAdministrativa().getOrgao
   * ().getTipoAdministracao().equals(TipoAdministracao.DIRETA)) return new
   * TipoMovimentacao[]{TipoMovimentacao.RetornoPorCessao}; else return new
   * TipoMovimentacao
   * []{TipoMovimentacao.RetornoPorCessao,TipoMovimentacao.RetornoPorEmprestimo
   * }; }
   */

}
