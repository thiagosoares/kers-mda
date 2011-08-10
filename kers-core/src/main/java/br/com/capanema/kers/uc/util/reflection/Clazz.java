/*
 * Copyright (c) Empresa de Processamento de Dados do Estado do Par�,
 * http://www.prodepa.gov.br/, 1968-2008. Todos os direitos reservados. 
 * Software produzido pelo setor de tecnologia e inova��o (GTI).
 * 
 * A redistribui��o e utiliza��o � restrita ao uso interno da PRODEPA em
 * suas depend�ncias, salvo cedido a alguma outra entidade da esfera municipal,
 * estadual ou federal, o que deve ser uma permiss�o fornecida por escrito, 
 * com caracter�sticas legais, registrado no sistema de protocolo do Estado do
 * Par�.
 * 
 * Java, o mascote Duke e todas as variantes do logo de Java da Sun s�o direitos
 * atribuidos � Sun Microsystems. Ao pioneirismo em inventar e promulgar (e
 * padronizar) a linguagem java e seu ambiente da Sun e ao James Gosling, aqui
 * ficam nossos sinceros agradecimentos. Ao pioneirismo de Dennis Ritchie e Bjarne 
 * Stroustrup, da AT&T, por inventar as linguagens C e C++, predecessoras do java,
 * tamb�m ficam nossos sinceros agradecimentos.
 */
