package br.com.capanema.kers.core.factory.pages.update;

import java.io.IOException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;

public class UpdatePageBuilder {

  public static void build(EAProjectConfig projeto) throws IOException {
    
    switch (projeto.getTipoArquitetura()) {
    case CALANGO:
      GeradorAlteracaoFormCalango.build(projeto);
      break;
    case MUIRAQUITA_FULL:
      GeradorAlteracaoFormMuiraquita.build(projeto);
      break;

    case MUIRAQUITA_LIGTH:
      GeradorAlteracaoFormMuiraquita.build(projeto);
      break;
    }
  }
}
