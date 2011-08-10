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

import java.util.HashMap;
import java.util.Map;

import br.com.capanema.kers.common.model.project.ProjectConfig;
import br.com.capanema.kers.core.build.BuildManager;

public class LayoutPage implements Page {

  private ProjectConfig configSpider;
	
	public LayoutPage (ProjectConfig configSpider) {
	  this.configSpider = configSpider;
	}

	public void build() throws Exception {
    // if (validateBuild()) {
    BuildManager buildManager = new BuildManager(this.configSpider);
    buildManager.buildLayout();
    // }
  }

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
	/*public void handleEvent(String event) {
    if (event == "buttonBuild") {
      //savePage(); //save configs
      //if (validateBuild()) {
          BuildManager buildManager = new BuildManager(this.configSpider);
          buildManager.buildLayout(); 
      //}
    } 
    else if (event.widget == buttonNextLayout) {
      loadLayout(1);
    } else if (event.widget == buttonPreviousLayout) {
      loadLayout(-1);
    }
  }*/
	
  private static Map<String, LayoutPage> instance = new HashMap<String, LayoutPage>();
	/*public static LayoutPage getInstance(PAGE_NAME pageName) {
		String projectName = ConfigurationEditor.getConfigSpider().getAppName();
		
		if (instance.get(projectName) == null) {
			instance.put(projectName, new LayoutPage(pageName));
		}
		ConfigurationEditor.loadTemplate(); //reload template - in this page it's used to load changes in colors or preview images
		
		return instance.get(projectName);
	}
	*/
	
	
	
	
//private Button buttonBuild;
  //private Button buttonLayout;
  //private Button buttonNextLayout;  
  //private Button buttonPreviousLayout;
  //private Button buttonHelp;
  
  //private ColorSelector colorOne;
  //private ColorSelector colorTwo;
  //private ColorSelector colorThree;
  //private ColorSelector colorText;
  //private Canvas canvasPreview;
  //private Label labelLayoutName;
  //private Label labelLayoutDescription;
  //private Group groupColor;
  //private Composite compositeLayout;
  
  //private int indexCurrentLayout = 0;
  
  //private List<SimpleSiteLayout> listSiteLayoutSelected;
  
  //private Button optionTechLayout[];
  
  //private PAGE_NAME pageName;
	
	
	
