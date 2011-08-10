package br.com.capanema.kers.core.factory.source.facade;

import java.io.IOException;
import java.util.List;

import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.templates.CopyrightTpl;
import br.com.capanema.kers.core.util.file.FileManager;

//TODO Deve suportar geracao para todos os tipo: Completa, mirin
public class GeradorFacadeMuiraquita extends CalangoFactory {

    public static void build(EAProjectConfig projeto) throws IOException {

	System.out.print("Gerando fachada " + projeto.getNomeFachada() + "Facade .......");
	StringBuffer srtBuffer = new StringBuffer();
	
	String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "facade";

	srtBuffer.append(CopyrightTpl.getConteudo());
	srtBuffer.append("package " + projeto.getPackageTree() + "facade; \n\n");
	srtBuffer.append("\n \n");
	srtBuffer.append("import java.util.List;");
	srtBuffer.append("import java.util.ArrayList;");
	srtBuffer.append("\n \n");
	srtBuffer.append("import javax.ejb.EJB; \n");
	srtBuffer.append("import javax.ejb.Stateless; \n");
	srtBuffer.append("import javax.ejb.Remove; \n");
	srtBuffer.append("import org.jboss.annotation.ejb.LocalBinding; \n");
	srtBuffer.append("import org.jboss.annotation.ejb.RemoteBinding; \n");
	srtBuffer.append("import org.apache.log4j.Logger; \n");
	srtBuffer.append("import org.apache.log4j.LogManager; ");

	srtBuffer.append("\n \n");

	srtBuffer.append("import br.gov.prodepa.exception.InternalException; \n");
	srtBuffer.append("import br.gov.prodepa.exception.NegocioException; \n");
	srtBuffer.append("import br.gov.prodepa.exception.SistemaException; \n");
	srtBuffer.append("import br.gov.prodepa.exception.security.PassaportInvalidoException; \n");
	srtBuffer.append("\n \n");

	srtBuffer.append("@Stateless \n");
	srtBuffer.append("public class " + projeto.getNomeFachada() + "FacadeBean implements " + projeto.getNomeFachada() + "FacadeLocal, "
		+ projeto.getNomeFachada() + "FacadeRemote {");

	srtBuffer.append("\n \n ");

	srtBuffer.append("private static Logger log = LogManager.getLogger(" + projeto.getNomeFachada() + "FacadeBean.class);");

	srtBuffer.append("\n \n ");

	// Gerar Injecoes
	for (Entity entidade : projeto.getEntidades()) {
	    if (!entidade.getIsEnum()) {
		srtBuffer.append(gerarInjecoes(entidade));
	    }
	}

	srtBuffer.append("/** \n");
	srtBuffer.append("* \n");
	srtBuffer.append("* =========== Rotinas =========== \n");
	srtBuffer.append("* \n");
	srtBuffer.append("*/ \n \n");

	// Gerar Metodos
	for (Entity entidade : projeto.getEntidades()) {
	    if (!entidade.getIsEnum()) {
        	    srtBuffer.append("/** \n");
        	    srtBuffer.append("* \n");
        	    srtBuffer.append("* Rotinas de " + entidade.getNomeEntidade() + "\n");
        	    srtBuffer.append("* \n");
        	    srtBuffer.append("*/ \n");
        
        	    srtBuffer.append(gerarMetodos(entidade));
	    }
	}

	srtBuffer.append("\n \n \n");

	srtBuffer.append("}");
	
	// FileManager.gerarDiretorio(caminho);
	FileManager.createFife(projeto.getNomeFachada() + "FacadeBean.java", caminho, srtBuffer.toString());

	gerarInterfaceLocal(projeto);
	gerarInterfaceRemota(projeto);
    }

    public static void gerarInterfaceLocal(EAProjectConfig projeto) throws IOException {

	String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "facade";
	StringBuffer srtBuffer = new StringBuffer();

	srtBuffer.append("package " + projeto.getPackageTree() + "facade; \n\n");
	srtBuffer.append("\n \n");
	srtBuffer.append("import java.util.List;");
	srtBuffer.append("\n \n");
	srtBuffer.append("import javax.ejb.Local; \n");

	srtBuffer.append("\n \n");

	srtBuffer.append("import br.gov.prodepa.exception.InternalException; \n");
	srtBuffer.append("import br.gov.prodepa.exception.NegocioException; \n");
	srtBuffer.append("import br.gov.prodepa.exception.SistemaException; \n");
	srtBuffer.append("import br.gov.prodepa.exception.security.PassaportInvalidoException; \n");
	srtBuffer.append("\n \n");

	srtBuffer.append("@Local \n");
	srtBuffer.append("public interface " + projeto.getNomeFachada() + "FacadeLocal {");

	srtBuffer.append("\n \n ");

	// Gerar Metodos
	for (Entity entidade : projeto.getEntidades()) {
	    if (!entidade.getIsEnum()) {
        	    srtBuffer.append("/** \n");
        	    srtBuffer.append("* \n");
        	    srtBuffer.append("* Assinaturas de " + entidade.getNomeEntidade() + "\n");
        	    srtBuffer.append("* \n");
        	    srtBuffer.append("*/ \n");
        
        	    srtBuffer.append(gerarAssinaturasMetodos(entidade));
	    }
	}

	srtBuffer.append("\n \n \n");

	srtBuffer.append("}");

	// FileManager.gerarDiretorio(caminho);
	FileManager.createFife(projeto.getNomeFachada() + "FacadeLocal.java", caminho, srtBuffer.toString());

    }

