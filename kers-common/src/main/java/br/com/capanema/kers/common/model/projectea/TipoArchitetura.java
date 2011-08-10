/*
 * Prodepa (c) 2007 - Processamento de dados do Estado do Par�
 */
package br.com.capanema.kers.common.model.projectea;


public enum TipoArchitetura {

	CALANGO(1, "Calango"),
	MUIRAQUITA_FULL(2, "MUIRAQUITA FULL"),
	MUIRAQUITA_LIGTH(3, "MUIRAQUITA LIGTH");

	private int codigo;
	private String descricao;
	
	private TipoArchitetura(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoArchitetura getEnum(String tipo) {
		if (tipo != null) {
			for (TipoArchitetura ad : TipoArchitetura.values()) {
				if (tipo.equals(ad.getDescricao()))
					return ad;
			}
		}
		return CALANGO;
	}

	public static boolean contains(String tipo) {
		for (TipoArchitetura ad : TipoArchitetura.values()) {
			if (tipo.equals(ad.getDescricao()))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}