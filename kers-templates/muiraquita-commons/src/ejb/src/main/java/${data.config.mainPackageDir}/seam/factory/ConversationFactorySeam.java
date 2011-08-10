package br.gov.pa.muiraquita.sample.rh.seam.factory;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.gov.pa.muiraquita.sample.rh.dao.EstadoDao;
import br.gov.pa.muiraquita.sample.rh.dao.IEstadoDao;
import br.gov.pa.muiraquita.sample.rh.dao.IMunicipioDao;
import br.gov.pa.muiraquita.sample.rh.dao.MunicipioDao;
import br.gov.pa.muiraquita.sample.rh.dto.EstadoDto;
import br.gov.pa.muiraquita.sample.rh.dto.MunicipioDto;
import br.gov.pa.muiraquita.sample.rh.dto.appInfo.SessionInformationsDto;
import br.gov.pa.muiraquita.sample.rh.entity.Estado;
import br.gov.pa.muiraquita.sample.rh.entity.Municipio;
import br.gov.pa.muiraquita.seam.AbstractSeam;

@Name("conversationFactory")
@Scope(ScopeType.CONVERSATION)
public class ConversationFactorySeam extends AbstractSeam {

  @In(create = true, required = false)
  SessionInformationsDto sessionInformations;
  
  @In("em")
  private EntityManager emSeam;
  

  @Factory("municipios")
  public List<Municipio> getMunicipiosPorEstado()     {
    try {
        return new MunicipioDao(emSeam).findAll();
    } catch (Throwable e) {
      seamTreatment.tratar(e);
      return new ArrayList<Municipio>();
    }
  }
  
  
}
