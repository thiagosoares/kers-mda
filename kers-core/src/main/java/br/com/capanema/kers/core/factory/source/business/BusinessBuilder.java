package br.com.capanema.kers.core.factory.source.business;

import java.io.IOException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;

public class BusinessBuilder {

  public static void build(EAProjectConfig projeto) throws IOException {
    
    switch (projeto.getTipoArquitetura()) {
    case CALANGO:
      GeradorBusinessCalango.build(projeto);
      break;
    case MUIRAQUITA_FULL:
      GeradorBusinessMuiraquitaFull.build(projeto);
      break;

    case MUIRAQUITA_LIGTH:
      GeradorBusinessMuiraquitaLigth.build(projeto);
      break;
    }
  }
}
