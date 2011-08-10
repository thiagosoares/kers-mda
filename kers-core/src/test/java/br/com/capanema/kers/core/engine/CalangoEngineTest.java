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


public class CalangoEngineTest {

  String DIR_PROJECT = "E:\\Desenvolvimento\\TMP_TEST\\icalango";
  
	@Test
	public void testGerar() throws Exception  {

		TemplateConfig parametros = new TemplateConfig();
		
		parametros.setArchetype(Archetype.MAVEN_JAVA_WEB_SEAM_2_0);
		parametros.setTipoFonteProjeto(DomainSourceType.FROM_XMI);
		parametros.setNomeSistema("iCalango");
		parametros.setSchemaAplicacao("calango"); //Opcional, se não informado usar o nome do projeto
		parametros.setNomeFachada("calango"); //Opcional, se não informado usar o nome do projeto
		parametros.setDiagramaIteracao("domain");
		parametros.setLocalDestinoProjeto(DIR_PROJECT);
		parametros.setSomenteEntidades(false);
		
		//parametros.setLocalSourceXmi("/home/thiago/Workspaces/projetoEduarda/calangoMDA/calangoMDA-core/src/test/resources/xml/teste.xml");
		parametros.setLocalSourceXmi(DIR_PROJECT + "\\icalango.xml");
		
		parametros.setPackageRoot("br.calango");
		
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
	public void testGerarListaEntidades()  throws Exception  {

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
