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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.eclipse.ui.internal.WorkbenchPlugin;
//import org.eclipse.ui.internal.util.BundleUtility;
//import org.osgi.framework.Bundle;
import org.j2eespider.ide.data.domain.PluginData;

/**
 * 
 * @author bruno.braga
 */
public class PluginVersionUtil {
	
	/**
	 * Return a List of all plug-ins installed on the Eclipse.
	 * @return
	 */
	public static List<PluginData> getPlugins() {
		/*Bundle[] bundles = WorkbenchPlugin.getDefault().getBundles();
		
        // create a data object for each bundle, remove duplicates, and include only resolved bundles (bug 65548)
        Map<String, PluginData> map = new HashMap<String, PluginData>();
        for (int i = 0; i < bundles.length; ++i) {
            PluginData data = new PluginData(bundles[i]);
            if (BundleUtility.isReady(data.getState()) && !map.containsKey(data.getVersionedId())) {
				map.put(data.getVersionedId(), data);
			}
        }
*/        
        List<PluginData> plugins = new ArrayList();
/*        for (PluginData data : map.values()) {
        	plugins.add(data);
        }
*/
		return plugins;
	}
	
	/**
	 * Check if plugin installed isMinimumVersion required.
	 * See examples in {@link org.j2eespider.tests.PluginVersionUtilTest}
	 * 
	 * @param requiredVersion
	 * @param currentVersion
	 * @return
	 */
	public static boolean isMinimumVersion(String requiredVersion, String currentVersion) {
		String requiredID[] = requiredVersion.split(".");
		String currentID[] = currentVersion.split(".");
		int maxlen = requiredID.length > currentID.length ? requiredID.length : currentID.length;
		
		for(int i=0; i<maxlen; i++){
			int requiredValue = 0;
			int currentValue = 0;
			boolean requiredNaN = false;
			try {
				requiredValue = new Integer(requiredID[i]).intValue();
			} catch (NullPointerException e) { 
				requiredValue = 0;
				requiredNaN = true;
			} catch (NumberFormatException e1) {
				requiredValue = 0;
				requiredNaN = true;
			}
			
			try {
				currentValue = new Integer(currentID[i]).intValue();
			} catch (NullPointerException e) { 
				currentValue = 0; 
			} catch (NumberFormatException e1) { 
				currentValue = 0;
			} catch (ArrayIndexOutOfBoundsException e2) {
				if (requiredNaN) {return true;} //last required is Not an Number, then current version >= required version
				else {return false;} //need a last number in required... current is out characters... then current < required
			}
			
			if (requiredValue==currentValue) {
				continue;
			} else{
				int bigger = requiredValue > currentValue ? requiredValue : currentValue;
				if(bigger==requiredValue)
					return false;
				if(bigger==currentValue)
					return true;
			}					
		}
		
		return true;
	}
}
