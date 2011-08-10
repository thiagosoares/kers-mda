package br.com.capanema.kers.core.factory.source.service;

import java.io.IOException;

import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.templates.CopyrightTpl;
import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorServicoCalango extends CalangoFactory {

    public static void build(EAProjectConfig projeto) throws IOException {

	System.out.println("Gerando Servico para classe .......");

	StringBuffer srtBuffer;
	String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "servico";

	for (Entity entidade : projeto.getEntidades()) {

	    srtBuffer = new StringBuffer();
	    String nomeClasse = entidade.getNomeEntidade() + "ServicoBean";

	    srtBuffer.append(CopyrightTpl.getConteudo());
	    srtBuffer.append("package " + projeto.getPackageTree() + "servico; \n\n");

	    srtBuffer.append("\n \n");
	    srtBuffer.append("import java.util.List;");
	    srtBuffer.append("\n \n");
	    srtBuffer.append("import javax.ejb.Stateless; \n");
	    srtBuffer.append("import javax.persistence.EntityManager; \n");
	    srtBuffer.append("import javax.persistence.PersistenceContext; \n");
	    srtBuffer.append("\n");
	    srtBuffer.append("import java.util.List; \n");
	    srtBuffer.append("import java.util.ArrayList; \n");
	    srtBuffer.append("import org.apache.log4j.Logger; \n");
	    srtBuffer.append("import org.apache.log4j.LogManager; \n");
	    srtBuffer.append("\n");
	    
	    srtBuffer.append("import org.jboss.seam.ScopeType;");
	    srtBuffer.append("\n");
	    srtBuffer.append("import org.jboss.seam.annotations.In;");
	    srtBuffer.append("\n");
	    srtBuffer.append("import org.jboss.seam.annotations.End;");
	    srtBuffer.append("\n");
	    srtBuffer.append("import org.jboss.seam.annotations.Begin;");
	    srtBuffer.append("\n");
	    srtBuffer.append("import org.jboss.seam.annotations.Name;");
	    srtBuffer.append("\n");
	    srtBuffer.append("import org.jboss.seam.annotations.Scope;");
	    srtBuffer.append("\n");
	    srtBuffer.append("import org.jboss.seam.annotations.Out;");
	    srtBuffer.append("\n");
	    srtBuffer.append("import org.jboss.seam.annotations.datamodel.DataModel;");
	    srtBuffer.append("\n");
	    srtBuffer.append("import org.jboss.seam.annotations.datamodel.DataModelSelection;");
	    
	    srtBuffer.append("\n \n");
	    
	    srtBuffer.append("import br.gov.prodepa.exception.InternalException; \n");
	    srtBuffer.append("import br.gov.prodepa.exception.NegocioException; \n");
	    srtBuffer.append("import br.gov.prodepa.exception.SistemaException; \n");
	    srtBuffer.append("import br.gov.prodepa.exception.security.PassaportInvalidoException; \n");
	    srtBuffer.append("\n \n");
	    
	    srtBuffer.append("import "+ projeto.getPackageTree() +"entity."+ entidade.getNomeEntidade() +"; \n");
	    srtBuffer.append("import "+ projeto.getPackageTree() +"dao.I"+ entidade.getNomeDao() +"; \n");
	    srtBuffer.append("import "+ projeto.getPackageTree() +"dao."+ entidade.getNomeDao() +"; \n \n");
	    srtBuffer.append("import "+ projeto.getPackageTree() +"entity.Usuario; \n \n");
	    
	    srtBuffer.append("import "+ projeto.getPackageTree() +"business."+ entidade.getNomeEntidade() +"Business; \n \n");
	    
	    srtBuffer.append("\n \n");

	    srtBuffer.append("@Stateless \n");
	    srtBuffer.append("@Scope(ScopeType.CONVERSATION) \n");
	    srtBuffer.append("@Name(\"" + entidade.getNomeVarAction() + "\") \n");
	    srtBuffer.append("public class " + nomeClasse + " extends AbstractServico implements " + entidade.getNomeEntidade() + "ServicoLocal {");

	    srtBuffer.append("\n \n \n");

	    srtBuffer.append("@PersistenceContext \n");
	    srtBuffer.append("EntityManager em;");
	    srtBuffer.append("\n \n");
	    
	    srtBuffer.append("@In(create = true, required = false)");
	    srtBuffer.append("\n");
	    srtBuffer.append("@Out(required = false)");
	    srtBuffer.append("\n");
	    srtBuffer.append(entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade() + ";");
	    srtBuffer.append("\n \n");

	    srtBuffer.append("@DataModel");
	    srtBuffer.append("\n");
	    srtBuffer.append("List<" + entidade.getNomeEntidade() + "> " + entidade.getNomeVarEntidade() + "Lista;");
	    srtBuffer.append("\n \n");

	    srtBuffer.append("@DataModelSelection(\"" + entidade.getNomeVarEntidade() + "Lista\")");
	    srtBuffer.append("\n");
	    srtBuffer.append(entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade() + "Selecionado;");
	    srtBuffer.append("\n \n");

	    srtBuffer.append("/** Auditoria **/ \n");
	    srtBuffer.append("\n");

	    srtBuffer.append("@In(required = true) \n");
	    srtBuffer.append("Usuario usuario; \n");

	    srtBuffer.append("\n");

	    srtBuffer.append("private I" + entidade.getNomeDao() + " " + entidade.getNomeVarDao() + "; \n");
	    srtBuffer.append("private I" + entidade.getNomeDao() + " get" + entidade.getNomeDao() + "() { \n\t");
	    srtBuffer.append("if (" + entidade.getNomeVarDao() + " == null) { \n\t");
	    srtBuffer.append(entidade.getNomeVarDao() + " = new " + entidade.getNomeDao() + "(em); \n\t");
	    srtBuffer.append("} \n");
	    srtBuffer.append("return " + entidade.getNomeVarDao() + "; \n");
	    srtBuffer.append("} \n");
	    srtBuffer.append("\n \n");

	    srtBuffer.append("/*");
	    srtBuffer.append("\n");
	    srtBuffer.append("* ---------- ********************** Rotinas de neg�cio ************************ ---------");
	    srtBuffer.append("\n");
	    srtBuffer.append("*/");
	    srtBuffer.append("\n \n");

	    // M�todo de cadastro
	    srtBuffer.append("public " + entidade.getNomeEntidade() + " cadastrar(" + entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade()
		    + ", Usuario usuario) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n \t");
	   
	    srtBuffer.append("new " + entidade.getNomeEntidade() + "Business(" + entidade.getNomeVarEntidade() + ", usuario).validarInclusao(); \n \t");
	    srtBuffer.append(entidade.getNomeVarEntidade() + " = get" + entidade.getNomeDao() + "().create(" + entidade.getNomeVarEntidade() + "); \n \t");
	    srtBuffer.append("return " + entidade.getNomeEntidade().toLowerCase() + "; \n");
	    srtBuffer.append("} \n");
	    srtBuffer.append("\n \n");

	    // M�todo de alteracao
	    srtBuffer.append("public " + entidade.getNomeEntidade() + " alterar(" + entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade()
		    + ", Usuario usuario) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n \t");
	    srtBuffer.append(entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade() + "Update = " +
	    		"get" + entidade.getNomeDao() + "().findById(" + entidade.getNomeVarEntidade() + ".getId()); \n \t");
	    srtBuffer.append("new " + entidade.getNomeEntidade() + "Business(" + entidade.getNomeVarEntidade() + ", "
		    + entidade.getNomeVarEntidade() + "Update, usuario).validarAlteracao(); \n \t");
	    srtBuffer.append(entidade.getNomeVarEntidade() + " = get" + entidade.getNomeDao() + "().update(" + entidade.getNomeVarEntidade() + "); \n \t");
	    srtBuffer.append("return " + entidade.getNomeVarEntidade() + "; \n");
	    srtBuffer.append("} \n");
	    srtBuffer.append("\n \n");

	    // M�todo de busca
	    srtBuffer.append("public List<" + entidade.getNomeEntidade() + "> buscar(" + entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade()
		    + ", Usuario usuario) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n \t");
	    srtBuffer.append("//new " + entidade.getNomeEntidade() + "Business(" + entidade.getNomeVarEntidade() + ", usuario).validarBuscas(); \n \t");
	    srtBuffer.append("List<" + entidade.getNomeEntidade() + "> " + entidade.getNomeVarEntidade() + "Lista = get"
		    + entidade.getNomeDao() + "().findByParameters(" + entidade.getNomeVarEntidade() + "); \n \t");
	    srtBuffer.append("return " + entidade.getNomeVarEntidade() + "Lista; \n");
	    srtBuffer.append("} \n");
	    srtBuffer.append("\n \n");

	    // M�todo de exclusao
	    srtBuffer.append("public void excluir(" + entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade()
		    + ", Usuario usuario) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n \t");
	    srtBuffer.append(entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade() + "Excluir = get" + entidade.getNomeDao()
		    + "().findById(" + entidade.getNomeVarEntidade() + ".getId()); \n \t");
	    srtBuffer.append("new " + entidade.getNomeEntidade() + "Business(" + entidade.getNomeVarEntidade() + "Excluir, usuario).validarExclusao(); \n \t");
	    srtBuffer.append("get" + entidade.getNomeDao() + "().delete(" + entidade.getNomeVarEntidade() + "Excluir); \n");
	    srtBuffer.append("} \n");

	    srtBuffer.append("\n \n \n");

	    srtBuffer.append("}");

	    // FileManager.gerarDiretorio(caminho);
	    FileManager.createFife(nomeClasse + ".java", caminho, srtBuffer.toString());

	    gerarInterfaceLocal(projeto);
	}
    }

    public static void gerarInterfaceLocal(EAProjectConfig projeto) throws IOException {

	StringBuffer srtBuffer;
	String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "servico";

	for (Entity entidade : projeto.getEntidades()) {
	    System.out.println("Gerando Interface local para o Servico da classe " + entidade.getNomeEntidade() + " .......");

	    srtBuffer = new StringBuffer();
	    String nomeClasse = entidade.getNomeEntidade() + "ServicoLocal";

	    srtBuffer.append(CopyrightTpl.getConteudo());
	    srtBuffer.append("package " + projeto.getPackageTree() + "servico; \n\n");

	    srtBuffer.append("\n \n");
	    srtBuffer.append("import java.util.List;");
	    srtBuffer.append("\n \n");
	    srtBuffer.append("import javax.ejb.Local;\n");
	    srtBuffer.append("\n");
	    
	    srtBuffer.append("import "+ projeto.getPackageTree() +"entity.Usuario; \n");
	    srtBuffer.append("import "+ projeto.getPackageTree() +"entity."+ entidade.getNomeEntidade() +"; \n");
	    
	    srtBuffer.append("import br.gov.prodepa.exception.InternalException; \n");
	    srtBuffer.append("import br.gov.prodepa.exception.NegocioException; \n");
	    srtBuffer.append("import br.gov.prodepa.exception.SistemaException; \n");
	    srtBuffer.append("import br.gov.prodepa.exception.security.PassaportInvalidoException; \n");
	    srtBuffer.append("\n \n");

	    srtBuffer.append("/** \n");
	    srtBuffer.append("* Interface declarativa de rotinas com escopo local.  \n");
	    srtBuffer.append("*/ \n");

	    srtBuffer.append("@Local \n");
	    srtBuffer.append("public interface " + nomeClasse + " {");

	    srtBuffer.append("\n \n \n");

	    // M�todo de cadastro
	    srtBuffer.append("public " + entidade.getNomeEntidade() + " cadastrar(" + entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade()
		    + ", Usuario usuario) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException; \n");

	    // M�todo de alteracao
	    srtBuffer.append("public " + entidade.getNomeEntidade() + " alterar(" + entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade()
		    + ", Usuario usuario) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException; \n");

	    // M�todo de busca
	    srtBuffer.append("public List<" + entidade.getNomeEntidade() + "> buscar(" + entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade()
		    + ", Usuario usuario) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException; \n");

	    // M�todo de exclusao
	    srtBuffer.append("public void excluir(" + entidade.getNomeEntidade() + " " + entidade.getNomeVarEntidade()
		    + ", Usuario usuario) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException; \n");

	    srtBuffer.append("\n \n \n");

	    srtBuffer.append("}");

	    // FileManager.gerarDiretorio(caminho);
	    FileManager.createFife(nomeClasse + ".java", caminho, srtBuffer.toString());
	}
    }

}