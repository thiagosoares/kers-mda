package br.com.capanema.kers.common.model.projectea;

public enum TipoEmpacotamento {

	EAR(1, "Java"), WAR(2, "PHP"), JAR(2, "PHP"), WEB_PHP(2, "PHP");

	private int codigo;
	private String descricao;

	private TipoEmpacotamento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoEmpacotamento getEnum(String tipo) {
		if (tipo != null) {
			for (TipoEmpacotamento ad : TipoEmpacotamento.values()) {
				if (tipo.equals(ad.getDescricao()))
					return ad;
			}
		}
		return EAR;
	}

	public static boolean contains(String tipo) {
		for (TipoEmpacotamento ad : TipoEmpacotamento.values()) {
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