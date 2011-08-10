package br.com.capanema.kers.core.util.file;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileManager {

	public static void createFife(String nome, String caminho, String conteudo) throws IOException {
		gerarDiretorio(caminho);
		File file = new File(caminho, nome);
		FileWriter fw = new FileWriter(file);

		// escreve
		fw.write(conteudo);
		fw.flush();
		// Cria
		file.createNewFile();
		fw.close();
	}

	public static void gerarDiretorio(String caminho) throws IOException {
		File diretorio = new File(caminho);
		if (!diretorio.exists()) {
			diretorio.mkdirs();
		}
	}
	
	public static void copiarArquivo(String caminhoOrigem, String caminhoDestino) throws IOException {
		
			// Create channel on the source
			FileChannel srcChannel = new FileInputStream(caminhoOrigem).getChannel();

			// Create channel on the destination
			FileChannel dstChannel = new FileOutputStream(caminhoDestino).getChannel();

			// Copy file contents from source to destination
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

			// Close the channels
			srcChannel.close();
			dstChannel.close();
	}
	
	public static String lerArquivo(String caminhoOrigem) throws IOException {
	  BufferedReader in = new BufferedReader(new FileReader(caminhoOrigem));
	  String str;
    String str2 = "";
    while ((str = in.readLine()) != null) {
        str2 += str + "\n" ; 
    }
    in.close();
    return str2;
	}
	
	public static void gravarArquivo(String caminhoOrigem, String conteudo) throws IOException {
	  BufferedWriter out = new BufferedWriter(new FileWriter(caminhoOrigem));
	  out.write(conteudo);
	  out.close();
	}
	
	  
}