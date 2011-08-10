package br.com.capanema.kers.core.util.reflection;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * Manipula diretamente propertys de beans.
 * Existe depend�ncia da biblioteca <a href="http://commons.apache.org/beanutils/">common-beanutils.jar</a>, 
 * da Apache. Sendo utilizada para manipula��o dos beans.
 * <b>ATEN��O:</b> Utilize esta classe utilit�ria com cautela. Todos os m�todos efetuam <code>throw</code>
 * com exce��es n�o checadas pois dessa forma o c�digo nas aplica��es que o utilizam diminuem 
 * consideravelmente; por�m h� trechos que voc� <b>DEVE</b> efetuar tratamento de exce��o, por isso seja
 * cuidadoso ao utilizar esta classe, ela pode ajud�-lo bastante, por�m pode ser um meio para que
 * problemas futuros n�o sejam solucionados rapidamente, se mal utilizada.
 * @author <a link="mailto:valtoni.boaventura@prodepa.pa.gov.br">Valtoni Boaventura</a>
 *
 */
public final class Property {

  /** Objeto interno de manipula��o beanUtils */
  private static transient PropertyUtilsBean pub = new PropertyUtilsBean();
  
  private static transient Logger log = LogManager.getLogger(Property.class);
  
  /**
   * Checagem de idoneidade, serve para verificar se o objeto est� �ntegro. 
   * @param bean objeto passado a ser manipulado (n�o pode ser nulo)
   * @param nomePropriedade propriedade a ser manipulada (n�o pode ser nula)
 * @throws Exception 
   * @throws InternalException
   */
  private static void checkMandatoryProperties(Object bean, String nomePropriedade) throws Exception  {
    if (bean == null) {
      throw new Exception("Para descobrir sua classe � necess�rio passar um objeto");
    }
    if (nomePropriedade == null) {
    	throw new Exception("C� t� de brincadeira n�? PASSA O NOME DA PROPRIEDADE RAP�!!!!");
    }
  }
  
  /**
   * Retorna as propriedades que uma classe tem.
   * Aten��o: apenas constar� nesta lista as propriedades que sejam realmente PROPRIEDADES
   * por defini��o, ou seja, campos acess�veis, encapsulados que tejam m�todos "get" e "set"
   * p�blicos devidamente declarados.
   * @param clazz classe a ser analizada
   * @return lista de propriedades acess�veis
   */
  public static List<String> getProperties(Class<?> clazz) {
    List<String> internalFields = new ArrayList<String>();
    // Captura o descritor de propriedades da classe informada
    PropertyDescriptor[] properties = pub.getPropertyDescriptors(clazz);
    for (PropertyDescriptor property: properties) {
      if (!property.getName().equals("class")) {
        internalFields.add(property.getName());
      }
    }
    return internalFields;
  }
  
  /**
   * Obt�m o valor de uma determinada propriedade no objeto informado.
   * @param bean objeto a ser manipulado
   * @param nomePropriedade propriedade a ser obtida do bean
   * @return inst�ncia referida do bean pelo nomePropriedade
 * @throws Exception 
   * @throws InternalException caso a propriedade n�o exista e/ou n�o esteja acess�vel (SecurityError)
   */
  public static Object getPropertyValue(Object bean, String nomePropriedade) throws Exception {
    checkMandatoryProperties(bean, nomePropriedade);
    try {
      return PropertyUtils.getProperty(bean, nomePropriedade);
    } catch (IllegalAccessException e) {
      e.printStackTrace(); return null;
    } catch (InvocationTargetException e) {
      e.printStackTrace(); return null;
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
      log.error("M�todo inexistente no alvo " + bean.getClass() + ": " + nomePropriedade);
      return null;
    }
  }
  
  public static void setFieldValue(Object bean, String nomePropriedade, Object valor) throws Exception {
    checkMandatoryProperties(bean, nomePropriedade);
    try {
      Field field;
      field = bean.getClass().getField(nomePropriedade);
      field.setAccessible(true);
      field.set(bean, valor);
    } catch (NoSuchFieldException e) {
      log.error("Field inexistente no alvo " + bean + ": " + nomePropriedade);
    } catch (Exception e) {
      log.error("Erro ao setar field: " + nomePropriedade);
      e.printStackTrace();
    }      
  }
  
