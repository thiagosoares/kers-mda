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
package br.com.capanema.kers.common.util;

import java.util.ArrayList;
import java.util.Iterator;

/*import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
*/
public class ProjectUtil {
	
	 /**
	  * Returns the selected resources.
	  *
	  * @return the selected resources
	  */
	 /*public static IResource[] getSelectedResources(ISelection selection) {
		  ArrayList resources = null;
		  if (!selection.isEmpty()) {
		   resources = new ArrayList();
		   Iterator elements = ((IStructuredSelection) selection).iterator();
		   while (elements.hasNext()) {
		    Object next = elements.next();
		    if (next instanceof IResource) {
		     resources.add(next);
		     continue;
		    }
		    if (next instanceof IAdaptable) {
		     IAdaptable a = (IAdaptable) next;
		     Object adapter = a.getAdapter(IResource.class);
		     if (adapter instanceof IResource) {
		      resources.add(adapter);
		      continue;
		     }
		    }
		   }
		  }
		  
		  
		  if (resources != null && !resources.isEmpty()) {
			   IResource[] result = new IResource[resources.size()];
			   resources.toArray(result);
			   return result;
		  }
		  
		  return new IResource[0];
	 }*/
	 
	 /*public static IResource getSelectedResource(ISelection selection) {
		 IResource[] resources = getSelectedResources(selection);
		 if (resources != null && resources.length >= 1) {
			 return resources[0];
		 }
		 return null;
	 }*/
	 
	 /*
	 public static IClasspathEntry[] getClasspathEntry() {
    	IClasspathEntry[] classpathEntries = null;
		try {
			IJavaProject javaProject = JavaCore.create(ConfigurationEditor.activeProject);
			classpathEntries = javaProject.getRawClasspath();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return classpathEntries;
	 }
	 */
}
