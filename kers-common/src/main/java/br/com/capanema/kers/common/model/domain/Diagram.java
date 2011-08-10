package br.com.capanema.kers.common.model.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Classe refatorada do cachorrinho para o kers
 * 
 * @author thiago
 *
 */
public class Diagram {

    private String idEA;

    List<String> elementos = new ArrayList<String>();
    
    /** Source **//*
    private String idSource;
    private String multiplicidadeSource;
    private String nomeRoleSource;
    
    // Model
    private String tipoSource; // type : Class
    private String nomeSource; // name : Aluno

    private Boolean isAgregacaoSource;
    private Boolean isNavegavelSource;

    *//** Target **//*
    private String idTarget;
    private String multiplicidadeTarget;
    private String nomeRoleTarget;

    // Model
    private String tipoTarget; // type : Class
    private String nomeTarget; // name : Aluno

    private Boolean isAgregacaoTarget;
    private Boolean isNavegavelTarget;
*/
    public Diagram() {
    }

	public String getIdEA() {
		return idEA;
	}

	public void setIdEA(String idEA) {
		this.idEA = idEA;
	}

	public List<String> getElementos() {
		return elementos;
	}

	public void setElementos(List<String> elementos) {
		this.elementos = elementos;
	}

@Override
public String toString() {
	return super.toString();
}
    
}