package br.com.capanema.kers.common.util.string;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.apache.commons.lang.StringUtils;

import br.com.capanema.kers.common.model.template.Template;
import br.com.capanema.kers.common.model.template.TemplateFile;
import br.com.capanema.kers.common.model.template.TemplateFolder;

public class StringUtil extends StringUtils {

  
  
  private static String[] searchCharacter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", 
          "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
          "á", "é", "í", "ó", "ú", "Á", "É", "Í", "Ó", "Ú", 
          "à", "è", "ì", "ò", "ù", "À", "È", "Ì", "Ò", "Ù", 
          "ã", "õ", "Ã", "Õ", 
          "â", "ê", "î", "ô", "û", "Â", "Ê", "Î", "Ô", "Û",
          "ç", "ü", "", "", "",
          };
	
	public static String convertSizeToMB(long sizeInBytes) {
		double size = sizeInBytes / 1024;
		String m = " Kb";
		if (size > 1024) {
			size = size / 1024;
			m = " MB";
		}
		
		BigDecimal bd = new BigDecimal(size);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(bd.doubleValue()) + m;
	}
	
	/**
	 * Scape special characters to use String in regular expressions
	 */
	public static String scapeRegexp(String aRegexFragment){
		final StringBuilder result = new StringBuilder();
	
		final StringCharacterIterator iterator = new StringCharacterIterator(aRegexFragment);
		char character =  iterator.current();
		while (character != CharacterIterator.DONE ){

			if (character == '.') {
				result.append("\\.");
			} else if (character == '\\') {
				result.append("\\\\");
			} else if (character == '?') {
				result.append("\\?");
			} else if (character == '*') {
				result.append("\\*");
			} else if (character == '+') {
				result.append("\\+");
			} else if (character == '&') {
				result.append("\\&");
			} else if (character == ':') {
				result.append("\\:");
			} else if (character == '{') {
				result.append("\\{");
			} else if (character == '}') {
				result.append("\\}");
			} else if (character == '[') {
				result.append("\\[");
			} else if (character == ']') {
				result.append("\\]");
			} else if (character == '(') {
				result.append("\\(");
			} else if (character == ')') {
				result.append("\\)");
			} else if (character == '^') {
				result.append("\\^");
			} else if (character == '$') {
				result.append("\\$");
			} else {
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}
	
	public static String toVariavelFormat(String srt) {
	    return srt.substring(0, 1).toLowerCase() + srt.substring(1);
	  }
	  
	  public static String toClasseNameFormat(String srt) {
	    return srt.substring(0, 1).toUpperCase() + srt.substring(1);
	  }
	  
	  
	  public static String toTableSintaxFormt(String str) {
		   if(str != null) {
		    
		    String[] replece = {"_a", "_b", "_c", "_d", "_e", "_f", "_g", "_h", "_i", "_j", "_k", "_l", "_m",
		    					"_n", "_o", "_p", "_q", "_r", "_s", "_t", "_u", "_v", "_w", "_x", "_y", "_z",
		    					"a", "e", "i", "o", "u", "_a", "_e", "_i", "_o", "_u",
		    					"a", "e", "i", "o", "u", "_a", "_e", "_i", "_o", "_u",
		    					"a", "o", "a", "o",
		    					"a", "e", "i", "o", "u", "_a", "_e", "_i", "_o", "_u",
		    					"c", "u", "_", "_", "_"
		    	          };
		   
		    
		    
	  	  String strTmp = StringUtils.replaceEach(str, searchCharacter, replece);
	  	  strTmp = StringUtils.replace(strTmp, " _", "");
	  	  if(strTmp.substring(0, 1).equals("_")) {
	  	    strTmp = strTmp.substring(1);
	  	  }
	  	  strTmp = StringUtils.replace(strTmp, " _", "");
	      return strTmp;
	   } else {
	     return null;
	   }
	  }
	  
	  public static String toLabelSintaxFormt(String str) {
		   
	   
		          
	    String[] replece = {" a", " b", " c", " d", " e", " f", " g", " h", " i", " j", " k", " l", " m",
              " n", " o", " p", " q", " r", " s", " t", " u", " v", " w", " x", " y", " z",
              " á", " é", " í", " ó", " ú", 
              " à", " è", " ì", " ò", " ù", 
              " ã", " õ", 
              " â", " ê", " î", " ô", " û",
	            };
		   
		    String strTmp = StringUtils.trim(StringUtils.replaceEach(str, searchCharacter, replece));
		    //System.out.println(StringUtils.capitaliseAllWords(strTmp));
		    return StringUtils.capitalise(strTmp);

	}
	  
	/*
	 * Paths  
	 */
	  
  public static String toValidLocalPath(String str) {
      
      String[] search = {"\\", "/"};
            
      String[] replece = {File.separator, File.separator};
     
      String strTmp = StringUtils.replaceEach(str, search, replece);

      return strTmp;
	 }

  public static void addFolder(Template template, File folder) throws IOException {

    String path = folder.getPath().replace(template.getRealPath(), "");
    String pathDest = formatPathTemplate(template, folder.getPath());

    if (folder.isFile()) {
      path = path.substring(0, path.lastIndexOf(File.separator));
      pathDest = pathDest.substring(0, pathDest.lastIndexOf(File.separator));
    }

    TemplateFolder tplFolder = new TemplateFolder(path, pathDest);

    if (!template.getTemplateFolderes().contains(tplFolder)) {
      template.getTemplateFolderes().add(tplFolder);
    }

  }

  public static void addFileToCopy(Template template, File folder) throws IOException {

    String path = folder.getPath().replace(template.getRealPath(), "");
    String pathDest = formatPathTemplate(template, folder.getPath());

    TemplateFile tplFile = new TemplateFile(path, pathDest);

    addFolder(template, folder);

    if (!template.getFilesToCopy().contains(tplFile)) {
      template.getFilesToCopy().add(tplFile);
    }
  }

  public static void addTemplateFile(Template template, File folder) throws IOException {

    String path = folder.getPath().replace(template.getRealPath(), "");
    String pathDest = formatPathTemplate(template, folder.getPath());

    addFolder(template, folder);

    TemplateFile tplFile = new TemplateFile(path, pathDest);

    if (!template.getTemplateFiles().contains(tplFile)) {
      template.getTemplateFiles().add(tplFile);
    }
  }

  public static String formatPathTemplate(Template template, String folder) {
    
    String path = folder.replace(template.getRealPath(), "");
    
    if (!path.startsWith("/")) {
      path = "/" + path;
    }
    
    path = path.replace("/"+template.getRootFolder()+"/", "/" + "${data.config.ArtifactId}" + "/");
    
    return path;
  }
 
}
