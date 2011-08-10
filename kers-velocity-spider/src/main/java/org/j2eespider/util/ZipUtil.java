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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;

public class ZipUtil {
	
	/**
	 * Unzip a inputstream to a folder
	 * @param pathDest
	 * @param buffInputStream
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void unZip(String pathDest, BufferedInputStream buffInputStream) throws FileNotFoundException, IOException {
		//create destination dir
		new File(pathDest).mkdirs();
		final int BUFFER = 2048;
		
		BufferedOutputStream dest = null;
		
		//unzip file
		ZipInputStream zis = new ZipInputStream(buffInputStream);
		ZipEntry entry;
		while((entry = zis.getNextEntry()) != null) {
			int count;
			byte data[] = new byte[BUFFER];
			
			// create dirs to file
			String pathFile = pathDest+File.separator+entry.getName();
			File file = new File(pathFile);
			if (entry.isDirectory()) { //unzip only files
				file.mkdir();
				continue;
			}
			
			// write the files to the disk
			FileOutputStream fos = new FileOutputStream(pathFile);
			dest = new BufferedOutputStream(fos, BUFFER);
			
			while ((count = zis.read(data, 0, BUFFER)) != -1) {
				dest.write(data, 0, count);
			}
			dest.flush();
			dest.close();
		}
		zis.close();		
	}

}
