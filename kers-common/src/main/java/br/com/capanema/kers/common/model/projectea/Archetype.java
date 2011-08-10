package br.com.capanema.kers.common.model.projectea;


public enum Archetype {

	MAVEN_JAVA_WEB_SEAM_2_0(1, "String"),
	MAVEN_JAVA_WEB_SEAM_2_1(1, "String"),
	MAVEN_JAVA_WEB_WEBBEAMS_1_0(1, "String"),
	MAVEN_JAVA_SWING(2, "Integer"),
	MAVEN_JAVA_JAR(100, "Outro"),
	PHP(1, "PHP");

	private int codigo;
	private String descricao;
	
	private Archetype(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return this.codigo + " " + this.descricao;
	}

}