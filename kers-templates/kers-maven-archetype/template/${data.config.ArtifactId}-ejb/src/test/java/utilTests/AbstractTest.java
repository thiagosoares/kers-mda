


package br.gov.pa.muiraquita.sample.rh.utilTests;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.gov.pa.controleacesso.entity.Usuario;


public abstract class AbstractTest {

  
  protected Logger log = LogManager.getLogger(this.getClass());
  
  public Usuario getUsuarioLogado() {
    
    Usuario obj = new Usuario();
    obj.setId(1L);
    obj.setNome("user");
    obj.setLogin("user.login");
    obj.setCpf("000.000.000-00");
    obj.setSenha("123456");
    obj.setPassaporte("21212121212");
    
    return obj;
  }
  
  
 
}
