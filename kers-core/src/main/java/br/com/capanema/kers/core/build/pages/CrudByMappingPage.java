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

import java.util.LinkedList;

import br.com.capanema.kers.common.model.project.ProjectConfig;
import br.com.capanema.kers.common.model.template.Crud;
import br.com.capanema.kers.common.util.PropertiesFileUtil;
import br.com.capanema.kers.core.build.BuildManager;


public class CrudByMappingPage implements Page {
  
  private ProjectConfig configSpider;
  
	
	public CrudByMappingPage (ProjectConfig configSpider) {
		this.configSpider = configSpider;
	}
	
	public void buildAllCRUDs() throws Exception {  
	  BuildManager buildManager = new BuildManager(this.configSpider);
    buildManager.buildCRUDs();
	}
	
	public void buildCRUD(Crud crud) throws Exception {
	  //String crudClassName = crudTableViewer.getCrudClass().getFullName();
    //procura o CRUD pela classe
    //Crud crud = CrudUtil.findCrudByClass(crudClassName);
    
    BuildManager buildManager = new BuildManager(this.configSpider);
    buildManager.buildCRUDs(); //s� gera 1 crud 
	}
	
	
	
	
	
	
	
	
	/*private static LinkedList<String> listAnnotationKey = new LinkedList<String>();
  private static LinkedList<String> listAnnotationDesc = new LinkedList<String>();
  
  public CrudByMappingPage (ProjectConfigSpider configSpider) {
    this.configSpider = configSpider;

    listAnnotationKey = new LinkedList<String>();
    listAnnotationDesc = new LinkedList<String>();
    listAnnotationKey.add("");
    listAnnotationKey.add("javax.persistence.Entity");
    listAnnotationKey.add("org.hibernate.annotations.Entity");
    listAnnotationDesc.add(PropertiesFileUtil.getMessage("page.crud.annotation.all", "spider"));
    listAnnotationDesc.add("ejb3 @Entity");
    listAnnotationDesc.add("hibernate @Entity");
  }*/

	/* 
	  public void handleEvent(String event) {
    if (event == "buttonBuild") {
      //savePage(); //save configs
      //if (validateBuild()) {
          //busca a classe do CRUD
          String crudClassName = crudTableViewer.getCrudClass().getFullName();
          //procura o CRUD pela classe
          Crud crud = CrudUtil.findCrudByClass(crudClassName);
          
          BuildManager buildManager = new BuildManager();
          buildManager.buildOneCRUD(crud); //s� gera 1 crud 
      //}
    } else if (event == "buttonBuildAll") {
      //savePage(); //save configs
      //if (validateBuildAll()) {
          BuildManager buildManager = new BuildManager();
          buildManager.buildAllCRUDs(); 
      //}
    } else if (event.widget == buttonLayout) {
      DialogUtil.openSyncDialog(crudLayoutDialog);
    } else if (event.widget == buttonRefresh) { 
      refreshComboAnnotationNow();
    } else if (event.widget == comboAnnotation) {
      crudTableViewer.changeInput(null); //limpa o grid
      savePage(); //salva o crud atual, se existir algum
      String key = listAnnotationKey.get(comboAnnotation.getSelectionIndex());
      loadListClass(key);
    } else if (event.widget == listClass) {
      savePage(); //salva o crud atual, se existir algum
      processSelectionClass();
    } else if (event.widget == listField) {
      controlFieldSelectionIndex(listField.getSelectionIndex());
      listField.deselectAll();
      listField.select(getFieldSelectionIndex());
      loadTable();
      savePage(); //salva o crud atual, se existir algum
    } else if (event.widget == buttonAllAttr) {
      if (listField.getItemCount() == 0) {return;} //n�o tem nada para selecionar
      
      //ainda n�o tem nda selecionado
      if (indexSelectionField.size() == 0) {
        controlFieldAddSelectAll();
      } 
      //j� tem algo selecionado mas n�o tudo
      else if (indexSelectionField.size() > 0 && listField.getItemCount() != indexSelectionField.size()) {
        controlFieldAddSelectAll();
      }
      //tudo est� selecionado
      else if (listField.getItemCount() == indexSelectionField.size()) {
        controlFieldRemoveSelectAll();
      }
      
      //reload selections
      listField.deselectAll();
      listField.select(getFieldSelectionIndex());
      loadTable();
      savePage(); //salva o crud atual, se existir algum
    }
  }
	
*/
	

