package br.com.capanema.kers.core.factory.source.dao;

import java.io.IOException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;

public class DaoBuilder {

  public static void build(EAProjectConfig projeto) throws IOException {
    
    switch (projeto.getTipoArquitetura()) {
    case CALANGO:
      GeradorDaoCalango.build(projeto);
      break;
    case MUIRAQUITA_FULL:
      GeradorDaoMuiraquita.build(projeto);
      break;

    case MUIRAQUITA_LIGTH:
      GeradorDaoMuiraquita.build(projeto);
      break;
    }
  }
}
