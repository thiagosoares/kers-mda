package br.com.capanema.kers.common.model.projectea;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.capanema.kers.common.model.template.DomainSourceType;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author thiago
 *
 */
public class TemplateConfig {

  private LinkedHashMap<String, List> tech;
  private Map<String, List> techLayout;
  
  private String name;
  
  private String nomeSistema;
  //Se n�o informado ser� o nome do sistema
  private String nomeFachada; 

  private String localDestinoProjeto;
  private String localSourceXmi;

  private String packageRoot;

  // Se n�o informado, gerar todos os TODO Fazer
  private String diagramaIteracao;

  private String schemaAplicacao; 
  
  // TODO Para XMI, Se informada gerar
  // somente elas
  private List<String> entidadesStr;

  /* Configuracoes de implementacao */
  private Archetype archetype;
  private DomainSourceType tipoFonteProjeto;
  private TipoArchitetura tipoArquitetura;
  private TipoEmpacotamento tipoEmpacotamento;
  private TipoPlataforma tipoPlataforma;
  private TipoFormulario tipoFormularioWeb;

  private Boolean contemAtivo;
  private Boolean somenteEntidades;

  public TemplateConfig() {
    super();
  }

  public String getNomeSistema() {
    return nomeSistema;
  }

  public void setNomeSistema(String nomeSistema) {
    this.nomeSistema = nomeSistema;
  }

  public String getNomeFachada() {
    return nomeFachada;
  }

  /**
   * Opcional, se n�o informado usar o nome do projeto
   * @param nomeFachada
   */
  public void setNomeFachada(String nomeFachada) {
    this.nomeFachada = nomeFachada;
  }

  public String getLocalDestinoProjeto() {
    return localDestinoProjeto;
  }

  /**
   * Local onde ser� gerados o prejetos. Destino das fontes
   * @param destinoSource
   */
  public void setLocalDestinoProjeto(String destinoSource) {
    this.localDestinoProjeto = destinoSource;
  }

  public TipoArchitetura getTipoArquitetura() {
    return tipoArquitetura;
  }

  /**
   * Configura o tipo de arquitetura que ser� gerado
   * @see {@link br.com.capanema.kers.common.model.projectea.TipoArchitetura}
   * @param tipoArquitetura
   */
  public void setTipoArquitetura(TipoArchitetura tipoArquitetura) {
    this.tipoArquitetura = tipoArquitetura;
  }

  public String getLocalSourceXmi() {
    return localSourceXmi;
  }

  /**
   * Local onde se encontra o xmi do projeto a se gerado
   * @param localXmi
   */
  public void setLocalSourceXmi(String localXmi) {
    this.localSourceXmi = localXmi;
  }

  public String getPackageRoot() {
    return packageRoot;
  }

  /**
   * @Deprecated ser� decoberto automaticamente
   * Informe o pacote root: br.com.capanema
   * @param packageRoot
   */
  @Deprecated
  public void setPackageRoot(String packageRoot) {
    this.packageRoot = packageRoot;
  }

  public TipoFormulario getTipoFormularioWeb() {
    return tipoFormularioWeb;
  }

  /**
   * Configura o tipo de formulario que ser� gerado:<br>
   * @see {@link br.com.capanema.kers.common.model.projectea.TipoFormulario}
   * @param tipoFormulario
   */
  public void setTipoFormularioWeb(TipoFormulario tipoFormulario) {
    this.tipoFormularioWeb = tipoFormulario;
  }

  public Boolean getContemAtivo() {
    return contemAtivo;
  }

  public void setContemAtivo(Boolean contemAtivo) {
    this.contemAtivo = contemAtivo;
  }

  public List<String> getEntidadesStr() {
    return entidadesStr;
  }

  /**
   * Opcional<br>
   * recebe a lista de entidades que ser�o geradas(XMI ou Entidades). Se null todas as entidades ser�o garadas
   * <br>
   * TODO Para gerar apartir das entidades ainda � necessario descobrir os nomes
   * @param entidadesStr
   */
  public void setEntidadesStr(List<String> entidadesStr) {
    this.entidadesStr = entidadesStr;
  }

  public String getDiagramaIteracao() {
    return diagramaIteracao;
  }

  /**
   * TODO dever� suportar Null e gerar para todas <br> 
   * Informa o diagrama da itera��o que ser� contru�da
   * @param diagramaIteracao
   */
  public void setDiagramaIteracao(String diagramaIteracao) {
    this.diagramaIteracao = diagramaIteracao;
  }

  public DomainSourceType getTipoFonteProjeto() {
    return tipoFonteProjeto;
  }

  public void setTipoFonteProjeto(DomainSourceType tipoFonte) {
    this.tipoFonteProjeto = tipoFonte;
  }

  public TipoEmpacotamento getTipoEmpacotamento() {
    return tipoEmpacotamento;
  }

  public void setTipoEmpacotamento(TipoEmpacotamento tipoEmpacotamento) {
    this.tipoEmpacotamento = tipoEmpacotamento;
  }

  public TipoPlataforma getTipoPlataforma() {
    return tipoPlataforma;
  }

  public void setTipoPlataforma(TipoPlataforma tipoPlataforma) {
    this.tipoPlataforma = tipoPlataforma;
  }

  public Archetype getArchetype() {
    return archetype;
  }

  /**
   * Configura o archetype que ser� gerado
   * @see {@link br.com.capanema.kers.common.model.projectea.Archetype}
   * @param archetype
   */
  public void setArchetype(Archetype archetype) {
    this.archetype = archetype;
  }

  public Boolean getSomenteEntidades() {
    return somenteEntidades;
  }

  /**
   * Indica se ser�o geradas somente as entidades ou todo projeto
   * @param somenteEntidades
   */
  public void setSomenteEntidades(Boolean somenteEntidades) {
    this.somenteEntidades = somenteEntidades;
  }

  public String getSchemaAplicacao() {
    return schemaAplicacao;
  }

  public void setSchemaAplicacao(String schemaAplicacao) {
    this.schemaAplicacao = schemaAplicacao;
  }

  public LinkedHashMap<String, List> getTech() {
    return tech;
  }

  public void setTech(LinkedHashMap<String, List> tech) {
    this.tech = tech;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, List> getTechLayout() {
    return techLayout;
  }

  public void setTechLayout(Map<String, List> techLayout) {
    this.techLayout = techLayout;
  }
  
}