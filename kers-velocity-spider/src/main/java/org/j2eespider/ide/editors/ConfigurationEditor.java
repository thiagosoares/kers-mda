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
package org.j2eespider.ide.editors;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
/*
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;*/
import org.j2eespider.SpiderPlugin;
import org.j2eespider.build.rules.BuildManager;
import org.j2eespider.ide.data.domain.ConfigSpider;
import org.j2eespider.ide.data.domain.FieldLocation;
import org.j2eespider.ide.data.domain.HtmlType;
import org.j2eespider.ide.data.domain.ProjectInfo;
import org.j2eespider.ide.data.domain.RepositoryTemplateInfo;
import org.j2eespider.ide.data.domain.Template;
import org.j2eespider.ide.data.domain.ValidatorType;
import org.j2eespider.ide.data.rules.XmlManager;
import org.j2eespider.ide.data.rules.XmlManager.XML_TYPE;
import org.j2eespider.ide.editors.exception.TemplateInvalidException;
import org.j2eespider.ide.editors.pages.ConfigPage;
//import org.j2eespider.ide.editors.pages.MappingPage;
import org.j2eespider.ide.editors.pages.Page;
//import org.j2eespider.ide.editors.pages.PageFactory;
import org.j2eespider.ide.editors.pages.PageName;
//import org.j2eespider.ide.editors.pages.TemplatePage;
import org.j2eespider.ide.editors.pages.PageName.PAGE_NAME;
//import org.j2eespider.ide.outline.OutlinePage;
import org.j2eespider.util.BuildManagerUtil;
import org.j2eespider.util.DialogUtil;
import org.j2eespider.util.ProjectUtil;
import org.j2eespider.util.PropertiesUtil;

import com.thoughtworks.xstream.converters.ConversionException;

/**
 * 
 */
public class ConfigurationEditor { //extends MultiPageEditorPart implements IResourceChangeListener {

	/** The text editor used in page 0. */
	//private TextEditor textEditor;

	/** Data of project config */
	private static ConfigSpider configSpider;
	
	/** Currente Template */
	private static Template currentTemplate;
	
	/** All templates available for this user */
	private static List<RepositoryTemplateInfo> listTemplateInfo;	
	
	private static List<RepositoryTemplateInfo> filteredTemplateInfo;
	
	/** Data of Validator Types*/
	private static java.util.List<ValidatorType> validatorTypes;
	
	/** Data of HTML Types*/
	private static java.util.List<HtmlType> htmlTypes;

	/** Data of where we need to show fields*/
	private static java.util.List<FieldLocation> fieldLocations;	
	
	/** File .spider */
	public static IFile fileConfigSpider;
	
	/** Instance of Editor */
	//public static IEditorPart editor;
	
	/** Active Project */
	public static IProject activeProject;
	
	//public static Shell shell;
	
	/** Outline for Spider Editor */
	//public static OutlinePage outlinePage;
	
	private HashMap<Integer, Page> mapPages;
	private int indexActivePage = 0;
	private int indexTextEditor = -1;
	
	private static ConfigurationEditor instance;
	
	/**
	 * Creates a multi-page editor example.
	 */
	public ConfigurationEditor() {
		super();
		//ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		instance = this;
		//outlinePage = null; //precisa ser null ao abrir o editor
		mapPages = new HashMap<Integer, Page>();
	}
	
	public static ConfigurationEditor getInstance() {
		return instance;
	}
	
	//static get methods
	public static ConfigSpider getConfigSpider() {
		return configSpider;
	}	
	
	public static Template getCurrentTemplate() {
		return currentTemplate;
	}

	/**
	 * Return all templates (internal + external)
	 * @return
	 */
	public static List<RepositoryTemplateInfo> getListTemplateInfo() {
		return listTemplateInfo;
	}
	
	/**
	 * Return external templates
	 * @return
	 */
	public static List<RepositoryTemplateInfo> getFilteredTemplateInfo() {
		if (filteredTemplateInfo == null) {
			filteredTemplateInfo = new ArrayList<RepositoryTemplateInfo>();
			
			for (RepositoryTemplateInfo templateSummary : ConfigurationEditor.getListTemplateInfo()) {
				if (!templateSummary.isInternalOnly()) { //exclude internal templates in this combo
					filteredTemplateInfo.add(templateSummary);
				}
			}	
		}
		
		return filteredTemplateInfo; 
	}	

