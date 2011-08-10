package br.com.capanema.kers.common.model.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.types.TipoDados;

public class Service {

  // EA
  private String idEa;
  private List<Attribute> atributos = new ArrayList<Attribute>();

  // Projeto
  private String importPath;
  private String packageClasse;

  private String nomeClasse;
  private String nomeVarClasse;

  private String nomeDto;
  private String nomeVarDto;

  private String nomeDao;
  private String nomeVarDao;

  private String nomeAction;
  private String nomeVarAction;
  private String nomeSeamAction;
  


  //TODO Remover. Subistituir pelo atributo.
  private List<String> propriedades = new ArrayList<String>();
  private List<String> idsPropriedades = new ArrayList<String>();
  private Map<String, String> mapPropriedades = new HashMap<String, String>();
  private Map<String, TipoDados> mapPropriedades2 = new HashMap<String, TipoDados>();

  // *********** Configuracoes de implementacao **************//
  private boolean pageLista_sem_resultados;

  
  
  public Service() {
  }
  

  /** Acessores **/
  public String getTipoProperty(String prop) {
    return mapPropriedades.get(prop);
  }

  public TipoDados getTipoProperty2(String prop) {
    return mapPropriedades2.get(prop);
  }

  public String getTipoPropertyStr(String prop) {
    return mapPropriedades.get(prop).toString();
  }

  public String getNomeClasse() {
    return nomeClasse;
  }

  public void setNomeClasse(String nomeClasse) {
    this.nomeClasse = nomeClasse;
  }

  public String getPackageClasse() {
    return packageClasse;
  }

  public void setPackageClasse(String packageClasse) {
    this.packageClasse = packageClasse;
  }

  public String getNomeDto() {
    return nomeDto;
  }

  public void setNomeDto(String nomeDto) {
    this.nomeDto = nomeDto;
  }

  public String getNomeVarClasse() {
    return nomeVarClasse;
  }

  public void setNomeVarClasse(String nomeVarClasse) {
    this.nomeVarClasse = nomeVarClasse;
  }

  public String getNomeVarDto() {
    return nomeVarDto;
  }

  public void setNomeVarDto(String nomeVarDto) {
    this.nomeVarDto = nomeVarDto;
  }

  public String getNomeDao() {
    return nomeDao;
  }

  public void setNomeDao(String nomeDao) {
    this.nomeDao = nomeDao;
  }

  public String getNomeVarDao() {
    return nomeVarDao;
  }

  public void setNomeVarDao(String nomeVarDao) {
    this.nomeVarDao = nomeVarDao;
  }

  public List<String> getPropriedades() {
    return propriedades;
  }

  public void setPropriedades(List<String> propriedades) {
    this.propriedades = propriedades;
  }

  public String getNomeAction() {
    return nomeAction;
  }

  public void setNomeAction(String nomeAction) {
    this.nomeAction = nomeAction;
  }

  public String getNomeVarAction() {
    return nomeVarAction;
  }

  public void setNomeVarAction(String nomeVarAction) {
    this.nomeVarAction = nomeVarAction;
  }

  public boolean isPageLista_sem_resultados() {
    return pageLista_sem_resultados;
  }

  public void setPageLista_sem_resultados(boolean pageLista_sem_resultados) {
    this.pageLista_sem_resultados = pageLista_sem_resultados;
  }

  public String getImportPath() {
    return importPath;
  }

  public void setImportPath(String importPath) {
    this.importPath = importPath;
  }

  public String getIdEa() {
    return idEa;
  }

  public void setIdEa(String idEa) {
    this.idEa = idEa;
  }

  public List<String> getIdsPropriedades() {
    return idsPropriedades;
  }

  public void setIdsPropriedades(List<String> idsPropriedades) {
    this.idsPropriedades = idsPropriedades;
  }

  public Map<String, String> getMapPropriedades() {
    return mapPropriedades;
  }

  public void setMapPropriedades(Map<String, String> mapPropriedades) {
    this.mapPropriedades = mapPropriedades;
  }

  public Map<String, TipoDados> getMapPropriedades2() {
    return mapPropriedades2;
  }

  public void setMapPropriedades2(Map<String, TipoDados> mapPropriedades2) {
    this.mapPropriedades2 = mapPropriedades2;
  }

  
  @Override
  public String toString() {
    return "ID: " + idEa;
  }

  public List<Attribute> getAtributos() {
    return atributos;
  }

  public void setAtributos(List<Attribute> atributos) {
    this.atributos = atributos;
  }

public String getNomeSeamAction() {
    return nomeSeamAction;
}

public void setNomeSeamAction(String nomeSeamAction) {
    this.nomeSeamAction = nomeSeamAction;
}

}
