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

import java.util.Map;
/*
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Display;*/
import org.j2eespider.build.dialogs.MessageDetailDialog;
import org.j2eespider.ide.editors.ConfigurationEditor;

public class DialogUtil {
	public static enum MESSAGE_TYPE {
		INFO, ERROR, WARN
	}
	
	/**
	 * Show Dialog for message (info / error)
	 * @param title
	 * @param message
	 * @param message_type
	 */
	public static void showMessage(final String title, final String message, final MESSAGE_TYPE message_type) {		
       /* Display.getDefault().asyncExec(new Runnable() {
           public void run() {
    		   Action action = new Action("Message") {
    				public void run() {
    					if (message_type == MESSAGE_TYPE.INFO) {
    						MessageDialog.openInformation(ConfigurationEditor.shell, title, message);
    					} else if (message_type == MESSAGE_TYPE.ERROR) {
    						MessageDialog.openError(ConfigurationEditor.shell, title, message);
    					} else if (message_type == MESSAGE_TYPE.WARN) {
    						MessageDialog.openWarning(ConfigurationEditor.shell, title, message);
    					}
    				}
    			};
    			action.run();
           }
        });*/
    }	
	
	/**
	 * Show Dialog for message with textarea (info / error)
	 * @param title
	 * @param message
	 * @param message_type
	 */
	public static void showMessageDetail(final String title, final String message, final String messageDetail, final Map<String, String> styles, final boolean enableDetail, final MESSAGE_TYPE message_type) {	//Param modificado	
        /*Display.getDefault().asyncExec(new Runnable() {
           public void run() {
    		   Action action = new Action("Message") {
    				public void run() {
    					if (message_type == MESSAGE_TYPE.INFO) {
    						MessageDetailDialog.openInformation(ConfigurationEditor.shell, title, message, messageDetail, styles, enableDetail);
    					} else if (message_type == MESSAGE_TYPE.ERROR) {
    						MessageDetailDialog.openError(ConfigurationEditor.shell, title, message, messageDetail, styles, enableDetail);
    					} else if (message_type == MESSAGE_TYPE.WARN) {
    						MessageDetailDialog.openWarning(ConfigurationEditor.shell, title, message, messageDetail, styles, enableDetail);
    					}
    				}
    			};
    			action.run();
           }
        });*/
    }	
	
	/**
	 * Show Sync Dialog for message (info / error)
	 * @param title
	 * @param message
	 * @param message_type
	 */
	public static void showSyncMessage(final String title, final String message, final MESSAGE_TYPE message_type) {		
        /*Display.getDefault().syncExec(new Runnable() {
           public void run() {
    		   Action action = new Action("Message") {
    				public void run() {
    					if (message_type == MESSAGE_TYPE.INFO) {
    						MessageDialog.openInformation(ConfigurationEditor.shell, title, message);
    					} else if (message_type == MESSAGE_TYPE.ERROR) {
    						MessageDialog.openError(ConfigurationEditor.shell, title, message);
    					} else if (message_type == MESSAGE_TYPE.WARN) {
    						MessageDialog.openWarning(ConfigurationEditor.shell, title, message);
    					}
    				}
    			};
    			action.run();
           }
        });*/
    }	
	
	/**
	 * Create a sync thread for Dialog
	 * @param dialog
	 */
	public static void openSyncDialog(final String dialog) { //Param modificado
		/*Display.getDefault().syncExec(new Runnable() {
	        public void run() {
	        	Action action = new Action("Dialog") {
	        		public void run() {
	        			dialog.open();
	        		}
	        	};
	        	action.run();
			}    
	    });*/
	}
}