	public static java.util.List<ValidatorType> getValidatorTypes() {
		return validatorTypes;
	}

	public static java.util.List<HtmlType> getHtmlTypes() {
		return htmlTypes;
	}

	public static java.util.List<FieldLocation> getFieldLocations() {
		return fieldLocations;
	}

	/*@Override
	public Object getAdapter(Class adapter) {
		if (IContentOutlinePage.class.equals(adapter)) { //ativa o outline
			if (this.outlinePage == null) {
				this.outlinePage = new OutlinePage();
			}
			return this.outlinePage; //abre o outline para essa Page
		} 
		
		return super.getAdapter(adapter);
	}
*/
	/**
	 * Creates page 0 of the multi-page editor,
	 * which contains a text editor.
	 */
	/*void createEditorPage() {
		try {
			textEditor = new TextEditor();
			indexTextEditor = addPage(textEditor, getEditorInput());
			setPageText(indexTextEditor, PropertiesUtil.getMessage("page.source", "spider"));
		} catch (PartInitException e) {
			ErrorDialog.openError(
				getSite().getShell(),
				"Error creating nested text editor",
				null,
				e.getStatus());
		}
	}*/

	/*void createPage(PAGE_NAME pageName) {
		Page page = PageFactory.newPage(pageName);

		int index = addPage(page.getPage(getContainer()));
		setPageText(index, PropertiesUtil.getMessage(pageName.getKey(), "spider"));
		
		//add page in map, to find in next steps
		mapPages.put(index, page);
	}*/
	
	/**
	 * Creates the pages of the multi-page editor.
	 */
	/*protected void createPages() {
		try {
			checkTemplate();
			
			//se tiver template carrega todas as p�ginas
			createPage(PAGE_NAME.Config);
			
			//create pages dynamically
			for (String pageId : currentTemplate.getEnabledPages()) {
				PAGE_NAME page = PageName.getPage("page."+pageId);
				createPage(page);
			}
			
			createEditorPage();
			createPage(PAGE_NAME.Template);
			createPage(PAGE_NAME.HowTo);
			
			ConfigPage configPage = (ConfigPage)getPage(PAGE_NAME.Config);
			configPage.checkPluginDependencies(); //check is the dependencies are ok for template default
		} catch (TemplateInvalidException e) {
			//existe algum problema com o template
			createPage(PAGE_NAME.Template);
			TemplatePage templatePage = (TemplatePage)getPage(PAGE_NAME.Template);
			templatePage.setWelcomeMessage(e.getMessage(), e.getParams());			
		}
	}*/
	
	/**
	 * Check if template exists and it's compatible with this plug-in version.
	 * @return
	 * @throws TemplateInvalidException 
	 */
	private boolean checkTemplate() throws TemplateInvalidException {
		//template don't exists
		if (BuildManagerUtil.getPathFolderTemplates() == null || BuildManagerUtil.getPathFolderTemplates().equals("")) {
			throw new TemplateInvalidException("dialog.validateTemplate.warn.selectTemplate", new String[] {});
		}
		
		if (this.listTemplateInfo == null || this.listTemplateInfo.size() == 0) {
			throw new TemplateInvalidException("dialog.validateTemplate.error.withoutTemplate", new String[] {});
		}
		
		for (RepositoryTemplateInfo templateSummary : this.listTemplateInfo) {
			long templateCompatibilityDate = Long.valueOf(templateSummary.getCompatibilityDate());
			long pluginCompatibilityDate = Long.valueOf(PropertiesUtil.getMessage("compatibilityDate", "template"));
			if (templateCompatibilityDate < pluginCompatibilityDate) {
				//clean template value
				SpiderPlugin.getDefault().setTemplatePathPreference("");
				
				throw new TemplateInvalidException("dialog.validateTemplate.error.templateCompatibilityDate", new String[] {Long.toString(pluginCompatibilityDate)});
			}
		}
		
		return true;
	}
	
	/**
	 * The <code>MultiPageEditorPart</code> implementation of this 
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	/*public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}*/
	
