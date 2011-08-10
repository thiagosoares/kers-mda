package br.com.capanema.kers.core.factory.source.dao;

import java.io.IOException;

import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.common.model.types.TipoDados;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.templates.CopyrightTpl;
import br.com.capanema.kers.core.util.file.FileManager;

// TODO Deve suportar geracao para todos os tipo: Completa, mirin, calango
public class GeradorDaoMuiraquita extends CalangoFactory {

  public static void build(EAProjectConfig projeto) throws IOException {

    System.out.println("Gerando Dao para classe .......");

    StringBuffer srtBuffer = new StringBuffer();
    String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "dao";

    for (Entity entidade : projeto.getEntidades()) {

      String varQueryEntidade = entidade.getNomeEntidade().substring(0, 1).toLowerCase();
      System.out.println("Dao:: to :: " + entidade.getNomeEntidade());
      srtBuffer = new StringBuffer();
      String nomeClasse = entidade.getNomeEntidade() + "Dao";
      
      if (!entidade.getIsEnum()) {
        
        srtBuffer.append(CopyrightTpl.getConteudo());
        srtBuffer.append("package " + projeto.getPackageTree() + "dao; \n\n");

        srtBuffer.append("\n \n");
        srtBuffer.append("import java.util.List; \n");
        srtBuffer.append("import java.util.ArrayList; \n");
        srtBuffer.append("import org.apache.commons.lang.StringUtils;");
        srtBuffer.append("\n \n");
        srtBuffer.append("import javax.persistence.Query; \n");
        srtBuffer.append("import javax.persistence.EntityManager; \n");

        srtBuffer.append("import br.gov.prodepa.exception.InternalException; \n");
        srtBuffer.append("import br.gov.prodepa.exception.SistemaException; \n");
        srtBuffer.append("import br.gov.prodepa.dao.entity.AbstractEntityNonPaginatedDao; \n");

        srtBuffer.append("import " + projeto.getPackageTree() + "entity." + entidade.getNomeEntidade() + "; \n");
        srtBuffer.append("import " + projeto.getPackageTree() + "dto." + entidade.getNomeDto() + "; \n \n");

        srtBuffer.append("\n \n");

        String tipoId = null;
        if(entidade.getContemId()) {
	        for (Attribute atributo : entidade.getAtributos()) {
	          if (atributo.getIsId()) {
	            tipoId = atributo.getTipo();
	            break;
	          }
	        }
        }

        if (tipoId == null) {
          srtBuffer.append("public class " + nomeClasse + " extends AbstractEntityNonPaginatedDao<" + entidade.getNomeEntidade() + ", "
                  + "idTypeEntidade> " + "implements I" + entidade.getNomeDao() + "{");
        } else {
          srtBuffer.append("public class " + nomeClasse + " extends AbstractEntityNonPaginatedDao<" + entidade.getNomeEntidade() + ", " + tipoId
                  + "> " + "implements I" + entidade.getNomeDao() + "{");
        }

        srtBuffer.append("\n \n \n");
        srtBuffer.append("\t");

        srtBuffer.append("public " + nomeClasse + "(String persistenceUnit) { \n \t\t");
        srtBuffer.append(" super(persistenceUnit); \n\t");
        srtBuffer.append("} \n");

        srtBuffer.append("\n \t");

        srtBuffer.append("public " + nomeClasse + "(EntityManager em) { \n \t\t");
        srtBuffer.append(" super(em); \n\t");
        srtBuffer.append("} \n");

        srtBuffer.append("\n ");

        // M�todo de busca por paramentros do dto
        srtBuffer.append("\t");
        srtBuffer.append("public List<" + entidade.getNomeEntidade() + "> findByParameters(" + entidade.getNomeDto()
                + " parametros) throws SistemaException, InternalException { \n\t\t");
        srtBuffer.append(" StringBuffer srtQuery = new StringBuffer(); \n \t \t");
        srtBuffer.append(" srtQuery.append(\"SELECT " + varQueryEntidade + " FROM " + entidade.getNomeEntidade() + " " + varQueryEntidade
                + " WHERE 0 = 0 \");\n \t \t");

        srtBuffer.append("\n");
        srtBuffer.append("\t \t // HQL \n \t");
        srtBuffer.append("\n \t");

        if(entidade.getContemId()) {
	        for (Attribute atributo : entidade.getAtributos()) {
	          if(atributo.getIsId()) {
	            srtBuffer.append("if(parametros." + atributo.getGetMetodo() + "() != null) {  \n \t \t");
	            srtBuffer.append(" srtQuery.append(\" AND " + varQueryEntidade + "." + atributo.getNome() + " = :" + atributo.getNome() + "\"); \n \t ");
	            srtBuffer.append("} else { \n \t");
	            break;
	          }
	        }
        } 

        for (Attribute atributo : entidade.getAtributos()) {
          if (!atributo.getIsId()) {
            if (atributo.getTipo().equals(TipoDados.STRING.toString())) {
              srtBuffer.append("\n\t");
              srtBuffer.append("if(!StringUtils.isEmpty(parametros." + atributo.getGetMetodo() + "())) { \n \t \t \t");
              srtBuffer.append(" srtQuery.append(\" AND " + varQueryEntidade + "." + atributo.getNome() + " LIKE :" + atributo.getNome()
                      + "\"); \n \t ");
              srtBuffer.append("}");
            } else
            if (atributo.getTipo().equals(TipoDados.INTEGER.toString()) || atributo.getTipo().equals("int")) {
              srtBuffer.append("\n\t");
              srtBuffer.append("if(parametros." + atributo.getGetMetodo() + "() != 0) { \n \t\t");
              srtBuffer.append(" srtQuery.append(\" AND " + varQueryEntidade + "." + atributo.getNome() + " = :" + atributo.getNome() + "\"); \n \t");
              srtBuffer.append("}");
            }

            else {
              srtBuffer.append("\n\t");
              srtBuffer.append("if(parametros." + atributo.getGetMetodo() + "() != null) { \n \t\t");
              srtBuffer.append(" srtQuery.append(\" AND " + varQueryEntidade + "." + atributo.getNome() + " = :" + atributo.getNome() + "\"); \n \t");
              srtBuffer.append("}");
            }
          }
        }

        srtBuffer.append("\n \t");
        if(entidade.getContemId()) {
        	srtBuffer.append("} \n \t");
        }

        srtBuffer.append(" srtQuery.append(\" ORDER BY " + varQueryEntidade
                + ".id \"); //TODO Nao Implementado. Definir campo para ordenacao\n \t");
        srtBuffer.append(" \n \t");

        srtBuffer.append(" Query query = createQuery(srtQuery.toString(), ILIMITADA); \n \t");

        srtBuffer.append(" \n //paramentro \n \t");

        if(entidade.getContemId()) {
	        for (Attribute atributo : entidade.getAtributos()) {
	          if (atributo.getIsId()) {
	            srtBuffer.append("if(parametros." + atributo.getGetMetodo()
	                    + "() != null) { //TODO Nao Implementado. Definir campo identificador \n \t\t");
	            srtBuffer.append("query.setParameter(\"" + atributo.getNome() + "\", parametros." + atributo.getGetMetodo() + "()); \n \t");
	            srtBuffer.append("} else { \n \t");
	          }
	        }
        }

        for (Attribute atributo : entidade.getAtributos()) {
          if (!atributo.getIsId()) {
            if (atributo.getTipo().equals(TipoDados.STRING.toString())) {
              srtBuffer.append("if(!StringUtils.isEmpty(parametros." + atributo.getGetMetodo() + "())) { \n \t\t");
              srtBuffer.append("query.setParameter(\"" + atributo.getNome() + "\", parametros." + atributo.getGetMetodo() + "()); \n \t");
              srtBuffer.append("} \n \t");
            } else {
              srtBuffer.append("if(parametros." + atributo.getGetMetodo() + "() != null) { \n \t\t");
              srtBuffer.append("query.setParameter(\"" + atributo.getNome() + "\", parametros." + atributo.getGetMetodo() + "()); \n \t");
              srtBuffer.append("} \n \t");
            }
          }
        }

        srtBuffer.append(" \n \t");
        if(entidade.getContemId()) {
        	srtBuffer.append("} \n \t");
        }
        srtBuffer.append(" return query.getResultList();");
        srtBuffer.append(" \n \t");

        srtBuffer.append("} \n");

        srtBuffer.append("\n \n \n");

        srtBuffer.append("}");

        // FileManager.gerarDiretorio(caminho);
        FileManager.createFife(nomeClasse + ".java", caminho, srtBuffer.toString());

        gerarInterface(projeto);
      }
    }
  }

