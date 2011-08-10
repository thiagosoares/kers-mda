package br.com.capanema.kers.core.factory.source.localizacoesCdu;

import java.io.IOException;

import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.templates.CopyrightTpl;
import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorLocalizacoesCDU extends CalangoFactory {

  public static void build(EAProjectConfig projeto) throws IOException {

    System.out.println("Gerando LocalizacoesCDU .......");
    StringBuffer srtBuffer = new StringBuffer();

    String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "servico";
    
    srtBuffer.append(CopyrightTpl.getConteudo());
    srtBuffer.append("package " + projeto.getPackageTree() + "servico; \n\n");
    srtBuffer.append("/** \n");
    srtBuffer.append("* A classe de localiza��o tem por objetivo centralizar a codifica��o dos casos de uso. \n");
    srtBuffer.append("* \n");
    srtBuffer.append("* Localiza��oLocaliza��o\n");
    srtBuffer.append("* 001 01 001\n * ^^^ ^^ ^^^ \n * |  |   | \n * |  |   +----> N�mero do caso de uso \n * |  +--------> N�mero do m�dulo \n * +-----------> N�mero do sistema");
    srtBuffer.append("* \n");
    srtBuffer.append("*/ \n");
    
    srtBuffer.append("\n \n");

    srtBuffer.append("public final class LocalizacaoCDU {");

    srtBuffer.append("\n \n \t");

    srtBuffer.append("public static final String CDU_GENERICO = \"0001000100000\"; \n\t");
    srtBuffer.append("public static final String CDU_SEGURANCA = \"0001000100000\"; \n\t");

    srtBuffer.append("\n \t");
    srtBuffer.append("/** Localizacoes de casos de uso de determinada itera��o **/ \n\t");
    srtBuffer.append("\n \t");
    
    // Gerar Metodos
    for (Entity entidade : projeto.getEntidades()) {
	if (!entidade.getIsEnum()) {
	    
          srtBuffer.append("/** "+ entidade.getNomeEntidade() +" **/ \n \t");
          srtBuffer.append("public static final String CDU_MANTER_"+ entidade.getNomeEntidade().toUpperCase()+ " = \"0001000100000\"; \n\t");
          srtBuffer.append("public static final String CDU_MANTER_"+ entidade.getNomeEntidade().toUpperCase()+ "_CADASTRO = \"0001000100000\"; \n\t");
          srtBuffer.append("public static final String CDU_MANTER_"+ entidade.getNomeEntidade().toUpperCase()+ "_ALTERACAO = \"0001000100000\"; \n\t");
          srtBuffer.append("public static final String CDU_MANTER_"+ entidade.getNomeEntidade().toUpperCase()+ "_EXCLUSAO = \"0001000100000\"; \n\t");
    
          srtBuffer.append("\n\n\t");
	}
      
    }

    srtBuffer.append("\n ");

    srtBuffer.append("}");

    // FileManager.gerarDiretorio(caminho);
    FileManager.createFife("LocalizacaoCDU.java", caminho, srtBuffer.toString());

  }

}