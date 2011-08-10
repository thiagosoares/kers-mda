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

public class PageName {
	public enum PAGE_NAME {
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
		private PAGE_NAME(String key) {this.key = key;}
		public String getKey() {
			return this.key;
		}
	}
	
	public static PAGE_NAME getPage(String name) {
		if (name.equals(PAGE_NAME.HowTo.getKey())) {
			return PAGE_NAME.HowTo;
		} else if (name.equals(PAGE_NAME.CRUDManual.getKey())) {
			return PAGE_NAME.CRUDManual;
		} else if (name.equals(PAGE_NAME.CRUDByMapping.getKey())) {
			return PAGE_NAME.CRUDByMapping;
		} else if (name.equals(PAGE_NAME.CRUDByDatabase.getKey())) {
			return PAGE_NAME.CRUDByDatabase;
		} else if (name.equals(PAGE_NAME.Config.getKey())) {
			return PAGE_NAME.Config;
		} else if (name.equals(PAGE_NAME.Package.getKey())) {
			return PAGE_NAME.Package;
		} else if (name.equals(PAGE_NAME.Tech.getKey())) {
			return PAGE_NAME.Tech;
		} else if (name.equals(PAGE_NAME.Layout.getKey())) {
			return PAGE_NAME.Layout;
		} else if (name.equals(PAGE_NAME.Mapping.getKey())) {
			return PAGE_NAME.Mapping;
		} else if (name.equals(PAGE_NAME.Template.getKey())) {
			return PAGE_NAME.Template;
		}
		
		return null;
	}
}
