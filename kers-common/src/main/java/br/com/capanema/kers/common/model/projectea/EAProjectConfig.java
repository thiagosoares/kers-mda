package br.com.capanema.kers.common.model.projectea;

import java.util.List;

import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.util.string.StringUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("projectConfig")
public class EAProjectConfig {

	private String nomeSistema;
	private String nomeFachada;
	private String schemaAplicacao;
	
	private String destinoSource;
	private String destinoView;
	private String packageTree; // TODO Fazer
	private String localXmi;
	private String diagramaIteracao;

	//Caminhos
	private String pathRootGerador;
	private String pathProjeto;
	private String pathModuloEAR;
	private String pathModuloEJB;
	private String pathModuloWEB;

	private List<Entity> entidades;

	// Configuracoes de implementacao
	private Archetype archetype;
	private TipoFormulario tipoFormulario;
	private TipoArchitetura tipoArquitetura;
	
	private Boolean contemAtivo;
	private Boolean somenteEntidades;
	

	public EAProjectConfig(TemplateConfig parametros) {
	  super();
		this.nomeSistema = StringUtil.toClasseNameFormat(parametros.getNomeSistema());
		this.nomeFachada = StringUtil.toClasseNameFormat(parametros.getNomeFachada() != null ? parametros.getNomeFachada() 
																							 : parametros.getNomeSistema());
		
		this.schemaAplicacao = StringUtil.toClasseNameFormat(parametros.getSchemaAplicacao() != null ? parametros.getSchemaAplicacao() 
                                               : parametros.getNomeSistema());
		
		
		String path = System.getProperties().getProperty("java.class.path", null);        
		//this.pathRootGerador = path.substring(0, path.indexOf("\\calangoMDA") + 13);
		
		this.pathRootGerador = "./";

		this.pathProjeto = parametros.getLocalDestinoProjeto().substring(parametros.getLocalDestinoProjeto().length()).equals("/") 
            ? parametros.getLocalDestinoProjeto() + nomeSistema.toLowerCase()  
            : parametros.getLocalDestinoProjeto() + "/" + nomeSistema.toLowerCase();
		
		this.pathModuloEAR = parametros.getLocalDestinoProjeto().substring(parametros.getLocalDestinoProjeto().length()).equals("/") 
				? parametros.getLocalDestinoProjeto()+ nomeSistema.toLowerCase() + "/" + nomeSistema.toLowerCase() + "-ear/" 
			    : parametros.getLocalDestinoProjeto() + "/"	+ nomeSistema.toLowerCase() + "/" + nomeSistema.toLowerCase() + "-ear/";
		
		this.pathModuloEJB = parametros.getLocalDestinoProjeto().substring(parametros.getLocalDestinoProjeto().length()).equals("/") 
				? parametros.getLocalDestinoProjeto()+ nomeSistema.toLowerCase() + "/" + nomeSistema.toLowerCase() + "-ejb/" 
			    : parametros.getLocalDestinoProjeto() + "/"	+ nomeSistema.toLowerCase() + "/" + nomeSistema.toLowerCase() + "-ejb/";
		
		this.pathModuloWEB = parametros.getLocalDestinoProjeto().substring(parametros.getLocalDestinoProjeto().length()).equals("/") 
				? parametros.getLocalDestinoProjeto()+ nomeSistema.toLowerCase() + "/" + nomeSistema.toLowerCase() + "-web/" 
			    : parametros.getLocalDestinoProjeto() + "/"	+ nomeSistema.toLowerCase() + "/" + nomeSistema.toLowerCase() + "-web/";
				
		
		this.destinoSource = this.pathModuloEJB + "src/main/java/"; 

		this.destinoView   = this.pathModuloWEB + "src/main/webapp/";

		this.packageTree = parametros.getPackageRoot().substring(parametros.getPackageRoot().length()).equals(".") 
		        ? parametros.getPackageRoot() 
		        : parametros.getPackageRoot() + ".";

		this.tipoArquitetura = parametros.getTipoArquitetura();
		this.localXmi        = parametros.getLocalSourceXmi();
		this.tipoFormulario  = parametros.getTipoFormularioWeb();
		this.archetype       = parametros.getArchetype();
		this.somenteEntidades = parametros.getSomenteEntidades();
		
	}

	public String getNomeSistema() {
		return nomeSistema;
	}

	public void setNomeSistema(String nomeSistema) {
		this.nomeSistema = nomeSistema;
	}

	public String getNomeFachada() {
		return nomeFachada;
	}

	public void setNomeFachada(String nomeFachada) {
		this.nomeFachada = nomeFachada;
	}

	public String getDestinoSource() {
		return destinoSource;
	}

	public void setDestinoSource(String destinoSource) {
		this.destinoSource = destinoSource;
	}

	public String getDestinoView() {
		return destinoView;
	}

	public void setDestinoView(String destinoView) {
		this.destinoView = destinoView;
	}

	public TipoArchitetura getTipoArquitetura() {
		return tipoArquitetura;
	}

	public void setTipoArquitetura(TipoArchitetura tipoArquitetura) {
		this.tipoArquitetura = tipoArquitetura;
	}

	public String getLocalXmi() {
		return localXmi;
	}

	public void setLocalXmi(String localXmi) {
		this.localXmi = localXmi;
	}

	public List<Entity> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<Entity> entidades) {
		this.entidades = entidades;
	}

	public String getPackageTree() {
		return packageTree;
	}

	public void setPackageTree(String packageTree) {
		this.packageTree = packageTree;
	}

	public TipoFormulario getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormulario tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	public Boolean getContemAtivo() {
		return contemAtivo;
	}

	public void setContemAtivo(Boolean contemAtivo) {
		this.contemAtivo = contemAtivo;
	}

	public String getDiagramaIteracao() {
		return diagramaIteracao;
	}

	public void setDiagramaIteracao(String diagramaIteracao) {
		this.diagramaIteracao = diagramaIteracao;
	}

	public Archetype getArchetype() {
		return archetype;
	}

	public void setArchetype(Archetype archetype) {
		this.archetype = archetype;
	}

	public String getPathModuloEAR() {
		return pathModuloEAR;
	}

	public void setPathModuloEAR(String pathModuloEAR) {
		this.pathModuloEAR = pathModuloEAR;
	}

	public String getPathModuloEJB() {
		return pathModuloEJB;
	}

	public void setPathModuloEJB(String pathModuloEJB) {
		this.pathModuloEJB = pathModuloEJB;
	}

	public String getPathModuloWEB() {
		return pathModuloWEB;
	}

	public void setPathModuloWEB(String pathModuloWEB) {
		this.pathModuloWEB = pathModuloWEB;
	}

  public Boolean getSomenteEntidades() {
    return somenteEntidades;
  }

  public void setSomenteEntidades(Boolean somenteEntidades) {
    this.somenteEntidades = somenteEntidades;
  }

  public String getPathRootGerador() {
    return pathRootGerador;
  }

  public void setPathRootGerador(String pathRootGerador) {
    this.pathRootGerador = pathRootGerador;
  }

  public String getPathProjeto() {
    return pathProjeto;
  }

  public void setPathProjeto(String pathProjeto) {
    this.pathProjeto = pathProjeto;
  }

  public String getSchemaAplicacao() {
    return schemaAplicacao;
  }

  public void setSchemaAplicacao(String schemaAplicacao) {
    this.schemaAplicacao = schemaAplicacao;
  }

 
  
}