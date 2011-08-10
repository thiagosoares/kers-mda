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

import java.lang.reflect.InvocationTargetException;

public class GenericReflection {

  @SuppressWarnings("unchecked")
  public static <T> T newInstance(T obj) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    Object newobj = obj.getClass().getConstructor().newInstance();
    return (T) newobj; // cast n�o checado
  }

  @SuppressWarnings("unchecked")
  public static <T> Class<? extends T> getComponentType(T[] a) {
    Class<?> k = a.getClass().getComponentType();
    return (Class<? extends T>) k; // cast n�o checado
  }

  /*@SuppressWarnings("unchecked")
  public static <T> T[] newArray(Class<? extends T> k, int size) {
    if (k.isPrimitive())
      throw ThrowMachine.levantarErrosInterno(InternalErrorProdepa.MANIPULACAO_REFLECTION, "O argumento n�o pode ser primitivo: " + k, null);
    Object a = java.lang.reflect.Array.newInstance(k, size);
    return (T[]) a; // cast n�o checado
  }

  public static <T> T[] newArray(T[] a, int size) {
    return newArray(getComponentType(a), size);
  }*/
  
}
