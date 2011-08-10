package br.com.capanema.kers.common.model.domain;

import br.com.capanema.kers.common.model.types.TipoRoleAssociacao;

/**
 * 
 * Classe refatorada do cachorrinho para o kers
 * 
 * @author thiago
 *
 */
public class Attribute {

  private String id_EA;
  private String nome;
  private String tipo;
  private String anotacao; // TODO N�o foi feito ainda
  private String getMetodo;
  private String setMetodo;
  private Boolean isId;
  private Boolean isHerdado;
  private String alias;

  // Caso represente um relacionamento
  private Boolean isRelacionamento;
  private String idEAAssociacao;
  private TipoRoleAssociacao tipoRole;

  public Attribute() {
  }

  public Attribute(String id_EA, String nome, String tipo, Boolean isRelacionamento, String getMetodo, String setMetodo) {
    super();
    this.id_EA = id_EA;
    this.nome = nome;
    this.tipo = tipo;
    this.isRelacionamento = isRelacionamento;
    this.getMetodo = getMetodo;
    this.setMetodo = setMetodo;
  }

  public String getId_EA() {
    return id_EA;
  }

  public void setId_EA(String id_EA) {
    this.id_EA = id_EA;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public Boolean getIsRelacionamento() {
    return isRelacionamento;
  }

  public void setIsRelacionamento(Boolean isRelacionamento) {
    this.isRelacionamento = isRelacionamento;
  }

  public String getGetMetodo() {
    return getMetodo;
  }

  public void setGetMetodo(String getMetodo) {
    this.getMetodo = getMetodo;
  }

  public String getSetMetodo() {
    return setMetodo;
  }

  public void setSetMetodo(String setMetodo) {
    this.setMetodo = setMetodo;
  }

  public String getIdEAAssociacao() {
    return idEAAssociacao;
  }

  public void setIdEAAssociacao(String idEAAssociacao) {
    this.idEAAssociacao = idEAAssociacao;
  }

  public TipoRoleAssociacao getTipoRole() {
    return tipoRole;
  }

  public void setTipoRole(TipoRoleAssociacao tipoRole) {
    this.tipoRole = tipoRole;
  }

  public String getAnotacao() {
    return anotacao;
  }

  public void setAnotacao(String anotacao) {
    this.anotacao = anotacao;
  }

  public Boolean getIsId() {
    return isId;
  }

  public void setIsId(Boolean isId) {
    this.isId = isId;
  }

  public Boolean getIsHerdado() {
    return isHerdado;
  }

  public void setIsHerdado(Boolean isHerdado) {
    this.isHerdado = isHerdado;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  @Override
  public String toString() {
    return tipo + "." + nome;
  }

}
