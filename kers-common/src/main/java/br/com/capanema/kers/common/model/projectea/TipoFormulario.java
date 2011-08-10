package br.com.capanema.kers.common.model.projectea;


public enum TipoFormulario {

	SEAM_PATTERN(1, "String"),
	PRODEPA_PATTERN(2, "Integer"),
	CALANGO_PATTERN(100, "Outro");

	private int tratamento;
	private String tipo;
	

	private TipoFormulario(int tratamento, String tipo) {
		this.tratamento = tratamento;
		this.tipo = tipo;
	}

	public int getTratamento() {
		return tratamento;
	}

	public String getTipo() {
		return tipo;
	}

	public static TipoFormulario getEnum(String tipo) {
		if (tipo != null) {
			for (TipoFormulario ad : TipoFormulario.values()) {
				if (tipo.equals(ad.getTipo()))
					return ad;
			}
		}
		return CALANGO_PATTERN;
	}

	public static boolean contains(String tipo) {
		for (TipoFormulario ad : TipoFormulario.values()) {
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