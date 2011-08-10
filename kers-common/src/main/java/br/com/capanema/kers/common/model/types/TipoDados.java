/*
 * Prodepa (c) 2007 - Processamento de dados do Estado do Par�
 */
package br.com.capanema.kers.common.model.types;


public enum TipoDados {

	STRING(1, "String"),
	INTEGER(2, "Integer"),
	LONG(2, "Long"),
	FLOAT(3, "Float"),
	DOUBLE(3, "Double"),
	DATA(4, "Date"),
	BOOLEAN(5, "Boolean"),
	TIPOBOLEANO(6, "TipoBoleano"),
	OBJETO(7, "Object"),
	LISTA(8, "List"),
	OUTRO(100, "Outro"),;

	private int tratamento;
	private String tipo;
	

	private TipoDados(int tratamento, String tipo) {
		this.tratamento = tratamento;
		this.tipo = tipo;
	}

	public int getTratamento() {
		return tratamento;
	}

	public String getTipo() {
		return tipo;
	}

	public static TipoDados getEnum(String tipo) {
		if (tipo != null) {
			for (TipoDados ad : TipoDados.values()) {
				if (tipo.equals(ad.getTipo()))
					return ad;
			}
		}
		return OBJETO;
	}

	public static boolean contains(String tipo) {
		for (TipoDados ad : TipoDados.values()) {
			if (tipo.equals(ad.getTipo()))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return this.tipo;
	}

}