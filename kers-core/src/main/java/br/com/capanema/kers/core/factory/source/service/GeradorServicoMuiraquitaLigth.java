package br.com.capanema.kers.core.factory.source.service;

import java.io.IOException;

import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.templates.CopyrightTpl;
import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorServicoMuiraquitaLigth extends CalangoFactory {

  public static void build(EAProjectConfig projeto) throws IOException {

    System.out.println("Gerando Servico para classe .......");

    StringBuffer srtBuffer;
    String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "servico";

    for (Entity entidade : projeto.getEntidades()) {

      srtBuffer = new StringBuffer();
      String nomeClasse = entidade.getNomeEntidade() + "ServicoBean";
      if (!entidade.getIsEnum()) {
        srtBuffer.append(CopyrightTpl.getConteudo());
        srtBuffer.append("package " + projeto.getPackageTree() + "servico; \n\n");

        srtBuffer.append("\n \n");
        srtBuffer.append("import java.util.List;");
        srtBuffer.append("\n \n");
        srtBuffer.append("import javax.ejb.Stateless; \n");
        srtBuffer.append("import javax.persistence.EntityManager; \n");
        srtBuffer.append("import javax.persistence.PersistenceContext; \n");
        srtBuffer.append("\n");
        srtBuffer.append("import br.gov.prodepa.exception.InternalException; \n");
        srtBuffer.append("import br.gov.prodepa.exception.NegocioException; \n");
        srtBuffer.append("import br.gov.prodepa.exception.SistemaException; \n");
        srtBuffer.append("import br.gov.prodepa.exception.security.PassaportInvalidoException; \n");
        srtBuffer.append("\n \n");

        srtBuffer.append("import br.gov.pa.controleacesso.dto.UsuarioDto; \n");

        srtBuffer.append("import " + projeto.getPackageTree() + "entity." + entidade.getNomeEntidade() + "; \n");
        srtBuffer.append("import " + projeto.getPackageTree() + "dto." + entidade.getNomeDto() + "; \n \n");

        srtBuffer.append("import " + projeto.getPackageTree() + "dao.I" + entidade.getNomeDao() + "; \n");
        srtBuffer.append("import " + projeto.getPackageTree() + "dao." + entidade.getNomeDao() + "; \n \n");

        srtBuffer.append("import " + projeto.getPackageTree() + "business." + entidade.getNomeEntidade() + "Business; \n \n");
        srtBuffer.append("import " + projeto.getPackageTree() + "converter." + entidade.getNomeEntidade() + "Converter; \n \n");

        srtBuffer.append("\n \n");

        srtBuffer.append("@Stateless \n");
        srtBuffer.append("public class " + nomeClasse + " extends AbstractServico implements " + entidade.getNomeEntidade() + "ServicoLocal {");

        srtBuffer.append("\n \n \n");

        srtBuffer.append("@PersistenceContext \n");
        srtBuffer.append("EntityManager em;");
        srtBuffer.append("\n \n");

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
        srtBuffer.append("public " + entidade.getNomeDto() + " cadastrar(" + entidade.getNomeDto() + " " + entidade.getNomeVarDto()
                + ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n \t");
        // srtDto.append(nomeEntidade + " " + nomeEntidade.toLowerCase()+
        // " = new "
        // + nomeEntidade+ "(); \n");
        srtBuffer.append("new " + entidade.getNomeEntidade() + "Business(" + entidade.getNomeVarDto() + ", usuarioDto).validarInclusao(); \n \t");
        srtBuffer.append(entidade.getNomeEntidade() + " " + entidade.getNomeEntidade().toLowerCase() + " = " + entidade.getNomeEntidade()
                + "Converter.get" + entidade.getNomeEntidade() + "(" + entidade.getNomeVarDto() + "); \n \t");
        srtBuffer.append("get" + entidade.getNomeDao() + "().create(" + entidade.getNomeEntidade().toLowerCase() + "); \n \t");
        srtBuffer.append("return " + entidade.getNomeEntidade() + "Converter.get" + entidade.getNomeDto() + "("
                + entidade.getNomeEntidade().toLowerCase() + "); \n");
        srtBuffer.append("} \n");
        srtBuffer.append("\n \n");

        // M�todo de alteracao
        srtBuffer.append("public " + entidade.getNomeDto() + " alterar(" + entidade.getNomeDto() + " " + entidade.getNomeVarDto()
                + ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n \t");
        srtBuffer.append(entidade.getNomeEntidade() + " " + entidade.getNomeEntidade().toLowerCase() + " = get" + entidade.getNomeDao()
                + "().findById(" + entidade.getNomeVarDto() + ".getId()); \n \t");
        srtBuffer.append("new " + entidade.getNomeEntidade() + "Business(" + entidade.getNomeEntidade().toLowerCase() + ", "
                + entidade.getNomeVarDto() + ", usuarioDto).validarAlteracao(); \n \t");
        srtBuffer.append(entidade.getNomeEntidade() + "Converter.get" + entidade.getNomeEntidade() + "Persistido("
                + entidade.getNomeEntidade().toLowerCase() + ", " + entidade.getNomeVarDto() + "); \n \t");
        srtBuffer.append("get" + entidade.getNomeDao() + "().update(" + entidade.getNomeEntidade().toLowerCase() + "); \n \t");
        srtBuffer.append("return " + entidade.getNomeEntidade() + "Converter.get" + entidade.getNomeDto() + "("
                + entidade.getNomeEntidade().toLowerCase() + "); \n");
        srtBuffer.append("} \n");
        srtBuffer.append("\n \n");

        // M�todo de busca
        srtBuffer.append("public List<" + entidade.getNomeDto() + "> buscar(" + entidade.getNomeDto() + " " + entidade.getNomeVarDto()
                + ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n \t");
        srtBuffer.append("//new " + entidade.getNomeEntidade() + "Business(" + entidade.getNomeVarDto() + ", usuarioDto).validarBuscas(); \n \t");
        srtBuffer.append("List<" + entidade.getNomeEntidade() + "> " + entidade.getNomeEntidade().toLowerCase() + "Lista = get"
                + entidade.getNomeDao() + "().findByParameters(" + entidade.getNomeVarDto() + "); \n \t");
        srtBuffer.append("return " + entidade.getNomeEntidade() + "Converter.get" + entidade.getNomeDto() + "Lista("
                + entidade.getNomeEntidade().toLowerCase() + "Lista); \n");
        srtBuffer.append("} \n");
        srtBuffer.append("\n \n");

        // M�todo de exclusao
        srtBuffer.append("public void excluir(" + entidade.getNomeDto() + " " + entidade.getNomeVarDto()
                + ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n \t");
        srtBuffer.append(entidade.getNomeEntidade() + " " + entidade.getNomeEntidade().toLowerCase() + " = get" + entidade.getNomeDao()
                + "().findById(" + entidade.getNomeVarDto() + ".getId()); \n \t");
        srtBuffer.append("new " + entidade.getNomeEntidade() + "Business(" + entidade.getNomeEntidade().toLowerCase() + ", "
                + entidade.getNomeVarDto() + ", usuarioDto).validarExclusao(); \n \t");
        srtBuffer.append("get" + entidade.getNomeDao() + "().delete(" + entidade.getNomeEntidade().toLowerCase() + "); \n");
        srtBuffer.append("} \n");

        srtBuffer.append("\n \n \n");

        srtBuffer.append("}");

        // FileManager.gerarDiretorio(caminho);
        FileManager.createFife(nomeClasse + ".java", caminho, srtBuffer.toString());

      }
    }
    gerarInterfaceLocal(projeto);
  }

  public static void gerarInterfaceLocal(EAProjectConfig projeto) throws IOException {

    StringBuffer srtBuffer;
    String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "servico";

    for (Entity entidade : projeto.getEntidades()) {
      if (!entidade.getIsEnum()) {
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

        srtBuffer.append("import br.gov.pa.controleacesso.dto.UsuarioDto; \n");

        srtBuffer.append("import " + projeto.getPackageTree() + "dto." + entidade.getNomeDto() + "; \n \n");

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
        srtBuffer.append("public " + entidade.getNomeDto() + " cadastrar(" + entidade.getNomeDto() + " " + entidade.getNomeVarDto()
                + ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException; \n");

        // M�todo de alteracao
        srtBuffer.append("public " + entidade.getNomeDto() + " alterar(" + entidade.getNomeDto() + " " + entidade.getNomeVarDto()
                + ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException; \n");

        // M�todo de busca
        srtBuffer.append("public List<" + entidade.getNomeDto() + "> buscar(" + entidade.getNomeDto() + " " + entidade.getNomeVarDto()
                + ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException; \n");

        // M�todo de exclusao
        srtBuffer.append("public void excluir(" + entidade.getNomeDto() + " " + entidade.getNomeVarDto()
                + ", UsuarioDto usuarioDto) throws PassaportInvalidoException, NegocioException, SistemaException, InternalException; \n");

        srtBuffer.append("\n \n \n");

        srtBuffer.append("}");

        // FileManager.gerarDiretorio(caminho);
        FileManager.createFife(nomeClasse + ".java", caminho, srtBuffer.toString());
      }
    }
  }

}