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
package org.j2eespider.ide.editors.pages;

//import org.eclipse.swt.widgets.Composite;
import org.j2eespider.ide.editors.pages.PageName.PAGE_NAME;

public interface Page {	
	//Composite getPage(Composite parent);
	void setEnabledBuild(boolean enabled);
	boolean validateBuild();
	void savePage();
	void onOpen();
	PAGE_NAME getPageName();
}
