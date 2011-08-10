package br.gov.pa.muiraquita.sample.rh.dto.appInfo;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.gov.pa.muiraquita.dto.AbstractDto;

@Scope(ScopeType.SESSION)
@Name("sessionInformations")
public class SessionInformationsDto extends AbstractDto {

  private static final long serialVersionUID = 1126124160121475913L;
  
  private String userName;
  private String userLogin;
  private Boolean userPasswChanged;

  public SessionInformationsDto() {
    super();
  }

  public String getUserLogin() {
    return userLogin;
  }

  public void setUserLogin(String userLogin) {
    this.userLogin = userLogin;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public Boolean getUserPasswChanged() {
    return userPasswChanged;
  }

  public void setUserPasswChanged(Boolean userPasswChanged) {
    this.userPasswChanged = userPasswChanged;
  }

  @Override
  public String toString() {
    return this.getPassaporte();
  }
}