package br.com.capanema.kers.uc.util.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public class Clazz {

  private static Logger log = Logger.getLogger(Clazz.class);

  /** Suffix for array class names: "[]" */
  public static final String ARRAY_SUFFIX = "[]";

  /** Prefix for internal array class names: "[L" */
  private static final String INTERNAL_ARRAY_PREFIX = "[L";

  /** The package separator character '.' */
  private static final char PACKAGE_SEPARATOR = '.';

  /** The inner class separator character '$' */
  private static final char INNER_CLASS_SEPARATOR = '$';

  /** The CGLIB class separator character "$$" */
  public static final String CGLIB_CLASS_SEPARATOR = "$$";

  /** The ".class" file suffix */
  public static final String CLASS_FILE_SUFFIX = ".class";
 
  
  /**
   * Map with primitive wrapper type as key and corresponding primitive
   * type as value, for example: Integer.class -> int.class.
   */
  private static final Map primitiveWrapperTypeMap = new HashMap(8);

  
  /**
   * Map with primitive type name as key and corresponding primitive
   * type as value, for example: "int" -> "int.class".
   */
  private static final Map primitiveTypeNameMap = new HashMap(16);
  
  
  static {
    primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
    primitiveWrapperTypeMap.put(Byte.class, byte.class);
    primitiveWrapperTypeMap.put(Character.class, char.class);
    primitiveWrapperTypeMap.put(Double.class, double.class);
    primitiveWrapperTypeMap.put(Float.class, float.class);
    primitiveWrapperTypeMap.put(Integer.class, int.class);
    primitiveWrapperTypeMap.put(Long.class, long.class);
    primitiveWrapperTypeMap.put(Short.class, short.class);

    Set primitiveTypeNames = new HashSet(16);
    primitiveTypeNames.addAll(primitiveWrapperTypeMap.values());
    primitiveTypeNames.addAll(Arrays.asList(new Class[] {
        boolean[].class, byte[].class, char[].class, double[].class,
        float[].class, int[].class, long[].class, short[].class}));
    for (Iterator it = primitiveTypeNames.iterator(); it.hasNext();) {
      Class primitiveClass = (Class) it.next();
      primitiveTypeNameMap.put(primitiveClass.getName(), primitiveClass);
    }
  }

  
  /**
   * Retorna o tipo gen�rico da superclasse.
   * 
   * Poder� ser utilizada em qualquer n�vel hier�rquico.
   * Para utiliz�-la, voc� precisa declarar uma superclasse com uma interface,
   * ambas implementando um generic; al�m de outra classe extendendo a classe citada
   * anteriormente.
   *  
   * @param classe classe a qual voc� deseja saber o tipo gen�rico declarado
   * @param declarationNumber n�mero da declara��o gen�rica
   * @return o tipo declarado, a qual pode ser feita um classcast para a classe gen�rica
   */
  public static Type typeForGenericSuperclass(Class<?> classe, int declarationNumber) {
    Class<?> clazz = classe;
    while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
      clazz = clazz.getSuperclass();
    }
    return ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[declarationNumber];
  }
  
  public static TypeVariable<?> t(Class<?> classe) {
    return classe.getTypeParameters()[0];
  }
  
  @SuppressWarnings("unchecked")
  public static <T> T newInstance(Class<T> classe) {
    try {
      return classe.newInstance();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    	//throw ThrowMachine.levantarErrosInternoNaoChecado(InternalErrorProdepa.MANIPULACAO_REFLECTION, "N�o foi poss�vel instanciar um objeto para a classe " + classe.getName(), e);
    } 
  }

  
  public static Object newInstance(String classe) {
    try {
      return Class.forName(classe).newInstance();
    } catch (Exception e) {
    	e.printStackTrace(); 
    	return null;
      //throw ThrowMachine.levantarErrosInternoNaoChecado(InternalErrorProdepa.MANIPULACAO_REFLECTION, "N�o foi poss�vel instanciar um objeto para a classe " + classe, e);
    } 
  }

  
  /**
   * Resolve the given class name into a Class instance. Supports
   * primitives (like "int") and array class names (like "String[]").
   * <p>This is effectively equivalent to the <code>forName</code>
   * method with the same arguments, with the only difference being
   * the exceptions thrown in case of class loading failure.
   * @param className the name of the Class
   * @param classLoader the class loader to use
   * (may be <code>null</code>, which indicates the default class loader)
   * @return Class instance for the supplied name
   * @throws IllegalArgumentException if the class name was not resolvable
   * (that is, the class could not be found or the class file could not be loaded)
   * @see #forName(String, ClassLoader)
   */
  public static Class resolveClassName(String className, ClassLoader classLoader) throws IllegalArgumentException {
    try {
      return forName(className, classLoader);
    }
    catch (ClassNotFoundException ex) {
      IllegalArgumentException iae = new IllegalArgumentException("Cannot find class [" + className + "]");
      iae.initCause(ex);
      throw iae;
    }
    catch (LinkageError ex) {
      IllegalArgumentException iae = new IllegalArgumentException(
          "Error loading class [" + className + "]: problem with class file or dependent class.");
      iae.initCause(ex);
      throw iae;
    }
  }

  /**
   * Replacement for <code>Class.forName()</code> that also returns Class instances
   * for primitives (like "int") and array class names (like "String[]").
   * <p>Always uses the default class loader: that is, preferably the thread context
   * class loader, or the ClassLoader that loaded the ClassUtils class as fallback.
   * @param name the name of the Class
   * @return Class instance for the supplied name
   * @throws ClassNotFoundException if the class was not found
   * @throws LinkageError if the class file could not be loaded
   * @see Class#forName(String, boolean, ClassLoader)
   * @see #getDefaultClassLoader()
   */
  @SuppressWarnings("unchecked")
  public static Class forName(String name) throws ClassNotFoundException, LinkageError {
    return forName(name, getDefaultClassLoader());
  }

  /**
   * Return the default ClassLoader to use: typically the thread context
   * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
   * class will be used as fallback.
   * <p>Call this method if you intend to use the thread context ClassLoader
   * in a scenario where you absolutely need a non-null ClassLoader reference:
   * for example, for class path resource loading (but not necessarily for
   * <code>Class.forName</code>, which accepts a <code>null</code> ClassLoader
   * reference as well).
   * @return the default ClassLoader (never <code>null</code>)
   * @see java.lang.Thread#getContextClassLoader()
   */
  public static ClassLoader getDefaultClassLoader() {
    ClassLoader cl = null;
    try {
      cl = Thread.currentThread().getContextClassLoader();
    }
    catch (Throwable ex) {
      log.debug("Cannot access thread context ClassLoader - falling back to system class loader", ex);
    }
    if (cl == null) {
      // No thread context class loader -> use class loader of this class.
      cl = Clazz.class.getClassLoader();
    }
    return cl;
  }

  
  /**
   * Replacement for <code>Class.forName()</code> that also returns Class instances
   * for primitives (like "int") and array class names (like "String[]").
   * @param name the name of the Class
   * @param classLoader the class loader to use
   * (may be <code>null</code>, which indicates the default class loader)
   * @return Class instance for the supplied name
   * @throws ClassNotFoundException if the class was not found
   * @throws LinkageError if the class file could not be loaded
   * @see Class#forName(String, boolean, ClassLoader)
   */
  @SuppressWarnings("unchecked")
  public static Class forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {

    Class clazz = resolvePrimitiveClassName(name);
    if (clazz != null) {
      return clazz;
    }

    // "java.lang.String[]" style arrays
    if (name.endsWith(ARRAY_SUFFIX)) {
      String elementClassName = name.substring(0, name.length() - ARRAY_SUFFIX.length());
      Class elementClass = forName(elementClassName, classLoader);
      return Array.newInstance(elementClass, 0).getClass();
    }

    // "[Ljava.lang.String;" style arrays
    int internalArrayMarker = name.indexOf(INTERNAL_ARRAY_PREFIX);
    if (internalArrayMarker != -1 && name.endsWith(";")) {
      String elementClassName = null;
      if (internalArrayMarker == 0) {
        elementClassName = name.substring(INTERNAL_ARRAY_PREFIX.length(), name.length() - 1);
      }
      else if (name.startsWith("[")) {
        elementClassName = name.substring(1);
      }
      Class elementClass = forName(elementClassName, classLoader);
      return Array.newInstance(elementClass, 0).getClass();
    }

    ClassLoader classLoaderToUse = classLoader;
    if (classLoaderToUse == null) {
      classLoaderToUse = getDefaultClassLoader();
    }
    return classLoaderToUse.loadClass(name);
  }

  /**
   * Resolve the given class name as primitive class, if appropriate,
   * according to the JVM's naming rules for primitive classes.
   * <p>Also supports the JVM's internal class names for primitive arrays.
   * Does <i>not</i> support the "[]" suffix notation for primitive arrays;
   * this is only supported by {@link #forName}.
   * @param name the name of the potentially primitive class
   * @return the primitive class, or <code>null</code> if the name does not denote
   * a primitive class or primitive array class
   */
  @SuppressWarnings("unchecked")
  public static Class resolvePrimitiveClassName(String name) {
    Class result = null;
    // Most class names will be quite long, considering that they
    // SHOULD sit in a package, so a length check is worthwhile.
    if (name != null && name.length() <= 8) {
      // Could be a primitive - likely.
      result = (Class) primitiveTypeNameMap.get(name);
    }
    return result;
  }

  
  /**
   * Return the qualified name of the given class: usually simply
   * the class name, but component type class name + "[]" for arrays.
   * @param clazz the class
   * @return the qualified name of the class
   */
  public static String getQualifiedName(Class clazz) {
    if (clazz.isArray()) {
      return getQualifiedNameForArray(clazz);
    }
    else {
      return clazz.getName();
    }
  }

  /**
   * Build a nice qualified name for an array:
   * component type class name + "[]".
   * @param clazz the array class
   * @return a qualified name for the array class
   */
  private static String getQualifiedNameForArray(Class clazz) {
    StringBuffer buffer = new StringBuffer();
    while (clazz.isArray()) {
      clazz = clazz.getComponentType();
      buffer.append(Clazz.ARRAY_SUFFIX);
    }
    buffer.insert(0, clazz.getName());
    return buffer.toString();
  }
  
}