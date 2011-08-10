package br.com.capanema.kers.common.model.domain;

/**
 * 
 * Classe refatorada do cachorrinho para o kers
 * 
 * @author thiago
 *
 */
public class Association {

    private String idEA;
    private String direction;
    
    /** Source **/
    private String idSource;
    private String multiplicidadeSource;
    private String nomeRoleSource;
    
    // Model
    private String tipoSource; // type : Class
    private String nomeSource; // name : Aluno

    private Boolean isAgregacaoSource;
    private Boolean isNavegavelSource;

    /** Target **/
    private String idTarget;
    private String multiplicidadeTarget;
    private String nomeRoleTarget;

    // Model
    private String tipoTarget; // type : Class
    private String nomeTarget; // name : Aluno

    private Boolean isAgregacaoTarget;
    private Boolean isNavegavelTarget;

    public Association() {
    }

    public String getIdEA() {
	return idEA;
    }

    public void setIdEA(String idEA) {
	this.idEA = idEA;
    }

    public String getIdSource() {
	return idSource;
    }

    public void setIdSource(String idSource) {
	this.idSource = idSource;
    }

    public String getMultiplicidadeSource() {
	return multiplicidadeSource;
    }

    public void setMultiplicidadeSource(String multiplicidadeSource) {
	this.multiplicidadeSource = multiplicidadeSource;
    }

    public String getIdTarget() {
	return idTarget;
    }

    public void setIdTarget(String idTarget) {
	this.idTarget = idTarget;
    }

    public String getMultiplicidadeTarget() {
	return multiplicidadeTarget;
    }

    public void setMultiplicidadeTarget(String multiplicidadeTarget) {
	this.multiplicidadeTarget = multiplicidadeTarget;
    }

    public String getTipoSource() {
	return tipoSource;
    }

    public void setTipoSource(String tipoSource) {
	this.tipoSource = tipoSource;
    }

    public String getNomeSource() {
	return nomeSource;
    }

    public void setNomeSource(String nomeSource) {
	this.nomeSource = nomeSource;
    }

    public Boolean getIsAgregacaoSource() {
	return isAgregacaoSource;
    }

    public void setIsAgregacaoSource(Boolean isAgregacaoSource) {
	this.isAgregacaoSource = isAgregacaoSource;
    }

    public Boolean getIsNavegavelSource() {
	return isNavegavelSource;
    }

    public void setIsNavegavelSource(Boolean isNavegavelSource) {
	this.isNavegavelSource = isNavegavelSource;
    }

    public String getTipoTarget() {
	return tipoTarget;
    }

    public void setTipoTarget(String tipoTarget) {
	this.tipoTarget = tipoTarget;
    }

    public String getNomeTarget() {
	return nomeTarget;
    }

    public void setNomeTarget(String nomeTarget) {
	this.nomeTarget = nomeTarget;
    }

    public Boolean getIsAgregacaoTarget() {
	return isAgregacaoTarget;
    }

    public void setIsAgregacaoTarget(Boolean isAgregacaoTarget) {
	this.isAgregacaoTarget = isAgregacaoTarget;
    }

    public Boolean getIsNavegavelTarget() {
	return isNavegavelTarget;
    }

    public void setIsNavegavelTarget(Boolean isNavegavelTarget) {
	this.isNavegavelTarget = isNavegavelTarget;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getNomeRoleSource() {
        return nomeRoleSource;
    }

    public void setNomeRoleSource(String nomeRoleSource) {
        this.nomeRoleSource = nomeRoleSource;
    }

    public String getNomeRoleTarget() {
        return nomeRoleTarget;
    }

    public void setNomeRoleTarget(String nomeRoleTarget) {
        this.nomeRoleTarget = nomeRoleTarget;
    }

    @Override
    public String toString() {
        return "[Source : " + nomeSource + "][target: " + nomeTarget + "] ";
    }
    
}