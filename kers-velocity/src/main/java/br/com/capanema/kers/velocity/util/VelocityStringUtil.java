package br.com.capanema.kers.velocity.util;

import org.apache.velocity.util.StringUtils;

/**
 * Extension of http://velocity.apache.org/engine/devel/apidocs/org/apache/velocity/util/StringUtils.html
 * @author bruno.braga
 */
public class VelocityStringUtil extends StringUtils {
	
  /**
   * <p> 
   * UnCapitalize the first letter but leave the rest as they are. 
   * </p>
   *
   * <p> 
   *  For example <code>FooBar</code> becomes <code>fooBar</code>.
   * </p>
   *
   * @param data uncapitalize this
   * @return String
   */
  public static String capitalizeFirstLetter ( String data ) {
      String firstLetter = data.substring(0,1).toUpperCase();
      String restLetters = data.substring(1);
      return firstLetter + restLetters;
  }
  
    /**
     * <p> 
     * UnCapitalize the first letter but leave the rest as they are. 
     * </p>
     *
     * <p> 
     *  For example <code>FooBar</code> becomes <code>fooBar</code>.
     * </p>
     *
     * @param data uncapitalize this
     * @return String
     */
    public static String unCapitalizeFirstLetter ( String data ) {
        String firstLetter = data.substring(0,1).toLowerCase();
        String restLetters = data.substring(1);
        return firstLetter + restLetters;
    }
    
    /**
     * Normalize the String to be used as a class name.
     * @param data
     * @return
     */
    public static String normalizeClassName( String data ) {
      data = data.replaceAll("[ÂÀÁÄÃ]","A");  
      data = data.replaceAll("[âãàáä]","a");  
      data = data.replaceAll("[ÊÈÉË]","E");  
      data = data.replaceAll("[êèéë]","e");  
      data = data.replaceAll("ÎÍÌÏ","I");  
      data = data.replaceAll("îíìï","i");  
      data = data.replaceAll("[ÔÕÒÓÖ]","O");  
      data = data.replaceAll("[ôõòóö]","o");  
      data = data.replaceAll("[ÛÙÚÜ]","U");  
      data = data.replaceAll("[ûúùü]","u");  
      data = data.replaceAll("Ç","C");  
      data = data.replaceAll("ç","c");   
      data = data.replaceAll("[ýÿ]","y");  
      data = data.replaceAll("Ý","Y");  
      data = data.replaceAll("ñ","n");  
      data = data.replaceAll("Ñ","N");        
      data = data.replaceAll("['<>\\|/\\-_]",""); 
    	
    	return capitalizeFirstLetter(data);
    }
    
    //TODO Ver regras para pluralizar
    public static String pluralizeName( String data ) {
      
      data = data + "s";
      
      return data;
    }
	
    /**
     * indexOf word in text.
     * @param text
     * @param word
     * @return
     */
	public static int indexOf(String text, String word) {
		return text.indexOf(word);
	}
	
	/**
	 * Delimiter parameters for annotations.
	 * @param index
	 * @param count
	 * @return
	 */
	public static String delimiterParameters(int index, int count) {
		if (index < count) {
			return ",";
		}
		
		return "";
	}
}
