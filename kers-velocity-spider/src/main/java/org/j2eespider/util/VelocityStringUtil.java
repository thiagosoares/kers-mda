package org.j2eespider.util;

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
    	data = data.replaceAll("[�����]","A");  
    	data = data.replaceAll("[�����]","a");  
    	data = data.replaceAll("[����]","E");  
    	data = data.replaceAll("[����]","e");  
    	data = data.replaceAll("����","I");  
    	data = data.replaceAll("����","i");  
    	data = data.replaceAll("[�����]","O");  
    	data = data.replaceAll("[�����]","o");  
    	data = data.replaceAll("[����]","U");  
    	data = data.replaceAll("[����]","u");  
    	data = data.replaceAll("�","C");  
    	data = data.replaceAll("�","c");   
    	data = data.replaceAll("[��]","y");  
    	data = data.replaceAll("�","Y");  
    	data = data.replaceAll("�","n");  
    	data = data.replaceAll("�","N");      	
    	data = data.replaceAll("['<>\\|/\\-_]",""); 
    	
    	return capitalizeFirstLetter(data);
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
