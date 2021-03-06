package br.com.capanema.kers.core.factory.pages.form;


import java.io.IOException;

import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.common.model.projectea.TipoFormulario;
import br.com.capanema.kers.common.model.types.TipoDados;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorCadastroFormCalango extends CalangoFactory {

    public static void build(EAProjectConfig projeto) throws IOException {

	for (Entity entidade : projeto.getEntidades()) {

	    String caminhoPaginas = projeto.getDestinoView() + projeto.getNomeSistema().toLowerCase() + "/"
		    + entidade.getNomeEntidade().toLowerCase();
	    String nomePagina = entidade.getNomeEntidade().toLowerCase() + "_form.xhtml";

	    StringBuffer srtBuffer = new StringBuffer();

	    srtBuffer.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> \n");
	    srtBuffer
		    .append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n");
	    srtBuffer
		    .append("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\" \n \t xmlns:ui=\"http://java.sun.com/jsf/facelets\" \n \t xmlns:h=\"http://java.sun.com/jsf/html\" \n\t xmlns:f=\"http://java.sun.com/jsf/core\" \n\t");
	    srtBuffer.append("template=\"../../layout/t_default_1.xhtml\"> \n\t");
	    srtBuffer.append("<ui:define name=\"titulo\">" + entidade.getNomeEntidade() + "</ui:define> \n\t");
	    srtBuffer.append("<ui:define name=\"caminho\">" + entidade.getNomeEntidade() + " - Cadastro</ui:define> \n\t");
	    srtBuffer.append("\n\t\t");
	    srtBuffer.append("<ui:define name=\"conteudo1\"> \n\t\t");
	    srtBuffer.append("<h:form id=\"" + entidade.getNomeEntidade().toLowerCase() + "Form\"> \n\t\t\t");
	    srtBuffer.append("<ui:include src=\"include/" + nomePagina + "\" /> \n\t\t");
	    srtBuffer.append("</h:form> \n\t");
	    srtBuffer.append("</ui:define> \n");
	    srtBuffer.append("</ui:composition> \n");

	    FileManager.createFife(nomePagina, caminhoPaginas, srtBuffer.toString());

	    gerarIncludeForm(projeto, entidade);
	}

    }

    private static void gerarIncludeForm(EAProjectConfig projeto, Entity entidade) throws IOException {

	String nomePagina = entidade.getNomeEntidade().toLowerCase() + "_form.xhtml";
	// String nomeInclude = entidade.getNomeEntidade().toLowerCase() +
	// "_form.xhtml";

	// String caminhoPaginas = projeto.getCaminhoView() +
	// projeto.getNomeSistema().toLowerCase() + "/"+
	// entidade.getNomeEntidade().toLowerCase();
	String caminhoInclude = projeto.getDestinoView() + projeto.getNomeSistema().toLowerCase() + "/" + entidade.getNomeEntidade().toLowerCase()
		+ "/include";

	StringBuffer srtPage = new StringBuffer();

	// Cabecalho
	srtPage.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> \n");
	srtPage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n");
	srtPage.append("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\" \n \t xmlns:ui=\"http://java.sun.com/jsf/facelets\" \n \t xmlns:h=\"http://java.sun.com/jsf/html\" \n\t xmlns:f=\"http://java.sun.com/jsf/core\" \n\t");
	srtPage.append("xmlns:s=\"http://jboss.com/products/seam/taglib\" \n\t xmlns:a4j=\"http://richfaces.org/a4j\" \n\t xmlns:rich=\"http://richfaces.org/rich\" \n\t");
	srtPage.append("template=\"/layout/util/panel.xhtml\"> \n\t");
	srtPage.append("\n\t");

	srtPage.append("<ui:define name=\"header\">Cadastro de " + entidade.getNomeEntidade() + "</ui:define> \n\t");
	srtPage.append("<ui:define name=\"body\"> \n\t\t\t");


	// Rotina pata gerar os campos
	if (projeto.getTipoFormulario() == TipoFormulario.SEAM_PATTERN) {
		// painel do formulario
		srtPage.append("<h:panelGrid columns=\"1\" styleClass=\"grd_consult_campo\"> \n");
		srtPage.append("\n\t\t\t\t");

		srtPage.append(gerarFormSeamPattern(projeto, entidade));
	} else if (projeto.getTipoFormulario() == TipoFormulario.PRODEPA_PATTERN) {
		// painel do formulario
		srtPage.append("<h:panelGrid columns=\"2\" styleClass=\"grd_consult_campo\"> \n");
		srtPage.append("\n\t\t\t\t");
		
	    srtPage.append(gerarFormProdepaPattern(projeto, entidade));
	} else {
		// painel do formulario
		srtPage.append("<h:panelGrid columns=\"1\" styleClass=\"grd_consult_campo\"> \n");
		srtPage.append("\n\t\t\t\t");

	    srtPage.append(gerarFormSeamPattern(projeto, entidade));
	}

	srtPage.append("\n \t\t\t");
	srtPage.append("</h:panelGrid> \n\t\t\t");
	srtPage.append("</ui:define> \n\t\t\t");
	srtPage.append("<ui:define name=\"botao\"> \n \t\t\t\t ");
	srtPage.append("<a4j:commandButton id=\"cadastrar\" value=\"Cadastrar\" action=\"#{" + entidade.getNomeVarAction()
		+ ".cadastrar}\" styleClass=\"botao\" /> \n\t\t\t");
	srtPage.append("</ui:define> \n ");
	srtPage.append("</ui:composition> \n ");

	FileManager.createFife(nomePagina, caminhoInclude, srtPage.toString());
    }

    private static String gerarFormProdepaPattern(EAProjectConfig projeto, Entity entidade) {

	StringBuffer srtPage = new StringBuffer();
	String nome = "";
	int tipo = 0;

	for (Attribute atributo : entidade.getAtributos()) {

	    if (!atributo.getIsId()) {
		nome = atributo.getNome();
		tipo = TipoDados.getEnum(atributo.getTipo()).getTratamento();

		srtPage.append("\n\t\t\t\t");
		switch (tipo) {
		case 1: // Strings

		    srtPage.append("<h:outputText value=\"" + nome + "\" /> \n\t\t\t\t");
		    srtPage.append("<h:inputText id=\"" + nome + "\" value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" " +
		    		"required=\"true\" requiredMessage=\"Favor informe o " + nome + "\" " +
		    		"size=\"50\" maxlength=\"100\" styleClass=\"compon_cadastro\" /> \n\t\t\t\t\t\t");
		    break;

		case 2: // Numericos exatos

		    srtPage.append("<h:outputText value=\"" + nome + "\" /> \n\t\t\t\t");
		    srtPage.append("<rich:inputNumberSpinner \n\t\t\t\t\t\t\t");
		    srtPage.append("id=\"" + nome + "\" \n\t\t\t\t\t\t\t");
		    srtPage.append("value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" \n\t\t\t\t\t\t\t");
		    srtPage.append("maxValue=\"1000\" \n\t\t\t\t\t\t\t");
		    srtPage.append("minValue=\"1\" \n\t\t\t\t\t\t\t");
		    srtPage.append("required=\"true\" \n\t\t\t\t\t\t\t");
		    srtPage.append("requiredMessage=\"Favor informe o " + nome + "\"  \n\t\t\t\t\t\t\t");
		    srtPage.append("enableManualInput=\"true\" \n\t\t\t\t\t\t\t");
		    srtPage.append("converterMessage=\"Favor informe um valor v�lido para o " + nome + "\" \n\t\t\t\t\t\t\t");
		    srtPage.append("styleClass=\"compon_cadastro_rich_spinner\" /> \n\t\t\t\t\t");
		    break;

		case 3: // Numericos flutuantes
		    srtPage.append("<h:outputText value=\"" + nome + "\" /> \n\t\t\t\t");
		    srtPage.append("<rich:inputNumberSpinner \n\t\t\t\t\t\t\t");
		    srtPage.append("id=\"" + nome + "\" \n\t\t\t\t\t\t\t");
		    srtPage.append("value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" \n\t\t\t\t\t\t\t");
		    srtPage.append("maxValue=\"1000\" \n\t\t\t\t\t\t\t");
		    srtPage.append("minValue=\"0.1\" \n\t\t\t\t\t\t\t");
		    srtPage.append("required=\"true\" \n\t\t\t\t\t\t\t");
		    srtPage.append("requiredMessage=\"Favor informe o " + nome + "\"  \n\t\t\t\t\t\t\t");
		    srtPage.append("enableManualInput=\"true\" \n\t\t\t\t\t\t\t");
		    srtPage.append("converterMessage=\"Favor informe um valor v�lido para o " + nome + "\" \n\t\t\t\t\t\t\t");
		    srtPage.append("styleClass=\"compon_cadastro_rich_spinner\" /> \n\t\t\t\t\t");

		    break;

		case 4: // Datas
		    srtPage.append("<h:outputText value=\"" + nome + "\" /> \n\t\t\t\t");
		    srtPage.append("<rich:calendar  \n\t\t\t\t\t\t");
		    srtPage.append("id=\"" + nome + "\" \n\t\t\t\t\t\t");
		    srtPage.append("value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" \n\t\t\t\t\t\t");
		    srtPage.append("required=\"true\" \n\t\t\t\t\t\t");
		    srtPage.append("requiredMessage=\"Informe o " + nome + "\"  \n\t\t\t\t\t\t");
		    srtPage.append("enableManualInput=\"true\" \n\t\t\t\t\t\t");
		    srtPage.append("converterMessage=\"A data da avaliaca��o deve obedecer o formato 31/12/2008\" \n\t\t\t\t\t\t");
		    srtPage.append("popup=\"true\" \n\t\t\t\t\t\t");
		    srtPage.append("datePattern=\"dd/MM/yy\" /> \n\t\t\t\t\t\t");
		    break;

		case 5: // Boleanos
		    //TODO N�o implementado
		    srtPage.append("<h:outputText value=\"" + nome + "\" /> \n\t\t\t\t");
		    srtPage.append("<h:inputText id=\"" + nome + "\" value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" " +
		    		"required=\"true\" requiredMessage=\"Favor informe o " + nome + "\"> \n\t\t\t\t\t\t");
		    break;

		case 6: // TipoBoleano da prodepa
		    srtPage.append("<h:outputText value=\"" + nome + "\" /> \n\t\t\t\t");
		    srtPage.append("<h:selectOneRadio value=\"#{" + entidade.getNomeVarDto() + "." + nome
				    + "}\" layout=\"lineDirection\" required=\"true\" requiredMessage=\"Selecione o Sexo!\" class=\"compon_cadastro_rich\"> \n\t\t\t\t\t\t");
		    srtPage.append("<s:selectItems value=\"#{tiposBoleanos}\" var=\"tipo\" label=\"#{tipo.mensagem}\" /> \n\t\t\t\t\t\t");
		    srtPage.append("<s:convertEnum /> \n\t\t\t\t\t\t");
		    srtPage.append("</h:selectOneRadio> \n\t\t\t\t");
		    break;

		case 7: // Objeto

		    srtPage.append("<h:outputText value=\"" + nome + "\" /> \n\t\t\t\t");
		    srtPage.append("<h:selectOneRadio value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" " +
		    		"layout=\"lineDirection\" required=\"true\" requiredMessage=\"Selecione o " + nome + "!\" " +
		    		"class=\"compon_cadastro_rich\"> \n\t\t\t\t\t");
		    srtPage.append("<s:selectItems value=\"#{" + nome + "FACTORY}\" var=\"" + nome + "\" label=\"#{" + nome + ".mensagem}\" /> \n\t\t\t\t\t");
		    srtPage.append("<s:convertEnum /> \n\t\t\t\t");
		    srtPage.append("</h:selectOneRadio> \n\t\t\t\t");
		    break;

		default:
		    srtPage.append("<h:outputText value=\"" + nome + "\" /> \n\t\t\t\t");
		    srtPage.append("<h:inputText id=\"" + nome + "\" value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" " +
		    		"required=\"true\" requiredMessage=\"Favor informe o " + nome + "\" " +
		    		"size=\"50\" maxlength=\"100\" styleClass=\"compon_cadastro\" /> \n\t\t\t\t\t\t");
		    break;
		}
	    }
	}
	return srtPage.toString();
    }
    
    private static String gerarFormSeamPattern(EAProjectConfig projeto, Entity entidade) {

	StringBuffer srtPage = new StringBuffer();
	String nome = "";
	int tipo = 0;

	for (Attribute atributo : entidade.getAtributos()) {

	    if (!atributo.getIsId()) {
		nome = atributo.getNome();
		tipo = TipoDados.getEnum(atributo.getTipo()).getTratamento();

		srtPage.append("\n\t\t\t\t");
		switch (tipo) {
		case 1: // Strings

		    srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/edit.xhtml\"> \n\t\t\t\t\t");
		    srtPage.append("<ui:define name=\"label\">" + nome + ":</ui:define> \n\t\t\t\t\t");
		    srtPage.append("<h:inputText id=\"" + nome + "\" value=\"#{" + entidade.getNomeVarDto() + "." + nome
			    + "}\" required=\"true\" requiredMessage=\"Favor informe o " + nome + "\"> \n\t\t\t\t\t\t");
		    srtPage.append("<a4j:support id=\"onblur\" event=\"onblur\" reRender=\"" + nome + "Field\"/> \n\t\t\t\t\t");
		    srtPage.append("</h:inputText> \n\t\t\t\t");
		    srtPage.append("</s:decorate>\n\t\t\t");
		    break;

		case 2: // Numericos exatos

		    srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/edit.xhtml\"> \n\t\t\t\t\t");
		    srtPage.append("<ui:define name=\"label\">" + nome + ":</ui:define> \n\t\t\t\t\t");
		    srtPage.append("<rich:inputNumberSpinner \n\t\t\t\t\t\t\t");
		    srtPage.append("id=\"" + nome + "\" \n\t\t\t\t\t\t\t");
		    srtPage.append("value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" \n\t\t\t\t\t\t\t");
		    srtPage.append("maxValue=\"1000\" \n\t\t\t\t\t\t\t");
		    srtPage.append("minValue=\"1\" \n\t\t\t\t\t\t\t");
		    srtPage.append("required=\"true\" \n\t\t\t\t\t\t\t");
		    srtPage.append("requiredMessage=\"Favor informe o " + nome + "\"  \n\t\t\t\t\t\t\t");
		    srtPage.append("enableManualInput=\"true\" \n\t\t\t\t\t\t\t");
		    srtPage.append("converterMessage=\"Favor informe um valor v�lido para o " + nome + "\" \n\t\t\t\t\t\t\t");
		    srtPage.append("styleClass=\"compon_cadastro_rich_spinner\"> \n\t\t\t\t\t");
		    srtPage.append("<a4j:support id=\"onblur\" event=\"onblur\" reRender=\"" + nome + "Field\"/> \n\t\t\t\t\t");
		    srtPage.append("</rich:inputNumberSpinner> \n\t\t\t\t");
		    srtPage.append("</s:decorate>\n\t\t\t");
		    break;

		case 3: // Numericos flutuantes
		    srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/edit.xhtml\"> \n\t\t\t\t\t");
		    srtPage.append("<ui:define name=\"label\">" + nome + ":</ui:define> \n\t\t\t\t\t");
		    srtPage.append("<rich:inputNumberSpinner \n\t\t\t\t\t\t\t");
		    srtPage.append("id=\"" + nome + "\" \n\t\t\t\t\t\t\t");
		    srtPage.append("value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" \n\t\t\t\t\t\t\t");
		    srtPage.append("maxValue=\"1000\" \n\t\t\t\t\t\t\t");
		    srtPage.append("minValue=\"0.1\" \n\t\t\t\t\t\t\t");
		    srtPage.append("required=\"true\" \n\t\t\t\t\t\t\t");
		    srtPage.append("requiredMessage=\"Favor informe o " + nome + "\"  \n\t\t\t\t\t\t\t");
		    srtPage.append("enableManualInput=\"true\" \n\t\t\t\t\t\t\t");
		    srtPage.append("converterMessage=\"Favor informe um valor v�lido para o " + nome + "\" \n\t\t\t\t\t\t\t");
		    srtPage.append("styleClass=\"compon_cadastro_rich_spinner\"> \n\t\t\t\t\t");

		    srtPage.append("<a4j:support id=\"onblur\" event=\"onblur\" reRender=\"" + nome + "Field\"/> \n\t\t\t\t\t");

		    srtPage.append("</rich:inputNumberSpinner> \n\t\t\t\t");
		    srtPage.append("</s:decorate>\n\t\t\t");
		    break;

		case 4: // Datas

		    srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/edit.xhtml\"> \n\t\t\t\t\t");
		    srtPage.append("<ui:define name=\"label\">" + nome + ":</ui:define> \n\t\t\t\t\t");
		    srtPage.append("<rich:calendar  \n\t\t\t\t\t\t");
		    srtPage.append("id=\"" + nome + "\" \n\t\t\t\t\t\t");
		    srtPage.append("value=\"#{" + entidade.getNomeVarDto() + "." + nome + "}\" \n\t\t\t\t\t\t");
		    srtPage.append("required=\"true\" \n\t\t\t\t\t\t");
		    srtPage.append("requiredMessage=\"Informe o " + nome + "\"  \n\t\t\t\t\t\t");
		    srtPage.append("enableManualInput=\"true\" \n\t\t\t\t\t\t");
		    srtPage.append("converterMessage=\"A data da avaliaca��o deve obedecer o formato 31/12/2008\" \n\t\t\t\t\t\t");
		    srtPage.append("popup=\"true\" \n\t\t\t\t\t\t");
		    srtPage.append("datePattern=\"dd/MM/yy\"> \n\t\t\t\t\t\t");
		    srtPage.append("<a4j:support id=\"onblur\" event=\"onblur\" reRender=\"" + nome + "Field\"/> \n\t\t\t\t\t");
		    srtPage.append("</rich:calendar> \n\t\t\t\t");
		    srtPage.append("</s:decorate>\n\t\t\t");
		    break;

		case 5: // Boleanos
		    // TODO N�o implementado
		    srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/edit.xhtml\"> \n\t\t\t\t\t");
		    srtPage.append("<ui:define name=\"label\">" + nome + ":</ui:define> \n\t\t\t\t\t");
		    srtPage.append("<h:inputText id=\"" + nome + "\" value=\"#{" + entidade.getNomeVarDto() + "." + nome
			    + "}\" required=\"true\" requiredMessage=\"Favor informe o " + nome + "\"> \n\t\t\t\t\t\t");
		    srtPage.append("<a4j:support id=\"onblur\" event=\"onblur\" reRender=\"" + nome + "Field\"/> \n\t\t\t\t\t");
		    srtPage.append("</h:inputText> \n\t\t\t\t");
		    srtPage.append("</s:decorate>\n\t\t\t");
		    break;

		case 6: // TipoBoleano da prodepa

		    srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/edit.xhtml\"> \n\t\t\t\t\t");
		    srtPage.append("<ui:define name=\"label\">" + nome + ":</ui:define> \n\t\t\t\t\t");
		    srtPage.append("<h:selectOneRadio value=\"#{" + entidade.getNomeVarDto() + "." + nome
				    + "}\" layout=\"lineDirection\" required=\"true\" requiredMessage=\"Selecione o Sexo!\" class=\"compon_cadastro_rich\"> \n\t\t\t\t\t\t");
		    srtPage.append("<s:selectItems value=\"#{tiposBoleanos}\" var=\"tipo\" label=\"#{tipo.mensagem}\" /> \n\t\t\t\t\t\t");
		    srtPage.append("<s:convertEnum /> \n\t\t\t\t\t\t");
		    srtPage.append("<a4j:support id=\"onblur\" event=\"onblur\" reRender=\"" + nome + "Field\"/> \n\t\t\t\t\t");
		    srtPage.append("</h:selectOneRadio> \n\t\t\t\t");
		    srtPage.append("</s:decorate>\n\t\t\t");
		    break;

		case 7: // Objeto

		    srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/edit.xhtml\"> \n\t\t\t\t\t");
		    srtPage.append("<ui:define name=\"label\">" + nome + ":</ui:define> \n\t\t\t\t\t");
		    srtPage.append("<h:selectOneRadio value=\"#{" + entidade.getNomeVarDto() + "." + nome
			    + "}\" layout=\"lineDirection\" required=\"true\" requiredMessage=\"Selecione o " + nome
			    + "!\" class=\"compon_cadastro_rich\"> \n\t\t\t\t\t\t");
		    srtPage.append("<s:selectItems value=\"#{" + nome + "FACTORY}\" var=\"" + nome + "\" label=\"#{" + nome
			    + ".mensagem}\" /> \n\t\t\t\t\t\t");
		    srtPage.append("<s:convertEnum /> \n\t\t\t\t\t\t");
		    srtPage.append("<a4j:support id=\"onblur\" event=\"onblur\" reRender=\"" + nome + "Field\"/> \n\t\t\t\t\t");
		    srtPage.append("</h:selectOneRadio> \n\t\t\t\t");
		    srtPage.append("</s:decorate>\n\t\t\t");
		    break;

		default:
		    srtPage.append("<s:decorate id=\"" + nome + "Field\" template=\"/layout/util/edit.xhtml\"> \n\t\t\t\t\t");
		    srtPage.append("<ui:define name=\"label\">" + nome + ":</ui:define> \n\t\t\t\t\t");
		    srtPage.append("<h:inputText id=\"" + nome + "\" value=\"#{" + entidade.getNomeVarDto() + "." + nome
			    + "}\" required=\"true\" requiredMessage=\"Favor informe o " + nome + "\"> \n\t\t\t\t\t\t");
		    srtPage.append("<a4j:support id=\"onblur\" event=\"onblur\" reRender=\"" + nome + "Field\"/> \n\t\t\t\t\t");
		    srtPage.append("</h:inputText> \n\t\t\t\t");
		    srtPage.append("</s:decorate>\n\t\t\t");
		    break;
		}
	    }
	}
	return srtPage.toString();
    }


}