package br.gov.pa.muiraquita.sample.rh.dto.appInfo;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.gov.pa.muiraquita.dto.AbstractDto;
import br.gov.pa.muiraquita.sample.rh.configuration.AppConfigurations;

@Scope(ScopeType.APPLICATION)
@Name("aplicationInfo")
public class AplicationInfo extends AbstractDto {

  private static final long serialVersionUID = -5994632938464332514L;
  private AppConfigurations config = null;

  public AplicationInfo() {
    super();
    if(this.config == null) { 
      this.config = AppConfigurations.getInstance();
    } 
  }

  public AppConfigurations getConfig() {
    return config;
  }
  
}
