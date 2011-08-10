package br.com.capanema.kers.common.model.projectea;
@Deprecated
public enum TipoPlataforma {

	JAVA(1, "Java"), PHP(2, "PHP");

	private int codigo;
	private String descricao;

	private TipoPlataforma(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoPlataforma getEnum(String tipo) {
		if (tipo != null) {
			for (TipoPlataforma ad : TipoPlataforma.values()) {
				if (tipo.equals(ad.getDescricao()))
					return ad;
			}
		}
		return JAVA;
	}

	public static boolean contains(String tipo) {
		for (TipoPlataforma ad : TipoPlataforma.values()) {
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