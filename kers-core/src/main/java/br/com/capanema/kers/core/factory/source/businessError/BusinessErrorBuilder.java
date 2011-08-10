package br.com.capanema.kers.core.factory.source.businessError;

import java.io.IOException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;

public class BusinessErrorBuilder {

  public static void build(EAProjectConfig projeto) throws IOException {
    
    switch (projeto.getTipoArquitetura()) {
    case CALANGO:
      GeradorBusinessErrorMuiraquita.build(projeto);
      break;
    case MUIRAQUITA_FULL:
      GeradorBusinessErrorMuiraquita.build(projeto);
      break;

    case MUIRAQUITA_LIGTH:
      GeradorBusinessErrorMuiraquita.build(projeto);
      break;
    }
  }
}
