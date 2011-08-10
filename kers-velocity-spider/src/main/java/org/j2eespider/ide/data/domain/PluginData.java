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
package org.j2eespider.ide.data.domain;

import java.io.IOException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.internal.provisional.verifier.CertificateVerifier;
import org.eclipse.osgi.internal.provisional.verifier.CertificateVerifierFactory;
//import org.eclipse.ui.internal.WorkbenchMessages;
//import org.eclipse.ui.internal.WorkbenchPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;

public class PluginData {
    private String providerName;

    private String name;

    private String version;

    private String id;

    private String versionedId = null;

	private Bundle bundle;
	
    private boolean isSignedDetermined = false;	

	private boolean isSigned;
    
    public PluginData(Bundle bundle) {
        this.providerName = getResourceString(bundle, Constants.BUNDLE_VENDOR);
        this.name = getResourceString(bundle, Constants.BUNDLE_NAME);
        this.version = getResourceString(bundle, Constants.BUNDLE_VERSION);
        this.id = bundle.getSymbolicName();
        
        this.bundle = bundle;   
    }

    public int getState() {
        return bundle.getState();
    }

    /**
     * Return a string representation of the arugment state. Does not return
     * null.
     */
    public String getStateName() {
        /*switch (getState()) {
        case Bundle.INSTALLED:
            return WorkbenchMessages.AboutPluginsDialog_state_installed;
        case Bundle.RESOLVED:
            return WorkbenchMessages.AboutPluginsDialog_state_resolved;
        case Bundle.STARTING:
            return WorkbenchMessages.AboutPluginsDialog_state_starting;
        case Bundle.STOPPING:
            return WorkbenchMessages.AboutPluginsDialog_state_stopping; 
        case Bundle.UNINSTALLED:
            return WorkbenchMessages.AboutPluginsDialog_state_uninstalled;
        case Bundle.ACTIVE:
            return WorkbenchMessages.AboutPluginsDialog_state_active; 
        default:
            return WorkbenchMessages.AboutPluginsDialog_state_unknown; 
        }*/
    	return null;
    }

    /**
     * A function to translate the resource tags that may be embedded in a
     * string associated with some bundle.
     * 
     * @param headerName
     *            the used to retrieve the correct string
     * @return the string or null if the string cannot be found
     */
    private static String getResourceString(Bundle bundle, String headerName) {
        String value = (String) bundle.getHeaders().get(headerName);
        return value == null ? null : Platform.getResourceString(bundle, value);
    }
    
    public boolean isSignedDetermined() {
    	return isSignedDetermined;
    }
    
    public boolean isSigned() throws IllegalStateException {

		/*if (isSignedDetermined)
			return isSigned;

		BundleContext bundleContext = WorkbenchPlugin.getDefault().getBundleContext();
		ServiceReference certRef = bundleContext
				.getServiceReference(CertificateVerifierFactory.class.getName());
		if (certRef == null)
			throw new IllegalStateException();
		CertificateVerifierFactory certFactory = (CertificateVerifierFactory) bundleContext
				.getService(certRef);
		try {
			CertificateVerifier verifier = certFactory.getVerifier(bundle);
			isSigned = verifier.isSigned();
			isSignedDetermined = true;
			return isSigned;
		} catch (IOException e) {
			throw (IllegalStateException) new IllegalStateException()
					.initCause(e);
		} finally {
			bundleContext.ungetService(certRef);
		}*/
    	
    	return true;
	}

	/**
	 * @return
	 */
	public Bundle getBundle() {
		return bundle;
	}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getVersion() {
        return version;
    }

    public String getVersionedId() {
        if (versionedId == null) {
			versionedId = getId() + "_" + getVersion(); //$NON-NLS-1$
		}
        return versionedId;
    }
}