	/**
	 * Here it is created the layout of the page of configuration of the plugin.
	 * bruno.braga
	 */
	/*public Composite getPage(final Composite parent) {
		//isso n�o pode estar no construtor, porque sen�o s� vai ser carregado 1 vez (singleton), altera��es no arquivo XML n�o v�o ser reconhecidas
		//TODO: dessa forma est� criando clone toda hora que ocorre o getPage, arrumar solu��o para isso
		listSiteLayoutSelected = ConfigurationEditor.getConfigSpider().getSiteLayoutClone();
		if (listSiteLayoutSelected == null) {
			listSiteLayoutSelected = new ArrayList<SimpleSiteLayout>();
		}
		
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		final ScrolledForm form = toolkit.createScrolledForm(parent);
		form.setText(PropertiesUtil.getMessage("editor.title", "spider"));
		form.getBody().setLayout(new FillLayout());
		
		Action helpAction = new Action() {
			@Override
			public void run() {
				IContext context = HelpSystem.getContext("org.j2eespider.layoutPageContext");
        		PlatformUI.getWorkbench().getHelpSystem().displayHelpResource(context.getRelatedTopics()[0].getHref());
			}
		};
		helpAction.setText(PropertiesUtil.getMessage("page.action.help", "spider"));
		helpAction.setImageDescriptor(SpiderPlugin.getImageDescriptor("images/icon_help.png"));
		form.getToolBarManager().add(helpAction);
		form.getToolBarManager().update(true);
		
		Composite composite = toolkit.createComposite(form.getBody(), SWT.NONE);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, "org.j2eespider.layoutPageContext"); //context of help
        composite.setLayout(new GridLayout());
		
		// setup bold font
		Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);

		
		// Section Layout
		Section layoutConfigSection = toolkit.createSection(composite, Section.TWISTIE | Section.EXPANDED | Section.DESCRIPTION|Section.TITLE_BAR);
		layoutConfigSection.setText(PropertiesUtil.getMessage("page.layout.group", "spider"));
		layoutConfigSection.setActiveToggleColor(new Color(Display.getDefault(), 255,0,0));
		layoutConfigSection.addExpansionListener(new ExpansionAdapter() {
			  public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			  }
			 });
		layoutConfigSection.setLayout(new FillLayout());
		layoutConfigSection.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		
		compositeLayout = toolkit.createComposite(layoutConfigSection, SWT.NONE);
	    compositeLayout.setLayout(new MigLayout("insets 0, wrap 3")); //insets 0 tira a margem
		layoutConfigSection.setClient(compositeLayout);
		

		
		Group groupLayout = new Group(compositeLayout, SWT.MULTI);
		groupLayout.setText(PropertiesUtil.getMessage("page.layout.group.description", "spider"));
		groupLayout.setLayout(new GridLayout(4, false));
		groupLayout.setLayoutData("grow, wmin 65%");
		
		GridData dataButtonLayout = new GridData(GridData.HORIZONTAL_ALIGN_CENTER | GridData.VERTICAL_ALIGN_CENTER);
		dataButtonLayout.verticalSpan = 2;
		
		buttonLayout = new Button(groupLayout, SWT.CHECK);
		buttonLayout.setLayoutData(dataButtonLayout);
		
		//preview image
	    canvasPreview = new Canvas(groupLayout, SWT.FILL);
	    
	    //name and description
	    toolkit.createLabel(groupLayout, PropertiesUtil.getMessage("page.layout.group.label.name", "spider"), SWT.WRAP);
		labelLayoutName = toolkit.createLabel(groupLayout, "", SWT.WRAP);
		
		//Label labelTitleLayoutDescription = toolkit.createLabel(groupLayout, PropertiesUtil.getMessage("page.layout.group.label.description", "spider"), SWT.WRAP);
		//labelTitleLayoutDescription.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING));
		labelLayoutDescription = toolkit.createLabel(groupLayout, "", SWT.WRAP);
		GridData dataDescLayout = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		dataDescLayout.horizontalSpan = 2;
		dataDescLayout.grabExcessHorizontalSpace = true;
		labelLayoutDescription.setLayoutData(dataDescLayout);
		
	    //next layout
		GridData dataButtonPrevious = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_END);
		GridData dataButtonNext = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_END);
		dataButtonNext.horizontalSpan = 2;
		
		Image imagePrevious = new Image(composite.getDisplay(), getClass().getResourceAsStream("/images/icon_previous.png"));
		buttonPreviousLayout = new Button(groupLayout, SWT.FLAT);
		buttonPreviousLayout.setLayoutData(dataButtonPrevious);
		buttonPreviousLayout.setImage(imagePrevious);
		buttonPreviousLayout.addListener(SWT.Selection, listener);
		
		Image imageNext = new Image(composite.getDisplay(), getClass().getResourceAsStream("/images/icon_next.png"));
		buttonNextLayout = new Button(groupLayout, SWT.FLAT);
		buttonNextLayout.setLayoutData(dataButtonNext);
		buttonNextLayout.setImage(imageNext);
		buttonNextLayout.addListener(SWT.Selection, listener);
		
	    //--color selection		
		groupColor = new Group(compositeLayout, SWT.MULTI);
		groupColor.setLayout(new MigLayout("wrap 2"));
		groupColor.setLayoutData("top, wmin 185px, h 100%");

		toolkit.createLabel(groupColor, PropertiesUtil.getMessage("page.layout.colors.first", "spider"), SWT.WRAP);
		colorOne = new ColorSelector(groupColor);
		
		toolkit.createLabel(groupColor, PropertiesUtil.getMessage("page.layout.colors.second", "spider"), SWT.WRAP);
		colorTwo = new ColorSelector(groupColor);
		
		toolkit.createLabel(groupColor, PropertiesUtil.getMessage("page.layout.colors.third", "spider"), SWT.WRAP);
		colorThree = new ColorSelector(groupColor);
		
		toolkit.createLabel(groupColor, PropertiesUtil.getMessage("page.layout.colors.text", "spider"), SWT.WRAP);
		colorText = new ColorSelector(groupColor);
		
		//load first layout of code template
		loadLayout(0);
		
		new Label(composite, SWT.NONE); //hack: invisible component
		
		//Tech Layout Section
		createTechLayoutSection(parent, toolkit, form, composite);
		
		Image imageBuild = new Image(composite.getDisplay(), getClass().getResourceAsStream("/images/icon_build.png"));
	    buttonBuild = toolkit.createButton(composite, PropertiesUtil.getMessage("page.button.build", "spider"), SWT.PUSH);
	    buttonBuild.setImage(imageBuild);
	    buttonBuild.addListener(SWT.Selection, listener);

		return form;
	}
	
	private void createTechLayoutSection(final Composite parent, FormToolkit toolkit, final ScrolledForm form, Composite composite) {			
		Section techLayoutSection = toolkit.createSection(composite, Section.TWISTIE | Section.EXPANDED | Section.TITLE_BAR);
		techLayoutSection.setText(PropertiesUtil.getMessage("page.layout.framework.group", "spider"));
		techLayoutSection.setActiveToggleColor(new Color(Display.getDefault(), 255,0,0));
		techLayoutSection.addExpansionListener(new ExpansionAdapter() {
			  public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			  }
			 });
		techLayoutSection.setLayout(new FillLayout());
		techLayoutSection.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		
		Composite techLayoutComposite = toolkit.createComposite(techLayoutSection, SWT.NONE);
		techLayoutComposite.setLayout(new RowLayout(SWT.VERTICAL));
		techLayoutSection.setClient(techLayoutComposite);
		
		
		int ind = 0;		
		optionTechLayout = new Button[ConfigurationEditor.getCurrentTemplate().getTechLayout().size()];
		for (String techLayout : ConfigurationEditor.getCurrentTemplate().getTechLayout().keySet()) {
			optionTechLayout[ind] = new Button(techLayoutComposite, SWT.RADIO);
			optionTechLayout[ind].setText(techLayout);
			
			optionTechLayout[ind].setEnabled(false);
			
			if (ConfigurationEditor.getConfigSpider().getTechLayout() != null && ConfigurationEditor.getConfigSpider().getTech("MVC").equals(techLayout)) {
				optionTechLayout[ind].setSelection(true);
			}

			ind++;
		}
		enableTechLayout(ConfigurationEditor.getConfigSpider().getTech("MVC"));
	}
	
	*//**
	 * Enable or disable tech layout based on TechMVC.
	 *//*
	public void enableTechLayout(String techMVC) {
		int ind = 0;
		for (String techLayout : ConfigurationEditor.getCurrentTemplate().getTechLayout().keySet()) {
			List<String> listAvailable = ConfigurationEditor.getCurrentTemplate().getTechLayout().get(techLayout);
			if (listAvailable.contains(techMVC)) {
				optionTechLayout[ind].setEnabled(true);
				optionTechLayout[ind].setSelection(true);
			} else {
				optionTechLayout[ind].setEnabled(false);
				optionTechLayout[ind].setSelection(false);
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
        		savePage(); //save configs
        		if (validateBuild()) {
            		BuildManager buildManager = new BuildManager();
            		buildManager.buildLayout();	
        		}
        	} else if (event.widget == buttonNextLayout) {
        		loadLayout(1);
        	} else if (event.widget == buttonPreviousLayout) {
        		loadLayout(-1);
        	}
        }
    };
    
    *//**
     * Load next or previous Layout.
     * @param increment
     *//*
    public void loadLayout(int increment) {
    	if (listSiteLayoutSelected == null) { //if null, not yet entered in method getPage
    		return;
    	}
    	
    	//update layout data, (selected / deselect)
    	updateLayoutSelected();

    	//change current index of layout
    	int tmpCurrentLayout = indexCurrentLayout + increment;
    	if (tmpCurrentLayout >= 0 && tmpCurrentLayout <= ConfigurationEditor.getCurrentTemplate().getSiteLayout().size()-1) {
    		indexCurrentLayout = indexCurrentLayout + increment;
    	}

    	SiteLayout siteLayout = ConfigurationEditor.getCurrentTemplate().getSiteLayout().get(indexCurrentLayout);
    	List<LayoutColor> layoutColor = siteLayout.getLayoutColor();
    	
    	//set checkbox value
    	buttonLayout.setData(siteLayout.getName());
    	if (getSelectedLayout(siteLayout.getName()) == null) { //if this layout will not be selected
    		buttonLayout.setSelection(false);
    	} else {
    		buttonLayout.setSelection(true);
    	}
    	
    	//set image preview
    	//TODO: fazer a troca de imagem de acordo com as configura��es de cores
    	//TODO: se a imagem abaixo n�o existir, vai dar erro. Exibir um alerta com uma mensagem explicando o problema, e fechar o editor
    	String previewImgPath = BuildManagerUtil.getPathFolderTemplates()+File.separator+ConfigurationEditor.getCurrentTemplate().getFolder()+layoutColor.get(0).getImage();
    	File previewFile = new File(previewImgPath);
    	final Image imageLayout;
    	if (!previewFile.exists()) {
			ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(SpiderPlugin.ID, "images/preview_error.png");
			imageLayout = imageDescriptor.createImage();
    	} else {
        	imageLayout = new Image(canvasPreview.getDisplay(), previewImgPath);
    	}
    	
	    canvasPreview.setSize(imageLayout.getImageData().width, imageLayout.getImageData().height);
	    canvasPreview.addPaintListener(new PaintListener() {
	        public void paintControl(PaintEvent e) {
	        	e.gc.drawImage(imageLayout, 0, 0);
	        }
	    });
		GridData dataGroup2 = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING);
		dataGroup2.verticalSpan = 3;
	    dataGroup2.widthHint = imageLayout.getImageData().width;
	    dataGroup2.heightHint = imageLayout.getImageData().height;
	    canvasPreview.setLayoutData(dataGroup2);
	    
	    //set name and description
	    labelLayoutName.setText(siteLayout.getName());
		//load template bundle
	    try {
			Properties properties = PropertiesUtil.loadPropertiesTemplate();
			labelLayoutDescription.setText(PropertiesUtil.getProperty(properties, siteLayout.getKeyDescription()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    //set color description
		groupColor.setText(PropertiesUtil.getMessage("page.layout.colors", "spider")+siteLayout.getName());
		
		//set colors
		setColor(siteLayout, layoutColor);
		
	    //check buttons
	    if (indexCurrentLayout == 0) {
	    	buttonPreviousLayout.setEnabled(false);
	    } else if (indexCurrentLayout == ConfigurationEditor.getCurrentTemplate().getSiteLayout().size()-1) {
	    	buttonNextLayout.setEnabled(false);
	    }

    	if (indexCurrentLayout > 0) {
	    	buttonPreviousLayout.setEnabled(true);
    	}
    	if (indexCurrentLayout < ConfigurationEditor.getCurrentTemplate().getSiteLayout().size()-1) {
	    	buttonNextLayout.setEnabled(true);
	    }

    	canvasPreview.redraw();
	    compositeLayout.redraw();
    }
     
    public void setIndexCurrentLayout(int indexCurrentLayout) {
		this.indexCurrentLayout = indexCurrentLayout;
	}

	*//**
     * Set Color fields, with layoutColor or selectedColor
     * @param siteLayout
     * @param layoutColor
     *//*
    private void setColor(SiteLayout siteLayout, List<LayoutColor> layoutColor) {
    	String layoutNameSelected = (String)buttonLayout.getData();
    	SimpleSiteLayout siteLayoutSelected = getSelectedLayout(layoutNameSelected);
    	
		if (siteLayout.isSetFirstColor()) {
			if (siteLayoutSelected != null) {
				colorOne.setColorValue(siteLayoutSelected.getRGBFirstColor());
			} else {
				colorOne.setColorValue(layoutColor.get(0).getRGBFirstColor());
			}
			colorOne.setEnabled(true);
		} else {
			colorOne.setColorValue(colorOne.getButton().getBackground().getRGB());
			colorOne.setEnabled(false);
		}
		
		if (siteLayout.isSetSecondColor()) {
			if (siteLayoutSelected != null) {
				colorTwo.setColorValue(siteLayoutSelected.getRGBSecondColor());
			} else {
				colorTwo.setColorValue(layoutColor.get(0).getRGBSecondColor());
			}
			colorTwo.setEnabled(true);
		} else {
			colorTwo.setColorValue(colorTwo.getButton().getBackground().getRGB());
			colorTwo.setEnabled(false);
		}
		
		if (siteLayout.isSetThirdColor()) {
			if (siteLayoutSelected != null) {
				colorThree.setColorValue(siteLayoutSelected.getRGBThirdColor());
			} else {
				colorThree.setColorValue(layoutColor.get(0).getRGBThirdColor());
			}
			colorThree.setEnabled(true);
		} else {
			colorThree.setColorValue(colorThree.getButton().getBackground().getRGB());
			colorThree.setEnabled(false);
		}
		
		if (siteLayout.isSetTextColor()) {
			if (siteLayoutSelected != null) {
				colorText.setColorValue(siteLayoutSelected.getRGBTextColor());
			} else {
				colorText.setColorValue(layoutColor.get(0).getRGBTextColor());
			}
			colorText.setEnabled(true);
		} else {
			colorText.setColorValue(colorText.getButton().getBackground().getRGB());
			colorText.setEnabled(false);
		}
    }
    
    *//**
     * Return data of layout (config), if selected
     * @param layoutName
     * @return
     *//*
    private SimpleSiteLayout getSelectedLayout(String layoutName) {
    	SimpleSiteLayout siteLayout = null;

    	for (SimpleSiteLayout simpleSiteLayout : listSiteLayoutSelected) {
    		if (simpleSiteLayout.getName().equals(layoutName)) {
    			siteLayout = simpleSiteLayout;
    			break;
    		}
    	}
    	
    	return siteLayout;
    }
    
    *//**
     * Update List (variable) of Layouts Selected
     *//*
    private void updateLayoutSelected() {
    	String layoutNameSelected = (String)buttonLayout.getData();
    	
    	SimpleSiteLayout siteLayout = null;
    	int indLayout = 0;
    	for (SimpleSiteLayout simpleSiteLayout : listSiteLayoutSelected) {
    		if (simpleSiteLayout.getName().equals(layoutNameSelected)) {
    			siteLayout = simpleSiteLayout;
    			break;
    		}
    		indLayout++;
    	}    	
    	
    	if (buttonLayout.getSelection()) {
	    	String firstColor = ColorUtil.RGBtoHex(colorOne.getColorValue());
	    	String secondColor = ColorUtil.RGBtoHex(colorTwo.getColorValue());
	    	String thirdColor = ColorUtil.RGBtoHex(colorThree.getColorValue());
	    	String textColor = ColorUtil.RGBtoHex(colorText.getColorValue());
	    	
	    	if (siteLayout != null) {
	    		siteLayout.setFirstColor(firstColor);
	    		siteLayout.setSecondColor(secondColor);
	    		siteLayout.setThirdColor(thirdColor);
	    		siteLayout.setTextColor(textColor);
	    	} else {
	    		siteLayout = new SimpleSiteLayout(layoutNameSelected, firstColor, secondColor, thirdColor, textColor);
	    		listSiteLayoutSelected.add(siteLayout);
	    	}
    	} else if (siteLayout != null) {
    		listSiteLayoutSelected.remove(indLayout);
    	}
    }
    
	public void savePage() {
		//update info of current layout
		updateLayoutSelected();
		
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider().clone();
		
		//set site layout
		configSpider.setSiteLayout(listSiteLayoutSelected);
		
		//set layout framework
		configSpider.setTechLayout(getTechValue(optionTechLayout));
		
		//save config if change
		ConfigurationEditor.saveConfigSpider(configSpider, true);		
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
	
	public PAGE_NAME getPageName() {
		return this.pageName;
	}

	public boolean validateBuild() {
		
		if (listSiteLayoutSelected.size() == 0) {
			DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.validateBuild.title", "spider"), PropertiesUtil.getMessage("dialog.validateBuild.layout", "spider"), DialogUtil.MESSAGE_TYPE.WARN);
			return false;
		}

		return true;
	}
	
	public void onOpen() {}

	public void setEnabledBuild(boolean enabled) {
		buttonBuild.setEnabled(enabled);		
	}
*/}