	/**
	 * Saves the multi-page editor's document.
	 */
	/*public void doSave(IProgressMonitor monitor) {		
		textEditor.doSave(monitor);
	}*/
	
	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	/*public void doSaveAs() {
		textEditor.doSaveAs();
		setPageText(indexTextEditor, textEditor.getTitle());
		setInput(textEditor.getEditorInput());
	}*/
	
	/* (non-Javadoc)
	 * Method declared on IEditorPart
	 */
	/*public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}*/
	
	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	/*public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {		
		if (!(editorInput instanceof IFileEditorInput)) {
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		}
		
		if (site.getPage().getSelection() == null) { //don't have a selected project
			MessageDialog.openError(site.getShell(), PropertiesUtil.getMessage("dialog.error.title", "spider"), PropertiesUtil.getMessage("dialog.error.selectedProject", "spider"));
			throw new PartInitException("Don't have a selected project");
		}
		
		//init activePage
		indexActivePage = -1; //n�o tem p�gina ativa ainda
		
		//read active project
		IResource selectionResource = ProjectUtil.getSelectedResource(site.getPage().getSelection());
		if (selectionResource != null) {
			activeProject = selectionResource.getProject();	
		}
		
		ProjectInfo.setLocation(activeProject.getLocation().toOSString());
		ProjectInfo.setName(activeProject.getName());
		
		//workspace refresh (prevent that files be outdated)
		try {
			activeProject.refreshLocal(IResource.DEPTH_ONE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}		
		
		fileConfigSpider = activeProject.getFile(XML_TYPE.CONFIG_SPIDER.getFileName());
		//File checkFile = new File(fileSpider.getLocation().toOSString());
		
		//if not exists file .spider, create then
		if (!fileConfigSpider.exists()) { //fileSpider.exists() cause wrong info, and BUG (SPIDER-14)
			try {
				//InputStream fileStream = new ByteArrayInputStream("".getBytes());
				//fileSpider.create(fileStream, true, null);
				//Config read of .spider
				ConfigurationEditor.configSpider = new ConfigSpider();
				configSpider.setAppName(activeProject.getName());
				XmlManager.writeXml(ConfigurationEditor.configSpider, fileConfigSpider.getLocation().toOSString(), XML_TYPE.CONFIG_SPIDER);
				
				refreshWorkspace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				ConfigurationEditor.configSpider = (ConfigSpider)XmlManager.loadXml(fileConfigSpider.getLocation().toOSString(), XML_TYPE.CONFIG_SPIDER);
			} catch (ConversionException e) {
				DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.error.title", "spider"), PropertiesUtil.getMessage("dialog.error.project.xmlWrong", "spider"), DialogUtil.MESSAGE_TYPE.ERROR);
				throw new PartInitException(PropertiesUtil.getMessage("dialog.error.project.xmlWrong", "spider"));
			}
		}
		
		//read list of templates
		this.listTemplateInfo = (List<RepositoryTemplateInfo>)XmlManager.loadXml(BuildManagerUtil.getPathFolderTemplates()+File.separator+XML_TYPE.TEMPLATE_LIST.getFileName(), XML_TYPE.TEMPLATE_LIST);
		this.filteredTemplateInfo = null;
		
		//set default template folder
		String variableTemplates = SpiderPlugin.getDefault().getTemplatePathPreference();
		
		if (variableTemplates == null || variableTemplates.equals("") || !new File(variableTemplates).exists()) { //if the path of templates folder not exists				
			BuildManagerUtil.clearTemplatesPathCache();
			SpiderPlugin.getDefault().setTemplatePathPreference("");
			
//				String pathTemplates = FileLocator.resolve(this.getClass().getResource("/templates/")).toString(); //aqui ainda n�o tenho setado o BuildManagerUtil.getTemplatesPath()
//				if (pathTemplates.matches("^file:/[a-zA-Z]{1}:.*")) { //windows
//					pathTemplates = pathTemplates.replaceAll("file:/", "").replaceAll("/", "\\\\");
//				} else { //linux
//					pathTemplates = pathTemplates.replaceAll("file:/", "/");
//				}
//				
		} else {
			try {
				loadTemplate();
			} catch (ConversionException e) {
				DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.error.title", "spider"), PropertiesUtil.getMessage("dialog.error.template.xmlWrong", "spider"), DialogUtil.MESSAGE_TYPE.ERROR);
				throw new PartInitException(PropertiesUtil.getMessage("dialog.error.template.xmlWrong", "spider"));
			}
		}

		//set types 
		loadValidatorType();
		loadHtmlType();
		loadFieldLocations();
		
		//set language
		Locale.setDefault(new Locale(ConfigurationEditor.configSpider.getLanguage()));
		
		shell = site.getShell();
		
		super.init(site, editorInput);
	}*/
	
