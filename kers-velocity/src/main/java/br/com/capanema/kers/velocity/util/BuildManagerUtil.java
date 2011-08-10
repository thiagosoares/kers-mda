/*******************************************************************************
 * Copyright (c) 2006 Bruno G. Braga.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno G. Braga - initial API and implementation
 *******************************************************************************/
package br.com.capanema.kers.velocity.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;

import br.com.capanema.kers.common.configuration.Constants;
import br.com.capanema.kers.common.model.template.Crud;
import br.com.capanema.kers.common.model.template.TemplateDataGroup;
import br.com.capanema.kers.common.model.template.TemplateFile;
import br.com.capanema.kers.common.model.template.TemplateSumary;
import br.com.capanema.kers.common.util.file.FileUtil;
import br.com.capanema.kers.tempate.engine.xml.magager.TemplateRepositoryManager;
import br.com.capanema.kers.velocity.template.engine.TemplateEngine;
import br.com.capanema.kers.velocity.template.engine.VelocityTemplateEngine;
import br.com.capanema.kers.velocity.template.factory.TemplateEngineFactory;

public class BuildManagerUtil {
	private static String templatesPathCache;
	//public static final String KEY_BUILD_LAYOUT = "##buildLAYOUT##";
	//public static final String KEY_BUILD_TECH = "##buildTECH##";
	//public static final String KEY_BUILD_PACKAGE = "##buildPACKAGE##";
	//public static final String KEY_BUILD_CRUD = "##buildCRUD##";
	//public static final String KEY_BUILD_MAPPING = "##buildMAPPING##";
	
	/**
	 * Process a path, and can return a list of path, according to the variable (she will go a list of values).
	 * @param path
	 * @return
	 * @throws Exception 
	 */
  public static String[] processPathVariables(String path, TemplateDataGroup dataGroup) throws Exception {
    StringBuffer replaceResult = new StringBuffer();

    // ---SEARCH FOR stringUtils
    String regexStringUtils = "\\$\\{stringUtils.*?\\(.*?\\)\\}";

    // Compile regular expression
    Pattern patternStringUtils = Pattern.compile(regexStringUtils);

    // Create Matcher
    Matcher matcherStringUtils = patternStringUtils.matcher(path);

    // Find occurrences
    while (matcherStringUtils.find()) {
      String variable = matcherStringUtils.group();

      // Variables
      HashMap<String, Object> velocityVariables = new HashMap<String, Object>();
      velocityVariables.put("data", dataGroup);

      TemplateEngine templateEngine = VelocityTemplateEngine.getInstance();
      String vmResult = templateEngine.runInLine(variable, velocityVariables);
      matcherStringUtils.appendReplacement(replaceResult, vmResult);
    }
    
    matcherStringUtils.appendTail(replaceResult);
    path = replaceResult.toString();
    replaceResult = new StringBuffer();

    // ---SEARCH FOR VARIABLE data
    String regexVariable = "\\$\\{.*?\\}";
    Map<String, String[]> mapVariableToDuplicateResult = new HashMap<String, String[]>();

    // Compile regular expression
    Pattern patternVariable = Pattern.compile(regexVariable);

    // Create Matcher
    Matcher matcherVariable = patternVariable.matcher(path);

    // Find occurrences
    while (matcherVariable.find()) {
      String variable = matcherVariable.group();
      variable = variable.replaceAll("\\$\\{", "").replaceAll("\\}", "");
      // variable = variable.substring(2, variable.length()-1); //remove ${ }

      // replace a list of matches
      Map<String, Object> objects = new HashMap<String, Object>();
      objects.put("data.config", dataGroup.getConfig());
      objects.put("data.crud", dataGroup.getCrud());
      objects.put("data.bundle", dataGroup.getBundle());

      String[] values = BuildManagerUtil.readValue(variable, objects);
      if (values.length == 1) { // one value
        matcherVariable.appendReplacement(replaceResult, values[0]);
      } else if (values.length > 1) { // list of values
        mapVariableToDuplicateResult.put(variable, values); // guarda todas as
                                                            // v�riaveis do tipo
                                                            // LIST do path
      }
    }
    matcherVariable.appendTail(replaceResult);

    // s� � permitido uma v�riavel do tipo list no nas v�riaveis
    if (mapVariableToDuplicateResult.size() > 1) {
      throw new Exception("Template error: Alone it is permitted one variable of the kind LIST in the paths");
    }

    // return results
    // process list of variables, or return
    if (mapVariableToDuplicateResult.size() == 1) { // rules of the list of
                                                    // values for the variable
                                                    // (existe uma v�riavel do
                                                    // tipo list)
      List<String> results = new ArrayList<String>(); // grava os resultados
      for (String variable : mapVariableToDuplicateResult.keySet()) { // loop
                                                                      // nas
                                                                      // variaveis
                                                                      // do tipo
                                                                      // list
                                                                      // (que �
                                                                      // uma s�)
        String[] values = mapVariableToDuplicateResult.get(variable); // pega os
                                                                      // valores
                                                                      // v�lidos
                                                                      // para a
                                                                      // variavel
                                                                      // do tipo
                                                                      // list
                                                                      // (valores
                                                                      // que
                                                                      // devem
                                                                      // entrar
                                                                      // no
                                                                      // lugar
                                                                      // da
                                                                      // v�riavel
                                                                      // list)
        for (String value : values) {
          results.add(replaceResult.toString().replaceAll("\\$\\{" + variable + "\\}", value)); // adiciona
                                                                                                // um
                                                                                                // path
                                                                                                // novo
                                                                                                // para
                                                                                                // cada
                                                                                                // valor
                                                                                                // que
                                                                                                // tem
                                                                                                // que
                                                                                                // ser
                                                                                                // substituido
        }
      }

      // transforma o list em array de string
      String[] values = new String[results.size()];
      for (int i = 0; i < results.size(); i++) {
        values[i] = results.get(i).toString();
      }
      return values;
    } else if (mapVariableToDuplicateResult.size() == 0 && !replaceResult.toString().equals("")) { // there
                                                                                                   // is
                                                                                                   // does
                                                                                                   // not
                                                                                                   // list
                                                                                                   // of
                                                                                                   // values,
                                                                                                   // is
                                                                                                   // simple
                                                                                                   // replace
      return new String[] { replaceResult.toString() };
    }

    // n�o encontrou resultados / not matches found
    return new String[] { path };
  }
	
