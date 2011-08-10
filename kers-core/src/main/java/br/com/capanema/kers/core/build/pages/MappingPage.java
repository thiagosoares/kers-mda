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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;  
import java.util.Map;

import br.com.capanema.kers.common.model.project.ProjectConfig;
import br.com.capanema.kers.core.build.BuildManager;


public class MappingPage implements Page {

  private ProjectConfig configSpider;
	
	public MappingPage (ProjectConfig configSpider) {
	  this.configSpider = configSpider;
	}
	
	public void build() throws Exception {
    //if (validateBuild()) {
        BuildManager buildManager = new BuildManager(this.configSpider);
        buildManager.buildMapping();  
    //}
  }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*private static Map<String, MappingPage> instance = new HashMap<String, MappingPage>();
	private Button buttonRefresh;
	private TreeViewer treeViewer;
	private Button buttonBuild;
	private PAGE_NAME pageName;*/
	
	/*public Composite getPage(final Composite parent) {	
		
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		final ScrolledForm form = toolkit.createScrolledForm(parent);
		form.setText(PropertiesUtil.getMessage("editor.title", "spider"));
		form.getBody().setLayout(new FillLayout());
		
		Action helpAction = new Action() {
			@Override
			public void run() {
				IContext context = HelpSystem.getContext("org.j2eespider.mappingPageContext");
        		PlatformUI.getWorkbench().getHelpSystem().displayHelpResource(context.getRelatedTopics()[0].getHref());
			}
		};
		helpAction.setText(PropertiesUtil.getMessage("page.action.help", "spider"));
		helpAction.setImageDescriptor(SpiderPlugin.getImageDescriptor("images/icon_help.png"));
		form.getToolBarManager().add(helpAction);
		form.getToolBarManager().update(true);
		
		Composite composite = toolkit.createComposite(form.getBody(), SWT.NONE);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, "org.j2eespider.mappingPageContext"); //context of help
        composite.setLayout(new GridLayout());
		
		//setup bold font
		Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
	    
		// Database config
		Section mappingConfigSection = toolkit.createSection(composite, Section.TWISTIE | Section.EXPANDED | Section.DESCRIPTION|Section.TITLE_BAR);
		mappingConfigSection.setText(PropertiesUtil.getMessage("page.mapping.group", "spider"));
		mappingConfigSection.setDescription(PropertiesUtil.getMessage("page.mapping.group.description", "spider"));
		mappingConfigSection.setActiveToggleColor(new Color(Display.getDefault(), 255,0,0));
		mappingConfigSection.addExpansionListener(new ExpansionAdapter() {
			  public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			  }
			 });
		mappingConfigSection.setLayout(new FillLayout());
		mappingConfigSection.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		
		Composite mappingConfigComposite = toolkit.createComposite(mappingConfigSection, SWT.NONE);
		mappingConfigComposite.setLayout(new MigLayout("wrap 2, fillx"));
		mappingConfigSection.setClient(mappingConfigComposite);
		
		//tree of packages
		treeViewer = new TreeViewer(mappingConfigComposite);
		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setLabelProvider(new TreeLabelProvider());
		treeViewer.setInput(new MappingFolderNode(new File(ClassSearchUtil.getSourcePath())));
		treeViewer.expandAll();
		treeViewer.getControl().setLayoutData("grow, hmin 200px, hmax 300px, wmax 97%, wrap");
		
		//select tree
		startSelectTree();
		
	    //button refresh
//		Image imageRefresh = new Image(mappingConfigComposite.getDisplay(), getClass().getResourceAsStream("/images/icon_refresh.png"));
//	    buttonRefresh = toolkit.createButton(mappingConfigComposite, "", SWT.PUSH);
//	    buttonRefresh.setImage(imageRefresh);
//	    buttonRefresh.addListener(SWT.Selection, listener);
//	    buttonRefresh.setLayoutData("left, top");

		Image imageBuild = new Image(composite.getDisplay(), getClass().getResourceAsStream("/images/icon_build.png"));
	    buttonBuild = toolkit.createButton(composite, PropertiesUtil.getMessage("page.button.build", "spider"), SWT.PUSH);
	    buttonBuild.setImage(imageBuild);
	    buttonBuild.addListener(SWT.Selection, listener);
	    
		return form;
	}
	
	private void startSelectTree() {
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider();
		if (configSpider.getDatabaseMapping() != null && configSpider.getDatabaseMapping().getDomainPackage() != null) {
				java.util.List<MappingFolderNode> root = new ArrayList();
				TreeItem[] items  = treeViewer.getTree().getItems();
				for (TreeItem item : items) {
					root.add((MappingFolderNode) item.getData());
				}
	
				selectTree(configSpider, root);
		}		
	}
	
	private void selectTree(ConfigSpider configSpider, java.util.List<MappingFolderNode> items) {
		for (MappingFolderNode treeNode : items) {
			if (treeNode.getPackage().equals(configSpider.getDatabaseMapping().getDomainPackage()) ){
				treeViewer.setSelection(new StructuredSelection(treeNode));
				return;
			}
			selectTree(configSpider, treeNode.getChildren());
		}
	}
	
	*//**
	 * Listener of the buttons of the view.
	 *//*
    Listener listener = new Listener() {
        public void handleEvent(Event event) {
        	if (event.widget == buttonBuild) {
        		savePage(); //save configs
        		if (validateBuild()) {
            		BuildManager buildManager = new BuildManager();
            		buildManager.buildMapping();	
        		}
        	} else if (event.widget == buttonRefresh) {
        		refreshTree();
        	}
        }
    };

    *//**
     * Refresh java source treeviewer.
     *//*
    public void refreshTree() {
		Display.getDefault().syncExec(new Runnable() {
	        public void run() {
	    		treeViewer.setInput(new MappingFolderNode(new File(ClassSearchUtil.getSourcePath())));
	    		treeViewer.expandAll();
	     		treeViewer.refresh();
	     		startSelectTree();
	        }
		});
    }
    
	public void savePage() {			
		TreeSelection treeSelection = (TreeSelection)treeViewer.getSelection();
		MappingFolderNode folderNode = (MappingFolderNode)treeSelection.getFirstElement();
		
		if (folderNode == null) {return;}
		
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider().clone();
		DatabaseMapping mapping = configSpider.getDatabaseMapping();
		if (mapping == null) {
			mapping = new DatabaseMapping();
		}
		mapping.setDomainPackage(folderNode.getPackage());
		
		configSpider.setDatabaseMapping(mapping);
		
		//save config if change
		ConfigurationEditor.saveConfigSpider(configSpider, true);		
	}

	public PAGE_NAME getPageName() {
		return this.pageName;
	}

	public boolean validateBuild() {
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider();
		if (configSpider.getDbHost() == null || configSpider.getDbHost().equals("") ||
			configSpider.getDbName() == null || configSpider.getDbName().equals("")	||
			configSpider.getDbUsername() == null || configSpider.getDbUsername().equals("")) {
			
			DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.validateBuild.title", "spider"), PropertiesUtil.getMessage("dialog.validateBuild.mapping.completeDatabaseInfo", "spider"), DialogUtil.MESSAGE_TYPE.WARN);
			return false;
		}
		
		if (configSpider.getTech("Database Type") == null || configSpider.getTech("Database Type").equals("")) {
			DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.validateBuild.title", "spider"), PropertiesUtil.getMessage("dialog.validateBuild.mapping.chooseDatabaseType", "spider"), DialogUtil.MESSAGE_TYPE.WARN);
			return false;	
		}
		
		TreeSelection treeSelection = (TreeSelection)treeViewer.getSelection();
		MappingFolderNode folderNode = (MappingFolderNode)treeSelection.getFirstElement();
		
		if (folderNode == null || folderNode.getPackage() == null || folderNode.getPackage().equals("")) {
			DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.validateBuild.title", "spider"), PropertiesUtil.getMessage("dialog.validateBuild.mapping.choosePackage", "spider"), DialogUtil.MESSAGE_TYPE.WARN);
			return false;	
		}
		
		return true;
	}
	
	public void onOpen() {}

	public void setEnabledBuild(boolean enabled) {
		buttonBuild.setEnabled(enabled);		
	}
*/
}
