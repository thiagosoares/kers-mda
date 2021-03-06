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

import br.com.capanema.kers.common.exception.FilteringException;

/**
 * <p>
 * This class inverts the replacements of a MatchFilter, transforming an
 * "exclude" type filter into an "include" type filter (or vice versa). It's
 * primary use is with the TextFilter and RegexFilter classes to create
 * "include" type filters. Under no circumstances should this filter be used
 * with "replacement" type filters - the results are not valid.
 * </p>
 * 
 * @author aubergineanode
 * @version 1.0
 */
public class NegatingMatchFilter extends MatchFilter {

  /**
   * <p>
   * The filter that is having its list of changes inverted.
   * </p>
   */
  private final MatchFilter internalFilter;

  /**
   * <p>
   * Creates a new NegatingMatchFilter around the given filter.
   * </p>
   * 
   * @param internalFilter The filter that is having its list of changes
   *          inverted.
   * 
   * @throws NullPointerException If internalFilter is null.
   */
  public NegatingMatchFilter(MatchFilter internalFilter) {
    if (internalFilter == null) {
      throw new NullPointerException("internalFilter should not be null.");
    }

    this.internalFilter = internalFilter;
  }

  /**
   * <p>
   * This method implements the MatchFilter.getAllReplacements() method.
   * </p>
   * 
   * <p>
   * Returns all replacements that invert the original filter. Calls the
   * getAllReplacements() method of the internal filter, and returns a list of
   * FilterReplacement's of all non-zero length gaps between the replacements of
   * the internal filter (including possible gaps at the start and end of the
   * input string). The returned matches should have the empty string as a
   * replacement.
   * </p>
   * 
   * @param input The string to generate all changes necessary for filtering.
   * 
   * @return A list of necessary changes to filter the string.
   * 
   * @throws NullPointerException Thrown if input is null.
   * @throws FilteringException Thrown if generation of the list of replacements
   *           can not be completed for any reason.
   */
  public List<DefaultFilterReplacement> getAllReplacements(String input) {
    if (input == null) {
      throw new NullPointerException("input should not be null.");
    }
    // Mark all the replacements onto a boolean array.
    boolean[] flags = FilterUtility.markReplacements(this, input, this.internalFilter.getAllReplacements(input));
    // Collect the excluded gaps to form an array of replacements.
    return FilterUtility.collectReplacements(flags);
  }

  /**
   * <p>
   * This method implements the MatchFilter.getFirstReplacement() method.
   * </p>
   * 
   * <p>
   * Returns the first replacement for the inversion of the internalFilter. This
   * method will return the first element generated by getAllReplacements().
   * </p>
   * 
   * @param input The input string to get the first replacement for.
   * 
   * @return The first replacement to be made in the process of filtering the
   *         input. If no such replacement is necessary, returns null.
   * 
   * @throws NullPointerException Thrown if input is null.
   * @throws FilteringException Thrown if for any reason the first replacement
   *           can not be generated.
   */
  public FilterReplacement getFirstReplacement(String input) {
    // This method simply delegates to getAllReplacements().
    List<DefaultFilterReplacement> allReplacements = this.getAllReplacements(input);
    if (allReplacements.isEmpty()) {
      return null;
    }
    return (FilterReplacement) allReplacements.get(0);
  }

  /**
   * <p>
   * Returns the filter being inverted.
   * </p>
   * 
   * @return The filter being inverted.
   */
  public MatchFilter getInternalFilter() {
    return this.internalFilter;
  }

}
