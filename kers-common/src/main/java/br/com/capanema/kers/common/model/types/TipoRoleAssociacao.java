/*
 * Prodepa (c) 2007 - Processamento de dados do Estado do Par�
 */
package br.com.capanema.kers.common.model.types;

public enum TipoRoleAssociacao {

  SOURCE(1, "Source"), TARGET(2, "Target");

  private int tratamento;
  private String tipo;

  private TipoRoleAssociacao(int tratamento, String tipo) {
    this.tratamento = tratamento;
    this.tipo = tipo;
  }

  public int getTratamento() {
    return tratamento;
  }

  public String getTipo() {
    return tipo;
  }

  public static TipoRoleAssociacao getEnum(String tipo) {
    if (tipo != null) {
      for (TipoRoleAssociacao ad : TipoRoleAssociacao.values()) {
	if (tipo.equals(ad.getTipo()))
	  return ad;
      }
    }
    return null;
  }

  public static boolean contains(String tipo) {
    for (TipoRoleAssociacao ad : TipoRoleAssociacao.values()) {
      if (tipo.equals(ad.getTipo()))
	return true;
    }
    return false;
  }

  public String toString() {
    return this.tipo;
  }

}