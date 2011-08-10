package br.com.capanema.kers.core.factory.source.businessError;

import java.io.IOException;

import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.templates.CopyrightTpl;
import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorBusinessErrorMuiraquita extends CalangoFactory {

    public static void build(EAProjectConfig projeto) throws IOException {

	System.out.println("Gerando BusinessError .......");

	StringBuffer srtBuffer;
	String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "business/error";

	for (Entity entidade : projeto.getEntidades()) {

	    srtBuffer = new StringBuffer();
	    String nomeClasse = entidade.getNomeEntidade() + "Error";

	    if (!entidade.getIsEnum()) {
		
		System.out.println(entidade.getNomeEntidade() + ".......");

		srtBuffer.append(CopyrightTpl.getConteudo());
		srtBuffer.append("package " + projeto.getPackageTree() + "business.error; \n\n");

		srtBuffer.append("\n \n");
		srtBuffer.append("import " + projeto.getPackageTree() + "servico.LocalizacaoCDU; \n");
		srtBuffer.append("import br.gov.prodepa.exception.retorno.retenum.IReturnError; \n");
		srtBuffer.append("\n \n");

		srtBuffer.append("public enum " + nomeClasse + " implements IReturnError { \n\n\n");

		int next = 1;
		for (Attribute atributo : entidade.getAtributos()) {
		    srtBuffer.append("\t");
		    srtBuffer.append(atributo.getNome().toUpperCase() + "_NULL(" + next + ", \" Informe o/a " + atributo.getNome() + ". \"),\n ");
		    next++;
		}
		srtBuffer.append("\t");
		srtBuffer.append(entidade.getNomeEntidade().toUpperCase() + "_INATIVO(" + next + ", \" O/A "
			+ entidade.getNomeEntidade().toUpperCase() + " esta inativo . \"),\n ");

		srtBuffer.append("\t");
		srtBuffer.append(entidade.getNomeEntidade().toUpperCase().toUpperCase() + "_IMPEDIDO_EXCLUSAO(" + (next + 1) + ", \" O/A "
			+ entidade.getNomeEntidade().toUpperCase() + " nao pode ser excluido .\"); \n ");

		srtBuffer.append(" \n");
		srtBuffer.append("\n \n");

		srtBuffer.append("\t");
		srtBuffer.append("private String mensagemErro; \n \t");
		srtBuffer.append("private int codigo;  \n ");
		srtBuffer.append("\n \t");
		srtBuffer.append("private " + nomeClasse + "(int codigo, String mensagemErro) {\n \t\t");
		srtBuffer.append("this.codigo = codigo; \n \t\t");
		srtBuffer.append("this.mensagemErro = mensagemErro; \n \t");
		srtBuffer.append("} ");
		srtBuffer.append("\n \n");

		srtBuffer.append("\t");
		srtBuffer.append("public String obterMensagemErro() { \n \t\t");
		srtBuffer.append("return mensagemErro; \n \t");
		srtBuffer.append("} ");
		srtBuffer.append("\n \n");

		srtBuffer.append("\t");
		srtBuffer.append("public int obterCodigo() { \n \t\t");
		srtBuffer.append("return codigo; \n \t");
		srtBuffer.append("} ");
		srtBuffer.append("\n \n");

		srtBuffer.append("\t");
		srtBuffer.append("public String obterLocalizacao() { \n \t\t");
		srtBuffer.append("return LocalizacaoCDU.CDU_MANTER_" + entidade.getNomeEntidade().toUpperCase() + "; \n \t");
		srtBuffer.append("} ");
		srtBuffer.append("\n \n");

		// Fechamento da classe
		srtBuffer.append("}");
		// FileManager.gerarDiretorio(caminho);
		FileManager.createFife(nomeClasse + ".java", caminho, srtBuffer.toString());
	    }
	    }
    }

}