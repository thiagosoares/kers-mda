package br.com.capanema.kers.uc.util.reflection;


public final class JavassistReflection {

  /*public static CtClass getClass(String clazz) throws Exception {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    ClassPool pool = ClassPool.getDefault();
    pool.insertClassPath(new LoaderClassPath(cl));
    CtClass cc = null;
    try {
      cc = pool.get(clazz);
    } catch (NotFoundException e) {
      throw new NotFoundException("A classe " + clazz + " n�o foi encontrada para ser introspeccionada", e);
    }
    return cc;
  }
  
  public static CtMethod getDeclaredMethod(String clazz, String metodo) throws Exception {
    CtMethod cm = null;
    try {
      cm = getClass(clazz).getDeclaredMethod(metodo);
    } catch (NotFoundException e) {
      throw new NotFoundException("O m�todo " + metodo + " n�o existe na classe " + clazz, e);
    }
    return cm;
  }*/
  
}
