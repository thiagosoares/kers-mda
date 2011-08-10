package org.j2eespider.util;

import java.io.File;

/*import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;*/

public class EditorUtil {

    /**
     * Close editor.
     */
	public static void closeEditor() {
		//IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		//activePage.closeEditor(activePage.getActiveEditor(), false);
	}
	
    /**
     * Open editor.
     */
	/*public static void openEditor(IFile file) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		try {
			IDE.openEditor(activePage, file, true);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
    /**
     * Close and re-open editor.
     */
	/*public static void reOpenEditor(IFile file) {
		closeEditor();
		openEditor(file);
	}*/
	
	/**
	 * Show Perspective
	 * @param perspectiveID
	 * @throws WorkbenchException
	 */
	public static void showPerspective(String perspectiveID) {
//		IPerspectiveDescriptor desc = PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithId(perspectiveID);
//		if( desc != null ) {
//			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//			activePage.setPerspective(desc);
//		}
		
		//esse c�digo de cima aparentemente faz a mesma coisa que isso:
		//PlatformUI.getWorkbench().showPerspective(perspectiveID, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	}
	
	public static void importExternalProject(final String path) {
		/*File fileImport = new File(path);
		EditorUtil editorUtil = new EditorUtil();
		ProjectRecord record = editorUtil.new ProjectRecord(fileImport);
		
		String newProjectName = record.getProjectName();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		
		//close others projects
		boolean projectAlreadyExists = false;
		IProject[] projects = workspace.getRoot().getProjects();
		if (projects != null && projects.length > 0) {
			for (IProject p : projects) {
				if (p.getName().equals(newProjectName)) {
					projectAlreadyExists = true;
					continue;
				}
				p.close(null);
			}
		}
		
		IProject project = workspace.getRoot().getProject(newProjectName);
		//create new project
		if (!projectAlreadyExists) {
			project.create(record.description, null);	
		}
		//open project
		project.open(IResource.BACKGROUND_REFRESH, null);*/
	}
	
	public class ProjectRecord {
		File projectSystemFile;

		String projectName;

		Object parent;

		int level;

		//IProjectDescription description;

		/**
		 * Create a record for a project based on the info in the file.
		 * 
		 * @param file
		 */
		ProjectRecord(File file) {
			projectSystemFile = file;
			setProjectName();
		}

		/**
		 * Set the name of the project based on the projectFile.
		 */
		private void setProjectName() {
			/*IProjectDescription newDescription = null;
			try {

				IPath path = new Path(projectSystemFile.getPath());
				// if the file is in the default location, use the directory
				// name as the project name
				if (isDefaultLocation(path)) {
					projectName = path.segment(path.segmentCount() - 2);
					newDescription = IDEWorkbenchPlugin
							.getPluginWorkspace().newProjectDescription(
									projectName);
				} else {
					newDescription = IDEWorkbenchPlugin
							.getPluginWorkspace().loadProjectDescription(
									path);
				}

			} catch (CoreException e) {
				// no good couldn't get the name
			}

			if (newDescription == null) {
				this.description = null;
				projectName = ""; //$NON-NLS-1$
			} else {
				this.description = newDescription;
				projectName = this.description.getName();
			}*/
		}

		/**
		 * Returns whether the given project description file path is in the
		 * default location for a project
		 * 
		 * @param path
		 *            The path to examine
		 * @return Whether the given path is the default location for a project
		 */
		/*private boolean isDefaultLocation(IPath path) {
			// The project description file must at least be within the project,
			// which is within the workspace location
			if (path.segmentCount() < 2)
				return false;
			return path.removeLastSegments(2).toFile().equals(
					Platform.getLocation().toFile());
		}*/

		/**
		 * Get the name of the project
		 * 
		 * @return String
		 */
		public String getProjectName() {
			return projectName;
		}
	}
}
