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

import java.util.List;

import br.com.capanema.kers.common.model.project.ProjectConfig;
import br.com.capanema.kers.common.model.template.Crud;
import br.com.capanema.kers.common.model.template.CrudField;
import br.com.capanema.kers.core.build.BuildManager;

public class CrudManualPage implements Page {
	
  private ProjectConfig configSpider;
	
	public CrudManualPage (ProjectConfig configSpider) {
		this.configSpider = configSpider;
	}
	
	public void buildAllCRUDs() throws Exception {  
	  BuildManager buildManager = new BuildManager(this.configSpider);
    buildManager.buildCRUDs(this.configSpider.getCruds());
	}
	
	public void buildCRUD(Crud crud) throws Exception {
	  BuildManager buildManager = new BuildManager(this.configSpider);
    buildManager.buildCRUDs(this.configSpider.getCruds());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*public void build(String event) {
    if (event == "buttonBuild") {
      // if (validateBuild()) {
      // busca a classe do CRUD
      String crudClassName = "crudTableViewer.getCrudClass().getFullName()";
      // procura o CRUD pela classe
      Crud crud = CrudUtil.findCrudByClass(crudClassName, null);

      BuildManager buildManager = new BuildManager();
      buildManager.buildOneCRUD(crud); // s� gera 1 crud
      // }
    } else if (event == "buttonBuildAll") {
      if (validateBuildAll()) {
        BuildManager buildManager = new BuildManager();
        buildManager.buildAllCRUDs();
      }
    }
  }*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
  /*public void handleEvent(String event) {
    if (event == "buttonBuild") {
      // savePage(); //save configs
      // if (validateBuild()) {
      // busca a classe do CRUD
      String crudClassName = "crudTableViewer.getCrudClass().getFullName()";
      // procura o CRUD pela classe
      Crud crud = CrudUtil.findCrudByClass(crudClassName, null);

      BuildManager buildManager = new BuildManager();
      buildManager.buildOneCRUD(crud); // s� gera 1 crud
      // }
    } else if (event == "buttonBuildAll") {
      // savePage(); //save configs
      if (validateBuildAll()) {
        BuildManager buildManager = new BuildManager();
        buildManager.buildAllCRUDs();
      }
    } else if (event == "buttonLayout") {
      // DialogUtil.openSyncDialog(crudLayoutDialog);
    } else if (event == "listTable") {
      // savePage(); //salva o crud atual, se existir algum
      // processSelectionCrud();
    }
  }*/
  //private List listTable;
  /**guarda o nome do CRUD*/
  //private String textName;
  /**guarda as keys para cadas desc do crud type*/
  //private Map<String, String> crudTypeKeys = new HashMap<String, String>();
  //private Button buttonBuild, buttonBuildAll, buttonLayout;
  //private Combo comboCrudType, comboCrudModule;
  //private CrudTableViewer crudTableViewer;
  /**dialog de altera o layout do CRUD*/
  //private CrudLayoutDialog crudLayoutDialog = new CrudLayoutDialog(SpiderPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell());
  //private FormToolkit toolkit;
	
	
	/*public Composite getPage(Composite parent) {	
		toolkit = new FormToolkit(parent.getDisplay());
		final ScrolledForm form = toolkit.createScrolledForm(parent);
		form.setText(PropertiesUtil.getMessage("editor.title", "spider"));
		form.getBody().setLayout(new FillLayout());
		
		Action helpAction = new Action() {
			@Override
			public void run() {
				IContext context = HelpSystem.getContext("org.j2eespider.crudPageContext");
        		PlatformUI.getWorkbench().getHelpSystem().displayHelpResource(context.getRelatedTopics()[0].getHref());
			}
		};
		helpAction.setText(PropertiesUtil.getMessage("page.action.help", "spider"));
		helpAction.setImageDescriptor(SpiderPlugin.getImageDescriptor("images/icon_help.png"));
		form.getToolBarManager().add(helpAction);
		form.getToolBarManager().update(true);
		
		Composite composite = toolkit.createComposite(form.getBody(), SWT.NONE);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, "org.j2eespider.crudPageContext"); //context of help
		composite.setLayout(new MigLayout("wrap 1")); 
	    
		// setup bold font
		Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
		
		//description
		Label l3 = toolkit.createLabel(composite, PropertiesUtil.getMessage("page.crud.manual.description", "spider"), SWT.WRAP);
		l3.setFont(boldFont);
	    
		createCrudAttributeSection(form, composite);

		//table
		createTableViewer(form, composite);
	
		//config
        Group groupConfig = new Group(composite, SWT.NONE);
        MigLayout layoutGroupConfig = new MigLayout("wrap 5, fillx", "[90px:90px:90px][17%:17%:18%][80px:80px:80px][17%:17%:18%][40%:50%:50%]", "[]");
        groupConfig.setLayout(layoutGroupConfig);
        groupConfig.setLayoutData("grow, w 90%:parent.x-75, wrap, gapleft 15px, gapright 60px"); //tive que fixar a largura m�xima do group pq tava redimencionando
        groupConfig.setBackground(new Color(Display.getDefault(),255,255,255));
		
		//crud type
		Label lCrudType = toolkit.createLabel(groupConfig, PropertiesUtil.getMessage("page.crud.type", "spider"), SWT.WRAP);
		createComboCrudType(groupConfig);
		
		//module
		if (ConfigurationEditor.getCurrentTemplate().getDisabledFeatures() == null || !ConfigurationEditor.getCurrentTemplate().getDisabledFeatures().contains(Template.DISABLED_FEATURE_CRUD_MODULE)) {
			Label lCrudModule = toolkit.createLabel(groupConfig, PropertiesUtil.getMessage("page.crud.module", "spider"), SWT.WRAP);
			createComboCrudModule(groupConfig);
		} else {
			new Label(groupConfig, SWT.WRAP);
			new Label(groupConfig, SWT.WRAP);
		}	
	
		//composite buttons
		Composite compositeButtons = toolkit.createComposite(composite, SWT.NULL);
		compositeButtons.setLayout(new GridLayout(3, false));
	
		//button layout
		if (ConfigurationEditor.getCurrentTemplate().getDisabledFeatures() == null || !ConfigurationEditor.getCurrentTemplate().getDisabledFeatures().contains(Template.DISABLED_FEATURE_CRUD_LAYOUT)) {
			Image imageLayout = new Image(compositeButtons.getDisplay(), getClass().getResourceAsStream("/images/icon_next.png"));
		    buttonLayout = toolkit.createButton(compositeButtons,PropertiesUtil.getMessage("page.crud.button.layout", "spider"), SWT.PUSH);
		    buttonLayout.setImage(imageLayout);
		    buttonLayout.addListener(SWT.Selection, listener);	
		}
   
	    //button build this
		Image imageBuild = new Image(compositeButtons.getDisplay(), getClass().getResourceAsStream("/images/icon_build.png"));
	    buttonBuild = toolkit.createButton(compositeButtons,PropertiesUtil.getMessage("page.crud.button.buildThis", "spider"), SWT.PUSH);
	    buttonBuild.setImage(imageBuild);
		buttonBuild.addListener(SWT.Selection, listener);
		
		//button build all
	    buttonBuildAll = toolkit.createButton(compositeButtons,PropertiesUtil.getMessage("page.crud.button.buildAll", "spider"), SWT.PUSH);
	    buttonBuildAll.setImage(imageBuild);
	    buttonBuildAll.addListener(SWT.Selection, listener);	    
		
	    return form;
	}*/

	/*private void createCrudAttributeSection(final ScrolledForm form, Composite composite) {
		Section crudAttributesSection = toolkit.createSection(composite, Section.TWISTIE | Section.EXPANDED | Section.TITLE_BAR);
		crudAttributesSection.setText(PropertiesUtil.getMessage("page.crud.cruds", "spider"));
		crudAttributesSection.setActiveToggleColor(new Color(Display.getDefault(), 255,0,0));
		crudAttributesSection.addExpansionListener(new ExpansionAdapter() {
			  public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			  }
			 });
		crudAttributesSection.setLayout(new FillLayout());
		crudAttributesSection.setLayoutData("grow, wmax 97%");
		
		Composite crudAttributesComposite = toolkit.createComposite(crudAttributesSection, SWT.NONE);
		crudAttributesComposite.setLayout(new MigLayout("wrap 5", "[100px:100px][150px:150px][100px:100px][][8px]"));
		crudAttributesSection.setClient(crudAttributesComposite);
		
        //label name
		Label lblName = toolkit.createLabel(crudAttributesComposite, PropertiesUtil.getMessage("page.crud.name", "spider"), SWT.WRAP);
		lblName.setLayoutData("grow, top, wmax 100px");
		
		//field name
		textName = toolkit.createText(crudAttributesComposite, "", SWT.SINGLE | SWT.BORDER);
		textName.setLayoutData("growx, top, wmin 150px, wmax 150px"); //redimenciona o text
		textName.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {}

			public void keyReleased(KeyEvent arg0) {
				listTable.setItem(listTable.getSelectionIndex(), textName.getText());
			}
		});
		
        //label cruds
		Label lblCruds = toolkit.createLabel(crudAttributesComposite, PropertiesUtil.getMessage("page.crud.cruds", "spider"), SWT.WRAP);
		lblCruds.setLayoutData("right, top, wmax 100px, gapright 10px");		
		
	    //Lists
		listTable = new List(crudAttributesComposite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		listTable.addListener(SWT.Selection, listener);
		listTable.setLayoutData("hmin 65px, hmax 65px, wmin 30%, wmax 50%"); //redimenciona o list
		
		//button new attribute
		Image imageCrud = new Image(crudAttributesComposite.getDisplay(), getClass().getResourceAsStream("/images/icon_add.png"));
		Button btnNewCrud = toolkit.createButton(crudAttributesComposite, "", SWT.PUSH);
		btnNewCrud.setImage(imageCrud);
		btnNewCrud.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				listTable.add("new");
				listTable.setSelection(listTable.getItemCount()-1);
				textName.setText("new");
				loadTable();
			}
		});			
		btnNewCrud.setLayoutData("align left, top");
	}
	
	private void createComboCrudType(Composite composite) {
		try {
			java.util.List<String> types = ConfigurationEditor.getCurrentTemplate().getCrudTypeConfig();
			Properties properties = PropertiesUtil.loadPropertiesTemplate();
			
			comboCrudType = new Combo(composite, SWT.SINGLE | SWT.BORDER);
			comboCrudType.setLayoutData("grow, wmax 17%");
			for (String type : types) {
				crudTypeKeys.put(PropertiesUtil.getProperty(properties, type), type);
				comboCrudType.add(PropertiesUtil.getProperty(properties, type));
			}
			comboCrudType.select(0); //default, por�m existe um evento que troca esse index quando carrega um crud
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/
	
	/*private void createComboCrudModule(Composite composite) {
		comboCrudModule = new Combo(composite, SWT.SINGLE | SWT.BORDER);
		comboCrudModule.setLayoutData("grow, wmax 17%");
		refreshComboModule();
	}*/
	
	/*public void refreshComboModule() {
		try {
			java.util.List<String> listModuleName = ConfigurationEditor.getConfigSpider().getModulesName();
			
			comboCrudModule.removeAll();
			
			for (String moduleName : listModuleName) {
				comboCrudModule.add(moduleName);
			}
			comboCrudModule.select(0); //default, por�m existe um evento que troca esse index quando carrega um crud
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/
	
	/**
	 * Carrega o List de CRUDs e ainda seleciona um item.
	 * @param annotation
	 * @param selectedIndex
	 */
	/*private void loadListTables(int selectedIndex) {
		//clear combo
		if (listTable.getItemCount() > 0) {
			listTable.removeAll();
		}
		
		//add class in combo
		for (Crud crud : ConfigurationEditor.getConfigSpider().getCruds()) {
			listTable.add(crud.getName());
		}
		
		if (selectedIndex >= 0) {
			listTable.setSelection(selectedIndex);
		}
	}*/
	
	/**
	 * Get Tables List
	 * @param annotation
	 */
	/*private void loadListTables() {
		loadListTables(-1);
	}*/	
	
	/**
	 * Load table with attribute selected 
	 */
	/*private void loadTable() {
		CrudClass crudClass = null;
		//mudou de classe, entao cria um novo crudclass para o tableviewer
		if (crudTableViewer.getCrudClass() == null || !crudTableViewer.getCrudClass().getFullName().equals(textName.getText())) {
			Crud crud = CrudUtil.findCrudByClass(textName.getText()); //verifica se esse crud j� existe
			if (crud == null || crud.getCrudClass() == null) {
				crudClass = new CrudClass(textName.getText());
			} else {
				crudClass = crud.getCrudClass();
				crudClass.syncFields(); //sync CrudFields / Class Fields (remove old fields)
			}
			
			crudTableViewer.changeInput(crudClass);
		}
		
		
		//isso vai adicionar o field somente se ele ainda n�o existir (se for novo)
		//TODO: adicionar o atributo no grid
		//crudTableViewer.addField(classFileSelected.getFullyQualifiedName(), field.getElementName(), typeName);
	}*/
	
	/**
	 * Load table with attribute selected 
	 */
	/*private void removeFieldFromTable() {
		//TODO: remover campo do grid
		//crudTableViewer.removeField(classFileSelected.getFullyQualifiedName(), field.getElementName(), typeName);
	}*/
	
	/**
	 * Set crud name in textfield
	 * @param className
	 */
	/*private void loadCrudName(String classPath) {
		Crud crud = CrudUtil.findCrudByClass(classPath);
		if (crud != null) {
			textName.setText(crud.getName());
			return;
		}
		
		//n�o encontrou o name, seta com base no nome da classe
		String name = CrudUtil.getClassNameFromClassPath(classPath);
		textName.setText(name);
	}*/
	
	/**
	 * Set crud type in combo
	 * @param className
	 */
	/*private void loadCrudType(String classPath) {
		Crud crud = CrudUtil.findCrudByClass(classPath);
		if (crud != null) {
			String crudType = crud.getCrudType();
			if (crudType == null) {comboCrudType.select(0); return;} //n�o tem crud type ainda
			try {
				Properties properties = PropertiesUtil.loadPropertiesTemplate();
				String descCrudType = PropertiesUtil.getProperty(properties, crudType);
				int i=0;
				for (String item : comboCrudType.getItems()) {
					if (item.equals(descCrudType)) {
						comboCrudType.select(i);
						break;
					}
					i++;
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			comboCrudType.select(0); return; //n�o tem crud ainda
		}
	}*/
	
	/**
	 * Set crud module in combo
	 * @param className
	 */
	/*private void loadCrudModule(String classPath) {
		Crud crud = CrudUtil.findCrudByClass(classPath);
		if (crud != null) {
			String moduleName = crud.getModuleName();
			if (moduleName == null) {comboCrudModule.select(0); return;} //n�o tem crud type ainda

			int i=0;
			for (String item : comboCrudModule.getItems()) {
				if (item.equals(moduleName)) {
					comboCrudModule.select(i);
					break;
				}
				i++;
			}
		} else {
			comboCrudModule.select(0); return; //n�o tem crud ainda
		}
	}*/
	
	/**
	 * Listener of the buttons of the view
	 */
    /*Listener listener = new Listener() {
        public void handleEvent(Event event) {
        	if (event.widget == buttonBuild) {
        		savePage(); //save configs
        		if (validateBuild()) {
            		//busca a classe do CRUD
            		String crudClassName = crudTableViewer.getCrudClass().getFullName();
            		//procura o CRUD pela classe
            		Crud crud = CrudUtil.findCrudByClass(crudClassName);
            		
            		BuildManager buildManager = new BuildManager();
            		buildManager.buildOneCRUD(crud); //s� gera 1 crud	
        		}
        	} else if (event.widget == buttonBuildAll) {
        		savePage(); //save configs
        		if (validateBuildAll()) {
            		BuildManager buildManager = new BuildManager();
            		buildManager.buildAllCRUDs();	
        		}
        	} else if (event.widget == buttonLayout) {
        		DialogUtil.openSyncDialog(crudLayoutDialog);
        	} else if (event.widget == listTable) {
        		savePage(); //salva o crud atual, se existir algum
        		processSelectionCrud();
        	}
        }
    };*/
    
    /**
     * Processa a selecao de um novo CRUD.
     */
    /*private void processSelectionCrud() {
		String selectedCrud = listTable.getItem(listTable.getSelectionIndex());
		loadCrudName(selectedCrud); //carrega o nome do crud se ele existir (se j� estava salvo)
		loadCrudType(selectedCrud);
		if (ConfigurationEditor.getCurrentTemplate().getDisabledFeatures() == null || !ConfigurationEditor.getCurrentTemplate().getDisabledFeatures().contains(Template.DISABLED_FEATURE_CRUD_MODULE)) {
			loadCrudModule(selectedCrud);
		}
		loadTable(); //trocou de crud, recarrega o table
		configDialogs(); //trocou de crud, configura os dialogs
    }   */ 
    
    /**
     * Retorna as annotations que j� est�o no POJO.
     * @param selectedClass
     * @throws JavaModelException 
     */
   /* private Map<String, String[]> getPojoValidators(String selectedClass) {
    	Map<String, String[]> annotationsInPojo = new HashMap<String, String[]>();
    	try {
    		//search class
    		ResolvedSourceType classFile = ClassSearchUtil.searchExactJavaFile(selectedClass);
    		
			IMethod[] methods = classFile.getMethods();
			for (IMethod imethod : methods) {
				String[] annotationsInMethod = AnnotationUtil.getAnnotationsInMethod(imethod, ValidatorUtil.getValidators()); //search in imethod any annotation of this code template
				annotationsInPojo.put(imethod.getElementName(), annotationsInMethod); //monta map de annotations
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return annotationsInPojo;
    }*/
    
    /**
     * Compara as valida��es (do POJO), e a configurada no SPIDER.
     * @param annotationsInPojo
     */
    /*private boolean hasEqualsAnnotationsFromPojoAndSpider(Map<String, String[]> annotationsInPojo) {
    	java.util.List<CrudField> fields = crudTableViewer.getCrudClass().getFields();
    	for (CrudField field : fields) {
    		//validator in crud
    		java.util.List<CrudFieldValidator> validators = field.getValidators();
    		ArrayList<String> listValidators = new ArrayList<String>();
    		if (validators != null) {
        		for (CrudFieldValidator validator : validators) {
        			ValidatorType validatorType = validator.getValidatorType();
        			listValidators.add(validatorType.getClassName());
        		}		
    		}
    		
    		//validators in pojo
    		String methodName = "get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
    		String[] annotationsInMethod = annotationsInPojo.get(methodName);
    		ArrayList<String> listValidatorsInPojo = new ArrayList<String>();
    		
    		//compare pojo to spider (verifica se a validacao existe no pojo e n existe no spider)
    		if (annotationsInMethod != null) {
        		for (String command : annotationsInMethod) {
        			if (command != null && !listValidators.contains(command)) {
        				System.out.println("valida��o nova ou nao gerada:" + command);
        				return false;
        			}
        			listValidatorsInPojo.add(command);
        		}
    		}
    		
    		//compare spider to pojo (verifica se a validacao deixou de existir no pojo)
    		for (String command : listValidators) {
    			if (command != null && !listValidatorsInPojo.contains(command)) {
    				System.out.println("valida��o deixou de existir:" + command);
    				return false;
    			}
    		}
    	}
    	
    	return true;
    }*/
    
    /**
     * Abre um CRUD para edi��o.
     */
    /*public void editCrud(String crudName) {
    	loadListTables(); //carrega os CRUDs
    	
    	Crud crud = CrudUtil.findCrudByName(crudName);    	
    	if (crud == null) { //n�o encontrou o crud
    		ConfigurationEditor.getInstance().refreshOutline(); //provavelmente o outline precisa de refresh
    		return;
    	}
    	
    	//seleciona a classe
    	//TODO: selecionar crud.getCrudClass().getFullName()

    	
    	//processSelectionClass();
    }
    
    *//**
     * Remove um CRUD
     *//*
    public void removeCrud(String crudName) {
		java.util.List<Crud> cruds = ConfigurationEditor.getConfigSpider().getCruds();
		java.util.List<Crud> crudsNew = new ArrayList<Crud>();
		for (Crud crud : cruds) {
			if (!crud.getName().equals(crudName)) {
				crudsNew.add(crud);
			}
		}
		
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider(); //nao precisa do clone porque eu quero salvar
		configSpider.setCruds(crudsNew);
		
		//save config if change
		ConfigurationEditor.saveConfigSpider(configSpider, false); //com isso (false) vai salvar sempre os cruds
		
		ConfigurationEditor.getInstance().refreshOutline(); //atualiza o outline
    }

    *//**
     * 	Configures all dialogs, using data that were safe. 
     *//*
    private void configDialogs() {
		Crud crud = CrudUtil.findCrudByClass(textName.getText());
		
		if (crud != null) {
			crudLayoutDialog.setHtmlLayout(crud.getHtmlLayout());
		}
    }*/
    
	/*public void savePage() {
		if (crudTableViewer.getCrudClass() == null) { //n�o tem crud definido ainda
			return;
		}
		
		//busca a classe do CRUD que est� sendo salva
		String crudClassName = crudTableViewer.getCrudClass().getFullName();
		
		//procura para ver se esse crud j� est� salvo com algum nome
		Crud crud = CrudUtil.findCrudByClass(crudClassName);
		java.util.List<Crud> cruds = ConfigurationEditor.getConfigSpider().getCruds();
		if (crud == null && crudTableViewer.getCrudClass().getFields().size() > 0) { //se esse crud n�o estava salvo e tem fields, cria um novo e adiciona na lista de cruds
			crud = new Crud();
			cruds.add(crud);
		}
		
		if (crud != null) { //se encontrou ou criou algum crud novo
			crud.setName(textName.getText());
			crud.setHtmlLayout(crudLayoutDialog.getHtmlLayout());
			int index = comboCrudType.getSelectionIndex();
			String descCrudType = comboCrudType.getItem(index);
			crud.setCrudType(crudTypeKeys.get(descCrudType)); //seta a key do crud type
			
			if (ConfigurationEditor.getCurrentTemplate().getDisabledFeatures() == null || !ConfigurationEditor.getCurrentTemplate().getDisabledFeatures().contains(Template.DISABLED_FEATURE_CRUD_MODULE)) {
				index = comboCrudModule.getSelectionIndex();
				String moduleName = comboCrudModule.getItem(index);
				crud.setModuleName(moduleName);
			}
			
			crud.setCrudClass(crudTableViewer.getCrudClass()); //pega o crudClass do grid (table)
		}
		
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider(); //nao precisa do clone porque eu quero salvar ....clone();
		configSpider.setCruds(cruds);
		
		//save config if change
		ConfigurationEditor.saveConfigSpider(configSpider, false); //com isso (false) vai salvar sempre os cruds
		
		ConfigurationEditor.getInstance().refreshOutline(); //troca o outline pq pode ter acabado de salvar um novo crud
	}*/

	/*private void createTableViewer(final ScrolledForm form, Composite composite) {
		crudTableViewer = new CrudTableViewer(form, composite, true);
	}*/

	/**
	 * Performs validation of CRUD before build.
	 */
	//public boolean validateBuild(Crud crud) {
		//busca a classe do CRUD
		//String crudClassName = this.configSpider.getccrudTableViewer.getCrudClass().getFullName();
		//procura o CRUD pela classe
		//Crud crud = CrudUtil.findCrudByClass(crudClassName, this.configSpider);
		
		//return validateCrud(crud);
//	}
	
	/**
	 * Performs validation of all CRUDs before build.
	 */
	public boolean validateBuildAll() {
		
	  List<Crud> cruds = configSpider.getCruds(); //todo os cruds desse projeto
		
		for (Crud crud : cruds) {
			if (!validateCrud(crud)) {
				return false;
			}
		}		
		
		return true;
	}
	
	/**
	 * Internal Validation Rules for CRUD.
	 * @param crud
	 * @return
	 */
	private boolean validateCrud(Crud crud) {
		boolean primaryKeyValid = false;
		
		for (CrudField field : crud.getCrudClass().getFields()) {
			if (field.isPrimaryKey()) {
				primaryKeyValid = true;
				break;
			}
		}
		
		if (!primaryKeyValid) {
			//DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.validateBuild.title", "spider"), PropertiesUtil.getMessage("dialog.validateBuild.crud.primaryKey", new String[] {crud.getName()}, "spider"), DialogUtil.MESSAGE_TYPE.WARN);
			return false;
		}
		
		return true;
	}
	
}