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

//import org.eclipse.swt.graphics.RGB;

/**
 * This class is responsible for converting object RGB of the SWT into a color in hexadexial (ex: #000000), and vice versa.
 * 
 * @author bruno.braga
 */
public class ColorUtil {
	/*public static String RGBtoHex(RGB rgb) {
		String red = Integer.toHexString(rgb.red);
		String green = Integer.toHexString(rgb.green);
		String blue = Integer.toHexString(rgb.blue);		
		
		StringBuffer strHexa = new StringBuffer();
		if (red.length() == 1) {strHexa.append("0");}
        strHexa.append(red);
        
        if (green.length() == 1) {strHexa.append("0");}
        strHexa.append(green);
        
        if (blue.length() == 1) {strHexa.append("0");}
        strHexa.append(blue);
        
        return strHexa.toString();
	}
	
	public static RGB HextoRGB(String hex) {
		if (hex == null || hex.equals("")) {return null;}
		hex = hex.replaceAll("#", "");
		
		while (hex.length() < 6) {
			hex = "0" + hex;
		}
		
		String red = "0x"+hex.substring(0, 2);
		String green = "0x"+hex.substring(2, 4);
		String blue = "0x"+hex.substring(4, 6);
		
		return new RGB(Integer.decode(red).intValue(), Integer.decode(green).intValue(), Integer.decode(blue).intValue());
	}*/
}
