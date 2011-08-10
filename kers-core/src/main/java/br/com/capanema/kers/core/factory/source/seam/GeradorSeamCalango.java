package br.com.capanema.kers.core.factory.source.seam;

import java.io.IOException;

import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.templates.CopyrightTpl;
import br.com.capanema.kers.core.util.file.FileManager;

// TODO Deve suportar geracao para todos os tipo: Completa, mirin, calango
public class GeradorSeamCalango extends CalangoFactory {

  public GeradorSeamCalango() {
  }

  public static void build(EAProjectConfig projeto) throws IOException {

    System.out.println("Gerando Seam para classe .......");

    StringBuffer srtBuffer;
    String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "seam";

    for (Entity entidade : projeto.getEntidades()) {

      srtBuffer = new StringBuffer();
      if (!entidade.getIsEnum()) {
        
        srtBuffer.append(CopyrightTpl.getConteudo());
        srtBuffer.append("package " + projeto.getPackageTree() + "seam; \n\n");

        srtBuffer.append("\n \n");
        srtBuffer.append("import java.util.List; \n");
        srtBuffer.append("import java.util.ArrayList; \n");
        srtBuffer.append("import org.apache.log4j.Logger; \n");
        srtBuffer.append("import org.apache.log4j.LogManager; \n");

        srtBuffer.append("\n \n");
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

        srtBuffer.append("import br.gov.pa.controleacesso.dto.UsuarioDto; \n");
        srtBuffer.append("import " + projeto.getPackageTree() + "dto." + entidade.getNomeDto() + "; \n \n");

        srtBuffer.append("\n");
        srtBuffer.append("import br.gov.prodepa.exception.InternalException; \n");
        srtBuffer.append("import br.gov.prodepa.exception.NegocioException; \n");
        srtBuffer.append("import br.gov.prodepa.exception.SistemaException; \n");
        srtBuffer.append("import br.gov.prodepa.exception.security.PassaportInvalidoException; \n");
        srtBuffer.append("import br.gov.prodepa.locator.ServiceLocator; \n");
        srtBuffer.append("import " + projeto.getPackageTree() + "seam.SeamTreatment; \n");

        srtBuffer.append("\n \n");

        srtBuffer.append("@Scope(ScopeType.CONVERSATION)");
        srtBuffer.append("\n");
        srtBuffer.append("@Name(\"" + entidade.getNomeVarAction() + "\")");
        srtBuffer.append("\n");
        srtBuffer.append("public class " + entidade.getNomeAction() + " extends AbstractSeam {");

        srtBuffer.append("\n \n \n");

        srtBuffer.append("@In(create = true, required = false)");
        srtBuffer.append("\n");
        srtBuffer.append("@Out(required = false)");
        srtBuffer.append("\n");
        srtBuffer.append(entidade.getNomeDto() + " " + entidade.getNomeVarDto() + ";");
        srtBuffer.append("\n \n");

        srtBuffer.append("@DataModel");
        srtBuffer.append("\n");
        srtBuffer.append("List<" + entidade.getNomeDto() + "> " + entidade.getNomeVarDto() + "Lista;");
        srtBuffer.append("\n \n");

        srtBuffer.append("@DataModelSelection(\"" + entidade.getNomeVarDto() + "Lista\")");
        srtBuffer.append("\n");
        srtBuffer.append(entidade.getNomeDto() + " " + entidade.getNomeVarDto() + "Selecionado;");
        srtBuffer.append("\n \n");

        srtBuffer.append("/** Auditoria **/ \n");
        srtBuffer.append("\n");

        srtBuffer.append("@In(required = true) \n");
        srtBuffer.append("UsuarioDto usuarioDto; \n");

        srtBuffer.append("\n \n \n");

        srtBuffer.append("/*");
        srtBuffer.append("* ------- Classes de apoio ---------");
        srtBuffer.append("*/ \n \n");
        // srtDto.append("private SistemaIFacade sistemaServico;");
        srtBuffer.append("\n");
        srtBuffer.append("private SeamTreatment seamTreatment = new SeamTreatment(); \n");
        srtBuffer.append("private static Logger log = LogManager.getLogger(" + entidade.getNomeAction() + ".class); \n");
        srtBuffer.append("\n");

        srtBuffer.append("/* \n");
        srtBuffer.append("* ---------- Vari�veis de apoio ************************ --------- \n");
        srtBuffer.append("*/ \n");
        srtBuffer.append("private static final String START_PAGE = \"/" + projeto.getNomeSistema().toLowerCase() + "/"
                + entidade.getNomeEntidade().toLowerCase() + "/" + entidade.getNomeEntidade().toLowerCase() + "_list.xhtml\"; \n");
        srtBuffer.append("private static final String FORM_PAGE = \"/" + projeto.getNomeSistema().toLowerCase() + "/"
                + entidade.getNomeEntidade().toLowerCase() + "/" + entidade.getNomeEntidade().toLowerCase() + "_form.xhtml\"; \n");
        srtBuffer.append("private static final String UPDATE_PAGE = \"/" + projeto.getNomeSistema().toLowerCase() + "/"
                + entidade.getNomeEntidade().toLowerCase() + "/" + entidade.getNomeEntidade().toLowerCase() + "_update.xhtml\"; \n");

        srtBuffer.append("\n \n");

        srtBuffer.append("/*");
        srtBuffer.append("\n");
        srtBuffer.append("* ---------- Rotinas de neg�cio ---------");
        srtBuffer.append("\n");
        srtBuffer.append("*/");
        srtBuffer.append("\n \n");

        // M�todo de cadastro
        srtBuffer.append("public void cadastrar() throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n");
        srtBuffer.append("try { \n");
        srtBuffer.append("\t get" + projeto.getNomeFachada() + "FacadeLocal().cadastrar" + entidade.getNomeEntidade() + "(this."
                + entidade.getNomeVarDto() + ", this.usuarioDto); \n \t");
        srtBuffer.append("this." + entidade.getNomeVarDto() + " = new " + entidade.getNomeDto() + "(); \n\t");
        srtBuffer.append("\t seamTreatment.addInfoMessage(\"O " + entidade.getNomeEntidade() + " foi cadastrado com Sucesso.\"); \n");
        srtBuffer.append("} catch (Throwable e) { \n");
        srtBuffer.append("log.info(this.getClass().getName() + \": Throwable: pilha:\"); \n");
        srtBuffer.append("seamTreatment.tratar(e); \n");
        srtBuffer.append("} \n");
        srtBuffer.append("} \n");
        srtBuffer.append("\n \n");

        // M�todo de alteracao
        srtBuffer.append("public void alterar() throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n");
        srtBuffer.append("try { \n");
        srtBuffer.append("\t get" + projeto.getNomeFachada() + "FacadeLocal().alterar" + entidade.getNomeEntidade() + "(this."
                + entidade.getNomeVarDto() + ", this.usuarioDto); \n");
        srtBuffer.append("\t seamTreatment.addInfoMessage(\"O " + entidade.getNomeEntidade() + " foi alterado com Sucesso.\"); \n");
        srtBuffer.append("\t} catch (Throwable e) { \n");
        srtBuffer.append("\t\t log.info(this.getClass().getName() + \": Throwable: pilha:\"); \n");
        srtBuffer.append("\t\t seamTreatment.tratar(e); \n");
        srtBuffer.append("\t} \n");
        srtBuffer.append("} \n \n");

        // M�todo de busca
        srtBuffer.append("@Begin(join = true) \n");
        srtBuffer.append("public void buscar() throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n");
        srtBuffer.append("try { \n");
        srtBuffer.append("\t this." + entidade.getNomeVarDto() + "Lista = get" + projeto.getNomeFachada() + "FacadeLocal().buscar"
                + entidade.getNomeEntidade() + "s(this." + entidade.getNomeVarDto() + ", this.usuarioDto); \n");
        srtBuffer.append("\t seamTreatment.addInfoMessage(this." + entidade.getNomeVarDto()
                + "Lista.isEmpty() ? \"#{messages.txt_busca_sem_resultado}\" : \"#{messages.txt_busca_sucesso}\"); \n");
        srtBuffer.append("\t} catch (Throwable e) { \n");
        srtBuffer.append("\t\t log.info(this.getClass().getName() + \": Throwable: pilha:\"); \n");
        srtBuffer.append("\t\t seamTreatment.tratar(e); \n");
        srtBuffer.append("\t} \n");
        srtBuffer.append("} \n \n");

        // M�todo de exclusao
        srtBuffer.append("public void excluir() throws PassaportInvalidoException, NegocioException, SistemaException, InternalException { \n");
        srtBuffer.append("try { \n");
        srtBuffer.append("\t get" + projeto.getNomeFachada() + "FacadeLocal().excluir" + entidade.getNomeEntidade() + "(this."
                + entidade.getNomeVarDto() + ", this.usuarioDto); \n");
        srtBuffer.append("\t seamTreatment.addInfoMessage(\"O " + entidade.getNomeEntidade() + " foi Exclu�do com Sucesso.\"); \n");
        srtBuffer.append("\t } catch (Throwable e) { \n");
        srtBuffer.append("\t\t log.info(this.getClass().getName() + \": Throwable: pilha:\"); \n");
        srtBuffer.append("\t\t seamTreatment.tratar(e); \n");
        srtBuffer.append("\t} \n");
        srtBuffer.append("} ");
        srtBuffer.append("\n \n");

        // Metodo para selecionar
        srtBuffer.append("public void selecionar() { \n");
        srtBuffer.append("\t this." + entidade.getNomeVarDto() + " = this." + entidade.getNomeVarDto() + "Selecionado; \n");
        srtBuffer.append("}\n");
        srtBuffer.append("\n");

        // Metodo para reiniciar a conversacao apartir do menu(geralmente)
        srtBuffer.append("@End(beforeRedirect = true) \n");
        srtBuffer.append("public String startConversation() { \n");
        srtBuffer.append("\t this." + entidade.getNomeVarDto() + " = new " + entidade.getNomeDto() + "(); \n");
        srtBuffer.append("\t return START_PAGE; \n");
        srtBuffer.append("}\n");
        srtBuffer.append("\n");

        srtBuffer.append("\n");

        // Metodo para preparar a conversacao e qualquer artificio para um
        // cadastro
        srtBuffer.append("@End \n");
        srtBuffer.append("public String formConversation() { \n");
        srtBuffer.append("\t this." + entidade.getNomeVarDto() + " = new " + entidade.getNomeDto() + "(); \n");
        srtBuffer.append("\t return FORM_PAGE; \n");
        srtBuffer.append("}\n");
        srtBuffer.append("\n");

        srtBuffer.append("\n");

        // Metodo para limpar a conversacao e qualquer artificio para um
        // cadastro
        srtBuffer.append("@End \n");
        srtBuffer.append("public void cleanConversation() { \n");
        srtBuffer.append("\t this." + entidade.getNomeVarDto() + " = new " + entidade.getNomeDto() + "(); \n");
        srtBuffer.append("}\n");
        srtBuffer.append("\n");

        srtBuffer.append("\n");
        srtBuffer.append("}");

        // FileManager.gerarDiretorio(caminho);
        FileManager.createFife(entidade.getNomeAction() + ".java", caminho, srtBuffer.toString());
      }
    }
  }
}