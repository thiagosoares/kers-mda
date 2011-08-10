package br.com.capanema.kers.core.factory.source.entity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.common.util.string.StringUtil;
import br.com.capanema.kers.core.templates.CopyrightTpl;
import br.com.capanema.kers.core.util.file.FileManager;

@Deprecated
public class GeradorEntityCalango {

  public GeradorEntityCalango() {
  }

  public static void build(EAProjectConfig projeto) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

    System.out.println("Gerando Entidades .......");

    StringBuffer srtBuffer = new StringBuffer();
    String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "/entity";

    for (Entity entidade : projeto.getEntidades()) {

      System.out.println(entidade.getNomeEntidade() + ".......");

      srtBuffer.append(CopyrightTpl.getConteudo());
      srtBuffer.append("package " + projeto.getPackageTree() + "entity; \n\n");
      srtBuffer.append("import java.util.List; \n\n");
      srtBuffer.append("import javax.persistence.Column; \n");
      srtBuffer.append("import javax.persistence.Entity; \n");
      srtBuffer.append("import javax.persistence.Enumerated; \n");
      srtBuffer.append("import javax.persistence.Id; \n\n");
      srtBuffer.append("import javax.persistence.GeneratedValue; \n\n");
      srtBuffer.append("import javax.persistence.JoinColumn; \n");
      srtBuffer.append("import javax.persistence.ManyToOne; \n");
      srtBuffer.append("import javax.persistence.OneToOne; \n");
      srtBuffer.append("import javax.persistence.Table; \n");
      srtBuffer.append("import javax.persistence.Temporal; \n");
      srtBuffer.append("import javax.persistence.TemporalType; \n\n");

      srtBuffer.append("import br.gov.prodepa.entity.AbstractEntity; \n\n\n");

      srtBuffer.append("@Entity \n");
      srtBuffer.append("@Table(name = \"" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()).toUpperCase() + "\", schema = \""
              + projeto.getSchemaAplicacao() + "\") \n");
      srtBuffer.append("public class " + entidade.getNomeEntidade() + " extends AbstractEntity  {");

      srtBuffer.append("\n \n");
      srtBuffer.append("private static final long serialVersionUID = 1L;");

      srtBuffer.append("\n\n\t");
      for (Attribute atributo : entidade.getAtributos()) {

        if (atributo.getAnotacao().contains("$")) {
          atributo.setAnotacao(atributo.getAnotacao().replace("$schema", projeto.getSchemaAplicacao()));
        }
        srtBuffer.append(atributo.getAnotacao() + "\n\t");
        srtBuffer.append("private " + atributo.getTipo() + " " + atributo.getNome() + "; \n\n\t");
      }

      srtBuffer.append("\n");
      srtBuffer.append("public " + entidade.getNomeEntidade() + "() { \n\t");
      srtBuffer.append("super(); \n");
      srtBuffer.append("} \n");

      for (Attribute atributo : entidade.getAtributos()) {
        srtBuffer.append("public void " + atributo.getSetMetodo() + "(" + atributo.getTipo() + " " + atributo.getNome().toLowerCase() + ") {\n \t");
        srtBuffer.append("this." + atributo.getNome().toLowerCase() + " = " + atributo.getNome().toLowerCase() + ";\n ");
        srtBuffer.append("} \n \n");

        srtBuffer.append("public " + atributo.getTipo() + " " + atributo.getGetMetodo() + "() {\n\t");
        srtBuffer.append("  return " + atributo.getNome().toLowerCase() + "; \n ");
        srtBuffer.append("} \n\n ");

      }
      srtBuffer.append("\n");
      srtBuffer.append("} ");

      // FileManager.gerarDiretorio(caminho);
      FileManager.createFife(entidade.getNomeEntidade() + ".java", caminho, srtBuffer.toString());
    }
    System.out.println("  OK.");
  }

}