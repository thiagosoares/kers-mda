package br.com.capanema.kers.core.factory.source.entity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.common.util.string.StringUtil;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.templates.CopyrightTpl;
import br.com.capanema.kers.core.util.file.FileManager;

//TODO Deve suportar geracao para todos os tipo: Completa, mirin, calango
public class GeradorEntityMuiraquita extends CalangoFactory {

  public GeradorEntityMuiraquita() {
  }

  public static void build(EAProjectConfig projeto) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

    System.out.println("Gerando Entidades .......");

    StringBuffer srtBuffer;
    String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "entity";

    for (Entity entidade : projeto.getEntidades()) {

      srtBuffer = new StringBuffer();

      if (entidade.getIsSuperTipo() == null || !entidade.getIsSuperTipo()) {
        if (!entidade.getIsEnum()) {
          gerarEntidade(projeto, entidade, srtBuffer);
        } else {
          gerarEnum(projeto, entidade, srtBuffer);
        }
      } else {
        gerarSuperEntidade(projeto, entidade, srtBuffer);
      }

      // FileManager.gerarDiretorio(caminho);
      FileManager.createFife(entidade.getNomeEntidade() + ".java", caminho, srtBuffer.toString());
    }
  }

  private static void gerarEntidade(EAProjectConfig projeto, Entity entidade, StringBuffer srtBuffer) {

    System.out.println(entidade.getNomeEntidade() + ".......");

    srtBuffer.append(CopyrightTpl.getConteudo());
    srtBuffer.append("package " + projeto.getPackageTree() + "entity; \n\n");
    srtBuffer.append("import java.util.List; \n\n");
    srtBuffer.append("import javax.persistence.Column; \n");
    srtBuffer.append("import javax.persistence.Entity; \n");
    srtBuffer.append("import javax.persistence.Enumerated; \n");
    srtBuffer.append("import javax.persistence.Id; \n\n");
    srtBuffer.append("import javax.persistence.GeneratedValue; \n");
    srtBuffer.append("import javax.persistence.GenerationType; \n\n");
    srtBuffer.append("import javax.persistence.JoinColumn; \n");
    srtBuffer.append("import javax.persistence.OneToOne; \n");
    srtBuffer.append("import javax.persistence.ManyToOne; \n");
    srtBuffer.append("import javax.persistence.ManyToMany; \n");
    srtBuffer.append("import javax.persistence.JoinTable; \n");
    srtBuffer.append("import javax.persistence.Table; \n");
    srtBuffer.append("import javax.persistence.Temporal; \n");
    srtBuffer.append("import javax.persistence.TemporalType; \n\n");

    srtBuffer.append("import br.gov.prodepa.entity.AbstractEntity; \n\n\n");

    srtBuffer.append("@Entity \n");
    srtBuffer.append("@Table(name = \"" + StringUtil.toTableSintaxFormt(entidade.getNomeEntidade()).toUpperCase() + "\", schema = \""
            + projeto.getSchemaAplicacao() + "\") \n");
    if (entidade.getIsSuperTipo() == null) {
      srtBuffer.append("public class " + entidade.getNomeEntidade() + " extends AbstractEntity  {");
    } else if (!entidade.getIsSuperTipo()) {
      srtBuffer.append("public class " + entidade.getNomeEntidade() + " extends " + entidade.getNomeSuperTipo() + "  {");
    }

    srtBuffer.append("\n \n");
    srtBuffer.append("private static final long serialVersionUID = 1L;");

    srtBuffer.append("\n\n\t");
    for (Attribute atributo : entidade.getAtributos()) {
      if (!atributo.getIsHerdado()) {
        srtBuffer.append(atributo.getAnotacao() + "\n\t");
        srtBuffer.append("private " + atributo.getTipo() + " " + atributo.getNome() + "; \n\n\t");
      }
    }

    srtBuffer.append("\n");
    srtBuffer.append("public " + entidade.getNomeEntidade() + "() { \n\t");
    srtBuffer.append("super(); \n");
    srtBuffer.append("} \n");

    for (Attribute atributo : entidade.getAtributos()) {
      if (!atributo.getIsHerdado()) {
        srtBuffer.append("public void " + atributo.getSetMetodo() + "(" + atributo.getTipo() + " " + atributo.getNome() + ") {\n \t");
        srtBuffer.append("this." + atributo.getNome() + " = " + atributo.getNome() + ";\n ");
        srtBuffer.append("} \n \n");

        srtBuffer.append("public " + atributo.getTipo() + " " + atributo.getGetMetodo() + "() {\n\t");
        srtBuffer.append("  return " + atributo.getNome() + "; \n ");
        srtBuffer.append("} \n\n ");
      }

    }
    srtBuffer.append("\n");
    srtBuffer.append("} ");

  }

  private static void gerarSuperEntidade(EAProjectConfig projeto, Entity entidade, StringBuffer srtBuffer) {

    System.out.println(entidade.getNomeEntidade() + ".......");

    srtBuffer.append("package " + projeto.getPackageTree() + "entity; \n\n");
    srtBuffer.append("import java.util.List; \n\n");
    srtBuffer.append("import javax.persistence.Column; \n");
    srtBuffer.append("import javax.persistence.Entity; \n");
    srtBuffer.append("import javax.persistence.Enumerated; \n");
    srtBuffer.append("import javax.persistence.Id; \n\n");
    srtBuffer.append("import javax.persistence.GeneratedValue; \n");
    srtBuffer.append("import javax.persistence.GenerationType; \n\n");
    srtBuffer.append("import javax.persistence.Inheritance; \n");
    srtBuffer.append("import javax.persistence.InheritanceType; \n\n");
    srtBuffer.append("import javax.persistence.JoinColumn; \n");
    srtBuffer.append("import javax.persistence.ManyToOne; \n");
    srtBuffer.append("import javax.persistence.OneToOne; \n");
    srtBuffer.append("import javax.persistence.Table; \n");
    srtBuffer.append("import javax.persistence.Temporal; \n");
    srtBuffer.append("import javax.persistence.TemporalType; \n\n");

    srtBuffer.append("import br.gov.prodepa.entity.AbstractEntity; \n\n\n");

    srtBuffer.append("@Entity \n");
    srtBuffer.append("@Table(name = \"" + entidade.getNomeEntidade().toUpperCase() + "\", schema = \"" + projeto.getSchemaAplicacao()
            + "\") \n");
    srtBuffer.append("@Inheritance(strategy = InheritanceType.JOINED)\n");
    srtBuffer.append("public class " + entidade.getNomeEntidade() + " extends AbstractEntity  {");

    /*
     * if(entidade.getIsSuperTipo()) { srtBuffer.append("public class " +
     * entidade.getNomeEntidade() + " extends "+ entidade.getNomeSuperTipo()
     * +"  {"); } else { srtBuffer.append("public class " +
     * entidade.getNomeEntidade() + " extends AbstractEntity  {"); }
     */

    srtBuffer.append("\n \n");
    //srtBuffer.append("private static final long serialVersionUID = 1L;");

    srtBuffer.append("\n\n\t");
    for (Attribute atributo : entidade.getAtributos()) {
      srtBuffer.append(atributo.getAnotacao() + "\n\t");
      srtBuffer.append("private " + atributo.getTipo() + " " + atributo.getNome() + "; \n\n\t");
    }

    srtBuffer.append("\n");
    srtBuffer.append("public " + entidade.getNomeEntidade() + "() { \n\t");
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

  }

  private static void gerarEnum(EAProjectConfig projeto, Entity entidade, StringBuffer srtBuffer) {

    System.out.println("Enum " + entidade.getNomeEntidade() + ".......");

    srtBuffer.append("package " + projeto.getPackageTree() + "entity; \n\n");

    srtBuffer.append("public enum " + entidade.getNomeEntidade() + " {");

    srtBuffer.append("\n\n\t");
    int count = 1;
    for (Attribute atributo : entidade.getAtributos()) {
      if (!atributo.getIsHerdado()) {
        srtBuffer.append(atributo.getNome().toUpperCase() + "(" + count + ", \"" + atributo.getAlias() != null ? atributo.getAlias() : atributo.getNome().toUpperCase() + "\")"
                + (count == entidade.getAtributos().size() ? ";" : ",") + " \n\t");
        count++;
      }
    }

    srtBuffer.append("\n");
    srtBuffer.append("private int id;\n");
    srtBuffer.append("private String descricao;\n");

    srtBuffer.append("\n");
    srtBuffer.append("private " + entidade.getNomeEntidade() + "(int id, String descricao) { \n\t");
    srtBuffer.append("	this.id = id; \n");
    srtBuffer.append("	this.descricao = descricao; \n");
    srtBuffer.append("} \n");

    srtBuffer.append("public int getId() {\n");
    srtBuffer.append("  return this.id; \n ");
    srtBuffer.append("} \n\n ");

    srtBuffer.append("public String getDescricao() {\n");
    srtBuffer.append("  return this.descricao; \n ");
    srtBuffer.append("} \n\n ");

    srtBuffer.append("\n");
    srtBuffer.append("} ");

  }

}