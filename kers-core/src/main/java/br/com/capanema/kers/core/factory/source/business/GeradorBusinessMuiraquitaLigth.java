package br.com.capanema.kers.core.factory.source.business;

import java.io.IOException;

import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.templates.CopyrightTpl;
import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorBusinessMuiraquitaLigth extends CalangoFactory {

    public static void build(EAProjectConfig projeto) throws IOException {

	System.out.println("Gerando Business para classe .......");

	StringBuffer srtBuffer;
	String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "business";

	for (Entity entidade : projeto.getEntidades()) {

	    srtBuffer = new StringBuffer();
	    String nomeClasse = entidade.getNomeEntidade() + "Business";
	    if (!entidade.getIsEnum()) {
        	    System.out.println(entidade.getNomeEntidade() + ".......");
        
        	    srtBuffer.append(CopyrightTpl.getConteudo());
        	    srtBuffer.append("package " + projeto.getPackageTree() + "business; \n\n");
        
        	    srtBuffer.append("import static "+ projeto.getPackageTree() +"business.error."+ entidade.getNomeEntidade() +"Error.*; \n \n");
        	    
        	    srtBuffer.append("import java.util.List; \n");
        	    srtBuffer.append("import java.util.ArrayList;\n");
        	    srtBuffer.append("import java.util.Date; \n");
        	    srtBuffer.append("\n \n");
        	    srtBuffer.append("import org.apache.commons.lang.StringUtils;");
        	    srtBuffer.append("\n \n");
        	    srtBuffer.append("import br.gov.prodepa.exception.InternalException; \n");
        	    srtBuffer.append("import br.gov.prodepa.exception.NegocioException; \n");
        	    srtBuffer.append("import br.gov.prodepa.exception.SistemaException; \n");
        	    srtBuffer.append("import br.gov.prodepa.exception.security.PassaportInvalidoException; \n");
        	    srtBuffer.append("import br.gov.prodepa.exception.control.AnexoManipulador; \n");
        	    srtBuffer.append("import "+ projeto.getPackageTree() +"entity."+ entidade.getNomeEntidade() +"; \n");
        	    srtBuffer.append("import "+ projeto.getPackageTree() +"dto."+ entidade.getNomeDto() +"; \n \n");
        	    
        	    srtBuffer.append("import " + projeto.getPackageTree() + "servico.LocalizacaoCDU;");
        	    srtBuffer.append("\n \n");
        
        	    srtBuffer.append("public class " + nomeClasse + " extends AbstractBusinnes {");
        
        	    srtBuffer.append("\n \n \n");
        
        	    srtBuffer.append("private " + entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade() + "; \n");
        	    srtBuffer.append("private " + entidade.getNomeDto() + " " + entidade.getNomeVarDto() + "; \n");
        	    srtBuffer.append("\n ");
        
        	    srtBuffer.append("private AnexoManipulador manipulador = new AnexoManipulador(); \n");
        	    srtBuffer.append("\n \n");
        
        	    srtBuffer.append("// Construtor para cadastro \n");
        	    srtBuffer.append("public " + nomeClasse + "(" + entidade.getNomeDto() + " " + entidade.getNomeVarDto()
        		    + ", UsuarioDto usuarioDto) { \n \t");
        	    srtBuffer.append("super.usuarioSession = usuarioDto; \n\t");
        	    srtBuffer.append("super.manipulador = this.manipulador; \n\n\t");
        
        	    srtBuffer.append("this." + entidade.getNomeVarDto() + " = " + entidade.getNomeVarDto() + "; \n");
        	    srtBuffer.append("} \n");
        	    srtBuffer.append("\n \n");
        
        	    srtBuffer.append("// Construtor para alteracao \n");
        	    srtBuffer.append("public " + nomeClasse + "(" + entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade() + ", "
        		    + entidade.getNomeDto() + " " + entidade.getNomeVarDto() + ", UsuarioDto usuarioDto) { \n \t");
        	    srtBuffer.append("super.usuarioSession = usuarioDto; \n\t");
        	    srtBuffer.append("super.manipulador = this.manipulador; \n\n\t");
        
        	    srtBuffer.append("this." + entidade.getNomeVarEntidade() + " = " + entidade.getNomeVarEntidade() + "; \n\t");
        	    srtBuffer.append("this." + entidade.getNomeVarDto() + " = " + entidade.getNomeVarDto() + "; \n");
        	    srtBuffer.append("} \n");
        	    srtBuffer.append("\n \n");
        
        	    srtBuffer.append("/** \n");
        	    srtBuffer.append("* Rotinas de validacao \n");
        	    srtBuffer.append("*/ \n \n");
        
        	    // M�todo de valida��o de cadastro
        	    srtBuffer.append("public void validarInclusao() throws NegocioException, PassaportInvalidoException, SistemaException { \n \t");
        	    srtBuffer.append("autorizarAcao(LocalizacaoCDU.CDU_MANTER_" + entidade.getNomeEntidade().toUpperCase() + "_CADASTRO); \n \t");
        	    srtBuffer.append("setarSituacaoCadastro(); \n \t");
        	    srtBuffer.append("verificarCamposObrigatorios(); \n \t");
        	    srtBuffer.append("manipulador.levantarErrosNegocio(); \n");
        	    srtBuffer.append("} \n");
        	    srtBuffer.append("\n \n");
        
        	    // M�todo de valida��o de altera��o
        	    srtBuffer.append("public void validarAlteracao() throws NegocioException, PassaportInvalidoException, SistemaException { \n \t");
        	    srtBuffer.append("autorizarAcao(LocalizacaoCDU.CDU_MANTER_" + entidade.getNomeEntidade().toUpperCase() + "_ALTERACAO); \n \t");
        	    srtBuffer.append("verificarSituacaoParaModificacao(); \n \t");
        	    srtBuffer.append("if(!manipulador.haErros()) { \n \t\t");
        	    srtBuffer.append("setarSituacaoAlteracao(); \n \t\t");
        	    srtBuffer.append("verificarCamposObrigatorios(); \n \t");
        	    srtBuffer.append("} \n \t");
        	    srtBuffer.append("manipulador.levantarErrosNegocio(); \n ");
        	    srtBuffer.append("} \n");
        	    srtBuffer.append("\n \n");
        
        	    // M�todo de valida��o de exclus�o
        	    srtBuffer.append("public void validarExclusao() throws NegocioException, PassaportInvalidoException, SistemaException { \n \t");
        	    srtBuffer.append("autorizarAcao(LocalizacaoCDU.CDU_MANTER_" + entidade.getNomeEntidade().toUpperCase() + "_EXCLUSAO); \n \t");
        	    srtBuffer.append("verificarSituacaoParaModificacao(); \n \t");
        	    srtBuffer.append("if(!manipulador.haErros()) { \n \t\t");
        	    srtBuffer.append("verificarRegrasParaExclusao(); \n \t");
        	    srtBuffer.append("} \n \t");
        	    srtBuffer.append("manipulador.levantarErrosNegocio(); \n ");
        	    srtBuffer.append("} \n");
        	    srtBuffer.append("\n \n ");
        
        	    srtBuffer.append("/** \n");
        	    srtBuffer.append("* Metodos de aux�lio � manipula��o do registro \n");
        	    srtBuffer.append("*/ \n \n");
        
        	    // Metodo para setar a situa��o do registro no cadastro
        	    srtBuffer.append("// Metodo para setar a situa��o do registro no cadastro \n");
        	    srtBuffer.append("private void setarSituacaoCadastro(){ \n \t");
        	    srtBuffer.append("this." + entidade.getNomeVarDto() + ".setSITUACAO(Situacao.ATIVO); \n \t");
        	    srtBuffer.append("this." + entidade.getNomeVarDto() + ".setDATASITUACAO(new Date()); \n");
        	    srtBuffer.append("} ");
        	    srtBuffer.append("\n \n");
        
        	    // Metodo para setar a situa��o do registro na altera��o
        	    srtBuffer.append("// Metodo para setar a situa��o do registro na altera��o \n");
        	    srtBuffer.append("private void setarSituacaoAlteracao(){ \n \t");
        	    srtBuffer.append("if(this." + entidade.getNomeVarDto() + ".getSITUACAO() != this." + entidade.getNomeVarEntidade()
        		    + ".getSITUACAO()); \n \t\t");
        	    srtBuffer.append("this." + entidade.getNomeVarDto() + ".setDATASITUACAO(new Date()); \n \t");
        	    srtBuffer.append("} \n");
        	    srtBuffer.append("} ");
        	    srtBuffer.append("\n \n");
        
        	    srtBuffer.append("/** \n");
        	    srtBuffer.append("* Metodos de auxilio � valida��o \n");
        	    srtBuffer.append("*/ \n \n");
        
        	    // M�todo de valida��o de campos obrigat�rios
        	    srtBuffer.append("//TODO Auto-generated method stub \n");
        	    srtBuffer.append("// Verifica se os pampos de preechimento obrigat�rio est�o setados \n");
        	    srtBuffer.append("private void verificarCamposObrigatorios() { \n \t");
        
        	    for (Attribute atributo : entidade.getAtributos()) {
        
        		// if (atributo.getTipo() == TipoDados.STRING) {
        		if (atributo.getTipo().equals("String")) {
        		    srtBuffer.append("\n\t");
        		    srtBuffer.append("if(StringUtils.isEmpty(this." + entidade.getNomeVarDto()+ "." + atributo.getGetMetodo() + "())) { \n \t\t");
        		    srtBuffer.append("manipulador.ERRO_VALIDACAO(" + atributo.getNome().toUpperCase() + "_NULL); \n \t");
        		    srtBuffer.append("}");
        		} else
        
        		if (atributo.getTipo().equals("Integer") || atributo.getTipo().equals("int")) {
        		    srtBuffer.append("\n\t");
        		    srtBuffer.append("if(this." + entidade.getNomeVarDto() + "." + atributo.getGetMetodo() + "() == 0) { \n \t\t");
        		    srtBuffer.append("manipulador.ERRO_VALIDACAO(" + atributo.getNome().toUpperCase() + "_NULL); \n \t");
        		    srtBuffer.append("}");
        		}
        		else {
        		    srtBuffer.append("\n\t");
        		    srtBuffer.append("if(this." + entidade.getNomeVarDto() + "." + atributo.getGetMetodo() + "() == null) { \n \t\t");
        		    srtBuffer.append("manipulador.ERRO_VALIDACAO(" + atributo.getNome().toUpperCase() + "_NULL); \n \t");
        		    srtBuffer.append("}");
        		}
        	    }
        
        	    srtBuffer.append(" \n");
        	    srtBuffer.append("} \n");
        	    srtBuffer.append("\n \n");
        
        	    // Metodo para verificar se a situa��o do registro permite
        	    // modifica��es
        	    srtBuffer.append("// Metodo para verificar se a situa��o do registro permite modifica��es \n");
        	    srtBuffer.append("private void verificarSituacaoParaModificacao(){ \n \t");
        	    srtBuffer.append("if(true) { \n \t\t");
        	    srtBuffer.append("manipulador.ERRO_VALIDACAO(" + entidade.getNomeEntidade().toUpperCase() + "_INATIVO); \n \t");
        	    srtBuffer.append("} \n");
        	    srtBuffer.append("} ");
        	    srtBuffer.append("\n \n");
        
        	    // Metodo para verificar se o registro pode ser excluido
        	    srtBuffer.append("// Metodo para verificar se o registro pode ser excluido \n");
        	    srtBuffer.append("private void verificarRegrasParaExclusao(){ \n \t");
        	    srtBuffer.append("if(true) { \n \t\t");
        	    srtBuffer.append("manipulador.ERRO_VALIDACAO(" + entidade.getNomeEntidade().toUpperCase() + "_IMPEDIDO_EXCLUSAO); \n \t");
        	    srtBuffer.append("} \n");
        	    srtBuffer.append("} ");
        	    srtBuffer.append("\n \n");
        
        	    srtBuffer.append("\n \n \n");
        
        	    // Fechamento da classe
        	    srtBuffer.append("}");
        	    // FileManager.gerarDiretorio(caminho);
        	    FileManager.createFife(nomeClasse + ".java", caminho, srtBuffer.toString());
	    }

	}
    }

}