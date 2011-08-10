#set ($package = ${data.config.mainPackage})
#set ($modelName = ${data.crud.name})
#set ($lowercasedModelName = $stringUtils.unCapitalizeFirstLetter($modelName))
#set ($uppercasedModelName = $modelName.toUpperCase())
package ${package}.business.error; 
 
import ${package}.configuration.LocationsCDU;

import br.gov.pa.muiraquita.exception.retorno.retenum.ErrorCode;
 
public enum ${modelName}Error implements ErrorCode { 

  //for
 	CAMPO_NULL(2, "O ${CAMPO} é obrigatório. "),
 	CAMPO_INVALID(3, "O valor informado para ${CAMPO} está inválido."),
 	//
 	
 	ESTADO_DUPLICATED(4, "Um registroo de  ${lowercasedModelName} já está cadastrado com essas informações."),
 	ESTADO_CANNOT_DELETE(5, "O registro de ${lowercasedModelName} não pode ser excluí­do ."); 
 
	private String errorMensagem; 
 	private int codigo;  
 
 	private ${modelName}Error(int codigo, String errorMensagem) {
 		this.codigo = codigo; 
 		this.errorMensagem = errorMensagem; 
 	} 
 
  public String getErrorMessage() { 
    return errorMensagem; 
  } 
  
  public int getCode() { 
    return codigo; 
  } 
 
  public String locateCode() { 
    return LocationsCDU.CDU_MANTER_ESTADO; 
  }  
}