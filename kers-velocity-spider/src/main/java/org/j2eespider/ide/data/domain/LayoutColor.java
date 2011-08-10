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
package org.j2eespider.ide.data.domain;


//import org.eclipse.swt.graphics.RGB;
import org.j2eespider.util.ColorUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("color")
public class LayoutColor {
	private String name;
	private String image;
	private String firstColor;
	private String secondColor;
	private String thirdColor;
	private String textColor;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getFirstColor() {
		return firstColor;
	}
	/*public RGB getRGBFirstColor() {
		return ColorUtil.HextoRGB(firstColor);
	}*/
	public void setFirstColor(String firstColor) {
		this.firstColor = firstColor;
	}
	public String getSecondColor() {
		return secondColor;
	}
	/*public RGB getRGBSecondColor() {
		return ColorUtil.HextoRGB(secondColor);
	}*/
	public void setSecondColor(String secondColor) {
		this.secondColor = secondColor;
	}
	public String getTextColor() {
		return textColor;
	}
	/*public RGB getRGBTextColor() {
		return ColorUtil.HextoRGB(textColor);
	}*/
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public String getThirdColor() {
		return thirdColor;
	}
	/*public RGB getRGBThirdColor() {
		return ColorUtil.HextoRGB(thirdColor);
	}*/
	public void setThirdColor(String thirdColor) {
		this.thirdColor = thirdColor;
	}
}
