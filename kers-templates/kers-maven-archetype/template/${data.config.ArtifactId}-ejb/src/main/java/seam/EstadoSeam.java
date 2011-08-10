#set ($package = ${data.config.mainPackage})
#set ($modelName = ${data.crud.name})
#set ($lowercasedModelName = $stringUtils.unCapitalizeFirstLetter($modelName))
#set ($uppercasedModelName = $modelName.toUpperCase())
#set ($uppercasedModelName = $modelName.toUpperCase())
#set ($normalizedArtefactId = $stringUtils.normalizeClassName(${data.config.artifactId}))
package ${package}.seam;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.security.Restrict;

import ${package}.dto.${modelName}Dto;
import ${package}.dto.buscas.BuscasDto;
import ${package}.seam.factory.ApplicationFactorySeam;
import ${package}.util.messages.SeamMessagesUtil;

@Name("${lowercasedModelName}Action")
@Scope(ScopeType.CONVERSATION)
public class ${modelName}Seam extends MyAbstractSeam {

  @In(create = true, required = false)
  @Out(required = false)  
  BuscasDto buscasDto;

  @In(create = true, required = false)
  @Out(required = false)
  ${modelName}Dto ${lowercasedModelName}Dto;

  @DataModel
  List<${modelName}Dto> ${lowercasedModelName}DtoLista;

  @DataModelSelection("${lowercasedModelName}DtoLista")
  ${modelName}Dto ${lowercasedModelName}DtoSelecionado;
  
  private static final String START_PAGE = "/ucs/${lowercasedModelName}/${lowercasedModelName}_list.xhtml";
  private static final String FORM_PAGE = "/ucs/${lowercasedModelName}/${lowercasedModelName}_form.xhtml";
  private static final String UPDATE_PAGE = "/ucs/${lowercasedModelName}/${lowercasedModelName}_update.xhtml";

  @End(beforeRedirect = true)
  public void cadastrar() {
    try {
      get${normalizedArtefactId}FacadeLocal().cadastrar${modelName}(this.${lowercasedModelName}Dto);
      seamTreatment.addInfoMessage("O ${lowercasedModelName} foi cadastrado com sucesso.");
    } catch (Throwable e) {
      seamTreatment.tratar(e);
    }
  }

  @End(beforeRedirect = true)
  public String alterar() {
    try {
      get${normalizedArtefactId}FacadeLocal().alterar${modelName}(this.${lowercasedModelName}Dto);
      seamTreatment.addInfoMessage("O ${lowercasedModelName} foi alterado com sucesso.");
      return START_PAGE;
    } catch (Throwable e) {
      seamTreatment.tratar(e);
      return null;
    }
  }

  @Begin(join = true)
  public String buscar() {
    try {
      this.${lowercasedModelName}DtoLista = get${normalizedArtefactId}FacadeLocal().buscar${stringUtils.normalizeClassName($modelName)}(this.buscasDto);
      seamTreatment.addInfoMessage(SeamMessagesUtil.getMessageForReturnList(this.${lowercasedModelName}DtoLista));
    } catch (Throwable e) {
      seamTreatment.tratar(e);
    }
    return START_PAGE;
  }

  @End(beforeRedirect = true)
  public String excluir() {
    try {
      get${normalizedArtefactId}FacadeLocal().excluir${modelName}(this.${lowercasedModelName}Dto);
      seamTreatment.addInfoMessage("O ${lowercasedModelName} foi excluído com sucesso.");
    } catch (Throwable e) {
      seamTreatment.tratar(e);
    }
    return START_PAGE;
  }

  @Begin(nested = true)
  public void selecionar() {
    this.showConfirmCadastro = false;
    this.${lowercasedModelName}Dto = this.${lowercasedModelName}DtoSelecionado;
  }

  @Begin(nested = true)
  public String formConversation() {
    this.${lowercasedModelName}Dto = new ${modelName}Dto();
    return FORM_PAGE;
  }
  
  @Begin(nested = true)
  public String updateConversation() {
    selecionar();
    return UPDATE_PAGE;
  }

  @End(beforeRedirect = true)
  public String backToSearch() {
    return START_PAGE;
  }
  
  @End(beforeRedirect = true, root = true)
  public String startConversation() {
    return START_PAGE;
  }
}