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

/**
 * <p>Provides a default implementation of the FilterReplacement interface. It is just a storage container for
 * the indexes and replacement fields.</p>
 *
 * @author aubergineanode
 * @version 1.0
 */
public class DefaultFilterReplacement implements FilterReplacement {

    /**
     * <p>The string that should replace the characters between startIndex and endIndex in the input string.
     * Set in constructor.</p>
     */
    private final String replacement;

    /**
     * <p>The index at which the replacement should start taking place. Set in constructor.</p>
     */
    private final int startIndex;

    /**
     * <p>The index at which replacement should end. Set in constructor.</p>
     */
    private final int endIndex;

    /**
     * <p>Creates a new DefaultFilterReplacement with the given parameters.</p>
     *
     * @param replacement The replacement string.
     * @param startIndex The start index of the replacement.
     * @param endIndex The end index of the replacement.
     *
     * @throws NullPointerException If replacement is null.
     * @throws IllegalArgumentException If startIndex is negative or startIndex is greater than endIndex.
     */
    public DefaultFilterReplacement(String replacement, int startIndex, int endIndex) {
        if (replacement == null) {
            throw new NullPointerException("replacement should not be null.");
        }
        if (startIndex < 0) {
            throw new IllegalArgumentException("startIndex should not be negative.");
        }
        if (startIndex > endIndex) {
            throw new IllegalArgumentException("startIndex should not be greater than endIndex.");
        }

        this.replacement = replacement;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    /**
     * <p>Returns the String that should be replaced in the original between the start and end indexes.</p>
     *
     * @return The replacement string that should replace the characters from startIndex to endIndex - 1 (inclusive)
     *         in the input string.
     */
    public String getReplacement() {
        return this.replacement;
    }

    /**
     * <p>Returns the start index of the replacement in the original string. This follows the normal java string
     * indexing convention.</p>
     *
     * @return The starting index at which replacement should take place.
     */
    public int getStartIndex() {
        return this.startIndex;
    }

    /**
     * <p>Returns the end index of the replacement in the original string. This follows the normal java string
     * indexing convention.</p>
     *
     * @return The ending index at which replacement should take place.
     */
    public int getEndIndex() {
        return this.endIndex;
    }

}