	private void loadValidatorType() {
		if (BuildManagerUtil.getPathFolderTemplates() == null || BuildManagerUtil.getPathFolderTemplates().equals("")) {return;} //n�o tem templates, n�o carrega os validators
		
		String templateFolder = ConfigurationEditor.getConfigSpider().getTemplateFolder();
		String pathValidatorConfigFile = BuildManagerUtil.getPathFolderTemplates()+File.separator+templateFolder+File.separator+XML_TYPE.VALIDATOR_TYPE.getFileName();
		validatorTypes = (java.util.List<ValidatorType>)XmlManager.loadXml(pathValidatorConfigFile, XML_TYPE.VALIDATOR_TYPE);
	}	
	
	private void loadHtmlType() {
		if (BuildManagerUtil.getPathFolderTemplates() == null || BuildManagerUtil.getPathFolderTemplates().equals("")) {return;} //n�o tem templates, n�o carrega os validators
		
		String templateFolder = ConfigurationEditor.getConfigSpider().getTemplateFolder();
		String pathValidatorConfigFile = BuildManagerUtil.getPathFolderTemplates()+File.separator+templateFolder+File.separator+XML_TYPE.HTML_TYPE.getFileName();
		htmlTypes = (java.util.List<HtmlType>)XmlManager.loadXml(pathValidatorConfigFile, XML_TYPE.HTML_TYPE);
	}	

	private void loadFieldLocations() {
		if (BuildManagerUtil.getPathFolderTemplates() == null || BuildManagerUtil.getPathFolderTemplates().equals("")) {return;} //n�o tem templates, n�o carrega os validators
		
		String templateFolder = ConfigurationEditor.getConfigSpider().getTemplateFolder();
		String pathValidatorConfigFile = BuildManagerUtil.getPathFolderTemplates()+File.separator+templateFolder+File.separator+XML_TYPE.FIELD_LOCATION.getFileName();
		fieldLocations = (java.util.List<FieldLocation>)XmlManager.loadXml(pathValidatorConfigFile, XML_TYPE.FIELD_LOCATION);
	}		
	
	/* (non-Javadoc)
	 * Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}
	
	/**
	 * 
	 */
	/*protected void pageChange(int newPageIndex) {
		//save data on pageChange
		if (indexActivePage >= 0) {			
			Page page = mapPages.get(indexActivePage);
		
			if (page != null) {
				page.savePage();
			}
		}

		//my control of active page index (last tab)
		indexActivePage = newPageIndex;	

		super.pageChange(newPageIndex);
		
		//change outline for new page
		Page newPage = mapPages.get(newPageIndex);
		if (newPage != null) {
			if (outlinePage != null) {
				outlinePage.refreshData(newPage.getPageName());
			}
			
			//call onopen event
			newPage.onOpen();
		}
	}*/
	
	/**
	 * Refresh Outline using Page
	 */
	/*public void refreshOutline() {
		if (outlinePage != null) {
			Page newPage = mapPages.get(indexActivePage);
			outlinePage.refreshData(newPage.getPageName());
		}
	}*/
	
	/**
	 * Change Page
	 * @param pageName
	 */
	/*public Page changeToPage(PAGE_NAME pageName) {
		for (Integer pageIndex : mapPages.keySet()) {
			Page page = mapPages.get(pageIndex);
			if (page.getPageName() == pageName) {
				setActivePage(pageIndex);
				return page;
			}
		}
		
		return null;
	}*/
	
	/**
	 * Return the object Page of pageName
	 * @param pageName
	 */
	public Page getPage(PAGE_NAME pageName) {
		for (Integer pageIndex : mapPages.keySet()) {
			Page page = mapPages.get(pageIndex);
			if (page.getPageName() == pageName) {
				return page;
			}
		}
		
		return null;
	}
	
