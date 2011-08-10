package br.com.capanema.kers.common.model.repository;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.com.capanema.kers.common.model.template.TemplateSumary;

@XStreamAlias("repository")
public class Repository {

  private String name;
  private String path;
  
  private List<TemplateSumary> sumaries;
  
  public Repository() {
    super();
  }
  
  public Repository(String path) {
    super();
    this.name = path;
    this.path = path;
  }

  
  public Repository(String name, String path, String realPath) {
    super();
    this.name = name;
    this.path = path;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public List<TemplateSumary> getSumaries() {
    return sumaries;
  }

  public void setSumaries(List<TemplateSumary> sumaries) {
    this.sumaries = sumaries;
  }
  
  
  
  
}
