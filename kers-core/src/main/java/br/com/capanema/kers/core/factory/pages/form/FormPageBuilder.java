package br.com.capanema.kers.core.factory.pages.form;

import java.io.IOException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;

public class FormPageBuilder {

  public static void build(EAProjectConfig projeto) throws IOException {
    
    switch (projeto.getTipoArquitetura()) {
    case CALANGO:
      GeradorCadastroFormCalango.build(projeto);
      break;
    case MUIRAQUITA_FULL:
      GeradorCadastroFormMuiraquita.build(projeto);
      break;

    case MUIRAQUITA_LIGTH:
      GeradorCadastroFormMuiraquita.build(projeto);
      break;
    }
  }
}
