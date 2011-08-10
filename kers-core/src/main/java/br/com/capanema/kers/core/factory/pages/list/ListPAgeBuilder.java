package br.com.capanema.kers.core.factory.pages.list;

import java.io.IOException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;

public class ListPAgeBuilder {

  public static void build(EAProjectConfig projeto) throws IOException {
    
    switch (projeto.getTipoArquitetura()) {
    case CALANGO:
      GeradorBuscasCalango.build(projeto);
      break;
    case MUIRAQUITA_FULL:
      GeradorBuscasMuiraquita.build(projeto);
      break;

    case MUIRAQUITA_LIGTH:
      GeradorBuscasMuiraquita.build(projeto);
      break;
    }
  }
}