  //private static Map<String, CrudByMappingPage> instance = new HashMap<String, CrudByMappingPage>();
  //private List listClass, listField;
  //private Button buttonBuild, buttonBuildAll, buttonLayout, buttonRefresh, buttonAllAttr;
  //private Combo comboAnnotation, comboCrudType, comboCrudModule;
  /**guarda a lista de indexes seleciontados no listField*/
  //private ArrayList<Integer> indexSelectionField = new ArrayList<Integer>();
  //private CrudTableViewer crudTableViewer;
  /**guarda a listagem de classes exibidas no listClass*/
  //private LinkedList<ResolvedSourceType> listClassFile;
  /**guarda o nome do CRUD*/
  //private Text textName;
  /**dialog de altera o layout do CRUD*/
  //private CrudLayoutDialog crudLayoutDialog = new CrudLayoutDialog(SpiderPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell());
  /**guarda o ultimo classFile selecionado*/
  //private ResolvedSourceType classFileSelected;
  /**guarda as keys para cadas desc do crud type*/
  //private Map<String, String> crudTypeKeys = new HashMap<String, String>();
  //private PAGE_NAME pageName;
  //private boolean refreshComboAnnotation = false;
  //private FormToolkit toolkit;
	
/*
	
	public Composite getPage(Composite parent) {	
		
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
		Label l3 = toolkit.createLabel(composite, PropertiesUtil.getMessage("page.crud.mapping.description", "spider"), SWT.WRAP);
		l3.setFont(boldFont);
	    
		createCrudAttributeSection(form, composite);

		//table
		createTableViewer(form, composite);

		//config
        Group groupConfig = new Group(composite, SWT.NONE);
        MigLayout layoutGroupConfig = new MigLayout("wrap 7, fillx", "[90px:90px:90px][17%:17%:18%][80px:80px:80px][17%:17%:18%][60px:60px:60px][17%:17%:18%][1%:1%:50%]", "[]");
        groupConfig.setLayout(layoutGroupConfig);
        groupConfig.setLayoutData("grow, w 90%:parent.x-75, wrap, gapleft 15px, gapright 60px"); //tive que fixar a largura m�xima do group pq tava redimencionando
        groupConfig.setBackground(new Color(Display.getDefault(),255,255,255));
        //label name
		toolkit.createLabel(groupConfig, PropertiesUtil.getMessage("page.crud.name", "spider"), SWT.WRAP);

		//field name
		textName = toolkit.createText(groupConfig, "", SWT.SINGLE | SWT.BORDER);
		textName.setLayoutData("grow, wmax 17%"); //redimenciona o text
		
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
	}

	private void createCrudAttributeSection(final ScrolledForm form, Composite composite) {
		Section crudAttributesSection = toolkit.createSection(composite, Section.TWISTIE | Section.EXPANDED | Section.TITLE_BAR);
		crudAttributesSection.setText(PropertiesUtil.getMessage("page.crud.classAttributes", "spider"));
		crudAttributesSection.setActiveToggleColor(new Color(Display.getDefault(), 255,0,0));
		crudAttributesSection.addExpansionListener(new ExpansionAdapter() {
			  public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			  }
			 });
		crudAttributesSection.setLayout(new FillLayout());
		crudAttributesSection.setLayoutData("grow, wmax 97%");
		
		Composite crudAttributesComposite = toolkit.createComposite(crudAttributesSection, SWT.NONE);
		crudAttributesComposite.setLayout(new MigLayout("wrap 2", "[50%:50%:50%][]"));
		crudAttributesSection.setClient(crudAttributesComposite);
		
		
	    //annotation filter
		Label lAnnotations = toolkit.createLabel(crudAttributesComposite, PropertiesUtil.getMessage("page.crud.annotations.label", "spider"), SWT.WRAP);
		lAnnotations.setLayoutData("split 4");
		
		//combo annotation
	    createComboAnnotation(crudAttributesComposite);
	    
	    //button refresh
		Image imageRefresh = new Image(crudAttributesComposite.getDisplay(), getClass().getResourceAsStream("/images/icon_refresh.png"));
	    buttonRefresh = toolkit.createButton(crudAttributesComposite, "", SWT.PUSH);
	    buttonRefresh.setImage(imageRefresh);
	    buttonRefresh.addListener(SWT.Selection, listener);
    
	    //instructions of the screen	
		toolkit.createLabel(crudAttributesComposite, PropertiesUtil.getMessage("page.crud.list.descClass", "spider"), SWT.WRAP);	    
		
		//label attr and button select all	
		Label lDescAttr = toolkit.createLabel(crudAttributesComposite, PropertiesUtil.getMessage("page.crud.list.descAttr", "spider"), SWT.WRAP);
		lDescAttr.setLayoutData("split 2, grow, gaptop 6");
		
	    buttonAllAttr = toolkit.createButton(crudAttributesComposite, PropertiesUtil.getMessage("page.crud.button.selectAll", "spider"), SWT.PUSH);
	    buttonAllAttr.addListener(SWT.Selection, listener);

	    //Lists
		listClass = new List(crudAttributesComposite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		listClass.addListener(SWT.Selection, listener);
		listClass.setLayoutData("hmin 65px, hmax 65px, wmin 46%, wmax 50%"); //redimenciona o list
		
		//load list class
		String key = listAnnotationKey.get(comboAnnotation.getSelectionIndex());
		loadListClass(key);
		
		listField = new List(crudAttributesComposite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		listField.addListener(SWT.Selection, listener);
		listField.setLayoutData("hmin 65px, hmax 65px, wmin 44%, wmax 50%"); //redimenciona o list
	}

	private void createComboAnnotation(Composite composite) {
		comboAnnotation = new Combo(composite, SWT.SINGLE | SWT.BORDER);
		comboAnnotation.setLayoutData("grow, wmax 19%");
		for (String annotationDesc : listAnnotationDesc) {
			comboAnnotation.add(annotationDesc);
		}
		comboAnnotation.select(1);
		comboAnnotation.addListener(SWT.Selection, listener);
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
	}
	
	private void createComboCrudModule(Composite composite) {
		comboCrudModule = new Combo(composite, SWT.SINGLE | SWT.BORDER);
		comboCrudModule.setLayoutData("grow, wmax 17%");
		refreshComboModule();
	}
	
	public void refreshComboModule() {
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
	}
	
	*//**
	 * Carrega o List de Classes e ainda seleciona um item.
	 * @param annotation
	 * @param selectedIndex
	 *//*
	private void loadListClass(String annotation, int selectedIndex) {
		//clear combo
		if (listClass.getItemCount() > 0) {
			listClass.removeAll();
		}
		
		//filter
		ArrayList listAnnotations = new ArrayList();
		if (annotation != null && !annotation.equals("")) {
			listAnnotations.add(annotation);
		}
		
		//it fills the list with the present classes in the project
		listClassFile = ClassSearchUtil.searchJavaFiles(listAnnotations);
		
		//add class in combo
		for (ResolvedSourceType classFile : listClassFile) {
//			if (classFile.getAnnotation(Entity.class) != null) {
				listClass.add(classFile.getFullyQualifiedName());
//			}
		}
		
		if (listField != null && listField.isDisposed() == false) {
			//clear list field
			listField.removeAll();
		}
		
		if (selectedIndex >= 0) {
			listClass.setSelection(selectedIndex);
		}
	}
	
	*//**
	 * Carrega o List de Classes
	 * @param annotation
	 *//*
	private void loadListClass(String annotation) {
		loadListClass(annotation, -1);
	}
	
	private void loadListField() {
		//clear list
		listField.removeAll();
		if (listClass.getSelectionIndex() == -1) {return;} //n�o tem classe selecionada
		
		String selectedClass = listClass.getItem(listClass.getSelectionIndex());	
		//verifica quais campos ja estavam selecionados se esse CRUD existir
		Crud crud = CrudUtil.findCrudByClass(selectedClass);
		java.util.List<CrudField> savedCrudFields = null;
		if (crud != null) {
			savedCrudFields = crud.getCrudClass().getFields();
		}
		indexSelectionField = new ArrayList<Integer>(); //limpa os itens selecionados, para pegar os que estão salvos
		
		for (ResolvedSourceType classFile : listClassFile) { //procura a classe selecionada
			if (selectedClass.equals(classFile.getFullyQualifiedName())) {
				this.classFileSelected = classFile;
				
				try {
					int cont = 0;
					for (IField field : classFile.getFields()) {
						listField.add(field.getElementName());
						
						//seleciona os itens que ja estavam salvos anteriormente
						if (savedCrudFields != null) {
							for (CrudField crudField : savedCrudFields) {
								if (crudField.getName().equals(field.getElementName())) {
									listField.select(cont);
									addFieldSelectionIndex(cont); //inicializando a lista de itens selecionados
									break;
								}
							}
						}
						cont++;
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				break;
			}
		}
		
	}


	
	*//**
	 * Load table with attribute selected 
	 *//*
	private void loadTable() {
		CrudClass crudClass = null;
		//mudou de classe, entao cria um novo crudclass para o tableviewer
		if (crudTableViewer.getCrudClass() == null || !crudTableViewer.getCrudClass().getFullName().equals(classFileSelected.getFullyQualifiedName())) {
			Crud crud = CrudUtil.findCrudByClass(classFileSelected.getFullyQualifiedName()); //verifica se esse crud j� existe
			if (crud == null || crud.getCrudClass() == null) {
				crudClass = new CrudClass(classFileSelected.getFullyQualifiedName());
			} else {
				crudClass = crud.getCrudClass();
				crudClass.syncFields(); //sync CrudFields / Class Fields (remove old fields)
			}
			
			crudTableViewer.changeInput(crudClass);
		}
		
		String[] selection = listField.getSelection(); //pega o atributo selecionado
		for (String selectedField : selection) { //loop na sele��o
			try {
				for (IField field : classFileSelected.getFields()) {
					if (field.getElementName().equals(selectedField)) {
						String typeName = JavaModelUtil.getResolvedTypeName(field.getTypeSignature(), field.getDeclaringType());
						//isso vai adicionar o field somente se ele ainda n�o existir (se for novo)
						crudTableViewer.addField(classFileSelected.getFullyQualifiedName(), field.getElementName(), typeName);	
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	*//**
	 * Load table with attribute selected 
	 *//*
	private void removeFieldFromTable(int indexField) {
		String selectedField = listField.getItem(indexField); //pega o atributo clicado
		
		try {
			for (IField field : classFileSelected.getFields()) {
				if (field.getElementName().equals(selectedField)) {
					String typeName = JavaModelUtil.getResolvedTypeName(field.getTypeSignature(), field.getDeclaringType());
					crudTableViewer.removeField(classFileSelected.getFullyQualifiedName(), field.getElementName(), typeName);
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	*//**
	 * Set crud name in textfield
	 * @param className
	 *//*
	private void loadCrudName(String classPath) {
		Crud crud = CrudUtil.findCrudByClass(classPath);
		if (crud != null) {
			textName.setText(crud.getName());
			return;
		}
		
		//n�o encontrou o name, seta com base no nome da classe
		String name = CrudUtil.getClassNameFromClassPath(classPath);
		textName.setText(name);
	}
	
	*//**
	 * Set crud type in combo
	 * @param className
	 *//*
	private void loadCrudType(String classPath) {
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
	}
	
	*//**
	 * Set crud module in combo
	 * @param className
	 *//*
	private void loadCrudModule(String classPath) {
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
	}
	
	*//**
   * Listener of the buttons of the view
   *//*
    Listener listener = new Listener() {
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
          } else if (event.widget == buttonRefresh) { 
            refreshComboAnnotationNow();
          } else if (event.widget == comboAnnotation) {
            crudTableViewer.changeInput(null); //limpa o grid
            savePage(); //salva o crud atual, se existir algum
            String key = listAnnotationKey.get(comboAnnotation.getSelectionIndex());
            loadListClass(key);
          } else if (event.widget == listClass) {
            savePage(); //salva o crud atual, se existir algum
            processSelectionClass();
          } else if (event.widget == listField) {
            controlFieldSelectionIndex(listField.getSelectionIndex());
            listField.deselectAll();
            listField.select(getFieldSelectionIndex());
            loadTable();
            savePage(); //salva o crud atual, se existir algum
          } else if (event.widget == buttonAllAttr) {
            if (listField.getItemCount() == 0) {return;} //n�o tem nada para selecionar
            
            //ainda n�o tem nda selecionado
            if (indexSelectionField.size() == 0) {
              controlFieldAddSelectAll();
            } 
            //j� tem algo selecionado mas n�o tudo
            else if (indexSelectionField.size() > 0 && listField.getItemCount() != indexSelectionField.size()) {
              controlFieldAddSelectAll();
            }
            //tudo est� selecionado
            else if (listField.getItemCount() == indexSelectionField.size()) {
              controlFieldRemoveSelectAll();
            }
            
            //reload selections
            listField.deselectAll();
            listField.select(getFieldSelectionIndex());
            loadTable();
            savePage(); //salva o crud atual, se existir algum
          }
        }
    };
    
    *//**
     * Refresh classes of Combo Annotation.
     *//*
    private void refreshComboAnnotationNow() {
    	int iSelectClass = -1;
    	int iSelectAnnotation =  comboAnnotation.getSelectionIndex();

		String key = listAnnotationKey.get(iSelectAnnotation);

		loadListClass(key, iSelectClass);
		loadListField();
    }
    
    *//**
     * Schedule refresh of Combo Annotation.
     *//*
    public void refreshComboAnnotation() {
    	refreshComboAnnotation = true;
    }
    
    *//**
     * Processa a selecao de uma nova classe.
     *//*
    private void processSelectionClass() {
		String selectedClass = listClass.getItem(listClass.getSelectionIndex());
		loadCrudName(selectedClass); //carrega o nome do crud se ele existir (se j� estava salvo)
		loadCrudType(selectedClass);
		loadCrudModule(selectedClass);
		loadListField();
		loadTable(); //trocou de classe, recarrega o table
		configDialogs(); //trocou de classe, configura os dialogs
		Map<String, String[]> annotationsInPojo = getPojoValidators(selectedClass); //carrega as annotations de valida��o que j� existem no POJO
		if (!hasEqualsAnnotationsFromPojoAndSpider(annotationsInPojo)) {
			String classPath = crudTableViewer.getCrudClass().getFullName();
			String className = CrudUtil.getClassNameFromClassPath(classPath);
			DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.message.title", "spider"), PropertiesUtil.getMessage("dialog.crud.validator.changePojo", new String[] {className}, "spider"), DialogUtil.MESSAGE_TYPE.INFO);
		}
    }
    
    *//**
     * Retorna as annotations que j� est�o no POJO.
     * @param selectedClass
     * @throws JavaModelException 
     *//*
    private Map<String, String[]> getPojoValidators(String selectedClass) {
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
    }
    
    *//**
     * Compara as valida��es (do POJO), e a configurada no SPIDER.
     * @param annotationsInPojo
     *//*
    private boolean hasEqualsAnnotationsFromPojoAndSpider(Map<String, String[]> annotationsInPojo) {
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
    }
    
    *//**
     * Abre um CRUD para edi��o.
     *//*
    public void editCrud(String crudName) {
    	comboAnnotation.select(0); //seleciona o combo de todas as classes
    	loadListClass(""); //carrega as classes
    	
    	Crud crud = CrudUtil.findCrudByName(crudName);    	
    	if (crud == null) { //n�o encontrou o crud
    		ConfigurationEditor.getInstance().refreshOutline(); //provavelmente o outline precisa de refresh
    		return;
    	}
    	
    	//seleciona o listClass
    	int itemIndex = -1;
    	for (int i=0; i<listClass.getItemCount(); i++) {
    		String listValue = listClass.getItem(i);
    		if (listValue.equals(crud.getCrudClass().getFullName())) {
    			itemIndex = i;
    		}
    	}

    	if (itemIndex == -1) {
			InvalidCrudClassDialog dialog = new InvalidCrudClassDialog(ConfigurationEditor.shell, crudName, crud.getCrudClass().getFullName());
			DialogUtil.openSyncDialog(dialog);
			
			if (dialog.getReturnCode() == InvalidCrudClassDialog.SKIP_ID) {
				return;
			} else if (dialog.getReturnCode() == InvalidCrudClassDialog.EXCLUDE_ID) {
				crudTableViewer.changeInput(null);
				removeCrud(crudName);
				return;
			} else if (dialog.getReturnCode() == InvalidCrudClassDialog.CHANGE_ID) {
				crudTableViewer.changeInput(null);
				crud.getCrudClass().setFullName(dialog.getPathClass()); //altera a classe
				editCrud(crudName); //chama o editar novamente
				return;
			}
    	}
    	
    	listClass.select(itemIndex);
    	
    	processSelectionClass();
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
		String selectedClass = listClass.getItem(listClass.getSelectionIndex());	
		Crud crud = CrudUtil.findCrudByClass(selectedClass);
		
		if (crud != null) {
			crudLayoutDialog.setHtmlLayout(crud.getHtmlLayout());
		}
    }
	
    *//**
     * Add or Remove item (index) of indexSelectionField (list of items)
     * @param index
     *//*
    private void controlFieldSelectionIndex(int index) {
    	int cont = 0;
    	for (Integer i : indexSelectionField) {
    		if (i == index) {
    			//aviso de confirmacao
    			boolean confirm = MessageDialog.openConfirm(ConfigurationEditor.shell, PropertiesUtil.getMessage("dialog.confirmExcAttr.title", "spider"), PropertiesUtil.getMessage("dialog.confirmExcAttr.description", "spider"));
    			if (confirm) {
    				indexSelectionField.remove(cont);
    				//remove o item da table
    				removeFieldFromTable(index);
    			}
    			return;
    		}
    		cont++;
    	}
    	
    	indexSelectionField.add(index);
    }
    
    *//**
     * Add all items in indexSelectionField (list of items)
     * @param index
     *//*
    private void controlFieldAddSelectAll() {
    	item: for (int index=0; index < listField.getItemCount(); index++) {
	    	for (Integer i : indexSelectionField) {
	    		if (i == index) {
	    			continue item; //esse j� est� selecionado
	    		}
	    	}
	    	indexSelectionField.add(index); //ainda n�o estava selecionado
    	}
    }
    
    *//**
     * Remove all items of indexSelectionField (list of items)
     * @param index
     *//*
    private void controlFieldRemoveSelectAll() {
		//aviso de confirmacao
		boolean confirm = MessageDialog.openConfirm(ConfigurationEditor.shell, PropertiesUtil.getMessage("dialog.confirmExcAttr.title", "spider"), PropertiesUtil.getMessage("dialog.confirmExcAllAttr.description", "spider"));
		if (confirm) {
	    	for (Integer index : indexSelectionField) {
				//remove o item da table
				removeFieldFromTable(index);
	    	}
			
			indexSelectionField.clear();
		}
    }
    
    *//**
     * Only add Field to selection
     * @param index
     *//*
    private void addFieldSelectionIndex(int index) {
    	for (Integer i : indexSelectionField) {
    		if (i == index) {
    			return;
    		}
    	}
    	
    	indexSelectionField.add(index);
    }
    
    private int[] getFieldSelectionIndex() {
    	int[] data = new int[this.indexSelectionField.size()];
    	int i = 0;
    	for (Integer index : indexSelectionField) {
    		data[i] = index;
    		i++;
    	}
    	
    	return data;
    }
    
	public void savePage() {
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
			
			index = comboCrudModule.getSelectionIndex();
			String moduleName = comboCrudModule.getItem(index);
			crud.setModuleName(moduleName);
			
			crud.setCrudClass(crudTableViewer.getCrudClass()); //pega o crudClass do grid (table)
		}
		
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider(); //nao precisa do clone porque eu quero salvar ....clone();
		configSpider.setCruds(cruds);
		
		//save config if change
		ConfigurationEditor.saveConfigSpider(configSpider, false); //com isso (false) vai salvar sempre os cruds
		
		ConfigurationEditor.getInstance().refreshOutline(); //troca o outline pq pode ter acabado de salvar um novo crud
	}

	public PAGE_NAME getPageName() {
		return this.pageName;
	}

	private void createTableViewer(final ScrolledForm form, Composite composite) {
		crudTableViewer = new CrudTableViewer(form, composite, false);
	}

	*//**
	 * Performs validation of CRUD before build.
	 *//*
	public boolean validateBuild() {
		//busca a classe do CRUD
		String crudClassName = crudTableViewer.getCrudClass().getFullName();
		//procura o CRUD pela classe
		Crud crud = CrudUtil.findCrudByClass(crudClassName);
		
		return validateCrud(crud);
	}
	
	*//**
	 * Performs validation of all CRUDs before build.
	 *//*
	public boolean validateBuildAll() {
		ConfigSpider configSpider = ConfigurationEditor.getConfigSpider();
		java.util.List<Crud> cruds = configSpider.getCruds(); //todo os cruds desse projeto
		
		for (Crud crud : cruds) {
			if (!validateCrud(crud)) {
				return false;
			}
		}		
		
		return true;
	}
	
	*//**
	 * Internal Validation Rules for CRUD.
	 * @param crud
	 * @return
	 *//*
	private boolean validateCrud(Crud crud) {
		boolean primaryKeyValid = false;
		
		for (CrudField field : crud.getCrudClass().getFields()) {
			if (field.isPrimaryKey()) {
				primaryKeyValid = true;
				break;
			}
		}
		
		if (!primaryKeyValid) {
			DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.validateBuild.title", "spider"), PropertiesUtil.getMessage("dialog.validateBuild.crud.primaryKey", new String[] {crud.getName()}, "spider"), DialogUtil.MESSAGE_TYPE.WARN);
			return false;
		}
		
		return true;
	}
	
	public void onOpen() {
		if (refreshComboAnnotation) {
			refreshComboAnnotationNow();
			refreshComboAnnotation = false;
		}
		
		//info about classes
		if (listClass.getItemCount() == 0) {
			String annotation = comboAnnotation.getItem(comboAnnotation.getSelectionIndex());
			
			DialogUtil.showMessage(PropertiesUtil.getMessage("dialog.message.title", "spider"), PropertiesUtil.getMessage("dialog.crudByMapping.noClass", new String[] {annotation}, "spider"), DialogUtil.MESSAGE_TYPE.INFO);
		}		
	}

	public void setEnabledBuild(boolean enabled) {
		buttonBuild.setEnabled(enabled);		
	}*/
}
