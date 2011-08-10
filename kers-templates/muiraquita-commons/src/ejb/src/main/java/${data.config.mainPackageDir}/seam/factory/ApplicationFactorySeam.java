package br.gov.pa.muiraquita.sample.rh.seam.factory;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;

import br.gov.pa.muiraquita.sample.rh.dao.EstadoDao;
import br.gov.pa.muiraquita.sample.rh.dao.IEstadoDao;
import br.gov.pa.muiraquita.sample.rh.dto.EstadoDto;
import br.gov.pa.muiraquita.sample.rh.entity.Estado;
import br.gov.pa.muiraquita.sample.rh.seam.RhAbstractSeam;
import br.gov.pa.muiraquita.seam.AbstractSeam;
import br.gov.pa.muiraquita.seam.SeamTreatment;
import br.gov.pa.muiraquita.types.TipoBoleano;

@Name("applicationFactory")
@Scope(ScopeType.APPLICATION)
public class ApplicationFactorySeam extends RhAbstractSeam {

  @In("em")
  private EntityManager emSeam;
  
  /*
   * Factorys de Enums
   */
  @Factory("boleanos")
  public Boolean[] getBooleanos() {
    return new Boolean[] { true, false };
  }

  @Factory("tiposBoleano")
  public TipoBoleano[] getTiposBoleano() {
    return TipoBoleano.values();
  }
  
  
  /*
   * Factorys de entidades manuteníveis
   */
  
  public void reloadEstadoFactory() {
    Contexts.getApplicationContext().set("estados", getEstados());
  }
  
  
  @Factory("estados")
  public List<Estado> getEstados()     {
    try {
      return  new EstadoDao(emSeam).findAll();
    } catch (Throwable e) {
      seamTreatment.tratar(e);
      return new ArrayList<Estado>();
    }
  }
  

}