  public static void gerarInterface(EAProjectConfig projeto) throws IOException {

    StringBuffer srtBuffer;
    String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "dao";

    for (Entity entidade : projeto.getEntidades()) {
	    if (!entidade.getIsEnum()) {
	      srtBuffer = new StringBuffer();
	      String nomeClasse = "I" + entidade.getNomeEntidade() + "Dao";
	
	      srtBuffer.append(CopyrightTpl.getConteudo());
	      srtBuffer.append("package " + projeto.getPackageTree() + "dao; \n\n");
	      srtBuffer.append("\n \n");
	      srtBuffer.append("import java.util.List;");
	      srtBuffer.append("\n \n");
	      srtBuffer.append("import br.gov.prodepa.dao.IDao;\n");
	      srtBuffer.append("import br.gov.prodepa.exception.InternalException;\n");
	      srtBuffer.append("import br.gov.prodepa.exception.SistemaException;\n");
	      srtBuffer.append("\n");
	      srtBuffer.append("import " + projeto.getPackageTree() + "entity." + entidade.getNomeEntidade() + "; \n");
	      srtBuffer.append("import " + projeto.getPackageTree() + "dto." + entidade.getNomeDto() + "; \n \n");
	      srtBuffer.append("\n");
	      srtBuffer.append("/** \n");
	      srtBuffer.append("* Interface declarativa de rotinas do Dao.  \n");
	      srtBuffer.append("*/ \n");
	
	      String tipoId = null;
	      for (Attribute atributo : entidade.getAtributos()) {
	        if (atributo.getIsId()) {
	          tipoId = atributo.getTipo();
	        }
	      }
	      if (tipoId == null) {
	        srtBuffer.append("public interface " + nomeClasse + " extends IDao<" + entidade.getNomeEntidade() + ", " + "idTypeEntidade" + "> {");
	      } else {
	        srtBuffer.append("public interface " + nomeClasse + " extends IDao<" + entidade.getNomeEntidade() + ", " + tipoId + "> {");
	      }
	
	      srtBuffer.append("\n \n \n");
	
	      srtBuffer.append("public List<" + entidade.getNomeEntidade() + "> findByParameters(" + entidade.getNomeDto() + " " + entidade.getNomeVarDto()
	              + ") throws SistemaException, InternalException; \n \t");
	
	      srtBuffer.append("\n \n \n");
	
	      srtBuffer.append("}");
	
	      FileManager.gerarDiretorio(caminho);
	      FileManager.createFife(nomeClasse + ".java", caminho, srtBuffer.toString());
	    }
    }
  }

}