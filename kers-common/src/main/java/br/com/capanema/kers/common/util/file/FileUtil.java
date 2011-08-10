/*******************************************************************************
 * Copyright (c) 2006 Bruno G. Braga. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Bruno G. Braga - initial API and implementation
 *******************************************************************************/
package br.com.capanema.kers.common.util.file;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

  /**
   * Copies all FILES under srcDir to dstDir (except velocity files .vm) If
   * dstDir does not exist, it will be created.
   * @param srcDir
   * @param dstDir
   * @throws IOException
   */
  public static void copyTemplateDirectory(File srcDir, File dstDir) throws IOException {
    if (srcDir.isDirectory()) {
      // o diret�rio j� � criado ao copiar o arquivo
      // if (!dstDir.exists()) {
      // dstDir.mkdirs();
      // }

      String[] children = srcDir.list();
      for (int i = 0; i < children.length; i++) {

        // ignore CVS and SVN folders
        if (children[i].equals("CVS") || children[i].equals(".svn")) { // n�o
                                                                       // processa
                                                                       // pastas
                                                                       // filhas
                                                                       // da
                                                                       // pasta
                                                                       // CVS ou
                                                                       // SVN
                                                                       // (elas
                                                                       // s�o
                                                                       // ignoradas)
          continue;
        }

        copyTemplateDirectory(new File(srcDir, children[i]), new File(dstDir, children[i]));
      }
    } else {
      // generate file
      Pattern pattern =
              Pattern.compile("(\\\\fragment\\\\.*?\\.vm)|(/fragment/.*?\\.vm)|(\\.[\\w\\d]+\\.vm)|(\\..*?\\.groovy)|(\\..*?\\.ftl)|(.*?\\.zip)"); // n�o
                                                                                                                                                   // realiza
                                                                                                                                                   // a
                                                                                                                                                   // copia
                                                                                                                                                   // do
                                                                                                                                                   // .*.vm
                                                                                                                                                   // ou
                                                                                                                                                   // /fragment/*.vm,
                                                                                                                                                   // groovy,
                                                                                                                                                   // ftl,
                                                                                                                                                   // zip
                                                                                                                                                   // ->
                                                                                                                                                   // s�o
                                                                                                                                                   // arquivos
                                                                                                                                                   // do
                                                                                                                                                   // template
                                                                                                                                                   // do
                                                                                                                                                   // spider
      Matcher matcher = pattern.matcher(srcDir.getPath());

      if (!matcher.find()) { // copy all, except velocity template (spider)
                             // files
        copyDir(srcDir, dstDir);
      }
    }
  }

  /**
   * Copies src file to dst file. If the dst file does not exist, it is created
   * @param src
   * @param dst
   * @throws IOException
   */
  private static void copyDir(File src, File dst) throws IOException {
    String dirWriter = dst.getAbsolutePath();
    if (src.exists() && src.isFile()) { // se vai fazer a copia de um arquivo,
                                        // pega o diretorio de geração e
                                        // certifica-se de que ele esta criado
      // trunc WriterFile
      int lastSeparator = dst.getAbsolutePath().lastIndexOf("/");
      if (lastSeparator == -1) {
        lastSeparator = dst.getAbsolutePath().lastIndexOf("\\");
      }
      dirWriter = dst.getAbsolutePath().substring(0, lastSeparator);
    }

    // create dir of WriterFile
    new File(dirWriter).mkdirs();

    if (!src.exists() || src.getAbsolutePath().equals("")) {
      return;
    } // file src not exists, return

    InputStream in = new FileInputStream(src);
    OutputStream out = new FileOutputStream(dst);

    // Transfer bytes from in to out
    byte[] buf = new byte[1024];
    int len;
    while ((len = in.read(buf)) > 0) {
      out.write(buf, 0, len);
    }
    in.close();
    out.close();
  }

  public static void copyFile(String caminhoOrigem, String caminhoDestino) throws IOException {

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

  public static void createDir(String caminho) throws IOException {
    File diretorio = new File(caminho);
    if (!diretorio.exists()) {
      diretorio.mkdirs();
    }
  }

  /**
   * Checks if exists the archive in the directory.
   * @param fileName
   * @param fileDir
   * @return
   */
  public static boolean existsFileInDir(String fileName, File fileDir) {
    if (fileDir.isDirectory()) {
      String[] childrens = fileDir.list();
      for (String children : childrens) {
        if (children.equals(fileName)) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Deletes all files and subdirectories under dir. Returns true if all
   * deletions were successful. If a deletion fails, the method stops attempting
   * to delete and returns false.
   */
  public static boolean deleteDir(File dir) {
    if (dir.isDirectory()) {
      String[] children = dir.list();
      for (int i = 0; i < children.length; i++) {
        boolean success = deleteDir(new File(dir, children[i]));
        if (!success) {
          return false;
        }
      }
    }

    // The directory is now empty so delete it
    return dir.delete();
  }

  /**
   * Write content to file.
   * @param filePath
   * @param content
   */
  public static void writeFile(String filePath, String content) {
    try {
      // Open or create the output file
      FileOutputStream os = new FileOutputStream(filePath);
      FileDescriptor fd = os.getFD();

      // Write some data to the stream
      os.write(content.getBytes());

      // Flush the data from the streams and writers into system buffers.
      // The data may or may not be written to disk.
      os.flush();

      // Block until the system buffers have been written to disk.
      // After this method returns, the data is guaranteed to have
      // been written to disk.
      fd.sync();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
