package br.com.capanema.kers.core.factory.source.converter;

import java.io.IOException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;

public class ConverterBuilder {

  public static void build(EAProjectConfig projeto) throws IOException {
    
    switch (projeto.getTipoArquitetura()) {
    case CALANGO:
      //GeradorConverterMuiraquita.build(projeto);
      break;
    case MUIRAQUITA_FULL:
      GeradorConverterMuiraquita.build(projeto);
      break;

    case MUIRAQUITA_LIGTH:
      //GeradorConverterMuiraquita.build(projeto);
      break;
    }
  }
}
