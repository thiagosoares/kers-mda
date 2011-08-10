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

public class DatabaseMapping implements Cloneable {
	private String domainPackage;

	public String getDomainPackage() {
		return domainPackage;
	}
	public void setDomainPackage(String domainPackage) {
		this.domainPackage = domainPackage;
	}
	
	public DatabaseMapping clone() {
	   try {
	      return (DatabaseMapping) super.clone();
	   } catch (CloneNotSupportedException e) {
	      return null;
	   }
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((domainPackage == null) ? 0 : domainPackage.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DatabaseMapping other = (DatabaseMapping) obj;
		if (domainPackage == null) {
			if (other.domainPackage != null)
				return false;
		} else if (!domainPackage.equals(other.domainPackage))
			return false;
		return true;
	}
	
	
}
