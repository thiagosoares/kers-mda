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
public class Entity {

    // EA
    private String idEa;
    private List<Attribute> atributos = new ArrayList<Attribute>();

    private List<Association> associacoes = new ArrayList<Association>();

    // Projeto
    //private String importPath;
    private String idEAPackage;
    private String packageClasse;
    
    private String nomeEntidade;
    private String nomeVarEntidade;

    private String nomeDto;   
    private String nomeVarDto;

    private String nomeDao;
    private String nomeVarDao;

    private String nomeAction;
    private String nomeVarAction;
    private String nomeSeamAction;

    private Boolean isSuperTipo;
    private String nomeSuperTipo;

    private Boolean isEnum;
    
    private Boolean contemId;
    
    // *********** Configuracoes de implementacao **************//
    private boolean pageLista_sem_resultados;

    public Entity() {
    }

    /** Acessores **/
    public String getNomeEntidade() {
	return nomeEntidade;
    }

    public void setNomeEntidade(String nomeEntidade) {
	this.nomeEntidade = nomeEntidade;
    }

    public String getPackageClasse() {
	return packageClasse;
    }

    public void setPackageClasse(String packageClasse) {
	this.packageClasse = packageClasse;
    }

    public String getNomeDto() {
	return nomeDto;
    }

    public void setNomeDto(String nomeDto) {
	this.nomeDto = nomeDto;
    }

    public String getNomeVarEntidade() {
	return nomeVarEntidade;
    }

    public void setNomeVarEntidade(String nomeVarEntidade) {
	this.nomeVarEntidade = nomeVarEntidade;
    }

    public String getNomeVarDto() {
	return nomeVarDto;
    }

    public void setNomeVarDto(String nomeVarDto) {
	this.nomeVarDto = nomeVarDto;
    }

    public String getNomeDao() {
	return nomeDao;
    }

    public void setNomeDao(String nomeDao) {
	this.nomeDao = nomeDao;
    }

    public String getNomeVarDao() {
	return nomeVarDao;
    }

    public void setNomeVarDao(String nomeVarDao) {
	this.nomeVarDao = nomeVarDao;
    }

    public String getNomeAction() {
	return nomeAction;
    }

    public void setNomeSeam(String nomeAction) {
	this.nomeAction = nomeAction;
    }

    public String getNomeVarAction() {
	return nomeVarAction;
    }

    public void setNomeVarAction(String nomeVarAction) {
	this.nomeVarAction = nomeVarAction;
    }

    public boolean isPageLista_sem_resultados() {
	return pageLista_sem_resultados;
    }

    public void setPageLista_sem_resultados(boolean pageLista_sem_resultados) {
	this.pageLista_sem_resultados = pageLista_sem_resultados;
    }

   /* public String getImportPath() {
	return importPath;
    }

    public void setImportPath(String importPath) {
	this.importPath = importPath;
    }*/

    public String getIdEa() {
	return idEa;
    }

    public void setIdEa(String idEa) {
	this.idEa = idEa;
    }

    public List<Attribute> getAtributos() {
	return atributos;
    }

    public void setAtributos(List<Attribute> atributos) {
	this.atributos = atributos;
    }

    public String getNomeSeamAction() {
	return nomeSeamAction;
    }

    public void setNomeSeamAction(String nomeSeamAction) {
	this.nomeSeamAction = nomeSeamAction;
    }

    public List<Association> getAssociacoes() {
	return associacoes;
    }

    public void setAssociacoes(List<Association> associacoes) {
	this.associacoes = associacoes;
    }

    
    public String getIdEAPackage() {
        return idEAPackage;
    }

    public void setIdEAPackage(String idEAPackage) {
        this.idEAPackage = idEAPackage;
    }

    public Boolean getIsSuperTipo() {
        return isSuperTipo;
    }

    public void setIsSuperTipo(Boolean isSubtipo) {
        this.isSuperTipo = isSubtipo;
    }

    public String getNomeSuperTipo() {
        return nomeSuperTipo;
    }

    public void setNomeSuperTipo(String nomeSuperTipo) {
        this.nomeSuperTipo = nomeSuperTipo;
    }

    public Boolean getIsEnum() {
        return isEnum;
    }

    public void setIsEnum(Boolean isEnum) {
        this.isEnum = isEnum;
    }

    public Boolean getContemId() {
		return contemId;
	}

	public void setContemId(Boolean contemId) {
		this.contemId = contemId;
	}

	public void setNomeAction(String nomeAction) {
		this.nomeAction = nomeAction;
	}

	@Override
    public String toString() {
        return "Nome: " + nomeEntidade;
    }
}
