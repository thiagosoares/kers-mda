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

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jdt.internal.core.ImportDeclaration;
import org.eclipse.jdt.internal.core.ResolvedSourceType;
import org.j2eespider.ide.data.domain.ProjectInfo;
import org.j2eespider.ide.editors.ConfigurationEditor;

/**
 * This class is responsible for search Java archives in the project of developer (activeProject).
 * Search is made by Pattern and Annotations.
 * 
 * @author bruno.braga
 */
public class ClassSearchUtil {
    
	public static LinkedList<ResolvedSourceType> searchJavaFiles() {
		return searchJavaFiles("*", new ArrayList());
	}
	
	public static LinkedList<ResolvedSourceType> searchJavaFiles(String stringPattern) {
		return searchJavaFiles(stringPattern, new ArrayList());
	}
	
	public static ResolvedSourceType searchExactJavaFile(String stringPattern) {
		LinkedList<ResolvedSourceType> result = searchJavaFiles(stringPattern, new ArrayList());
		
		for (ResolvedSourceType classFile : result) {
			if (classFile.getFullyQualifiedName().equals(stringPattern)) {
				return classFile;
			}
		}
		
		return null; 
	}
	
	public static LinkedList<ResolvedSourceType> searchJavaFiles(ArrayList listAnnotations) {
		return searchJavaFiles("*", listAnnotations);
	}
	
    public static LinkedList<ResolvedSourceType> searchJavaFiles(String stringPattern, ArrayList listAnnotations) {
    	IJavaProject javaProject = JavaCore.create(ConfigurationEditor.activeProject);    	
    	    	
        // Create Pattern of search
    	SearchPattern pattern = SearchPattern.createPattern(stringPattern, IJavaSearchConstants.CLASS, IJavaSearchConstants.ALL_OCCURRENCES, SearchPattern.R_PATTERN_MATCH);
        
        // Scope of search
        IJavaSearchScope scope = SearchEngine.createJavaSearchScope(new IJavaElement[] {javaProject}, IJavaSearchScope.SOURCES);

        // Get the search requestor
        LinkedList<ResolvedSourceType> listClassFile = new LinkedList<ResolvedSourceType>();
        SearchRequestor requestor = new ClassSearchResultCollector(listClassFile, listAnnotations);
        
        // Participant
        SearchParticipant[] participants= new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() };
        
        // Search
        SearchEngine searchEngine = new SearchEngine();
        try {
			searchEngine.search(pattern, participants, scope, requestor, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		return listClassFile;
    }
    
    /**
     * Get path of source folder.
     * @return
     */
	public static String getSourcePath() {
		//it fills the list with the present classes in the project
		LinkedList<ResolvedSourceType> listClassFile = ClassSearchUtil.searchJavaFiles(new ArrayList());
		
		String path = ProjectInfo.getLocation()+File.separator;
		
		if (listClassFile.size() > 0) {
			ResolvedSourceType classOne = listClassFile.get(0); //get first class
			//String javaSourceFolder = classOne.getPackageFragment().getParent().getPath().lastSegment(); //get name of java source folder
			String projectName = ProjectInfo.getName();
			if (File.separator.equals("\\")) {
				path = path.replaceFirst("\\\\"+projectName+"\\\\", "");
			} else {
				path = path.replaceFirst("/"+projectName+"/", "");
			}
			
			
			return path+classOne.getPackageFragment().getParent().getPath().toOSString(); //return project path + java source folder
		}
		
		return path;
	}
	
	/**
	 * Return path of JavaSources defined in .classpath
	 * @return
	 * @throws JavaModelException
	 */
	public static List<String> getJavaSources() throws JavaModelException {
		List<String> javaSources = new ArrayList<String>();
		
		IJavaProject javaProject = JavaCore.create(ConfigurationEditor.activeProject);
		IPackageFragmentRoot[] roots = javaProject.getAllPackageFragmentRoots();
		if (roots != null && roots.length > 0) {
			for (IPackageFragmentRoot root : roots) {
				if (root.getPath().segment(0).equals(javaProject.getElementName())) {
					javaSources.add(root.getPath().removeFirstSegments(1).makeRelative().toString());
				}
			}	
		}
		
		return javaSources;
	}
    
	private static class ClassSearchResultCollector extends SearchRequestor {
		ArrayList<String> findControl = new ArrayList();
		ArrayList<String> annotationsControl = new ArrayList();
		ArrayList<String> annotations;
		LinkedList<ResolvedSourceType> fMatches;
		

		public ClassSearchResultCollector(LinkedList<ResolvedSourceType> list, ArrayList<String> annotations) {
			this.fMatches=list;
			this.annotations = annotations;
		}

		public void acceptSearchMatch(SearchMatch match) throws CoreException {
			Object enclosingElement = match.getElement();
			
			if (annotations.size() > 0 && enclosingElement instanceof ImportDeclaration && annotations.contains(((ImportDeclaration)enclosingElement).getNameWithoutStar())) {
				ImportDeclaration importDeclaration = (ImportDeclaration)	enclosingElement;
				annotationsControl.add(importDeclaration.getParent().getParent().getHandleIdentifier());
			}
			
			if (!(enclosingElement instanceof ResolvedSourceType)) {
				return;
			}
			
			ResolvedSourceType sourceType = (ResolvedSourceType) enclosingElement;
			if (!findControl.contains(sourceType.getFullyQualifiedName())) { //se ainda n�o tiver inserido essa classe
				if (annotations.size() == 0 || annotationsControl.contains(sourceType.getParent().getHandleIdentifier())) {
					fMatches.add((ResolvedSourceType) enclosingElement);
					findControl.add(sourceType.getFullyQualifiedName());
				}
			}
		}

		public void endReporting() {
		}
	}
}
