package br.com.capanema.kers.core.factory.pages.list;

import java.io.IOException;

import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.common.model.projectea.TipoFormulario;
import br.com.capanema.kers.common.model.types.TipoDados;
import br.com.capanema.kers.common.util.string.StringUtil;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorBuscasMuiraquita extends CalangoFactory {

  public static void build(EAProjectConfig projeto) throws IOException {

    for (Entity entidade : projeto.getEntidades()) {
      if (!entidade.getIsEnum()) {
        String nomePagina = StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "_lista.xhtml";

        String caminhoPaginas = projeto.getDestinoView() + projeto.getNomeSistema().toLowerCase() + "/" 
                          + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade());

        StringBuffer srtPage = new StringBuffer();

        srtPage.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> \n");
        srtPage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n");
        srtPage.append("<ui:composition  xmlns=\"http://www.w3.org/1999/xhtml\"" + " \n \t xmlns:ui=\"http://java.sun.com/jsf/facelets\""
                + " \n \t xmlns:h=\"http://java.sun.com/jsf/html\"" + " \n\t xmlns:f=\"http://java.sun.com/jsf/core\""
                + " \n\t xmlns:rich=\"http://richfaces.org/rich\" " + " \n\t template=\"../../layout/t_default_1.xhtml\"> " + "\n\t");
        srtPage.append("<ui:define name=\"titulo\">" + StringUtil.toLabelSintaxFormt(entidade.getNomeEntidade()) + "</ui:define> \n\t");
        srtPage.append("<ui:define name=\"caminho\">" + StringUtil.toLabelSintaxFormt(entidade.getNomeEntidade()) + " - Cadastro</ui:define> \n\t");
        srtPage.append("<ui:define name=\"pesquisa\"> \n\t\t");
        srtPage.append("<h:form id=\"" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "_form_pesq\"> \n\t\t\t");
        srtPage.append("<ui:include src=\"include/" + entidade.getNomeEntidade().toLowerCase() + "_search.xhtml\" /> \n\t\t");
        srtPage.append("</h:form> \n\t");
        srtPage.append("</ui:define> \n\t");

        srtPage.append("<ui:define name=\"conteudo1\"> \n\t\t");
        srtPage.append("<h:form id=\"" + entidade.getNomeEntidade().toLowerCase() + "_form_list\"> \n\t\t\t");
        srtPage.append("<ui:include src=\"include/" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "_list.xhtml\" /> \n\t\t\t");
        srtPage.append("<ui:include src=\"include/" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "_delete.xhtml\" /> \n\t\t");
        srtPage.append("</h:form> \n\t\t");
        srtPage.append("<ui:include src=\"include/" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "_view.xhtml\" /> \n\t");
        srtPage.append("</ui:define>\n");
        srtPage.append("</ui:composition> \n");

        FileManager.createFife(nomePagina, caminhoPaginas, srtPage.toString());

        gerarIncludePesquisa(projeto, entidade);
        gerarIncludeLista(projeto, entidade);
        gerarIncludeConsulta(projeto, entidade);

        // Rotina pata gerar os campos
        if (projeto.getTipoFormulario() == TipoFormulario.SEAM_PATTERN) {
          gerarIncludeExcluirSeamPattern(projeto, entidade);
        } else if (projeto.getTipoFormulario() == TipoFormulario.PRODEPA_PATTERN) {
          gerarIncludeExcluirProdepaPattern(projeto, entidade);
        } else {
          gerarIncludeExcluirProdepaPattern(projeto, entidade);
        }
      }
    }
  }

  private static void gerarIncludePesquisa(EAProjectConfig projeto, Entity entidade) throws IOException {

    String nomePagina = StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "_search.xhtml";

    String caminhoInclude =
            projeto.getDestinoView() + projeto.getNomeSistema().toLowerCase() + "/" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "/include";

    StringBuffer srtPage = new StringBuffer();

    // Cabecalho
    srtPage.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> \n");
    srtPage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n");
    srtPage.append("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"" + " \n \t xmlns:ui=\"http://java.sun.com/jsf/facelets\""
            + " \n \t xmlns:h=\"http://java.sun.com/jsf/html\"" + " \n\t xmlns:f=\"http://java.sun.com/jsf/core\"" + " \n\t");
    srtPage.append("xmlns:s=\"http://jboss.com/products/seam/taglib\" \n\t xmlns:a4j=\"http://richfaces.org/a4j\" \n\t xmlns:rich=\"http://richfaces.org/rich\" \n\t");
    srtPage.append("template=\"/layout/util/panel_2.xhtml\"> \n\t");
    srtPage.append("\n\t");

    String idField = null;
    for (Attribute atributo : entidade.getAtributos()) {
      if (atributo.getIsId()) {
        idField = atributo.getNome();
      }
    }

    srtPage.append("<ui:define name=\"body\"> \n\t\t");
    srtPage.append("<h:panelGrid columns=\"5\" styleClass=\"grd_consult_campo\"> \n\t\t\t");
    srtPage.append("<h:outputText value=\"Identificador: \" /> \n\t\t\t");
    if (idField == null) {
      srtPage.append("<h:inputText value=\"#{" + entidade.getNomeVarDto() + ".DESCRITOR_FIELD}\" styleClass=\"compon_cadastro\" /> \n\t\t\t");
      srtPage.append(" \n\t\t\t");
    } else {
      srtPage.append("<h:inputText value=\"#{" + entidade.getNomeVarDto() + "." + idField + "}\" styleClass=\"compon_cadastro\" /> \n\t\t\t");
      srtPage.append(" \n\t\t\t");
    }
    srtPage.append("</h:panelGrid> \n \t");
    srtPage.append("</ui:define> \n ");

    srtPage.append("\n \t");
    srtPage.append("<ui:define name=\"pesquisar\"> \n\t\t");
    srtPage.append("<a4j:commandButton action=\"#{" + entidade.getNomeVarAction() + ".buscar}\" type=\"submit\" "
            + "reRender=\"resultados\" value=\"Pesquisar\" styleClass=\"botao\" /> \n \t\t");
    srtPage.append("<h:outputText value=\" - \" /> \n \t\t");
    srtPage.append("<s:link action=\"#{" + entidade.getNomeVarAction() + ".formConversation}\" view=\"/" 
                      + StringUtil.toTableSintaxFormt(projeto.getNomeSistema()) + "/" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) 
                      + "/" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "_form.xhtml\" title=\"Novo "
                 + StringUtil.toLabelSintaxFormt(entidade.getNomeEntidade()) + "\"> \n \t\t\t");
    srtPage.append("<h:graphicImage value=\"../../images/btn_novo.gif\" style=\"vertical-align: middle;\" /> \n \t\t");
    srtPage.append("</s:link> \n \t");
    srtPage.append("</ui:define> \n ");
    srtPage.append("</ui:composition> \n ");

    FileManager.createFife(nomePagina, caminhoInclude, srtPage.toString());
  }

  private static void gerarIncludeLista(EAProjectConfig projeto, Entity entidade) throws IOException {

    String nomePagina = StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "_list.xhtml";

    String caminhoPaginas =
            projeto.getDestinoView() + projeto.getNomeSistema().toLowerCase() + "/" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "/include";

    StringBuffer srtPage = new StringBuffer();

    // Cabecalho
    srtPage.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> \n");
    srtPage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n\n");
    srtPage.append("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"\n\t xmlns:ui=\"http://java.sun.com/jsf/facelets\"\n\t xmlns:h=\"http://java.sun.com/jsf/html\"\n\t xmlns:f=\"http://java.sun.com/jsf/core\"\n\t ");
    srtPage.append("xmlns:s=\"http://jboss.com/products/seam/taglib\"\n\t xmlns:a4j=\"http://richfaces.org/a4j\"\n\t xmlns:rich=\"http://richfaces.org/rich\" > \n\t");
    srtPage.append("\n");

    srtPage.append("<script>\n\t");
    srtPage.append("function getRightTop(ref) {\n\t\t");
    srtPage.append("var position = new Object();\n\t\t");
    srtPage.append("position.top = 0; //ref.offsetTop;\n\t\t");
    srtPage.append("position.left =0; // ref.offsetLeft+ref.clientWidth+6;\n\t\t");
    srtPage.append("return position;\n\t");
    srtPage.append("}\n");
    srtPage.append("</script>\n");
    srtPage.append("\n");

    srtPage.append("\n\t");

    srtPage.append("<a4j:outputPanel id=\"resultados\"> \n\t\t");
    srtPage.append("<h:panelGroup rendered=\"#{" + entidade.getNomeVarDto() + "Lista.rowCount == 0}\"> \n\t\t\t");
    srtPage.append("Nenhum " + StringUtil.toLabelSintaxFormt(entidade.getNomeEntidade()) + " foi encontrado. \n\t\t");
    srtPage.append("</h:panelGroup>\n\t\t");
    srtPage.append("\n\t\t");
    srtPage.append("<rich:dataTable value=\"#{" + entidade.getNomeVarDto() + "Lista}\" \n \t\t\t");
    srtPage.append("var=\"" + entidade.getNomeVarEntidade().toLowerCase() + "\" \n \t\t\t");
    srtPage.append("id=\"" + entidade.getNomeVarEntidade().toLowerCase() + "Table\" rows =\"10\" \n \t\t\t");
    srtPage.append("onRowMouseOver=\"this.style.backgroundColor='#FFFF99'\" \n\t\t\t");
    srtPage.append("onRowMouseOut=\"this.style.backgroundColor='#{a4jSkin.tableBackgroundColor}'\" \n\t\t\t");
    srtPage.append("cellpadding=\"0\"  cellspacing=\"0\"  \n\t\t\t");
    srtPage.append("width=\"100%\"   border=\"0\" \n\t\t\t");
    srtPage.append("rendered=\"#{" + entidade.getNomeVarDto() + "Lista.rowCount > 0}\"> \n");

    srtPage.append(gerarGrid(projeto, entidade));

    srtPage.append(" \n\t\t\t");

    srtPage.append("<h:column headerClass=\"col_button\"> \n\t\t\t\t");
    srtPage.append("<f:facet name=\"header\"> \n\t\t\t\t\t");
    srtPage.append("<h:outputText value=\"\"/> \n\t\t\t\t");
    srtPage.append("</f:facet> \n\t\t\t\t");
    srtPage.append("<a4j:commandLink id=\"showItem\" action=\"#{" + entidade.getNomeVarAction()
            + ".selecionar}\" reRender=\"consulta\" onclick=\"javascript:Richfaces.showModalPanel('mp_consulta',{width:420, top:200});\" title=\"Visualizar "
            + StringUtil.toLabelSintaxFormt(entidade.getNomeEntidade()) + "\"> \n\t\t\t\t\t");
    srtPage.append("<h:graphicImage value=\"../../images/ico_lupa.gif\" border=\"0\"/> \n\t\t\t\t");
    srtPage.append("</a4j:commandLink> \n\t\t\t");
    srtPage.append("</h:column> \n\t\t\t\t");
    srtPage.append(" \n\t\t\t");

    srtPage.append("<h:column headerClass=\"col_button\"> \n\t\t\t\t");
    srtPage.append("<f:facet name=\"header\"> \n\t\t\t\t\t");
    srtPage.append("<h:outputText value=\"\"/> \n\t\t\t\t");
    srtPage.append("</f:facet> \n\t\t\t\t");
    srtPage.append("<s:link action=\"#{" + entidade.getNomeVarAction() + ".selecionar}\"  view=\"/" 
            + StringUtil.toTableSintaxFormt(projeto.getNomeSistema())+ "/" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) 
            + "/" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "_alteracao.xhtml\" title=\"Alterar "
            + StringUtil.toLabelSintaxFormt(entidade.getNomeEntidade()) + "\"> \n\t\t\t\t\t");
    srtPage.append("<h:graphicImage value=\"../../images/bot_editar_dados.gif\" border=\"0\" /> \n\t\t\t\t");
    srtPage.append("</s:link> \n\t\t\t");
    srtPage.append("</h:column> \n\t\t\t\t");
    srtPage.append(" \n\t\t\t");

    srtPage.append("<h:column headerClass=\"col_button\"> \n\t\t\t\t");
    srtPage.append("<f:facet name=\"header\"> \n\t\t\t\t\t");
    srtPage.append("<h:outputText value=\"\"/> \n\t\t\t\t");
    srtPage.append("</f:facet> \n\t\t\t\t");
    srtPage.append("<a4j:commandLink id=\"deleteItem\" reRender=\"exclusao\" action=\"#{" + entidade.getNomeVarAction()
            + ".selecionar}\" onclick=\"javascript:Richfaces.showModalPanel('mp_excluir',{width:420, top:200});\"  title=\"Excluir "
            + StringUtil.toLabelSintaxFormt(entidade.getNomeEntidade()) + "\"> \n\t\t\t\t\t");
    srtPage.append("<h:graphicImage value=\"../../images/btexcluir.gif\" border=\"0\" />	 \n\t\t\t\t");
    srtPage.append("</a4j:commandLink> \n\t\t\t");
    srtPage.append("</h:column> \n\t\t\t\t");
    srtPage.append(" \n\t\t");

    srtPage.append("</rich:dataTable> \n\t\t\t");

    srtPage.append(" \n\t\t");
    srtPage.append("<br/> \n\t\t");
    srtPage.append("<rich:datascroller id=\"paginador\" for=\"" + entidade.getNomeVarEntidade().toLowerCase()
            + "Table\" maxPages=\"5\" rendered=\"#{" + entidade.getNomeVarDto() + "Lista.rowCount > 10}\" /> \n\t\t");
    srtPage.append(" \n\t");

    srtPage.append("</a4j:outputPanel>\n ");
    srtPage.append("</ui:composition> \n ");

    FileManager.createFife(nomePagina, caminhoPaginas, srtPage.toString());
  }

  private static void gerarIncludeConsulta(EAProjectConfig projeto, Entity entidade) throws IOException {

    String nomePagina = StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "_view.xhtml";

    String caminhoInclude =
            projeto.getDestinoView() + projeto.getNomeSistema().toLowerCase() + "/" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "/include";

    StringBuffer srtPage = new StringBuffer();

    // Cabecalho
    srtPage.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> \n\t");
    srtPage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n");
    srtPage.append("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\" \n \t xmlns:ui=\"http://java.sun.com/jsf/facelets\" \n \t xmlns:h=\"http://java.sun.com/jsf/html\" \n\t xmlns:f=\"http://java.sun.com/jsf/core\" \n\t ");
    srtPage.append("xmlns:s=\"http://jboss.com/products/seam/taglib\" \n\t xmlns:a4j=\"http://richfaces.org/a4j\" \n\t xmlns:rich=\"http://richfaces.org/rich\" \n\t ");
    srtPage.append("> \n\t");
    srtPage.append("\n\t");

    //srtPage.append("<ui:define name=\"header\">" + StringUtil.toLabelSintaxFormt(entidade.getNomeEntidade()) + "</ui:define> \n\t");
    srtPage.append("<rich:modalPanel id=\"mp_consulta\" headerClass=\"headerConsulta\" height=\"140\" width=\"470\" zindex=\"2000\" > \n\t\t");

    srtPage.append("<f:facet name=\"header\"> \n\t\t\t <h:outputText value=\" Excluir" + StringUtil.toLabelSintaxFormt(entidade.getNomeEntidade()) + " \"/> \n\t\t</f:facet> \n\t\t");
    srtPage.append("<f:facet name=\"controls\"> \n\t\t\t" +
                     "<h:graphicImage value=\"../../images/ico_fecharjanela.gif\" style=\"cursor:pointer\" onclick=\"Richfaces.hideModalPanel('mp_consulta')\" /> \n\t\t" +
                   "</f:facet>\n\t");
    srtPage.append("<h:panelGrid id=\"consulta\" columns=\"2\" columnClasses=\"cl30_consulta, cl70_consulta\" > \n");
    
    
    // Rotina pata gerar os campos
    if (projeto.getTipoFormulario() == TipoFormulario.SEAM_PATTERN) {
      srtPage.append(gerarCamposConsultaSeamPattern(projeto, entidade));
    } else if (projeto.getTipoFormulario() == TipoFormulario.PRODEPA_PATTERN) {
      srtPage.append(gerarCamposConsultaProdepaPattern(projeto, entidade));
    } else {
      srtPage.append(gerarCamposConsultaSeamPattern(projeto, entidade));
    }

    srtPage.append("\t\t</h:panelGrid>\n");
    srtPage.append("\t</rich:modalPanel> \n ");
    srtPage.append("</ui:composition> \n ");

    FileManager.createFife(nomePagina, caminhoInclude, srtPage.toString());
  }

  private static void gerarIncludeExcluirSeamPattern(EAProjectConfig projeto, Entity entidade) throws IOException {

    String nomePagina = StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "_delete.xhtml";

    String caminhoInclude =
            projeto.getDestinoView() + projeto.getNomeSistema().toLowerCase() + "/" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "/include";

    StringBuffer srtPage = new StringBuffer();

    // Cabecalho
    srtPage.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> \n\t");
    srtPage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n");
    srtPage.append("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\" \n \t xmlns:ui=\"http://java.sun.com/jsf/facelets\" \n \t xmlns:h=\"http://java.sun.com/jsf/html\" \n\t xmlns:f=\"http://java.sun.com/jsf/core\" \n\t ");
    srtPage.append("xmlns:s=\"http://jboss.com/products/seam/taglib\" \n\t xmlns:a4j=\"http://richfaces.org/a4j\" \n\t xmlns:rich=\"http://richfaces.org/rich\" > \n\t ");
    srtPage.append("\n\t");

    srtPage.append("<rich:modalPanel id=\"mp_excluir\" height=\"140\" width=\"470\" zindex=\"2000\"> \n\t\t");

    srtPage.append("<f:facet name=\"header\"> \n\t\t\t");
      srtPage.append("<h:outputText value=\"Excluir "+ StringUtil.toLabelSintaxFormt(entidade.getNomeEntidade()) +" \"/> \n\t\t");
    srtPage.append("</f:facet>\n\t\t");

    srtPage.append("<f:facet name=\"controls\">\n \t\t\t");
    srtPage.append("<h:graphicImage value=\"../../images/ico_fecharjanela.gif\" style=\"cursor:pointer\" onclick=\"Richfaces.hideModalPanel('mp_excluir')\" /> \n \t\t");
    srtPage.append("</f:facet> \n \t\t");

    srtPage.append(" \n\t\t");

    srtPage.append("<h:panelGrid id=\"exclusao\" columns=\"2\"> \n\t\t\t");

    String idField = null;
    for (Attribute atributo : entidade.getAtributos()) {
      if (atributo.getIsId()) {
        idField = atributo.getNome();
      }
    }
    if (idField != null) {
      srtPage.append("<s:decorate template=\"../../../layout/util/display_view.xhtml\"> \n\t\t\t\t");
      srtPage.append("<ui:define name=\"label\">IDENTIFICADOR:</ui:define> \n\t\t\t\t");
      srtPage.append("<ui:define name=\"label\"><h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + idField + "}\" />:</ui:define> \n\t\t\t");
      srtPage.append("</s:decorate>\n\t\t\t");
    } else {
      srtPage.append("<s:decorate template=\"../../../layout/util/display_view.xhtml\"> \n\t\t\t\t");
      srtPage.append("<ui:define name=\"label\">IDENTIFICADOR:</ui:define> \n\t\t\t\t");
      srtPage.append("<ui:define name=\"label\"><h:outputText value=\"#{" + entidade.getNomeVarDto() + ".IDENTIFICADOR}\" />:</ui:define> \n\t\t\t");
      srtPage.append("</s:decorate>\n\t\t\t");
    }

    srtPage.append("</h:panelGrid> \n \t");

    srtPage.append("<h:panelGrid id=\"exclusaocontrols\" columns=\"2\"> \n \t\t");
    srtPage.append("<a4j:commandButton id=\"confirmaExclusao\" value=\"Confirmar Exclus�o\" styleClass=\"botao\" action=\"#{"
                    + entidade.getNomeVarAction() + ".excluir}\" oncomplete=\"Richfaces.hideModalPanel('mp_excluir')\" reRender=\"" + entidade.getNomeEntidade().toLowerCase() + "_form_list\"/> \n \t\t");
    srtPage.append("<a4j:commandButton id=\"cancelaExclusao\" value=\"Cancelar Exclus�o\" styleClass=\"botao\" onclick=\"Richfaces.hideModalPanel('mp_excluir')\" /> \n \t");
    srtPage.append("</h:panelGrid> \n \t");
    srtPage.append(" \n \t");
    srtPage.append(" \n \t");

    srtPage.append("</rich:modalPanel> \n ");

    srtPage.append("</ui:composition> \n ");

    FileManager.createFife(nomePagina, caminhoInclude, srtPage.toString());
  }

  private static void gerarIncludeExcluirProdepaPattern(EAProjectConfig projeto, Entity entidade) throws IOException {

    String nomePagina = StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "_delete.xhtml";

    String caminhoInclude = projeto.getDestinoView() + projeto.getNomeSistema().toLowerCase() + "/" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()) + "/include";

    StringBuffer srtPage = new StringBuffer();

    // Cabecalho
    srtPage.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> \n\t");
    srtPage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n");
    srtPage.append("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\" \n \t xmlns:ui=\"http://java.sun.com/jsf/facelets\" \n \t xmlns:h=\"http://java.sun.com/jsf/html\" \n\t xmlns:f=\"http://java.sun.com/jsf/core\" \n\t ");
    srtPage.append("xmlns:s=\"http://jboss.com/products/seam/taglib\" \n\t xmlns:a4j=\"http://richfaces.org/a4j\" \n\t xmlns:rich=\"http://richfaces.org/rich\" > \n\t ");
    srtPage.append("\n\t");

    srtPage.append("<rich:modalPanel id=\"mp_excluir\" height=\"140\" width=\"470\" zindex=\"2000\"> \n\t\t");

    srtPage.append("<f:facet name=\"header\"> \n\t\t\t");
    srtPage.append("<h:outputText value=\"Excluir "+ StringUtil.toLabelSintaxFormt(entidade.getNomeEntidade()) +" \"/> \n\t\t");
    srtPage.append("</f:facet>\n\t\t");

    srtPage.append("<f:facet name=\"controls\">\n \t\t\t");
    srtPage.append("<h:graphicImage value=\"../../images/ico_fecharjanela.gif\" style=\"cursor:pointer\" onclick=\"Richfaces.hideModalPanel('mp_excluir')\" /> \n \t\t");
    srtPage.append("</f:facet> \n \t\t");

    srtPage.append(" \n\t\t");

    srtPage.append("<h:panelGrid id=\"exclusao\" columns=\"2\"> \n\t\t\t");

    String idField = null;
    for (Attribute atributo : entidade.getAtributos()) {
      if (atributo.getIsId()) {
        idField = atributo.getNome();
      }
    }
    if (idField != null) {
      srtPage.append("<h:outputText value=\"" + idField + ":\" style=\"componente_label_view\"/> \n\t\t\t");
      srtPage.append("<h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + idField + "}:\" style=\"componente_field_view\"/> \n\t\t");
    } else {
      srtPage.append("<h:outputText value=\"IDENTIFICADOR:\" style=\"componente_label_view\"/> \n\t\t\t");
      srtPage.append("<h:outputText value=\"#{" + entidade.getNomeVarDto() + ".IDENTIFICADOR}:\" style=\"componente_field_view\"/> \n\t\t");
    }

    srtPage.append("</h:panelGrid> \n \t");

    srtPage.append("<h:panelGrid id=\"exclusaocontrols\" columns=\"2\"> \n \t\t");
    srtPage.append("<a4j:commandButton id=\"confirmaExclusao\" value=\"Confirmar Exclus�o\" styleClass=\"botao\" action=\"#{"
            + entidade.getNomeVarAction() + ".excluir}\" oncomplete=\"Richfaces.hideModalPanel('mp_excluir')\" reRender=\"" + entidade.getNomeEntidade().toLowerCase() + "_form_pesq\" /> \n \t\t");
    srtPage.append("<a4j:commandButton id=\"cancelaExclusao\" value=\"Cancelar Exclus�o\" styleClass=\"botao\" onclick=\"Richfaces.hideModalPanel('mp_excluir')\" /> \n \t");
    srtPage.append("</h:panelGrid> \n \t");
    srtPage.append(" \n \t");
    srtPage.append(" \n \t");

    srtPage.append("</rich:modalPanel> \n ");
    srtPage.append("</ui:composition> \n ");

    FileManager.createFife(nomePagina, caminhoInclude, srtPage.toString());
  }

  private static String gerarGrid(EAProjectConfig projeto, Entity entidade) {

    StringBuffer srtPage = new StringBuffer();
    String nome = "";
    String nomeVar = "";
    int tipo = 0;
    for (Attribute atributo : entidade.getAtributos()) {
      nome = atributo.getNome();
      tipo = TipoDados.getEnum(atributo.getTipo()).getTratamento();
      nomeVar = entidade.getNomeEntidade().toLowerCase();

      srtPage.append("\n\t\t\t");
      switch (tipo) {

      case 1: // Strings
        srtPage.append("<rich:column sortBy=\"#{" + nomeVar + "." + nome + "}\"> \n\t\t\t\t");
        srtPage.append("<f:facet name=\"header\"> \n\t\t\t\t\t");
        srtPage.append("<h:outputText value=\"" + StringUtil.toLabelSintaxFormt(nome) + "\"/> \n\t\t\t\t");
        srtPage.append("</f:facet> \n\t\t\t\t");
        srtPage.append("<h:outputText value=\"#{" + nomeVar + "." + nome + "}\"/> \n\t\t\t");
        srtPage.append("</rich:column> \n\t\t\t");
        break;

      case 2: // Numericos exatos
        srtPage.append("<rich:column sortBy=\"#{" + nomeVar + "." + nome + "}\"> \n\t\t\t\t");
        srtPage.append("<f:facet name=\"header\"> \n\t\t\t\t\t");
        srtPage.append("<h:outputText value=\"" + StringUtil.toLabelSintaxFormt(nome) + "\"/> \n\t\t\t\t");
        srtPage.append("</f:facet> \n\t\t\t\t");
        srtPage.append("<h:outputText value=\"#{" + nomeVar + "." + nome + "}\"/> \n\t\t\t");
        srtPage.append("</rich:column> \n\t\t\t");
        break;

      case 3: // Numericos flutuantes
        srtPage.append("<rich:column sortBy=\"#{" + nomeVar + "." + nome + "}\"> \n\t\t\t\t");
        srtPage.append("<f:facet name=\"header\"> \n\t\t\t\t\t");
        srtPage.append("<h:outputText value=\"" + StringUtil.toLabelSintaxFormt(nome) + "\"/> \n\t\t\t\t");
        srtPage.append("</f:facet> \n\t\t\t\t");
        srtPage.append("<h:outputText value=\"#{" + nomeVar + "." + nome + "}\"/> \n\t\t\t");
        srtPage.append("</rich:column> \n\t\t\t");
        break;

      case 4: // Datas
        srtPage.append("<rich:column sortBy=\"#{" + nomeVar + "." + nome + "}\"> \n\t\t\t\t");
        srtPage.append("<f:facet name=\"header\"> \n\t\t\t\t\t");
        srtPage.append("<h:outputText value=\"" + StringUtil.toLabelSintaxFormt(nome) + "\"/> \n\t\t\t\t");
        srtPage.append("</f:facet> \n\t\t\t\t");
        srtPage.append("<h:outputText value=\"#{" + nomeVar + "." + nome + "}\"> \n\t\t\t\t\t");
        srtPage.append("<f:convertDateTime type=\"date\" dateStyle=\"medium\" pattern=\"dd/MM/yyyy\"/>  \n\t\t\t\t");
        srtPage.append("</h:outputText> \n\t\t\t");
        srtPage.append("</rich:column> \n\t\t\t");
        break;

      case 5: // Boleanos
        srtPage.append("<rich:column sortBy=\"#{" + nomeVar + "." + nome + "}\"> \n\t\t\t\t");
        srtPage.append("<f:facet name=\"header\"> \n\t\t\t\t\t");
        srtPage.append("<h:outputText value=\"" + StringUtil.toLabelSintaxFormt(nome) + "\"/> \n\t\t\t\t");
        srtPage.append("</f:facet> \n\t\t\t\t");
        srtPage.append("<h:outputText value=\"#{" + nomeVar + "." + nome + "}\"/> \n\t\t\t");
        srtPage.append("</rich:column> \n\t\t\t");
        break;

      case 6: // TipoBoleano da prodepa
        srtPage.append("<rich:column sortBy=\"#{" + nomeVar + "." + nome + "}\"> \n\t\t\t\t");
        srtPage.append("<f:facet name=\"header\"> \n\t\t\t\t\t");
        srtPage.append("<h:outputText value=\"" + StringUtil.toLabelSintaxFormt(nome) + "\"/> \n\t\t\t\t");
        srtPage.append("</f:facet> \n\t\t\t\t");
        srtPage.append("<h:outputText value=\"#{" + nomeVar + "." + nome + "}\"/> \n\t\t\t");
        srtPage.append("</rich:column> \n\t\t\t");
        break;

      case 7: // Objeto
        srtPage.append("<rich:column sortBy=\"#{" + nomeVar + "." + nome + "}\"> \n\t\t\t\t");
        srtPage.append("<f:facet name=\"header\"> \n\t\t\t\t\t");
        srtPage.append("<h:outputText value=\"" + StringUtil.toLabelSintaxFormt(nome) + "\"/> \n\t\t\t\t");
        srtPage.append("</f:facet> \n\t\t\t\t");
        srtPage.append("<h:outputText value=\"#{" + nomeVar + "." + nome + "}\"/> \n\t\t\t");
        srtPage.append("</rich:column> \n\t\t\t");
        break;

      default:
        srtPage.append("<rich:column sortBy=\"#{" + nomeVar + "." + nome + "}\"> \n\t\t\t\t");
        srtPage.append("<f:facet name=\"header\"> \n\t\t\t\t\t");
        srtPage.append("<h:outputText value=\"" + StringUtil.toLabelSintaxFormt(nome) + "\"/> \n\t\t\t\t");
        srtPage.append("</f:facet> \n\t\t\t\t");
        srtPage.append("<h:outputText value=\"#{" + nomeVar + "." + nome + "}\"/> \n\t\t\t");
        srtPage.append("</rich:column> \n\t\t\t");

        break;
      }
    }

    return srtPage.toString();
  }

  private static String gerarCamposConsultaSeamPattern(EAProjectConfig projeto, Entity entidade) {

    StringBuffer srtPage = new StringBuffer();
    String nome = "";
    String nomeVar = "";
    int tipo = 0;

    for (Attribute atributo : entidade.getAtributos()) {
      nome = atributo.getNome();
      tipo = TipoDados.getEnum(atributo.getTipo()).getTratamento();
      nomeVar = entidade.getNomeEntidade().toLowerCase();
      srtPage.append("\n\t\t");

      switch (tipo) {

      case 1: // Strings
        srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/display_view.xhtml\"> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\">" + StringUtil.toLabelSintaxFormt(nome) + ":</ui:define> \n\t\t\t");
        srtPage.append("<ui:define name=\"valueLabel\"><h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome
                + "}\" /></ui:define> \n\t\t");
        srtPage.append("</s:decorate>\n\t\t");
        break;

      case 2: // Numericos exatos
        srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/display_view.xhtml\"> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\">" + StringUtil.toLabelSintaxFormt(nome) + ":</ui:define> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\"><h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" /></ui:define> \n\t\t");
        srtPage.append("</s:decorate>\n\t\t");
        break;

      case 3: // Numericos flutuantes
        srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/display_view.xhtml\"> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\">" + StringUtil.toLabelSintaxFormt(nome) + ":</ui:define> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\"><h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" /></ui:define> \n\t\t");
        srtPage.append("</s:decorate>\n\t\t");
        break;

      case 4: // Datas
        srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/display_view.xhtml\"> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\">" + StringUtil.toLabelSintaxFormt(nome) + ":</ui:define> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\"> \n\t\t\t\t");
        srtPage.append("<h:outputText value=\"#{" + nomeVar + "." + nome + "}\"> \n\t\t\t\t");
        srtPage.append("<f:convertDateTime type=\"date\" dateStyle=\"medium\" pattern=\"dd/MM/yyyy\"/>  \n\t\t\t\t");
        srtPage.append("</h:outputText> \n\t\t\t");
        srtPage.append("</ui:define> \n\t\t");
        srtPage.append("</s:decorate>\n\t\t");
        break;

      case 5: // Boleanos
        srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/display_view.xhtml\"> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\">" + StringUtil.toLabelSintaxFormt(nome) + ":</ui:define> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\"><h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" /></ui:define> \n\t\t");
        srtPage.append("</s:decorate>\n\t\t");
        break;

      case 6: // TipoBoleano da prodepa
        srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/display_view.xhtml\"> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\">" + StringUtil.toLabelSintaxFormt(nome) + ":</ui:define> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\"><h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" /></ui:define> \n\t\t");
        srtPage.append("</s:decorate>\n\t\t");
        break;

      case 7: // Objeto
        srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/display_view.xhtml\"> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\">" + StringUtil.toLabelSintaxFormt(nome) + ":</ui:define> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\"><h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" /></ui:define> \n\t\t");
        srtPage.append("</s:decorate>\n\t\t");
        break;

      default:
        srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/display_view.xhtml\"> \n\t\t\t");
        srtPage.append("<ui:define name=\"label\">" + StringUtil.toLabelSintaxFormt(nome) + ":</ui:define> \n\t\t\t");
        srtPage.append("<ui:define name=\"valueLabel\"><h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome
                + "}\" /></ui:define> \n\t\t");
        srtPage.append("</s:decorate>\n\t\t");

        break;
      }
    }
    return srtPage.toString();
  }

  private static String gerarCamposConsultaProdepaPattern(EAProjectConfig projeto, Entity entidade) {

    StringBuffer srtPage = new StringBuffer();
    String nome = "";
    int tipo = 0;

    for (Attribute atributo : entidade.getAtributos()) {
      nome = atributo.getNome();
      tipo = TipoDados.getEnum(atributo.getTipo()).getTratamento();
      srtPage.append("\n\t\t");

      switch (tipo) {

      case 1: // Strings
        srtPage.append("<h:outputText value=\" " + StringUtil.toLabelSintaxFormt(nome) + ":\" style=\"componente_label_view\" /> \n\t\t");
        srtPage.append("<h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" style=\"componente_label_view\" /> \n\t\t");
        break;

      case 2: // Numericos exatos
        srtPage.append("<h:outputText value=\" " + StringUtil.toLabelSintaxFormt(nome) + ":\" style=\"componente_label_view\" /> \n\t\t");
        srtPage.append("<h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" style=\"componente_label_view\" /> \n\t\t");
        break;

      case 3: // Numericos flutuantes
        srtPage.append("<h:outputText value=\" " + StringUtil.toLabelSintaxFormt(nome) + ":\" style=\"componente_label_view\" /> \n\t\t");
        srtPage.append("<h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" style=\"componente_label_view\" /> \n\t\t");
        break;

      case 4: // Datas
        srtPage.append("<h:outputText value=\" " + StringUtil.toLabelSintaxFormt(nome) + ":\" style=\"componente_label_view\" /> \n\t\t");
        srtPage.append("<h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" style=\"componente_label_view\" > \n\t\t\t");
        srtPage.append(" <f:convertDateTime type=\"date\" dateStyle=\"medium\" pattern=\"dd/MM/yyyy\"/> \n\t\t");
        srtPage.append("</h:outputText> \n\t\t");
        break;

      case 5: // Boleanos
        srtPage.append("<h:outputText value=\" " + StringUtil.toLabelSintaxFormt(nome) + ":\" style=\"componente_label_view\" /> \n\t\t");
        srtPage.append("<h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" style=\"componente_label_view\" /> \n\t\t");
        break;

      case 6: // TipoBoleano da prodepa
        srtPage.append("<h:outputText value=\" " + StringUtil.toLabelSintaxFormt(nome) + ":\" style=\"componente_label_view\" /> \n\t\t");
        srtPage.append("<h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome + ".descricao}\" style=\"componente_label_view\" /> \n\t\t");
        break;

      case 7: // Objeto
        srtPage.append("<h:outputText value=\" " + StringUtil.toLabelSintaxFormt(nome) + ":\" style=\"componente_label_view\" /> \n\t\t");
        srtPage.append("<h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" style=\"componente_label_view\" /> \n\t\t");
        break;

      default:
        srtPage.append("<h:outputText value=\" " + StringUtil.toLabelSintaxFormt(nome) + ":\" style=\"componente_label_view\" /> \n\t\t");
        srtPage.append("<h:outputText value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" style=\"componente_label_view\" /> \n\t\t");

        break;
      }
    }
    return srtPage.toString();
  }

}