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
package br.com.capanema.kers.core.build.pages;

import br.com.capanema.kers.common.model.project.ProjectConfig;


public class PageFactory {
	
  public static Page newPage(PageName pageName, ProjectConfig config) {
		if (pageName == PageName.HowTo) {
			//return HowToPage.getInstance(pageName);
		} else if (pageName == PageName.CRUDManual) {
			return new CrudManualPage(config);
		} else if (pageName == PageName.CRUDByMapping) {
			return new CrudByMappingPage(config);
		} else if (pageName == PageName.CRUDByDatabase) {
			//return new CrudByDatabasePage.getInstance(config);
		} else if (pageName == PageName.Config) {
			//return new ConfigPage.getInstance(config);
		} else if (pageName == PageName.Package) {
			//return new PackagePage.getInstance(config);
		} else if (pageName == PageName.Tech) {
			return new TechPage(config);
		} else if (pageName == PageName.Layout) {
			return new LayoutPage(config);
		} else if (pageName == PageName.Mapping) {
			return new MappingPage(config);
		} else if (pageName == PageName.Template) {
			//return new TemplatePage.getInstance(config);
		}
		return null;
	}
}