  public static Object getFieldValue(Object bean, String nomePropriedade) throws Exception {
    checkMandatoryProperties(bean, nomePropriedade);
    try {
      Field field;
      field = bean.getClass().getField(nomePropriedade);
      field.setAccessible(true);
      return field.get(bean);
    } catch (NoSuchFieldException e) {
      log.error("Field inexistente no alvo " + bean + ": " + nomePropriedade);
      return null;
    } catch (Exception e) {
      log.error("Erro ao setar field: " + nomePropriedade);
      return null;
    }      
  }
  
  /**
   * Define um valor para o bean, segundo a propriedade passada.
   * 
   * Este m�todo � "semi-inteligente": faz a convers�o entre propriedade e valor, contanto
   * que sejam num�ricos
   *  
   * @param bean inst�ncia a ser manipulada
   * @param nomePropriedade propriedade a ser setada no bean 
   * @param valor valor a ser setado na propriedade passada para o bean
 * @throws Exception 
   */
  /*public static void setPropertyValue(Object bean, String nomePropriedade, Object valor) {
    checkMandatoryProperties(bean, nomePropriedade);
    try {
      if (bean == null) {
        throw ThrowMachine.levantarErrosInternoNaoChecado(PROPRIEDADE_INEXISTENTE, "O objeto fonte estava nulo", null);
      }
      Class<?> classeDestino = getClassType(bean, nomePropriedade);
      Class<?> classeFonte = valor.getClass(); 
      
      if (valor == null) {
        log.trace("Destino: " + nomePropriedade + "[" + classeDestino.getName() + "], Fonte: " + nomePropriedade + ", VALOR NULO");
        PropertyUtils.setProperty(bean, nomePropriedade, null);
        return;
      }
      if (classeDestino == null) {
        log.trace("Destino: " + nomePropriedade + ", Fonte: " + bean + ", (Classe Desconhecida): " + valor);
        PropertyUtils.setProperty(bean, nomePropriedade, valor);
        return;
      }
      
      log.trace("Destino: " + nomePropriedade + "[" + classeDestino.getName() + "], Fonte: " + nomePropriedade + "[" + valor.getClass().getName() + "], valor: " + valor);
      
      if (NumberUtil.checkNumber(classeDestino, classeFonte)) {
        log.trace("Convers�o entre num�ricos");
        PropertyUtils.setProperty(bean, nomePropriedade, NumberUtil.convertNumberToTargetClass((Number)valor, classeDestino));
      }
      else if (classeDestino == Character.class && classeFonte == String.class) {
        log.trace("DestType: CHARACTER e FontType: STRING, fazendo convers�o");
        if (((String)valor).length() > 1) {
          log.warn("A convers�o aproveitou apenas o 1o. caracter '" + ((String)valor).toCharArray()[0] + "'");
        }
        PropertyUtils.setProperty(bean, nomePropriedade, ((String)valor).toCharArray()[0]);
      }
      else {
        PropertyUtils.setProperty(bean, nomePropriedade, valor);
      }
    } catch (IllegalArgumentException e) {
      throw ThrowMachine.levantarErrosInternoNaoChecado(PROPRIEDADE_INEXISTENTE, "Acesso ilegal � propriedade " + nomePropriedade, e);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      throw ThrowMachine.levantarErrosInternoNaoChecado(PROPRIEDADE_INEXISTENTE, "Acesso ilegal � propriedade " + nomePropriedade, e);
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      throw ThrowMachine.levantarErrosInternoNaoChecado(PROPRIEDADE_INEXISTENTE, "Erro ao invocar o alvo " + nomePropriedade, e);
    } catch (NoSuchMethodException e) {
      log.warn("M�todo inexistente no alvo (provavelmente field): " + nomePropriedade);
//      e.printStackTrace();
//      throw ThrowMachine.levantarErrosInterno(PROPRIEDADE_INEXISTENTE, "Erro ao invocar o alvo " + nomePropriedade, e);
    } catch (Exception e) {
      e.printStackTrace();
      throw ThrowMachine.levantarErrosInternoNaoChecado(INDEFINIDO, "Erro ao invocar o alvo " + nomePropriedade, e);
    }
  }*/
  
  
  public static Class<?> getClassType(Object bean, String nomePropriedade) throws Exception {
    checkMandatoryProperties(bean, nomePropriedade);
    try {
      log.trace("Tentando obter a classe da propriedade " + nomePropriedade + " no bean " + bean);
      return PropertyUtils.getPropertyType(bean, nomePropriedade);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return null;
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      return null;
    } catch (NoSuchMethodException e) {
      return null;
    }

  }
  
}
