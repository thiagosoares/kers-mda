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
 * <p>Provides information about where a filter will replace some characters in an input string, and what those
 * characters should be replaced with.</p>
 *
 * @author aubergineanode
 * @version 1.0
 */
public interface FilterReplacement {

    /**
     * <p>Returns the start index of the replacement in the original string. This follows the normal java string
     * indexing convention.</p>
     *
     * @return The starting index at which replacement should take place.
     */
    int getStartIndex();

    /**
     * <p>Returns the end index of the replacement in the original string. This follows the normal java string
     * indexing convention.</p>
     *
     * @return The ending index at which replacement should take place.
     */
    int getEndIndex();

    /**
     * <p>Returns the String that should be replaced in the original between the start and end indexes.</p>
     *
     * @return The replacement string that should replace the characters from startIndex to endIndex - 1 (inclusive)
     *         in the input string.
     */
    String getReplacement();

}