    public static void gerarInterfaceRemota(EAProjectConfig projeto) throws IOException {

	String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "facade";
	StringBuffer srtBuffer = new StringBuffer();

	srtBuffer.append("package " + projeto.getPackageTree() + "facade; \n\n");
	srtBuffer.append("\n \n");
	srtBuffer.append("import java.util.List;");
	srtBuffer.append("\n \n");
	srtBuffer.append("import javax.ejb.Remote; \n");

	srtBuffer.append("\n \n");

	srtBuffer.append("import br.gov.prodepa.exception.InternalException; \n");
	srtBuffer.append("import br.gov.prodepa.exception.NegocioException; \n");
	srtBuffer.append("import br.gov.prodepa.exception.SistemaException; \n");
	srtBuffer.append("import br.gov.prodepa.exception.security.PassaportInvalidoException; \n");
	srtBuffer.append("\n \n");

	srtBuffer.append("@Remote \n");
	srtBuffer.append("public interface " + projeto.getNomeFachada() + "FacadeRemote {");

	srtBuffer.append("\n \n ");

	srtBuffer.append("}");

	// FileManager.gerarDiretorio(caminho);
	FileManager.createFife(projeto.getNomeFachada() + "FacadeRemote.java", caminho, srtBuffer.toString());

    }

    private static String gerarInjecoes(Entity entidade) {

	StringBuffer srtInjec = new StringBuffer();
	srtInjec.append("\t");
	srtInjec.append("/* ----- Servi�o de " + entidade.getNomeEntidade() + " ----- */ \n \t");
	srtInjec.append("@EJB \n \t");
	srtInjec.append("private " + entidade.getNomeEntidade() + "ServicoLocal " + entidade.getNomeEntidade().toLowerCase() + "Servico; \t");
	srtInjec.append("\n \n");

	return srtInjec.toString();
    }

    private static String gerarMetodos(Entity entidade) {

	StringBuffer srtMetodo = new StringBuffer();

	String nomeVariavelServico = entidade.getNomeEntidade().toLowerCase() + "Servico";

	// M�todo de cadastro
	srtMetodo.append("public " + entidade.getNomeDto() + " cadastrar" + entidade.getNomeEntidade() + "(" + entidade.getNomeDto() + " "
		+ entidade.getNomeVarDto()
		+ ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n \t");
	srtMetodo.append("return " + nomeVariavelServico + ".cadastrar(" + entidade.getNomeVarDto() + ", usuarioDto); \n");
	srtMetodo.append("}");
	srtMetodo.append("\n \n");

	// M�todo de alteracao
	srtMetodo.append("public " + entidade.getNomeDto() + " alterar" + entidade.getNomeEntidade() + "(" + entidade.getNomeDto() + " "
		+ entidade.getNomeVarDto()
		+ ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n \t");
	srtMetodo.append("return " + nomeVariavelServico + ".alterar(" + entidade.getNomeVarDto() + ", usuarioDto); \n");
	srtMetodo.append("}");
	srtMetodo.append("\n \n");

	// M�todo de busca
	srtMetodo.append("public List<" + entidade.getNomeDto() + "> buscar" + entidade.getNomeEntidade() + "s(" + entidade.getNomeDto() + " "
		+ entidade.getNomeVarDto()
		+ ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n \t");
	srtMetodo.append("return " + nomeVariavelServico + ".buscar(" + entidade.getNomeVarDto() + ", usuarioDto); \n");
	srtMetodo.append("}");
	srtMetodo.append("\n \n");

	// M�todo de exclusao
	srtMetodo.append("public void excluir" + entidade.getNomeEntidade() + "(" + entidade.getNomeDto() + " " + entidade.getNomeVarDto()
		+ ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n \t");
	srtMetodo.append(nomeVariavelServico + ".excluir(" + entidade.getNomeVarDto() + ", usuarioDto); \n");
	srtMetodo.append("}");
	srtMetodo.append("\n \n");

	return srtMetodo.toString();
    }

    private static String gerarAssinaturasMetodos(Entity entidade) {

	StringBuffer srtMetodo = new StringBuffer();

	// String nomeClasse = entidade.getClass().getSimpleName() +
	// "ServicoBean";

	// M�todo de cadastro
	srtMetodo.append("public " + entidade.getNomeDto() + " cadastrar" + entidade.getNomeEntidade() + "(" + entidade.getNomeDto() + " "
		+ entidade.getNomeVarDto()
		+ ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException; \t");
	srtMetodo.append("\n \n");

	// M�todo de alteracao
	srtMetodo.append("public " + entidade.getNomeDto() + " alterar" + entidade.getNomeEntidade() + "(" + entidade.getNomeDto() + " "
		+ entidade.getNomeVarDto()
		+ ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException; \t");
	srtMetodo.append("\n \n");

	// M�todo de busca
	srtMetodo.append("public List<" + entidade.getNomeDto() + "> buscar" + entidade.getNomeEntidade() + "s(" + entidade.getNomeDto() + " "
		+ entidade.getNomeVarDto()
		+ ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException; \t");
	srtMetodo.append("\n \n");

	// M�todo de exclusao
	srtMetodo.append("public void excluir" + entidade.getNomeEntidade() + "(" + entidade.getNomeDto() + " " + entidade.getNomeVarDto()
		+ ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException; \t");
	srtMetodo.append("\n \n");

	return srtMetodo.toString();
    }

}