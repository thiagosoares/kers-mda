package br.gov.pa.muiraquita.sample.rh.facade;

import javax.ejb.Remote;

@Remote
public interface RhFacadeRemote {

  public String select();

}