package br.com.capanema.kers.core.factory.source.service;

import java.io.IOException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;

public class ServiceBuilder {

  public static void build(EAProjectConfig projeto) throws IOException {
    
    switch (projeto.getTipoArquitetura()) {
    case CALANGO:
      GeradorServicoCalango.build(projeto);
      break;
    case MUIRAQUITA_FULL:
      GeradorServicoMuiraquitaFull.build(projeto);
      break;

    case MUIRAQUITA_LIGTH:
      GeradorServicoMuiraquitaLigth.build(projeto);
      break;
    }
  }
}
