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
 * <p>Ao inv�s de filtrar uma string, subclasses da classe MatchFilter dizem quais ser�o as trocas
 * necess�rias para filtrar a string. Note que essas trocas podem incluir uma string vazia, ent�o os
 * tipo de filtro "include" e "exclude" podem ser tratados equivalentemente aos tipos de filtro
 * "replacement".
 * 
 * @author aubergineanode
 * @author <a link="mailto:valtoni.boaventura@prodepa.pa.gov.br">Valtoni Boaventura</a>
 * @version 1.0
 */
public abstract class MatchFilter implements Filter {

    /**
     * <p>Cria um novo MatchFilter.</p>
     */
    public MatchFilter() {
    }

    /**
     * <p>Implementa o m�todo Filter.filter().</p>
     *
     * <p>A filtragem � obtida aplicando cada uma das trocas obtidas pelo m�todo getAllReplacements()
     * na ordem informada.
     *
     * @param input A string de entrada a ser filtrada.
     *
     * @return A string filtrada.
     *
     * @throws NullPointerException Levantada se a entrada for nula.
     * @throws FilteringException Levantada se por qualquer raz�o o filtro n�o for aplicado com sucesso nessa entrada.
     */
    public String filter(String input) {
        if (input == null) {
            throw new NullPointerException("a entrada n�o deve ser nula.");
        }

        // Aplica todas as trocas com a classe utilit�ria.
        return FilterUtility.applyReplacements(
                this,
                input,
                this.getAllReplacements(input));
    }

    /**
     * <p>Generates a list of replacements necessary to filter the input string. The returned list should consist
     * of FilterReplacement objects. If no replacements are needed, an empty list should be returned. Each
     * FilterReplacement in the returned list should have its start and end indexes referenced to the string as if
     * all previous replacements in the list had already been applied, not in regard to where the replacement takes
     * place in the input string.</p>
     *
     * <p>For example, if the filter is replacing "abc" with "z", and the input is "abc#abc" then the returned set
     * of indexes should be (start = 0, end = 3), (start = 2, end = 5). It should not be (start = 0, end = 3),
     * (start = 4, end = 7).</p>
     *
     * <p>This method throws the unchecked exception FilteringException. If any operation used in this method can
     * cause an exception to be thrown, then the exception should be caught and a FilteringException wrapping the
     * caught exception should be thrown.</p>
     *
     * @param input The string to generate all changes necessary for filtering.
     *
     * @return A list of necessary changes to filter the string.
     *
     * @throws NullPointerException Thrown if input is null.
     * @throws FilteringException Thrown if generation of the list of replacements can not be completed for any reason.
     */
    public abstract List<DefaultFilterReplacement> getAllReplacements(String input);

    /**
     * <p>Generates the first replacement to be used in filtering the input string. If no replacement is needed,
     * null should be returned. This method should always return the same as getAllReplacements().get(0). However,
     * implementations are encouraged to determine the first replacement in a faster way than generating all
     * replacements and returning the first of them.</p>
     *
     * <p>This method throws the unchecked exception FilteringException. If any operation used in this method can
     * cause a exception to be thrown, then the exception should be caught and a FilteringException wrapping the
     * caught exception should be thrown.</p>
     *
     * @param input The input string to get the first replacement for.
     *
     * @return The first replacement to be made in the process of filtering the input. If no such replacement is
     *         necessary, returns null.
     *
     * @throws NullPointerException Thrown if input is null.
     * @throws FilteringException Thrown if for any reason the first replacement can not be generated.
     */
    public abstract FilterReplacement getFirstReplacement(String input);

}
