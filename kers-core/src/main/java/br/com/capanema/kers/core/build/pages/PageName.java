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

public enum PageName {
  
		HowTo("page.howto"),
		CRUDManual("page.crudManual"), 
		CRUDByMapping("page.crudByMapping"), 
		CRUDByDatabase("page.crudByDatabase"), 
		Config("page.config"), 
		Package("page.package"), 
		Tech("page.tech"),
		Layout("page.layout"),
		Mapping("page.mapping"),
		Template("page.template");
		
		private String key;
		private PageName(String key) {this.key = key;}
		public String getKey() {
			return this.key;
		}
	
	public static PageName getPage(String name) {
		if (name.equals(PageName.HowTo.getKey())) {
			return PageName.HowTo;
		} else if (name.equals(PageName.CRUDManual.getKey())) {
			return PageName.CRUDManual;
		} else if (name.equals(PageName.CRUDByMapping.getKey())) {
			return PageName.CRUDByMapping;
		} else if (name.equals(PageName.CRUDByDatabase.getKey())) {
			return PageName.CRUDByDatabase;
		} else if (name.equals(PageName.Config.getKey())) {
			return PageName.Config;
		} else if (name.equals(PageName.Package.getKey())) {
			return PageName.Package;
		} else if (name.equals(PageName.Tech.getKey())) {
			return PageName.Tech;
		} else if (name.equals(PageName.Layout.getKey())) {
			return PageName.Layout;
		} else if (name.equals(PageName.Mapping.getKey())) {
			return PageName.Mapping;
		} else if (name.equals(PageName.Template.getKey())) {
			return PageName.Template;
		}
		
		return null;
	}
}
