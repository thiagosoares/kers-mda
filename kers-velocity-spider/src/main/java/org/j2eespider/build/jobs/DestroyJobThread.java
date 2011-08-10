package org.j2eespider.build.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.j2eespider.util.CommandLineUtil;

public class DestroyJobThread extends Thread {
	private boolean check = true;
	private IProgressMonitor monitor;
	
	public DestroyJobThread(IProgressMonitor monitor) {
		this.monitor = monitor;
	}
	
	public void run() {
		while (check) {
	        try {
	        	Thread.sleep(1000);
	        } catch (InterruptedException e) {
	        	e.printStackTrace();
	        }
	        
			//check cancel
	        if (monitor.isCanceled()) {
	        	CommandLineUtil.cancelProcess(); //cancel background process if exists	  
	        }
		}
	}
	
	public void stopCheck() {
		this.check = false;
	}
}
