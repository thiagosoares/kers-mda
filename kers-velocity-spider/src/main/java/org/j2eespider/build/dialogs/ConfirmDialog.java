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
package org.j2eespider.build.dialogs;


/*import org.eclipse.core.variables.IValueVariable;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;*/
import org.j2eespider.SpiderPlugin;
import org.j2eespider.ide.editors.pages.ConfigPage;
import org.j2eespider.util.EclipseVariableUtil;
import org.j2eespider.util.PropertiesUtil;

public class ConfirmDialog { //extends Dialog {
	/*public int MERGE_ID = 1;
	public int REPLACE_ID = 2;
	public int SKIP_ID = 3;
	//private Button optionRemember;
	private boolean isRemember;	
	private String pathFile;
	
	public ConfirmDialog(Shell parentShell, String pathFile) {
		super(parentShell);
		this.pathFile = pathFile;
	}
	
	protected Control createContents(Composite parent) {
		// create the top level composite for the dialog
		Composite composite = new Composite(parent, 0);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(composite);
		// initialize the dialog units
		initializeDialogUnits(composite);
		// create the dialog area and button bar
		dialogArea = createDialogArea(composite);
		buttonBar = createButtonBar(composite);
		createRememberArea(composite);
		
		Shell shell = getShell();
		Image icon = new Image(composite.getDisplay(), getClass().getResourceAsStream("/images/icon_merge.png"));
		shell.setImage(icon);
		
		//skip, if close
		shell.addListener (SWT.Close, new Listener () {
			public void handleEvent (Event event) {
				setReturnCode(SKIP_ID);
			}
		});
		
		return composite;
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite)super.createDialogArea(parent);
		
		//description
		String description = PropertiesUtil.getMessage("dialog.confirmBuild.description", "spider");
		description = description.replaceAll("\\{0\\}", pathFile);
		
		Label labelDescription = new Label(composite, SWT.NONE);
		labelDescription.setText(description);
		labelDescription.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));		
		
		return composite;
	}
	
	
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, MERGE_ID, PropertiesUtil.getMessage("dialog.confirmBuild.merge", "spider"), true);
		createButton(parent, REPLACE_ID, PropertiesUtil.getMessage("dialog.confirmBuild.replace", "spider"), false);
		createButton(parent, SKIP_ID, PropertiesUtil.getMessage("dialog.confirmBuild.skip", "spider"), false);

		String mergeTool = SpiderPlugin.getDefault().getMergeToolPreference();
		if (mergeTool == null || mergeTool.equals("")) { //if not config merge tool
			getButton(MERGE_ID).setEnabled(false);
		}
	}

	protected Control createRememberArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);		
		
		optionRemember = new Button(composite, SWT.CHECK);
		
		Label labelRemember = new Label(composite, SWT.NONE);
		labelRemember.setText(PropertiesUtil.getMessage("dialog.confirmBuild.remember", "spider"));
		
		return composite;
	}
	
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(PropertiesUtil.getMessage("dialog.confirmBuild.title", "spider"));
	}
	
	protected void buttonPressed(int buttonId) {
		if (optionRemember.getSelection()) {
			isRemember = true;
		} else {
			isRemember = false;
		}
		
		
		setReturnCode(buttonId);
		close();
	}
	
	public boolean isRemember() {
		return isRemember;
	}
	*/
}
