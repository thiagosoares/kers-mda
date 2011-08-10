package br.com.capanema.kers.core.factory.source.entity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import br.com.capanema.kers.common.model.projectea.EAProjectConfig;

public class EntityBuilder {

  public static void build(EAProjectConfig projeto) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    
    switch (projeto.getTipoArquitetura()) {
    case CALANGO:
      //GeradorEntityCalango.build(projeto);
      GeradorEntityMuiraquita.build(projeto);
      break;
    case MUIRAQUITA_FULL:
      GeradorEntityMuiraquita.build(projeto);
      break;

    case MUIRAQUITA_LIGTH:
      GeradorEntityMuiraquita.build(projeto);
      //GeradorEntityMuiraquitaLigth.build(projeto);
      break;
    }
  }
}
