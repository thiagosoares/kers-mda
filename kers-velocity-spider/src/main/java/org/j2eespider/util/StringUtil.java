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

import java.math.BigDecimal;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StringUtil {
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
	
}