	/**
	 * Return Pages
	 * @param pageName
	 */
	public List<Page> getPages() {
		List<Page> pages = new ArrayList<Page>();
		
		for (Integer pageIndex : mapPages.keySet()) {
			Page page = mapPages.get(pageIndex);
			pages.add(page);
		}
		
		return pages;
	}
	

	/**
	 * Find TemplateSummary by name.
	 * @param templateName
	 * @return
	 */
//	public static TemplateSummary getTemplateSummaryByName(String templateName) {
//		for (TemplateSummary templateSummary : listTemplateSummary) {
//			if (templateName.equals(templateSummary.getName())) {
//				return templateSummary;
//			}
//		}
//		
//		return null;
//	}
	
	public static void loadTemplate() {
		String templateFolder = ConfigurationEditor.getConfigSpider().getTemplateFolder();
		String pathTemplateFile = BuildManagerUtil.getPathFolderTemplates()+File.separator+templateFolder+File.separator+XML_TYPE.TEMPLATE.getFileName();
		
		//n�o tem template selecionado, pega o primeiro da lista
		if (templateFolder == null || !(new File(pathTemplateFile).exists())) {
			//get the first template (exclude internal templates)
			for (RepositoryTemplateInfo templateInfo : ConfigurationEditor.getListTemplateInfo()) {
				if (templateInfo != null && !templateInfo.isInternalOnly()) {
					ConfigurationEditor.getConfigSpider().setTemplateFolder(templateInfo.getFolder());
					break;
				}
			}
			
			templateFolder = ConfigurationEditor.getConfigSpider().getTemplateFolder();
			pathTemplateFile = BuildManagerUtil.getPathFolderTemplates()+File.separator+templateFolder+File.separator+XML_TYPE.TEMPLATE.getFileName();
		}
		
		currentTemplate = (Template)XmlManager.loadXml(pathTemplateFile, XML_TYPE.TEMPLATE);
		//Make this attribute optional
		if (currentTemplate.getLanguages() == null || currentTemplate.getLanguages().size() == 0) {
			LinkedList<String> listLanguageCode = new LinkedList<String>();
			listLanguageCode.add("en");
			listLanguageCode.add("pt");			
			currentTemplate.setLanguages(listLanguageCode);
		}
	}
	
	/**
	 * Closes all project files on project close.
	 */
	/*public void resourceChanged(final IResourceChangeEvent event){
		if(event.getType() == IResourceChangeEvent.PRE_CLOSE){
			Display.getDefault().asyncExec(new Runnable(){
				public void run(){
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (int i = 0; i<pages.length; i++){
						if(((FileEditorInput)textEditor.getEditorInput()).getFile().getProject().equals(event.getResource())){
							IEditorPart editorPart = pages[i].findEditor(textEditor.getEditorInput());
							pages[i].closeEditor(editorPart,true);
						}
					}
				}            
			});
		} else if (event.getType() == IResourceChangeEvent.POST_CHANGE) { //verifica altera��es no projeto
			MappingPage mappingPage = (MappingPage)ConfigurationEditor.getInstance().getPage(PAGE_NAME.Mapping);
			if (mappingPage != null && BuildManager.isInBuild() == false) { //s� considera os eventos que ocorrem depois que o mapping page foi criado e que n�o esteja no meio do build
				mappingPage.refreshTree();	
			}
		}
	}*/

	public static void saveConfigSpider(ConfigSpider config, boolean checkChange) {
		//if change config, save!... or no check, save!
		if ((checkChange && !config.equals(configSpider)) || checkChange == false) {
			configSpider = config; //new data of configSpider
			saveConfigSpider();
		}
	}
	
	public static void saveConfigSpider() {
		XmlManager.writeXml(configSpider, fileConfigSpider.getLocation().toOSString(), XML_TYPE.CONFIG_SPIDER);
		
		//workspace refresh
		refreshWorkspace();
	}

	/**
	 * Refresh files of the workspace
	 */
	public static void refreshWorkspace() {
		try {
			ConfigurationEditor.activeProject.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}