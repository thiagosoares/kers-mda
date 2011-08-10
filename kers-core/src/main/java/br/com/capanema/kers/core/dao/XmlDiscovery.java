package br.com.capanema.kers.core.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import br.com.capanema.kers.common.model.domain.Association;
import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.domain.Diagram;
import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.domain.Generalization;
import br.com.capanema.kers.common.model.types.TipoRoleAssociacao;
import br.com.capanema.kers.common.util.string.StringUtil;

public class XmlDiscovery {

  private XMLConfiguration config;
  private String diagramaIteracao;

  private static final String TAG_ELEMENTOS_CLASSE = "xmi:Extension.elements.element";
  private static final String TAG_ELEMENTOS_ASSOCIACAO = "xmi:Extension.connectors.connector";
  private static final String TAG_ELEMENTOS_DIAGRAMAS = "xmi:Extension.diagrams.diagram";
  private static final String TAG_ELEMENTOS_PACOTES = "uml:Model.packagedElement";

  public XmlDiscovery(String localXmi, String diagramaIteracao) throws ConfigurationException {
    super();
    this.config = new XMLConfiguration(localXmi);
    this.diagramaIteracao = diagramaIteracao;
  }

  @SuppressWarnings("unchecked")
  public List<Entity> readClasses() {

    List<Entity> classes = new ArrayList<Entity>();

    Object elementos = config.getProperty(TAG_ELEMENTOS_CLASSE + ".[@xmi:type]");
    List<String> elementosLista = new ArrayList<String>();

    List<Diagram> diagramas = readDiagrams(diagramaIteracao);

    if (elementos != null) {
      if (elementos instanceof Collection<?>) {
        elementosLista = (List<String>) elementos;
      } else {
        elementosLista.add(elementos.toString());
      }

      /** Descoberta da classes **/
      // Percorre a lista de elementos encontrados
      for (int i = 0; i < elementosLista.size() + 1; i++) {

        Entity classe = new Entity();
        classe.setContemId(false);
        if (config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").[@xmi:type]") != null
                && config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").[@xmi:type]").equals("uml:Class")
                && config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").extendedProperties.[@package_name]") != null
                && config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").extendedProperties.[@package_name]").equals("entity")
                && config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").[@name]") != null) {

          System.out.println("\t\t Classe:" + StringUtil.toClasseNameFormat(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").[@name]")));
          if (isContidoDiagrama(diagramas, config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").[@xmi:idref]"))) {

            System.out.println(StringUtil.toClasseNameFormat(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").[@name]")));
            
            if (config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").properties.[@stereotype]") == null
                    || !config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").properties.[@stereotype]").equals("enumeration")) {

              System.out.println(StringUtil.toClasseNameFormat(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").[@name]")));
    
              classe.setIdEa(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").[@xmi:idref]"));
              classe.setIdEAPackage(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").model.[@package]"));

              // Setar nome da classe
              classe.setNomeEntidade(StringUtil.toClasseNameFormat(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").[@name]")));
              classe.setNomeVarEntidade(StringUtil.toVariavelFormat(classe.getNomeEntidade()));
              classe.setNomeDto(classe.getNomeEntidade() + "Dto");
              classe.setNomeVarDto(classe.getNomeVarEntidade() + "Dto");
              classe.setNomeDao(classe.getNomeEntidade() + "Dao");
              classe.setNomeVarDao(classe.getNomeVarEntidade() + "Dao");
              classe.setNomeSeam(classe.getNomeEntidade() + "Seam");
              classe.setNomeVarAction(classe.getNomeVarEntidade() + "Action");
              classe.setNomeSeamAction(classe.getNomeVarEntidade() + "Action");
              classe.setIsSuperTipo(null);
              classe.setIsEnum(false);

              Object atributos = config.getProperty(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute.[@name]");

              /** Descoberta dos atributos **/
              List<String> atributosLista = new ArrayList<String>();
              if (atributos != null) {
                if (atributos instanceof Collection<?>) {
                  atributosLista = (List<String>) atributos;
                } else {
                  atributosLista.add(atributos.toString());
                }
              }

              // percorre os atributos das classe e os seta no objeto
              for (int j = 0; j < atributosLista.size(); j++) {
                Attribute atr = new Attribute();

                // Cria e adiona o atributo
                atr.setId_EA(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").[@xmi:idref]"));
                atr.setNome(StringUtil.toVariavelFormat(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").[@name]")));

                if (config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").properties.[@type]") != null) {
                  atr.setTipo(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").properties.[@type]"));
                } else {
                  atr.setTipo("String");
                }

                String s1 = atr.getNome().substring(0, 1).toUpperCase();
                String s2 = atr.getNome().substring(1);
                atr.setGetMetodo("get" + s1 + s2);
                atr.setSetMetodo("set" + s1 + s2);
                atr.setIsRelacionamento(false);
                atr.setIsHerdado(false);

                // Descobrir o ID
                if (config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").stereotype.[@stereotype]") != null) {
                  if (config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").stereotype.[@stereotype]").toLowerCase().equals("id")
                          || config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").stereotype.[@stereotype]").toLowerCase().equals("idfield")
                          || config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").stereotype.[@stereotype]").toLowerCase().equals("identificador")) {
                    atr.setIsId(true);
                  } else {
                    atr.setIsId(false);
                  }
                } else {
                  atr.setIsId(false);
                }
                if (atr.getIsId()) {
                  atr.setAnotacao("@Id \n @GeneratedValue(strategy=GenerationType.IDENTITY) \n @Column(name = \""
                          + StringUtil.toTableSintaxFormt(atr.getNome()) + "\", insertable = false, updatable = false)");
                  classe.setContemId(true);
                } else {
                  atr.setAnotacao("@Column(name = \"" + StringUtil.toTableSintaxFormt(atr.getNome()) + "\")");
                }

                classe.getAtributos().add(atr);

              }

            } else if (config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").properties.[@stereotype]").equals("enumeration")) {

              System.out.print("Emunnnnnnnnnnnnnnnnnnnn!!!!!!!  ");
              System.out.println("NOME::::: " + StringUtil.toClasseNameFormat(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").[@name]")));
              classe.setIdEa(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").[@xmi:idref]"));
              classe.setIdEAPackage(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").model.[@package]"));

              // Setar nome da classe
              classe.setNomeEntidade(StringUtil.toClasseNameFormat(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").[@name]")));
              classe.setNomeVarEntidade(StringUtil.toVariavelFormat(classe.getNomeEntidade()));
              
              classe.setIsSuperTipo(null);
              classe.setIsEnum(true);

              Object atributos = config.getProperty(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute.[@name]");

              /** Descoberta dos atributos **/
              List<String> atributosLista = new ArrayList<String>();
              if (atributos != null) {
                if (atributos instanceof Collection<?>) {
                  atributosLista = (List<String>) atributos;
                } else {
                  atributosLista.add(atributos.toString());
                }
              }

              // percorre os atributos das classe e os seta no objeto
              for (int j = 0; j < atributosLista.size(); j++) {
                if (config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").stereotype.[@stereotype]") != null) {
                  if (config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").stereotype.[@stereotype]").toLowerCase().equals("enum")) {

                    Attribute atr = new Attribute();

                    // Cria e adiona o atributo
                    atr.setId_EA(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").[@xmi:idref]"));
                    atr.setNome(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").[@name]"));
                    if(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute.initial.[@body]") != null) {
                      atr.setAlias(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute.initial.[@body]"));
                    } else {
                      atr.setAlias(atr.getNome().toLowerCase());
                    }
                    
                    if (config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").properties.[@type]") != null) {
                      atr.setTipo(config.getString(TAG_ELEMENTOS_CLASSE + "(" + i + ").attributes.attribute(" + j + ").properties.[@type]"));
                    } else {
                      atr.setTipo("String");
                    }

                    String s1 = atr.getNome().substring(0, 1).toUpperCase();
                    String s2 = atr.getNome().substring(1);
                    atr.setGetMetodo("get" + s1 + s2);
                    atr.setSetMetodo("set" + s1 + s2);
                    atr.setIsRelacionamento(false);
                    atr.setIsHerdado(false);

                    atr.setIsId(false);
                    atr.setAnotacao(" @Enumerated \n@Column(name = \"" + StringUtil.toTableSintaxFormt(atr.getNome()) + "\")");

                    classe.getAtributos().add(atr);

                  }
                }
              }
            }

            classes.add(classe);
          }
        }
      }

      /** Descoberta dos relacionamentos **/
      classes = setarPropriedadesAssociacao(classes);

      /** Descoberta das generalizacoes **/
      classes = setarPropriedadesGeneralizacao(classes);

      diagramas.clear();

      return classes;

    } else {
      return null;
    }
  }

  public List<Association> readAssociacoes() {

    Object elementos = config.getProperty(TAG_ELEMENTOS_ASSOCIACAO + ".properties.[@ea_type]");
    List<String> elementosLista = new ArrayList<String>();
    List<Association> associacoes = new ArrayList<Association>();

    if (elementos != null) {
      if (elementos instanceof Collection<?>) {
        elementosLista = (List<String>) elementos;
      } else {
        elementosLista.add(elementos.toString());
      }

      /** Descoberta das associacoes **/
      // Percorre a lista de elementos encontrados
      for (int i = 0; i < elementosLista.size(); i++) {
        if (config.getProperty(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").properties.[@ea_type]").equals("Association")
                && config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.model.[@type]").equals("Class")) {

          Association ass = new Association();

          ass.setIdEA(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").[@xmi:idref]"));

          if (!config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").properties.[@direction]").equals("Unspecified")) {
            ass.setDirection(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").properties.[@direction]"));
          } else {
            ass.setDirection("Source -> Destination");
          }

          // Contigurar Source
          ass.setIdSource(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.[@xmi:idref]"));
          ass.setNomeSource(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.model.[@name]"));
          ass.setTipoSource(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.model.[@type]"));
          ass.setNomeRoleSource(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.role.[@name]"));
          // ass.setIsAgregacaoSource(config.getBoolean(TAG_ELEMENTOS_ASSOCIACAO
          // + "(" + i + ").source.type.[@aggregation]"));
          if (config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.type.[@multiplicity]") != null) {
            ass.setMultiplicidadeSource(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.type.[@multiplicity]"));
          } else {
            ass.setMultiplicidadeSource("1");
          }
          ass.setIsNavegavelSource(config.getBoolean(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.modifiers.[@isNavigable]"));

          // Contigurar Target
          ass.setIdTarget(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.[@xmi:idref]"));
          ass.setNomeTarget(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.model.[@name]"));
          ass.setTipoTarget(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.model.[@type]"));
          ass.setNomeRoleTarget(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.role.[@name]"));
          // ass.setIsAgregacaoTarget(config.getBoolean(TAG_ELEMENTOS_ASSOCIACAO
          // + "(" + i + ").target.type.[@aggregation]"));
          if (config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.type.[@multiplicity]") != null) {
            ass.setMultiplicidadeTarget(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.type.[@multiplicity]"));
          } else {
            ass.setMultiplicidadeTarget("1");
          }
          ass.setIsNavegavelTarget(config.getBoolean(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.modifiers.[@isNavigable]"));

          associacoes.add(ass);
        }
      }
    }

    return associacoes;
  }

  private List<Entity> setarPropriedadesAssociacao(List<Entity> entidades) {
    List<Association> associacoes = readAssociacoes();

    // Adicionar Propriedades de associacoes
    // para cada associacao, buscar as classes e adicionar a propriedade
    for (Association associacao : associacoes) {

      System.out.println("add Associacao : " + associacao.getNomeSource() + " | " + associacao.getNomeTarget());
      // Busca a classe Source
      Entity source = null;
      for (Entity entidade : entidades) {
        if (entidade.getIdEa().equals(associacao.getIdSource())) {
          source = entidade;
          break;
        }
      }

      // Busca a classe Target
      Entity target = null;
      for (Entity entidade : entidades) {
        if (entidade.getIdEa().equals(associacao.getIdTarget())) {
          target = entidade;
          break;
        }
      }

      String entidadeTarget = StringUtil.toClasseNameFormat(associacao.getNomeTarget());
      String varEntidadeTarget =
              associacao.getNomeRoleTarget() != null ? associacao.getNomeRoleTarget() : StringUtil.toVariavelFormat(associacao.getNomeTarget());
      String entidadeSource = StringUtil.toClasseNameFormat(associacao.getNomeSource());
      String varEntidadeSource =
              associacao.getNomeRoleSource() != null ? associacao.getNomeRoleSource() : StringUtil.toVariavelFormat(associacao.getNomeSource());

      System.out.println(varEntidadeTarget + " ###### " + varEntidadeSource);

      if (source != null) {

        // Setar propriedades
        // Source
        if (associacao.getIsNavegavelTarget()) {

          // Verificar se ser� uma propriedade ou uma lista
          if (associacao.getMultiplicidadeTarget().equals("1")) {
            // Cria e adiona o atributo
            Attribute atr = new Attribute();
            atr.setIsId(false);
            atr.setIsHerdado(false);

            // atr.setId_EA();
            atr.setNome(varEntidadeTarget);
            atr.setTipo(entidadeTarget);
            String s1 = atr.getNome().substring(0, 1).toUpperCase();
            String s2 = atr.getNome().substring(1);
            atr.setGetMetodo("get" + s1 + s2);
            atr.setSetMetodo("set" + s1 + s2);

            if (target != null && target.getIsEnum()) {
              atr.setAnotacao("@Enumerated @Column(name=\"ID_" + entidadeTarget.toUpperCase() + "\")");
            } else {
              if (associacao.getMultiplicidadeSource().equals("1")) { // OneToOne
                atr.setAnotacao("@OneToOne @JoinColumn(name=\"ID_" + entidadeTarget.toUpperCase() + "\")");
              } else { // Pouco prov�vel
                atr.setAnotacao("@ManyToOne @JoinColumn(name=\"ID_" + entidadeTarget.toUpperCase() + "\")");
              }
            }

            atr.setIsRelacionamento(true);
            atr.setIdEAAssociacao(associacao.getIdEA());
            atr.setTipoRole(TipoRoleAssociacao.SOURCE);

            source.getAtributos().add(atr);

          } else { // Sen�o � 0 ou 1, � lista .
            if (!associacao.getMultiplicidadeTarget().equals("0")) {
              // Cria e adiona o atributo
              Attribute atr = new Attribute();
              atr.setIsId(false);
              atr.setIsHerdado(false);

              // verifica se foi definido o nome da role
              if (associacao.getNomeRoleTarget() != null) {
                atr.setNome(associacao.getNomeRoleTarget());
              } else {
                atr.setNome(varEntidadeTarget + "Lista");
              }
              atr.setTipo("List<" + entidadeTarget + ">");
              String s1 = atr.getNome().substring(0, 1).toUpperCase();
              String s2 = atr.getNome().substring(1);
              atr.setGetMetodo("get" + s1 + s2);
              atr.setSetMetodo("set" + s1 + s2);

              if (target != null && target.getIsEnum()) {
                atr.setAnotacao("@Enumerated @Column(name=\"ID_" + entidadeTarget.toUpperCase() + "\")");
              } else {
                if (associacao.getMultiplicidadeSource().equals("1")) { // Um
                                                                        // pra
                                                                        // muitos
                  atr.setAnotacao("@OneToMany(mappedBy = \"" + varEntidadeSource + "\")");
                } else {
                  atr.setAnotacao("@ManyToMany \n" + "\t@JoinTable(name = \"" + entidadeSource.toLowerCase() + "_" + entidadeTarget.toLowerCase()
                          + ", schema = $schema "  
                          + "\", \n" + "\t\tjoinColumns = { @JoinColumn(name = \"id_" + StringUtil.toTableSintaxFormt(entidadeSource)
                          + "\", referencedColumnName = \"id_" + StringUtil.toTableSintaxFormt(entidadeSource) + "\") }, \n"
                          + "\t\tinverseJoinColumns = { @JoinColumn(name = \"id_" + StringUtil.toTableSintaxFormt(entidadeTarget)
                          + "\", referencedColumnName = \"id_" + StringUtil.toTableSintaxFormt(entidadeTarget) + "\") } \n" + "\t)");
                }
              }

              atr.setIsRelacionamento(true);
              atr.setIdEAAssociacao(associacao.getIdEA());
              atr.setTipoRole(TipoRoleAssociacao.SOURCE);

              source.getAtributos().add(atr);
            }
          }
        }

        // target
        if (associacao.getIsNavegavelSource() && target != null) {

          // Verificar se ser� uma propriedade ou uma lista
          if (associacao.getMultiplicidadeSource().equals("1")) {
            // Cria e adiona o atributo
            Attribute atr = new Attribute();
            atr.setIsId(false);
            atr.setIsHerdado(false);

            atr.setNome(varEntidadeSource);
            atr.setTipo(entidadeSource);
            String s1 = atr.getNome().substring(0, 1).toUpperCase();
            String s2 = atr.getNome().substring(1);
            atr.setGetMetodo("get" + s1 + s2);
            atr.setSetMetodo("set" + s1 + s2);

            if (associacao.getMultiplicidadeTarget().equals("1")) {
              atr.setAnotacao("@OneToOne(mappedBy = \"" + varEntidadeTarget + "\")");
            } else {
              atr.setAnotacao("@ManyToOne @JoinColumn(name=\"ID_" + entidadeSource.toUpperCase() + "\")");
            }

            atr.setIsRelacionamento(true);
            atr.setIdEAAssociacao(associacao.getIdEA());
            atr.setTipoRole(TipoRoleAssociacao.TARGET);

            target.getAtributos().add(atr);

          } else { // Sen�o � 0 ou 1, � lista
            if (!associacao.getMultiplicidadeSource().equals("0")) {
              // Cria e adiona o atributo
              Attribute atr = new Attribute();
              atr.setIsId(false);
              atr.setIsHerdado(false);

              if (associacao.getNomeRoleSource() != null) {
                atr.setNome(associacao.getNomeRoleSource());
              } else {
                atr.setNome(varEntidadeSource + "Lista");
              }
              atr.setTipo("List<" + entidadeSource + ">");
              String s1 = atr.getNome().substring(0, 1).toUpperCase();
              String s2 = atr.getNome().substring(1);
              atr.setGetMetodo("get" + s1 + s2);
              atr.setSetMetodo("set" + s1 + s2);

              if (associacao.getMultiplicidadeTarget().equals("1")) {
                atr.setAnotacao("@OneToMany(mappedBy = \"" + varEntidadeTarget + "\")");
              } else { // Muitos para muitos
                atr.setAnotacao("@ManyToMany \n\t" + "@JoinTable(name = \"" + entidadeSource.toLowerCase() + "_" + entidadeTarget.toLowerCase()
                        + ", schema = $schema "  
                        + "\", \n" + "\t\tjoinColumns = { @JoinColumn(name = \"id_" + StringUtil.toTableSintaxFormt(entidadeSource)
                        + "\", referencedColumnName = \"id_" + StringUtil.toTableSintaxFormt(entidadeSource) + "\") }, \n"
                        + "\t\tinverseJoinColumns = { @JoinColumn(name = \"id_" + StringUtil.toTableSintaxFormt(entidadeTarget)
                        + "\", referencedColumnName = \"id_" + StringUtil.toTableSintaxFormt(entidadeTarget) + "\") } \n" + "\t)");
              }

              atr.setIsRelacionamento(true);
              atr.setIdEAAssociacao(associacao.getIdEA());
              atr.setTipoRole(TipoRoleAssociacao.TARGET);

              target.getAtributos().add(atr);
            }
          }

        }
      }

    }

    return entidades;
  }

  public List<Generalization> readGerneralizacoes() {

    Object elementos = config.getProperty(TAG_ELEMENTOS_ASSOCIACAO + ".properties.[@ea_type]");
    List<String> elementosLista = new ArrayList<String>();
    List<Generalization> generalizacoes = new ArrayList<Generalization>();

    if (elementos != null) {
      if (elementos instanceof Collection<?>) {
        elementosLista = (List<String>) elementos;
      } else {
        elementosLista.add(elementos.toString());
      }

      /** Descoberta das associacoes **/
      // Percorre a lista de elementos encontrados
      for (int i = 0; i < elementosLista.size(); i++) {
        if (config.getProperty(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").properties.[@ea_type]").equals("Generalization")
                && config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.model.[@type]").equals("Class")) {

          Generalization ass = new Generalization();

          ass.setIdEA(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").[@xmi:idref]"));

          if (!config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").properties.[@direction]").equals("Unspecified")) {
            ass.setDirection(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").properties.[@direction]"));
          } else {
            ass.setDirection("Source -> Destination");
          }

          // Contigurar Source
          ass.setIdSource(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.[@xmi:idref]"));
          ass.setNomeSource(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.model.[@name]"));
          ass.setTipoSource(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.model.[@type]"));
          ass.setNomeRoleSource(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.role.[@name]"));
          // ass.setIsAgregacaoSource(config.getBoolean(TAG_ELEMENTOS_ASSOCIACAO
          // + "(" + i + ").source.type.[@aggregation]"));
          if (config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.type.[@multiplicity]") != null) {
            ass.setMultiplicidadeSource(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.type.[@multiplicity]"));
          } else {
            ass.setMultiplicidadeSource("1");
          }
          ass.setIsNavegavelSource(config.getBoolean(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").source.modifiers.[@isNavigable]"));

          // Contigurar Target
          ass.setIdTarget(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.[@xmi:idref]"));
          ass.setNomeTarget(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.model.[@name]"));
          ass.setTipoTarget(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.model.[@type]"));
          ass.setNomeRoleTarget(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.role.[@name]"));
          // ass.setIsAgregacaoTarget(config.getBoolean(TAG_ELEMENTOS_ASSOCIACAO
          // + "(" + i + ").target.type.[@aggregation]"));
          if (config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.type.[@multiplicity]") != null) {
            ass.setMultiplicidadeTarget(config.getString(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.type.[@multiplicity]"));
          } else {
            ass.setMultiplicidadeTarget("1");
          }
          ass.setIsNavegavelTarget(config.getBoolean(TAG_ELEMENTOS_ASSOCIACAO + "(" + i + ").target.modifiers.[@isNavigable]"));

          generalizacoes.add(ass);
        }
      }
    }

    return generalizacoes;
  }

  private List<Entity> setarPropriedadesGeneralizacao(List<Entity> entidades) {
    List<Generalization> generalizacoes = readGerneralizacoes();

    // Adicionar Propriedades de associacoes
    // para cada associacao, buscar as classes e adicionar a propriedade
    for (Generalization associacao : generalizacoes) {
      // Busca a classe Source
      Entity source = null;
      for (Entity entidade : entidades) {
        if (entidade.getIdEa().equals(associacao.getIdSource())) {
          source = entidade;
          break;
        }
      }

      // Busca a classe Target
      Entity target = null;
      for (Entity entidade : entidades) {
        if (entidade.getIdEa().equals(associacao.getIdTarget())) {
          target = entidade;
          break;
        }
      }

      if (source != null && target != null) {

        System.out.println("Source: " + source.getNomeEntidade() + " target: " + target.getNomeEntidade());

        target.setIsSuperTipo(true);
        source.setIsSuperTipo(false);
        source.setNomeSuperTipo(target.getNomeEntidade());

        // Setar propriedades
        for (Attribute atr : target.getAtributos()) {
          atr.setIsHerdado(true);
          source.getAtributos().add(atr);

        }
      }

    }

    return entidades;
  }

  private String getTreePackage(String idPackage) {
    String tree = "";

    Object elementos = config.getProperty(TAG_ELEMENTOS_PACOTES + ".[@xmi:type]");
    List<String> elementosLista = new ArrayList<String>();

    if (elementos != null) {
      if (elementos instanceof Collection<?>) {
        elementosLista = (List<String>) elementos;
      } else {
        elementosLista.add(elementos.toString());
      }

      /** Descoberta da classes **/
      // Percorre a lista de elementos encontrados
      for (int i = 0; i < elementosLista.size() + 1; i++) {
        String tag_pacote = ".packagedElement";
        int nroNodes = 0;
        String pac = TAG_ELEMENTOS_PACOTES + tag_pacote;
        if (config.getString(TAG_ELEMENTOS_PACOTES + ".[@xmi:type]").equals("uml:Package")) {
          pac += tag_pacote;
          while (config.getString(TAG_ELEMENTOS_PACOTES + ".[@xmi:type]") != null) {

          }

        }
      }
    }

    return tree;
  }

  public List<Diagram> readDiagrams(String diagramaIteracao) {

    Object elementosXml = config.getProperty(TAG_ELEMENTOS_DIAGRAMAS + ".properties.[@type]");
    List<String> elementosLista = new ArrayList<String>();
    List<Diagram> diagramas = new ArrayList<Diagram>();

    if (elementosXml != null) {
      if (elementosXml instanceof Collection<?>) {
        elementosLista = (List<String>) elementosXml;
      } else {
        elementosLista.add(elementosXml.toString());
      }

      /** Descoberta das associacoes **/
      // Percorre a lista de elementos encontrados
      for (int i = 0; i < elementosLista.size(); i++) {
        if (diagramaIteracao == null || config.getProperty(TAG_ELEMENTOS_DIAGRAMAS + "(" + i + ").properties.[@name]").equals(diagramaIteracao)) {

          Diagram diag = new Diagram();

          Object elementosDiagramaXml = config.getProperty(TAG_ELEMENTOS_DIAGRAMAS + "(" + i + ").elements.element.[@subject]");
          List<String> elementosDiagrama = new ArrayList<String>();

          if (elementosDiagramaXml != null) {
            if (elementosDiagramaXml instanceof Collection<?>) {
              elementosDiagrama = (List<String>) elementosDiagramaXml;
            } else {
              elementosDiagrama.add(elementosXml.toString());
            }

            for (int j = 0; j < elementosDiagrama.size(); j++) {
              diag.getElementos().add(config.getString(TAG_ELEMENTOS_DIAGRAMAS + "(" + i + ").elements.element(" + j + ").[@subject]"));
            }

          }
          System.out.println(diag.getElementos().size());
          diagramas.add(diag);
        }
      }
    }

    return diagramas;
  }

  public boolean isContidoDiagrama(List<Diagram> diagramas, String idClass) {

    for (Diagram diagrama : diagramas) {
      if (diagrama.getElementos().contains(idClass)) {
        return true;
      }
    }
    return false;
  }

  public void clearConfig() {
    this.config.clear();
  }

}
