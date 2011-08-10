package br.com.capanema.kers.core.factory.resources.web;

import java.io.IOException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorManifest extends CalangoFactory {

  public static void gerarArquivoSeam20(EAProjectConfig projeto) throws IOException {

    System.out.print("\t\tGerando arquivo MANIFEST.MF ");

    String caminhoPaginas = projeto.getDestinoView() + "/META-INF/";
    StringBuffer srtBuffer = new StringBuffer();

    srtBuffer.append("Manifest-Version: 1.0 \n Class-Path:");

    FileManager.createFife("MANIFEST.MF", caminhoPaginas, srtBuffer.toString());
  }

}
