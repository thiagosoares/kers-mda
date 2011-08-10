package br.com.capanema.kers.core.engine;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;

import br.com.capanema.kers.common.model.projectea.Archetype;
import br.com.capanema.kers.common.model.projectea.TemplateConfig;
import br.com.capanema.kers.common.model.projectea.TipoArchitetura;
import br.com.capanema.kers.common.model.projectea.TipoFormulario;
import br.com.capanema.kers.common.model.template.DomainSourceType;
import br.com.capanema.kers.core.facade.CalangoFacade;

/**
 * 
 * @author thiago
 *
 */
public class InterPassTest {

	@Test
	public void testGerar1Iteracao()  throws Exception  {

		TemplateConfig parametros = new TemplateConfig();
		
		parametros.setArchetype(Archetype.MAVEN_JAVA_WEB_SEAM_2_0);
		parametros.setTipoFonteProjeto(DomainSourceType.FROM_XMI);
		parametros.setNomeSistema("InterPass");
		parametros.setNomeFachada("InterPass"); //Opcional, se não informado usar o nome do projeto
		parametros.setDiagramaIteracao("Implementação");
		//parametros.setLocalDestinoProjeto("/home/thiago/Desktop/TMP/testeMDA");
		parametros.setLocalDestinoProjeto("/home/thiago/Prodepa/projetos/InterPassMPI/code/");
		parametros.setSomenteEntidades(false);
		
		//parametros.setLocalSourceXmi("/home/thiago/Workspaces/projetoEduarda/calangoMDA/calangoMDA-core/src/test/resources/xml/teste.xml");
		parametros.setLocalSourceXmi("/home/thiago/Prodepa/projetos/InterPassMPI/casacivil_mpi_xmi/casacivil_mpi_2010_04_08_v3.xmi");
		
		parametros.setPackageRoot("br.gov.pa.interPass");
		
		List<String> lista = new ArrayList<String>();
		parametros.setEntidadesStr(lista);// Pode ser informada para limitar o escopo
		parametros.setEntidadesStr(null);// Neste caso, todas (não precisava setar null)

		parametros.setTipoArquitetura(TipoArchitetura.MUIRAQUITA_FULL);
		parametros.setTipoFormularioWeb(TipoFormulario.PRODEPA_PATTERN);
		

		//Estagiario.gerar(parametros);
		CalangoFacade gerador = new CalangoFacade();
		gerador.buildAllProject(parametros);
	}
	
	//@Test
	public void testGerar2Iteracao() throws Exception  {

		TemplateConfig parametros = new TemplateConfig();
		
		parametros.setArchetype(Archetype.MAVEN_JAVA_WEB_SEAM_2_0);
		parametros.setTipoFonteProjeto(DomainSourceType.FROM_XMI);
		parametros.setNomeSistema("sivdm");
		parametros.setNomeFachada("sivdm"); //Opcional, se não informado usar o nome do projeto
		parametros.setDiagramaIteracao("2º_Iteracao_Atendimento");
		//parametros.setLocalDestinoProjeto("/home/thiago/Desktop/TMP/testeMDA");
		parametros.setLocalDestinoProjeto("/home/thiago/Prodepa/projetos/sivdm/");
		parametros.setSomenteEntidades(false);
		
		//parametros.setLocalSourceXmi("/home/thiago/Workspaces/projetoEduarda/calangoMDA/calangoMDA-core/src/test/resources/xml/teste.xml");
		parametros.setLocalSourceXmi("/home/thiago/Prodepa/projetos/sivdm/2_iteracao.xml");
		
		parametros.setPackageRoot("br.gov.pa.sejudh");
		
		List<String> lista = new ArrayList<String>();
		parametros.setEntidadesStr(lista);// Pode ser informada para limitar o escopo
		parametros.setEntidadesStr(null);// Neste caso, todas (não precisava setar null)

		parametros.setTipoArquitetura(TipoArchitetura.MUIRAQUITA_FULL);
		parametros.setTipoFormularioWeb(TipoFormulario.PRODEPA_PATTERN);
		

		//Estagiario.gerar(parametros);
		CalangoFacade gerador = new CalangoFacade();
		gerador.buildAllProject(parametros);

	}

//	@Test
	public void testGerarListaEntidades() throws Exception  {

		List<String> lista = new ArrayList<String>();
		lista.add("br.gov.pa.nucleopa.entity.Orgao");
		lista.add("br.gov.pa.nucleopa.entity.Estado");
		lista.add("br.gov.pa.nucleopa.entity.Municipio");
		lista.add("br.gov.pa.nucleopa.entity.Pessoa");
		lista.add("br.gov.pa.nucleopa.entity.PessoaFisica");
		lista.add("br.gov.pa.nucleopa.entity.PessoaJuridica");
	//	lista.add("br.gov.pa.nucleopa.entity.TipoPessoa");

		TemplateConfig parametros = new TemplateConfig();
		parametros.setNomeSistema("nuceopa");
		parametros.setNomeFachada("nuceopa");
		parametros.setLocalDestinoProjeto("E:/codigo");
		// parametros.setDestinoView("D:/ECLIPSE-IDE/projeto_eduarda/teste");
		parametros.setPackageRoot("br.gov.pa.nuceopa");

		parametros.setEntidadesStr(lista);

		// configuracoes
		parametros.setArchetype(Archetype.MAVEN_JAVA_WEB_SEAM_2_0);
    parametros.setTipoFonteProjeto(DomainSourceType.FROM_JAR);
		parametros.setTipoFormularioWeb(TipoFormulario.PRODEPA_PATTERN);
		parametros.setTipoArquitetura(TipoArchitetura.MUIRAQUITA_FULL);
		//Estagiario.gerar(parametros);

		new CalangoFacade().buildAllProject(parametros);
	}

}
