package br.com.capanema.kers.uc.util.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.util.string.StringUtil;

public class BeanDiscovery {

  private List<Class<?>> entidadesListaStr = new ArrayList<Class<?>>();

  public BeanDiscovery(List<Class<?>> entidadesListaStr) {
    super();
    this.entidadesListaStr = entidadesListaStr;
  }

  public List<Entity> readClasses() {

    List<Entity> classes = new ArrayList<Entity>();

    if (entidadesListaStr != null) {

      /** Descoberta da classes **/
      // Percorre a lista de elementos encontrados
      for (int i = 0; i < entidadesListaStr.size(); i++) {

        Entity entidade = new Entity();
        entidade.setContemId(false);
        Class<?> classe = entidadesListaStr.get(i);

        if (classe != null) {

          System.out.println("Classe >> " + classe.getSimpleName());

          entidade.setIdEa(String.valueOf(i));
          entidade.setIdEAPackage("1");

          // Setar nome da classe
          entidade.setNomeEntidade(StringUtil.toClasseNameFormat(classe.getSimpleName()));
          entidade.setNomeVarEntidade(StringUtil.toVariavelFormat(entidade.getNomeEntidade()));
          entidade.setNomeDto(entidade.getNomeEntidade() + "Dto");
          entidade.setNomeVarDto(entidade.getNomeVarEntidade() + "Dto");
          entidade.setNomeDao(entidade.getNomeEntidade() + "Dao");
          entidade.setNomeVarDao(entidade.getNomeVarEntidade() + "Dao");
          entidade.setNomeSeam(entidade.getNomeEntidade() + "Seam");
          entidade.setNomeVarAction(entidade.getNomeVarEntidade() + "Action");
          entidade.setNomeSeamAction(entidade.getNomeVarEntidade() + "Action");
          entidade.setIsSuperTipo(true);
          entidade.setIsEnum(false);

          // verifica se possui supertipo
          Class<?> superclasse = classe.getSuperclass();
          boolean isSubtipo = false;

          if (superclasse != null) {
            Annotation[] anotacoes = superclasse.getDeclaredAnnotations();
            for (Annotation annotation : anotacoes) {
              if (annotation.annotationType() == javax.persistence.Inheritance.class) {
                isSubtipo = true;
              }
            }
          }
          if (isSubtipo) {
            Field[] listaFieldsSuper = superclasse.getDeclaredFields();

            for (int j = 0; j < listaFieldsSuper.length; j++) {
              Attribute atr = new Attribute();

              if (!listaFieldsSuper[j].getName().equals("serialVersionUID")) {
                
                System.out.println("Super atributo : " + listaFieldsSuper[j].getName());
                
                atr.setId_EA(String.valueOf(j));
                atr.setNome(StringUtil.toVariavelFormat(listaFieldsSuper[j].getName()));
                atr.setTipo(listaFieldsSuper[j].getType().getSimpleName());
                String s1 = atr.getNome().substring(0, 1).toUpperCase();
                String s2 = atr.getNome().substring(1);
                atr.setGetMetodo("get" + s1 + s2);
                atr.setSetMetodo("set" + s1 + s2);
                atr.setIsRelacionamento(false);
                atr.setIsHerdado(true);

                atr.setIsId(false);
                
                Annotation[] listaAnotacoes = listaFieldsSuper[j].getDeclaredAnnotations();
                StringBuffer anotacoes = new StringBuffer();
                String linha = "";
                for (Annotation annotation : listaAnotacoes) {
                  anotacoes.append(linha + "@"+annotation.annotationType().getSimpleName());
                  if (annotation.annotationType() == javax.persistence.Id.class) {
                    atr.setIsId(true);
                    entidade.setContemId(true);
                  }
                  linha ="\n\t";
                }
                atr.setAnotacao(anotacoes.toString());
                entidade.getAtributos().add(atr);
              }
            }

          }

          Field[] listaFields = classe.getDeclaredFields();

          for (int j = 0; j < listaFields.length; j++) {

            Attribute atr = new Attribute();
            if (!listaFields[j].getName().equals("serialVersionUID")) {
              
              System.out.println("atributo : " + listaFields[j].getName());
              
              atr.setId_EA(String.valueOf(j));
              atr.setNome(listaFields[j].getName());
              atr.setTipo(listaFields[j].getType().getSimpleName());
              String s1 = atr.getNome().substring(0, 1).toUpperCase();
              String s2 = atr.getNome().substring(1);
              atr.setGetMetodo("get" + s1 + s2);
              atr.setSetMetodo("set" + s1 + s2);
              atr.setIsRelacionamento(false);
              atr.setIsHerdado(false);

              atr.setIsId(false);
              Annotation[] listaAnotacoes = listaFields[j].getDeclaredAnnotations();
              StringBuffer anotacoes = new StringBuffer();
              String linha = "";
              for (Annotation annotation : listaAnotacoes) {
                String args = annotation.toString().substring(annotation.toString().indexOf(annotation.annotationType().getSimpleName()));
                
                String anotacao = annotation.toString().substring(0,1) + (args.substring(args.length() - 2).equals("()")? args.substring(0, args.length() - 2) : args );
                
                anotacao = anotacao.replace("scale=0,", "").replace("precision=0,", "").replace("unique=false,", "").replace("columnDefinition=,", "").replace("table=)", ")");
                
                anotacoes.append(linha + anotacao);
                if (annotation.annotationType() == javax.persistence.Id.class) {
                  atr.setIsId(true);
                }
                linha ="\n\t";

              }
              atr.setAnotacao(anotacoes.toString());
              entidade.getAtributos().add(atr);
            }
          }

          classes.add(entidade);
        }
      }

      /** Descoberta dos relacionamentos **/
      // classes = setarPropriedadesAssociacao(classes);

      /** Descoberta das generalizacoes **/
      // classes = setarPropriedadesGeneralizacao(classes);

      return classes;

    } else {
      return null;
    }
  }

}
