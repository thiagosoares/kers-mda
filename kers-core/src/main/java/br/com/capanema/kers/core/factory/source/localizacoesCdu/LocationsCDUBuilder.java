package br.com.capanema.kers.core.factory.source.localizacoesCdu;

import java.io.IOException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;

public class LocationsCDUBuilder {

  public static void build(EAProjectConfig projeto) throws IOException {
    
    switch (projeto.getTipoArquitetura()) {
    case CALANGO:
      GeradorLocalizacoesCDU.build(projeto);
      break;
    case MUIRAQUITA_FULL:
      GeradorLocalizacoesCDU.build(projeto);
      break;

    case MUIRAQUITA_LIGTH:
      GeradorLocalizacoesCDU.build(projeto);
      break;
    }
  }
}
