package br.com.capanema.kers.common.model.template;

/**
 * 
 * Classe refatorada do cachorrinho para o kers
 * 
 * @author thiago
 *
 */
public enum DomainSourceType {

	FROM_XMI(1, "XMI"), 
	FROM_JAR(2, "Entidades"),
	FROM_INTERFACE(2, "Entidades");

	private int codigo;
	private String descricao;

	private DomainSourceType(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static DomainSourceType getEnum(String tipo) {
		if (tipo != null) {
			for (DomainSourceType ad : DomainSourceType.values()) {
				if (tipo.equals(ad.getDescricao()))
					return ad;
			}
		}
		return FROM_JAR;
	}

	public static boolean contains(String tipo) {
		for (DomainSourceType ad : DomainSourceType.values()) {
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