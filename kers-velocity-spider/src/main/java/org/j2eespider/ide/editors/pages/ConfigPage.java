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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*import net.miginfocom.swt.MigLayout;

import org.eclipse.help.HelpSystem;
import org.eclipse.help.IContext;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
*/import org.j2eespider.SpiderPlugin;
import org.j2eespider.ide.data.domain.ConfigSpider;
import org.j2eespider.ide.data.domain.PluginData;
import org.j2eespider.ide.data.domain.PluginRequired;
import org.j2eespider.ide.data.domain.RepositoryTemplateInfo;
import org.j2eespider.ide.editors.ConfigurationEditor;
import org.j2eespider.ide.editors.pages.PageName.PAGE_NAME;
import org.j2eespider.util.ClassSearchUtil;
import org.j2eespider.util.DialogUtil;
import org.j2eespider.util.EditorUtil;
import org.j2eespider.util.PluginVersionUtil;
import org.j2eespider.util.PropertiesUtil;

public class ConfigPage implements Page {

	public void setEnabledBuild(boolean enabled) {
		// TODO Auto-generated method stub
		
	}

	public boolean validateBuild() {
		// TODO Auto-generated method stub
		return false;
	}

	public void savePage() {
		// TODO Auto-generated method stub
		
	}

	public void onOpen() {
		// TODO Auto-generated method stub
		
	}

