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
import br.com.capanema.kers.core.build.BuildManager;

public class TechPage implements Page {
	
  private ProjectConfig configSpider;

  public TechPage (ProjectConfig configSpider) {
		this.configSpider = configSpider;
	}
	
  public void build(String event) throws Exception {
    if (event == "buttonBuild") {
      // if (validateBuild()) {
      // savePage(); //save configs
      BuildManager buildManager = new BuildManager(this.configSpider);
      buildManager.buildTechConfig();
      // }
    }
  }
  
  
  
  
  //private Map<Integer, String> mapOptionToTech;
  //private Button[] optionTech[];
  //private org.eclipse.swt.widgets.List listTechDatabaseType;
  //private Button buttonBuild;
  //private Button buttonHelp;
  //private PAGE_NAME pageName;
  //private FormToolkit toolkit;

	/*public Composite getPage(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		final ScrolledForm form = toolkit.createScrolledForm(parent);
		form.setText(PropertiesUtil.getMessage("editor.title", "spider"));
		form.getBody().setLayout(new FillLayout());
		
		Action helpAction = new Action() {
			@Override
			public void run() {
				IContext context = HelpSystem.getContext("org.j2eespider.techPageContext");
        		PlatformUI.getWorkbench().getHelpSystem().displayHelpResource(context.getRelatedTopics()[0].getHref());
			}
		};
		helpAction.setText(PropertiesUtil.getMessage("page.action.help", "spider"));
		helpAction.setImageDescriptor(SpiderPlugin.getImageDescriptor("images/icon_help.png"));
		form.getToolBarManager().add(helpAction);
		form.getToolBarManager().update(true);
		
		Composite composite = toolkit.createComposite(form.getBody(), SWT.NONE);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, "org.j2eespider.techPageContext"); //context of help
		
		composite.setLayout(new GridLayout(3,true));  
		
		// setup bold font
		Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);    
	    
		Label l = toolkit.createLabel(composite, PropertiesUtil.getMessage("page.tech.description", "spider"), SWT.WRAP);
		l.setFont(boldFont);
		GridData gd = new GridData();
		gd.horizontalSpan = 3;
		l.setLayoutData(gd);
	
		
		//checkbox`s
		RowLayout layoutGroup = new RowLayout(SWT.VERTICAL);

	    
		//init options and number of buttons
		int maxButtons = 0;
		for (String key : ConfigurationEditor.getCurrentTemplate().getTech().keySet()) {
			//get the max number of option (exclude Database Type -> list)
			if (!key.equals("Database Type") && ConfigurationEditor.getCurrentTemplate().getTech().get(key).size() > maxButtons) {
				maxButtons = ConfigurationEditor.getCurrentTemplate().getTech().get(key).size();
			}
		}
		optionTech = new Button[ConfigurationEditor.getCurrentTemplate().getTech().size()][maxButtons];
		mapOptionToTech = new HashMap<Integer, String>(); //initializing
		
		int optionIndex = 0;
		for (String key : ConfigurationEditor.getCurrentTemplate().getTech().keySet()) {
			if (key.equals("Database Type")) {
				continue;
			}
			
			//blank space
			if (optionIndex % 3 == 0) {
				toolkit.createLabel(composite, "", SWT.NONE);
				toolkit.createLabel(composite, "", SWT.NONE);
				toolkit.createLabel(composite, "", SWT.NONE);
			}
			
			//get selected values
			List<String> selected = new ArrayList<String>();
			if (!key.equals("Others") && ConfigurationEditor.getConfigSpider().getTech(key) != null) {
				selected.add(ConfigurationEditor.getConfigSpider().getTech(key));
			} else if (key.equals("Others")) {
				selected = ConfigurationEditor.getConfigSpider().getTechOthers();
			}
			
			//set map (option to tech)
			this.mapOptionToTech.put(optionIndex, key);
			
			List templateOption = ConfigurationEditor.getCurrentTemplate().getTech().get(key);
			List templateSelected = ConfigurationEditor.getCurrentTemplate().getTechSelected().get(key);
			
			//create components
			createOptionSection(form, composite, templateOption, templateSelected, selected, layoutGroup, optionIndex);
			
			optionIndex++;
		}

		//database type  
		createDatabaseTypeSection(form, composite, ConfigurationEditor.getCurrentTemplate(), layoutGroup);
		
		//blank space to complete three columns
		optionIndex++;
		if (optionIndex % 2 == 0) {
			toolkit.createLabel(composite, "", SWT.NONE);
		} else if (optionIndex % 3 != 0 && optionIndex % 1 == 0) {
			toolkit.createLabel(composite, "", SWT.NONE);	
		}

		
		Image imageBuild = new Image(composite.getDisplay(), getClass().getResourceAsStream("/images/icon_build.png"));
	    buttonBuild = toolkit.createButton(composite, PropertiesUtil.getMessage("page.button.build", "spider"), SWT.PUSH);
	    buttonBuild.setImage(imageBuild);
	    buttonBuild.addListener(SWT.Selection, listener);
	    
	    return form;
	}
	
	private void createOptionSection(final ScrolledForm form, Composite composite, List<String> options, List<String> templateSelected, List<String> listSelected, RowLayout layoutGroup, int buttonIndex) {
		Section mvcGroupSection = toolkit.createSection(composite, Section.TWISTIE | Section.EXPANDED | Section.TITLE_BAR);
		mvcGroupSection.setText(mapOptionToTech.get(buttonIndex));
		mvcGroupSection.setActiveToggleColor(new Color(Display.getDefault(), 255,0,0));
		mvcGroupSection.addExpansionListener(new ExpansionAdapter() {
			  public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			  }
			 });
		mvcGroupSection.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		Composite mvcGroupComposite = toolkit.createComposite(mvcGroupSection, SWT.NONE);
		mvcGroupComposite.setLayout(layoutGroup);
		mvcGroupSection.setLayout(new FillLayout());
		mvcGroupSection.setClient(mvcGroupComposite);
		
		int buttonType = SWT.RADIO;
		if (mapOptionToTech.get(buttonIndex).equals("Others")) {
			buttonType = SWT.CHECK;
		}
		boolean isMVC = false;
		if (mapOptionToTech.get(buttonIndex).equals("MVC")) {
			isMVC = true;
		}
		
		int ind = 0;
		for (final String techOption : options) {
			optionTech[buttonIndex][ind] = new Button(mvcGroupComposite, buttonType);
			optionTech[buttonIndex][ind].setText(techOption);
			if (listSelected != null && listSelected.contains(techOption)) { //selected by user
				optionTech[buttonIndex][ind].setSelection(true);
			} else if ((listSelected == null || listSelected.size() == 0) && templateSelected != null && templateSelected.contains(techOption)) { //selected by template
				optionTech[buttonIndex][ind].setSelection(true);
			} else {
				optionTech[buttonIndex][ind].setSelection(false);
			}
			
			if (isMVC) {
				optionTech[buttonIndex][ind].addListener(SWT.Selection, new Listener () { //enable or disable tech layout based on tech mvc
					public void handleEvent (Event event) {
						LayoutPage layoutPage = (LayoutPage)ConfigurationEditor.getInstance().getPage(PAGE_NAME.Layout);
						layoutPage.enableTechLayout(techOption);
					}
				});	
			}

			//FIXME: somente para 1.0.0-M1
//			if (techName.equals("JSF")) {
//				optionTechMVC[ind].setEnabled(false);
//			}
			//----------------------------
			ind++;
		}
	}
	
	private void createDatabaseTypeSection(final ScrolledForm form, Composite composite, Template template, RowLayout layoutGroup) {
		Section databaseTypeSection = toolkit.createSection(composite, Section.TWISTIE | Section.EXPANDED | Section.TITLE_BAR);
		databaseTypeSection.setText(PropertiesUtil.getMessage("page.tech.databaseType.group", "spider"));
		databaseTypeSection.setActiveToggleColor(new Color(Display.getDefault(), 255,0,0));
		databaseTypeSection.addExpansionListener(new ExpansionAdapter() {
			  public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			  }
			 });
		databaseTypeSection.setLayout(new FillLayout());
		databaseTypeSection.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		
		Composite databaseTypeComposite = toolkit.createComposite(databaseTypeSection, SWT.NONE);
		databaseTypeComposite.setLayout(new MigLayout());
		databaseTypeSection.setClient(databaseTypeComposite);

		listTechDatabaseType = new org.eclipse.swt.widgets.List(databaseTypeComposite, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
		listTechDatabaseType.setLayoutData("grow, hmax 92px, wmin 85%, wmax 90%");
		int ind=0;
		for (String techName : template.getTech("Database Type")) {
			listTechDatabaseType.add(techName);
			if (ConfigurationEditor.getConfigSpider().getTech("Database Type") != null && ConfigurationEditor.getConfigSpider().getTech("Database Type").equals(techName)) {
				listTechDatabaseType.setSelection(ind);
				listTechDatabaseType.showSelection();
			}
			ind++;
		}	
	}
	
	
	*//**
	 * Listener of the buttons of the view
	 *//*
    Listener listener = new Listener() {
        public void handleEvent(Event event) {
        	
	        if (event.widget == buttonBuild) {
	        	if (validateBuild()) {
	        		savePage(); //save configs     		
	        		BuildManager buildManager = new BuildManager();
	        		buildManager.buildTech();
	        	}
	        }
          
        }
    };
    
    *//**
     * Return list of values of selected buttons
     * @param optionTech
     * @return
     *//*
    private List<String> getTechList(Button[] optionTech) {
    	List<String> techList = new ArrayList<String>();
    	if (optionTech == null || optionTech.length == 0) {return techList;}
    	
    	for (int i=0; i<optionTech.length; i++) {
		  Button button = ((Button)optionTech[i]);
		  if (button == null) {return techList;}
		  
		  boolean selectStatus = button.getSelection();
		  if (selectStatus) {
			  techList.add(button.getText());
		  }
    	}
    	
    	return techList;
    }
    
    *//**
     * Return values of selected button
     * @param optionTech
     * @return
     *//*
    private String getTechValue(Button[] optionTech) {
    	
    	for (int i=0; i<optionTech.length; i++) {
		  Button button = ((Button)optionTech[i]);
		  boolean selectStatus = button.getSelection();
		  if (selectStatus) {
			  return button.getText();
		  }
    	}
    	
    	return null;
    }

	public void savePage() {
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider().clone();
		Map<String, String> tech = configSpider.getTech();
		for (Integer optionIndex : mapOptionToTech.keySet()) {
			String techName = mapOptionToTech.get(optionIndex);
			if (techName.equals("Others")) {
				List<String> techListOthers = getTechList(optionTech[optionIndex]);
				configSpider.setTechOthers(techListOthers);
			} else {
				tech.put(techName, getTechValue(optionTech[optionIndex]));
			}
		}

		if (listTechDatabaseType.getSelection() != null && listTechDatabaseType.getSelection().length > 0) {
			tech.put("Database Type", listTechDatabaseType.getSelection()[0]);
		} 
		configSpider.setTech(tech);
		
		//save config if change
		ConfigurationEditor.saveConfigSpider(configSpider, true);	
	}
	
	public PAGE_NAME getPageName() {
		return this.pageName;
	}
	
	public boolean validateBuild() {
		if (listTechDatabaseType.getSelection() == null || listTechDatabaseType.getSelection().length == 0) {
			DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.validateBuild.title", "spider"), PropertiesUtil.getMessage("dialog.validateBuild.databaseType", "spider"), DialogUtil.MESSAGE_TYPE.WARN);
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
