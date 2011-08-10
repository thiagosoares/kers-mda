package br.com.capanema.kers.common.model.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import br.com.capanema.kers.common.model.projectea.Archetype;
import br.com.capanema.kers.common.model.projectea.TemplateConfig;
import br.com.capanema.kers.common.model.projectea.TipoArchitetura;
import br.com.capanema.kers.common.model.projectea.TipoFormulario;
import br.com.capanema.kers.common.model.template.DomainSourceType;

public class TemplateConfigTest {

  @Test
  public void templateConfigToXml() {
    
    
    //XStream x = XmlManager.getXStream(XmlType.PROJECT_CONFIG);
    //System.out.println(x.toXML(getParam()));
    
  }
  
  

  
  public TemplateConfig getParam()  {

    TemplateConfig parametros = new TemplateConfig();
    
    parametros.setArchetype(Archetype.MAVEN_JAVA_WEB_SEAM_2_0);
    parametros.setTipoFonteProjeto(DomainSourceType.FROM_XMI);
    parametros.setNomeSistema("Kers");
    parametros.setSchemaAplicacao("calango"); //Opcional, se não informado usar o nome do projeto
    parametros.setNomeFachada("calango"); //Opcional, se não informado usar o nome do projeto
    parametros.setDiagramaIteracao("domain");
    parametros.setLocalDestinoProjeto("E:\\Desenvolvimento\\TMP_TEST\\icalango");
    parametros.setSomenteEntidades(false);
    
    //parametros.setLocalSourceXmi("/home/thiago/Workspaces/projetoEduarda/calangoMDA/calangoMDA-core/src/test/resources/xml/teste.xml");
    parametros.setLocalSourceXmi("E:\\Desenvolvimento\\TMP_TEST\\icalango\\icalango.xml");
    
    parametros.setPackageRoot("br.calango");
    
    List<String> lista = new ArrayList<String>();
    parametros.setEntidadesStr(lista);// Pode ser informada para limitar o escopo
    parametros.setEntidadesStr(null);// Neste caso, todas (não precisava setar null)

    parametros.setTipoArquitetura(TipoArchitetura.MUIRAQUITA_FULL);
    parametros.setTipoFormularioWeb(TipoFormulario.PRODEPA_PATTERN);
    
    
    LinkedHashMap<String, List> tech = new LinkedHashMap<String, List>();
    lista.add("112121");
    tech.put("111", lista);
    
    parametros.setTech(tech);
    
    Map<String, List> techLayout = new HashMap<String, List>();
    techLayout.put("dddd", lista);
    
    parametros.setTechLayout(techLayout);
    
    
    return parametros;
  }
  
}
