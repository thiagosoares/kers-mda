/*
 * Copyright (c) Empresa de Processamento de Dados do Estado do Par�,
 * http://www.pa.muiraquita.quita.gov.br/, 1968-2008. Todos os direitos reservados. 
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
package br.com.capanema.kers.common.util.string;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import br.com.capanema.kers.common.exception.FilteringException;

/**
 * <p>
 * Package level utility classes used to help implementation of the filters.
 * </p>
 * 
 * <p>
 * The methods contained are oriented to implement non-trivial functionality
 * that is reused in the package. Both the class and the methods (and constant)
 * are of package visibility.
 * </p>
 * 
 * @version 1.0
 */
class FilterUtility {

  /**
   * <p>
   * The empty string constant.
   * </p>
   */
  static final String EMPTY = "";

  /**
   * <p>
   * Apply a list of replacements to a string in sequence.
   * </p>
   * 
   * @param filter The filter requesting this action (for exception purpose).
   * @param input The input string to be filtered.
   * @param replacements The list of replacements to apply.
   * 
   * @return The string after all the replacements applied in sequence.
   * 
   * @throws FilteringException Thrown if some replacement references out of the
   *           string.
   */
  static String applyReplacements(Filter filter, String input, List<DefaultFilterReplacement> replacements) {
    // Use a StringBuffer to avoid memory reallocation.
    StringBuffer output = new StringBuffer(input);

    for (Iterator<DefaultFilterReplacement> itr = replacements.iterator(); itr.hasNext();) {
      FilterReplacement filterReplacement = (FilterReplacement) itr.next();

      // Make each replacement in sequence.
      try {
        output.replace(filterReplacement.getStartIndex(), filterReplacement.getEndIndex(), filterReplacement.getReplacement());
      } catch (StringIndexOutOfBoundsException sioobe) {

        throw new FilteringException(filter, "Invalid replacement position.", input, sioobe);
      }
    }

    return output.toString();
  }

  /**
   * <p>
   * Mark a list of replacements onto a boolean array. The array will be of the
   * length of the input string. All replaced spots are with true value.
   * </p>
   * 
   * @param filter The filter requesting this action (for exception purpose).
   * @param input The input string to be filtered.
   * @param replacements The list of replacements to mark.
   * 
   * @return a boolean array with the length of the input string. True value
   *         stands for replaced spots.
   * 
   * @throws FilteringException Thrown if any replacement string is not empty.
   */
  static boolean[] markReplacements(Filter filter, String input, List<DefaultFilterReplacement> replacements) {
    // The algorithm does not follow the one recommended by design. One of the
    // purpose is to
    // save memory usage (an integer array). Another reason is that the
    // recommended algorithm
    // requires to update all offsets after start position for each replacement
    // and that could
    // be quite inefficient. In theory a naive approach (searching from start
    // for each replacement)
    // is of equivalent complexity. Optimization is applied here if the
    // replacements are sorted
    // (as the result of TextFilter and RegexFilter will be), the position and
    // offset will not
    // go back. This means the mark-up is performed in linear time.
    int totalLength = input.length();
    int currentPos = 0;
    int currentOffset = 0;
    boolean[] flags = new boolean[totalLength];

    for (Iterator<DefaultFilterReplacement> itr = replacements.iterator(); itr.hasNext();) {
      FilterReplacement filterReplacement = (FilterReplacement) itr.next();

      if (filterReplacement.getReplacement().length() > 0) {
        throw new FilteringException(filter, "Only empty replacement is accepted.", input);
      }

      // If start index goes back just reset the position and offset cache.
      if (filterReplacement.getStartIndex() < currentOffset) {
        currentPos = 0;
        currentOffset = 0;
      }

      // Advance until offset meets start index.
      while (currentOffset < filterReplacement.getStartIndex()
              && currentPos < totalLength) {
        if (!flags[currentPos]) {
          ++currentOffset;
        }
        ++currentPos;
      }

      // Mark up remainMatches characters (that are replaced).
      int remainMatches =
              filterReplacement.getEndIndex()
                      - filterReplacement.getStartIndex();
      while (remainMatches > 0 && currentPos < totalLength) {
        if (!flags[currentPos]) {
          --remainMatches;
          flags[currentPos] = true;
        }
        ++currentPos;
      }
    }

    return flags;
  }

  /**
   * Coleta um vetor marcado de boleano para estruturas a serem trocadas.
   * Posi��es com o valor "true" n�o ser�o trocadas. A string de troca sempre
   * ser� uma string vazia. Posi��es de trica subsequentes ser�o combinadas.
   * 
   * @param flags Vetor de boleanos de onde ser�o coletadas as trocas
   * @return Uma lista de inst�ncia de {@link FilterReplacement}.
   */
  static List<DefaultFilterReplacement> collectReplacements(boolean[] flags) {
    int totalLength = flags.length;
    int currentPos = 0;
    int currentOffset = 0;
    int startIndex = 0;
    int endIndex = 0;

    List<DefaultFilterReplacement> replacements =
            new ArrayList<DefaultFilterReplacement>();
    while (currentPos < totalLength) {
      if (flags[currentPos]) {
        // Se a posi��o est� marcada, simplesmente avan�a
        ++currentPos;
        ++currentOffset;
      } else {
        // Caso contr�rio procura o final do segmento
        startIndex = currentOffset;
        endIndex = currentOffset;
        while (currentPos < totalLength && !flags[currentPos]) {
          ++currentPos;
          ++endIndex;
        }
        // Produz o conte�do da troca e coloca-o na lista para retorno
        replacements.add(new DefaultFilterReplacement(FilterUtility.EMPTY, startIndex, endIndex));
      }
    }

    return replacements;
  }

}
