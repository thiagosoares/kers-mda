package br.com.capanema.kers.common.model.template;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("entity")
public class EntityTemplate {

  private String classDeclaration;
  private List<String> idAnnotations;
  private List<String> classAnnotations;
  
  public EntityTemplate() {
    super();
  }

  public String getClassDeclaration() {
    return classDeclaration;
  }

  public void setClassDeclaration(String classDeclaration) {
    this.classDeclaration = classDeclaration;
  }

  public List<String> getIdAnnotations() {
    return idAnnotations;
  }

  public void setIdAnnotations(List<String> idAnnotations) {
    this.idAnnotations = idAnnotations;
  }

  public List<String> getClassAnnotations() {
    return classAnnotations;
  }

  public void setClassAnnotations(List<String> classAnnotations) {
    this.classAnnotations = classAnnotations;
  }

}
