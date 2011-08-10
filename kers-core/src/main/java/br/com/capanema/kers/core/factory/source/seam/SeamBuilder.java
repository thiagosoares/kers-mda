package br.com.capanema.kers.core.factory.source.seam;

import java.io.IOException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;

public class SeamBuilder {

  public static void build(EAProjectConfig projeto) throws IOException {
    
    switch (projeto.getTipoArquitetura()) {
    case CALANGO:
      GeradorSeamCalango.build(projeto);
      break;
    case MUIRAQUITA_FULL:
      GeradorSeamMuiraquitaFull.build(projeto);
      break;

    case MUIRAQUITA_LIGTH:
      GeradorSeamMuiraquitaLigth.build(projeto);
      break;
    }
  }
}
