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
package org.j2eespider.util;

import java.util.List;

import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.compiler.IScanner;
import org.eclipse.jdt.core.compiler.ITerminalSymbols;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.j2eespider.ide.data.domain.ValidatorType;

/**
 * In the Eclipse (until version 3.3), the IMethod and IField do not possess methods to search annotations
 * (as in the Method and Field of the Java). Then we create this class, using this solution:
 * http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jdt.launching/launching/org/eclipse/jdt/internal/launching/JavaLaunchableTester.java?revision=1.3
 * 
 * @author bruno.braga
 */
public class AnnotationUtil {
    
	/**
	 * Return an array of annotations found in method
	 */
	public static String[] getAnnotationsInMethod(IMethod imethod, List<ValidatorType> annotations) {
		String[] annotationsInMethod = new String[annotations.size()];
		
		try {
			IBuffer buffer = imethod.getOpenable().getBuffer();
			ISourceRange sourceRange = imethod.getSourceRange();
			ISourceRange nameRange = imethod.getNameRange();
			IScanner scanner = null; // delay initialization
			
			if (sourceRange != null && nameRange != null) {
				if (scanner == null) {
					scanner= ToolFactory.createScanner(false, false, true, false);
					scanner.setSource(buffer.getCharacters());
				}
				scanner.resetTo(sourceRange.getOffset(), nameRange.getOffset());
			} else {
				return annotationsInMethod;
			}
			
			int i=0;
			for (ValidatorType annotationType: annotations) {
				if (findAnnotation(scanner, annotationType.getClassName()) || findAnnotation(scanner, annotationType.getImplementationClass())) {
					annotationsInMethod[i] = annotationType.getClassName();
					i++;
				}
			}
		} catch (JavaModelException e) {
		} catch (InvalidInputException e) {
		}
		
		return annotationsInMethod;
	}
	
	/**
	 * Replace annotations in method.
	 */
	public static void replaceAnnotationsInMethod(IMethod imethod, List<ValidatorType> annotations, String textNewAnnotations) {
		
		try {
			//monta o scanner do metodo
			IBuffer methodBuffer = imethod.getOpenable().getBuffer();
			ISourceRange sourceRange = imethod.getSourceRange();
			ISourceRange javadocRange = null;//imethod.getJavadocRange();
			ISourceRange nameRange = imethod.getNameRange();
			IScanner scanner = null; // delay initialization
			
			if (sourceRange != null && nameRange != null) {
				if (scanner == null) {
					scanner = ToolFactory.createScanner(false, false, true, false);
					scanner.setSource(methodBuffer.getCharacters());
				}
				scanner.resetTo(sourceRange.getOffset(), nameRange.getOffset());
			}
			
			//apaga todas as annotations que est�o em annotationsType e adiciona textNewAnnotations
			StringBuffer sourceMethod = new StringBuffer();
			int tok = scanner.getNextToken();
			while (tok != ITerminalSymbols.TokenNameprivate && 
					tok != ITerminalSymbols.TokenNameprotected &&
					tok != ITerminalSymbols.TokenNamepublic) { //loop nas annotations
				
				if (tok == ITerminalSymbols.TokenNameAT) { //encontrou o inicio de uma annotation
					StringBuffer thisAnnotation = new StringBuffer("@");
					tok = scanner.getNextToken();

					//trabalha todo o conte�do da annotation
					while (tok != ITerminalSymbols.TokenNameAT &&
							tok != ITerminalSymbols.TokenNameprivate && 
							tok != ITerminalSymbols.TokenNameprotected &&
							tok != ITerminalSymbols.TokenNamepublic) { //pega todo o conteudo desta annotation
						thisAnnotation.append(scanner.getCurrentTokenSource()); //pega o nome dessa annotation
						tok = scanner.getNextToken();
					}
					
					//verifica se � para apagar essa annotation (s� joga no novo sourceMethod se ela n�o tiver na lista que � para apagar)
					if (!ValidatorUtil.containsValidator(annotations, thisAnnotation.toString())) {
						sourceMethod.append(thisAnnotation);
					}
				}

			}
			
			//grava o resto do metodo			
			int posStartMethod = scanner.getCurrentTokenStartPosition();
			int lengthRemoved = posStartMethod - sourceRange.getOffset(); //conta quantos caracteres foram removidos desse metodo (as annotations)
			String codeRemain = String.valueOf(scanner.getSource()).substring(posStartMethod, posStartMethod+sourceRange.getLength()-lengthRemoved);
			if (!sourceMethod.toString().equals("") && sourceMethod.toString().lastIndexOf("\n") != sourceMethod.toString().length()-1) { //se j� tem alguma annotation, ve se precisa de quebra de linha
				sourceMethod.append("\n\t");
			}
			sourceMethod.append(textNewAnnotations); //adiciona as novas annotations antes do resto do m�todo
			sourceMethod.append(codeRemain);
			
			if (javadocRange != null) { //se tiver javadoc, n�o altera ele...
				methodBuffer.replace(sourceRange.getOffset()+javadocRange.getLength(), sourceRange.getLength()-javadocRange.getLength(), "\t"+sourceMethod.toString()); //altera o c�digo do m�todo
			} else {
				methodBuffer.replace(sourceRange.getOffset(), sourceRange.getLength(), sourceMethod.toString()); //altera o c�digo do m�todo
			}

			imethod.getOpenable().save(null, true);
		} catch (JavaModelException e) {
		} catch (InvalidInputException e) {
		}

	}

	private static boolean findAnnotation(IScanner scanner, String annotationName) throws InvalidInputException {
		String simpleName = Signature.getSimpleName(annotationName);
		StringBuffer buf = new StringBuffer();
		int tok = scanner.getNextToken();
		while (tok != ITerminalSymbols.TokenNameEOF) {
			if (tok == ITerminalSymbols.TokenNameAT) {
				buf.setLength(0);
				tok = readName(scanner, buf);
				String name = buf.toString();
				if (name.equals(annotationName) || name.equals(simpleName) || name.endsWith('.' + simpleName)) {
					return true;
				}
			} else {
				tok = scanner.getNextToken();
			}
		}
		return false;
	}
	
	private static int readName(IScanner scanner, StringBuffer buf) throws InvalidInputException {
		int tok = scanner.getNextToken();
		while (tok == ITerminalSymbols.TokenNameIdentifier) {
			buf.append(scanner.getCurrentTokenSource());
			tok = scanner.getNextToken();
			if (tok != ITerminalSymbols.TokenNameDOT) {
				return tok;
			}
			buf.append('.');
			tok = scanner.getNextToken();
		}
		return tok;
	}
	
}
