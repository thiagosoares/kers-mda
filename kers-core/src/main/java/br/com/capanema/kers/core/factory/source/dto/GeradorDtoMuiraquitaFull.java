package br.com.capanema.kers.core.factory.source.dto;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.templates.CopyrightTpl;
import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorDtoMuiraquitaFull extends CalangoFactory {

  public GeradorDtoMuiraquitaFull() {
  }

  public static void build(EAProjectConfig projeto) throws IOException  {

    System.out.println("Gerando Dtos .......");

    StringBuffer srtBuffer;
    String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "/dto";

    for (Entity entidade : projeto.getEntidades()) {
      srtBuffer = new StringBuffer();
      if (!entidade.getIsEnum()) {
        System.out.println(entidade.getNomeEntidade() + ".......");

        srtBuffer.append(CopyrightTpl.getConteudo());
        srtBuffer.append("package " + projeto.getPackageTree() + "dto; \n\n");
        srtBuffer.append("import java.util.List; \n\n");
        srtBuffer.append("import java.util.Date; \n\n");
        srtBuffer.append("import org.jboss.seam.ScopeType; \n");
        srtBuffer.append("import org.jboss.seam.annotations.Name; \n");
        srtBuffer.append("import org.jboss.seam.annotations.Scope; \n \n");

        srtBuffer.append("import br.gov.prodepa.dto.AbstractDto; \n\n\n");

        srtBuffer.append("@Scope(ScopeType.CONVERSATION)");
        srtBuffer.append("\n");
        srtBuffer.append("@Name(\"" + entidade.getNomeVarDto() + "\")");
        srtBuffer.append("\n");
        srtBuffer.append("public class " + entidade.getNomeDto() + " extends AbstractDto {");

        srtBuffer.append("\n \n");
        srtBuffer.append("private static final long serialVersionUID = 1L;");

        srtBuffer.append("\n\n\t");
        for (Attribute atributo : entidade.getAtributos()) {
          srtBuffer.append("private " + atributo.getTipo() + " " + atributo.getNome() + "; \n\n\t");
        }

        srtBuffer.append("\n");
        srtBuffer.append("public " + entidade.getNomeDto() + "() { \n\t");
        srtBuffer.append("super(); \n");
        srtBuffer.append("} \n");

        for (Attribute atributo : entidade.getAtributos()) {
          srtBuffer.append("public void " + atributo.getSetMetodo() + "(" + atributo.getTipo() + " " + atributo.getNome() + ") {\n \t");
          srtBuffer.append("this." + atributo.getNome() + " = " + atributo.getNome() + ";\n ");
          srtBuffer.append("} \n \n");

          srtBuffer.append("public " + atributo.getTipo() + " " + atributo.getGetMetodo() + "() {\n\t");
          srtBuffer.append("  return " + atributo.getNome() + "; \n ");
          srtBuffer.append("} \n\n ");

        }
        srtBuffer.append("\n");
        srtBuffer.append("} ");
        // FileManager.gerarDiretorio(caminho);
        FileManager.createFife(entidade.getNomeDto() + ".java", caminho, srtBuffer.toString());
      }
    }
  }

}