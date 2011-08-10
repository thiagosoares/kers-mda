package br.com.capanema.kers.core.util.jar;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class JarUtil {

	
	public static void ler() throws IOException {
		
		
		FileInputStream ie = new FileInputStream("/home/thiago/TMP/interpass-ejb-1.0-SNAPSHOT.jar");
		
		JarInputStream jar = new JarInputStream(ie);
		
		Manifest ma = jar.getManifest();
		
		JarEntry jare = jar.getNextJarEntry();
		
		JarEntry type = null;
		do{
			type = (JarEntry) jar.getNextJarEntry();
			System.out.println(type);
		}while (type != null);
		
		//JarEntry jarz = jar.getNextJarEntry();
		
	}
	
	
	public static void main(String[] args) {
		try {
			ler();
			System.out.println("OK");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