	/**
	 * Read attribute of ConfigSpider, util for process ${data.config.xxx} variable
	 * @param variableValue
	 * @return
	 */
/*	public static String[] readConfigSpiderValue(String variableValue, ConfigSpider configSpider) {
		Object fieldValue = null;
		
		char firstLetter = variableValue.charAt(0);
		String methodName = "get" + String.valueOf(firstLetter).toUpperCase() + variableValue.substring(1);
		
		try {
			Method method = ConfigSpider.class.getMethod(methodName, null);
			fieldValue = method.invoke(configSpider, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (fieldValue instanceof List) {
			List listValues = (List)fieldValue;
			String[] values = new String[listValues.size()];
			for (int i=0; i<listValues.size(); i++) {
				values[i] = listValues.get(i).toString();
			}
			return values;
		} else {
			return new String[] {fieldValue.toString()};
		}
	}*/
	
	/**
	 * Read attribute of CRUD, ConfigSpider, ... Example: ${data.crud.xxx}, ${data.config.xxx}, ...
	 * @param variableValue
	 * @return
	 */
	public static String[] readValue(String variable, Map<String, Object> objects) {
		try {			
			org.apache.commons.jexl.Expression e = ExpressionFactory.createExpression(variable);
			JexlContext jc = JexlHelper.createContext();
			for (String prefix : objects.keySet()) {
				jc.getVars().put(prefix, objects.get(prefix)); //value is bean
			}
			Object fieldValue = e.evaluate(jc);
			
			//calcula o tipo de retorno
			if (fieldValue instanceof String[]) {
				return (String[])fieldValue;
			} else if (fieldValue instanceof List) {
				List listValues = (List)fieldValue;
				String[] values = new String[listValues.size()];
				for (int i=0; i<listValues.size(); i++) {
					values[i] = listValues.get(i).toString();
				}
				return values;	
			} else {
				return new String[] {fieldValue.toString()};
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}	
	
	/**
	 * Give path and return File name
	 * @param path
	 * @return
	 */
	public static String getFileNameFromPath(String path) {
		if (path.indexOf("/") >= 0) {
			String[] temp = path.split("/");
			return temp[temp.length-1]; //last name = file name
		} else {
			return path;
		}
	}

	/**
	 * Get name of first variable in Path.
	 * @param path
	 * @return
	 */
	public static String getFirstVariableName(String path) {
		String regexVariable = "\\$\\{.*?\\}";
		Pattern patternVariable = Pattern.compile(regexVariable);
		Matcher matcherVariable = patternVariable.matcher(path);
		
		matcherVariable.find(); //find first variable
		String variable = matcherVariable.group();
		variable = variable.replaceAll("\\$\\{", "").replaceAll("\\}", "");
		
		return variable;
	}
	
	/**
	 * Return path of templates folder (main folder).
	 * @return
	 */
	@Deprecated
	public static String getPathFolderTemplates() {
		if (templatesPathCache == null) {
			String path = TemplateRepositoryManager.instance().getTemplatePathPreference();
			if (path != null && !path.equals("")) {
				templatesPathCache = path;
			}
		}
		
		return templatesPathCache;
	}
	
	/**
	 * If the variable ConfigPage.VARIABLE_TEMPLATES will be changed, clear cache.
	 */
	public static void clearTemplatesPathCache() {
		templatesPathCache = null;
	}
	
	
	public static void replaceAnnotationsInMethods(Crud crud) { //throws JavaModelException {
		//search class
		/*ResolvedSourceType resolvedCrudClass = ClassSearchUtil.searchExactJavaFile(crud.getCrudClass().getFullName());
		if (resolvedCrudClass != null) {
			IMethod[] methods = resolvedCrudClass.getMethods();
			
			for (IMethod imethod : methods) {
				CrudField crudField = CrudUtil.getFieldInCrud(imethod, crud.getCrudClass());
				if (crudField != null) {
					List<CrudFieldValidator> validators = crudField.getValidators();
					AnnotationUtil.replaceAnnotationsInMethod(imethod, ValidatorUtil.getValidators(), ValidatorUtil.getValidatorsSource(validators));
				}
			}
		}*/
	}
	
	/**
	 * Retorna uma lista com o nome de todos os builds que j� foram realizados no projeto.
	 * @param lastBuild
	 * @return
	 */
	public static List getListBuilds(Map lastBuild) {
		List<String> listBuilds = new ArrayList<String>();
		if (lastBuild.get(Constants.KEY_BUILD_LAYOUT) != null) {
			listBuilds.add("CONFIG");
		}
		if (lastBuild.get(Constants.KEY_BUILD_PACKAGE) != null) {
			listBuilds.add("PACKAGE");
		}
		if (lastBuild.get(Constants.KEY_BUILD_TECH) != null) {
			listBuilds.add("TECH");
		}
		if (lastBuild.get(Constants.KEY_BUILD_FOR_CRUD) != null) {
			listBuilds.add("CRUD");
		}
		
		return listBuilds;
	}
	
	/**
	 * Increment source code of filePath using template fragment.
	 * 
	 * @param filePath
	 * @param incrementPath
	 * @param incrementPattern
	 * @param dataGroup
	 */
	public static void incrementSourceCode(String filePath, String incrementPath, String incrementPattern, String firstAfter, Map<String,String> params, TemplateDataGroup dataGroup) {
		try {
			HashMap<String, Object> velocityVariables = new HashMap<String, Object>(); //map for velocity variables
			velocityVariables.put("data", dataGroup); //conjunto de v�riaveis que podem ser usados no template
			
			if (params != null && params.size() > 0) {
/*				for (String name : params.keySet()) {
					velocityVariables.put("params."+name, params.get(name));
				}*/
				velocityVariables.put("params", params);
			}
			
			//rodando velocity do incremento
			TemplateEngine templateEngine = TemplateEngineFactory.getEngine(incrementPath , true);
			String incrementSource = templateEngine.runFile(incrementPath, velocityVariables);
			
			// Create the pattern
	        Pattern pattern = Pattern.compile("[\r\n]*[\t]*"+incrementPattern, Pattern.DOTALL); //o "[\r\n]*" � para pegar as quebras de linhas que podem existir no incremento
	        Matcher matcher = pattern.matcher("");
	        
			//novo incremento
	        //aqui vai executar o pattern no pr�prio incremento... se o pattern estiver errado n�o ir� encontrar nada e vai abortar o incremento
			matcher.reset(incrementSource);
			Map<String, String[]> mapGroupIncrement = new LinkedHashMap<String, String[]>(); 
			while (matcher.find()) {
				String[] groups = new String[matcher.groupCount()];
				for (int i=0; i<matcher.groupCount(); i++) {
					groups[i] = matcher.group(i+1); 
				}
				
				String increment = matcher.group(); //new increment
				mapGroupIncrement.put(increment, groups); //map increment vs groups
			} 
		
			if (mapGroupIncrement.size() == 0) { //n�o encontrou groups no incremento (usado para as compara��es), aborta
				return;
			}
			
			//le o arquivo atual
			FileInputStream inputFilePath = new FileInputStream(filePath);
	        BufferedInputStream bufferedInput = new BufferedInputStream(inputFilePath);
	        StringBuffer filePathContent = new StringBuffer();
	        int ch=0;
	        while((ch=bufferedInput.read()) > -1){
	        	filePathContent.append((char)ch);
	        }
	        inputFilePath.close();
	        bufferedInput.close();
			
	        //procura no arquivo todo pela express�o regular
	        matcher = pattern.matcher("");
	        matcher.reset(filePathContent);
	        StringBuffer newContentFile = new StringBuffer();
	        int countMatcher = 0;
	        Map<String, String[]> mapGroupFile = new HashMap<String, String[]>(); 
	        while (matcher.find()) { //verifica cada ocorr�ncia da express�o no arquivo
        		String[] groups = new String[matcher.groupCount()]; //pega os groups "()" do matcher para fazer a compara��o com os groups do incremento que est� sendo gerado
        		for (int i=0; i<matcher.groupCount(); i++) {
        			groups[i] = matcher.group(i+1); 
        		}
        		mapGroupFile.put(matcher.group(), groups); //adiciona esse group em uma lista para ser avaliado depois
            	
        		countMatcher++; //isso vai contar onde esta o �ltimo matcher
            }
	        
	        //valida quais incrementos s�o realmente novos, comparando os groups
	        List<String> newIncrements = new LinkedList<String>();
    		for (String groupIncrement : mapGroupIncrement.keySet()) {
    			String[] groups1 = mapGroupIncrement.get(groupIncrement); //groups do incremento
    			boolean itemFound = false;
    			
    			for (String groupFile : mapGroupFile.keySet()) {
    				String[] groups2 = mapGroupFile.get(groupFile); //groups do incremento
    				
                	if (ArrayUtil.equals(groups1, groups2)) { //se no arquivo tem o c�digo que est� sendo gerado, n�o precisa adicionar esse incremnto
                		itemFound = true;
                	}
    			}
            	if (!itemFound) { //se no arquivo n�o tem o c�digo que est� sendo gerado, adiciona em um array
            		newIncrements.add(groupIncrement);
            	}
    		}
	        
    		//realiza uma quebra de linha adicional para o primeiro item do incremento, para n�o ficar na mesma linha do �ltimo matcher no arquivo
        	StringBuffer newIncrementSource = new StringBuffer();
        	int i=0;
        	for (String incremento : newIncrements) {
        		if (i==0 && incremento.indexOf("\r\n\r\n") != 0) { //s� coloca quebra de linha se precisar
        			newIncrementSource.append("\r\n");
        		}
        		
        		newIncrementSource.append(incremento);
        		i++;
        	}
    		
    		if (newIncrements.size() > 0) {  			
	    		//n�o encontrou nenhum increment no arquivo, ent�o procura pelo firstAfter para inserir o primeiro incremento
	    		if (countMatcher == 0 && firstAfter != null && !firstAfter.equals("")) {
	    	        pattern = Pattern.compile(firstAfter, Pattern.DOTALL);
	    	        matcher = pattern.matcher("");
	    	        matcher.reset(filePathContent);
	    			
	    	        //esse loop s� serviu para contar e achar o �ltimo matcher. N�o consegui fazer sem isso :(
	    	        countMatcher = 0;
	    	        while (matcher.find()) { //verifica cada ocorr�ncia da express�o
	    	        	countMatcher++;
	    	        }
	    		}
	    		
    	        //aqui vou realmente substituir os valores, j� sabendo qual � a �ltima ocorrencia da palavra no arquivo
    	        i=0;
    	        matcher.reset();
    	        while (matcher.find()) { //verifica cada ocorr�ncia da express�o
    	        	i++;
    	        	if (countMatcher == i) { //se encontrou a �ltima ocorrencia
	    	        	String matcherGroup = matcher.group();
	    	        	matcher.appendReplacement(newContentFile, matcherGroup+newIncrementSource); //substitui a ultima ocorrencia do firstIncrement	
    	        	}
    	        }
    	        matcher.appendTail(newContentFile);
    		}
	        
	        //save new file
	        if (newContentFile.length() > 0) {
	        	FileUtil.writeFile(filePath, newContentFile.toString());
	        }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * Control of recent files generated in the build.
	 * 
	 * @param lastBuild
	 * @param pathWriterFile
	 * @param finalPathGeneration
	 */
	public static void addFileToLastBuild(Map<String, Long> lastBuild, String pathWriterFile, String finalPathGeneration) {
		File fileGeneration = new File(pathWriterFile);
		if (fileGeneration.isFile()) {
			lastBuild.put(finalPathGeneration, new Date().getTime()); //update last build
		}
	}
	
	/**
	 * Replace sem considerar express�o regular.
	 * @param input
	 * @param search
	 * @param replacement
	 * @return
	 */
	public static String stringReplace(final String input, final String search, final String replacement) {
        if ( search.equals("") ) {
           throw new IllegalArgumentException("Old pattern must have content.");
        }

        final StringBuffer result = new StringBuffer();
        //startIdx and idxOld delimit various chunks of aInput; these
        //chunks always end where aOldPattern begins
        int startIdx = 0;
        int idxOld = 0;
        while ((idxOld = input.indexOf(search, startIdx)) >= 0) {
          //grab a part of aInput which does not include aOldPattern
          result.append( input.substring(startIdx, idxOld) );
          //add aNewPattern to take place of aOldPattern
          result.append( replacement );

          //reset the startIdx to just after the current match, to see
          //if there are any further matches
          startIdx = idxOld + search.length();
        }
        //the final chunk will go to the end of aInput
        result.append( input.substring(startIdx) );
        
        return result.toString();
    }

    public static Map<String, String> getPathAllTemplates() {
    	Map<String, String> paths = new HashMap<String, String>();
    	
    	List<TemplateSumary> listTemplateSummary = (List<TemplateSumary>) TemplateRepositoryManager.instance().getTemplateSumaries().values();
    	for (TemplateSumary templateSummary : listTemplateSummary) {
    		paths.put(templateSummary.getFolder(), BuildManagerUtil.getPathFolderTemplates()+File.separator+templateSummary.getFolder());
    	}
    	
    	return paths;
    }
    
    /**
     * Convert ${common}/tech/common/WebContent/WEB-INF/lib/mysql-connector-java-5.1.5-bin.jar to c:/.../templates/common/tech/common/WebContent/WEB-INF/lib/mysql-connector-java-5.1.5-bin.jar
     * @param template
     * @param pathTemplate
     * @return
     */
    public static String getFullPathTemplateFile(TemplateFile template, String pathTemplate) {
      String fullPathFileTemplate = "";
      
      if (template.getPathTemplateFile().indexOf("${") == 0) { //check other template reference
        String otherTemplateName = BuildManagerUtil.getFirstVariableName(template.getPathTemplateFile());
        String pathOtherTemplate = BuildManagerUtil.getPathFolderTemplates()+File.separator+otherTemplateName;
        
        //template.setPathTemplateFile(template.getPathTemplateFile().replaceAll("\\$\\{"+otherTemplateName+"\\}", "")); //remove the template var of path
        fullPathFileTemplate = pathOtherTemplate + template.getPathTemplateFile().replaceAll("\\$\\{"+otherTemplateName+"\\}", "");   
      } else {
        fullPathFileTemplate = pathTemplate + template.getPathTemplateFile();
      }
      
      return fullPathFileTemplate;
    }
    
}