	public org.j2eespider.ide.editors.pages.PageName.PAGE_NAME getPageName() {
		// TODO Auto-generated method stub
		return null;
	}
	/*private static Map<String, ConfigPage> instance = new HashMap<String, ConfigPage>();
	private Combo comboLanguage;
	private Combo comboTemplateCode;
	private Text textPathMerge, textDbUsername, textDbPassword, textDbHost, textDbPort, textDbName;
	
	public final static String VARIABLE_TOOL_MERGE = "spider.pathToolMerge";
	public final static String VARIABLE_TEMPLATES = "spider.pathTemplates";
	
	private PAGE_NAME pageName;
	
	private ConfigPage () {}
	
	private ConfigPage (PAGE_NAME pageName) {
		this.pageName = pageName;
	}
	
	public static ConfigPage getInstance(PAGE_NAME pageName) {
		String projectName = ConfigurationEditor.getConfigSpider().getAppName();		
		
		if (instance.get(projectName) == null) {
			instance.put(projectName, new ConfigPage(pageName));
		}
		
		return instance.get(projectName);
	}
	
	*//**
	 * Here it is created the layout of the page of configuration of the plugin.
	 * bruno.braga
	 *//*
	public Composite getPage(final Composite parent) {	
		
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		final ScrolledForm form = toolkit.createScrolledForm(parent);
		form.setText(PropertiesUtil.getMessage("editor.title", "spider"));
		form.getBody().setLayout(new FillLayout());
		
		Action helpAction = new Action() {
			@Override
			public void run() {
				IContext context = HelpSystem.getContext("org.j2eespider.configPageContext");
        		PlatformUI.getWorkbench().getHelpSystem().displayHelpResource(context.getRelatedTopics()[0].getHref());
			}
		};
		helpAction.setText(PropertiesUtil.getMessage("page.action.help", "spider"));
		helpAction.setImageDescriptor(SpiderPlugin.getImageDescriptor("images/icon_help.png"));
		form.getToolBarManager().add(helpAction);
		form.getToolBarManager().update(true);
		
		Composite composite = toolkit.createComposite(form.getBody(), SWT.NONE);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, "org.j2eespider.configPageContext"); //context of help
        composite.setLayout(new GridLayout());
		
		//setup bold font
		Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
		
		//Language and Code Template Section
		createTemplateLanguageSelection(toolkit, form, composite, boldFont);
		
		// Path of the user Tool Merge preferred.
		mergePreferenceSection(parent, toolkit, form, composite);
	    
		// Database config
		Section databaseConfigSection = toolkit.createSection(composite, Section.TWISTIE | Section.EXPANDED | Section.DESCRIPTION|Section.TITLE_BAR);
		databaseConfigSection.setText(PropertiesUtil.getMessage("page.config.database.group", "spider"));
		databaseConfigSection.setDescription(PropertiesUtil.getMessage("page.config.database.description", "spider"));
		databaseConfigSection.setActiveToggleColor(new Color(Display.getDefault(), 255,0,0));
		databaseConfigSection.addExpansionListener(new ExpansionAdapter() {
			  public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			  }
			 });
		databaseConfigSection.setLayout(new FillLayout());
		databaseConfigSection.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		
		Composite databaseConfigComposite = toolkit.createComposite(databaseConfigSection, SWT.NONE);
		databaseConfigComposite.setLayout(new MigLayout("wrap 2, fillx", "[90px][]", "[]"));
		databaseConfigSection.setClient(databaseConfigComposite);
	    
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider();
		
		//db name
		toolkit.createLabel(databaseConfigComposite, PropertiesUtil.getMessage("page.config.database.name", "spider"), SWT.WRAP);
		textDbName = toolkit.createText(databaseConfigComposite, "", SWT.SINGLE | SWT.BORDER);
		textDbName.setLayoutData("grow, wmax 50%");
		if (configSpider.getDbName() != null) {
			textDbName.setText(configSpider.getDbName());
		}
		
		//db username
		toolkit.createLabel(databaseConfigComposite, PropertiesUtil.getMessage("page.config.database.username", "spider"), SWT.WRAP);
		textDbUsername = toolkit.createText(databaseConfigComposite, "", SWT.SINGLE | SWT.BORDER);
		textDbUsername.setLayoutData("grow, wmax 50%");
		if (configSpider.getDbUsername() != null) {
			textDbUsername.setText(configSpider.getDbUsername());
		}
		
		//db password
		toolkit.createLabel(databaseConfigComposite, PropertiesUtil.getMessage("page.config.database.password", "spider"), SWT.WRAP);
		textDbPassword = toolkit.createText(databaseConfigComposite, "", SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
		textDbPassword.setLayoutData("grow, wmax 50%");
		if (configSpider.getDbPassword() != null) {
			textDbPassword.setText(configSpider.getDbPassword());
		}
		
		
		//db host
		toolkit.createLabel(databaseConfigComposite, PropertiesUtil.getMessage("page.config.database.host", "spider"), SWT.WRAP);
		textDbHost = toolkit.createText(databaseConfigComposite, "", SWT.SINGLE | SWT.BORDER);
		textDbHost.setLayoutData("grow, wmax 50%");
		if (configSpider.getDbHost() != null) {
			textDbHost.setText(configSpider.getDbHost());		
		}
		
		//db port
		toolkit.createLabel(databaseConfigComposite, PropertiesUtil.getMessage("page.config.database.port", "spider"), SWT.WRAP);
		textDbPort = toolkit.createText(databaseConfigComposite, "", SWT.SINGLE | SWT.BORDER);
		textDbPort.setLayoutData("grow, wmax 50%, split 2");
		textDbPort.setText(String.valueOf(configSpider.getDbPort()));
		
		toolkit.createLabel(databaseConfigComposite, PropertiesUtil.getMessage("page.config.database.port.default", "spider"), SWT.WRAP);		
		
		return form;
	}

	private void mergePreferenceSection(final Composite parent, FormToolkit toolkit, final ScrolledForm form, Composite composite) {
		Section mergePreferenceSection = toolkit.createSection(composite, Section.TWISTIE | Section.EXPANDED | Section.DESCRIPTION|Section.TITLE_BAR);
		mergePreferenceSection.setText(PropertiesUtil.getMessage("page.config.toolMerge.group", "spider"));
		mergePreferenceSection.setDescription(PropertiesUtil.getMessage("page.config.toolMerge.description", "spider"));
		mergePreferenceSection.setActiveToggleColor(new Color(Display.getDefault(), 255,0,0));
		mergePreferenceSection.addExpansionListener(new ExpansionAdapter() {
			  public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			  }
			 });
		mergePreferenceSection.setLayout(new FillLayout());
		mergePreferenceSection.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		
		Composite mergePreferenceComposite = toolkit.createComposite(mergePreferenceSection, SWT.NONE);
		mergePreferenceComposite.setLayout(new MigLayout("wrap 2", "[50%][90px]", "[]"));
		mergePreferenceSection.setClient(mergePreferenceComposite);
		
		textPathMerge = toolkit.createText(mergePreferenceComposite, "", SWT.SINGLE | SWT.BORDER);
		textPathMerge.setLayoutData("grow, wmin 50%, wmax 50%");
	
		String variableMerge = SpiderPlugin.getDefault().getMergeToolPreference();
		if (variableMerge != null && !variableMerge.equals("")) {
			textPathMerge.setText(variableMerge);
		}
	
 		Image imageSearch = new Image(composite.getDisplay(), getClass().getResourceAsStream("/images/icon_search.png"));		
	    Button buttonSearch = toolkit.createButton(mergePreferenceComposite, PropertiesUtil.getMessage("page.config.toolMerge.select", "spider"), SWT.PUSH);
	    buttonSearch.setImage(imageSearch);
	    buttonSearch.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        FileDialog dlg = new FileDialog(parent.getShell(), SWT.OPEN);
	        String fn = dlg.open();
	        if (fn != null) {
	        	textPathMerge.setText(fn);
	        }
	      }
	    });
	    buttonSearch.setLayoutData("right");
	}

	private void createTemplateLanguageSelection(FormToolkit toolkit, final ScrolledForm form, Composite composite, Font boldFont) {
		Section languageSection = toolkit.createSection(composite, Section.TWISTIE | Section.EXPANDED | Section.DESCRIPTION|Section.TITLE_BAR);
		languageSection.setText(PropertiesUtil.getMessage("page.config.language.group", "spider"));
		languageSection.setDescription(PropertiesUtil.getMessage("page.config.language.description", "spider"));
		languageSection.setActiveToggleColor(new Color(Display.getDefault(), 255,0,0));
		languageSection.addExpansionListener(new ExpansionAdapter() {
			  public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			  }
			 });
		languageSection.setLayout(new FillLayout());
		languageSection.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		
		Composite languageComposite = toolkit.createComposite(languageSection, SWT.NONE);

		languageComposite.setLayout(new MigLayout("wrap 2", "[30%:30%:30%][30%:30%:30%]", "[]"));
		
		templateGroupCreation(toolkit, boldFont, languageComposite);	
		languageSection.setClient(languageComposite);
	}

	private void templateGroupCreation(FormToolkit toolkit, Font boldFont, Composite languageComposite) {
		//label language
		Label l = toolkit.createLabel(languageComposite, PropertiesUtil.getMessage("page.config.language", "spider"), SWT.WRAP);
		l.setFont(boldFont);
		
		//label template
		Label l2 = toolkit.createLabel(languageComposite, PropertiesUtil.getMessage("page.config.template", "spider"), SWT.WRAP);
		l2.setFont(boldFont);
		
		//combo language
		comboLanguage = new Combo(languageComposite, SWT.SINGLE | SWT.BORDER);
		for (String codeLang : ConfigurationEditor.getCurrentTemplate().getLanguages()) {
			comboLanguage.add(PropertiesUtil.getMessage("page.config.language."+codeLang, "spider"));
		}
		//set selected
		int indexLanguage = ConfigurationEditor.getCurrentTemplate().getLanguages().indexOf(ConfigurationEditor.getConfigSpider().getLanguage());
		comboLanguage.select(indexLanguage);
		comboLanguage.addListener(SWT.Modify, listener);
		comboLanguage.setLayoutData("grow, wmin 30%, wmax 30%");
	    
		//combo template
		int indexTemplate = -1;
		int i = 0;
		comboTemplateCode = new Combo(languageComposite, SWT.SINGLE | SWT.BORDER);
		for (RepositoryTemplateInfo templateSummary : ConfigurationEditor.getFilteredTemplateInfo()) {
			comboTemplateCode.add(templateSummary.getName());
			if (templateSummary.getFolder().equals(ConfigurationEditor.getConfigSpider().getTemplateFolder())) {
				indexTemplate = i;
			}
			i++;
		}
		//set selected
		if (indexTemplate != -1) {
			comboTemplateCode.select(indexTemplate);
		}
		
		comboTemplateCode.addListener(SWT.Modify, listener);
		comboTemplateCode.setLayoutData("grow, wmax 30%, left");
	}
	
	*//**
	 * Listener of the buttons of the view
	 *//*
    Listener listener = new Listener() {
        public void handleEvent(Event event) {
        	if (event.widget == comboLanguage) {
        		changeLanguage();
        	} else if (event.widget == comboTemplateCode) {
        		changeTemplate();
        	}
        }
    };
    
    *//**
     * Check plug-in dependencies for this code template.
     *//*
    public void checkPluginDependencies() {
    	if (ConfigurationEditor.getCurrentTemplate() == null || comboTemplateCode.getSelectionIndex() == -1) {
    		DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.message.title", "spider"), PropertiesUtil.getMessage("page.config.selectTemplate", "spider"), DialogUtil.MESSAGE_TYPE.INFO);
    		setEnabledBuild(false);
    		return;
    	}
    	
    	List<PluginRequired> requiredPlugins = ConfigurationEditor.getCurrentTemplate().getRequiredPlugins();
    	if (requiredPlugins != null && requiredPlugins.size() > 0) { //check plug-ins dependencies
    		List<PluginData> plugins = PluginVersionUtil.getPlugins();
    		
    		req: for (PluginRequired pluginRequired : requiredPlugins) {
    			for (PluginData pluginData : plugins) {
    				if (pluginRequired.getId().equals(pluginData.getId()) && PluginVersionUtil.isMinimumVersion(pluginRequired.getMinimumVersion(), pluginData.getVersion())) {
    					continue req;
    				}
    			}
    			
    			DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.validatePlugins.title", "spider"), PropertiesUtil.getMessage("dialog.validatePlugins.required", new String[] {ConfigurationEditor.getCurrentTemplate().getFolder(), pluginRequired.getShortName(), pluginRequired.getMinimumVersion(), pluginRequired.getUrl()}, "spider"), DialogUtil.MESSAGE_TYPE.WARN);
    			setEnabledBuild(false);
    			return;
    		}
    		setEnabledBuild(true);
    	}
    }
    
    *//**
     * Enable / Disable build of all pages
     *//*
    public void setEnabledBuild(boolean enabled) {
    	List<Page> pages = ConfigurationEditor.getInstance().getPages();
    	for (Page page : pages) {
    		if (page.getPageName() != PAGE_NAME.Config) {
    			page.setEnabledBuild(enabled);
    		}
    	}
    }
    
    *//**
     * Save and change template (re-open editor).
     *//*
    private void changeTemplate() {
    	//---save changes
		savePage(true, true);
		
		//---reopen editor
		EditorUtil.reOpenEditor(ConfigurationEditor.fileConfigSpider);
    }
    
    *//**
     * Save and change language (re-open editor).
     *//*
    private void changeLanguage() {
    	//save new language
		String strLanguage = ConfigurationEditor.getCurrentTemplate().getLanguages().get(comboLanguage.getSelectionIndex());
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider().clone();
		configSpider.setLanguage(strLanguage);
		ConfigurationEditor.saveConfigSpider(configSpider, true);
		
		EditorUtil.reOpenEditor(ConfigurationEditor.fileConfigSpider);
    }
    
	public void savePage() {
		savePage(false, false);
	}
    
	public void savePage(boolean clearLayout, boolean setSourceFolder) {		
		//set language
		String strLanguage = ConfigurationEditor.getCurrentTemplate().getLanguages().get(comboLanguage.getSelectionIndex());
		
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider().clone();
		configSpider.setLanguage(strLanguage);
		
		//set code template
		RepositoryTemplateInfo templateInfo = ConfigurationEditor.getFilteredTemplateInfo().get(comboTemplateCode.getSelectionIndex());
		configSpider.setTemplateFolder(templateInfo.getFolder());
		
		//database
		configSpider.setDbHost(textDbHost.getText());
		configSpider.setDbName(textDbName.getText());
		configSpider.setDbPassword(textDbPassword.getText());
		configSpider.setDbPort(Integer.valueOf(textDbPort.getText()));
		configSpider.setDbUsername(textDbUsername.getText());
		
		//unset layout
		if (clearLayout) {
			configSpider.setTechLayout(null);
		}
		
		//set source folder
		if (setSourceFolder || configSpider.getSourceFolder() == null) {
			String sourceFolder = "";
			if (templateInfo.getTechGroup().equals("JAVA") || templateInfo.getTechGroup().equals("JEE")) {
				try {
					sourceFolder = ClassSearchUtil.getJavaSources().get(0);
					if (sourceFolder == null) {sourceFolder = "/JavaSource";}
				} catch (Exception e) {
					sourceFolder = "/JavaSource";
				}
			}
			configSpider.setSourceFolder(sourceFolder);	
		}
		
		//save config if change
		ConfigurationEditor.saveConfigSpider(configSpider, true);
		
		//reload template if it change
		if (!ConfigurationEditor.getCurrentTemplate().getFolder().equals(configSpider.getTemplateFolder())) {
			ConfigurationEditor.loadTemplate();	
		}
		
		//The tool of merge is a personal option of the developer, then stayed recorded variable of his environment of work (Eclipse)... 
		//Each developer be able to have the tool of merge in a folder
		SpiderPlugin.getDefault().setMergeToolPathPreference(textPathMerge.getText());		
	}

	public PAGE_NAME getPageName() {
		return this.pageName;
	}

	public boolean validateBuild() {
		return true;
	}
	
	public void onOpen() {}*/
}
