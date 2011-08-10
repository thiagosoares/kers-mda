package br.com.capanema.kers.core.factory.source.facade;

import java.io.IOException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;

public class FacadeBuilder {

  public static void build(EAProjectConfig projeto) throws IOException {
    
    switch (projeto.getTipoArquitetura()) {
    case CALANGO:
      GeradorFacadeCalango.build(projeto);
      break;
    case MUIRAQUITA_FULL:
      GeradorFacadeMuiraquita.build(projeto);
      break;

    case MUIRAQUITA_LIGTH:
      GeradorFacadeMuiraquita.build(projeto);
      break;
    }
  }
}
