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

import br.com.capanema.kers.common.exception.FilteringException;

/**
 * <p>Implementa��es da interface Filtro devem filtrar uma string de alguma forma. Se a informa��o foi
 * passada sobre que trocas ser�o feitas para tansformar a string, ent�o a classe abstrata MatchFilter
 * deve ser extendida.</p>
 *
 * @author aubergineanode
 * @version 1.0
 */
public interface Filter {

    /**
     * <p>Roda esse filtro para a entrada informada.</p>
     *
     * <p>Esse m�todo lan�a a exce��o n�o checada FilteringException. Se qualquer opera��o usada
     * por esse m�todo puder lan�ar uma exce��o, ent�o essa exce��o dever� ser lan�ada e a exce��o
     * FilteringException dever� ser empacotada dentro dela.</p>
     *
     * @param input A string de entrada a ser filtrada.
     *
     * @return A string filtrada.
     *
     * @throws NullPointerException Levantada se a entrada for nula.
     * @throws FilteringException Levantada se por qualquer raz�o o filtro n�o for aplicado com sucesso nessa entrada.
     */
    String filter(String input);

}
