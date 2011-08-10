package br.com.capanema.kers.core.trash;

import java.util.List;

import javax.naming.ConfigurationException;

import org.apache.commons.configuration.XMLConfiguration;

import br.com.capanema.kers.common.model.domain.Association;
import br.com.capanema.kers.common.model.domain.Entity;

public class XmlClasseDiscovery {

  private XMLConfiguration config;
  
  
  public XmlClasseDiscovery(XMLConfiguration config) throws ConfigurationException {
    super();
    this.config = config;
  }

  public List<String> readClasse(String idEA) {
    
    return null;
  }

  public List<Entity> readClasses() {
    
    //TODO Carreger ids das propriedades
    
    return null;
  }

  
  public Association readAssociacao(String idEA) {
    
    
    return null;
  }

  public List<Association> readAssociacoes() {
    
    
    return null;
  }

  
  
}
