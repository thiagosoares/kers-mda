package br.com.capanema.kers.core.factory.source.dto;

import java.io.IOException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;

public class DtoBuilder {

  public static void build(EAProjectConfig projeto) throws IOException {
    
    switch (projeto.getTipoArquitetura()) {
    case CALANGO:
      //GeradorDtoMuiraquitaFull.build(projeto);
      break;
    case MUIRAQUITA_FULL:
      GeradorDtoMuiraquitaFull.build(projeto);
      break;

    case MUIRAQUITA_LIGTH:
      //GeradorDtoMuiraquitaFull.build(projeto);
      break;
    }
  }
}
