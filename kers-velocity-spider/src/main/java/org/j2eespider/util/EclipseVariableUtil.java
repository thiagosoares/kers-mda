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

/*import org.eclipse.core.runtime.CoreException;
 import org.eclipse.core.variables.IStringVariableManager;
 import org.eclipse.core.variables.IValueVariable;
 import org.eclipse.core.variables.VariablesPlugin;*/

public class EclipseVariableUtil {

	/**
	 * Add new Eclipse Variable
	 * 
	 * @param name
	 * @param value
	 * @param description
	 */
	/*public static void addEclipseVariable(String name, String value,
			String description) {
		IStringVariableManager stringVariableManager = VariablesPlugin
				.getDefault().getStringVariableManager();
		// IStringVariableManager stringVariableManager =
		// StringVariableManager.getDefault();

		// write Eclipse variables
		IValueVariable newVariable = stringVariableManager.newValueVariable(
				name, description);
		newVariable.setValue(value);
		try {
			stringVariableManager
					.removeVariables(new IValueVariable[] { newVariable });
			stringVariableManager
					.addVariables(new IValueVariable[] { newVariable });
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}*/

	/**
	 * Add new Eclipse Variable
	 * 
	 * @param name
	 * @param value
	 * @param description
	 */
	/*public static void removeEclipseVariable(String name) {
		IStringVariableManager stringVariableManager = VariablesPlugin
				.getDefault().getStringVariableManager();

		IValueVariable variable = stringVariableManager.getValueVariable(name);
		if (variable != null) {
			stringVariableManager
					.removeVariables(new IValueVariable[] { variable });
		}
	}*/

	/**
	 * Return value of Str
	 * 
	 * @param name
	 * @return
	 */
	/*public static IValueVariable getEclipseVariable(String name) {
		IStringVariableManager stringVariableManager = VariablesPlugin
				.getDefault().getStringVariableManager();
		// IStringVariableManager stringVariableManager =
		// StringVariableManager.getDefault();

		// get Eclipse variables
		IValueVariable variable = stringVariableManager.getValueVariable(name);

		return variable;

		// IValueVariable[] elements =
		// stringVariableManager.getValueVariables();
		// for (IValueVariable variable : elements) {
		// if (variable.getName().equals(name)) {
		// return variable;
		// }
		// }
		//
		// return null;
	}*/
}
