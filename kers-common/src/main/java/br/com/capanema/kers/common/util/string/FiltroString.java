package br.com.capanema.kers.common.util.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class FiltroString {
  
  private static Pattern nonAlfaNumericsPattern;
  private static Pattern nonNumericspattern;
  private static Pattern nonValidPattern;
  private static Pattern banishedCharactersPattern;
  
  private static Pattern patternToCopy;
  
  static {
    nonNumericspattern = Pattern.compile("[^0-9]"); 
    nonAlfaNumericsPattern = Pattern.compile("[^0-9 ^a-z ^A-Z]");
    banishedCharactersPattern = Pattern.compile("[ẽẼ]");
    nonValidPattern = Pattern.compile("[^\\p{L} ^\\p{N} ^\\s ^\\' ^\\. ^\\- ^\\/]");
    
    patternToCopy = Pattern.compile("(.vm)|(templateConfig.xml)|(.xml.xml)|(properties.xml)|(.groovy)|(.ftl)|(.zip)|(jar)");
  }
  
  
  /**
   * Filtra somente caracteres e numeros.
   * @param input
   * @return
   */
  public static String apenasAlfaENumero(String input) {
    return RegexFilter.createIncludeFilter("[0-9a-z A-Z \\s ]").filter(input);
  }
  
  /**
   * Filter: [0-9a-z A-Z éúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄçÇ\\s \\' \\. \\- \\/]"
   * @param input
   * @return
   */
  public static String apenasAlfaNumeroEEspeciaisSeguros(String input) {
    return RegexFilter.createIncludeFilter("[\\p{L} \\p{N} \\s \\' \\. \\- \\/]").filter(input);
  }
  
  /**
   * Converte o input de entrada para caixa alta e filtra somente caracteres.
   * @param input
   * @return
   */
  public static String apenasAlfaENumeroCaixaAlta(String input) {
    return RegexFilter.createIncludeFilter("[A-Z 0-9\\s \\.]").filter(input.toUpperCase());
  }
	
  /**
   * Converte o input de entrada para caixa alta e filtra somente caracteres.
   * @param input
   * @return
   */
  public static String apenasAlfaCaixaAlta(String input) {
    return RegexFilter.createIncludeFilter("[A-Z\\s]").filter(input.toUpperCase());
  }

  /**
   * Converte o input de entrada para caixa baixa e filtra somente caracteres.
   * @param input
   * @return
   */
  public static String apenasAlfaCaixaBaixa(String input) {
    return RegexFilter.createIncludeFilter("[a-z\\s]").filter(input.toLowerCase());
  }
  
  /**
   * Devolve uma string com caracteres alfa, filtrando qualquer caracter
   * que não esteja em caixa alta.
   * @param input
   * @return
   */
  public static String apenasAlfaMaiusculas(String input) {
    return RegexFilter.createIncludeFilter("[A-Z\\s]").filter(input);
  }
  
  /**
   * Devolve uma string com apenas caracteres alfa, em qualquer caixa.
   * @param input
   * @return
   */
  public static String apenasAlfa(String input) {
    return RegexFilter.createIncludeFilter("[a-zA-Z\\s]").filter(input);
  }

  /**
   * Devolve uma string com caracteres alfa. Atenção: apenas filtra,
   * não faz conversão para minúsculas (caixa baixa). Para isso utilize
   * o método {@link FiltroString#apenasAlfaCaixaBaixa(String)} 
   * @see FiltroString#apenasAlfaCaixaBaixa(String)
   * @param input
   * @return
   */
  public static String apenasAlfaMinusculas(String input) {
    return RegexFilter.createIncludeFilter("[a-z\\s]").filter(input);
  }

  /**
   * Devolve uma string apenas com números, filtrando o que
   * for passado como parâmetro.
   * @param input String de entrada
   * @return String contendo dígitos de 0 a 9 
   */
  public static String apenasNumeros(String input) {
    return RegexFilter.createIncludeFilter("[0-9]").filter(input);
  }
  
  /**
   * Devolve uma string apenas com números, filtrando o que
   * for passado como parâmetro.
   * @param input String de entrada
   * @return String contendo dígitos de 0 a 9 
   */
  public static String apenasNumerosEDeterminadoresDocumentos(String input) {
    return RegexFilter.createIncludeFilter("[0-9\\.\\-\\/]").filter(input);
  }
  
  //Validacoes
 
  public static Boolean isApenasAlfaENumero(String input) {
    if (nonAlfaNumericsPattern.matcher(input).find()) {
      return false;  
    } else {
      return true;
    }
 }
  
  public static Boolean isApenasNumeros(String input) {
    if (nonNumericspattern.matcher(input).find()) {
      return false;  
    } else {
      return true;
    }
  }
  
  /**
   * if (Pattern.compile("[^0-9 ^a-z ^A-Z \\s \\. \\- \\/]").matcher(input).find()) {
   * if (Pattern.compile("[^\\p{Alnum} ^\\p{Space} ^\\s ^\\' ^\\. ^\\- ^\\/]").matcher(input).find()) {
   */
  public static Boolean isImputValueValid(String input) {
    if (nonValidPattern.matcher(input).find()
            || banishedCharactersPattern.matcher(input).find()) {
      return false;  
    } else {
      return true;
    }
  }

  /*
   * Paths 
   */
  
  public Matcher getMatcherFromPatternToCopy(String value) {
    return patternToCopy.matcher(value);   
  }
  
  public static String removeTemplateFileExtentions(String path) {
    
    path = RegexFilter.createExcludeFilter("\\.vm").filter(path);
    
    return path;
  }
  
}