#set ($package = ${data.config.mainPackage})
#set ($modelName = ${data.crud.name})
#set ($lowercasedModelName = $stringUtils.unCapitalizeFirstLetter($modelName))
#set ($uppercasedModelName = $modelName.toUpperCase())
package ${package}.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import br.gov.pa.muiraquita.entity.AbstractEntity;

@Entity
@Table(name = "${uppercasedModelName}", schema = "{data.config.dbSchema}")
public class ${modelName} extends AbstractEntity {

  private static final long serialVersionUID = 1L;
  
  #foreach( $field in $data.crud.crudClass.fieldsInOrder )
  #set ($attrName = ${field.name})
    #foreach( $annot in $field.annotations )
    ${annot}    
    #end
    private ${field.dataType} ${field.name};
    
  #end

    public ${modelName}() {
      super();
    }
    
  #foreach( $field in $data.crud.crudClass.fieldsInOrder )
  #set ($attrName = ${field.name})
  #set ($capName = $stringUtils.capitalizeFirstLetter(${field.name}))

    public ${field.dataType} get${capName}() {
      return ${attrName};  
    }

    public void set${capName}(${field.dataType} ${attrName}) {
      this.${attrName} = ${attrName};
    }
  #end
  
}