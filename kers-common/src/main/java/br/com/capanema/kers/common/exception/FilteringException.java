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
package br.com.capanema.kers.common.exception;

import br.com.capanema.kers.common.util.string.Filter;

/**
 * <p>Thrown when for some reason one of the methods of the Filter or MatchFilter classes can not return a valid
 * result.</p>
 *
 * @author aubergineanode
 * @version 1.0
 */
public class FilteringException extends RuntimeException {

    private static final long serialVersionUID = -7569082138294780767L;

    /**
     * <p>The filter for which filtering failed.  Set in constructor.</p>
     */
    private final Filter filter;

    /**
     * <p>The attempted string on which filtering failed.  Set in constructor.</p>
     */
    private final String input;

    /**
     * <p>Creates a new FilterException for the given filter and attempted input string.</p>
     *
     * @param filter The filter that was unable to process the input.
     * @param message Description of error.
     * @param input The input for which filtering failed.
     */
    public FilteringException(Filter filter, String message, String input) {
        super(message);

        this.filter = filter;
        this.input = input;
    }

    /**
     * <p>Creates a new FilteringException to wrap another exception.</p>
     *
     * @param filter The filter that was unable to process the request.
     * @param message Description of the error.
     * @param input The input for which filtering failed
     * @param cause The exception that was caught.
     */
    public FilteringException(Filter filter, String message, String input, Throwable cause) {
        super(message, cause);

        this.filter = filter;
        this.input = input;
    }

    /**
     * <p>Returns the filter for which filtering failed.</p>
     *
     * @return The filter for which filtering failed.
     */
    public Filter getFilter() {
        return this.filter;
    }

    /**
     * <p>Returns the input that caused filtering to fail.</p>
     *
     * @return The input that caused filtering to fail.
     */
    public String getInput() {
        return this.input;
    }

}
